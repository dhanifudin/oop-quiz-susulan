package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EquipmentEncapsulationTest {

    @Test
    @DisplayName("All fields of Equipment are private")
    void allFieldsPrivate() {
        for (Field f : Equipment.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("setDailyRate updates the daily rate")
    void setDailyRateUpdatesDailyRate() {
        Equipment item = new Equipment("Tripod", 18000);
        item.setDailyRate(20000);
        assertEquals(20000.0, item.getDailyRate(), 0.001);
    }

    @Test
    @DisplayName("setDailyRate ignores a negative daily rate and keeps the old daily rate")
    void setDailyRateIgnoresNegative() {
        Equipment item = new Equipment("Tripod", 18000);
        item.setDailyRate(-1000);
        assertEquals(18000.0, item.getDailyRate(), 0.001);
    }
}
