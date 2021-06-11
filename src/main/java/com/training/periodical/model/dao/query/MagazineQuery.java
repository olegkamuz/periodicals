package com.training.periodical.model.dao.query;

import java.io.Serializable;

public class MagazineQuery implements Serializable {
    private static final long serialVersionUID = -4845845434231486872L;
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

    public static final String SQL__FIND_ALL_SEARCHED_MAGAZINES_BY_THEME_NAME =
            "SELECT * FROM magazine m JOIN theme c ON m.theme_id=c.id WHERE c.name=? AND m.name LIKE ? ";

    public static final String SQL__COUNT_ALL =
            "SELECT COUNT(*) FROM `magazine`";

    public static final String SQL__COUNT_ALL_SEARCHED =
            "SELECT COUNT(*) FROM `magazine` WHERE name LIKE ?";

    public static final String SQL__COUNT_FILTERED =
            "SELECT COUNT(*) FROM magazine m JOIN theme c ON m.theme_id=c.id WHERE c.name=?";

    public static final String SQL__COUNT_SEARCHED_FILTERED =
            "SELECT COUNT(*) FROM magazine m JOIN theme c ON m.theme_id=c.id WHERE c.name=? AND m.name LIKE ? ";

    public static final String SQL__FIND_MAGAZINE_BY_ID =
            "SELECT * FROM magazine WHERE id=?";

    public static final String SQL__UPDATE_MAGAZINE =
            "UPDATE `magazine` SET name=?, price=?, image=?, theme_id=? WHERE id=?";

    public static final String SQL__DELETE_MAGAZINE =
            "DELETE FROM `magazine` WHERE id=?";

    public static final String SQL__FIND_MAGAZINE_PAGE =
            "SELECT * FROM `magazine` LIMIT ? OFFSET ?";

    public static final String SQL__FIND_SEARCHED_MAGAZINE_PAGE =
            "SELECT * FROM `magazine` WHERE name LIKE ? LIMIT ? OFFSET ?";

    public static final String SQL__FIND_SUB_PAGINATED =
            " LIMIT ? OFFSET ?";

    public static final String SQL__SUB_SORT_NAME_ASC =
            " ORDER BY name ASC ";
    public static final String SQL__SUB_SORT_NAME_DESC =
            " ORDER BY name DESC ";
    public static final String SQL__SUB_SORT_PRICE_ASC =
            " ORDER BY price ASC ";
    public static final String SQL__SUB_SORT_PRICE_DESC =
            " ORDER BY price DESC ";
    public static final String SQL_FIND_SORT =
            "SELECT * FROM `magazine` ";
    public static final String SQL_FIND_SEARCHED_SORT =
            "SELECT * FROM `magazine` WHERE name LIKE ? ";
    public static final String SQL__SUB_FILTER_SORT_NAME_ASC =
            " ORDER BY m.name ASC ";
    public static final String SQL__SUB_FILTER_SORT_NAME_DESC =
            " ORDER BY m.name DESC ";
    public static final String SQL__SUB_FILTER_SORT_PRICE_ASC =
            " ORDER BY m.price ASC ";
    public static final String SQL__SUB_FILTER_SORT_PRICE_DESC =
            " ORDER BY m.price DESC ";

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
