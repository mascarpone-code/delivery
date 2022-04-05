package com.mascarpone.delivery.service.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.repository.ordermodifier.OrderModifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderModifierServiceImpl implements OrderModifierService {
    private final OrderModifierRepository repository;

    @Override
    public List<OrderModifier> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(OrderModifier object) {
        repository.save(object);
    }

}
