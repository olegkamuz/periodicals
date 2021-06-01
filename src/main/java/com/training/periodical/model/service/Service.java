package com.training.periodical.model.service;

import java.util.Optional;

public interface Service<T> {
    Optional<T> findById(String id) throws ServiceException;
}
