package manager;

import model.*;
import data.*;
import exception.*;
import java.io.*;

public class FileManager {

    private static final String DEFAULT_SAVE_FILE = "inventory_save.dat";

    public static void autoSave(Inventory inventory) {
        try{
            saveToFile(inventory, DEFAULT_SAVE_FILE);
        } catch (FileHandlingException e) {
            System.out.println("Failed to save inventory: " + e.getMessage());
        }
    }

    public static void autoLoad(Inventory inventory) {
        File saveFile = new File(DEFAULT_SAVE_FILE);
        if (saveFile.exists()) {
            try {
                loadFromFile(inventory, DEFAULT_SAVE_FILE);
            } catch (FileHandlingException e) {
                System.out.println("Failed to load inventory: " + e.getMessage());
            }
        }
    }

    public static void deleteSaveFile() {
        File saveFile = new File(DEFAULT_SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    private static void saveToFile(Inventory inventory, String filename) throws FileHandlingException {
        InventoryData data = new InventoryData(inventory.getPlayer(), inventory.getItems());

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(data);
        }catch (IOException e) {
            throw new FileHandlingException("Failed to save inventory: " + e.getMessage());
        }
    }

    private static void loadFromFile(Inventory inventory, String filename) throws FileHandlingException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            InventoryData data = (InventoryData) in.readObject();

            inventory.clear();

            Player player = inventory.getPlayer();
            player.reset();
            for (int i = 1; i < data.getPlayerLevel(); i++) {
                player.levelUp();
            }

            for (Item item : data.getItems()) {
                try {
                    inventory.addItem(item);
                } catch (InventoryFullException | WeightLimitException e) {
                    // Skip items that don't fit when loading
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileHandlingException("File not found: " + filename);
        } catch (IOException e) {
            throw new FileHandlingException("Error reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new FileHandlingException("Incompatible file format");
        }
    }
}
