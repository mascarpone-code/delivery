package com.mascarpone.delivery.service.orderaccessory;

import com.mascarpone.delivery.entity.orderaccessory.OrderAccessory;
import com.mascarpone.delivery.repository.orderaccessory.OrderAccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderAccessoryServiceImpl implements OrderAccessoryService {
    private final OrderAccessoryRepository repository;

    @Override
    public List<OrderAccessory> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(OrderAccessory object) {
        repository.save(object);
    }
}
