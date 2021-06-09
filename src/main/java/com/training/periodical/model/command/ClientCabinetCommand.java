package com.training.periodical.model.command;

import com.training.periodical.Path;
import com.training.periodical.bean.UserSubscriptionBean;
import com.training.periodical.entity.User;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.model.repository.UserSubscriptionRepository;
import com.training.periodical.util.validator.Valid;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ClientCabinetCommand extends AbstractCommand {
    private static final long serialVersionUID = 5034889545771020837L;
    private static final Logger log = Logger.getLogger(ClientCabinetCommand.class);
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;

    public ClientCabinetCommand(UserSubscriptionRepository usService, UserRepository userRepository) {
        this.userSubscriptionRepository = usService;
        this.userRepository = userRepository;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("User cabinet command starts");
        this.request = request;

        updateLocaleIfRequested(request.getParameter("localeToSet"));

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        User user = getUser(userId);
        showBalance(userId);

        String replenish = request.getParameter("replenish");

        if (Valid.notNullNotEmpty(replenish)) {
            try {
                BigDecimal newBalance = (new BigDecimal(replenish))
                        .add(user.getBalance());
                userRepository.updateBalance(newBalance, user.getId());
                showBalance(userId);
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }

        if (!isAttributeSet("subscriptionList")) {
            setSessionSubscriptionList(getSubscriptions());
        }

        log.info("User cabinet command finish");
        return Path.PAGE__CLIENT_CABINET;
    }

    private void showBalance(long userId) {
        try {
            BigDecimal balanceFromDB = getUser(userId).getBalance();
            ((User) request.getSession().getAttribute("user")).setBalance(balanceFromDB);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    private User getUser(long userId) throws CommandException {
        User user;
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                throw createCommandException("exception in getUser");
            }
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
        return user;
    }

    private List<UserSubscriptionBean> getSubscriptions() throws CommandException {
        try {
            return userSubscriptionRepository.findSubscriptionByUserId(((User) request.getSession().getAttribute("user")).getId());
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private void setSessionSubscriptionList(List<UserSubscriptionBean> subscriptionList) {
        request.getSession().setAttribute("subscriptionList", subscriptionList);
    }

    private boolean isAttributeSet(String attributeName) {
        return Valid.isAttributeNull(request, attributeName);
    }
}
