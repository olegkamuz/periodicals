package com.training.periodical.model.dao.query;

public class UserQuery {
    public static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM `user` WHERE login=?";

    public static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM `user` WHERE id=?";

    public static final String SQL_UPDATE_USER =
            "UPDATE `user` SET password=?, first_name=?, last_name=?, locale_name=?" +
                    "	WHERE id=?";

    public static final String SQL__UPDATE_BALANCE_WHERE_ID =
            "UPDATE `user` SET balance=? WHERE id=?";
}
