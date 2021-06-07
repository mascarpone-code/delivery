package com.mascarpone.delivery.utils.shop;

import com.mascarpone.delivery.entity.shop.Shop;
import com.mascarpone.delivery.entity.user.User;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.service.shop.ShopService;
import com.mascarpone.delivery.service.unit.UnitService;
import com.mascarpone.delivery.service.user.UserService;
import org.springframework.stereotype.Component;

import static com.mascarpone.delivery.exception.ExceptionConstants.SHOP_NOT_FOUND;
import static com.mascarpone.delivery.exception.ExceptionConstants.UNIT_NOT_FOUND;

@Component
public class ShopUtils {
    private static ShopService shopService;
    private static UnitService unitService;
    private static UserService userService;

    public ShopUtils(ShopService shopService,
                     UnitService unitService,
                     UserService userService) {
        ShopUtils.shopService = shopService;
        ShopUtils.unitService = unitService;
        ShopUtils.userService = userService;
    }

    public static void checkShop(long shopId) {
        if (shopService.findById(shopId).isEmpty()) {
            throw new BadRequestException(SHOP_NOT_FOUND);
        }
    }

    public static void checkUnit(long unitId) {
        if (unitService.findById(unitId).isEmpty()) {
            throw new BadRequestException(UNIT_NOT_FOUND);
        }
    }

    public static Shop getShop(long shopAdminId) {
        User shopAdmin = userService.getOne(shopAdminId);
        return shopAdmin.getShop();
    }
}
