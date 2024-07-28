package com.example.qr_test.Entity;

public class Milk {
    private Long id;
    private String name;
    private String type;
    private String brand;
    private String origin;
    private Double price;
    private String flavor;
    private String ingredient;
    private String description;
    private String unit;
    private String capacity;

    public Milk(String name, String type, String brand, String origin, Double price, String flavor, String ingredient, String description, String unit, String capacity) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.origin = origin;
        this.price = price;
        this.flavor = flavor;
        this.ingredient = ingredient;
        this.description = description;
        this.unit = unit;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
