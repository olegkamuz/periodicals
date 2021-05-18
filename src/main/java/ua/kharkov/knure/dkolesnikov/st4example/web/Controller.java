package ua.kharkov.knure.dkolesnikov.st4example.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkov.knure.dkolesnikov.st4example.web.command.Command;
import ua.kharkov.knure.dkolesnikov.st4example.web.command.CommandContainer;

/**
 * Main servlet controller.
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(Controller.class);

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

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        String input = ((HttpServletRequest) request).getRequestURL().toString();
        String regex = "(?<=\\/)([\\-\\w\\s]+)(?=.jsp)";
        Pattern pi = Pattern.compile(regex);
        Matcher mi = pi.matcher(input);
        String res = "";
        while (mi.find()) {
            res = mi.group();
        }

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        String result = command.execute(request, response);
        log.trace("Forward address --> " + result);

        log.debug("Controller finished, now go to forward address --> " + result);

        // if the forward address is not null go to the address
//		if (result != null) {
//			RequestDispatcher disp = request.getRequestDispatcher(result);
//			disp.forward(request, response);
//		}

        if (result != null) {
            if (result.contains("redirect:")) {
                response.sendRedirect(result.replace("redirect:", "/FP_war"));
            }
        } else {
            request.getRequestDispatcher(result).forward(request, response);
        }
    }

}