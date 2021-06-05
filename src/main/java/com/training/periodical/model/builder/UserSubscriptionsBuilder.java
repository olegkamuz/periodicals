package com.training.periodical.model.builder;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.model.dao.Fields;

public class UserSubscriptionsBuilder implements Builder<UserSubscriptionBean>{
    private static final long serialVersionUID = 2384741942615781824L;
    private String magazineName;
    private BigDecimal magazinePrice;
    private String magazineImage;
    private String themeName;

    public String getMagazineName() {
        return magazineName;
    }
    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }
    public BigDecimal getMagazinePrice() {
        return magazinePrice;
    }
    public void setMagazinePrice(BigDecimal magazinePrice) {
        this.magazinePrice = magazinePrice;
    }
    public String getMagazineImage() {
        return magazineImage;
    }
    public void setMagazineImage(String magazineImage) {
        this.magazineImage = magazineImage;
    }
    public String getThemeName() {
        return themeName;
    }
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public UserSubscriptionBean build(ResultSet rs) throws SQLException {
        UserSubscriptionBean usb = new UserSubscriptionBean();
        usb.setMagazineName(rs.getString(Fields.MAGAZINE__NAME));
        usb.setMagazinePrice(rs.getBigDecimal(Fields.MAGAZINE__PRICE));
        usb.setMagazineImage(rs.getString(Fields.MAGAZINE__IMAGE));
        usb.setThemeName(rs.getString(Fields.THEME_NAME));
        return usb;
    }
}
