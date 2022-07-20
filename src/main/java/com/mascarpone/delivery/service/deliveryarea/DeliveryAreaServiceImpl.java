package com.mascarpone.delivery.service.deliveryarea;

import com.mascarpone.delivery.entity.deliveryarea.DeliveryArea;
import com.mascarpone.delivery.exception.BadRequestException;
import com.mascarpone.delivery.payload.GeneralAnswer;
import com.mascarpone.delivery.payload.deliveryarea.DeliveryAreaIdNameMinAmountResponse;
import com.mascarpone.delivery.repository.coordinatepoint.CoordinatePointRepository;
import com.mascarpone.delivery.repository.deliveryarea.DeliveryAreaRepository;
import com.mascarpone.delivery.repository.shop.ShopRepository;
import com.mascarpone.delivery.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mascarpone.delivery.exception.ExceptionConstants.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryAreaServiceImpl implements DeliveryAreaService {
    private final DeliveryAreaRepository deliveryAreaRepository;
    private final CoordinatePointRepository coordinatePointRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Override
    public List<DeliveryArea> getAll() {
        return deliveryAreaRepository.findAll();
    }

    @Override
    public void save(DeliveryArea object) {
        deliveryAreaRepository.save(object);
    }

    @Override
    public ResponseEntity<?> createDeliveryArea(DeliveryArea deliveryArea, UUID shopAdminUuid) {
        var coordinatePointList = deliveryArea.getCoordinatePoints();

        if (deliveryArea.getId() != null) {
            var requestedDeliveryArea = deliveryAreaRepository.getOne(deliveryArea.getId());

            if (coordinatePointList == null) {
                deliveryArea.setCoordinatePoints(requestedDeliveryArea.getCoordinatePoints());
            } else {
                coordinatePointRepository.deleteAll(requestedDeliveryArea.getCoordinatePoints());
                var deliveryAreaCoordinatePoints = deliveryArea.getCoordinatePoints();
                deliveryAreaCoordinatePoints.forEach(coordinatePoint -> {
                    coordinatePoint.setDeliveryArea(deliveryArea);
                    coordinatePointRepository.save(coordinatePoint);
                });
            }

            if (deliveryArea.getName() == null) {
                deliveryArea.setName(requestedDeliveryArea.getName());
            }

            if (deliveryArea.getMinimumOrderAmount() == null) {
                deliveryArea.setMinimumOrderAmount(requestedDeliveryArea.getMinimumOrderAmount());
            }

            if (deliveryArea.getPolygonColor() == null) {
                deliveryArea.setPolygonColor(requestedDeliveryArea.getPolygonColor());
            }

            deliveryArea.setShop(requestedDeliveryArea.getShop());
            deliveryAreaRepository.save(deliveryArea);
        } else {
            coordinatePointRepository.saveAll(coordinatePointList);
            var shopAdmin = userRepository.getOne(shopAdminUuid);
            var shop = shopAdmin.getShop();
            deliveryArea.setShop(shop);
            deliveryAreaRepository.save(deliveryArea);

            coordinatePointList.forEach(coordinatePoint -> {
                coordinatePoint.setDeliveryArea(deliveryArea);
                coordinatePointRepository.save(coordinatePoint);
            });
        }

        return ResponseEntity.ok(deliveryArea);
    }

    @Override
    public ResponseEntity<?> getDeliveryAreas(String shopPrefix) {
        var currentShop = shopRepository.findByPrefix(shopPrefix)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));
        var deliveryAreaList = deliveryAreaRepository.findAllByShop(currentShop);

        return ResponseEntity.ok(deliveryAreaList);
    }

    @Override
    public ResponseEntity<?> getDeliveryArea(Long id) {
        var deliveryArea = deliveryAreaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(DELIVERY_AREA_NOT_FOUND));

        return ResponseEntity.ok(deliveryArea);
    }

    @Override
    public GeneralAnswer<String> deleteDeliveryArea(Long areaId, UUID shopAdminUuid) {
        var deliveryArea = deliveryAreaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(DELIVERY_AREA_NOT_FOUND));
        deliveryAreaRepository.delete(deliveryArea);

        return new GeneralAnswer<>("OK", null, null);
    }

    @Override
    public ResponseEntity<?> checkPoint(String shopPrefix, Double y, Double x) {
        var shop = shopRepository.findByPrefix(shopPrefix)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));
        var pointInArea = false;
        var deliveryAreaList = deliveryAreaRepository.findAllByShop(shop);
        DeliveryArea requestedArea = null;

        for (var deliveryArea : deliveryAreaList) {
            var coordinatePointList = deliveryArea.getCoordinatePoints();

            int i;
            int j;
            var result = false;

            for (i = 0, j = coordinatePointList.size() - 1; i < coordinatePointList.size(); j = i++) {
                if ((coordinatePointList.get(i).getLatitude() > y) != (coordinatePointList.get(j).getLatitude() > y) &&
                        (x < (coordinatePointList.get(j).getLongitude() - coordinatePointList.get(i).getLongitude()) *
                                (y - coordinatePointList.get(i).getLatitude()) / (coordinatePointList.get(j).getLatitude()
                                - coordinatePointList.get(i).getLatitude()) + coordinatePointList.get(i).getLongitude())) {
                    result = !result;
                }
            }

            if (result) {
                pointInArea = true;
                requestedArea = deliveryArea;
                break;
            }
        }

        if (!pointInArea) {
            throw new BadRequestException(NO_DELIVERY_AREA);
        }

        var response = new DeliveryAreaIdNameMinAmountResponse(requestedArea);

        return ResponseEntity.ok(response);
    }
}
