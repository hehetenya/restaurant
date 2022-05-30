package com.epam.koval.restaurant.database.entity;

public enum Category {
    PIZZA(1, "Pizza"),
    SUSHI(2, "Sushi"),
    BURGER(3, "Burger"),
    DRINK(4, "Drink"),
    SALAD(5, "Salad"),
    DESSERT(6, "Dessert");

    private final int id;
    private final String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category getCategoryById(int id){
        for (Category s: values()) {
            if(s.id == id){
                return s;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
