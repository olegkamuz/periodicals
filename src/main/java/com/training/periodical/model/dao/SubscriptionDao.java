package com.training.periodical.model.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for Order entity and UserOrderBean bean.
 */
public class SubscriptionDao extends AbstractDao {

    private static final Logger log = Logger.getLogger(UserDao.class);
    private Connection con;

    private static final String SQL__GET_USER_ORDER_BEANS =
            "SELECT o.id, u.first_name, u.last_name, o.bill, s.name" +
                    "	FROM users u, orders o, statuses s" +
                    "	WHERE o.user_id=u.id AND o.status_id=s.id";

    private static final String SQL__FIND_ALL_ORDERS =
            "SELECT * FROM orders";

    private static final String SQL__FIND_ORDERS_BY_STATUS_AND_USER =
            "SELECT * FROM orders WHERE status_id=? AND user_id=?";

    private static final String SQL__FIND_ORDERS_BY_STATUS =
            "SELECT * FROM orders WHERE status_id=?";

    /********************* NOT USED *******************************/
    private static final String SQL__INSERT_SUBSCRIPTION =
            "INSERT INTO subscription (user_id, magazine_id) VALUES (?, ?)";

    private static final String SQL_INSERT_ORDER_MENU =
            "INSERT INTO orders_menu VALUES (?, ?)";

    private static final String SQL_UPDATE_ORDER =
            "UPDATE orders SET status_id=? WHERE id=?";

    private static final String SQL__UPDATE_ORDER_SET_BILL =
            "UPDATE orders SET bill=" +
                    "(SELECT SUM(m.price) FROM menu m, orders_menu om " +
                    "WHERE m.id=om.menu_id and om.order_id=?) " +
                    "WHERE id=?";

    /**************************************************************/


    public SubscriptionDao(Connection con){
        this.con = con;
    }

    public void getPreparedStatement(Connection con, Long userId, Long magazineId)
            throws SQLException {
            PreparedStatement ps = con.prepareStatement(SQL__INSERT_SUBSCRIPTION);
            ps.setLong(1, userId);
            ps.setLong(2, magazineId);
            ps.executeUpdate();
            ps.close();
    }

    public void createSubscription(Long userId, List<Long> magazineIds) throws SQLException {
        PreparedStatement ps = null;
            for (Long magazineId : magazineIds) {
                getPreparedStatement(con, userId, magazineId);
            }
    }

    @Override
    public Optional getById(long id) throws SQLException {
        return Optional.empty();
    }
}













