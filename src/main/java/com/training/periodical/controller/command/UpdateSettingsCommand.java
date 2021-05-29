package com.training.periodical.controller.command;

import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.model.dao.UserDao;
import com.training.periodical.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Update settings items.
 */
public class UpdateSettingsCommand implements Command {


    private static final Logger log = Logger.getLogger(UpdateSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        // UPDATE USER ////////////////////////////////////////////////////////

        User user = (User) request.getSession().getAttribute("user");
        boolean updateUser = false;

        // update first name
        String firstName = request.getParameter("firstName");
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
            updateUser = true;
        }

        // update last name
        String lastName = request.getParameter("lastName");
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
            updateUser = true;
        }

        String localeToSet = request.getParameter("localeToSet");
        if (localeToSet != null && !localeToSet.isEmpty()) {
            HttpSession session = request.getSession();
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
            session.setAttribute("defaultLocale", localeToSet);
            user.setLocaleName(localeToSet);
            updateUser = true;
        }

//        if (updateUser == true)
//            new UserDao().updateUser(user);


        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

}