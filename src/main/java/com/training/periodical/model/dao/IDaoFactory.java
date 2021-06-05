package com.training.periodical.model.dao;

import com.training.periodical.bean.UserSubscriptionBean;

import java.io.Serializable;
import java.sql.Connection;

public interface IDaoFactory extends Serializable {
    UserDao createUserDao();
    UserDao createUserDao(Connection connection);
    SubscriptionDao createSubscriptionDao();
    MagazineDao createMagazineDao();
    ThemeDao createThemeDao();
}
