package com.mascarpone.delivery.service.nomenclatureunit;

import com.mascarpone.delivery.entity.nomenclatureunit.NomenclatureUnit;
import com.mascarpone.delivery.repository.nomenclatureunit.NomenclatureUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NomenclatureUnitServiceImpl implements NomenclatureUnitService {
    private final NomenclatureUnitRepository repository;

    @Override
    public Optional<NomenclatureUnit> getByName(String s) {
        return repository.findByName(s);
    }

    @Override
    public NomenclatureUnit getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<NomenclatureUnit> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(NomenclatureUnit object) {
        repository.save(object);
    }
}
