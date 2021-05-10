package ua.kharkov.knure.dkolesnikov.st4example.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB manager. Works with Apache Derby DB.
 * Only the required DAO methods are defined!
 *
 * @author D.Kolesnikov
 */
public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class);
    private static final Object monitor = new Object();


    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

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
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() {
        Connection con = null;
        try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");

			DataSource ds = (DataSource)envContext.lookup("jdbc/ST4DB");

            con = ds.getConnection();
            con.setTransactionIsolation(((BasicDataSource)ds).getDefaultTransactionIsolation());
            con.setAutoCommit(((BasicDataSource)ds).getDefaultAutoCommit());
		} catch (NamingException | SQLException e) {
        log.error("Cannot obtain a connection from th pool", e);
		}
		return con;
    }

	private DBManager() {}


    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

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
     * @param con Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

/**************** THIS METHOD IS NOT USED IN THE PROJECT *******/
    /**
     * Returns a DB connection. This method is just for a example how to use the
     * DriverManager to obtain a DB connection. It does not use a pool
     * connections and not used in this project. It is preferable to use
     * {@link #getConnection()} method instead.
     *
     * @return A DB connection.
     */
    public Connection getConnectionWithDriverManager() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/st4db?user=springuser&password=ThePassword");
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        return connection;
    }
/**************************************************************/

}