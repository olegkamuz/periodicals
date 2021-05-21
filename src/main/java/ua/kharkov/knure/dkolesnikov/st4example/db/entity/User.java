package ua.kharkov.knure.dkolesnikov.st4example.db.entity;

import java.math.BigDecimal;

/**
 * User entity.
 *
 */
public class User extends Entity {

    private static final long serialVersionUID = -6889036256149495388L;

    private Long id;

    private String login;

    private String password;

    private BigDecimal balance;

    private String firstName;

    private String lastName;

    private String localeName;

    private int roleId;

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

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
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
                + ", localeName=" + localeName + ", roleId=" + roleId + "]";
    }

}
