package com.training.periodical.web.command;

import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.db.MagazineDao;
import com.training.periodical.db.entity.Magazine;
import com.training.periodical.db.entity.Theme;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists menu items.
 * 
 */
public class ListByCategoryMenuCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(ListByCategoryMenuCommand.class);

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
	}

}