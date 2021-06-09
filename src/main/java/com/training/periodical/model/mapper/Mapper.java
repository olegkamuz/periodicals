package com.training.periodical.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Builder build set entity
 *
 */
public interface Mapper<T> {

    T build(ResultSet rs) throws SQLException;
}
