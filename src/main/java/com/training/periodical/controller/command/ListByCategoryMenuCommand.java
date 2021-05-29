package com.training.periodical.controller.command;

import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.ThemeService;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists menu items.
 */
public class ListByCategoryMenuCommand implements Command {

    private static final Logger log = Logger.getLogger(ListByCategoryMenuCommand.class);
    private final ThemeService themeService;
    private final MagazineService magazineService;

    public ListByCategoryMenuCommand(ThemeService themeService, MagazineService magazineService) {
        this.themeService = themeService;
        this.magazineService = magazineService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        Map<Theme, List<Magazine>> map = new HashMap<>();

        try {
            for (Theme theme : themeService.findAll()) {
                map.put(theme, magazineService.
                        findMagazineByThemeName(theme.getName()));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.getSession().setAttribute("menuItemsByCategory", map);
        log.trace("Set the request attribute: menuByCategoryItems --> " + map);

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

}









