package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.service.ServiceException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for magazine related entities.
 */
public class MagazineDao extends AbstractDao<Magazine> {
    private static final long serialVersionUID = -1560516295484382753L;
    private final Connection connection;
    private final MagazineBuilder builder;

    public MagazineDao(Connection connection, MagazineBuilder magazineBuilder) {
        this.connection = connection;
        this.builder = magazineBuilder;
        tableName = "magazine";
    }

    public List<Magazine> findAll() throws DaoException {
        return findAll(connection, builder);
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

    protected static void close(ResultSet rs) throws DaoException {
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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
            rollback(connection);
            throw new DaoException(ex);
        } finally {
            commit(connection);
            close(rs);
        }
    }


    @Override
    public int create(Magazine magazine) throws DaoException {
        Object[] parameters = builder.unBuildStrippedMagazine(magazine);
        return executeUpdateNow(connection, MagazineQuery.SQL__CREATE_MAGAZINE, parameters);
    }

    public int update(Magazine magazine) throws DaoException {
        String query = MagazineQuery.SQL__UPDATE_MAGAZINE;
        Object[] parameters = builder.unBuild(magazine);
        return executeUpdate(connection, query, parameters);
    }

    public int updateNow(Magazine magazine) throws DaoException {
        String query = MagazineQuery.SQL__UPDATE_MAGAZINE;
        Object[] parameters = builder.unBuild(magazine);
        return executeUpdateNow(connection, query, parameters);
    }

    @Override
    public int delete(long id) {
        return 0;
    }


    public int deleteNow(long id) throws DaoException {
        String query = MagazineQuery.SQL__DELETE_MAGAZINE;
        Object[] parameters = {id};
        return executeUpdateNow(connection, query, parameters);
    }

    /**
     * Returns magazine by category name.
     *
     * @param themeName String to find by
     * @return List of theme entities.
     */
    public List<Magazine> findMagazineByThemeName(String themeName) throws DaoException {
        try {
            Object[] parameters = {themeName};
            return executeQuery(connection, MagazineQuery.
                    SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME, builder, parameters);
        } catch (SQLException ex) {
            rollback(connection);
            throw new DaoException(ex);
        } finally {
            commit(connection);
        }
    }

    public List<Magazine> findSorted(String sortSubQuery) throws DaoException {
        Object[] parameters = {};
        try {
            return executeQuery(connection, MagazineQuery.SQL_FIND_SORT + sortSubQuery, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Magazine> findSortedPaginated(String sortSubQuery, int limit, int offset) throws DaoException {
        Object[] parameters = {limit, offset};
        try {
            return executeQuery(connection, MagazineQuery.SQL_FIND_SORT +
                            sortSubQuery +
                            MagazineQuery.SQL__FIND_SUB_PAGINATED
                    , builder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Magazine> findFiltered(String filterName) throws DaoException {
        Object[] parameters = {filterName};
        try {
            return executeQuery(connection, MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Magazine> findFilteredPaginated(String filterName, int limit, int offset) throws DaoException {
        Object[] parameters = {filterName, limit, offset};
        try {
            return executeQuery(connection,
                    MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME +
                            MagazineQuery.SQL__FIND_SUB_PAGINATED, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Magazine> findFilteredSortedPaginated(String filterName, String sortSubQuery, int limit, int offset) throws DaoException {
        Object[] parameters = {filterName, limit, offset};
        try {
            return executeQuery(connection,
                    MagazineQuery.SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME +
                            sortSubQuery +
                            MagazineQuery.SQL__FIND_SUB_PAGINATED, builder, parameters);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Magazine> findPage(int limit, int offset) throws DaoException {
        return findAll(connection, builder, limit, offset);
    }

    @Override
    public Optional<Magazine> findById(long id) throws DaoException {
        try {
            return executeSingleResponseQuery(connection, MagazineQuery.SQL__FIND_MAGAZINE_BY_ID, builder, id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DaoException createDaoException(String methodName, Exception e) {
        return new DaoException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}
