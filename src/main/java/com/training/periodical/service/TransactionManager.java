package com.training.periodical.service;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private Connection con;

    public TransactionManager(Connection con) {
        this.con = con;
    }

    /**
     * Commits and close the given connection.
     *
     */
    public void commitAndClose() {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     */
    public void rollbackAndClose() {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
