package com.training.periodical.model.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Service<T> extends Serializable {
    int create(T entity) throws ServiceException;
    Optional<T> findById(long id) throws ServiceException;
    List<T> findAll() throws ServiceException;
    int update(T entity) throws ServiceException;
    int delete(long id) throws ServiceException;
}
