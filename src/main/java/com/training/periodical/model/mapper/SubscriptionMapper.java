package com.training.periodical.model.mapper;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping ResultSet data to Subscription entity
 * @see ResultSet
 * @see Subscription
 *
 */
public class SubscriptionMapper implements Mapper<Subscription> {

    @Override
    public Subscription build(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setUserId(rs.getLong(Fields.USER__ID));
        subscription.setMagazineId(rs.getLong(Fields.MAGAZINE__ID));
        return subscription;
    }
}
