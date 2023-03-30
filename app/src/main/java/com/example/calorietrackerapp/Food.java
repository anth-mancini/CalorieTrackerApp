package com.example.calorietrackerapp;

public class Food {
    private String name;
    private int calorieCount;

    public Food(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
    }

    // Constructor that takes a single string argument, used to create a Food object from a string
    public Food(String foodString) {
        String[] parts = foodString.split(",");
        this.name = parts[0];
        this.calorieCount = Integer.parseInt(parts[1]);
    }

    // Method to convert a Food object to a string representation
    @Override
    public String toString() {
        return name + "," + calorieCount;
    }

    public String getName() {
        return name;
    }

    public int getCalorieCount() {
        return calorieCount;
    }
}

