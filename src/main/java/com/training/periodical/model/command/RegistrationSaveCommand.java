package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.encoder.Encoder;
import com.training.periodical.util.encoder.EncoderException;
import com.training.periodical.util.validator.Valid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class RegistrationSaveCommand extends AbstractCommand {
    private static final long serialVersionUID = 584824241835000931L;
    private final UserRepository userRepository;
    private final UserBuilder userBuilder;
    private boolean error = false;
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    public RegistrationSaveCommand(UserRepository userRepository, UserBuilder userBuilder) {
        this.userRepository = userRepository;
        this.userBuilder = userBuilder;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Executing Registration command");
        this.request = request;

        setLogin();
        setPassword();
        setFirstname();
        setLastName();

        preventXSSAttack();
        validateAllInput();

        if (error) {
            error = false;
            return Path.PAGE__REGISTRATION;
        }

        validateLoginTaken();

        if (error) {
            error = false;
            return Path.PAGE__REGISTRATION;
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

    private void setLogin() {
        this.login = getLogin();
    }

    private void setPassword() {
        this.password = getPassword();
    }

    private void setFirstname() {
        this.firstName = getFirstName();
    }

    private void setLastName() {
        this.lastName = getLastName();
    }

    private void validateLoginTaken() throws CommandException {
        if (isExistedUser()) {
            error = true;
            setError("This login is already taken", "error_taken_login");
        } else {
            request.getSession().removeAttribute("error_symbols");
        }
    }

    private boolean preventXSSAttack(){
        if (!Valid.isValidSymbols(login, firstName, lastName)) {
            setError("Error symbols", "error_symbols");
            return true;
        } else {
            request.getSession().removeAttribute("error_symbols");
        }
        return false;
    }

    private boolean validateAllInput(){
        if (!Valid.notNullNotEmpty(login, password, firstName, lastName)) {
            setError("All fields required", "error_reg");
            return true;
        } else {
            request.getSession().removeAttribute("error_reg");
        }
        return false;
    }

    private User buildUser(String login, String password, String firstName, String lastName) throws CommandException {
        try {
            return userBuilder
                    .setLogin(Valid.escapeInput(login))
                    .setPassword(Encoder.encrypt(password))
                    .setFirstName(Valid.escapeInput(firstName))
                    .setLastName(Valid.escapeInput(lastName))
                    .build();
        } catch (EncoderException e) {
            throw createCommandException("buildUser", e);
        }
    }

    private boolean isExistedUser() throws CommandException {
        try {
            Optional<User> optionalUser = userRepository.findUserByLogin(request.getParameter("login"));
            return optionalUser.isPresent();
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }
}