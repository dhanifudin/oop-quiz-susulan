package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * RentalLine does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class RentalLineClassTest {

    private static Class<?> loadClass() {
        try {
            return Class.forName("id.ac.polinema.oop.RentalLine");
        } catch (ClassNotFoundException e) {
            return fail("Class RentalLine not found — create it as described in the README/UML diagram");
        }
    }

    @Test
    @DisplayName("Class RentalLine exists in package id.ac.polinema.oop")
    void classExists() {
        loadClass();
    }

    @Test
    @DisplayName("All fields of RentalLine are private")
    void allFieldsPrivate() {
        for (Field f : loadClass().getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("RentalLine has a constructor (Equipment, int)")
    void hasConstructor() {
        try {
            loadClass().getConstructor(Equipment.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("Constructor RentalLine(Equipment, int) not found");
        }
    }

    @Test
    @DisplayName("RentalLine has getEquipment(), getDays(), getSubtotal() with correct return types")
    void hasGetters() {
        Class<?> c = loadClass();
        try {
            assertEquals(Equipment.class, c.getMethod("getEquipment").getReturnType(),
                    "getEquipment() must return Equipment");
            assertEquals(int.class, c.getMethod("getDays").getReturnType(),
                    "getDays() must return int");
            assertEquals(double.class, c.getMethod("getSubtotal").getReturnType(),
                    "getSubtotal() must return double");
        } catch (NoSuchMethodException e) {
            fail("Missing method on RentalLine: " + e.getMessage());
        }
    }
}
