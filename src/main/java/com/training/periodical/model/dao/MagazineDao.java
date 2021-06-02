package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Subscription;
import com.training.periodical.entity.Theme;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.dao.query.ThemeQuery;
import com.training.periodical.model.dao.query.UserQuery;
import sun.security.x509.EDIPartyName;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Data access object for magazine related entities.
 */
public class MagazineDao extends AbstractDao<Magazine> {
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

    protected static void close(ResultSet rs) throws DaoException {
        if (rs == null) return;
        try {
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public BigDecimal findSumPriceByIds(Object[] ids) throws DaoException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MagazineQuery.
                    getQueryFindSumPriceByIds(ids.length));
            prepareStatement(preparedStatement, ids);
            ResultSet rs = preparedStatement.executeQuery();

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
        }
    }

    public List<Magazine> findBatch(int limit, int offset) throws DaoException {
        return findAll(connection, builder, limit, offset);
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


//    /**
//     * Returns magazine by id.
//     *
//     * @param id id to find by.
//     * @return Magazine entity.
//     */
//    public Magazine findMagazineById(Long id) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            MagazineMapper mapper = new MagazineMapper();
//            pstmt = con.prepareStatement(MagazineQuery.
//                    SQL__FIND_MAGAZINE_BY_ID);
//            pstmt.setLong(1, id);
//            rs = pstmt.executeQuery();
//            if(rs.next()){
//                return mapper.mapRow(rs);
//            }
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return null;
//    }

//    /**
//     * Returns all menu items.
//     *
//     * @return List of menu item entities.
//     */
//    public List<Magazine> findMagazines() {
//        List<Magazine> magazinesList = new ArrayList<>();
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            MagazineMapper mapper = new MagazineMapper();
//            stmt = con.createStatement();
//            rs = stmt.executeQuery(MagazineQuery.
//                    SQL__FIND_ALL_MAGAZINES);
//            while (rs.next())
//                magazinesList.add(mapper.mapRow(rs));
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return magazinesList;
//    }/**
//     * Returns menu items of the given order.
//     *
//     * @param order Order entity.
//     * @return List of menu item entities.
//     */
//    public List<Magazine> findMagazines(Order order) {
//        List<Magazine> magazinesList = new ArrayList<>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            MagazineMapper mapper = new MagazineMapper();
//            pstmt = con.prepareStatement(MagazineQuery.
//                    SQL__FIND_MENU_ITEMS_BY_ORDER);
//            pstmt.setLong(1, order.getId());
//            rs = pstmt.executeQuery();
//            while (rs.next())
//                magazinesList.add(mapper.mapRow(rs));
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return magazinesList;
//    }
//
//    /**
//     * Returns menu items of the given order.
//     *
//     * @param category Order entity.
//     * @return List of menu item entities.
//     */
//    public List<Magazine> findMagazines(Category category) {
//        List<Magazine> menuItemsList = new ArrayList<>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            MagazineMapper mapper = new MagazineMapper();
//            pstmt = con.prepareStatement(MagazineQuery.
//                    SQL__FIND_MAGAZINES_BY_THEME);
//            pstmt.setLong(1, category.getId());
//            rs = pstmt.executeQuery();
//            while (rs.next())
//                menuItemsList.add(mapper.mapRow(rs));
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return menuItemsList;
//    }
//
//    /**
//     * Returns magazine with given identifiers.
//     *
//     * @param ids Identifiers of magazine.
//     * @return List of magazine entities.
//     */
//    public List<Magazine> findMagazines(String[] ids) {
//        List<Magazine> magazinesList = new ArrayList<>();
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            MagazineMapper mapper = new MagazineMapper();
//
//            // create SQL query like "... id IN (1, 2, 7)"
//            StringBuilder query = new StringBuilder(
//                    "SELECT * FROM magazine WHERE id IN (");
//            for (String idAsString : ids) {
//                query.append(idAsString).append(',');
//            }
//            query.deleteCharAt(query.length() - 1);
//            query.append(')');
//
//            stmt = con.createStatement();
//            rs = stmt.executeQuery(query.toString());
//            while (rs.next())
//                magazinesList.add(mapper.mapRow(rs));
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return magazinesList;
//    }

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

//    /**
//     * Extracts a theme from the result set row.
//     */
//    private static class ThemeMapper implements EntityMapper<Theme> {
//
//        @Override
//        public Theme mapRow(ResultSet rs) {
//            try {
//                Theme theme = new Theme();
//                theme.setId(rs.getLong(Fields.ENTITY__ID));
//                theme.setName(rs.getString(Fields.THEME__NAME));
//                return theme;
//            } catch (SQLException e) {
//                throw new IllegalStateException(e);
//            }
//        }
//    }
//
//    /**
//     * Extracts a magazine from the result set row.
//     */
//    private static class MagazineMapper implements EntityMapper<Magazine> {
//
//        @Override
//        public Magazine mapRow(ResultSet rs) {
//            try {
//                Magazine magazine = new Magazine();
//                magazine.setId(rs.getLong(Fields.ENTITY__ID));
//                magazine.setName(rs.getString(Fields.MAGAZINE__NAME));
//                magazine.setPrice(rs.getBigDecimal(Fields.MAGAZINE__PRICE));
//                magazine.setImage(rs.getString(Fields.MAGAZINE__IMAGE));
//                magazine.setThemeId(rs.getLong(Fields.MAGAZINE__THEME_ID));
//                return magazine;
//            } catch (SQLException e) {
//                throw new IllegalStateException(e);
//            }
//        }
//    }
}
