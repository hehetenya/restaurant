package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.exeptions.DBException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CartManager {

    /**
     * Extracts data about cart of certain user from database
     * @param id user's id
     * @return a map of dishes and their amount in a cart
     * @throws DBException if any SQLException was caught
     */
    public static Map<Dish, Integer> getCart(int id) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(DBManager.GET_CART_BY_USER_ID)) {
            Map<Dish, Integer> cart = new HashMap<>();
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Dish dish = DishManager.getDishByID(rs.getInt(2));
                    cart.put(dish, rs.getInt(3));
                }
                return cart;
            }
        } catch (SQLException ex) {
            throw new DBException("Cannot get cart", ex);
        }
    }

    /**
     * Inserts data about new dish in a cart of certain user into database.
     * @param userId user's id
     * @param dishId dish's id
     * @param amount amount of dish
     * @throws DBException if any SQLException was caught
     */
    public static void addDishToCart(int userId, int dishId, int amount) throws DBException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBManager.PUT_DISH_INTO_CART);
            int k = 0;
            ps.setInt(++k, userId);
            ps.setInt(++k, dishId);
            ps.setInt(++k, amount);
            if (ps.executeUpdate() == 0) {
                throw new DBException("Inserting failed");
            }
            con.commit();
        } catch (SQLException ex) {
            if (con != null) DBManager.rollback(con);
            throw new DBException("Cannot add dish to cart", ex);
        } finally {
            DBManager.close(con);
            DBManager.close(ps);
        }
    }

    /**
     * Updates amount of dish in user's cart in a database.
     * @param userId user's id
     * @param dishId dish's id
     * @param amount amount of dish that needs to be updated
     * @throws DBException if any SQLException was caught
     */
    public static void changeAmountOfDish(int userId, int dishId, int amount) throws DBException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBManager.UPDATE_DISH_AMOUNT_IN_CART);
            int k = 0;
            ps.setInt(++k, amount);
            ps.setInt(++k, userId);
            ps.setInt(++k, dishId);
            if (ps.executeUpdate() == 0) {
                throw new DBException("Inserting failed");
            }
            con.commit();
        } catch (SQLException ex) {
            if (con != null) DBManager.rollback(con);
            throw new DBException("Cannot update dish amount in cart", ex);
        } finally {
            DBManager.close(con);
            DBManager.close(ps);
        }
    }

    /**
     * Deletes dish from a cart of certain user.
     * @param userId user's id
     * @param dishId dish's id
     * @throws DBException if any SQLException was caught
     */
    public static void deleteDishFromCart(int userId, int dishId) throws DBException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBManager.DELETE_DISH_FROM_CART);
            int k = 0;
            ps.setInt(++k, userId);
            ps.setInt(++k, dishId);
            if (ps.executeUpdate() == 0) {
                throw new DBException("Deleting failed");
            }
            con.commit();
        } catch (SQLException ex) {
            if (con != null) DBManager.rollback(con);
            throw new DBException("Cannot delete dish from cart", ex);
        } finally {
            DBManager.close(con);
            DBManager.close(ps);
        }
    }

    /**
     * Creates new receipt in database and inserts data about dishes in it.
     * @param userId user's id
     * @param cart user's cart represented by a map of dishes and their amount
     * @throws DBException if any SQLException was caught
     */
    public static void submitOrder(int userId, Map<Dish, Integer> cart) throws DBException {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            int receiptId = createNewReceipt(con, userId);
            for (Dish d : cart.keySet()) {
                putDishIntoReceipt(con, receiptId, d, cart.get(d));
            }
        } catch (SQLException ex) {
            if (con != null) DBManager.rollback(con);
            throw new DBException("Cannot submit order", ex);
        } finally {
            DBManager.close(con);
        }
    }

    private static int createNewReceipt(Connection con, int userId) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(DBManager.CREATE_NEW_RECEIPT_BY_USER_ID, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Cannot create new receipt");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    con.commit();
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private static void putDishIntoReceipt(Connection con, int receiptId, Dish dish, int amount) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(DBManager.PUT_DISH_INTO_RECEIPT)) {
            int k = 0;
            ps.setInt(++k, receiptId);
            ps.setInt(++k, dish.getId());
            ps.setInt(++k, amount);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Putting dish into receipt failed");
            }
            con.commit();
        }
    }

    /**
     * Deletes all data about user's cart and dishes in it.
     * @param id user's id
     * @throws DBException if any SQLException was caught
     */
    public static void cleanCart(int id) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(DBManager.CLEAN_CART)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Cleaning cart error");
            }
        } catch (SQLException ex) {
            throw new DBException("Cannot clean cart", ex);
        }
    }
}
