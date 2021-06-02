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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists menu items.
 */
public class IndexCommand implements Command {

    private static final Logger log = Logger.getLogger(IndexCommand.class);
    private final ThemeService themeService;
    private final MagazineService magazineService;

    public IndexCommand(ThemeService themeService, MagazineService magazineService) {
        this.themeService = themeService;
        this.magazineService = magazineService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        int pageNum = getNumberOfPages(3);
        int currentPage = 0;
        String currentPageParameter = request.getParameter("page");
        if (currentPageParameter != null && !currentPageParameter.equals("")) {
            currentPage = Integer.parseInt(currentPageParameter);
        }
        if (currentPage < 0 || currentPage > pageNum) {
            return Path.REDIRECT__INDEX + "?page=1";
        }

        List<Integer> firstFourPages = Arrays.asList(1, 2, 3, 4);
        if (pageNum > 4) {
            boolean pageFirstExists = true;
            boolean pageLastExists = true;
            List<Integer> carriage;

            if (currentPage < 4) {
                carriage = firstFourPages;
                pageFirstExists = false;
            } else if (currentPage >= pageNum - 2) {
                carriage = Arrays.asList(pageNum - 3, pageNum - 2, pageNum - 1, pageNum);
                pageLastExists = false;
            } else {
                carriage = Arrays.asList(currentPage - 1, currentPage, currentPage + 1);
            }

            request.getSession().setAttribute("pageLast", pageLastExists);
            request.getSession().setAttribute("pageFirst", pageFirstExists);
            request.getSession().setAttribute("areDots", true);
            request.getSession().setAttribute("carriage", carriage);
            request.getSession().setAttribute("currentPage", currentPage);
        } else {
            request.getSession().setAttribute("areDots", false);
            request.getSession().setAttribute("firstFourPages", firstFourPages);
            request.getSession().setAttribute("currentPage", currentPage);
        }
        request.getSession().setAttribute("firstPage", 1);
        request.getSession().setAttribute("lastPage", pageNum);
        request.getSession().setAttribute("previousPage", currentPage - 1);
        request.getSession().setAttribute("nextPage", currentPage + 1);

        Map<Theme, List<Magazine>> map = new HashMap<>();

        try {
            for (Theme theme : themeService.findAll()) {
                map.put(theme, magazineService.
                        findMagazineByThemeName(theme.getName()));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        request.getSession().setAttribute("magazinesByThemes", map);
        log.trace("Set the request attribute: menuByCategoryItems --> " + map);

        try {
            List<Magazine> all = magazineService.findAll();
            request.getSession().setAttribute("magazinesList", all);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private int getNumberOfPages(int pageSize) throws CommandException {
        try {
//            int count =
            magazineService.getCount();
            int count = 50;
            int numberOfPages;
            numberOfPages = count / pageSize;
            return count % pageSize == 0 ? numberOfPages : ++numberOfPages;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

}









