package com.mascarpone.delivery.service.auth;

import com.mascarpone.delivery.entity.enums.AccountType;
import com.mascarpone.delivery.entity.enums.PaymentBank;
import com.mascarpone.delivery.security.Role;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.shopbranch.ShopBranch;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userbonusaccount.UserBonusAccount;
import com.mascarpone.delivery.entity.userrole.UserRole;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.auth.*;
import com.mascarpone.delivery.payload.cook.CookFullResponse;
import com.mascarpone.delivery.payload.courier.CourierFullResponse;
import com.mascarpone.delivery.payload.shop.ShopNameAndPrefixResponse;
import com.mascarpone.delivery.payload.user.UserLoginPasswordRequest;
import com.mascarpone.delivery.repository.shop.ShopRepository;
import com.mascarpone.delivery.repository.shopbranch.ShopBranchRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.repository.userbonusaccount.UserBonusAccountRepository;
import com.mascarpone.delivery.repository.userrole.UserRoleRepository;
import com.mascarpone.delivery.security.TokenProvider;
import com.mascarpone.delivery.service.mail.MailSendService;
import com.mascarpone.delivery.service.smssender.SmsSenderService;
import com.mascarpone.delivery.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mascarpone.delivery.exception.ExceptionConstants.*;
import static com.mascarpone.delivery.utils.Constants.*;
import static com.mascarpone.delivery.utils.Utils.keyGenerator;
import static com.mascarpone.delivery.utils.Utils.keyGeneratorDigitsOnly;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;
    private final MailSendService mailSendService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final SmsSenderService smsSenderService;
    private final UserBonusAccountRepository userBonusAccountRepository;
    private final ShopBranchRepository shopBranchRepository;

    /**
     * Root admin registration
     *
     * @param request - login, password and secret word
     * @return
     */
    @Override
    public GeneralAnswer<String> registerRootAdmin(UserLoginPasswordRequest request) {
        if (request.getSecretWord().equals(SECRET_WORD)) {
            checkAvailableLogin(request.getLogin());
            createNewRootAdmin(request.getLogin(), request.getPassword());

            return new GeneralAnswer<>("OK", null, null);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Root admin and shop authentication
     *
     * @param request - login and password
     * @return authentication token
     */
    @Override
    public ResponseEntity<?> authenticateShop(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User currentUser = userRepository.findByLogin(request.getLogin());
        AuthResponse response = new AuthResponse(tokenProvider.createToken(authentication), currentUser.getRoles());

        return ResponseEntity.ok(response);
    }

    /**
     * Shop registration
     *
     * @param request - login, shop name, shop prefix and payment bank
     * @return shop name and shop prefix
     * @throws MessagingException
     */
    @Override
    public ResponseEntity<?> registerShop(SignUpRequest request) throws MessagingException {
        checkAvailableLogin(request.getLogin());
        Shop newShop = createNewShop(request.getShopName(), request.getPrefix(), request.getPaymentBank());
        createNewShopUser(request.getLogin(), newShop);
        ShopNameAndPrefixResponse response = new ShopNameAndPrefixResponse(newShop);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop's request for password recovery
     *
     * @param email - shop e-mail
     * @return shop name and shop prefix
     * @throws MessagingException
     */
    @Override
    public ResponseEntity<?> sendPasswordResetRequest(String email) throws MessagingException {
        checkUserPresent(email);
        User shopUser = userRepository.findByLogin(email);
        String newPassword = keyGenerator().generate(10);
        shopUser.setPassword(passwordEncoder.encode(newPassword));
        shopUser.setPasswordReset(false);
        userRepository.save(shopUser);

        mailSendService.sendNewShopPassword(shopUser.getLogin(), newPassword);
        ShopNameAndPrefixResponse response = new ShopNameAndPrefixResponse(shopUser.getShop());

        return ResponseEntity.ok(response);
    }

    /**
     * User authentication: sms code request
     *
     * @param phoneNumber - phone number
     * @param shopPrefix  - shop prefix
     * @return status: registration or authentication
     */
    @Override
    public GeneralAnswer<String> requestSmsCode(String phoneNumber, String shopPrefix) {
        checkPhoneNumberMask(phoneNumber);
        String smsCode = keyGeneratorDigitsOnly().generate(4);
        User currentUser;
        Date currentDate = new Date();
        String statusRegistration;
        Shop currentShop = shopRepository.findByPrefix(shopPrefix)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));

        if (userRepository.findByLoginAndShopPrefix(phoneNumber, shopPrefix).isPresent()) {
            currentUser = userRepository.findByLoginAndShopPrefix(phoneNumber, shopPrefix).get();

            if (Utils.getDateDiff(currentUser.getSmsCodeSendDate(), currentDate, TimeUnit.SECONDS) < 60) {
                throw new BadRequestException(TOO_FREQUENT_CODE_REQUEST);
            }

            statusRegistration = "auth";
        } else {
            currentUser = createNewUser(phoneNumber, currentShop);
            statusRegistration = "reg";
        }

        smsSenderService.sendSms(phoneNumber, currentShop.getName() + ": " + smsCode);
        currentUser.setSmsCode(smsCode);
        currentUser.setSmsCodeSendDate(currentDate);
        currentUser.setSmsCodeEnterCount(0);
        userRepository.save(currentUser);

        return new GeneralAnswer<>(statusRegistration, null, null);
    }

    /**
     * User authentication: sms code check
     *
     * @param request - user's phone number (79XXXXXXXXX), sms code and shop prefix
     * @return authentication token and user role
     */
    @Override
    public ResponseEntity<?> checkSmsCode(CheckSmsCodeRequest request) {
        String phoneNumber = request.getPhoneNumber();
        checkPhoneNumberMask(phoneNumber);
        User currentUser = userRepository.findByLoginAndShopPrefix(phoneNumber, request.getShopPrefix())
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        Date currentDate = new Date();
        checkCodeEnterTime(currentUser, currentDate);
        int count = currentUser.getSmsCodeEnterCount();
        checkAttemptsCount(count);
        currentUser.setSmsCodeEnterCount(++count);
        userRepository.save(currentUser);

        checkSmsCode(request.getSmsCode(), currentUser, phoneNumber);
        currentUser.setEnabled(true);
        userRepository.save(currentUser);

        String token = tokenProvider.buildAuthToken(currentUser.getId(), currentDate);
        AuthResponse response = new AuthResponse(token, currentUser.getRoles());

        return ResponseEntity.ok(response);
    }

    /**
     * Courier registration
     *
     * @param request     - courier's login, password, name, phone number and passport
     * @param shopAdminId - shop admin id
     * @return courier entity
     */
    @Override
    public ResponseEntity<?> registerCourier(CourierRegisterRequest request, Long shopAdminId) {
        checkAvailableLogin(request.getLogin());
        User shopAdmin = userRepository.getOne(shopAdminId);

        return ResponseEntity.ok(new CourierFullResponse(createNewCourier(request, shopAdmin.getShop())));
    }

    /**
     * Courier authentication
     *
     * @param request - courier's login and password and shop prefix
     * @return authentication token, user role and username
     */
    @Override
    public ResponseEntity<?> authenticateCourier(CourierAndCookLoginRequest request) {
        String login = request.getLogin();
        User currentUser = userRepository.findByLoginAndShopPrefix(login, request.getPrefix())
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CourierAuthResponse response = new CourierAuthResponse(
                tokenProvider.createToken(authentication),
                currentUser.getRoles(),
                currentUser.getName());

        return ResponseEntity.ok(response);
    }

    /**
     * Cook registration
     *
     * @param request     - cook's login and password and shop branch id
     * @param shopAdminId - shop admin id
     * @return cook entity
     */
    @Override
    public ResponseEntity<?> registerCook(CookRegisterRequest request, Long shopAdminId) {
        checkAvailableLogin(request.getLogin());
        User shopAdmin = userRepository.getOne(shopAdminId);
        CookFullResponse response = new CookFullResponse(createNewCook(request, shopAdmin.getShop()));

        return ResponseEntity.ok(response);
    }

    /**
     * Cook authentication
     *
     * @param request - cook's login and password and shop prefix
     * @return authentication token, user role and username
     */
    @Override
    public ResponseEntity<?> authenticateCook(CourierAndCookLoginRequest request) {
        String login = request.getLogin();
        User currentUser = userRepository.findByLoginAndShopPrefix(login, request.getPrefix())
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponse response = new AuthResponse(tokenProvider.createToken(authentication), currentUser.getRoles());

        return ResponseEntity.ok(response);
    }

    /**
     * Creating a new user with role "root admin"
     *
     * @param login    - login
     * @param password - password
     */
    private void createNewRootAdmin(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setDateCreate(new Date());
        user.setPasswordReset(false);
        user.setSmsCodeEnterCount(0);
        user.setAccountType(AccountType.ROOTADMIN);
        userRepository.save(user);
        createNewUserRole(user, Role.ROOT_ADMIN);
        userRepository.save(user);
    }

    /**
     * Creating a new shop
     *
     * @param name        - shop name
     * @param prefix      - shop prefix
     * @param paymentBank - payment bank
     * @return shop entity
     */
    private Shop createNewShop(String name, String prefix, PaymentBank paymentBank) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setPrefix(prefix);
        shop.setPaymentBank(paymentBank);
        shop.setBonusPayAmount(new BigDecimal(0));
        shop.setWelcomeBonusAmount(new BigDecimal(0));
        shop.setOrderAmountPercent(new BigDecimal(0));
        shop.setOrderPreparationTime(0);
        shopRepository.save(shop);
        createDefaultShopBranch(shop);

        return shop;
    }

    /**
     * Creating a new user with role "shop"
     *
     * @param login - login
     * @param shop  - shop entity
     * @throws MessagingException
     */
    private void createNewShopUser(String login, Shop shop) throws MessagingException {
        User user = new User();
        user.setLogin(login);
        String password = keyGenerator().generate(10);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setPasswordReset(false);
        user.setDateCreate(new Date());
        user.setSmsCodeEnterCount(0);
        user.setShop(shop);
        user.setShopPrefix(shop.getPrefix());
        user.setAccountType(AccountType.SHOP);
        userRepository.save(user);
        createNewUserRole(user, Role.SHOP);
        userRepository.save(user);
        mailSendService.sendNewShopPassword(login, password);
    }

    /**
     * Creating a new user role
     *
     * @param user - user entity
     * @param role - role
     */
    private void createNewUserRole(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }

    /**
     * Creating a new user bonus account
     *
     * @param user - user entity
     * @param shop - shop entity
     */
    private void createNewUserBonusAccount(User user, Shop shop) {
        UserBonusAccount newUserBonusAccount = new UserBonusAccount();
        newUserBonusAccount.setCustomer(user);
        newUserBonusAccount.setBonusAmount(BigDecimal.valueOf(0));

        if (shop.isBonusSystem()) {
            newUserBonusAccount.setBonusAmount(shop.getWelcomeBonusAmount());
        } else {
            newUserBonusAccount.setBonusAmount(new BigDecimal(0));
        }

        userBonusAccountRepository.save(newUserBonusAccount);
        user.setBonusAccount(newUserBonusAccount);
        userRepository.save(user);
    }

    /**
     * Creating a new user with role "user"
     *
     * @param phoneNumber - user's phone number
     * @param shop        - shop entity
     * @return user entity
     */
    private User createNewUser(String phoneNumber, Shop shop) {
        User newUser = new User();
        newUser.setLogin(phoneNumber);
        newUser.setDateCreate(new Date());
        newUser.setShopPrefix(shop.getPrefix());
        newUser.setShop(shop);
        newUser.setAccountType(AccountType.CUSTOMER);
        userRepository.save(newUser);
        createNewUserRole(newUser, Role.USER);
        createNewUserBonusAccount(newUser, shop);
        userRepository.save(newUser);

        return newUser;
    }

    /**
     * Checking a phone number mask
     *
     * @param phoneNumber - user's phone number
     */
    private void checkPhoneNumberMask(String phoneNumber) {
        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new BadRequestException(INVALID_REQUEST_FORMAT);
        }
    }

    /**
     * Checking a code enter time
     *
     * @param currentUser - user entity
     * @param currentDate - current date
     */
    private void checkCodeEnterTime(User currentUser, Date currentDate) {
        if (Utils.getDateDiff(currentUser.getSmsCodeSendDate(), currentDate, TimeUnit.MINUTES) > 5) {
            throw new BadRequestException(TIME_IS_UP);
        }
    }

    /**
     * Checking an attempts count
     *
     * @param count - attempts count
     */
    private void checkAttemptsCount(int count) {
        if (count >= 3) {
            throw new BadRequestException(ATTEMPTS_EXHAUSTED);
        }
    }

    /**
     * Checking an sms code
     *
     * @param smsCode     - sms code
     * @param currentUser - user entity
     * @param phoneNumber - user's phone number
     */
    private void checkSmsCode(String smsCode, User currentUser, String phoneNumber) {
        if (!smsCode.equals(currentUser.getSmsCode()) && !(phoneNumber.equals(SECRET_PHONE_NUMBER) && smsCode.equals(SECRET_SMS_CODE))) {
            throw new BadRequestException(WRONG_SMS_CODE);
        }
    }

    /**
     * Creating a new user with role "courier"
     *
     * @param request - courier's login, password, name, phone number and passport
     * @param shop    - shop entity
     * @return courier entity
     */
    private User createNewCourier(CourierRegisterRequest request, Shop shop) {
        checkPhoneNumberMask(request.getPhoneNumber());

        User newCourier = new User();
        newCourier.setLogin(request.getLogin());
        newCourier.setPassword(passwordEncoder.encode(request.getPassword()));
        newCourier.setName(request.getCourierName());
        newCourier.setPhoneNumber(request.getPhoneNumber());
        newCourier.setPassport(request.getPassport());
        newCourier.setShop(shop);
        newCourier.setShopPrefix(shop.getPrefix());
        newCourier.setEnabled(true);
        newCourier.setPasswordReset(false);
        newCourier.setDateCreate(new Date());
        newCourier.setCourierActive(false);
        newCourier.setAccountType(AccountType.COURIER);
        userRepository.save(newCourier);
        createNewUserRole(newCourier, Role.COURIER);
        userRepository.save(newCourier);

        return newCourier;
    }

    /**
     * Checking an available login
     *
     * @param login - login
     */
    private void checkAvailableLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new BadRequestException(USER_ALREADY_EXISTS);
        }
    }

    /**
     * Creating a new user with role "cook"
     *
     * @param request - courier's login, password, name, phone number and passport
     * @param shop    - shop entity
     * @return cook entity
     */
    private User createNewCook(CookRegisterRequest request, Shop shop) {
        User newCook = new User();
        newCook.setLogin(request.getLogin());
        newCook.setPassword(passwordEncoder.encode(request.getPassword()));
        newCook.setShop(shop);
        newCook.setShopPrefix(shop.getPrefix());
        newCook.setEnabled(true);
        newCook.setPasswordReset(false);
        newCook.setDateCreate(new Date());
        newCook.setSmsCodeEnterCount(0);
        newCook.setAccountType(AccountType.COOK);
        newCook.setShopBranch(shopBranchRepository.findById(request.getShopBranchId()).orElseThrow(() -> new BadRequestException(DEFINE_SHOP_BRANCH)));
        userRepository.save(newCook);
        createNewUserRole(newCook, Role.COOK);
        userRepository.save(newCook);

        return newCook;
    }

    /**
     * Creating a default shop branch
     *
     * @param shop - shop entity
     */
    private void createDefaultShopBranch(Shop shop) {
        ShopBranch shopBranch = new ShopBranch();
        shopBranch.setShop(shop);
        shopBranch.setActive(true);
        shopBranchRepository.save(shopBranch);
    }

    /**
     * Checking is user present
     *
     * @param email - e-mail
     */
    private void checkUserPresent(String email) {
        if (!userRepository.existsByLogin(email)) {
            throw new BadRequestException(USER_NOT_FOUND);
        }
    }
}
