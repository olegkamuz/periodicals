package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MenuDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.MenuItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Lists menu items.
 * 
 * @author D.Kolesnikov
 * 
 */
public class ListMenuCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(ListMenuCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		log.debug("Command starts");
		
		// get menu items list
		List<MenuItem> menuItems = new MenuDao().findMenuItems();
		log.trace("Found in DB: menuItemsList --> " + menuItems);
		
		// sort menu by category
		Collections.sort(menuItems, new Comparator<MenuItem>() {
			public int compare(MenuItem o1, MenuItem o2) {
				return (int)(o1.getCategoryId() - o2.getCategoryId());
			}
		});		
		
		// put menu items list to the request
		request.setAttribute("menuItems", menuItems);		
		log.trace("Set the request attribute: menuItems --> " + menuItems);
		
		log.debug("Command finished");
		return Path.PAGE__LIST_MENU;
	}

}