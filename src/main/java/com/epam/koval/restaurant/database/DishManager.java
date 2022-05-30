package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.Category;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.exeptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DishManager {

    public static Dish getDishByID(int id) throws DBException{
        Dish dish;
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.GET_DISH_BY_ID)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    dish = new Dish(id, rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            Category.getCategoryById(rs.getInt(5)),
                            rs.getString(6));
                    return dish;
                } else{
                    return null;
                }
            }
        } catch (SQLException ex){
            throw new DBException("Cannot get dish by id", ex);
        }
    }

    public static List<Dish> getAllDishes() throws DBException {
        List<Dish> dishes = new ArrayList<>();
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(DBManager.GET_ALL_DISHES)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                dishes.add(getDishByID(rs.getInt(1)));
            }
        }catch(SQLException ex){
            throw new DBException("Cannot get all dishes", ex);
        }
        return dishes;
    }

    public static List<Dish> getDishesByCategory(String category) throws DBException {
        List<Dish> allDishes = getAllDishes();
        return allDishes.stream()
                .filter((a) -> a.getCategory().getName().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static List<Dish> sortBy(List<Dish> dishes, String sortBy) {
        if(sortBy.equalsIgnoreCase("price")){
            dishes = dishes.stream()
                    .sorted(Comparator.comparingInt(Dish::getPrice))
                    .collect(Collectors.toList());
        }else if(sortBy.equalsIgnoreCase("name")){
            dishes = dishes.stream()
                    .sorted(Comparator.comparing(Dish::getName))
                    .collect(Collectors.toList());
        }else{
            dishes = dishes.stream()
                    .sorted(Comparator.comparing(Dish::getCategory))
                    .collect(Collectors.toList());
        }
        return dishes;
    }

    public static List<Dish> getDishesOnPage(List<Dish> dishes, int currentPage) {
        int begin = (currentPage-1)*10;
        if(dishes.size()>0 && dishes.size() < begin+10){
            dishes = dishes.subList(begin, dishes.size());
        }else {
            dishes = dishes.subList(begin, begin+10);
        }
        return dishes;
    }
}
