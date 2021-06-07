package com.mascarpone.delivery.repository.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatePointRepository extends JpaRepository<CoordinatePoint, Long> {
    List<CoordinatePoint> findAllByDeliveryAreaId(Long areaId);
}
