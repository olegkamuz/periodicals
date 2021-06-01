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

public class RegistrationSaveCommand implements Command {
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final UserService userService;
    private final UserBuilder userBuilder;

    public RegistrationSaveCommand(UserService userService, UserBuilder userBuilder) {
        this.userService = userService;
        this.userBuilder = userBuilder;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Executing Registration command");

        String result = "";
        if (
                request.getParameter("login") == null ||
                        request.getParameter("login").equals("") ||
                        request.getParameter("password") == null ||
                        request.getParameter("password").equals("") ||
                        request.getParameter("first-name") == null ||
                        request.getParameter("first-name").equals("") ||
                        request.getParameter("last-name") == null ||
                        request.getParameter("last-name").equals("")
        ) {
            request.setAttribute("msg", "Please fill in all the fields of the form"); //TODO in18ze
            return result;
        }

        if (isExistedUser(request)) {
            request.setAttribute("msg", "User already exists"); //TODO in18ze
            return result;
        }

        try {
            userService.create(buildUser(request));
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        log.info("User registered successfully");
        request.setAttribute("msgSuccess", "You have been registered successfully");
        return Path.REDIRECT__LOGIN;
    }

    private User buildUser(HttpServletRequest request) {
        return userBuilder
                .setLogin(request.getParameter("login"))
                .setPassword(request.getParameter("password"))
                .setFirstName(request.getParameter("first-name"))
                .setLastName(request.getParameter("last-name"))
                .build();
    }

    private boolean isExistedUser(HttpServletRequest request) throws CommandException {
        Optional<User> optionalUser = null;
        try {
            optionalUser = userService.findUserByLogin(request.getParameter("login"));
            return optionalUser.isPresent();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}