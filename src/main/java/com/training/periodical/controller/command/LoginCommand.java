package com.training.periodical.controller.command;

import com.training.periodical.model.service.ServiceException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Login command. Just to return page path;
 */
public class LoginCommand implements Command {
    private static final long serialVersionUID = -507636500922845647L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Login command starts");

        log.debug("Login command finished");
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