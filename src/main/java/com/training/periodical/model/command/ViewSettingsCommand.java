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
public class ViewSettingsCommand implements Command {
    private static final long serialVersionUID = -8183706805970306137L;
    private static final Logger log = Logger.getLogger(ViewSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        log.debug("Command starts");

        log.debug("Command finished");
        return Path.PAGE__SETTINGS;
    }

    @Override
    public CommandException createCommandException(String methodName, RepositoryException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }

    @Override
    public CommandException createCommandException(String methodName, ValidatorException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}