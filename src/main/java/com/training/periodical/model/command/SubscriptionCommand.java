package com.training.periodical.model.command;

import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.SubscriptionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Create Subscription.
 */
public class SubscriptionCommand implements Command {
    private static final long serialVersionUID = 4085515554426545356L;
    private static final Logger log = Logger.getLogger(SubscriptionCommand.class);
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

        setPreviousParameters(request);

        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        List<String> magazineIds = new ArrayList<>();
        Object obj = request.getSession().getAttribute("magazineId");
        if (obj instanceof List) {
            for (Object ob : (List) obj) {
                if (ob instanceof String) {
                    magazineIds.add((String) ob);
                }
            }
        }

        magazineIds = removePossibleMagIdsDuplication(magazineIds);

        magazineIds = checkForAlreadyAdded(subscriptionRepository, userId, magazineIds);


        if (magazineIds.size() == 0) {
            log.debug("Command finished");
            return Path.REDIRECT__USER_CABINET;
        }

        try {
            if (isEnoughMoney(String.valueOf(userId), magazineIds)) {
                subscriptionRepository.createSubscriptionPurchase(userId, magazineIds, getSubtractedBalance());
                cleanSessionMagazineId(request);
            }
        } catch (RepositoryException e) {
            throw new CommandException("exception in execute method at " +
                    this.getClass().getSimpleName(), e);
        }

        cleanSessionSubscriptionList(request);

        log.debug("Command finished");
        return Path.REDIRECT__USER_CABINET;
    }

    private void setPreviousParameters(HttpServletRequest request) {
        if (request.getParameterValues("magazineId") != null) {
            List<String> list = new ArrayList<>(Arrays.asList(request.getParameterValues("magazineId")));
            request.getSession().setAttribute("pre_sub_magazineId", list);
        }
        if (request.getParameter("pre_sort") != null) {
            request.getSession().setAttribute("pre_sub_sort", request.getParameter("pre_sort"));
        }
        if (request.getParameter("pre_filter") != null) {
            request.getSession().setAttribute("pre_sub_filter", request.getParameter("pre_filter"));
        }
        if (request.getParameter("pre_page") != null) {
            request.getSession().setAttribute("pre_sub_page", request.getParameter("pre_page"));
        }
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

    private List<String> removePossibleMagIdsDuplication(List<String> magazineIds) {
        List<String> magId = new ArrayList<>();
        if (!magazineIds.isEmpty()) {
            magId = new ArrayList<>(
                    new HashSet<>(magazineIds));
        }
        return magId;
    }

    private void cleanSessionSubscriptionList(HttpServletRequest request) {
        if (request.getSession().getAttribute("subscriptionList") != null) {
            request.getSession().removeAttribute("subscriptionList");
        }
    }

    private void cleanSessionMagazineId(HttpServletRequest request) {
        if (request.getSession().getAttribute("magazineId") != null) {
            request.getSession().removeAttribute("magazineId");
            request.getSession().removeAttribute("pre_sub_sort");
            request.getSession().removeAttribute("pre_sub_filter");
            request.getSession().removeAttribute("pre_sub_page");
        }
    }

    private BigDecimal getSubtractedBalance() {
        if (userBalance == null) {
            return null;
        }
        return userBalance = userBalance.subtract(sumPriceMagazines);
    }

    private boolean isEnoughMoney(String userId, List<String> magazineIds) throws CommandException {
        try {
            sumPriceMagazines = getSumPriceMagazines(magazineIds);
            if (sumPriceMagazines == null) return false;

            userBalance = getUserBalance(userId);
            if (userBalance == null) return false;
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }

        return userBalance.compareTo(sumPriceMagazines) >= 0;
    }

    private BigDecimal getSumPriceMagazines(List<String> magazineIds) throws RepositoryException {
        return magazineRepository.findSumPriceByIds(magazineIds);
    }

    private BigDecimal getUserBalance(String userId) throws RepositoryException {
        BigDecimal userBalance;
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userBalance = user.getBalance();
        } else {
            log.debug("Command finished");
            return null;
        }
        return userBalance;
    }

    @Override
    public CommandException createCommandException(String methodName, RepositoryException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }

    @Override
    public CommandException createCommandException(String methodName, ValidatorException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}