package com.mascarpone.delivery.repository.startpopup;

import com.mascarpone.delivery.entity.startpopup.StartPopUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartPopUpRepository extends JpaRepository<StartPopUp, Long> {
    Optional<StartPopUp> findByShopId(Long shopId);
}
