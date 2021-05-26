package com.training.periodical.controller.command;

import org.apache.log4j.Logger;
import com.training.periodical.Path;
import com.training.periodical.model.dao.MagazineDao;
import com.training.periodical.entity.Magazine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {

        log.debug("Command starts");

        Map<String, List<Magazine>> map = new HashMap<>();

        MagazineDao magazineDao = new MagazineDao();

        List<Magazine> list;
        String themeName;
        Object theme = request.getSession().getAttribute("theme");

        if (theme instanceof String && !(themeName = (String) theme).equals("")) {
            list = magazineDao.findMagazineByThemeName(themeName);
            request.getSession().setAttribute("theme", "");

            request.getSession().setAttribute("checked", request.getSession().getAttribute("magazineId"));
            request.getSession().setAttribute("magazineId", "");

            return getPreparedForward(request, map, list, themeName);
        } else if ((themeName = request.getParameter("theme")) != null) {
            try {
                list = magazineDao.findMagazineByThemeName(URLDecoder.decode(request.getParameter("theme"), StandardCharsets.UTF_8.name()));
            } catch (UnsupportedEncodingException e) {
               throw new CommandException(e);
            }

            return getPreparedForward(request, map, list, themeName);
        }

        return Path.PAGE__LIST_MAGAZINES_BY_THEMES;
    }

    private String getPreparedForward(HttpServletRequest request, Map<String, List<Magazine>> map, List<Magazine> list, String themeName) {
        map.put(themeName, list);

        request.getSession().setAttribute("magazinesByOneTheme", map);
        log.trace("Set the request attribute: magazinesByOneTheme --> " + map);

        log.debug("Command finished");
        return Path.PAGE__LIST_MAGAZINES_BY_ONE_THEME;
    }
}