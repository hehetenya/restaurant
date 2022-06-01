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

    /**
     * Extracts data about all receipts that exist in a database
     * @return list of receipts from a database
     * @throws DBException if any SQLException was caught
     */
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

    /**
     * Creates an object of Receipt class by a given result set.
     * @param rs Result set from a database
     * @return new receipt object
     * @throws DBException if any SQLException was caught
     */
    private static Receipt createReceipt(ResultSet rs) throws DBException {
        Receipt receipt;
        try{
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

    /**
     * Extracts data about dishes and their amount in a receipt from a database.
     * @param receiptId id of a receipt
     * @return map of dishes and their amount
     * @throws DBException if any SQLException was caught
     */
    private static Map<Dish, Integer> getDishesByReceiptId(int receiptId) throws DBException{
        Map<Dish, Integer> dishes = new HashMap<>();
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.FIND_DISHES_BY_RECEIPT_ID)){
            ps.setInt(1, receiptId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    dishes.put(DishManager.getDishByID(rs.getInt(2)), rs.getInt(3));
                }
            }catch (SQLException ex){
                throw new DBException("Cannot put dishes into map", ex);
            }
        }catch (SQLException ex){
            throw new DBException("Cannot find dishes by receipt id", ex);
        }
        return dishes;
    }

    /**
     * Updates status of a given receipt in a database.
     * @param receiptId id of a receipt
     * @param status new status
     * @throws DBException if any SQLException was caught
     */
    public static void changeStatus(int receiptId, Status status) throws DBException {
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

    /**
     * Creates a list of receipts from a data extracted from a database by user's id.
     * @param userId user's id
     * @return list of user's receipts
     * @throws DBException if any SQLException was caught
     */
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

    /**
     * Calculates amount of pages that are needed to display a given amount of elements.
     * There can be max 10 elements on one page.
     * @param amount of items that need to be displayed
     * @return an amount of pages needed to display these items
     */
    public static int countMaxPage(int amount){
        if(amount%10==0){
            return amount/10;
        }else{
            return amount/10 + 1;
        }
    }

    /**
     * Creates a sublist from a given list of receipts that will display into a certain page.
     * There can be max 10 receipts on page.
     * @param receipts list of receipts
     * @param currentPage a number of page on which receipts should be displayed
     * @return sublist of a given list of receipts
     */
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
