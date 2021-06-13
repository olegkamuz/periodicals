package com.training.periodical.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping ResultSet data to various entities
 * @see ResultSet
 *
 */
public interface Mapper<T> {

    T build(ResultSet rs) throws SQLException;
}
