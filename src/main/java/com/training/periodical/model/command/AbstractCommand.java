package com.training.periodical.model.command;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.Repository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.util.validator.Valid;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractCommand implements Command {
    private static final long serialVersionUID = 22379153150765607L;
    final Logger log = Logger.getLogger(getClass());
    final int PAGE_SIZE = 5;
    HttpServletRequest request;

    void updateLocaleIfRequested() {
        request.getSession().removeAttribute("error_not_valid_symbols_locale_format");
        String localeToSet = request.getParameter("localeToSet");
        if (Valid.notNullNotEmpty(localeToSet)) {
            if(Valid.isValidLocale(localeToSet)){
                Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
            } else {
                setError("Only valid symbols", "error_not_valid_symbols_locale_format");
            }

        }
    }


    void setError(String errorMessage, String attributeName) {
        request.getSession().setAttribute(attributeName, errorMessage);
        log.error("errorMessage --> " + errorMessage);
    }

    User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    String getLogin() {
        return request.getParameter("login");
    }

    String getPassword() {
        return request.getParameter("password");
    }

    String getFirstName() {
        return request.getParameter("firstName");
    }

    String getLastName() {
        return request.getParameter("lastName");
    }

    @Override
    public CommandException createCommandException(String methodName, Exception e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName(), e);
    }


    @Override
    public CommandException createCommandException(String methodName) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName());
    }

    boolean isPageOutOfRange(int magazinesAmount, String page) {
        return !validatePage(page, getNumberOfPages(magazinesAmount));
    }

    private boolean validatePage(String data, int range_to) {
        return Valid.notNullNotEmptyCastToIntInRange(range_to, data);
    }

    int getNumberOfPages(int magazineAmount) {
        int numberOfPages;
        numberOfPages = magazineAmount / PAGE_SIZE;
        return magazineAmount % PAGE_SIZE == 0 ? numberOfPages : ++numberOfPages;
    }

    List<String> getMagazineIds() {
        List<String> magazineIds = new ArrayList<>();
        Object obj = request.getSession().getAttribute("magazineId");
        if (obj instanceof List) {
            for (Object ob : (List) obj) {
                if (ob instanceof String) {
                    magazineIds.add((String) ob);
                }
            }
        }
        magazineIds = removePossibleMagIdsDuplication(magazineIds);
        return magazineIds;
    }

    private List<String> removePossibleMagIdsDuplication(List<String> magazineIds) {
        List<String> magId = new ArrayList<>();
        if (!magazineIds.isEmpty()) {
            magId = new ArrayList<>(
                    new HashSet<>(magazineIds));
        }
        return magId;
    }
}
