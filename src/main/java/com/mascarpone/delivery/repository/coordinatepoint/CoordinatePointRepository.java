package com.mascarpone.delivery.repository.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordinatePointRepository extends JpaRepository<CoordinatePoint, Long> {
    List<CoordinatePoint> findAllByDeliveryAreaId(Long areaId);
}
