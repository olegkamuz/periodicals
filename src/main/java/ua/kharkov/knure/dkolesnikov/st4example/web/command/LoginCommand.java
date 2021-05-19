package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.Role;
import ua.kharkov.knure.dkolesnikov.st4example.db.UserDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Login command. Just to return page path;
 *
 */
public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        log.debug("Command finished");
        return Path.PAGE__LOGIN;

    }

}