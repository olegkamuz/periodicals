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

    public RegistrationSaveCommand(UserRepository userRepository, UserBuilder userBuilder) {
        this.userRepository = userRepository;
        this.userBuilder = userBuilder;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Executing Registration command");
        this.request = request;

        if (!validateAllFields(getLogin(), getPassword(), getFirstName(), getLastName())){
            setError("All fields required", "error_reg");
            return Path.PAGE__REGISTRATION;
        }

        if (isExistedUser()) {
            throw new CommandException("Such user already registered");
        }

        User user = buildUser(getLogin(), getPassword(), getFirstName(), getLastName());
        try {
            userRepository.create(user);
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }

        log.info("User registered successfully");
        return Path.REDIRECT__LOGIN;
    }

    private boolean validateAllFields(String login, String password, String firstName, String lastName) {
        return Valid.notNullNotEmpty(login) && Valid.notNullNotEmpty(password)
                && Valid.notNullNotEmpty(firstName) && Valid.notNullNotEmpty(lastName);
    }

    private User buildUser(String login, String password, String firstName, String lastName) throws CommandException{
        try {
            return userBuilder
                    .setLogin(login)
                    .setPassword(Encoder.encrypt(password))
                    .setFirstName(firstName)
                    .setLastName(lastName)
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