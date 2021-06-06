package com.training.periodical.controller.command;

import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import com.training.periodical.util.validator.Validator;
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
public class UpdateSettingsCommand implements Command {
    private static final long serialVersionUID = 3553552307337644253L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final UserService userService;

    public UpdateSettingsCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");

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

        String localeToSet = request.getParameter("localeToSet");
        if(isUpdateLocale(localeToSet, user, request)) {
            updateUser = true;
        }

        if (updateUser) {
            try {
                userService.updateNow(user);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

    private boolean isUpdateFirstName(String firstName,User user) throws CommandException {
        try {
            if (Validator.isValid(firstName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)) {
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
            if (Validator.isValid(lastName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)) {
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
            if(Validator.isValid(localeToSet, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)) {
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