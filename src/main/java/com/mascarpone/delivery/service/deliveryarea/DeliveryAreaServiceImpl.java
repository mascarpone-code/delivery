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
import java.util.Optional;

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
    public List<DeliveryArea> findAllByShopId(Long shopId) {
        return deliveryAreaRepository.findAllByShopId(shopId);
    }

    /**
     * Shop creates or updates delivery area.
     *
     * @param deliveryArea - delivery area entity
     * @param shopAdminId  - shop admin id
     * @return delivery area entity
     */
    @Override
    public ResponseEntity<?> createDeliveryArea(DeliveryArea deliveryArea, Long shopAdminId) {
        var coordinatePoints = deliveryArea.getCoordinatePoints();

        if (deliveryArea.getId() != null) {
            var requestedDeliveryArea = deliveryAreaRepository.getOne(deliveryArea.getId());

            if (coordinatePoints == null) {
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
            coordinatePoints.forEach(coordinatePointRepository::save);
            var shopAdmin = userRepository.getOne(shopAdminId);
            var shop = shopAdmin.getShop();
            deliveryArea.setShop(shop);
            deliveryAreaRepository.save(deliveryArea);

            coordinatePoints.forEach(coordinatePoint -> {
                coordinatePoint.setDeliveryArea(deliveryArea);
                coordinatePointRepository.save(coordinatePoint);
            });
        }

        return ResponseEntity.ok(deliveryArea);
    }

    /**
     * Getting a list of shop's delivery areas.
     *
     * @param shopPrefix - shop prefix
     * @return list of shop's delivery areas
     */
    @Override
    public ResponseEntity<?> getDeliveryAreas(String shopPrefix) {
        var shop = shopRepository.findByPrefix(shopPrefix)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));
        var deliveryAreas = deliveryAreaRepository.findAllByShop(shop);

        return ResponseEntity.ok(deliveryAreas);
    }

    /**
     * Getting a delivery area
     *
     * @param id - delivery area id
     * @return delivery area entity
     */
    @Override
    public ResponseEntity<?> getDeliveryArea(Long id) {
        var deliveryArea = deliveryAreaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(DELIVERY_AREA_NOT_FOUND));

        return ResponseEntity.ok(deliveryArea);
    }

    /**
     * Deleting a delivery area
     *
     * @param areaId      - delivery area id
     * @param shopAdminId - shop admin id
     * @return
     */
    @Override
    public GeneralAnswer<String> deleteDeliveryArea(Long areaId, Long shopAdminId) {
        var deliveryArea = deliveryAreaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(DELIVERY_AREA_NOT_FOUND));
        deliveryAreaRepository.delete(deliveryArea);

        return new GeneralAnswer<>("OK", null, null);
    }

    /**
     * Checking whether the address belongs to the delivery area.
     *
     * @param shopPrefix - shop prefix
     * @param y          - point latitude
     * @param x          - point longitude
     * @return delivery area dto
     */
    @Override
    public ResponseEntity<?> checkPoint(String shopPrefix, Double y, Double x) {
        var shop = shopRepository.findByPrefix(shopPrefix)
                .orElseThrow(() -> new BadRequestException(SHOP_NOT_FOUND));
        var pointInArea = false;
        var areas = deliveryAreaRepository.findAllByShop(shop);
        DeliveryArea requestedArea = null;

        for (var area : areas) {
            var coordinatePoints = area.getCoordinatePoints();

            int i;
            int j;
            var result = false;

            for (i = 0, j = coordinatePoints.size() - 1; i < coordinatePoints.size(); j = i++) {
                if ((coordinatePoints.get(i).getLatitude() > y) != (coordinatePoints.get(j).getLatitude() > y) &&
                        (x < (coordinatePoints.get(j).getLongitude() - coordinatePoints.get(i).getLongitude()) *
                                (y - coordinatePoints.get(i).getLatitude()) / (coordinatePoints.get(j).getLatitude()
                                - coordinatePoints.get(i).getLatitude()) + coordinatePoints.get(i).getLongitude())) {
                    result = !result;
                }
            }

            if (result) {
                pointInArea = true;
                requestedArea = area;
                break;
            }
        }

        if (pointInArea) {
            var response = new DeliveryAreaIdNameMinAmountResponse(requestedArea);

            return ResponseEntity.ok(response);
        } else {
            throw new BadRequestException(NO_DELIVERY_AREA);
        }
    }

    @Override
    public Optional<DeliveryArea> findById(Long id) {
        return deliveryAreaRepository.findById(id);
    }
}
