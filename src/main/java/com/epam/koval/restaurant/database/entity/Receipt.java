package com.epam.koval.restaurant.database.entity;

import java.util.Map;

public class Receipt {
    private  int id;
    private  User user;
    private  Status status;
    private Map<Dish, Integer> dishes;
    private int total;

    public Receipt() {
    }

    public Receipt(int id, User user, Status status, Map<Dish, Integer> dishes) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.dishes = dishes;
    }

    public Receipt(int id, User user, Status status) {
        this.id = id;
        this.user = user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Status getStatus() {
        return status;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public int getTotal() {
        return total;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", dishes=" + dishes +
                '}';
    }

    public void countTotal() {
        total = 0;
        for (Dish d:
             dishes.keySet()) {
            total += (d.getPrice() * dishes.get(d));
        }
    }
}
