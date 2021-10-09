package com.mascarpone.delivery.service.ordermodifier;

import com.mascarpone.delivery.entity.ordermodifier.OrderModifier;
import com.mascarpone.delivery.repository.ordermodifier.OrderModifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderModifierServiceImpl implements OrderModifierService {
    private final OrderModifierRepository repository;

    @Override
    public List<OrderModifier> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<OrderModifier> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(OrderModifier object) {
        repository.save(object);
    }

}
