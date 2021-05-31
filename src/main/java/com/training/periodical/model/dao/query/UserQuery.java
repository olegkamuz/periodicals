package com.training.periodical.model.dao.query;

public class UserQuery {
    public static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM `user` WHERE login=?";

    public static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM `user` WHERE id=?";

    public static final String SQL_UPDATE_USER =
            "UPDATE `user` SET password=?, first_name=?, last_name=?, locale_name=?" +
                    "	WHERE id=?";

    public static final String SQL__CREATE_USER =
            "INSERT INTO `user` VALUES (DEFAULT, ?, ?, DEFAULT, ?, ?, NULL, 1, 0)";

    public static final String SQL__UPDATE_BALANCE_WHERE_ID =
            "UPDATE `user` SET balance=? WHERE id=?";

    public static final String SQL__FIND_SUBSCRIPTIONS_WHERE_USER_ID =
    "SELECT m.name, m.price, m.image, t.name FROM subscription s" +
            " JOIN magazine m ON m.id = s.magazine_id" +
            " JOIN theme t ON t.id = m.theme_id" +
            " WHERE user_id=?";

    public static String getUpdateColumnQuery(String userId, String column) {
        return "UPDATE `user` SET `" + column + "`=? WHERE `id`=?";
    }
}
