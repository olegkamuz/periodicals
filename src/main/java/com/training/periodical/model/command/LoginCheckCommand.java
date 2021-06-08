package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.Valid;
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
public class LoginCheckCommand extends AbstractCommand {

//    private static final Logger log = Logger.getLogger(LoginCheckCommand.class);
    private final UserRepository userRepository;
    private String forward;
    private HttpSession session;

    public LoginCheckCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");
        this.request= request;

        session = request.getSession();
        resetErrorMassages(session);

        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        forward = Path.PAGE__ERROR_PAGE;

        if (!validateLoginPassword(login, password)){
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
            Role userRole = getUserRole(user);
            String previousParameters = getPreviousParameters();
            toCabinet(userRole);

            setUserToSession(user, userRole);
            setLocaleToSession(user);
        }

        log.debug("Command finished");
        return forward;
    }


    private void resetErrorMassages(HttpSession session) {
        Enumeration<String> attributes = session.getAttributeNames();
        while(attributes.hasMoreElements()){
            String o = attributes.nextElement();
            if(((o.split("_"))[0]).equals("error")){
                session.removeAttribute(o);
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
            Optional<User> optionalUser = userRepository.findUserByLogin(login);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new CommandException("exception in getUser method at " + this.getClass().getSimpleName());
            }
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private void setLocaleToSession(User user) throws CommandException {
        String userLocaleName = user.getLocale();
        log.trace("userLocalName --> " + userLocaleName);

        try{
            if(Valid.notNullNotEmpty(userLocaleName)){
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

                session.setAttribute("defaultLocale", userLocaleName);
                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

                log.info("Locale for user: defaultLocale --> " + userLocaleName);
            }
        } catch (ValidatorException e){
            throw createCommandException("setLocaleToSession", e);
        }
    }

    private void setUserToSession(User user, Role userRole) {
        session.setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);

        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
    }

    private Role getUserRole(User user){
        Role role = Role.getRole(user);
        log.trace("userRole --> " + role);
        return role;
    }

    private void toCabinet(Role role) {
        if (role == Role.ADMIN) {
            forward = Path.REDIRECT__ADMIN_CABINET;
        }
        if (role == Role.CLIENT) {
            forward = Path.REDIRECT__USER_CABINET;
        }
    }


    private boolean validateLoginPassword(String login, String password) throws CommandException {
        try {
            if(Valid.notNullNotEmpty(login) && Valid.notNullNotEmpty(password)){
                return true;
            }
        } catch (ValidatorException e) {
            throw createCommandException("execute", e);
        }
        return false;
    }

}