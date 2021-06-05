package com.training.periodical.controller.command;

import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.ThemeService;
import com.training.periodical.util.constants.ThemeConstants;
import com.training.periodical.util.validator.Validator;
import com.training.periodical.util.validator.ValidatorException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lists menu items.
 */
public class IndexCommand implements Command {

    private static final Logger log = Logger.getLogger(IndexCommand.class);
    private final ThemeService themeService;
    private final MagazineService magazineService;
    private final int PAGE_SIZE = 5;
    private final int RANGE_FROM = 0;

    public IndexCommand(ThemeService themeService, MagazineService magazineService) {
        this.themeService = themeService;
        this.magazineService = magazineService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");

        resetCheckedIfRequested(request);
        setCheckedMagazines(request);

        String sort = getSort(request);
        String filter = getFilter(request);
        String page = getPage(request);

        showMagazinesByThemes(request);

        if (validateFilterOrSort(sort) && validateFilterOrSort(filter)) {
            log.debug("Command finished");
            return showFilteredSorted(request, sort, filter, page);
        } else if (validateFilterOrSort(filter)) {
            log.debug("Command finished");
            return showFiltered(request, filter, page);
        } else if (validateFilterOrSort(sort)) {
            log.debug("Command finished");
            return showSorted(request, sort, page);
        } else {
            return showAll(request, page);
        }
    }

    @Override
    public CommandException createCommandException(String methodName, ServiceException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }


    private void resetCheckedIfRequested(HttpServletRequest request) {
        if (request.getParameter("reset_checked") != null) {
            request.getSession().removeAttribute("magazineId");
//            request.getSession().removeAttribute("magIds");
        }
    }

    private String getSort(HttpServletRequest request) {
        String sort = "";
        if (request.getSession().getAttribute("pre_sort") != null) {
            sort = (String) request.getSession().getAttribute("pre_sort");
            request.getSession().removeAttribute("pre_sort");
        } else {
            sort = request.getParameter("sort");
        }
        return sort;
    }

    private String getFilter(HttpServletRequest request) {
        String filter = "";
        if (request.getSession().getAttribute("pre_filter") != null) {
            filter = (String) request.getSession().getAttribute("pre_filter");
            request.getSession().removeAttribute("pre_filter");
        } else {
            filter = request.getParameter("filter");
        }
        return filter;
    }

    private String getPage(HttpServletRequest request) {
        String page = "";
        if (request.getSession().getAttribute("pre_page") != null) {
            page = (String) request.getSession().getAttribute("pre_page");
            request.getSession().removeAttribute("pre_page");
        } else {
            page = request.getParameter("page");
        }
        return page;
    }

    private void setPreviousCheckedMagazines(HttpServletRequest request) {
        if (request.getSession().getAttribute("magazineId") != null) {
            request.getSession().setAttribute("checked",
                    request.getSession().getAttribute("magazineId"));
        }
    }

    private void setCheckedMagazines(HttpServletRequest request) {

        HttpSession session = request.getSession();

        List<String> previousMagIds = new ArrayList<>();
        String[] thisMomentMagIds = null;
        Object obj = session.getAttribute("magazineId");
        if (obj instanceof List) {
            for (Object ob : (List) obj) {
                if (ob instanceof String) {
                    previousMagIds.add((String) ob);
                }
            }
        }

        if (request.getParameterValues("magazineId") != null) {
            thisMomentMagIds = request.getParameterValues("magazineId");
        }


        ArrayList<String> magIds = new ArrayList<>();
        if (thisMomentMagIds != null) {
//            if (previousMagIds != null) {
//                Set<String> result = Arrays.stream(thisMomentMagIds)
//                        .distinct()
//                        .filter(previousMagIds::contains)
//                        .collect(Collectors.toSet());
//                magIds.addAll(result);
//            } else {
//                magIds.addAll(Arrays.asList(thisMomentMagIds));
//            }
            magIds.addAll(Arrays.asList(thisMomentMagIds));
        }
        if (previousMagIds != null) {
            magIds.addAll(previousMagIds);
//            session.removeAttribute("magazineId");
        }

        if(thisMomentMagIds != null && previousMagIds != null){
            magIds.addAll(Arrays.asList(thisMomentMagIds));
            Set<String> result = Arrays.stream(thisMomentMagIds)
                    .distinct()
                    .filter(previousMagIds::contains)
                    .collect(Collectors.toSet());
            magIds.removeAll(result);
        }

        request.getSession().setAttribute("magazineId", magIds);
    }

    private void showMagazinesByThemes(HttpServletRequest request) throws CommandException {
        setMagazinesByThemes(request, getMagazineByThemes());
    }

    private String showAll(HttpServletRequest request, String page) throws CommandException {

        int allMagazineAmount = getAllMagazineAmount();

        if (isPageOutOfRange(allMagazineAmount, page)) {
            log.debug("Page out of range");
            return Path.REDIRECT__INDEX + "?page=1";
        }

        int currentPage = Integer.parseInt(page);

        request.getSession().setAttribute("fieldToSort", "all");
        request.getSession().setAttribute("fieldToFilter", "all");

        if (allMagazineAmount > 0) {
            setToJspMagazinesPage(request, getMagazinesPage(currentPage));
            setToJspPaginationBar(request, currentPage, getNumberOfPages(allMagazineAmount));
            log.debug("Show all magazines");
        } else {
            setToJspMagazinesPage(request, Collections.emptyList());
            log.trace("No magazines");
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showSorted(HttpServletRequest request, String sort, String page) throws CommandException {
        String sortSubQuery = getSortSubQuery(sort);
        request.getSession().setAttribute("fieldToSort", sort);
        request.getSession().setAttribute("fieldToFilter", "all");

        int allMagazineAmount = getAllMagazineAmount();
        if (isPageOutOfRange(allMagazineAmount, page)) {
            log.debug("Page out of range");
            return Path.REDIRECT__INDEX + "?page=1";
        }
        int currentPage = Integer.parseInt(page);

        if (allMagazineAmount > 0) {
            setToJspMagazinesPage(request, getMagazinesSortedPaginated(sortSubQuery, currentPage));
            setToJspPaginationBar(request, currentPage, getNumberOfPages(allMagazineAmount));
            log.debug("Show filtered magazines");
        } else {
            setToJspMagazinesPage(request, Collections.emptyList());
            log.trace("No magazines");
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showFiltered(HttpServletRequest request, String filter, String page) throws CommandException {
        String filterName = getFilterName(filter);
        request.getSession().setAttribute("fieldToFilter", filter);
        request.getSession().setAttribute("fieldToSort", "all");

        int filteredMagazineAmount = getFilteredMagazineAmount(filterName);
        if (isPageOutOfRange(filteredMagazineAmount, page)) {
            log.debug("Page out of range");
            return Path.REDIRECT__INDEX + "?page=1";
        }
        int currentPage = Integer.parseInt(page);

        request.getSession().setAttribute("fieldToFilter", filter);

        if (filteredMagazineAmount > 0) {
            setToJspMagazinesPage(request, getMagazinesFilteredPaginated(filterName, currentPage));
            setToJspPaginationBar(request, currentPage, getNumberOfPages(filteredMagazineAmount));
            log.debug("Show sorted magazines");
        } else {
            setToJspMagazinesPage(request, Collections.emptyList());
            log.trace("No magazines in filter theme -->" + filterName);
        }
        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showFilteredSorted(HttpServletRequest request, String sort, String filter, String page) throws CommandException {
        String sortSubQuery = getFilterSortSubQuery(sort);
        String filterName = getFilterName(filter);

        request.getSession().setAttribute("fieldToSort", sort);
        request.getSession().setAttribute("fieldToFilter", filter);

        int filteredMagazineAmount = getFilteredMagazineAmount(filterName);

        if (isPageOutOfRange(filteredMagazineAmount, page)) {
            log.debug("Page out of range");
            return Path.REDIRECT__INDEX + "?page=1";
        }
        int currentPage = Integer.parseInt(page);

        if (filteredMagazineAmount > 0) {
            setToJspMagazinesPage(request, getMagazinesFilteredSortedPaginates(filterName, sortSubQuery, currentPage));
            setToJspPaginationBar(request, currentPage, getNumberOfPages(filteredMagazineAmount));
            log.debug("Show filtered and sorted magazines");
        } else {
            setToJspMagazinesPage(request, Collections.emptyList());
            log.trace("No magazines in filter theme " + filterName);
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private boolean isPageOutOfRange(int allMagazinesAmount, String page) throws CommandException {
        return !validatePage(page, getNumberOfPages(allMagazinesAmount));
    }

    private List<Magazine> getMagazinesPage(int currentPage) {
        List<Magazine> page = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineService.findPage(PAGE_SIZE, offset);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return page;
    }

    private List<Magazine> getMagazinesFilteredPaginated(String filterName, int currentPage) {
        List<Magazine> filteredPaginated = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineService.findFilteredPaginated(filterName, PAGE_SIZE, offset);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return filteredPaginated;
    }

    private List<Magazine> getMagazinesFilteredSortedPaginates(String filterName, String sortSubQuery, int currentPage) {
        List<Magazine> filteredSortedPaginated = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineService.findFilteredSortedPaginated(filterName, sortSubQuery, PAGE_SIZE, offset);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return filteredSortedPaginated;
    }

    private String getSortSubQuery(String sort) {
        String sortSubQuery = "";
        switch (sort) {
            case "name_asc":
                sortSubQuery = MagazineQuery.SQL__SUB_SORT_NAME_ASC;
                break;
            case "name_desc":
                sortSubQuery = MagazineQuery.SQL__SUB_SORT_NAME_DESC;
                break;
            case "price_asc":
                sortSubQuery = MagazineQuery.SQL__SUB_SORT_PRICE_ASC;
                break;
            case "price_desc":
                sortSubQuery = MagazineQuery.SQL__SUB_SORT_PRICE_DESC;
                break;
        }
        return sortSubQuery;
    }

    private String getFilterSortSubQuery(String sort) {
        String sortSubQuery = "";
        switch (sort) {
            case "name_asc":
                sortSubQuery = MagazineQuery.SQL__SUB_FILTER_SORT_NAME_ASC;
                break;
            case "name_desc":
                sortSubQuery = MagazineQuery.SQL__SUB_FILTER_SORT_NAME_DESC;
                break;
            case "price_asc":
                sortSubQuery = MagazineQuery.SQL__SUB_FILTER_SORT_PRICE_ASC;
                break;
            case "price_desc":
                sortSubQuery = MagazineQuery.SQL__SUB_FILTER_SORT_PRICE_DESC;
                break;
        }
        return sortSubQuery;
    }

    private String getFilterName(String filter) {
        String filterName = "";
        switch (filter) {
            case "interior":
                filterName = ThemeConstants.INTERIOR;
                break;
            case "sport":
                filterName = ThemeConstants.SPORT;
                break;
            case "it_world":
                filterName = ThemeConstants.IT_WORLD;
                break;
            case "music":
                filterName = ThemeConstants.MUSIC;
                break;
        }
        return filterName;
    }

    private List<Magazine> getMagazinesSortedPaginated(String sortSubQuery, int currentPage) {
        List<Magazine> sorted = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineService.findSortedPaginated(sortSubQuery, PAGE_SIZE, offset);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return sorted;
    }

    private void setMagazinesByThemes(HttpServletRequest request, Map<Theme, List<Magazine>> map) throws CommandException {
        request.getSession().setAttribute("magazinesByThemes", map);
        log.trace("Set the request attribute: menuByCategoryItems --> " + map);
    }

    /**
     * if magazinesPage empty jsp page section will not render and show user no magazines warning.
     */
    private void setToJspMagazinesPage(HttpServletRequest request, List<Magazine> magazinesPage) {
        request.getSession().setAttribute("magazinesPage", magazinesPage);
        log.trace("Set the request attribute: magazinesPage --> " + magazinesPage);
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

    private boolean validateFilterOrSort(String data) throws CommandException {
        try {
            if (Validator.isValid(data,
                    Validator.Check.NOT_NULL,
                    Validator.Check.NOT_EMPTY,
                    Validator.Check.URL_DECODE,
                    Validator.Check.ALL
            )) return true;
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }

    private boolean validatePage(String data, int range_to)
            throws CommandException {
        Validator.range_int_from = RANGE_FROM;
        Validator.range_int_to = range_to;
        try {
            if (Validator.isValid(data,
                    Validator.Check.NOT_NULL,
                    Validator.Check.NOT_EMPTY,
                    Validator.Check.IS_CAST_TO_INT,
                    Validator.Check.IN_INT_RANGE_INCLUSIVE_INCLUSIVE
            )) return true;
        } catch (ValidatorException e) {
            throw new CommandException(e);
        }
        return false;
    }

    private int getAllMagazineAmount() throws CommandException {
        try {
            return magazineService.getCount();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private int getFilteredMagazineAmount(String filterName) throws CommandException {
        try {
            return magazineService.getCountFiltered(filterName);
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

    private void setToJspPaginationBar(HttpServletRequest request, int currentPage, int pageAmount) throws CommandException {
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

    private int getNumberOfPages(int magazineAmount) {
        int numberOfPages;
        numberOfPages = magazineAmount / PAGE_SIZE;
        return magazineAmount % PAGE_SIZE == 0 ? numberOfPages : ++numberOfPages;
    }
}









