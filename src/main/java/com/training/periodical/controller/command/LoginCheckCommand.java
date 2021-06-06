package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Enumeration;
import java.util.Optional;

/**
 * Login check command.
 *
 * Validate login/password
 * From error messages if needed
 * Log user in
 */
public class LoginCheckCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCheckCommand.class);
    private final UserService userService;
    private String forward;
    private Role userRole;
    private HttpSession session;

    public LoginCheckCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        session = request.getSession();

        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        forward = Path.PAGE__ERROR_PAGE;

        if (!validateLoginPassword(login, password, request)){
            setError("Login/password cannot be empty", "error_logPass");
            return Path.PAGE__LOGIN;
        }

        User user = getUser(login);

        if (isBlockedUser(user)){
            setError("Sorry, your account is blocked", "error_blocked");
            return Path.PAGE__LOGIN;
        }

        request.setAttribute("user", user);
        log.trace("Found in DB: user --> " + user);

        if (!password.equals(user.getPassword())) {
            log.error("errorMessage --> " + "Cannot find user with such login/password");
            return Path.REDIRECT__INDEX;
        } else {
            ifAdminToCabinet(user);

            String previousParameters = getPreviousParameters(request);
            ifClientToCabinet(previousParameters);

            setUserToSession(user);
            setLocaleToSession(user);
        }

        log.debug("Command finished");
        return forward;
    }

    private void setError(String errorMessage, String attributeName) {
        session.setAttribute(attributeName, errorMessage);
        log.error("errorMessage --> " + errorMessage);
    }

    private void resetErrorMassages(HttpSession session) {
        Enumeration attributes = session.getAttributeNames();
        while(attributes.hasMoreElements()){
            Object o = attributes.nextElement();
            if(o instanceof String){
                if(((String)(((String) o).split("_"))[0]).equals("error")){
                    session.removeAttribute((String)o);
                }
            }
        }
    }

    private boolean isBlockedUser(User user) {
        if (user.getBlocked() == 1) {
            log.trace("Blocked user with login: " + user.getLogin() + " trying to login");
            return true;
        }
        return false;
    }

    private User getUser(String login) throws CommandException{
        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new CommandException("exception in getUser method at " + this.getClass().getSimpleName());
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void setLocaleToSession(User user) throws CommandException {
        String userLocaleName = user.getLocale();
        log.trace("userLocalName --> " + userLocaleName);

        try{
            if(Validator.isValid(userLocaleName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)){
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

                session.setAttribute("defaultLocale", userLocaleName);
                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

                log.info("Locale for user: defaultLocale --> " + userLocaleName);
            }
        } catch (ValidatorException e){
            throw createCommandException("setLocaleToSession", e);
        }
    }

    private void setUserToSession(User user) {
        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);

        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
    }

    private void ifClientToCabinet(String previousParameters) {
        if (userRole == Role.CLIENT) {
            if (session.getAttribute("magazineId") != null) {
                forward = Path.REDIRECT__INDEX + previousParameters;
            } else {
                forward = Path.FORWARD__INDEX;
            }
        }
    }

    private void ifAdminToCabinet(User user) {
        userRole = Role.getRole(user);
        log.trace("userRole --> " + userRole);

        if (userRole == Role.ADMIN) {
            forward = Path.REDIRECT__ADMIN_CABINET;
        }
    }

    private String getPreviousParameters(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        if (request.getSession().getAttribute("pre_sort") != null) {
            sb.append("?sort=");
            sb.append(request.getSession().getAttribute("pre_sort"));
        }
        if (request.getSession().getAttribute("pre_filter") != null) {
            sb.append("&filter=");
            sb.append(request.getSession().getAttribute("pre_filter"));
        }
        if (request.getSession().getAttribute("pre_page") != null) {
            sb.append("&page=");
            sb.append(request.getSession().getAttribute("pre_page"));
        }
        return sb.toString();
    }

    private boolean validateLoginPassword(String login, String password, HttpServletRequest request) throws CommandException {
        try {
            if (Validator.isValid(login, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY) &&
                    Validator.isValid(password, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)) {
                return true;
            }
        } catch (ValidatorException e) {
            throw createCommandException("execute", e);
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