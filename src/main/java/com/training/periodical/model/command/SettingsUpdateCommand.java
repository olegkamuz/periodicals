package com.training.periodical.model.command;

import com.training.periodical.model.builder.UserBuilder;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.validator.Valid;
import com.training.periodical.Path;
import com.training.periodical.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Update personal settings for clint or admin.
 *
 */
public class SettingsUpdateCommand extends AbstractCommand {
    private static final long serialVersionUID = 3553552307337644253L;
    private final UserRepository userRepository;
    private boolean error = false;
    private boolean updateUser = false;
    private User user;
    private String firstName;
    private String lastName;
    private String localeToSetUser;

    public SettingsUpdateCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        this.request = request;

        updateLocaleIfRequested();

        setUser(getUser());
        setFirstname(getFirstName());
        setLastName(getLastName());
        setLocaleToSetUser(getLocaleToSetUser());

        processFirstName();
        processLastName();
        processLocale();

        if (error) {
            return Path.PAGE__SETTINGS;
        }

        updateUser(updateUser);

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

    private void processFirstName() {
        if (Valid.notNullNotEmptyValidSymbols(firstName)) {
            request.getSession().removeAttribute("error_not_valid_symbols_first_name");
            user.setFirstName(Valid.escapeInput(firstName));
            updateUser = true;
        } else {
            setError("Only valid symbols", "error_not_valid_symbols_first_name");
            error = true;
        }
    }

    private void processLastName() {
        if (Valid.notNullNotEmptyValidSymbols(lastName)) {
            request.getSession().removeAttribute("error_not_valid_symbols_last_name");
            user.setLastName(Valid.escapeInput(lastName));
            updateUser = true;
        } else {
            setError("Only valid symbols", "error_not_valid_symbols_last_name");
            error = true;
        }
    }

    private void processLocale() {
        if (Valid.notNullNotEmptyValidLocale()) {
            request.getSession().removeAttribute("error_not_valid_symbols_locale_format");
            Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", localeToSetUser);
            request.getSession().setAttribute("defaultLocale", localeToSetUser);
            user.setLocale(localeToSetUser);
            updateUser = true;
        } else {
            setError("Only valid symbols", "error_not_valid_symbols_locale_user_format");
            error = true;
        }
    }

    private void updateUser(boolean updateUser) throws CommandException {
        if (updateUser) {
            try {
                userRepository.update(user);
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }
    }

    private void setLocaleToSetUser(String localeToSetUser) {
        this.localeToSetUser = localeToSetUser;
    }

    private void setUser(User user) {
        this.user = user;
    }

//    private boolean isValidateFirstName(boolean notNullNotEmpty, boolean validSymbol) {
//        if (Valid.notNullNotEmpty(firstName) && !Valid.isValidSymbols(firstName)) {
//            setError("Only valid symbols", "error_not_valid_symbols_first_name");
//            error = true;
//            return false;
//        } else {
//            request.getSession().removeAttribute("error_not_valid_symbols_first_name");
//            firstName = Valid.escapeInput(firstName);
//            return true;
//        }
//    }

//    private boolean isValidateLastName() {
//        if (Valid.notNullNotEmpty(lastName) && !Valid.isValidSymbols(lastName)) {
//            setError("Only valid symbols", "error_not_valid_symbols_last_name");
//            error = true;
//            return false;
//        } else {
//            request.getSession().removeAttribute("error_not_valid_symbols_last_name");
//            lastName = Valid.escapeInput(lastName);
//            return true;
//        }
//    }

    private String getLocaleToSetUser() {
        return request.getParameter("localeToSetUser");
    }


//    private boolean isValidLocale() {
//        if (Valid.notNullNotEmptyValidLocale(localeToSetUser) && !Valid.isValidLocale(localeToSetUser)) {
//            setError("Only valid symbols", "error_not_valid_symbols_language_format");
//            error = true;
//            return false;
//        } else {
//            request.getSession().removeAttribute("error_not_valid_symbols_language_format");
//            return true;
//        }
//    }

//    private boolean isUpdateFirstName() {
//        if (Valid.notNullNotEmpty(firstName)) {
//            user.setFirstName(firstName);
//            return true;
//        }
//        return false;
//    }

//    private boolean isUpdateLastName() {
//        if (Valid.notNullNotEmpty(lastName)) {
//            user.setLastName(lastName);
//            return true;
//        }
//        return false;
//    }

//    private boolean isUpdateLocale() {
//        if (Valid.notNullNotEmpty(localeToSetUser)) {
//            HttpSession session = request.getSession();
//            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSetUser);
//            session.setAttribute("defaultLocale", localeToSetUser);
//            user.setLocale(localeToSetUser);
//            return true;
//        }
//
//        return false;
//    }


    private void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }
}