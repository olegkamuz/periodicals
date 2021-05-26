package com.training.periodical.controller.command;

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

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        log.debug("Command finished");
        return Path.PAGE__LOGIN;

    }

}