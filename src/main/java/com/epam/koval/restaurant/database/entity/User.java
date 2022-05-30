package com.epam.koval.restaurant.database.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private int roleId;

    public User(int id, String login, String password, int roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoleId() {
        return roleId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
