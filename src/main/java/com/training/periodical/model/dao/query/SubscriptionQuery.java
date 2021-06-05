package com.training.periodical.model.dao.query;

import java.io.Serializable;

public class SubscriptionQuery implements Serializable {
    private static final long serialVersionUID = -6262811607549592274L;
    public static final String SQL__GET_USER_ORDER_BEANS =
            "SELECT o.id, u.first_name, u.last_name, o.bill, s.name" +
                    "	FROM users u, orders o, statuses s" +
                    "	WHERE o.user_id=u.id AND o.status_id=s.id";

    public static final String SQL__FIND_ALL_ORDERS =
            "SELECT * FROM orders";

    public static final String SQL__FIND_ORDERS_BY_STATUS_AND_USER =
            "SELECT * FROM orders WHERE status_id=? AND user_id=?";

    public static final String SQL__FIND_ORDERS_BY_STATUS =
            "SELECT * FROM orders WHERE status_id=?";

    public static final String SQL__INSERT_SUBSCRIPTION =
            "INSERT INTO subscription (user_id, magazine_id) VALUES (?, ?)";

    public static final String SQL_INSERT_ORDER_MENU =
            "INSERT INTO orders_menu VALUES (?, ?)";

    public static final String SQL_UPDATE_ORDER =
            "UPDATE orders SET status_id=? WHERE id=?";

    public static final String SQL__UPDATE_ORDER_SET_BILL =
            "UPDATE orders SET bill=" +
                    "(SELECT SUM(m.price) FROM menu m, orders_menu om " +
                    "WHERE m.id=om.menu_id and om.order_id=?) " +
                    "WHERE id=?";
    public static final String SQL__COUNT_BY_COMPOSITE_KEY =
            "SELECT COUNT(*) FROM `subscription` WHERE user_id=? AND magazine_id=?";

}
