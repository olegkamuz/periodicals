package com.training.periodical.model.builder;

import com.training.periodical.entity.Magazine;

import java.math.BigDecimal;

public class MagazineBuilder implements Builder<Magazine> {
    private String id;
    private String name;
    private BigDecimal price;
    private String image;
    private Long themeId;

    public MagazineBuilder setId(String id) {
        this.id = id;
        return this;
    }
    public MagazineBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public MagazineBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    public MagazineBuilder setImage(String image) {
        this.image = image;
        return this;
    }
    public MagazineBuilder setThemeId(Long themeId) {
        this.themeId = themeId;
        return this;
    }

    public Magazine strippedMagazine(){
            return new Magazine(name, price, image, themeId);
    }

    public Magazine fullMagazine(){
        return new Magazine(name, price, image, themeId);
    }

    public Magazine build() {
        return id == null ? strippedMagazine() : fullMagazine();
    }

    public Object[] unBuild(Magazine magazine) {
        Object[] objArr =  {
                magazine.getName(),
                magazine.getPrice(),
                magazine.getImage(),
                magazine.getThemeId(),
                magazine.getId()
        };
        return objArr;
    }

    public Object[] unBuildStrippedMagazine(Magazine magazine) {
        return new Object[]{
                magazine.getName(),
                magazine.getPrice(),
                magazine.getImage(),
                magazine.getThemeId()
        };
    }

}
