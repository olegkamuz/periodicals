package com.training.periodical.controller.command;

import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.model.dao.DBManager;
import com.training.periodical.model.dao.MagazineDao;
import com.training.periodical.model.dao.SubscriptionDao;
import com.training.periodical.model.dao.UserDao;
import com.training.periodical.entity.User;
import com.training.periodical.model.service.SubscriptionService;
import com.training.periodical.model.service.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Create Subscription.
 */
public class SubscriptionCommand extends Command {
    private static final long serialVersionUID = 7732286214029478505L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
    private UserService userService;
    private SubscriptionService subscriptionService;
    private TransactionManager transactionManager;

    public SubscriptionCommand(UserService userService, SubscriptionService subscriptionService, TransactionManager transactionManager) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.transactionManager = transactionManager;
    }


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        if (request.getSession().getAttribute("user") instanceof User) {
            Long userId = ((User) request.getSession().getAttribute("user")).getId();
            String[] magazineIds = request.getParameterMap().get("magazineId");

            if (magazineIds != null && magazineIds.length > 0) {
                List<Long> magazineIdsLong = Arrays.stream(magazineIds).map(Long::parseLong).collect(Collectors.toList());
                BigDecimal sumPriceMagazines = magazineIdsLong.stream()
                        .map(id -> (new MagazineDao())
                                .findMagazineById(id)
                                .getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);


                Connection con = DBManager.getInstance().getConnection();

                BigDecimal userBalance = null;
                Optional<User> optionalUser = null;
                try {
                    optionalUser = (new UserService(new UserDao(con))).findUserById(userId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userBalance = user.getBalance();
                }

                if (userBalance.compareTo(sumPriceMagazines) >= 0) {
                    userBalance = userBalance.subtract(sumPriceMagazines);

                    subscriptionService.createSubscriptionPurchase(userId, magazineIdsLong,userBalance, transactionManager);

//                    (new SubscriptionService(new UserDao(con), new SubscriptionDao(con), new TransactionManager(con))).createSubscriptionPurchase(userId, magazineIdsLong, userBalance);
                }
            }
        }

        log.debug("Command finished");
        return Path.REDIRECT__INDEX;
    }

}