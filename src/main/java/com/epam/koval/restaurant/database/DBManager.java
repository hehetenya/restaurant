package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.exeptions.AppException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class DBManager {

    public static final String LOG_IN = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
    public static final String SIGN_UP = "INSERT INTO user (login, password) VALUE (?, ?)";

    public static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";

    public static final String GET_ALL_DISHES = "SELECT * FROM dish";
    public static final String GET_DISH_BY_ID = "SELECT * FROM dish WHERE id = ?";

    public static final String GET_ALL_RECEIPTS = "SELECT * FROM receipt ORDER BY create_date DESC";
    public static final String GET_RECEIPTS_BY_USER_ID = "SELECT * FROM receipt WHERE user_id LIKE ? ORDER BY create_date DESC";
    public static final String CHANGE_RECEIPT_STATUS = "UPDATE receipt SET status_id = ? WHERE id = ?";
    public static final String FIND_DISHES_BY_RECEIPT_ID = "SELECT * FROM receipt_has_dish WHERE receipt_id LIKE ?";
    public static final String CREATE_NEW_RECEIPT_BY_USER_ID = "INSERT INTO receipt (user_id) VALUE (?)";
    public static final String PUT_DISH_INTO_RECEIPT = "INSERT INTO receipt_has_dish (receipt_id, dish_id, amount) VALUE (?, ?, ?)";

    public static final String GET_CART_BY_USER_ID = "SELECT * FROM cart WHERE user_id LIKE ?";
    public static final String PUT_DISH_INTO_CART = "INSERT INTO cart (user_id, dish_id, amount) VALUE (?, ?, ?)";
    public static final String UPDATE_DISH_AMOUNT_IN_CART = "UPDATE cart SET amount = ? WHERE user_id LIKE ? AND dish_id LIKE ?";
    public static final String DELETE_DISH_FROM_CART = "DELETE FROM cart WHERE user_id LIKE ? AND dish_id LIKE ?";
    public static final String CLEAN_CART = "DELETE FROM cart WHERE user_id LIKE ?";

    private static DBManager instance;
    private final DataSource ds;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/restaurant");
        } catch (NamingException e) {
            throw new AppException("Cannot init DBManager");
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void rollback(Connection con, Savepoint s) {
        try {
            con.rollback(s);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
