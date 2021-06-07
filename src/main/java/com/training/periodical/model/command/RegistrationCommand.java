package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand extends AbstractCommand {
    private static final long serialVersionUID = 188178176376167889L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Registration command starts");

        updateLocaleIfRequested(request.getParameter("localeToSet"), request);

        log.info("Registration command finish");
        return Path.PAGE__REGISTRATION;
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