package com.mascarpone.delivery.service.startpopup;

import com.mascarpone.delivery.entity.startpopup.StartPopUp;
import com.mascarpone.delivery.service.GeneralService;

import java.util.Optional;

public interface StartPopUpService extends GeneralService<StartPopUp> {
    Optional<StartPopUp> findByShopId(Long shopId);
}
