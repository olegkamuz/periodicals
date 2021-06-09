package com.training.periodical.model.repository;

import com.training.periodical.entity.Theme;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.ThemeDao;

import java.util.List;
import java.util.Optional;

public class ThemeRepository extends AbstractRepository<Theme> {
    private static final long serialVersionUID = 7039501575798690288L;

    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    /**
     * Returns all themes.
     *
     * @return List of themes entities.
     */
    public List<Theme> findAll() throws RepositoryException {
        try (ThemeDao themeDao = daoFactory.createThemeDao()) {
                return themeDao.findAll();
        } catch (DaoException e) {
            throw createRepositoryException("findAll", e);
        }
    }

    @Override
    public int update(Theme entity) throws RepositoryException {
        return 0;
    }

    @Override
    public int delete(long id) throws RepositoryException {
        return 0;
    }

    @Override
    public List<Theme> findPage(int pageSize, int offSet) {
        return null;
    }

    @Override
    public int create(Theme entity) throws RepositoryException {
        return 0;
    }

    @Override
    public Optional<Theme> findById(long id) throws RepositoryException {
        return Optional.empty();
    }
}
