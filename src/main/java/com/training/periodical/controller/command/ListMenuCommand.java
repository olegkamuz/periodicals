package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.model.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Lists menu items.
 */
public class ListMenuCommand implements Command {


    private static final Logger log = Logger.getLogger(ListMenuCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

//		log.debug("Command starts");
//
//		// get menu items list
//		List<MenuItem> menuItems = new MagazineDao().findMagazines();
//		log.trace("Found in DB: menuItemsList --> " + menuItems);
//
//		// sort menu by category
//		Collections.sort(menuItems, new Comparator<MenuItem>() {
//			public int compare(MenuItem o1, MenuItem o2) {
//				return (int)(o1.getCategoryId() - o2.getCategoryId());
//			}
//		});
//
//		// put menu items list to the request
//		request.setAttribute("menuItems", menuItems);
//		log.trace("Set the request attribute: menuItems --> " + menuItems);
//
//		log.debug("Command finished");
        return Path.PAGE__LIST_MENU;
    }

    @Override
    public CommandException createCommandException(String methodName, ServiceException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}