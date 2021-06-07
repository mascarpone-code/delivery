package com.mascarpone.delivery.controller.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.security.UserPrincipal;
import com.mascarpone.delivery.service.deliveryarea.DeliveryAreaService;
import com.mascarpone.delivery.utils.UserAuthChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryAreaController {
    private final DeliveryAreaService deliveryAreaService;

    /**
     * Shop creates or updates delivery area.
     *
     * @param userPrincipal - authenticated shop admin
     * @param deliveryArea  - delivery area entity
     * @return delivery area entity
     */
    @Transactional
    @PostMapping("/api/shop/area")
    public ResponseEntity<?> createDeliveryArea(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestBody DeliveryArea deliveryArea) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAreaService.createDeliveryArea(deliveryArea, userPrincipal.getId());
    }

    /**
     * Getting a list of shop's delivery areas.
     *
     * @param shopPrefix - shop prefix
     * @return list of shop's delivery areas
     */
    @GetMapping("/api/area/{shopPrefix}")
    public ResponseEntity<?> getDeliveryAreas(@PathVariable("shopPrefix") String shopPrefix) {
        return deliveryAreaService.getDeliveryAreas(shopPrefix);
    }

    /**
     * Getting a delivery area
     *
     * @param id - delivery area id
     * @return delivery area entity
     */
    @GetMapping("/api/area/{id}")
    public ResponseEntity<?> getDeliveryArea(@PathVariable("id") Long id) {
        return deliveryAreaService.getDeliveryArea(id);
    }

    /**
     * Deleting a delivery area
     *
     * @param userPrincipal - authenticated shop admin
     * @param id            - delivery area id
     * @return
     */
    @DeleteMapping("/api/shop/area/{id}")
    public GeneralAnswer deleteDeliveryArea(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @PathVariable("id") Long id) {
        UserAuthChecker.checkAuth(userPrincipal);
        return deliveryAreaService.deleteDeliveryArea(id, userPrincipal.getId());
    }

    /**
     * Checking whether the address belongs to the delivery area.
     *
     * @param shopPrefix - shop prefix
     * @param y          - point latitude
     * @param x          - point longitude
     * @return delivery area dto
     */
    @GetMapping("/api/area/point")
    public ResponseEntity<?> checkPoint(@RequestParam("shopPrefix") String shopPrefix,
                                        @RequestParam("latitude") Double y,
                                        @RequestParam("longitude") Double x) {
        return deliveryAreaService.checkPoint(shopPrefix, y, x);
    }
}
