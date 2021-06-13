package com.training.periodical.model.mapper;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping ResultSet data to Magazine entity
 * @see ResultSet
 * @see Magazine
 *
 */
public class UserSubscriptionsMapper implements Mapper<UserSubscriptionBean> {

    @Override
    public UserSubscriptionBean build(ResultSet rs) throws SQLException {
        UserSubscriptionBean usb = new UserSubscriptionBean();
        usb.setMagazineName(rs.getString(Fields.MAGAZINE__NAME));
        usb.setMagazinePrice(rs.getBigDecimal(Fields.MAGAZINE__PRICE));
        usb.setMagazineImage(rs.getString(Fields.MAGAZINE__IMAGE));
        usb.setThemeName(rs.getString(Fields.BEAN_THEME_NAME));
        return usb;
    }
}
