package com.training.periodical.model.dao.query;

import com.training.periodical.bean.UserSubscriptionBean;

/**
 * Holder of queries related to UserSubscriptionBean
 * @see UserSubscriptionBean
 *
 */
public class UserSubscriptionBeanQuery {

    public static final String SQL__FIND_SUBSCRIPTIONS_WHERE_USER_ID =
            "SELECT m.name AS name, m.price, m.image, t.name AS theme_name FROM subscription s" +
                    " JOIN magazine m ON m.id = s.magazine_id" +
                    " JOIN theme t ON t.id = m.theme_id" +
                    " WHERE user_id=?";
}
