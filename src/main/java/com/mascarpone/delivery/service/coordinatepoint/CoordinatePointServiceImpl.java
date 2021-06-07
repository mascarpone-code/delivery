package com.mascarpone.delivery.service.coordinatepoint;

import com.mascarpone.delivery.entity.coordinatepoint.CoordinatePoint;
import com.mascarpone.delivery.repository.coordinatepoint.CoordinatePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoordinatePointServiceImpl implements CoordinatePointService {
    private final CoordinatePointRepository repository;

    @Override
    public List<CoordinatePoint> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CoordinatePoint> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(CoordinatePoint object) {
        repository.save(object);
    }

    @Override
    public void deleteList(List<CoordinatePoint> object) {
        repository.deleteAll(object);
    }

    @Override
    public List<CoordinatePoint> findAllByDeliveryAreaId(Long areaId) {
        return repository.findAllByDeliveryAreaId(areaId);
    }
}
