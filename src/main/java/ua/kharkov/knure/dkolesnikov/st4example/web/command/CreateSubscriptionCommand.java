package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MagazineDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.SubscriptionDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.UserDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Magazine;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Theme;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create Subscription.
 */
public class CreateSubscriptionCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(CreateSubscriptionCommand.class);

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
                BigDecimal userBalance = (new UserDao()).findUserById(userId).getBalance();
                if (userBalance.compareTo(sumPriceMagazines) >= 0) {
                    MathContext mc = new MathContext(2);
                    userBalance = userBalance.subtract(sumPriceMagazines, mc);
                    (new SubscriptionDao()).insertSubscription(userId, magazineIdsLong);
                }
            }
        }

        log.debug("Command finished");
        return Path.REDIRECT__INDEX;
    }

}