package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EquipmentConstructorTest {

    private Object readField(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    @Test
    @DisplayName("Constructor runs without exception")
    void constructorRuns() {
        assertDoesNotThrow(() -> new Equipment("Tripod", 18000));
    }

    @Test
    @DisplayName("Constructor stores name into the field")
    void constructorStoresName() throws Exception {
        Equipment item = new Equipment("Tripod", 18000);
        assertEquals("Tripod", readField(item, "name"));
    }

    @Test
    @DisplayName("Constructor stores daily rate into the field")
    void constructorStoresDailyRate() throws Exception {
        Equipment item = new Equipment("Lampu Studio", 12000);
        assertEquals(12000.0, (double) readField(item, "dailyRate"), 0.001);
    }
}
