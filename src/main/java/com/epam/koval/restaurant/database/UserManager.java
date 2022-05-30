package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    public static User getUserByLogin(String login) throws DBException {
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.FIND_USER_BY_LOGIN)){
            ps.setString(1, login);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("returning user by login");
                    return createUser(rs);
                } else{
                    return null;
                }
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find user by login", ex);
        }
    }

    public static User signUp(String login, String password) throws DBException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBManager.getInstance().getConnection();
            ps = con.prepareStatement(DBManager.SIGN_UP);
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, password);
            if(ps.executeUpdate() == 0){
                throw new DBException("Sign up failed");
            }
            con.commit();
            return getUserByLogin(login);
        }catch (SQLException ex){
            if(con != null) DBManager.rollback(con);
            throw new DBException("Cannot signup", ex);
        }finally {
            DBManager.close(con);
            DBManager.close(ps);
        }

    }

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

    public static User getUserById(int id) throws DBException{
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.FIND_USER_BY_ID)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("Returning user in getUserById");
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
