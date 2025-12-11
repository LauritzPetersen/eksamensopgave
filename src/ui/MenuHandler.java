package ui;

import manager.*;
import model.*;
import exception.*;
import java.util.Scanner;

// MenuHandler.java - Simplified with auto-save
public class MenuHandler {
    private Scanner scanner;
    private Player player;
    private Inventory inventory;

    public MenuHandler() {
        this.scanner = new Scanner(System.in);
        this.player = new Player();
        this.inventory = new Inventory(player);

        // Auto-load on startup
        FileManager.autoLoad(inventory);
    }

    public void run() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   INVENTORY MANAGEMENT SYSTEM          ║");
        System.out.println("║   (Auto-save enabled)                  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        while (true) {
            displayMainMenu();
            int choice = getIntInput();

            if (!handleMainMenu(choice)) {
                break;
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Level: " + player.getLevel() + " | Slots: " +
                inventory.getUsedSlots() + "/" + player.getMaxInventorySlots() +
                " | Weight: " + String.format("%.1f", inventory.getCurrentWeight()) + "/50 kg");
        System.out.println("\n1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Display Inventory");
        System.out.println("4. Sort Inventory");
        System.out.println("5. Level Up");
        System.out.println("6. Reset Everything");
        System.out.println("7. Exit");
        System.out.print("\nChoice: ");
    }

    private boolean handleMainMenu(int choice) {
        switch (choice) {
            case 1:
                addItemMenu();
                break;
            case 2:
                removeItemMenu();
                break;
            case 3:
                displayInventory();
                break;
            case 4:
                sortMenu();
                break;
            case 5:
                levelUpPlayer();
                break;
            case 6:
                resetEverything();
                break;
            case 7:
                System.out.println("\nExiting... Your progress is saved!");
                return false;
            default:
                System.out.println("\nInvalid option!");
        }
        return true;
    }

    private void addItemMenu() {
        System.out.println("\n=== ADD ITEM ===");
        System.out.println("1. Weapon");
        System.out.println("2. Armor");
        System.out.println("3. Consumable");
        System.out.println("4. Cancel");
        System.out.print("\nChoice: ");

        int choice = getIntInput();

        try {
            switch (choice) {
                case 1:
                    ItemAdder.addWeapon(scanner, inventory);
                    System.out.println("\n✓ Weapon added!");
                    FileManager.autoSave(inventory); // Auto-save after adding
                    break;
                case 2:
                    ItemAdder.addArmor(scanner, inventory);
                    System.out.println("\n✓ Armor added!");
                    FileManager.autoSave(inventory); // Auto-save after adding
                    break;
                case 3:
                    ItemAdder.addConsumable(scanner, inventory);
                    System.out.println("\n✓ Consumable added!");
                    FileManager.autoSave(inventory); // Auto-save after adding
                    break;
                case 4:
                    return;
                default:
                    System.out.println("\nInvalid option!");
            }
        } catch (InventoryFullException | WeightLimitException e) {
            System.out.println("\n✗ " + e.getMessage());
        }
    }

    private void removeItemMenu() {
        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty!");
            return;
        }

        displayInventory();
        System.out.print("\nEnter item index to remove (or -1 to cancel): ");
        int index = getIntInput();

        if (index == -1) return;

        try {
            inventory.removeItem(index);
            System.out.println("\n✓ Item removed!");
            FileManager.autoSave(inventory); // Auto-save after removing
        } catch (InvalidItemException e) {
            System.out.println("\n✗ " + e.getMessage());
        }
    }

    private void sortMenu() {
        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty!");
            return;
        }

        System.out.println("\n=== SORT BY ===");
        System.out.println("1. Name");
        System.out.println("2. Weight");
        System.out.println("3. Type");
        System.out.println("4. Cancel");
        System.out.print("\nChoice: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                InventorySorter.sortByName(inventory.getItems());
                System.out.println("\n✓ Sorted by name!");
                FileManager.autoSave(inventory); // Auto-save after sorting
                break;
            case 2:
                InventorySorter.sortByWeight(inventory.getItems());
                System.out.println("\n✓ Sorted by weight!");
                FileManager.autoSave(inventory); // Auto-save after sorting
                break;
            case 3:
                InventorySorter.sortByType(inventory.getItems());
                System.out.println("\n✓ Sorted by type!");
                FileManager.autoSave(inventory); // Auto-save after sorting
                break;
            case 4:
                return;
            default:
                System.out.println("\nInvalid option!");
        }
    }

    private void levelUpPlayer() {
        if (player.canLevelUp()) {
            player.levelUp();
            System.out.println("\n✓ Leveled up to level " + player.getLevel() + "!");
            System.out.println("  New max slots: " + player.getMaxInventorySlots());
            FileManager.autoSave(inventory); // Auto-save after leveling
        } else {
            System.out.println("\nAlready at max level (6)!");
        }
    }

    private void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty.");
            return;
        }

        System.out.println("\n=== INVENTORY ===");
        for (int i = 0; i < inventory.getItems().size(); i++) {
            System.out.println(i + ". " + inventory.getItems().get(i));
        }
        System.out.println("\nSlots: " + inventory.getUsedSlots() + "/" + player.getMaxInventorySlots());
        System.out.println("Weight: " + String.format("%.2f", inventory.getCurrentWeight()) + "/50.0 kg");
    }

    private void resetEverything() {
        System.out.print("\nAre you sure you want to reset EVERYTHING? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            player.reset();
            inventory.clear();
            FileManager.deleteSaveFile(); // Delete save file
            System.out.println("\n✓ Everything has been reset!");
        } else {
            System.out.println("\nReset cancelled.");
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }
}