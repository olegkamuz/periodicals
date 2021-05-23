package com.training.periodical.controller.command;

import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login command.
 *
 * @author D.Kolesnikov
 */
public class LoginSessionCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginSessionCommand.class);

    /**
    * Check by session if user already logged in
     * if not forward to login page
     * if logged forward to role appropriate page
     */
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        HttpSession session = request.getSession();

        Object userSession = session.getAttribute("user");
        String forward = "";
        if (userSession instanceof User) {
            if (((User) userSession).getRoleId() == 0) {
                forward = Path.COMMAND__LIST_ORDERS;
            }

            if (((User) userSession).getRoleId() == 1)
                forward = Path.COMMAND__LIST_MENU;
        } else {
            forward = Path.PAGE__LOGIN;
        }

        log.debug("Command finished");
        return forward;
    }

}