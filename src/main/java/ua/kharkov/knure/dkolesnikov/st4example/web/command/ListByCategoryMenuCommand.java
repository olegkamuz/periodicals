package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MenuDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Category;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists menu items.
 * 
 * @author D.Kolesnikov
 * 
 */
public class ListByCategoryMenuCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(ListByCategoryMenuCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Command starts");

		Map<Category, List<MenuItem>> map = new HashMap<>();

        MenuDao menuDao = new MenuDao();

        for(Category category: menuDao.findCategories()){
		    map.put(category, menuDao.findMenuItems(category));
        }

		request.getSession().setAttribute("menuItemsByCategory", map);
		log.trace("Set the request attribute: menuByCategoryItems --> " + map);
		
		log.debug("Command finished");
		return Path.REDIRECT__LIST_BY_CATEGORY_MENU;
//        return Path.PAGE__LIST_BY_CATEGORY_MENU;
//        return "/WEB-INF/jsp/album.jsp";
	}

}