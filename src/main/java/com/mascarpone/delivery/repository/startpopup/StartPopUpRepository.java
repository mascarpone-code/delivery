package com.mascarpone.delivery.repository.startpopup;

import com.mascarpone.delivery.entity.startpopup.StartPopUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StartPopUpRepository extends JpaRepository<StartPopUp, Long> {
    Optional<StartPopUp> findByShopId(Long shopId);
}
