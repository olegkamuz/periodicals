package com.training.periodical.entity;

import java.math.BigDecimal;

/**
 * Magazine item entity.
 */
public class Magazine extends Entity {
    private static final long serialVersionUID = -8305716862338793072L;
    private String name;
    private BigDecimal price;
    private String image;
    private long themeId;

    public Magazine(){}
    public Magazine(String name, BigDecimal price, String image, long themeId){
        this.name = name;
        this.price = price;
        this.image = image;
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Long getThemeId() {
        return themeId;
    }
    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Magazine [id=" + getId() + ", name=" + name + ", price=" + price + ", image=" + image + ", themeId="
                + themeId + "]";
    }

}