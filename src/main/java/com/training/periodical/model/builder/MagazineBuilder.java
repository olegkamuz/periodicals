package com.training.periodical.model.builder;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.Fields;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazineBuilder implements Builder<Magazine> {
    private static final long serialVersionUID = -52120070602340346L;
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

    @Override
    public Magazine build(ResultSet rs) throws SQLException {
        Magazine magazine = new Magazine();
        magazine.setId(rs.getLong(Fields.ENTITY__ID));
        magazine.setName(rs.getString(Fields.MAGAZINE__NAME));
        magazine.setPrice(rs.getBigDecimal(Fields.MAGAZINE__PRICE));
        magazine.setImage(rs.getString(Fields.MAGAZINE__IMAGE));
        magazine.setThemeId(rs.getLong(Fields.MAGAZINE__THEME_ID));
        return magazine;
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
