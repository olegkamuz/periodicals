package com.training.periodical.model.command;

import com.training.periodical.model.dao.query.MagazineQuery;
import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.ThemeRepository;
import com.training.periodical.util.validator.Valid;
import com.training.periodical.util.constants.ThemeConstants;
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
 * Lists magazines with pagination, sorting and filtering; Shows magazines by themes.
 */
public class IndexCommand extends AbstractCommand {
    private static final long serialVersionUID = -650070544358974520L;

    private final ThemeRepository themeRepository;
    private final MagazineRepository magazineRepository;

    public IndexCommand(ThemeRepository themeRepository, MagazineRepository magazineRepository) {
        this.themeRepository = themeRepository;
        this.magazineRepository = magazineRepository;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        this.request = request;

        updateLocaleIfRequested();

        resetCheckedIfRequested();
        setCheckedUncheckedMagazines();

        showMagazinesByThemes();

        final String sort = getSort();
        final String filter = getFilter();
        final String page = getPage();


        if (validateFilterOrSort(sort) && validateFilterOrSort(filter)) {
            log.debug("Command finished");
            return showFilteredSorted(sort, filter, page);
        } else if (validateFilterOrSort(getFilter())) {
            log.debug("Command finished");
            return showFiltered(filter, page);
        } else if (validateFilterOrSort(getSort())) {
            log.debug("Command finished");
            return showSorted(sort, page);
        } else {
            return showAll(page);
        }
    }

    private void resetCheckedIfRequested() {
        if (request.getParameter("reset_checked") != null) {
            request.getSession().removeAttribute("magazineId");
        }
    }

    private void setCheckedUncheckedMagazines() {
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
            magIds.addAll(Arrays.asList(thisMomentMagIds));
        }
        if (previousMagIds != null) {
            magIds.addAll(previousMagIds);
        }

        if (thisMomentMagIds != null && previousMagIds != null) {
            magIds.addAll(Arrays.asList(thisMomentMagIds));
            Set<String> result = Arrays.stream(thisMomentMagIds)
                    .distinct()
                    .filter(previousMagIds::contains)
                    .collect(Collectors.toSet());
            magIds.removeAll(result);
        }

        request.getSession().setAttribute("magazineId", magIds);
    }

    private void showMagazinesByThemes() throws CommandException {
        setMagazinesByThemes(getMagazineByThemes());
    }

    private String showAll(String page) throws CommandException {

        int allMagazineAmount = getAllMagazineAmount();

        if (isPageOutOfRange(allMagazineAmount, page)) {
            log.debug("Page out of range");
            return Path.REDIRECT__INDEX + "?page=1";
        }

        int currentPage = Integer.parseInt(page);

        request.getSession().setAttribute("fieldToSort", "all");
        request.getSession().setAttribute("fieldToFilter", "all");

        if (allMagazineAmount > 0) {
            setMagazinesPage(getMagazinesPage(magazineRepository, currentPage));
            setPaginationBar(currentPage, getNumberOfPages(allMagazineAmount));
            log.debug("Show all magazines");
        } else {
            setMagazinesPage(Collections.emptyList());
            log.trace("No magazines");
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showSorted(String sort, String page) throws CommandException {
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
            setMagazinesPage(getMagazinesSortedPaginated(sortSubQuery, currentPage));
            setPaginationBar(currentPage, getNumberOfPages(allMagazineAmount));
            log.debug("Show filtered magazines");
        } else {
            setMagazinesPage(Collections.emptyList());
            log.trace("No magazines");
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showFiltered(String filter, String page) throws CommandException {
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
            setMagazinesPage(getMagazinesFilteredPaginated(filterName, currentPage));
            setPaginationBar(currentPage, getNumberOfPages(filteredMagazineAmount));
            log.debug("Show sorted magazines");
        } else {
            setMagazinesPage(Collections.emptyList());
            log.trace("No magazines in filter theme -->" + filterName);
        }
        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }

    private String showFilteredSorted(String sort, String filter, String page) throws
            CommandException {
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
            setMagazinesPage(getMagazinesFilteredSortedPaginates(filterName, sortSubQuery, currentPage));
            setPaginationBar(currentPage, getNumberOfPages(filteredMagazineAmount));
            log.debug("Show filtered and sorted magazines");
        } else {
            setMagazinesPage(Collections.emptyList());
            log.trace("No magazines in filter theme " + filterName);
        }

        log.debug("Command finished");
        return Path.PAGE_INDEX;
    }


    private List<Magazine> getMagazinesFilteredPaginated(String filterName, int currentPage) {
        List<Magazine> filteredPaginated = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineRepository.findFilteredPaginated(filterName, PAGE_SIZE, offset);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return filteredPaginated;
    }

    private List<Magazine> getMagazinesFilteredSortedPaginates(String filterName, String sortSubQuery,
                                                               int currentPage) {
        List<Magazine> filteredSortedPaginated = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineRepository.findFilteredSortedPaginated(filterName, sortSubQuery, PAGE_SIZE, offset);
        } catch (RepositoryException e) {
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
            return magazineRepository.findSortedPaginated(sortSubQuery, PAGE_SIZE, offset);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return sorted;
    }

    private void setMagazinesByThemes(Map<Theme, List<Magazine>> map) {
        request.getSession().setAttribute("magazinesByThemes", map);
        log.trace("Set the request attribute: menuByCategoryItems --> " + map);
    }

    /**
     * if magazinesPage empty jsp page section will not render and show user no magazines warning.
     */
    private void setMagazinesPage(List<Magazine> magazinesPage) {
        request.getSession().setAttribute("magazinesPage", magazinesPage);
        log.trace("Set the request attribute: magazinesPage --> " + magazinesPage);
    }

    private Map<Theme, List<Magazine>> getMagazineByThemes() throws CommandException {
        Map<Theme, List<Magazine>> map = new HashMap<>();
        try {
            for (Theme theme : themeRepository.findAll()) {
                map.put(theme, magazineRepository.
                        findMagazineByThemeName(theme.getName()));
            }
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }

        return map;
    }

    private boolean validateFilterOrSort(String data) {
        return Valid.notNullNotEmptyUrlDecodeAll(data);
    }

    private int getAllMagazineAmount() throws CommandException {
        try {
            return magazineRepository.getCount();
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private int getFilteredMagazineAmount(String filterName) throws CommandException {
        try {
            return magazineRepository.getCountFiltered(filterName);
        } catch (RepositoryException e) {
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

    private void setPaginationBar(int currentPage, int pageAmount) {
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

    private String getSort() {
        String sort = "";
        if (request.getParameter("sort") != null && !request.getParameter("sort").equals("")) {
            return request.getParameter("sort");
        }

        if (request.getSession().getAttribute("fieldToSort") != null && !request.getSession().getAttribute("fieldToSort").equals("")) {
            return (String) request.getSession().getAttribute("fieldToSort");
        }

        return sort;
    }

    private String getFilter() {
        if (request.getParameter("filter") != null && !request.getParameter("filter").equals("")) {
            return request.getParameter("filter");
        }
        if (request.getSession().getAttribute("fieldToFilter") != null && !request.getSession().getAttribute("fieldToFilter").equals("")) {
            return (String) request.getSession().getAttribute("fieldToFilter");
        }
        return "";
    }

    private String getPage() {
        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            return request.getParameter("page");
        }

        if (request.getSession().getAttribute("currentPage") != null && request.getSession().getAttribute("currentPage").equals("")) {
            return String.valueOf(request.getSession().getAttribute("currentPage"));
        }
        return "";
    }
}









