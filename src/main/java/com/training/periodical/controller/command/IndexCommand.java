package com.training.periodical.controller.command;

import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.ThemeService;
import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

        int magazineAmount = getMagazineAmount();

        int pageAmount = getNumberOfPages(3, magazineAmount);
        pageAmount = 7;

        String currentPageParameter = request.getParameter("page");

        if (!validate(currentPageParameter, 0, pageAmount)) {
            return Path.REDIRECT__INDEX + "?page=1";
        }

        paginate(request, Integer.parseInt(currentPageParameter), pageAmount);

        Map<Theme, List<Magazine>> map = getMagazineByThemes();
        request.getSession().setAttribute("magazinesByThemes", map);
        log.trace("Set the request attribute: menuByCategoryItems --> " + map);

        List<Magazine> all = getAllMagazines();
        if (all != null) request.getSession().setAttribute("magazinesList", all);
        log.trace("Set the request attribute: magazinesList --> " + all);

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private List<Magazine> getAllMagazines() {
        List<Magazine> all = null;
        try {
            return magazineService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return all;
    }

    private Map<Theme, List<Magazine>> getMagazineByThemes() throws CommandException {
        Map<Theme, List<Magazine>> map = new HashMap<>();
        try {
            for (Theme theme : themeService.findAll()) {
                map.put(theme, magazineService.
                        findMagazineByThemeName(theme.getName()));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return map;
    }

    private boolean validate(String data, int range_from, int range_to)
            throws CommandException {
        Validator.range_int_from = range_from;
        Validator.range_int_to = range_to;
        try {
            if (Validator.isValid(data,
                    Validator.CheckType.NOT_NULL,
                    Validator.CheckType.NOT_EMPTY,
                    Validator.CheckType.IS_CAST_TO_INT,
                    Validator.CheckType.IN_INT_RANGE_INCLUSIVE_INCLUSIVE
            )) return true;
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }

    private int getMagazineAmount() throws CommandException {
        try {
            return magazineService.getCount();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private List<Integer> getBaseList(int pageAmount) {
        List<Integer> bs = new ArrayList<>();
        fillBaseList(bs, Math.min(pageAmount, 6));
        return bs;
    }

    private void fillBaseList(List<Integer> baseList, int pageAmount) {
        for (int i = 1; i <= pageAmount; i++) {
            baseList.add(i);
        }
    }

    private void paginate(HttpServletRequest request, int currentPage, int pageAmount) throws CommandException {
        List<Integer> baseList = getBaseList(pageAmount);

        if (pageAmount > 6) {
            boolean pageFirstExists = true;
            boolean pageLastExists = true;
            List<Integer> carriage;

            if (currentPage < 4) {
                carriage = baseList;
                pageFirstExists = false;
            } else if (currentPage >= pageAmount - 2) {
                carriage = Arrays.asList(pageAmount - 3, pageAmount - 2, pageAmount - 1, pageAmount);
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
            request.getSession().setAttribute("baseList", baseList);
            request.getSession().setAttribute("currentPage", currentPage);
        }
        request.getSession().setAttribute("firstPage", 1);
        request.getSession().setAttribute("lastPage", pageAmount);
        request.getSession().setAttribute("previousPage", currentPage - 1);
        request.getSession().setAttribute("nextPage", currentPage + 1);
    }

    private int getNumberOfPages(int pageSize, int magazineAmount) {
        int numberOfPages;
        numberOfPages = magazineAmount / pageSize;
        return magazineAmount % pageSize == 0 ? numberOfPages : ++numberOfPages;
    }
}









