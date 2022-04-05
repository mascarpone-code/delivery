package com.mascarpone.delivery.service.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface PromoCodeService extends GeneralService<PromoCode> {
    Page<PromoCode> findAllByShopId(Long shopId, int page, int size);

    Optional<PromoCode> findByIdAndShopId(Long id, Long shopId);

    Page<PromoCode> getActiveCodes(Long shopId, Date date, int page, int size);

    Optional<PromoCode> findByPromoCode(String promoCode);

    List<PromoCode> findAllByShop(Long shopId, String code);

    ResponseEntity<?> createPromoCode(PromoCode promoCode, UUID shopAdminUuid);

    ResponseEntity<?> getPromoCodesByShopAdmin(Optional<Integer> page, boolean active, UUID shopAdminUuid);

    ResponseEntity<?> getPromoCode(Long promoCodeId, UUID shopAdminUuid);

    ResponseEntity<?> applyPromoCodeByCustomer(String promoCode, UUID customerUuid);
}
