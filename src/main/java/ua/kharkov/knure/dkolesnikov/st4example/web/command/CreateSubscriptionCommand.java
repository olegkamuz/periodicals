package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MagazineDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.SubscriptionDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Magazine;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Theme;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Subscription.
 * 
 */
public class CreateSubscriptionCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(CreateSubscriptionCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Command starts");

        if(request.getSession().getAttribute("user") instanceof User){
            String[] magazineIds = request.getParameterMap().get("magazineId");
            Long userId = ((User)request.getSession().getAttribute("user")).getId();
            for(String magazineId: magazineIds){
                (new SubscriptionDao()).insertSubscription(userId, Long.parseLong(magazineId));
            }
        }

		log.debug("Command finished");
		return Path.PAGE_INDEX;
	}

}