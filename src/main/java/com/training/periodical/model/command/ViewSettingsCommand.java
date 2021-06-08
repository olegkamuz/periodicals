package com.training.periodical.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.periodical.Path;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

/**
 * View settings command.
 */
public class ViewSettingsCommand extends AbstractCommand {
    private static final long serialVersionUID = -8183706805970306137L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        log.debug("Command starts");
        this.request = request;

        updateLocaleIfRequested(request.getParameter("localeToSet"));

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }
}