package com.mascarpone.delivery.service;

import java.util.List;

public interface GeneralService<T> {
    List<T> getAll();

    void save(T object);
}
