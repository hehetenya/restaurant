package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    /**
     * Creates a new User object with data from a database extracted by user's login.
     * @param login login of a user
     * @return new User object
     * @throws DBException if any SQLException was caught
     */
    public static User getUserByLogin(String login) throws DBException {
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.GET_USER_BY_LOGIN)){
            ps.setString(1, login);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return createUser(rs);
                } else{
                    return null;
                }
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find user by login", ex);
        }
    }

    /**
     * Inserts data about new user into a database and returns a User object.
     * @param login user's login
     * @param password user's password
     * @return a User object
     * @throws DBException if any SQLException was caught
     */
    public static User signUp(String login, String password) throws DBException {
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.SIGN_UP)){
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, password);
            if(ps.executeUpdate() == 0){
                throw new DBException("Sign up failed");
            }
            return getUserByLogin(login);
        }catch (SQLException ex){
            throw new DBException("Cannot signup", ex);
        }
    }

    /**
     * Checks if user with such login and password exists in database and if so
     * creates a new User object from a result set.
     * @param login user's login
     * @param password user's password
     * @return a User object
     * @throws DBException if any SQLException was caught
     */
    public static User logIn(String login, String password) throws DBException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(DBManager.LOG_IN)){
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, password);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return createUser(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex){
            throw new DBException("Log In error", ex);
        }
    }

    private static User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
    }

    /**
     * Extracts data about a user by their id from database.
     * @param id user's id
     * @return a User object
     * @throws DBException if any SQLException was caught
     */
    public static User getUserById(int id) throws DBException{
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.GET_USER_BY_ID)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return createUser(rs);
                } else{
                    return null;
                }
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find user by id", ex);
        }
    }
}
