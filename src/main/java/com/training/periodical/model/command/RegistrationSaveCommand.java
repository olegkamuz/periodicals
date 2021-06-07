package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.Valid;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationSaveCommand implements Command {
    private static final long serialVersionUID = 584824241835000931L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final UserRepository userRepository;
    private final UserBuilder userBuilder;

    public RegistrationSaveCommand(UserRepository userRepository, UserBuilder userBuilder) {
        this.userRepository = userRepository;
        this.userBuilder = userBuilder;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Executing Registration command");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");

        if (!validateAllFields(login, password, firstName, lastName, request)){
            setError(request, "All fields required", "error_reg");
            return Path.PAGE__REGISTRATION;
        }

        if (isExistedUser(request)) {
            throw new CommandException("Such user already registered");
        }

        User user = buildUser(login, password, firstName, lastName);
        try {
            userRepository.create(user);
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }

        log.info("User registered successfully");
        return Path.REDIRECT__LOGIN;
    }
    private void setError(HttpServletRequest request, String errorMessage, String attributeName) {
        request.getSession().setAttribute(attributeName, errorMessage);
        log.error("errorMessage --> " + errorMessage);
    }

    private boolean validateAllFields(String login, String password, String firstName, String lastName, HttpServletRequest request) throws CommandException {
        try {
            if(Valid.notNullNotEmpty(login) && Valid.notNullNotEmpty(password)
            && Valid.notNullNotEmpty(firstName) && Valid.notNullNotEmpty(lastName)){
                return true;
            }
        } catch (ValidatorException e) {
            throw createCommandException("execute", e);
        }
        return false;
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
            Optional<User> optionalUser = userRepository.findUserByLogin(request.getParameter("login"));
            return optionalUser.isPresent();
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    @Override
    public CommandException createCommandException(String methodName, RepositoryException e) {
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