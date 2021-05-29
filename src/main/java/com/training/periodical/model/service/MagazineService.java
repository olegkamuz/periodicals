package com.training.periodical.model.service;

import com.training.periodical.entity.Magazine;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.MagazineDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MagazineService extends AbstractService<Magazine>{

    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    public List<Magazine> findMagazineByThemeName(String themeName) throws ServiceException{
        try (MagazineDao magazineDao = daoFactory.createMagazineDao()){
               return magazineDao.findMagazineByThemeName(themeName);
        } catch (DaoException e){
            throw createServiceException("findMagazineByThemeName", e);
        }
    }

    @Override
    public Optional<Magazine> findById(long id) throws ServiceException {
        return Optional.empty();
    }

    public BigDecimal findSumPriceByIds(String[] magazineIds) throws ServiceException{
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
