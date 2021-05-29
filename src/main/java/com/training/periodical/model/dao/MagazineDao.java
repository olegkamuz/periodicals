package com.training.periodical.model.dao;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Theme;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.dao.query.ThemeQuery;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Returns magazine by category name.
     *
     * @param themeName String to find by
     * @return List of theme entities.
     */
    public List<Magazine> findMagazineByThemeName(String themeName) throws DaoException {
        try {
            String[] parameters = {themeName};
            return executeQuery(connection, MagazineQuery.
                    SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME, builder, parameters);
        } catch (SQLException ex) {
            rollback(connection);
            throw new DaoException(ex);
        } finally {
            commit(connection);
        }
    }

    public BigDecimal findSumPriceByIds(String[] ids) throws DaoException {
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
        return Optional.empty();
    }

    @Override
    public void close()  {
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
