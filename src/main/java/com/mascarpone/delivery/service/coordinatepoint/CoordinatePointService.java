package com.mascarpone.delivery.service.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import com.mascarpone.delivery.service.GeneralService;

import java.util.List;

public interface CoordinatePointService extends GeneralService<CoordinatePoint> {
    /**
     * Get coordinate points of the delivery area
     *
     * @param areaId - delivery area id
     * @return list of coordinate points
     */
    List<CoordinatePoint> findAllByDeliveryAreaId(Long areaId);

    /**
     * Delete the list of coordinate points
     *
     * @param object - the list of coordinate points
     */
    void deleteList(List<CoordinatePoint> object);
}

