package com.training.periodical.controller.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.training.periodical.entity.User;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;

/**
 * Security filter
 */
public class CommandAccessFilter implements Filter, Serializable {
    private static final long serialVersionUID = -5440261993963302752L;
    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

    // commands access
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (accessAllowed(request)) {
            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            log.error("Error in do filter method at CommandAccessFilter");
            setPreviousParameters(httpRequest);
            httpResponse.sendRedirect(Path.REDIRECT__LOGIN.replace("redirect:", ""));
        }
    }

    private void setPreviousParameters(HttpServletRequest request){
        if(request.getParameterValues("magazineId") != null){
            List<String> list = new ArrayList<>(Arrays.asList(request.getParameterValues("magazineId")));
            request.getSession().setAttribute("magazineId", list);
        }
        if(request.getParameter("pre_sort") != null){
            request.getSession().setAttribute("pre_sort", request.getParameter("pre_sort"));
        }
        if(request.getParameter("pre_filter") != null){
            request.getSession().setAttribute("pre_filter", request.getParameter("pre_filter"));
        }
        if(request.getParameter("pre_page") != null){
            request.getSession().setAttribute("pre_page", request.getParameter("pre_page"));
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

//        // todo Just for DEVELOPMENT purposes, remove on prod

//        try {
//            Optional<User> user = (AbstractDaoFactory.getInstance().createUserDao()).findUserByLogin("admin");
//            ((HttpServletRequest) request).getSession().setAttribute("user", user.get());
//            ((HttpServletRequest) request).getSession().setAttribute("userRole", Role.ADMIN);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

//        try {
//            Optional<User> user = (AbstractDaoFactory.getInstance().createUserDao()).findUserByLogin("петров");
//            ((HttpServletRequest) request).getSession().setAttribute("user", user.get());
//            ((HttpServletRequest) request).getSession().setAttribute("userRole", Role.CLIENT);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

//        // todo remove on prod

        String commandName = ((HttpServletRequest) request).getServletPath().replace("/", "");

        AbstractDaoFactory.getInstance();

        try {
            if (!Validator.isValid(commandName, Validator.Check.NOT_NULL, Validator.Check.NOT_EMPTY)){
                return false;
            }
        } catch (ValidatorException e) {
            log.error(e);
        }

//        if (commandName == null || commandName.isEmpty())
//        if (commandName.isEmpty())
//            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.CLIENT, asList(fConfig.getInitParameter("client")));
        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        log.trace("Out of control commands --> " + outOfControl);

        log.debug("Filter initialization finished");
    }

    /**
     * Extracts parameter values from string.
     *
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

}