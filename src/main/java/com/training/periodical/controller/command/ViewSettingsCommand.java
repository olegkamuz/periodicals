package com.training.periodical.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.periodical.Path;
import org.apache.log4j.Logger;

/**
 * View settings command.
 */
public class ViewSettingsCommand implements Command {

    private static final Logger log = Logger.getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        log.debug("Command starts");

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

}