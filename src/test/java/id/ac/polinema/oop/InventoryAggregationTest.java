package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryAggregationTest {

    @Test
    @DisplayName("All fields of Inventory are private")
    void allFieldsPrivate() {
        for (Field f : Inventory.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("A new inventory is empty")
    void newInventoryIsEmpty() {
        assertEquals(0, new Inventory().getEquipmentCount());
    }

    @Test
    @DisplayName("addEquipment increases the equipment count")
    void addIncreasesCount() {
        Inventory inventory = new Inventory();
        inventory.addEquipment(new Equipment("Tripod", 18000));
        inventory.addEquipment(new Equipment("Lampu Studio", 12000));
        assertEquals(2, inventory.getEquipmentCount());
    }

    @Test
    @DisplayName("findEquipment returns the same object that was added (aggregation)")
    void findReturnsSameObject() {
        Inventory inventory = new Inventory();
        Equipment tripod = new Equipment("Tripod", 18000);
        inventory.addEquipment(tripod);
        assertSame(tripod, inventory.findEquipment("Tripod"));
    }

    @Test
    @DisplayName("findEquipment returns null when the name is in the inventory")
    void findUnknownReturnsNull() {
        Inventory inventory = new Inventory();
        inventory.addEquipment(new Equipment("Tripod", 18000));
        assertNull(inventory.findEquipment("Drone"));
    }

    @Test
    @DisplayName("A full inventory (10 items) ignores further additions")
    void fullInventoryIgnoresExtraItems() {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 12; i++) {
            inventory.addEquipment(new Equipment("Gear " + i, 10000));
        }
        assertEquals(10, inventory.getEquipmentCount());
    }
}
