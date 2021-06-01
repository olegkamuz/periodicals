package com.training.periodical.model.builder;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MagazineBuilder implements Builder<Magazine> {

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
}
