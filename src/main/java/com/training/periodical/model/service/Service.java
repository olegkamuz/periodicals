package com.training.periodical.model.service;

import java.util.Optional;

public interface Service<T> {
    Optional<T> getById(long id);
}
