package com.mascarpone.delivery.service;

import java.util.List;
import java.util.Optional;

public interface GeneralService<T> {
    List<T> getAll();

    Optional<T> findById(Long id);

    void save(T object);
}
