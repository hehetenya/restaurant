package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.database.entity.Receipt;
import com.epam.koval.restaurant.database.entity.Status;
import com.epam.koval.restaurant.exeptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptManager {
    public static List<Receipt> getAllReceipts() throws DBException {
        List<Receipt> receipts = new ArrayList<>();
        try(Connection con = DBManager.getInstance().getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBManager.GET_ALL_RECEIPTS);
            while (rs.next()){
                Receipt receipt = createReceipt(rs);
                receipts.add(receipt);
            }
        } catch (SQLException ex){
            throw new DBException("Cannot get all receipts", ex);
        }
        return receipts;
    }

    private static Receipt createReceipt(ResultSet rs) throws DBException {
        Receipt receipt;
        try{
            System.out.println("in try section create receipt");
            receipt = new Receipt(rs.getInt(1),
                    UserManager.getUserById(rs.getInt(2)),
                    Status.getStatusById(rs.getInt(3)));
            receipt.setDishes(getDishesByReceiptId(receipt.getId()));
            receipt.countTotal();
            System.out.println(receipt);
        } catch (SQLException ex)  {
            throw new DBException("Cannot create receipt", ex);
        }
        return receipt;
    }


    private static Map<Dish, Integer> getDishesByReceiptId(int receiptId) throws DBException{
        Map<Dish, Integer> dishes = new HashMap<>();
        System.out.println("In getDishesByReceiptId");
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.FIND_DISHES_BY_RECEIPT_ID)){
            System.out.println("in try section of getDishesByReceiptId");
            ps.setInt(1, receiptId);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("result set created");
                while(rs.next()){
                    dishes.put(DishManager.getDishByID(rs.getInt(2)), rs.getInt(3));
                    System.out.println("dish put");
                }
            }catch (SQLException ex){
                throw new DBException("Cannot put dishes into map", ex);
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find dishes by receipt id", ex);
        }
        return dishes;
    }


    public static void changeStatus(int receiptId, Status status) throws DBException {
        System.out.println("In changeStatus");
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.CHANGE_RECEIPT_STATUS)){
            ps.setInt(1, status.getId());
            ps.setInt(2, receiptId);
            if(ps.executeUpdate() == 0){
                throw new SQLException("Change status failed");
            }
        }catch (SQLException ex){
            throw new DBException("Cannot change receipt status", ex);
        }
    }

    public static List<Receipt> getReceiptsByUserId(int userId) throws DBException {
        List<Receipt> receipts = new ArrayList<>();
        try(Connection con = DBManager.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(DBManager.GET_RECEIPTS_BY_USER_ID)){
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Receipt receipt = createReceipt(rs);
                    receipts.add(receipt);
                }
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find receipts by user id", ex);
        }
        return receipts;
    }
    
    public static int countMaxPage(int amount){
        if(amount%10==0){
            return amount/10;
        }else{
            return amount/10 + 1;
        }
    }

    public static List<Receipt> getReceiptsOnPage(List<Receipt> receipts, int currentPage) {
        int begin = (currentPage-1)*10;
        if(receipts.size()>0 && receipts.size() < begin+10){
            receipts = receipts.subList(begin, receipts.size());
        }else {
            receipts = receipts.subList(begin, begin+10);
        }
        return receipts;
    }
}
