package model;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected double weight;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public abstract String getType();
}