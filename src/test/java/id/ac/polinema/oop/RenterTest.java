package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RenterTest {

    @Test
    @DisplayName("All fields of Renter are private")
    void allFieldsPrivate() {
        for (Field f : Renter.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("Constructor stores renterId and name, getters return them")
    void constructorAndGetters() {
        Renter c = new Renter("R001", "Sari Wulandari");
        assertEquals("R001", c.getRenterId());
        assertEquals("Sari Wulandari", c.getName());
    }

    @Test
    @DisplayName("setName updates the name")
    void setNameUpdatesName() {
        Renter c = new Renter("R001", "Sari Wulandari");
        c.setName("Sari W.");
        assertEquals("Sari W.", c.getName());
    }

    @Test
    @DisplayName("setName ignores a null name and keeps the old name")
    void setNameIgnoresNull() {
        Renter c = new Renter("R001", "Sari Wulandari");
        c.setName(null);
        assertEquals("Sari Wulandari", c.getName());
    }

    @Test
    @DisplayName("setName ignores a blank name and keeps the old name")
    void setNameIgnoresBlank() {
        Renter c = new Renter("R001", "Sari Wulandari");
        c.setName("   ");
        assertEquals("Sari Wulandari", c.getName());
    }
}
