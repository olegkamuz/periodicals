package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Registration command starts");

        log.info("Registration command finish");
        return Path.PAGE__REGISTRATION;
//        return Path.REDIRECT__REGISTRATION;
    }
}