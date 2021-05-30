package com.training.periodical.controller.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.periodical.Path;
import com.training.periodical.controller.command.CommandContainer;
import com.training.periodical.controller.command.CommandException;
import org.apache.log4j.Logger;

/**
 * Main servlet controller.
 */
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(Servlet.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws IOException, ServletException {
        log.debug("Controller starts");

        String commandName = request.getServletPath().replace("/", "");
        log.trace("Request parameter: command --> " + commandName);

        String result = "";
        try {
            result = CommandContainer.get(commandName).execute(request, response);
        } catch (CommandException e) {
            result = Path.PAGE__ERROR_PAGE;
        }
        log.trace("Forward address --> " + result);

        log.debug("Controller finished, now go to address --> " + result);

        dispatch(request, response, result);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String result)
            throws IOException, ServletException {
        if (result.contains("redirect:")) {
            response.sendRedirect(result.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(result).forward(request, response);
        }
    }


}











