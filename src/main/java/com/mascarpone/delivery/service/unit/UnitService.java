package com.mascarpone.delivery.service.unit;

import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.service.GeneralService;

import java.util.Optional;

public interface UnitService extends GeneralService<Unit> {
    Optional<Unit> findById(long unitId);
}
