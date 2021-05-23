package com.training.periodical.controller;

import com.training.periodical.controller.command.Command;
import org.apache.log4j.Logger;
import com.training.periodical.controller.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(IndexController.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        log.debug("Index controller starts");

        // obtain command object by its name
        Command command = CommandContainer.get("listByCategoryMenu");
        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        String result = command.execute(request, response);
        log.trace("Forward address --> " + result);

        log.debug("Controller finished, now go to forward address --> " + result);

        if (result != null && result.contains("redirect:")) {
            response.sendRedirect(result.replace("redirect:", "/FP_war"));
        } else {
            request.getRequestDispatcher(result).forward(request, response);
        }
    }

}