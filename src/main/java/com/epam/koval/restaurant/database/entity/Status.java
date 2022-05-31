package com.epam.koval.restaurant.database.entity;

public enum Status {
    NEW(1, "New"),
    APPROVED(2,"Approved"),
    CANCELLED(3, "Cancelled"),
    COOKING(4, "Cooking"),
    DELIVERING(5, "Delivering"),
    RECEIVED(6, "Received");

    private final int id;
    private final String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public static Status getStatusById(int id){
        for (Status s: values()) {
            if(s.id == id){
                return s;
            }
        }
        return null;
    }

    public static Status getStatusByName(String name){
        for (Status s: values()) {
            if(s.name.equals(name)){
                return s;
            }
        }
        return null;
    }
}
