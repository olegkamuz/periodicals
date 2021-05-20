package ua.kharkov.knure.dkolesnikov.st4example.web.command;

import org.apache.log4j.Logger;
import ua.kharkov.knure.dkolesnikov.st4example.Path;
import ua.kharkov.knure.dkolesnikov.st4example.db.MagazineDao;
import ua.kharkov.knure.dkolesnikov.st4example.db.entity.Magazine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lists magazines by one theme.
 */
public class ListByOneCategoryMenuCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

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
            list = magazineDao.findMagazineByThemeName(URLDecoder.decode(request.getParameter("theme"), StandardCharsets.UTF_8.name()));

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