package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationSaveCommand implements Command {
    private static final long serialVersionUID = 584824241835000931L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final UserService userService;
    private final UserBuilder userBuilder;

    public RegistrationSaveCommand(UserService userService, UserBuilder userBuilder) {
        this.userService = userService;
        this.userBuilder = userBuilder;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Executing Registration command");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");

        try {
            if (!Validator.isValid(login, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY) ||
                    !Validator.isValid(password, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY) ||
                    !Validator.isValid(firstName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY) ||
                    !Validator.isValid(lastName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)
            ) {
                return Path.REDIRECT__INDEX;
            }
        } catch (ValidatorException e) {
            throw new CommandException("Not valid data at RegistrationSameCommand in execution method");
        }

        if (isExistedUser(request)) {
            throw new CommandException("Such user already registered");
//            return Path.REDIRECT__INDEX;
        }

        User user = buildUser(login, password, firstName, lastName);
        try {
            userService.create(user);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        log.info("User registered successfully");
        return Path.REDIRECT__LOGIN;
    }

    private User buildUser(String login, String password, String firstName, String lastName) {
        return userBuilder
                .setLogin(login)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();
    }

    private boolean isExistedUser(HttpServletRequest request) throws CommandException {
        try {
            Optional<User> optionalUser = userService.findUserByLogin(request.getParameter("login"));
            return optionalUser.isPresent();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    @Override
    public CommandException createCommandException(String methodName, ServiceException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }

    @Override
    public CommandException createCommandException(String methodName, ValidatorException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}