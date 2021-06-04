package com.training.periodical.controller.command;

import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Create Subscription.
 */
public class SubscriptionCommand implements Command {
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final MagazineService magazineService;

    private BigDecimal sumPriceMagazines;
    private BigDecimal userBalance;

    public SubscriptionCommand(SubscriptionService subscriptionService, UserService userService, MagazineService magazineService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
        this.magazineService = magazineService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        String[] magazineIds = request.getParameterMap().get("magazineId");

        if (magazineIds == null || magazineIds.length == 0) {
            log.debug("Command finished");
            return Path.REDIRECT__INDEX;
        }

        try {
            if (isEnoughMoney(String.valueOf(userId), magazineIds)) subscriptionService.
                    createSubscriptionPurchase(userId, magazineIds, getSubtractedBalance());
        } catch (ServiceException e) {
            throw new CommandException("exception in execute method at " +
                    this.getClass().getSimpleName(), e);
        }

        cleanSessionSubscriptionList(request);

        log.debug("Command finished");
        return Path.REDIRECT__USER_CABINET;
    }

    private void cleanSessionSubscriptionList(HttpServletRequest request) {
        if (request.getSession().getAttribute("subscriptionList") != null){
            request.getSession().removeAttribute("subscriptionList");
        }
    }

    private BigDecimal getSubtractedBalance() {
        if (userBalance == null) {
            return null;
        }
        return userBalance = userBalance.subtract(sumPriceMagazines);
    }

    private boolean isEnoughMoney(String userId, String[] magazineIds) throws CommandException {
        try {
            sumPriceMagazines = getSumPriceMagazines(magazineIds);
            if (sumPriceMagazines == null) return false;

            userBalance = getUserBalance(userId);
            if (userBalance == null) return false;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return userBalance.compareTo(sumPriceMagazines) >= 0;
    }

    private BigDecimal getSumPriceMagazines(String[] magazineIds) throws ServiceException {
        return magazineService.findSumPriceByIds(magazineIds);
    }

    private BigDecimal getUserBalance(String userId) throws ServiceException {
        BigDecimal userBalance;
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userBalance = user.getBalance();
        } else {
            log.debug("Command finished");
            return null;
        }
        return userBalance;
    }
}