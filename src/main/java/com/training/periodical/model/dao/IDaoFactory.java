package com.training.periodical.model.dao;

import java.io.Serializable;
import java.sql.Connection;

/**
 * Interface for Data Access Objects factories
 *
 */
public interface IDaoFactory extends Serializable {
    UserDao createUserDao();
    UserDao createUserDao(Connection connection);
    SubscriptionDao createSubscriptionDao();
    MagazineDao createMagazineDao();
    ThemeDao createThemeDao();
}
