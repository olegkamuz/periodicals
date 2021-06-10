package com.training.periodical.model.command;

import com.sun.istack.internal.NotNull;
import com.training.periodical.Path;
import com.training.periodical.entity.Magazine;
import com.training.periodical.entity.User;
import com.training.periodical.model.builder.MagazineBuilder;
import com.training.periodical.model.repository.MagazineRepository;
import com.training.periodical.model.repository.RepositoryException;
import com.training.periodical.model.repository.UserRepository;
import com.training.periodical.util.validator.Valid;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminCabinetCommand extends AbstractCommand {
    private static final Logger log = Logger.getLogger(AdminCabinetCommand.class);
    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;
    private final MagazineBuilder magazineBuilder;

    public AdminCabinetCommand(UserRepository userRepository, MagazineRepository magazineRepository, MagazineBuilder magazineBuilder) {
        this.userRepository = userRepository;
        this.magazineRepository = magazineRepository;
        this.magazineBuilder = magazineBuilder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.info("Admin cabinet command starts");
        this.request = request;

        updateLocaleIfRequested();

        User user = getUserById();
        changeUser(user);
        showUsersList(request);

        Magazine magazine = getMagazineById(request);
        changeMagazine(magazine);
        if (magazine != null || request.getSession().getAttribute("magazineList") == null) {
            showMagazinesList();
        }

        if (isMagazineDataComplete()) {
            try {
                magazineRepository.create(buildMagazine());
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }

        showMagazinesList();

        log.debug("Admin cabinet command finish");
        return Path.PAGE__ADMIN_CABINET;
    }

    private Magazine buildMagazine() {
        return magazineBuilder
                .setName(request.getParameter("magazine_name_add_value"))
                .setPrice(new BigDecimal(request.getParameter("magazine_price_add_value")))
                .setImage(request.getParameter("magazine_image_add_value"))
                .setThemeId(Long.parseLong(request.getParameter("magazine_theme_id_add_value")))
                .build();
    }

    private boolean isMagazineDataComplete() {
        String magazineName = request.getParameter("magazine_name_add_value");
        String magazinePrice = request.getParameter("magazine_price_add_value");
        String magazineImage = request.getParameter("magazine_price_add_value");
        String magazineThemeId = request.getParameter("magazine_theme_id_add_value");
        return magazineName != null &&
                magazinePrice != null &&
                magazineImage != null &&
                magazineThemeId != null;
    }

    private User getUserById() throws CommandException {
        String userId = request.getParameter("user_id");
        Optional<User> user;
        if (userId != null && !userId.equals("")) {
            try {
                user = userRepository.findById(Long.parseLong(userId));
                if (user.isPresent()) {
                    return user.get();
                }
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }
        return null;
    }

    private void showMagazinesList() throws CommandException {
        try {
            List<Magazine> magazineList = magazineRepository.findAll();
            request.getSession().setAttribute("magazineList", magazineList);
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private void changeMagazine(Magazine magazine) throws CommandException {
        if (magazine == null) return;

        Object updateMagazine = request.getParameter("update_magazine");
        if (updateMagazine != null) {
            updateMagazine(magazine);
        }

        Object deleteMagazine = request.getParameter("delete_magazine");
        if (deleteMagazine != null) {
            Long magazineId = Long.parseLong(request.getParameter("magazine_id"));
            try {
                magazineRepository.delete(magazineId);
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }
    }

    private void updateMagazine(Magazine magazine) throws CommandException {
        setParameters(magazine);
        try {
            magazineRepository.update(magazine);
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }

    private void changeUser(User user) throws CommandException {
        String block = request.getParameter("change_block");
        String userId = request.getParameter("user_id");
        if (block != null && userId != null) {
            int changed = Integer.parseInt(block) == 0 ? 1 : 0;
            user.setBlocked(changed);
            try {
                userRepository.update(user);
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }

    }

    private void showUsersList(HttpServletRequest request) throws CommandException {
        try {
            List<User> userList = userRepository.findAllClients();
            request.getSession().setAttribute("userList", userList);
        } catch (RepositoryException e) {
            throw new CommandException(e);
        }
    }


    private void setParameters(Magazine magazine) {
        String magazineName = request.getParameter("magazine_name_change_value");
        if(Valid.notNullNotEmpty(magazineName)){
            magazine.setName(magazineName);
        }
        String magazinePrice = request.getParameter("magazine_price_change_value");
        if(Valid.notNullNotEmpty(magazinePrice)){
            magazine.setPrice(new BigDecimal(magazinePrice));
        }
        String magazineImage = request.getParameter("magazine_image_change_value");
        if(Valid.notNullNotEmpty(magazineImage)){
            magazine.setImage(magazineImage);
        }
        String magazineTheme = request.getParameter("magazine_theme_id_change_value");
        if(Valid.notNullNotEmpty(magazineTheme)){
            magazine.setThemeId(Long.parseLong(magazineTheme));
        }
    }

    private Magazine getMagazineById(HttpServletRequest request) throws CommandException {
        String magazineId = request.getParameter("magazine_id");
        Optional<Magazine> optionalMagazine;
        if(Valid.notNullNotEmpty(magazineId)){
            try {
                optionalMagazine = magazineRepository.findById(Long.parseLong(magazineId));
                if (optionalMagazine.isPresent()) {
                    return optionalMagazine.get();
                }
            } catch (RepositoryException e) {
                throw new CommandException(e);
            }
        }
        return null;
    }

    private List<Magazine> getMagazinesPage(int currentPage) {
        List<Magazine> page = new ArrayList<>();
        try {
            int offset = PAGE_SIZE * (currentPage - 1);
            return magazineRepository.findPage(PAGE_SIZE, offset);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return page;
    }
}
