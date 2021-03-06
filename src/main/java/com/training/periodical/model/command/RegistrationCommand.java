package com.training.periodical.model.command;

import com.training.periodical.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Show registration page with form to register
 *
 */
public class RegistrationCommand extends AbstractCommand {
    private static final long serialVersionUID = 188178176376167889L;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Registration command starts");
        this.request = request;

        updateLocaleIfRequested();

        log.info("Registration command finish");
        return Path.PAGE__REGISTRATION;
    }

}