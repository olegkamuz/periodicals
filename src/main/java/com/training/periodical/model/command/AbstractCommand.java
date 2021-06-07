package com.training.periodical.model.command;

import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public abstract class AbstractCommand implements Command{
    private static final long serialVersionUID = 22379153150765607L;

    protected void updateLocaleIfRequested(String localeToSet, HttpServletRequest request) throws CommandException {
        try {
            if(Validator.isValid(localeToSet, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)) {
                HttpSession session = request.getSession();
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
                session.setAttribute("defaultLocale", localeToSet);
            }
        } catch (ValidatorException e) {
            throw createCommandException("updateLocaleIfRequested", e);
        }
    }
}
