package com.training.periodical.model.service;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    private UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findUserById(Long userId) throws SQLException{
        return userDao.getById(userId);
    }
}



















