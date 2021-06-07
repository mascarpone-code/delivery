package com.mascarpone.delivery.service.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.service.GeneralService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeliveryAreaService extends GeneralService<DeliveryArea> {
    List<DeliveryArea> findAllByShopId(Long shopId);

    ResponseEntity<?> createDeliveryArea(DeliveryArea deliveryArea, Long shopAdminId);

    ResponseEntity<?> getDeliveryAreas(String shopPrefix);

    ResponseEntity<?> getDeliveryArea(Long id);

    GeneralAnswer deleteDeliveryArea(Long areaId, Long shopAdminId);

    ResponseEntity<?> checkPoint(String shopPrefix, Double y, Double x);
}
