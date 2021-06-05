package com.training.periodical.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.training.periodical.Path;
import com.training.periodical.model.service.ServiceException;
import org.apache.log4j.Logger;

/**
 * Logout command.
 *
 */
public class LogoutCommand implements Command {
    private static final long serialVersionUID = -2785976616686657267L;
    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        log.debug("Command finished");
        return Path.PAGE__LOGIN;
    }

    @Override
    public CommandException createCommandException(String methodName, ServiceException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}