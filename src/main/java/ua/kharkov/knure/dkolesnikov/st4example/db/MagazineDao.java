package ua.kharkov.knure.dkolesnikov.st4example.db;

import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Magazine;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Order;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for magazine related entities.
 */
public class MagazineDao {

    private static final String SQL__FIND_ALL_MAGAZINES =
            "SELECT * FROM magazine";

    private static final String SQL__FIND_MENU_ITEMS_BY_ORDER =
            "select * from magazine where id in (select menu_id from orders_menu where order_id=?)";

    private static final String SQL__FIND_MAGAZINES_BY_THEME =
            "select * from magazine where theme_id=?";

    private static final String SQL__FIND_ALL_THEME =
            "SELECT * FROM theme";

    private static final String SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME =
            "SELECT * FROM magazine m JOIN theme c ON m.theme_id=c.id WHERE c.name=?";


    /**
     * Returns magazine by category name.
     *
     * @param themeName String to find by
     * @return List of theme entities.
     */
    public List<Magazine> findMagazineByThemeName(String themeName) {
        List<Magazine> magazinesList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MagazineMapper mapper = new MagazineMapper();
            pstmt = con.prepareStatement(SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME);
            pstmt.setString(1, themeName);
            rs = pstmt.executeQuery();
            while (rs.next())
                magazinesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return magazinesList;
    }
    /**
     * Returns all categories.
     *
     * @return List of category entities.
     */
    public List<Theme> findThemes() {
        List<Theme> themesList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            ThemeMapper mapper = new ThemeMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_THEME);
            while (rs.next())
                themesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return themesList;
    }

    /**
     * Returns all menu items.
     *
     * @return List of menu item entities.
     */
    public List<Magazine> findMagazines() {
        List<Magazine> magazinesList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MagazineMapper mapper = new MagazineMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_MAGAZINES);
            while (rs.next())
                magazinesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return magazinesList;
    }/**
     * Returns menu items of the given order.
     *
     * @param order Order entity.
     * @return List of menu item entities.
     */
    public List<Magazine> findMagazines(Order order) {
        List<Magazine> magazinesList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MagazineMapper mapper = new MagazineMapper();
            pstmt = con.prepareStatement(SQL__FIND_MENU_ITEMS_BY_ORDER);
            pstmt.setLong(1, order.getId());
            rs = pstmt.executeQuery();
            while (rs.next())
                magazinesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return magazinesList;
    }

    /**
     * Returns menu items of the given order.
     *
     * @param category Order entity.
     * @return List of menu item entities.
     */
    public List<Magazine> findMagazines(Category category) {
        List<Magazine> menuItemsList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MagazineMapper mapper = new MagazineMapper();
            pstmt = con.prepareStatement(SQL__FIND_MAGAZINES_BY_THEME);
            pstmt.setLong(1, category.getId());
            rs = pstmt.executeQuery();
            while (rs.next())
                menuItemsList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return menuItemsList;
    }

    /**
     * Returns magazine with given identifiers.
     *
     * @param ids Identifiers of magazine.
     * @return List of magazine entities.
     */
    public List<Magazine> findMagazines(String[] ids) {
        List<Magazine> magazinesList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MagazineMapper mapper = new MagazineMapper();

            // create SQL query like "... id IN (1, 2, 7)"
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM magazine WHERE id IN (");
            for (String idAsString : ids) {
                query.append(idAsString).append(',');
            }
            query.deleteCharAt(query.length() - 1);
            query.append(')');

            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
            while (rs.next())
                magazinesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return magazinesList;
    }

    /**
     * Extracts a theme from the result set row.
     */
    private static class ThemeMapper implements EntityMapper<Theme> {

        @Override
        public Theme mapRow(ResultSet rs) {
            try {
                Theme theme = new Theme();
                theme.setId(rs.getLong(Fields.ENTITY__ID));
                theme.setName(rs.getString(Fields.THEME__NAME));
                return theme;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * Extracts a magazine from the result set row.
     */
    private static class MagazineMapper implements EntityMapper<Magazine> {

        @Override
        public Magazine mapRow(ResultSet rs) {
            try {
                Magazine magazine = new Magazine();
                magazine.setId(rs.getLong(Fields.ENTITY__ID));
                magazine.setName(rs.getString(Fields.MAGAZINE__NAME));
                magazine.setPrice(rs.getInt(Fields.MAGAZINE__PRICE));
                magazine.setImage(rs.getString(Fields.MAGAZINE__IMAGE));
                magazine.setThemeId(rs.getLong(Fields.MAGAZINE__THEME_ID));
                return magazine;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
