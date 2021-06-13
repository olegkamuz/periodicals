package com.training.periodical.model.command;

import com.training.periodical.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Show login page with login form for clint or admin
 *
 */
public class LoginCommand extends AbstractCommand {
    private static final long serialVersionUID = -507636500922845647L;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Login command starts");
        this.request = request;

        updateLocaleIfRequested();

        log.debug("Login command finished");
        return Path.PAGE__LOGIN;

    }
}