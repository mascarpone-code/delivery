package com.mascarpone.delivery.repository.promocode;

import com.mascarpone.delivery.entity.promocode.PromoCode;
import com.mascarpone.delivery.entity.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    Page<PromoCode> findAllByShopId(Long shopId, Pageable pageable);

    Optional<PromoCode> findByIdAndShopId(Long id, Long shopId);

    Optional<PromoCode> findByIdAndShop(Long id, Shop shop);

    @Query("select new com.mascarpone.delivery.entity.promocode.PromoCode(p.id, p.promoCode, p.price, p.dateCreate, p.validFrom, p.validTo) " +
            "from PromoCode p where p.shop.id = :shopId and p.validTo >= :date")
    Page<PromoCode> getActiveCodes(@Param("shopId") Long shopId,
                                   @Param("date") Date date,
                                   Pageable pageable);

    Optional<PromoCode> findByPromoCode(String promoCode);

    @Query("select new com.mascarpone.delivery.entity.promocode.PromoCode(p.id, p.promoCode, p.price, p.dateCreate, p.validFrom, p.validTo) " +
            "from PromoCode p where p.shop.id = :shopId and p.promoCode <> :code")
    List<PromoCode> findAllByShop(@Param("shopId") Long shopId,
                                  @Param("code") String code);
}
