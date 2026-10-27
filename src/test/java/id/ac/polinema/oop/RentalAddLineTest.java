package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Rental does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class RentalAddLineTest {

    private static Object newRental(Renter renter) throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.Rental");
        } catch (ClassNotFoundException e) {
            return fail("Class Rental not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor(Renter.class).newInstance(renter);
        } catch (NoSuchMethodException e) {
            return fail("Constructor Rental(Renter) not found");
        }
    }

    private static void addLine(Object rental, Equipment item, int days) throws Throwable {
        Method m;
        try {
            m = rental.getClass().getMethod("addLine", Equipment.class, int.class);
        } catch (NoSuchMethodException e) {
            fail("Method addLine(Equipment, int) not found on Rental — "
                    + "Rental must create the RentalLine internally (composition)");
            return;
        }
        try {
            m.invoke(rental, item, days);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static int lineCount(Object rental) throws Throwable {
        try {
            return (int) rental.getClass().getMethod("getLineCount").invoke(rental);
        } catch (NoSuchMethodException e) {
            return fail("Method getLineCount() not found on Rental");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("addLine(Equipment, int) exists — Rental builds the RentalLine itself (composition)")
    void addLineSignatureExists() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        addLine(rental, new Equipment("Tripod", 18000), 1);
    }

    @Test
    @DisplayName("Each addLine increases the line count by one")
    void addLineIncreasesCount() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        addLine(rental, new Equipment("Tripod", 18000), 2);
        assertEquals(1, lineCount(rental));
        addLine(rental, new Equipment("Lampu Studio", 12000), 1);
        assertEquals(2, lineCount(rental));
    }
}
