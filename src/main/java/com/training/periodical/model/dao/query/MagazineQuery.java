package com.training.periodical.model.dao.query;

public class MagazineQuery {
    public static final String SQL__FIND_ALL_MAGAZINES =
            "SELECT * FROM magazine";

    public static final String SQL__FIND_MENU_ITEMS_BY_ORDER =
            "select * from magazine where id in (select menu_id from orders_menu where order_id=?)";

    public static final String SQL__FIND_MAGAZINES_BY_THEME =
            "select * from magazine where theme_id=?";

    public static final String SQL__CREATE_MAGAZINE =
            "INSERT INTO `magazine` VALUES(DEFAULT, ?, ?, ?, ?)";

    public static final String SQL__FIND_ALL_MAGAZINES_BY_THEME_NAME =
            "SELECT * FROM magazine m JOIN theme c ON m.theme_id=c.id WHERE c.name=?";

    public static final String SQL__FIND_MAGAZINE_BY_ID =
            "SELECT * FROM magazine WHERE id=?";

    public static final String SQL__UPDATE_MAGAZINE =
            "UPDATE `magazine` SET name=?, price=?, image=?, theme_id=? WHERE id=?";

    public static final String SQL__DELETE_MAGAZINE =
            "DELETE FROM `magazine` WHERE id=?";

    public static String getQueryFindSumPriceByIds(int amount){
        StringBuilder sb = new StringBuilder("SELECT sum(price) AS total FROM magazine WHERE id IN (");
        for (int i = 0; i < amount; i++) {
            sb.append("?,");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(")");
        return sb.toString();
    }
}
