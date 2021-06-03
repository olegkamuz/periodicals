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

    public int deleteNow(Long magazineId) throws ServiceException{
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.deleteNow(magazineId);
        } catch (DaoException e) {
            throw createServiceException("deleteNow", e);
        }
    }

    public int update(Magazine magazine) throws ServiceException{
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.update(magazine);
        } catch (DaoException e) {
            throw createServiceException("update", e);
        }
    }

    @Override
    public int delete(long id) throws ServiceException {
        return 0;
    }

    public int getCount() throws ServiceException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.getCount();
        } catch (DaoException e) {
            throw createServiceException("getCount", e);
        }
    }

    public int updateNow(Magazine magazine) throws ServiceException{
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.updateNow(magazine);
        } catch (DaoException e) {
            throw createServiceException("updateNow", e);
        }
    }

    @Override
    public List<Magazine> findAll() throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findAll();
        } catch (DaoException e) {
            throw createServiceException("findAll", e);
        }
    }

    public List<Magazine> findPage(int limit, int offset) throws ServiceException {
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()) {
            return magazineDao.findPage(limit, offset);
        } catch (DaoException e) {
            throw createServiceException("findPage", e);
        }
    }

    @Override
    public int create(Magazine magazine) throws ServiceException {
        try(MagazineDao magazineDao = daoFactory.createMagazineDao()){
            return magazineDao.create(magazine);
        } catch (DaoException e) {
            throw createServiceException("create", e);
        }
    }

    @Override
    public Optional<Magazine> findById(long id) throws ServiceException {
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
