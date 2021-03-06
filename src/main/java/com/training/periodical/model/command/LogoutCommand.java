package com.training.periodical.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.training.periodical.Path;

/**
 * Logout client and admin from application
 *
 */
public class LogoutCommand extends AbstractCommand {
    private static final long serialVersionUID = -2785976616686657267L;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");


        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        log.debug("Command finished");
        return Path.REDIRECT__INDEX;
    }
}