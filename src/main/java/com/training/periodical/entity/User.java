package com.training.periodical.entity;

import java.math.BigDecimal;

/**
 * User entity.
 *
 */
public class User extends Entity { // add serialization

    private String login;
    private String password;
    private BigDecimal balance;
    private String firstName;
    private String lastName;
    private String locale;
    private int roleId;

    public User(){}

    public User(long userId, String login, String password,
                BigDecimal balance, String firstName,
                String lastName, int roleId){
        id = userId;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }
    public User(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    @Override
    public String toString() {
        return "User [login=" + login + ", password=" + password +
                ", balance " + balance + ", firstName=" + firstName +
                ", lastName=" + lastName + ",localeName=" + locale +
                ", roleId=" + roleId + "]";
    }

}
