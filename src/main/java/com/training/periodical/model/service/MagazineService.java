package com.training.periodical.model.service;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.MagazineDao;
import com.training.periodical.model.dao.UserDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MagazineService extends AbstractService<Magazine> {

    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public List<Magazine> findMagazineByThemeName(String themeName) throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findMagazineByThemeName(themeName);
        } catch (DaoException e) {
            throw createServiceException("findMagazineByThemeName", e);
        }
    }

    public int update(Magazine magazine) throws ServiceException{
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.update(magazine);
        } catch (DaoException e) {
            throw createServiceException("update", e);
        }
    }

    public int updateNow(Magazine magazine) throws ServiceException{
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.updateNow(magazine);
        } catch (DaoException e) {
            throw createServiceException("update", e);
        }
    }

    public List<Magazine> findAll() throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findAll();
        } catch (DaoException e) {
            throw createServiceException("findAll", e);
        }
    }

    @Override
    public Optional<Magazine> findById(String id) throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findById(id);
        } catch (DaoException e) {
            throw createServiceException("findAll", e);
        }
    }

    public BigDecimal findSumPriceByIds(String[] magazineIds) throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findSumPriceByIds(magazineIds);
        } catch (DaoException e) {
            throw createServiceException("findSumPriceByIds", e);
        }
    }

    @Override
    protected ServiceException createServiceException(
            String methodName,
            DaoException e) {
        return new ServiceException("exception in " +
                methodName + " method at " +
                this.getClass().getSimpleName(), e);
    }
}
