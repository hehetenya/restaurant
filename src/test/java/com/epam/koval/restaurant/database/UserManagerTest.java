package com.epam.koval.restaurant.database;

import com.epam.koval.restaurant.database.entity.Category;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.DBException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserManagerTest {

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
    void getUserByLogin() throws SQLException, DBException {
        User expected = createTestUser(1);
        mockResultSet(rs, expected);
        setUp();
        try (MockedStatic<DBManager> dbmMock = mockStatic(DBManager.class)) {
            dbmMock.when(DBManager::getInstance).thenReturn(dbm);
            User actual = UserManager.getUserByLogin(expected.getLogin());
            assertEquals (expected, actual);
        }
    }

    @Test
    void signUp() throws SQLException {
        User expected = createTestUser(3);
        mockResultSet(rs, expected);
        setUp();
        try (MockedStatic<DBManager> dbmMock = mockStatic(DBManager.class)) {
            dbmMock.when(DBManager::getInstance).thenReturn(dbm);
            assertThrows (DBException.class, () -> UserManager.signUp(expected.getLogin(), expected.getPassword()));
        }
    }

    @Test
    void logIn() throws SQLException, DBException {
        User expected = createTestUser(3);
        mockResultSet(rs, expected);
        setUp();
        try (MockedStatic<DBManager> dbmMock = mockStatic(DBManager.class)) {
            dbmMock.when(DBManager::getInstance).thenReturn(dbm);
            User actual = UserManager.logIn(expected.getLogin(), expected.getPassword());
            assertEquals (expected, actual);
        }
    }

    @Test
    void getUserById() throws SQLException, DBException {
        User expected = createTestUser(2);
        mockResultSet(rs, expected);
        setUp();
        try (MockedStatic<DBManager> dbmMock = mockStatic(DBManager.class)) {
            dbmMock.when(DBManager::getInstance).thenReturn(dbm);
            User actual = UserManager.getUserById(2);
            assertEquals (expected, actual);
        }
    }

    private static User createTestUser(int i) {
        return new User(i, "user" + i, "pass" +i, 1);
    }

    private static void mockResultSet(ResultSet rs, User u) throws SQLException {
        when(rs.getInt(1)).thenReturn(u.getId());
        when(rs.getString(2)).thenReturn(u.getLogin());
        when(rs.getString(3)).thenReturn(u.getPassword());
        when(rs.getInt(4)).thenReturn(u.getRoleId());
    }
}