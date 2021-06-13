package com.training.periodical.model.dao.query;

import com.training.periodical.entity.Subscription;

import java.io.Serializable;

/**
 * Holder of queries related to Subscription entity
 *
 * @see Subscription
 */
public class SubscriptionQuery implements Serializable {
    private static final long serialVersionUID = -6262811607549592274L;

    public static final String SQL__INSERT_SUBSCRIPTION =
            "INSERT INTO subscription (user_id, magazine_id) VALUES (?, ?)";

    public static final String SQL__COUNT_BY_COMPOSITE_KEY =
            "SELECT COUNT(*) FROM `subscription` WHERE user_id=? AND magazine_id=?";

}
