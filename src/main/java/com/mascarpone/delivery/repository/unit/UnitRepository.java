package com.mascarpone.delivery.repository.unit;

import com.mascarpone.delivery.entity.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
