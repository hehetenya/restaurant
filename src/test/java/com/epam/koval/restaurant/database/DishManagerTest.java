package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.Category;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.exeptions.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DishManagerTest {

    private Connection c = mock(Connection.class);

    private PreparedStatement ps = mock(PreparedStatement.class);

    private ResultSet rs = mock(ResultSet.class);

    private DBManager dbm = mock(DBManager.class);

    private void setUp() throws SQLException {
        when(dbm.getConnection()).thenReturn(c);
        when(c.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void getDishByIdTest() throws DBException, SQLException {
        Dish expected = createTestDish(1);
        mockResultSet(rs, expected);
        setUp();
        try (MockedStatic<DBManager> dbmMock = mockStatic(DBManager.class)) {
            dbmMock.when(DBManager::getInstance).thenReturn(dbm);
            Dish result = DishManager.getDishByID(1);
            assertEquals (expected, result);
        }
    }

    @Test
    public void sortDishesByCategoryTest(){
        List<Dish> expected = createTestDishes(6);
        List<Dish> actual = DishManager.sortBy(expected, "category");
        assertEquals(expected, actual);
    }

    @Test
    public void sortDishesByNameTest(){
        List<Dish> expected = createTestDishes(6);
        List<Dish> actual = DishManager.sortBy(expected, "name");
        assertEquals(expected, actual);
    }

    @Test
    public void sortDishesByPriceTest(){
        List<Dish> expected = createTestDishes(6);
        List<Dish> actual = DishManager.sortBy(expected, "price");
        assertEquals(expected, actual);
    }

    @Test
    public void getDishesOnPageTest(){
        List<Dish> dishes = createTestDishes(12);
        List<Dish> page1expected = dishes.subList(0, 10);
        List<Dish> page2expected = dishes.subList(10, 12);
        assertEquals(page1expected, DishManager.getDishesOnPage(dishes, 1));
        assertEquals(page2expected, DishManager.getDishesOnPage(dishes, 2));
    }

    private static Dish createTestDish(int i) {
        int categoryId = i;
        while(categoryId>6){
            categoryId-=6;
        }
        return new Dish(i, "Dish " + i, i, i, Category.getCategoryById(categoryId), "Description " + i);
    }

    private static List<Dish> createTestDishes(int amount) {
        List<Dish> dishes = new ArrayList<>();
        for (int i = 1; i <= amount; ++i) {
            dishes.add(createTestDish(i));
        }
        return dishes;
    }

    private static void mockResultSet(ResultSet rs, Dish d) throws SQLException {
        when(rs.getInt(1)).thenReturn(d.getId());
        when(rs.getString(2)).thenReturn(d.getName());
        when(rs.getInt(3)).thenReturn(d.getCategory().getId());
        when(rs.getInt(4)).thenReturn(d.getPrice());
        when(rs.getInt(5)).thenReturn(d.getWeight());
        when(rs.getString(6)).thenReturn(d.getDescription());
    }
}