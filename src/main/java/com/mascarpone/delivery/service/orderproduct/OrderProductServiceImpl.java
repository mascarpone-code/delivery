package com.mascarpone.delivery.service.orderproduct;

import com.mascarpone.delivery.entity.orderproduct.OrderProduct;
import com.mascarpone.delivery.repository.orderproduct.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository repository;

    @Override
    public List<OrderProduct> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(OrderProduct object) {
        repository.save(object);
    }
}
