package com.training.periodical.controller.command;

import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.UserDao;
import com.training.periodical.model.service.MagazineService;
import com.training.periodical.model.service.ServiceException;
import com.training.periodical.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AdminCabinetCommand implements Command {
    private static final Logger log = Logger.getLogger(ListByOneCategoryMenuCommand.class);
    private final UserService userService;
    private final MagazineService magazineService;
    private String magazineId;

    public AdminCabinetCommand(UserService userService, MagazineService magazineService) {
        this.userService = userService;
        this.magazineService = magazineService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Admin cabinet command starts");

        String block = request.getParameter("change_block");
        String userId = request.getParameter("user_id");
        if (block != null && userId != null) {
            int changed = Integer.parseInt(block) == 0 ? 1 : 0;
            try {
                userService.update(userId, "blocked", String.valueOf(changed));
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

        try {
            List<User> userList = userService.findAll();
            request.getSession().setAttribute("userList", userList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


        Magazine magazine = getMagazineById(request);
        if (magazine != null) {
            setParameters(request, magazine);
            updateMagazine(magazine);
        }

        if(magazine != null || request.getSession().getAttribute("magazineList") == null) {
            try {
                List<Magazine> magazineList = magazineService.findAll();
                request.getSession().setAttribute("magazineList", magazineList);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }


        log.debug("User cabinet command finish");
        return Path.PAGE__ADMIN_CABINET;
    }

    private void updateMagazine(Magazine magazine) throws CommandException{
        try {
            magazineService.updateNow(magazine);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void setParameters(HttpServletRequest request, Magazine magazine){
        String magazineName = request.getParameter("magazine_name_change_value");
        if (magazineName != null && !magazineName.equals("")) {
            magazine.setName(magazineName);
        }
        String magazinePrice = request.getParameter("magazine_price_change_value");
        if (magazinePrice != null && !magazinePrice.equals("")) {
            magazine.setPrice(new BigDecimal(magazinePrice));
        }
        String magazineImage = request.getParameter("magazine_image_change_value");
        if (magazineImage != null && !magazinePrice.equals("")) {
            magazine.setImage(magazineImage);
        }
    }

    private Magazine getMagazineById(HttpServletRequest request) throws CommandException {
        magazineId = request.getParameter("magazine_id");
        Optional<Magazine> optionalMagazine;
        if (magazineId != null && !magazineId.equals("")) {
            try {
                optionalMagazine = magazineService.findById(magazineId);
                if (optionalMagazine.isPresent()) {
                    return optionalMagazine.get();
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return null;
    }
}
