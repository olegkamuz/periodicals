package com.training.periodical.model.dao;

import com.training.periodical.entity.Theme;
import com.training.periodical.model.mapper.ThemeMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ThemeDao extends AbstractDao<Theme> {
    private static final long serialVersionUID = -4329084276299458939L;
    private final ThemeMapper mapper;

    public ThemeDao(Connection connection, ThemeMapper themeMapper) {
        this.connection = connection;
        this.mapper = themeMapper;
        tableName = "theme";
    }

    public int create(Theme theme) {
        return 0;
    }

    public int update(Theme theme) throws DaoException {
        return 0;
    }

    public int delete(long id) {
        return 0;
    }

    public List<Theme> findAll() throws DaoException {
        return findAll(mapper);
    }

    @Override
    public Optional<Theme> findById(long id) throws DaoException {
        return Optional.empty();
    }

}
