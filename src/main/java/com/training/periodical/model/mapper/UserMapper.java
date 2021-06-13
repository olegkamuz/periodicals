package com.training.periodical.model.mapper;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping ResultSet data to User entity
 * @see ResultSet
 * @see User
 *
 */
public class UserMapper implements Mapper<User> {

    public User build(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY__ID));
        user.setLogin(rs.getString(Fields.USER__LOGIN));
        user.setPassword(rs.getString(Fields.USER__PASSWORD));
        user.setBalance(rs.getBigDecimal(Fields.USER__BALANCE));
        user.setFirstName(rs.getString(Fields.USER__FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER__LAST_NAME));
        user.setLocale(rs.getString(Fields.USER__LOCALE));
        user.setRoleId(rs.getInt(Fields.USER__ROLE_ID));
        user.setBlocked(rs.getInt(Fields.USER__BLOCKED));
        return user;
    }

}