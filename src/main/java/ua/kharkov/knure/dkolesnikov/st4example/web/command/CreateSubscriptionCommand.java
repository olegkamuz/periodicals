package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MagazineDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Magazine;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Theme;

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

		Map<Theme, List<Magazine>> map = new HashMap<>();

        MagazineDao magazineDao = new MagazineDao();

        for(Theme theme: magazineDao.findThemes()){
		    map.put(theme, magazineDao.findMagazineByThemeName(theme.getName()));
        }

		request.getSession().setAttribute("menuItemsByCategory", map);
		log.trace("Set the request attribute: menuByCategoryItems --> " + map);
		
		log.debug("Command finished");
		return Path.PAGE_INDEX;
//        return Path.REDIRECT__LIST_BY_CATEGORY_MENU;
//        return Path.PAGE__LIST_BY_CATEGORY_MENU;
//        return Path.PAGE__LIST_BY_CATEGORY_MENU;
//        return "/WEB-INF/jsp/album.jsp";
	}

}