
import model.*;

// Main.java
public class Main {
    public static void main(String[] args) {

        Weapon weapon = new Weapon(WeaponType.CROSSBOW);
        System.out.println(weapon);

        Weapon weapon1 = new Weapon(WeaponType.DAGGER, ItemRarity.EPIC);
        System.out.println(weapon1);

        Consumable con = new Consumable(ConsumableType.DRIED_MEAT, 3, ItemRarity.RARE);
        System.out.println(con);

        Armor armor = new Armor(ArmorType.BELT);
        System.out.println(armor);

        ConsumableType[] types = ConsumableType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d) %s%n", i, types[i].name());


        }
    }
}



