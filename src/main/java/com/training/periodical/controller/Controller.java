package com.training.periodical.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.periodical.Path;
import com.training.periodical.controller.command.Command;
import com.training.periodical.controller.command.CommandException;
import com.training.periodical.controller.command.CommandFactory;
import org.apache.log4j.Logger;

import com.training.periodical.controller.command.CommandContainer;

/**
 * Main servlet controller.
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(Controller.class);

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
        try (CommandFactory commandFactory = new CommandFactory()) {
            Command action = commandFactory.create(commandName);
            result = action.execute(request, response);
        } catch (CommandException e) {
            result = Path.PAGE__ERROR_PAGE;
        }

        log.trace("Forward address --> " + result);

        log.debug("Controller finished, now go to address --> " + result);

        dispatch(request, response, result);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String result) throws IOException, ServletException {
        if (result.contains("redirect:")) {
            response.sendRedirect(result.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(result).forward(request, response);
        }
    }


}