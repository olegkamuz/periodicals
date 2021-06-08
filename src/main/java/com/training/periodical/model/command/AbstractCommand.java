package com.training.periodical.model.command;

import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.Repository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.util.Valid;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCommand implements Command {
    private static final long serialVersionUID = 22379153150765607L;
    final Logger log = Logger.getLogger(getClass());
    final int PAGE_SIZE = 5;
    HttpServletRequest request;

    void updateLocaleIfRequested(String localeToSet) throws CommandException {
        try {
            if (Valid.notNullNotEmpty(localeToSet)) {
                HttpSession session = request.getSession();
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
                session.setAttribute("defaultLocale", localeToSet);
            }
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
    }

    List<Magazine> getMagazinesPage(Repository repository, int currentPage) {
        List<Magazine> page = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return repository.findPage(PAGE_SIZE, offset);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return page;
    }

    String getPreviousParameters() {
        StringBuilder sb = new StringBuilder();
        if (request.getSession().getAttribute("pre_sort") != null) {
            sb.append("?sort=");
            sb.append(request.getSession().getAttribute("pre_sort"));
        }
        if (request.getSession().getAttribute("pre_filter") != null) {
            sb.append("&filter=");
            sb.append(request.getSession().getAttribute("pre_filter"));
        }
        if (request.getSession().getAttribute("pre_page") != null) {
            sb.append("&page=");
            sb.append(request.getSession().getAttribute("pre_page"));
        }
        return sb.toString();
    }

    void setError(String errorMessage, String attributeName) {
        request.getSession().setAttribute(attributeName, errorMessage);
        log.error("errorMessage --> " + errorMessage);
    }

    String getLogin() {
        return request.getParameter("login");
    }

    String getPassword() {
        return request.getParameter("password");
    }

    String getFirstName() {
        return request.getParameter("first-name");
    }

    String getLastName() {
        return request.getParameter("last-name");
    }

    @Override
    public CommandException createCommandException(String methodName, RepositoryException e) {
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

    @Override
    public CommandException createCommandException(String methodName, ValidatorException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                getClass().getSimpleName(), e);
    }

    String getSort() {
        String sort = "";
        if (request.getSession().getAttribute("pre_sub_sort") != null) {
            sort = (String) request.getSession().getAttribute("pre_sub_sort");
            request.getSession().removeAttribute("pre_sub_sort");
        } else {
            if (request.getSession().getAttribute("pre_sort") != null) {
                sort = (String) request.getSession().getAttribute("pre_sort");
                request.getSession().removeAttribute("pre_sort");
            } else {
                sort = request.getParameter("sort");
            }
        }
        return sort;
    }

    String getFilter() {
        String filter = "";
        if (request.getSession().getAttribute("pre_sub_filter") != null) {
            filter = (String) request.getSession().getAttribute("pre_sub_filter");
            request.getSession().removeAttribute("pre_sub_filter");
        } else {
            if (request.getSession().getAttribute("pre_filter") != null) {
                filter = (String) request.getSession().getAttribute("pre_filter");
                request.getSession().removeAttribute("pre_filter");
            } else {
                filter = request.getParameter("filter");
            }
        }
        return filter;
    }

    String getPage() {
        String page = "";
        if (request.getSession().getAttribute("pre_sub_page") != null) {
            page = (String) request.getSession().getAttribute("pre_sub_page");
            request.getSession().removeAttribute("pre_sub_page");
        } else {
            if (request.getSession().getAttribute("pre_page") != null) {
                page = (String) request.getSession().getAttribute("pre_page");
                request.getSession().removeAttribute("pre_page");
            } else {
                page = request.getParameter("page");
            }
        }
        return page;
    }

    boolean isPageOutOfRange(int magazinesAmount, String page) throws CommandException {
        return !validatePage(page, getNumberOfPages(magazinesAmount));
    }

    private boolean validatePage(String data, int range_to)
            throws CommandException {
        try {
            if (Valid.notNullNotEmptyCastToIntInRange(data, range_to))
                return true;
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }

    int getNumberOfPages(int magazineAmount) {
        int numberOfPages;
        numberOfPages = magazineAmount / PAGE_SIZE;
        return magazineAmount % PAGE_SIZE == 0 ? numberOfPages : ++numberOfPages;
    }
}
