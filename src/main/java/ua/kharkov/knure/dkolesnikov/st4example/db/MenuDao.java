package ua.kharkov.knure.dkolesnikov.st4example.db;

import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for menu related entities.
 */
public class MenuDao {

    private static final String SQL__FIND_ALL_MENU_ITEMS =
            "SELECT * FROM menu";

    private static final String SQL__FIND_MENU_ITEMS_BY_ORDER =
            "select * from menu where id in (select menu_id from orders_menu where order_id=?)";

    private static final String SQL__FIND_ALL_CATEGORIES =
            "SELECT * FROM categories";

    /**
     * Returns all categories.
     *
     * @return List of category entities.
     */
    public List<Category> findCategories() {
        List<Category> categoriesList = new ArrayList<Category>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_CATEGORIES);
            while (rs.next())
                categoriesList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return categoriesList;
    }

    /**
     * Returns all menu items.
     *
     * @return List of menu item entities.
     */
    public List<MenuItem> findMenuItems() {
        List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MenuItemMapper mapper = new MenuItemMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_MENU_ITEMS);
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
     * Returns menu items of the given order.
     *
     * @param order Order entity.
     * @return List of menu item entities.
     */
    public List<MenuItem> findMenuItems(Order order) {
        List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MenuItemMapper mapper = new MenuItemMapper();
            pstmt = con.prepareStatement(SQL__FIND_MENU_ITEMS_BY_ORDER);
            pstmt.setLong(1, order.getId());
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
     * Returns menu items with given identifiers.
     *
     * @param ids Identifiers of menu items.
     * @return List of menu item entities.
     */
    public List<MenuItem> findMenuItems(String[] ids) {
        List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            MenuItemMapper mapper = new MenuItemMapper();

            // create SQL query like "... id IN (1, 2, 7)"
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM menu WHERE id IN (");
            for (String idAsString : ids)
                query.append(idAsString).append(',');
            query.deleteCharAt(query.length() - 1);
            query.append(')');

            stmt = con.createStatement();
            rs = stmt.executeQuery(query.toString());
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
     * Extracts a category from the result set row.
     */
    private static class CategoryMapper implements EntityMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs) {
            try {
                Category category = new Category();
                category.setId(rs.getLong(Fields.ENTITY__ID));
                category.setName(rs.getString(Fields.CATEGORY__NAME));
                return category;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * Extracts a menu item from the result set row.
     */
    private static class MenuItemMapper implements EntityMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet rs) {
            try {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getLong(Fields.ENTITY__ID));
                menuItem.setName(rs.getString(Fields.MENU_ITEM__NAME));
                menuItem.setPrice(rs.getInt(Fields.MENU_ITEM__PRICE));
                menuItem.setCategoryId(rs.getLong(Fields.MENU_ITEM__CATEGORY_ID));
                return menuItem;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
