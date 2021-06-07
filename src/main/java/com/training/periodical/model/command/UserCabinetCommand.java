package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.model.repository.UserSubscriptionRepository;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserCabinetCommand extends AbstractCommand {
    private static final long serialVersionUID = 5034889545771020837L;
    private static final Logger log = Logger.getLogger(UserCabinetCommand.class);
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;

    public UserCabinetCommand(UserSubscriptionRepository usService, UserRepository userRepository) {
        this.userSubscriptionRepository = usService;
        this.userRepository = userRepository;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("User cabinet command starts");

        updateLocaleIfRequested(request.getParameter("localeToSet"), request);

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        User user = getUser(userId);
        showBalance(userId, request);

        String replenish = request.getParameter("replenish");

        if (replenish != null && !replenish.equals("")) {
            try {
                BigDecimal newBalance = (new BigDecimal(replenish))
                        .add(user.getBalance());
                userRepository.updateBalance(newBalance, user.getId());
                showBalance(userId, request);
            } catch (RepositoryException e) {
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
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()){
                user = optionalUser.get();
            } else {
                throw new CommandException("exception in getUser" +
                         UserCabinetCommand.class.getSimpleName());
            }
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
        return user;
    }

    private void setSessionSubscriptionList(HttpServletRequest request) throws CommandException {
        List<UserSubscriptionBean> subscriptionList = null;
        try {
            subscriptionList = userSubscriptionRepository.findSubscriptionByUserId(((User) request.getSession().getAttribute("user")).getId());
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
        request.getSession().setAttribute("subscriptionList", subscriptionList);
    }

    private boolean isAttributeSet(HttpServletRequest request, String attributeName) {
        return request.getSession().getAttribute(attributeName) != null;
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
