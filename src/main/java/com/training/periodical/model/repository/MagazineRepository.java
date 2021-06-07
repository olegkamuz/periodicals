package com.training.periodical.model.repository;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.MagazineDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MagazineRepository extends AbstractRepository<Magazine> {
    private static final long serialVersionUID = -3746123482820783199L;
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public List<Magazine> findMagazineByThemeName(String themeName) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findMagazineByThemeName(themeName);
        } catch (DaoException e) {
            throw createRepositoryException("findMagazineByThemeName", e);
        }
    }

    public List<Magazine> findSorted(String sortSubQuery) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findSorted(sortSubQuery);
        } catch (DaoException e) {
            throw createRepositoryException("findSorted", e);
        }
    }
    public List<Magazine> findSortedPaginated(String sortSubQuery, int limit, int offset) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findSortedPaginated(sortSubQuery, limit, offset);
        } catch (DaoException e) {
            throw createRepositoryException("findSorted", e);
        }
    }

    public List<Magazine> findFiltered(String filterName) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findFiltered(filterName);
        } catch (DaoException e) {
            throw createRepositoryException("findSorted", e);
        }
    }
    public List<Magazine> findFilteredPaginated(String filterName, int limit, int offset) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findFilteredPaginated(filterName, limit, offset);
        } catch (DaoException e) {
            throw createRepositoryException("findSorted", e);
        }
    }
    public List<Magazine> findFilteredSortedPaginated(String filterName, String sortSubQuery, int limit, int offset) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findFilteredSortedPaginated(filterName, sortSubQuery, limit, offset);
        } catch (DaoException e) {
            throw createRepositoryException("findSorted", e);
        }
    }

    public List<Magazine> findPage(int limit, int offset) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findPage(limit, offset);
        } catch (DaoException e) {
            throw createRepositoryException("findPage", e);
        }
    }


    public int deleteNow(Long magazineId) throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.deleteNow(magazineId);
        } catch (DaoException e) {
            throw createRepositoryException("deleteNow", e);
        }
    }

    public int update(Magazine magazine) throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.update(magazine);
        } catch (DaoException e) {
            throw createRepositoryException("update", e);
        }
    }

    @Override
    public int delete(long id) throws RepositoryException {
        return 0;
    }

    public int getCount() throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.getCount();
        } catch (DaoException e) {
            throw createRepositoryException("getCount", e);
        }
    }
    public int getCountFiltered(String filterName) throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.getCountFiltered(filterName);
        } catch (DaoException e) {
            throw createRepositoryException("getCount", e);
        }
    }


    public int updateNow(Magazine magazine) throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.updateNow(magazine);
        } catch (DaoException e) {
            throw createRepositoryException("updateNow", e);
        }
    }

    @Override
    public List<Magazine> findAll() throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findAll();
        } catch (DaoException e) {
            throw createRepositoryException("findAll", e);
        }
    }


    @Override
    public int create(Magazine magazine) throws RepositoryException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()){
            return magazineDao.create(magazine);
        } catch (DaoException e) {
            throw createRepositoryException("create", e);
        }
    }

    @Override
    public Optional<Magazine> findById(long id) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findById(id);
        } catch (DaoException e) {
            throw createRepositoryException("findAll", e);
        }
    }

    public BigDecimal findSumPriceByIds(List<String> magazineIds) throws RepositoryException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findSumPriceByIds(magazineIds);
        } catch (DaoException e) {
            throw createRepositoryException("findSumPriceByIds", e);
        }
    }

    @Override
    protected RepositoryException createRepositoryException(
            String methodName,
            DaoException e) {
        return new RepositoryException("exception in " +
                methodName + " method at " +
                this.getClass().getSimpleName(), e);
    }
}
