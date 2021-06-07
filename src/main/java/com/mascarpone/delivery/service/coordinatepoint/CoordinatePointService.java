package com.mascarpone.delivery.service.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;

public interface CoordinatePointService extends GeneralService<CoordinatePoint> {
    List<CoordinatePoint> findAllByDeliveryAreaId(Long areaId);

    void deleteList(List<CoordinatePoint> object);
}

