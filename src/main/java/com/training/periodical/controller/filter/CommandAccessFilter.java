package com.training.periodical.controller.filter;

import java.io.IOException;
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
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import com.training.periodical.Path;
import com.training.periodical.model.dao.Role;

/**
 * Security filter
 */
public class CommandAccessFilter implements Filter {

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
        HttpServletResponse httpResponce = (HttpServletResponse) response;

        if (accessAllowed(request)) {
            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            setPreviousParameters(httpRequest);
            httpResponce.sendRedirect(Path.REDIRECT__LOGIN.replace("redirect:", ""));

//            if (response instanceof HttpServletResponse) {
//                if (request instanceof HttpServletRequest) {
//                    ((HttpServletRequest) request).getSession().setAttribute("magazineId", Arrays.asList(request.getParameterValues("magazineId")));
//                    ((HttpServletRequest) request).getSession().setAttribute("theme", request.getParameter("theme"));
//                }
//                ((HttpServletResponse) response).sendRedirect(Path.REDIRECT__LOGIN.replace("redirect:", ""));
//            } else {
//
//                String errorMessage = "You do not have permission to access the requested resource";
//
//                request.setAttribute("errorMessage", errorMessage);
//                log.trace("Set the request attribute: errorMessage --> " + errorMessage);
//
//                request.getRequestDispatcher(Path.PAGE__ERROR_PAGE)
//                        .forward(request, response);
//            }
        }
    }

    private void setPreviousParameters(HttpServletRequest request){
        if(request.getParameterValues("magazineId") != null){
            request.getSession().setAttribute("magazineId", Arrays.asList(request.getParameterValues("magazineId")));
        }
        if(request.getParameter("theme") != null){
            request.getSession().setAttribute("theme", request.getParameter("theme"));
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

//        String commandName = request.getParameter("command");
        String commandName;
        if (request instanceof HttpServletRequest) {
            commandName = ((HttpServletRequest) request).getServletPath().replace("/", "");
        } else {
            return false;
        }

        AbstractDaoFactory.getInstance();

//        if (commandName == null || commandName.isEmpty())
        if (commandName.isEmpty())
            return false;

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
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

}