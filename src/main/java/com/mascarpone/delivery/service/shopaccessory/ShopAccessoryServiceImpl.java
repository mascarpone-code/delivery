package com.mascarpone.delivery.service.shopaccessory;

import com.mascarpone.delivery.entity.shopaccessory.ShopAccessory;
import com.mascarpone.delivery.repository.shopaccessory.ShopAccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopAccessoryServiceImpl implements ShopAccessoryService {
    private final ShopAccessoryRepository repository;

    @Override
    public List<ShopAccessory> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ShopAccessory> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(ShopAccessory object) {
        repository.save(object);
    }

    @Override
    public Optional<ShopAccessory> findByAccessoryIdAndShopId(Long accessoryId, Long shopId) {
        return repository.findByAccessoryIdAndShopId(accessoryId, shopId);
    }

    @Override
    public List<ShopAccessory> findAllByShopId(Long shopId) {
        return repository.findAllByShopId(shopId);
    }
}
