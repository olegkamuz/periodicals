package com.training.periodical.model.repository;

import com.training.periodical.entity.Subscription;
import com.training.periodical.model.dao.AbstractDaoFactory;
import com.training.periodical.model.dao.DaoException;
import com.training.periodical.model.dao.IDaoFactory;
import com.training.periodical.model.dao.SubscriptionDao;
import com.training.periodical.model.dao.UserDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


/**
 * Repository for Subscription entity
 * @see Subscription
 *
 */
public class SubscriptionRepository extends AbstractRepository<Subscription> {
    private static final long serialVersionUID = -1935724741540882617L;
    private static final Logger log = Logger.getLogger(SubscriptionRepository.class);
    private final IDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    /**
     * Transaction to create subscriptions and update user balance
     *
     * @param userId user id
     * @param magazineIds list of magazine ids
     * @param userBalance user balance
     * @throws RepositoryException
     */
    public void createSubscriptionPurchase(Long userId, List<String> magazineIds, BigDecimal userBalance) throws RepositoryException {
        try (SubscriptionDao subscriptionDao = daoFactory.createSubscriptionDao();
             Connection connection = subscriptionDao.getConnection();
             UserDao userDao = daoFactory.createUserDao(connection)) {
            subscriptionDao.createSubscription(userId, magazineIds, userDao, userBalance);
            log.info(this.getClass().getSimpleName() + " creating subscription using Daos");
        } catch (DaoException | SQLException e) {
            throw createRepositoryException("createSubscriptionPurchase", e);
        }
    }

    public int countByCompositeKey(long userId, long magazineId) throws RepositoryException {
        try(SubscriptionDao subscriptionDao = daoFactory.createSubscriptionDao()) {
            return subscriptionDao.countByCompositeKey(userId, magazineId);
        } catch (DaoException e){
            throw createRepositoryException("countByCompositeKey", e);
        }
    }

    @Override
    public int create(Subscription entity) throws RepositoryException {
        return 0;
    }

    @Override
    public Optional<Subscription> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Subscription> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public int update(Subscription entity) throws RepositoryException {
        return 0;
    }

    @Override
    public int delete(long id) throws RepositoryException {
        return 0;
    }

    @Override
    public List<Subscription> findPage(int pageSize, int offSet) {
        return null;
    }

}



















