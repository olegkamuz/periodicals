package com.training.periodical.model.service;

import java.util.Optional;

public interface Service<T> {
    Optional<T> findById(long id) throws ServiceException;
}
