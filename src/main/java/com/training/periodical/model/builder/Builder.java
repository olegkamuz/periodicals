package com.training.periodical.model.builder;

import java.sql.SQLException;

/**
 * Build entity with set fields
 *
 */
public interface Builder<T> {

    T build() throws SQLException;
}
