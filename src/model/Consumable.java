package model;

public class Consumable extends Item {
    private static final long serialVersionUID = 1L;
    private ConsumableType consumableType;
    private int quantity;
    private static final double DEFAULT_WEIGHT = 0.2;

    public Consumable(ConsumableType consumableType, String name, int quantity) {
        super(name, DEFAULT_WEIGHT);
        this.consumableType = consumableType;
        this.quantity = quantity;
    }

    public Consumable(ConsumableType consumableType, String name, int quantity, ItemRarity itemRarity) {
        super(name, DEFAULT_WEIGHT, itemRarity);
        this.consumableType = consumableType;
        this.quantity = quantity;
    }

    public ConsumableType getConsumableType() { return consumableType; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int amount) { quantity += amount; }
    public void removeQuantity(int amount) { quantity -= amount; }

    @Override
    public String getType() { return "CONSUMABLE"; }

    @Override
    public String toString() {
        return "CONSUMABLE, " + itemRarity + ", " + name + ", " + weight + " kg, x" + quantity;
    }
}