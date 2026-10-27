package id.ac.polinema.oop;

/**
 * The lab inventory: holds up to 10 {@link Equipment} objects.
 *
 * This is an AGGREGATION: the Equipment objects are created outside
 * and passed in — they can exist without the Inventory.
 *
 * Complete every method body below (replace the
 * {@code throw new UnsupportedOperationException(...)} lines).
 * Use a plain array, NOT List/ArrayList.
 *
 * Declare the fields yourself: see the class diagram in the README
 * (all fields must be private).
 */
public class Inventory {

    /**
     * Creates an empty inventory: initialize the array with capacity 10
     * and the counter with 0.
     */
    public Inventory() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Adds equipment at index {@code equipmentCount}, then increments the counter.
     * When the inventory is full (10 items), do nothing.
     *
     * @param equipment the equipment to add
     */
    public void addEquipment(Equipment equipment) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Searches the stored equipment by exact name.
     *
     * @param name equipment name to look for
     * @return the matching Equipment, or {@code null} when not found
     */
    public Equipment findEquipment(String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getEquipmentCount() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
