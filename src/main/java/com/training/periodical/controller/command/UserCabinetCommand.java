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
import java.math.BigDecimal;
import java.util.List;

public class UserCabinetCommand implements Command {
    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final UserSubscriptionService USService;
    private final UserService userService;

    public UserCabinetCommand(UserSubscriptionService usService, UserService userService) {
        this.USService = usService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.debug("User cabinet command starts");

        String replenish = request.getParameter("replenish");
        String userId = request.getParameter("user-id");

        if (replenish != null && !replenish.equals("") &&
                userId != null && !userId.equals("")) {
            try {
                BigDecimal newBalance = (new BigDecimal(replenish))
                        .add(((User) request.getSession().getAttribute("user")).getBalance());
                userService.updateBalance(userId, String.valueOf(newBalance));
                ((User) request.getSession().getAttribute("user")).setBalance(newBalance);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        if (!isAttributeSet(request, "subscriptionList")) {
            setSessionSubscriptionList(request);
        }

        log.debug("User cabinet command finish");
        return Path.PAGE__USER_CABINET;
    }

    private void setSessionSubscriptionList(HttpServletRequest request) throws CommandException {
        List<UserSubscriptionBean> subscriptionList = null;
        try {
            subscriptionList = USService.findSubscriptionByUserId(((User) request.getSession().getAttribute("user")).getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("subscriptionList", subscriptionList);
    }

    private boolean isAttributeSet(HttpServletRequest request, String attributeName) {
        return request.getSession().getAttribute(attributeName) != null;
    }
}
