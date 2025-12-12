
import model.*;

// Main.java
public class Main {
    public static void main(String[] args) {

        Weapon weapon = new Weapon(WeaponType.CROSSBOW);
        System.out.println(weapon);

        Weapon weapon1 = new Weapon(WeaponType.DAGGER, ItemRarity.EPIC);
        System.out.println(weapon1);

        Consumable con = new Consumable(ConsumableType.DRINK, "Health Potion", 3);
        System.out.println(con);

        Armor armor = new Armor(ArmorType.BELT);
        System.out.println(armor);

    }
}



