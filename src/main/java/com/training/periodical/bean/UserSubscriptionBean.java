package com.training.periodical.bean;

import java.math.BigDecimal;

public class UserSubscriptionBean {
    private String magazineName;
    private BigDecimal magazinePrice;
    private String magazineImage;
    private String themeName;

    public UserSubscriptionBean(){}

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
    public String toString() {
        return "UserSubscriptionBean [magazineName=" + magazineName +
                ", magazinePrice=" + magazinePrice +
                ", magazineImage=" + magazineImage +
                ", themeName=" + themeName + "]";
    }
}
