package ui;

import manager.Inventory;
import model.*;
import exception.*;
import java.util.Scanner;

// ItemAdder.java
public class ItemAdder {

    public static void addWeapon(Scanner scanner, Inventory inventory)
            throws InventoryFullException, WeightLimitException {
        System.out.println("\n=== WEAPONS ===");
        WeaponType[] weapons = WeaponType.values();
        for (int i = 0; i < weapons.length; i++) {
            System.out.printf("%d. %-20s %.1f kg%n", i, weapons[i], weapons[i].getWeight());
        }
        System.out.print("\nChoose weapon: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 0 && choice < weapons.length) {
            Weapon weapon = new Weapon(weapons[choice]);
            inventory.addItem(weapon);
        } else {
            throw new InvalidItemException("Invalid weapon choice");
        }
    }

    public static void addArmor(Scanner scanner, Inventory inventory)
            throws InventoryFullException, WeightLimitException {
        System.out.println("\n=== ARMOR ===");
        ArmorType[] armors = ArmorType.values();
        for (int i = 0; i < armors.length; i++) {
            System.out.printf("%d. %-20s %.1f kg%n", i, armors[i], armors[i].getWeight());
        }
        System.out.print("\nChoose armor: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 0 && choice < armors.length) {
            Armor armor = new Armor(armors[choice]);
            inventory.addItem(armor);
        } else {
            throw new InvalidItemException("Invalid armor choice");
        }
    }

    public static void addConsumable(Scanner scanner, Inventory inventory)
            throws InventoryFullException, WeightLimitException {
        System.out.println("\n=== CONSUMABLE TYPE ===");
        ConsumableType[] types = ConsumableType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s%n", i, types[i]);
        }
        System.out.print("\nChoose type: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 0 && choice < types.length) {
            System.out.print("Name (e.g., Apple): ");
            String name = scanner.nextLine();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Consumable consumable = new Consumable(types[choice], name, quantity);
            inventory.addItem(consumable);
        } else {
            throw new InvalidItemException("Invalid consumable type");
        }
    }
}