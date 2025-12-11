package manager;

import model.*;
import model.*;
import exception.*;
import java.util.ArrayList;
import java.util.List;

// Inventory.java
public class Inventory {
    private List<Item> items;
    private Player player;
    private static final double MAX_WEIGHT = 50.0;

    public Inventory(Player player) {
        this.items = new ArrayList<>();
        this.player = player;
    }

    public void addItem(Item item) throws InventoryFullException, WeightLimitException {
        // Check for consumable stacking
        if (item instanceof Consumable) {
            Consumable newCons = (Consumable) item;
            for (Item existing : items) {
                if (existing instanceof Consumable) {
                    Consumable existingCons = (Consumable) existing;
                    if (existingCons.getName().equals(newCons.getName()) &&
                            existingCons.getConsumableType() == newCons.getConsumableType()) {
                        // Check weight before stacking
                        if (getCurrentWeight() + item.getWeight() > MAX_WEIGHT) {
                            throw new WeightLimitException("Adding this quantity would exceed max weight of " + MAX_WEIGHT + " kg");
                        }
                        existingCons.addQuantity(newCons.getQuantity());
                        return;
                    }
                }
            }
        }

        // Check slot limit
        if (getUsedSlots() >= player.getMaxInventorySlots()) {
            throw new InventoryFullException("Inventory is full! (" + player.getMaxInventorySlots() + " slots)");
        }

        // Check weight limit
        if (getCurrentWeight() + item.getWeight() > MAX_WEIGHT) {
            throw new WeightLimitException("Adding this item would exceed max weight of " + MAX_WEIGHT + " kg");
        }

        items.add(item);
    }

    public void removeItem(int index) throws InvalidItemException {
        if (index < 0 || index >= items.size()) {
            throw new InvalidItemException("Invalid item index: " + index);
        }
        items.remove(index);
    }

    public int getUsedSlots() {
        return items.size();
    }

    public double getCurrentWeight() {
        double total = 0;
        for (Item item : items) {
            total += item.getWeight();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public List<Item> getItems() {return items;}

    public Player getPlayer() {
        return player;
    }
}



