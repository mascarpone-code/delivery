package com.mascarpone.delivery.service.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.entity.userbonusaccount.UserBonusAccount;
import com.mascarpone.delivery.entity.userpromocode.UserPromoCode;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.promocode.CreatedPromoCodeResponse;
import com.mascarpone.delivery.payload.promocode.PromoCodeListResponse;
import com.mascarpone.delivery.payload.promocode.UserPromoCodeResponse;
import com.mascarpone.delivery.repository.promocode.PromoCodeRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import com.mascarpone.delivery.repository.userbonusaccount.UserBonusAccountRepository;
import com.mascarpone.delivery.repository.userpromocode.UserPromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mascarpone.delivery.exception.ExceptionConstants.*;
import static com.mascarpone.delivery.utils.Constants.DEFAULT_PAGE;
import static com.mascarpone.delivery.utils.Constants.FETCH_RECORD_COUNT;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromoCodeServiceImpl implements PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;
    private final UserPromoCodeRepository userPromoCodeRepository;
    private final UserBonusAccountRepository userBonusAccountRepository;

    @Override
    public List<PromoCode> getAll() {
        return promoCodeRepository.findAll();
    }

    @Override
    public Optional<PromoCode> findById(Long id) {
        return promoCodeRepository.findById(id);
    }

    @Override
    public void save(PromoCode object) {
        promoCodeRepository.save(object);
    }

    @Override
    public Page<PromoCode> findAllByShopId(Long shopId, int page, int size) {
        return promoCodeRepository.findAllByShopId(shopId, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public Optional<PromoCode> findByIdAndShopId(Long id, Long shopId) {
        return promoCodeRepository.findByIdAndShopId(id, shopId);
    }

    @Override
    public Page<PromoCode> getActiveCodes(Long shopId, Date date, int page, int size) {
        return promoCodeRepository.getActiveCodes(shopId, date, PageRequest.of(page, size, Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public Optional<PromoCode> findByPromoCode(String promoCode) {
        return promoCodeRepository.findByPromoCode(promoCode);
    }

    @Override
    public List<PromoCode> findAllByShop(Long shopId, String code) {
        return promoCodeRepository.findAllByShop(shopId, code);
    }

    /**
     * Shop creates or updates a promocode.
     *
     * @param promoCode   - promocode entity
     * @param shopAdminId - shop admin id
     * @return promocode dto
     */
    @Override
    public ResponseEntity<?> createPromoCode(PromoCode promoCode, Long shopAdminId) {
        User creator = userRepository.getOne(shopAdminId);
        Shop shop = creator.getShop();
        String newPromoCode = promoCode.getPromoCode();

        if (promoCode.getId() != null) {
            PromoCode requestedPromoCode = promoCodeRepository.getOne(promoCode.getId());
            String oldPromoCode = requestedPromoCode.getPromoCode();

            if (!newPromoCode.equals(oldPromoCode)) {
                List<PromoCode> promoCodes = promoCodeRepository.findAllByShop(shop.getId(), oldPromoCode);
                List<String> codes = new ArrayList<>();

                for (PromoCode code : promoCodes) {
                    codes.add(code.getPromoCode());
                }

                if (codes.contains(newPromoCode)) {
                    throw new BadRequestException(PROMOCODE_EXISTS);
                }
            }

            promoCode.setCreator(requestedPromoCode.getCreator());
            promoCode.setShop(requestedPromoCode.getShop());
            promoCode.setDateCreate(requestedPromoCode.getDateCreate());
        } else {
            List<PromoCode> promoCodes = shop.getPromoCodes();
            List<String> codes = new ArrayList<>();

            for (PromoCode code : promoCodes) {
                codes.add(code.getPromoCode());
            }

            if (codes.contains(newPromoCode)) {
                throw new BadRequestException(PROMOCODE_EXISTS);
            }

            promoCode.setCreator(creator);
            promoCode.setShop(creator.getShop());
            promoCode.setDateCreate(new Date());
        }

        promoCodeRepository.save(promoCode);
        CreatedPromoCodeResponse response = new CreatedPromoCodeResponse(promoCode);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop gets a list of it's promocodes.
     *
     * @param page        - page number
     * @param active      - is promocode active or not
     * @param shopAdminId - shop admin id
     * @return list of promocodes
     */
    @Override
    public ResponseEntity<?> getPromoCodesByShopAdmin(Optional<Integer> page, boolean active, Long shopAdminId) {
        Page<PromoCode> promoCodes;
        Date nowDate = new Date();
        User shopAdmin = userRepository.getOne(shopAdminId);
        long shopId = shopAdmin.getShop().getId();

        if (active) {
            promoCodes = promoCodeRepository.getActiveCodes(shopId, nowDate, PageRequest.of(page.orElse(DEFAULT_PAGE), FETCH_RECORD_COUNT));
        } else {
            promoCodes = promoCodeRepository.findAllByShopId(shopId, PageRequest.of(page.orElse(DEFAULT_PAGE), FETCH_RECORD_COUNT));
        }

        List<CreatedPromoCodeResponse> responses = promoCodes
                .stream()
                .map(CreatedPromoCodeResponse::new)
                .collect(Collectors.toList());

        PromoCodeListResponse response = new PromoCodeListResponse(promoCodes.getTotalElements(), responses);

        return ResponseEntity.ok(response);
    }

    /**
     * Shop gets it's promocode.
     *
     * @param promoCodeId - promo code id
     * @param shopAdminId - shop admin id
     * @return promocode dto
     */
    @Override
    public ResponseEntity<?> getPromoCode(Long promoCodeId, Long shopAdminId) {
        User shopAdmin = userRepository.getOne(shopAdminId);
        PromoCode promoCode = promoCodeRepository.findByIdAndShop(promoCodeId, shopAdmin.getShop())
                .orElseThrow(() -> new BadRequestException(PROMOCODE_NOT_FOUND));
        CreatedPromoCodeResponse response = new CreatedPromoCodeResponse(promoCode);

        return ResponseEntity.ok(response);
    }

    /**
     * Customer applies a promocode
     *
     * @param promoCode  - promocode string
     * @param customerId - customer's id
     * @return status of the customer's bonus account
     */
    @Override
    public ResponseEntity<?> applyPromoCodeByCustomer(String promoCode, Long customerId) {
        User customer = userRepository.getOne(customerId);
        PromoCode currentPromoCode = promoCodeRepository.findByPromoCode(promoCode)
                .orElseThrow(() -> new BadRequestException(PROMOCODE_NOT_FOUND));
        Date nowDate = new Date();

        if (currentPromoCode.getValidFrom().after(nowDate) || currentPromoCode.getValidTo().before(nowDate)) {
            throw new BadRequestException(PROMOCODE_EXPIRED);
        } else {
            if (userPromoCodeRepository.existsByCustomerAndPromoCode(customer, currentPromoCode)) {
                throw new BadRequestException(PROMOCODE_ALREADY_USED);
            } else {
                UserPromoCode newUserPromoCode = new UserPromoCode();
                newUserPromoCode.setCustomer(customer);
                newUserPromoCode.setPromoCode(currentPromoCode);
                newUserPromoCode.setDateCreate(nowDate);
                userPromoCodeRepository.save(newUserPromoCode);

                UserBonusAccount currentUserBonusAccount = customer.getBonusAccount();
                BigDecimal promoCodePrice = currentPromoCode.getPrice();
                currentUserBonusAccount.setBonusAmount(currentUserBonusAccount.getBonusAmount().add(promoCodePrice));
                userBonusAccountRepository.save(currentUserBonusAccount);

                UserPromoCodeResponse response = new UserPromoCodeResponse(promoCodePrice, currentUserBonusAccount.getBonusAmount());

                return ResponseEntity.ok(response);
            }
        }
    }
}
