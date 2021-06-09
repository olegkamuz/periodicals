package com.training.periodical.model.command;

import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.SubscriptionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create Subscription.
 */
public class SubscriptionCommand extends AbstractCommand {
    private static final long serialVersionUID = 4085515554426545356L;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;

    private BigDecimal sumPriceMagazines;
    private BigDecimal userBalance;

    public SubscriptionCommand(SubscriptionRepository subscriptionRepository, UserRepository userRepository, MagazineRepository magazineRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.magazineRepository = magazineRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        this.request = request;

        Long userId = ((User) request.getSession().getAttribute("user")).getId();

        List<String> magazineIds = getMagazineIds();

        magazineIds = checkForAlreadyAdded(subscriptionRepository, userId, magazineIds);

        if (magazineIds.size() == 0) {
            log.debug("Command finished");
            return Path.REDIRECT__USER_CABINET;
        }

        try {
            if (isEnoughMoney(userId, magazineIds)) {
                subscriptionRepository.createSubscriptionPurchase(userId, magazineIds, getSubtractedBalance());
                cleanSessionMagazineId();
            }
        } catch (RepositoryException e) {
            throw new CommandException("exception in execute method at " +
                    this.getClass().getSimpleName(), e);
        }

        refreshCacheSubscriptions();

        log.debug("Command finished");
        return Path.REDIRECT__USER_CABINET;
    }

    private List<String> checkForAlreadyAdded(SubscriptionRepository subscriptionService, Long userId, List<String> magazineIds) throws CommandException {
        try {
            List<String> temp = new ArrayList<>();
            for (String magId : magazineIds) {
                if (!(subscriptionService.countByCompositeKey(userId, Long.parseLong(magId)) > 0)) {
                    temp.add(magId);
                }
            }
            return temp;
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }


    private void refreshCacheSubscriptions() {
        if (request.getSession().getAttribute("subscriptionList") != null) {
            request.getSession().removeAttribute("subscriptionList");
        }
    }

    private void cleanSessionMagazineId() {
        if (request.getSession().getAttribute("magazineId") != null) {
            request.getSession().removeAttribute("magazineId");
        }
    }

    private BigDecimal getSubtractedBalance() {
        if (userBalance == null) {
            return null;
        }
        return userBalance = userBalance.subtract(sumPriceMagazines);
    }

    private boolean isEnoughMoney(long userId, List<String> magazineIds) throws CommandException {
        try {
            sumPriceMagazines = getSumPriceMagazines(magazineIds);
            if (sumPriceMagazines == null) return false;

            userBalance = getUserBalance(userId);
            if (userBalance == null) return false;
        } catch (RepositoryException e) {
            throw createCommandException("isEnoughMoney", e);
        }

        return userBalance.compareTo(sumPriceMagazines) >= 0;
    }

    private BigDecimal getSumPriceMagazines(List<String> magazineIds) throws RepositoryException {
        return magazineRepository.findSumPriceByIds(magazineIds);
    }

    private BigDecimal getUserBalance(long userId) throws RepositoryException {
        BigDecimal userBalance;
        Optional<User> optionalUser = userRepository.findById(userId);
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