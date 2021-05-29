package com.training.periodical.entity;

import java.math.BigDecimal;

/**
 * User entity.
 *
 */
public class User extends Entity { // add serialization

    private Long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private String firstName;
    private String lastName;
    private int roleId;

    public User(long id, String login, String password,
                BigDecimal balance, String firstName,
                String lastName, int roleId){
        this.id = id;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
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

    @Override
    public String toString() {
        return "User [login=" + login + ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName
                + ", roleId=" + roleId + "]";
    }

}
