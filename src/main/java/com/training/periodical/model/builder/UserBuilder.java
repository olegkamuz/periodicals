package com.training.periodical.model.builder;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.dao.Fields;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extracts a user from the result set row.
 */
public class UserBuilder implements Builder<User> {
    private static final long serialVersionUID = 1955202766270229852L;
    private Long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private String firstName;
    private String lastName;
    private String locale;
    private int roleId;
    private int blocked;

    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public int getBlocked() {
        return blocked;
    }
    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }
    public UserBuilder setId(long id) {
        this.id = id;
        return this;
    }
    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }
    public UserBuilder setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
    public UserBuilder setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }

    public User build() {
        return id == null ? strippedUser() :fullUser();
    }

    private User fullUser(){
        return new User(id, login, password, balance, firstName, lastName, locale, roleId, blocked);
    }
    private User strippedUser(){
        return new User(login, password, firstName, lastName);
    }

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

    public Object[] unBuildStrippedUser(User user) {
        return new Object[]{
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        };
    }
    public Object[] unBuild(User user) {
        return new Object[]{
                user.getLogin(),
                user.getPassword(),
                user.getBalance(),
                user.getFirstName(),
                user.getLastName(),
                user.getLocale(),
                user.getRoleId(),
                user.getBlocked(),
                user.getId()
        };
    }


}
