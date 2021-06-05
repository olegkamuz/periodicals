package com.training.periodical.controller.command;

import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists magazines by one theme.
 */
public class ListByOneCategoryMenuCommand implements Command {

    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final MagazineService magazineService;

    public ListByOneCategoryMenuCommand(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        log.debug("Command starts");
        Map<String, List<Magazine>> map = new HashMap<>();
        String theme;

        if (request.getSession().getAttribute("theme") == null) { // first visit
            theme = getDecodedTheme(request);
            request.getSession().setAttribute("theme", theme);
        } else { // already visited
            theme = (String) request.getSession().getAttribute("theme");

            request.getSession().removeAttribute("theme");
            request.getSession().setAttribute("checked",
                    request.getSession().getAttribute("magazineId"));
            request.getSession().removeAttribute("magazineId");
        }

        return getPreparedForward(request, map, getMagazineList((String) theme), (String) theme);
    }

    private String getDecodedTheme(HttpServletRequest request) throws CommandException {
        try {
            return URLDecoder.decode(request.getParameter("theme"),
                    StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new CommandException(e);
        }
    }

    private List<Magazine> getMagazineList(String theme) throws CommandException {
        try {
            return magazineService.findMagazineByThemeName(theme);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String getPreparedForward(HttpServletRequest request, Map<String, List<Magazine>> map, List<Magazine> list, String themeName) {
        map.put(themeName, list);

        request.getSession().setAttribute("magazinesByOneTheme", map);
        log.trace("Set the request attribute: magazinesByOneTheme --> " + map);

        log.debug("Command finished");
        return Path.PAGE__LIST_MAGAZINES_BY_ONE_THEME;
    }

    @Override
    public CommandException createCommandException(String methodName, ServiceException e) {
        return new CommandException("exception in " +
                methodName +
                " method at " +
                this.getClass().getSimpleName(), e);
    }
}