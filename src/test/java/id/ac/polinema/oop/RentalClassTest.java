package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Rental does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class RentalClassTest {

    private static Class<?> loadClass() {
        try {
            return Class.forName("id.ac.polinema.oop.Rental");
        } catch (ClassNotFoundException e) {
            return fail("Class Rental not found — create it as described in the README/UML diagram");
        }
    }

    private static Object newRental(Renter renter) throws Exception {
        try {
            return loadClass().getConstructor(Renter.class).newInstance(renter);
        } catch (NoSuchMethodException e) {
            return fail("Constructor Rental(Renter) not found");
        }
    }

    private static Object invoke(Object target, String name) throws Throwable {
        Method m;
        try {
            m = target.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            return fail("Method " + name + "() not found on Rental");
        }
        try {
            return m.invoke(target);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Class Rental exists in package id.ac.polinema.oop")
    void classExists() {
        loadClass();
    }

    @Test
    @DisplayName("All fields of Rental are private")
    void allFieldsPrivate() {
        for (Field f : loadClass().getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                    "Field '" + f.getName() + "' must be private");
        }
    }

    @Test
    @DisplayName("getRenter returns the same Renter object (association)")
    void getRenterReturnsSameObject() throws Throwable {
        Renter sari = new Renter("R001", "Sari Wulandari");
        Object rental = newRental(sari);
        assertSame(sari, invoke(rental, "getRenter"));
    }

    @Test
    @DisplayName("A new rental has zero lines")
    void newRentalIsEmpty() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        assertEquals(0, (int) invoke(rental, "getLineCount"));
    }
}
