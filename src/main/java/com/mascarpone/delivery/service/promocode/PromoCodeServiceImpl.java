package com.mascarpone.delivery.service.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
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
        var creator = userRepository.getOne(shopAdminId);
        var shop = creator.getShop();
        var newPromoCode = promoCode.getPromoCode();

        if (promoCode.getId() != null) {
            var requestedPromoCode = promoCodeRepository.getOne(promoCode.getId());
            var oldPromoCode = requestedPromoCode.getPromoCode();

            if (!newPromoCode.equals(oldPromoCode)) {
                var promoCodes = promoCodeRepository.findAllByShop(shop.getId(), oldPromoCode);
                var codes = new ArrayList<>();

                for (var code : promoCodes) {
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
            var promoCodes = shop.getPromoCodes();
            var codes = new ArrayList<>();

            for (var code : promoCodes) {
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
        var response = new CreatedPromoCodeResponse(promoCode);

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
        var nowDate = new Date();
        var shopAdmin = userRepository.getOne(shopAdminId);
        long shopId = shopAdmin.getShop().getId();

        if (active) {
            promoCodes = promoCodeRepository.getActiveCodes(shopId, nowDate, PageRequest.of(page.orElse(DEFAULT_PAGE), FETCH_RECORD_COUNT));
        } else {
            promoCodes = promoCodeRepository.findAllByShopId(shopId, PageRequest.of(page.orElse(DEFAULT_PAGE), FETCH_RECORD_COUNT));
        }

        var responses = promoCodes
                .stream()
                .map(CreatedPromoCodeResponse::new)
                .collect(Collectors.toList());

        var response = new PromoCodeListResponse(promoCodes.getTotalElements(), responses);

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
        var shopAdmin = userRepository.getOne(shopAdminId);
        var promoCode = promoCodeRepository.findByIdAndShop(promoCodeId, shopAdmin.getShop())
                .orElseThrow(() -> new BadRequestException(PROMOCODE_NOT_FOUND));
        var response = new CreatedPromoCodeResponse(promoCode);

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
        var customer = userRepository.getOne(customerId);
        var currentPromoCode = promoCodeRepository.findByPromoCode(promoCode)
                .orElseThrow(() -> new BadRequestException(PROMOCODE_NOT_FOUND));
        var nowDate = new Date();

        if (currentPromoCode.getValidFrom().after(nowDate) || currentPromoCode.getValidTo().before(nowDate)) {
            throw new BadRequestException(PROMOCODE_EXPIRED);
        } else {
            if (userPromoCodeRepository.existsByCustomerAndPromoCode(customer, currentPromoCode)) {
                throw new BadRequestException(PROMOCODE_ALREADY_USED);
            } else {
                var newUserPromoCode = new UserPromoCode();
                newUserPromoCode.setCustomer(customer);
                newUserPromoCode.setPromoCode(currentPromoCode);
                newUserPromoCode.setDateCreate(nowDate);
                userPromoCodeRepository.save(newUserPromoCode);

                var currentUserBonusAccount = customer.getBonusAccount();
                var promoCodePrice = currentPromoCode.getPrice();
                currentUserBonusAccount.setBonusAmount(currentUserBonusAccount.getBonusAmount().add(promoCodePrice));
                userBonusAccountRepository.save(currentUserBonusAccount);

                var response = new UserPromoCodeResponse(promoCodePrice, currentUserBonusAccount.getBonusAmount());

                return ResponseEntity.ok(response);
            }
        }
    }
}
