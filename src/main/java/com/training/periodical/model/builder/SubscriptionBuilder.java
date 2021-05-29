package com.training.periodical.model.builder;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extracts a subscription from the result set row.
 */
public class SubscriptionBuilder implements Builder<Subscription> {

    @Override
    public Subscription build(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setUserId(rs.getLong(Fields.USER__ID));
        subscription.setMagazineId(rs.getLong(Fields.MAGAZINE__ID));
        return subscription;
    }
}
