package com.mascarpone.delivery.service.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface DeliveryAreaService extends GeneralService<DeliveryArea> {
    /**
     * Shop creates or updates delivery area.
     *
     * @param deliveryArea - delivery area entity
     * @param shopAdminUuid  - shop admin uuid
     * @return delivery area entity
     */
    ResponseEntity<?> createDeliveryArea(DeliveryArea deliveryArea, UUID shopAdminUuid);

    /**
     * Getting a list of shop's delivery areas.
     *
     * @param shopPrefix - shop prefix
     * @return list of shop's delivery areas
     */
    ResponseEntity<?> getDeliveryAreas(String shopPrefix);

    /**
     * Getting a delivery area
     *
     * @param id - delivery area id
     * @return delivery area entity
     */
    ResponseEntity<?> getDeliveryArea(Long id);

    /**
     * Deleting a delivery area
     *
     * @param areaId      - delivery area id
     * @param shopAdminUuid - shop admin uuid
     * @return
     */
    GeneralAnswer<String> deleteDeliveryArea(Long areaId, UUID shopAdminUuid);

    /**
     * Checking whether the address belongs to the delivery area.
     *
     * @param shopPrefix - shop prefix
     * @param y          - point latitude
     * @param x          - point longitude
     * @return delivery area dto
     */
    ResponseEntity<?> checkPoint(String shopPrefix, Double y, Double x);
}
