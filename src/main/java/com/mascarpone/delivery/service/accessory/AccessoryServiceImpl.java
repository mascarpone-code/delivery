package com.mascarpone.delivery.service.accessory;

import com.mascarpone.delivery.entity.accessory.Accessory;
import com.mascarpone.delivery.repository.accessory.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessoryServiceImpl implements AccessoryService {
    private final AccessoryRepository repository;

    @Override
    public List<Accessory> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Accessory> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Accessory object) {
        repository.save(object);
    }
}
