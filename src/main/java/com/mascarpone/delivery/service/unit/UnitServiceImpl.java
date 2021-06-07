package com.mascarpone.delivery.service.unit;

import com.mascarpone.delivery.entity.unit.Unit;
import com.mascarpone.delivery.repository.unit.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UnitServiceImpl implements UnitService {
    private final UnitRepository repository;

    @Override
    public List<Unit> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return findById(id);
    }

    @Override
    public void save(Unit object) {
        repository.save(object);
    }
}
