package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminCabinetCommand implements Command {
    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final UserService userService;

    public AdminCabinetCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Admin cabinet command starts");

        String block = request.getParameter("change_block");
        String userId = request.getParameter("user_id");
        if (block != null && userId != null) {
            int changed = Integer.parseInt(block) == 0 ? 1 : 0;
            try {
                userService.update(userId, "blocked", String.valueOf(changed));
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        try {
            List<User> userList = userService.findAll();
            request.getSession().setAttribute("userList", userList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        log.debug("User cabinet command finish");
        return Path.PAGE__ADMIN_CABINET;
    }
}
