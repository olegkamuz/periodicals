package ua.kharkov.knure.dkolesnikov.st4example.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DB manager.
 *
 */
public class DBManager {
    private static final Logger log = Logger.getLogger(DBManager.class);
    private static final Object monitor = new Object();

    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (monitor) {
            if (instance == null) {
                instance = new DBManager();
            }
        }
        return instance;
    }

    /**
     * Returns a database connection from the Pool Connections.
     *
     * @return a database connection.
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource ds = (DataSource) envContext.lookup("jdbc/periodical");

            con = ds.getConnection();
        } catch (NamingException | SQLException e) {
            log.error("Cannot obtain a connection from th pool", e);
        }
        return con;
    }

    private DBManager() {
    }

    /**
     * Commits and close the given connection.
     *
     * @param con Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
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
     * @param con Connection to be rolled back and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}