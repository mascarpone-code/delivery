package com.mascarpone.delivery.service.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.shop.ShopForAdminResponse;
import com.mascarpone.delivery.payload.shop.UpdateShopInfoRequest;
import com.mascarpone.delivery.repository.shop.ShopRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mascarpone.delivery.exception.ExceptionConstants.ORDER_TYPE_ERROR;
import static com.mascarpone.delivery.exception.ExceptionConstants.SHOP_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    @Override
    public void save(Shop object) {
        shopRepository.save(object);
    }

    @Override
    public Optional<Shop> findByPrefix(String prefix) {
        return shopRepository.findByPrefix(prefix);
    }

    /**
     * Updating a shop info
     *
     * @param request     - shop dto
     * @param shopAdminUuid - shop admin uuid
     * @return shop entity
     */
    @Override
    public ResponseEntity<?> updateShopInfo(UpdateShopInfoRequest request, UUID shopAdminUuid) {
        var shopAdmin = userRepository.getOne(shopAdminUuid);
        var currentShop = shopRepository.findByPrefix(shopAdmin.getShopPrefix())
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));

        if (!currentShop.isDelivery()
                && !currentShop.isPickup()
                && request.getDelivery().isEmpty()
                && request.getPickup().isEmpty()) {
            throw new BadRequestException(ORDER_TYPE_ERROR);
        }

        request.getDelivery().ifPresent(currentShop::setDelivery);
        request.getPickup().ifPresent(currentShop::setPickup);
        request.getBonusSystem().ifPresent(currentShop::setBonusSystem);
        request.getShopOpen().ifPresent(currentShop::setShopOpen);
        request.getTerminalPayment().ifPresent(currentShop::setTerminalPayment);
        request.getCashPayment().ifPresent(currentShop::setCashPayment);
        request.getWelcomeBonusAmount().ifPresent(currentShop::setWelcomeBonusAmount);
        request.getOrderAmountPercent().ifPresent(currentShop::setOrderAmountPercent);
        request.getBonusPayAmount().ifPresent(currentShop::setBonusPayAmount);
        request.getServiceTerms().ifPresent(currentShop::setServiceTerms);
        request.getDeliveryTerms().ifPresent(currentShop::setDeliveryTerms);
        request.getTimeZone().ifPresent(currentShop::setTimeZone);
        request.getOrderBeginTime().ifPresent(currentShop::setOrderBeginTime);
        request.getOrderEndTime().ifPresent(currentShop::setOrderEndTime);
        request.getPhoneNumber().ifPresent(currentShop::setPhoneNumber);
        request.getFlampLink().ifPresent(currentShop::setFlampLink);
        request.getOrderPreparationTime().ifPresent(currentShop::setOrderPreparationTime);

        shopRepository.save(currentShop);

        return ResponseEntity.ok(new ShopForAdminResponse(currentShop));
    }

    @Override
    public Optional<Shop> findById(long shopId) {
        return shopRepository.findById(shopId);
    }
}
