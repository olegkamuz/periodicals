package com.training.periodical.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.periodical.Path;

/**
 * View settings command.
 */
public class SettingsCommand extends AbstractCommand {
    private static final long serialVersionUID = -8183706805970306137L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        log.debug("Command starts");
        this.request = request;

        updateLocaleIfRequested();

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }
}