package com.mascarpone.delivery.service.user;

import com.mascarpone.delivery.entity.enums.AccountType;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.payload.user.UserForAdminListResponse;
import com.mascarpone.delivery.payload.user.UserForAdminResponse;
import com.mascarpone.delivery.payload.user.UserNameAddressRequest;
import com.mascarpone.delivery.payload.user.UserNameAddressResponse;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.repository.user.specification.UserSpecification;
import com.mascarpone.delivery.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;
import static com.mascarpone.delivery.utils.shop.ShopUtils.getShop;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User object) {
        userRepository.save(object);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Optional<User> findByLoginAndShopId(String login, Long shopId) {
        return userRepository.findByLoginAndShopId(login, shopId);
    }

    @Override
    public Optional<User> findByLoginAndShopPrefix(String login, String shopPrefix) {
        return userRepository.findByLoginAndShopPrefix(login, shopPrefix);
    }

    public Optional<User> findByUuidAndShopId(UUID uuid, Long shopId) {
        return userRepository.findByUuidAndShopId(uuid, shopId);
    }

    @Override
    public Page<User> findAllByShopIdAndRoles_Role(User filter, int page, int size) {
        var specification = Specification.where(new UserSpecification(filter));

        return userRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "name"));
    }

    @Override
    public Optional<User> findByShopPrefixAndPhoneNumber(User user) {
        var specification = Specification.where(new UserSpecification(user));

        return userRepository.findOne(specification);
    }

    @Override
    public Page<User> findAllByShop(User user, int page, int size) {
        var specification = Specification.where(new UserSpecification(user));

        return userRepository.findAll(specification, PageRequest.of(page, size, Sort.Direction.ASC, "login"));
    }

    @Override
    public List<User> findAllByShopIdAndRoles_Role(Long shopId, Role role) {
        return userRepository.findAllByShopIdAndRoles_Role(shopId, role);
    }

    @Override
    public List<User> findAllByShopBranchIdAndRoles_Role(Long shopBranchId, Role role) {
        return userRepository.findAllByShopBranchIdAndRoles_Role(shopBranchId, role);
    }

    @Override
    public List<User> findAllByShopIdAndRoles_RoleAndCourierActive(Long shopId, Role role) {
        return userRepository.findAllByShopIdAndRoles_RoleAndCourierActiveTrue(shopId, role);
    }

    @Override
    public Optional<User> findByLoginAndRoles_Role(String login, Role role) {
        return userRepository.findByLoginAndRoles_Role(login, role);
    }

    @Override
    public User getOne(UUID id) {
        return userRepository.getOne(id);
    }

    /**
     * Search for users by phone number.
     *
     * @param phoneNumber - phone number
     * @param page        - page number
     * @param shopAdminUuid - shop admin uuid
     * @return list of users
     */
    @Override
    public ResponseEntity<?> findUsersByPhoneNumberAndShopPrefix(String phoneNumber, Optional<Integer> page, UUID shopAdminUuid) {
        var user = new User();
        var shop = getShop(shopAdminUuid);
        user.setShopPrefix(shop.getPrefix());
        user.setPhoneNumber(phoneNumber);
        user.setAccountType(AccountType.CUSTOMER);

        var users = findAllByShop(
                user,
                page.orElse(DEFAULT_PAGE),
                FETCH_RECORD_COUNT);

        var userForAdminResponses = users
                .stream()
                .map(UserForAdminResponse::new)
                .collect(Collectors.toList());

        long totalCustomersCount = users.getTotalElements();
        var response = new UserForAdminListResponse(totalCustomersCount, userForAdminResponses);

        return ResponseEntity.ok(response);
    }

    /**
     * Customer gets his profile.
     *
     * @param customerUuid - customer's id
     * @return customer's profile
     */
    @Override
    public ResponseEntity<?> getUserProfile(UUID customerUuid) {
        var currentUser = userRepository.getOne(customerUuid);

        return ResponseEntity.ok(new UserNameAddressResponse(currentUser));
    }

    /**
     * Customer updates his profile.
     *
     * @param request    - customer's name and address
     * @param customerUuid - customer's id
     * @return customer's profile
     */
    @Override
    public ResponseEntity<?> updateCustomerProfile(UserNameAddressRequest request, UUID customerUuid) {
        var currentUser = userRepository.getOne(customerUuid);
        currentUser.setName(request.getName());
        currentUser.setAddress(request.getAddress());
        userRepository.save(currentUser);

        return ResponseEntity.ok(new UserNameAddressResponse(currentUser));
    }

    @Override
    public Optional<User> findByUuid(UUID userUuid) {
        return userRepository.findById(userUuid);
    }
}
