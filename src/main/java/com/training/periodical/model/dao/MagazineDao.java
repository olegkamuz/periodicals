package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.mapper.MagazineMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for magazine related entities.
 */
public class MagazineDao extends AbstractDao<Magazine> {
    private static final long serialVersionUID = -1560516295484382753L;
    private final MagazineMapper mapper;
    private final MagazineBuilder builder;

    public MagazineDao(Connection connection, MagazineMapper magazineMapper, MagazineBuilder builder) {
        this.builder = builder;
        this.connection = connection;
        this.mapper = magazineMapper;
        tableName = "magazine";
    }

    public List<Magazine> findAll() throws DaoException {
        return findAll(mapper);
    }

    public int getCount() throws DaoException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(MagazineQuery.SQL__COUNT_ALL)) {
            Object[] parameters = {};
            prepareStatement(preparedStatement, parameters);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            close(rs);
        }
        return 0;
    }

    public int getCountFiltered(String filterName) throws DaoException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(MagazineQuery.SQL__COUNT_FILTERED)) {
            Object[] parameters = {filterName};
            prepareStatement(preparedStatement, parameters);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            close(rs);
        }
        return 0;
    }

    public BigDecimal findSumPriceByIds(List<String> ids) throws DaoException {
        ResultSet rs = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MagazineQuery.
                    getQueryFindSumPriceByIds(ids.size()));
            prepareStatement(preparedStatement, ids.toArray());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("total");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } finally {
            close(rs);
        }
    }


    @Override
    public int create(Magazine magazine) throws DaoException {
        Object[] parameters = builder.unBuildStrippedMagazine(magazine);
        return executeUpdate(MagazineQuery.SQL__CREATE_MAGAZINE, parameters);
    }

    public int update(Magazine magazine) throws DaoException {
        String query = MagazineQuery.SQL__UPDATE_MAGAZINE;
        Object[] parameters = builder.unBuild(magazine);
        return executeUpdate(query, parameters);
    }

    @Override
    public int delete(long id) throws DaoException {
        String query = MagazineQuery.SQL__DELETE_MAGAZINE;
        Object[] parameters = {id};
        return executeUpdate(query, parameters);
    }

    /**
     * Returns magazine by category name.
     *
     * @param themeName String to find by
     * @return List of theme entities.
     */
    public List<Magazine> findMagazineByThemeName(String themeName) throws DaoException {
        Object[] parameters = {themeName};
        return find(MagazineQuery.
                SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME, parameters);
    }

    private List<Magazine> find(String query, Object[] parameters) {
        return executeQuery(query, mapper, parameters);
    }

    public List<Magazine> findSorted(String sortSubQuery) throws DaoException {
        Object[] parameters = {};
        return find(MagazineQuery.SQL_FIND_SORT + sortSubQuery, parameters);
    }

    public List<Magazine> findSortedPaginated(String sortSubQuery, int limit, int offset) throws DaoException {
        Object[] parameters = {limit, offset};
        return find(MagazineQuery.SQL_FIND_SORT +
                sortSubQuery +
                MagazineQuery.SQL__FIND_SUB_PAGINATED, parameters);
    }

    public List<Magazine> findFiltered(String filterName) throws DaoException {
        Object[] parameters = {filterName};
        return find(MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME, parameters);
    }

    public List<Magazine> findFilteredPaginated(String filterName, int limit, int offset) throws DaoException {
        Object[] parameters = {filterName, limit, offset};
        return find(MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME +
                MagazineQuery.SQL__FIND_SUB_PAGINATED, parameters);
    }

    public List<Magazine> findFilteredSortedPaginated(String filterName, String sortSubQuery, int limit, int offset) throws DaoException {
        Object[] parameters = {filterName, limit, offset};
        return find(MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME +
                sortSubQuery +
                MagazineQuery.SQL__FIND_SUB_PAGINATED, parameters);
    }

    public List<Magazine> findSearchedFilteredSortedPaginated(String search, String filterName, String sortSubQuery, int limit, int offset) throws DaoException {
        Object[] parameters = {filterName, search, limit, offset};
        return find(MagazineQuery.SQL__FIND_ALL_SEARCHED_MAGAZINES_BY_THEME_NAME +
                sortSubQuery +
                MagazineQuery.SQL__FIND_SUB_PAGINATED, parameters);
    }


    public List<Magazine> findPage(int limit, int offset) throws DaoException {
        return findAll(mapper, limit, offset);
    }

    @Override
    public Optional<Magazine> findById(long id) throws DaoException {
        return executeSingleResponseQuery(MagazineQuery.SQL__FIND_MAGAZINE_BY_ID, mapper, id);
    }
}
