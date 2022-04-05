package com.mascarpone.delivery.service.startpopup;

import com.mascarpone.delivery.entity.startpopup.StartPopUp;
import com.mascarpone.delivery.repository.startpopup.StartPopUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartPopUpServiceImpl implements StartPopUpService {
    private final StartPopUpRepository repository;

    @Override
    public List<StartPopUp> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(StartPopUp object) {
        repository.save(object);
    }

    @Override
    public Optional<StartPopUp> findByShopId(Long shopId) {
        return repository.findByShopId(shopId);
    }
}

