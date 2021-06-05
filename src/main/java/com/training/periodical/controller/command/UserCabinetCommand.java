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
import java.util.Optional;

public class UserCabinetCommand implements Command {
    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final UserSubscriptionService UserSubService;
    private final UserService userService;

    public UserCabinetCommand(UserSubscriptionService usService, UserService userService) {
        this.UserSubService = usService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("User cabinet command starts");

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        User user = getUser(userId);
        showBalance(userId, request);

        String replenish = request.getParameter("replenish");

        if (replenish != null && !replenish.equals("")) {
            try {
                BigDecimal newBalance = (new BigDecimal(replenish))
                        .add(user.getBalance());
                userService.updateBalance(newBalance, user.getId());
                showBalance(userId, request);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        if (!isAttributeSet(request, "subscriptionList")) {
            setSessionSubscriptionList(request);
        }

        log.info("User cabinet command finish");
        return Path.PAGE__USER_CABINET;
    }

    private void showBalance(long userId, HttpServletRequest request) {
        try {
            BigDecimal balanceFromDB = getUser(userId).getBalance();
            ((User) request.getSession().getAttribute("user")).setBalance(balanceFromDB);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    private User getUser(long userId) throws CommandException{
        User user;
        try {
            Optional<User> optionalUser = userService.findById(userId);
            if(optionalUser.isPresent()){
                user = optionalUser.get();
            } else {
                throw new CommandException("exception in getUser" +
                         UserCabinetCommand.class.getSimpleName());
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return user;
    }

    private void setSessionSubscriptionList(HttpServletRequest request) throws CommandException {
        List<UserSubscriptionBean> subscriptionList = null;
        try {
            subscriptionList = UserSubService.findSubscriptionByUserId(((User) request.getSession().getAttribute("user")).getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("subscriptionList", subscriptionList);
    }

    private boolean isAttributeSet(HttpServletRequest request, String attributeName) {
        return request.getSession().getAttribute(attributeName) != null;
    }
}
