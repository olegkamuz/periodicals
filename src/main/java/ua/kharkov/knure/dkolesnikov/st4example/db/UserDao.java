package ua.kharkov.knure.dkolesnikov.st4example.db;

import ua.kharkov.knure.dkolesnikov.st4example.db.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Data access object for User entity.
 */
public class UserDao {
    private static final Logger log = Logger.getLogger(UserDao.class);

    private static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM `user` WHERE login=?";

    private static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM `user` WHERE id=?";

    private static final String SQL_UPDATE_USER =
            "UPDATE `user` SET password=?, first_name=?, last_name=?, locale_name=?" +
                    "	WHERE id=?";

    private static final String SQL__UPDATE_BALANCE_WHERE_ID =
            "UPDATE `user` SET balance=? WHERE id=?";

    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     */
    public User findUserById(Long id) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     */
    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    /**
     * Update user.
     *
     * @param user user to update.
     */
    public void updateUser(User user) {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            updateUser(con, user);
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    // //////////////////////////////////////////////////////////
    // Entity access methods (for transactions)
    // //////////////////////////////////////////////////////////

    /**
     * Update user.
     *
     * @param user to update.
     * @throws SQLException
     */
    public void updateUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
        int k = 1;
        pstmt.setString(k++, user.getPassword());
        pstmt.setString(k++, user.getFirstName());
        pstmt.setString(k++, user.getLastName());
        pstmt.setString(k++, user.getLocaleName());
        pstmt.setLong(k, user.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void setPreparedStatementUpdateBalance(Connection con, Long userId, BigDecimal balance) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL__UPDATE_BALANCE_WHERE_ID);
        ps.setBigDecimal(1, balance);
        ps.setLong(2, userId);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Extracts a user from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getLong(Fields.ENTITY__ID));
                user.setLogin(rs.getString(Fields.USER__LOGIN));
                user.setPassword(rs.getString(Fields.USER__PASSWORD));
                user.setBalance(rs.getBigDecimal(Fields.USER__BALANCE));
                user.setFirstName(rs.getString(Fields.USER__FIRST_NAME));
                user.setLastName(rs.getString(Fields.USER__LAST_NAME));
                user.setLocaleName(rs.getString(Fields.USER__LOCALE_NAME));
                user.setRoleId(rs.getInt(Fields.USER__ROLE_ID));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
