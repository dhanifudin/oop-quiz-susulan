package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquipmentGetterTest {

    @Test
    @DisplayName("getName returns the stored name")
    void getNameReturnsName() {
        Equipment item = new Equipment("Tripod", 18000);
        assertEquals("Tripod", item.getName());
    }

    @Test
    @DisplayName("getDailyRate returns the stored daily rate")
    void getDailyRateReturnsDailyRate() {
        Equipment item = new Equipment("Lampu Studio", 12000);
        assertEquals(12000.0, item.getDailyRate(), 0.001);
    }
}
