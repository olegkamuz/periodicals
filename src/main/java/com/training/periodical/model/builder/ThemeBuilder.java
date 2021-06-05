package com.training.periodical.model.builder;

import com.training.periodical.entity.Theme;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeBuilder implements Builder<Theme> {
    private static final long serialVersionUID = 2312353289307871299L;

    public Theme build (ResultSet rs)  throws SQLException {
        Theme theme = new Theme();
        theme.setId(rs.getLong(Fields.ENTITY__ID));
        theme.setName(rs.getString(Fields.THEME_NAME));
        return theme;
    }
}
