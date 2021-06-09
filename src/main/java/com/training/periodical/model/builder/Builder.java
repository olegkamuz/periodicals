package com.training.periodical.model.builder;

import java.sql.SQLException;

/**
 *
 * Builder build set entity
 *
 */
public interface Builder<T> {

    T build() throws SQLException;
}
