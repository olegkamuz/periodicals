package com.training.periodical.model.command;

import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.Valid;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Update settings items.
 */
public class UpdateSettingsCommand extends AbstractCommand {
    private static final long serialVersionUID = 3553552307337644253L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final UserRepository userRepository;

    public UpdateSettingsCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");

        updateLocaleIfRequested(request.getParameter("localeToSet"), request);

        User user = (User) request.getSession().getAttribute("user");
        boolean updateUser = false;

        String firstName = request.getParameter("firstName");
        if(isUpdateFirstName(firstName, user)) {
            updateUser = true;
        }

        String lastName = request.getParameter("lastName");
        if(isUpdateLastName(lastName, user)) {
            updateUser = true;
        }

        String localeToSet = request.getParameter("localeToSetUser");
        if(isUpdateLocale(localeToSet, user, request)) {
            updateUser = true;
        }

        if (updateUser) {
            try {
                userRepository.updateNow(user);
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }


        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

    private boolean isUpdateFirstName(String firstName,User user) throws CommandException {
        try {
            if (Valid.notNullNotEmpty(firstName)) {
                user.setFirstName(firstName);
                return true;
            }
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }
    private boolean isUpdateLastName(String lastName,User user) throws CommandException {
        try {
            if (Valid.notNullNotEmpty(lastName)) {
                user.setLastName(lastName);
                return true;
            }
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }
    private boolean isUpdateLocale(String localeToSet, User user, HttpServletRequest request) throws CommandException {
        try {
            if (Valid.notNullNotEmpty(localeToSet)) {
                HttpSession session = request.getSession();
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
                session.setAttribute("defaultLocale", localeToSet);
                user.setLocale(localeToSet);
                return true;
            }
        } catch (ValidatorException e) {
            throw createCommandException("isUpdateLocale", e);
        }

        return false;
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