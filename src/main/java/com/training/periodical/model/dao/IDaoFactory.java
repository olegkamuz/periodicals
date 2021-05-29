package com.training.periodical.model.dao;

import java.sql.Connection;

public interface IDaoFactory {

    UserDao createUserDao();
    UserDao createUserDao(Connection connection);
    SubscriptionDao createSubscriptionDao();
    MagazineDao createMagazineDao();
    ThemeDao createThemeDao();
}
