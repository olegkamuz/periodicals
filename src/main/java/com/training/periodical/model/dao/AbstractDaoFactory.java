package com.training.periodical.model.dao;

import com.training.periodical.model.repository.SubscriptionRepository;
import org.apache.log4j.Logger;

public abstract class AbstractDaoFactory implements IDaoFactory  {
    private static final long serialVersionUID = -8758810611945335453L;
    private static final Logger log = Logger.getLogger(SubscriptionRepository.class);
    private static IDaoFactory daoFactory;

    public static IDaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (AbstractDaoFactory.class) {
                if (daoFactory == null) {
                    JDBCDaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                    log.info("using jdbc");
                }
            }
        }
        return daoFactory;
    }
}
