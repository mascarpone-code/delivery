package com.mascarpone.delivery.repository.accessory;

import com.mascarpone.delivery.entity.accessory.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}
