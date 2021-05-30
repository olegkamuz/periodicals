package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import com.training.periodical.model.service.UserSubscriptionService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserCabinetCommand implements Command {
    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final UserSubscriptionService USService;

    public UserCabinetCommand(UserSubscriptionService usService) {
        this.USService = usService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.debug("User cabinet command starts");

        List<UserSubscriptionBean> subscriptionList = null;
        try {
            subscriptionList = USService.findSubscriptionByUserId(((User)request.getSession().getAttribute("user")).getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("subscriptionList", subscriptionList);
        request.getSession().setAttribute("userLogin", ((User)request.getSession().getAttribute("user")).getLogin());
        request.getSession().setAttribute("userBalance", String.valueOf(
                ((User)request.getSession().getAttribute("user")).getBalance())
        );


        log.debug("User cabinet command finish");
        return Path.PAGE__USER_CABINET;
    }
}
