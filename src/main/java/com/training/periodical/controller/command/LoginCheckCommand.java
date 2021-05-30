package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Optional;

/**
 * Login check command.
 */
public class LoginCheckCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCheckCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = null;
        try {
            Optional<User> optionalUser = (new UserService()).findUserByLogin(login);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                throw new CommandException("exception in execute method at " + this.getClass().getSimpleName());
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("user", user);
        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.ADMIN) {
                forward = Path.REDIRECT__INDEX;
            }

            if (userRole == Role.CLIENT) {
                if (session.getAttribute("theme") != null && session.getAttribute("magazineId") != null) {
                    forward = Path.REDIRECT__LIST_MAGAZINES_BY_ONE_THEME;
                } else {
                    forward = Path.FORWARD__INDEX;
                }
            }

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);

            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

            // work with i18n
            String userLocaleName = user.getLocale();
            log.trace("userLocalName --> " + userLocaleName);

            if (userLocaleName != null && !userLocaleName.isEmpty()) {
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

                session.setAttribute("defaultLocale", userLocaleName);
                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

                log.info("Locale for user: defaultLocale --> " + userLocaleName);
            }
        }

        log.debug("Command finished");
        return forward;
    }

}