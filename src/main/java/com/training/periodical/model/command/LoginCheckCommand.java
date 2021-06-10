package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.encoder.Encoder;
import com.training.periodical.util.encoder.EncoderException;
import com.training.periodical.util.validator.Valid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

/**
 * Login check command.
 *
 * Validate login/password
 * Form error messages if needed
 * Logs user in
 */
public class LoginCheckCommand extends AbstractCommand {
    private static final long serialVersionUID = 7629970801615724477L;
    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;
    private String forward;

    public LoginCheckCommand(UserRepository userRepository, MagazineRepository magazineRepository) {
        this.userRepository = userRepository;
        this.magazineRepository = magazineRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        this.request = request;

        resetErrorMassages();

        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        forward = Path.PAGE__ERROR_PAGE;

        if (!validateLoginPassword(login, password)) {
            setError("Login/password cannot be empty", "error_logPass");
            return Path.PAGE__LOGIN;
        }

        User user = findUserByLogin(login);

        if (isBlockedUser(user)) {
            setError("Sorry, your account is blocked", "error_blocked");
            return Path.PAGE__LOGIN;
        }

        request.setAttribute("user", user);
        log.trace("Found in DB: user --> " + user);

        try {
            if (!Encoder.encrypt(password).equals(user.getPassword())) {
                log.error("errorMessage --> " + "Cannot find user with such login/password");
                return Path.REDIRECT__INDEX;
            } else {
                Role userRole = getUserRole(user);
                toCabinet(userRole, user.getId());

                setUserToSession(user, userRole);
                setLocaleToSession(user);
            }
        } catch (EncoderException e) {
            throw createCommandException("execute", e);
        }

        log.debug("Command finished");
        return forward;
    }


    private void resetErrorMassages() {
        Enumeration<String> attributes = request.getSession().getAttributeNames();
        while (attributes.hasMoreElements()) {
            String o = attributes.nextElement();
            if (((o.split("_"))[0]).equals("error")) {
                request.getSession().removeAttribute(o);
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

    private User findUserByLogin(String login) throws CommandException {
        try {
            Optional<User> optionalUser = userRepository.findUserByLogin(login);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw createCommandException("findUserByLogin");
            }
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private void setLocaleToSession(User user) {
        String userLocaleName = user.getLocale();
        log.trace("userLocalName --> " + userLocaleName);

        if (Valid.notNullNotEmpty(userLocaleName)) {
            Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

            request.getSession().setAttribute("defaultLocale", userLocaleName);
            log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

            log.info("Locale for user: defaultLocale --> " + userLocaleName);
        }
    }

    private void setUserToSession(User user, Role userRole) {
        request.getSession().setAttribute("user", user);
        log.trace("Set the session attribute: user --> " + user);

        request.getSession().setAttribute("userRole", userRole);
        log.trace("Set the session attribute: userRole --> " + userRole);

        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
    }

    private Role getUserRole(User user) {
        Role role = Role.getRole(user);
        log.trace("userRole --> " + role);
        return role;
    }

    private void toCabinet(Role role, long userId) throws CommandException {
        if (role == Role.ADMIN) {
            forward = Path.REDIRECT__ADMIN_CABINET;
        }
        if (role == Role.CLIENT) {
            if(!Valid.isMagazineIdEmpty(request) && isEnoughMoney(userId)){
                forward = Path.FORWARD__SUBSCRIPTION;
            } else {
                forward = Path.REDIRECT__USER_CABINET;
            }
        }
    }


    private boolean validateLoginPassword(String login, String password) {
        return Valid.notNullNotEmpty(login) && Valid.notNullNotEmpty(password);
    }

    private boolean isEnoughMoney(long userId) throws CommandException {
        BigDecimal userBalance = null;
        BigDecimal sumPriceMagazines = null;
        try {
            sumPriceMagazines = getSumPriceMagazines(getMagazineIds());
            if (sumPriceMagazines == null) return false;
            userBalance = getUserBalance(userId);
            if (userBalance == null) return false;
        } catch (RepositoryException e) {
            throw createCommandException("isEnoughMoney", e);
        }

        return userBalance.compareTo(sumPriceMagazines) >= 0;
    }

    private BigDecimal getSumPriceMagazines(List<String> magazineIds) throws RepositoryException {
        return magazineRepository.findSumPriceByIds(magazineIds);
    }

    private BigDecimal getUserBalance(long userId) throws RepositoryException {
        BigDecimal userBalance;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userBalance = user.getBalance();
        } else {
            log.debug("Command finished");
            return null;
        }
        return userBalance;
    }
}