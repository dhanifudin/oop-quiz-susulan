package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Rental does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class RentalDiscountTest {

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
        try {
            rental.getClass().getMethod("addLine", Equipment.class, int.class)
                    .invoke(rental, item, days);
        } catch (NoSuchMethodException e) {
            fail("Method addLine(Equipment, int) not found on Rental");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static double finalTotal(Object rental) throws Throwable {
        try {
            return (double) rental.getClass().getMethod("getFinalTotal").invoke(rental);
        } catch (NoSuchMethodException e) {
            return fail("Method getFinalTotal() not found on Rental");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Total below 100000 gets no discount")
    void belowThresholdNoDiscount() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        addLine(rental, new Equipment("Tripod", 18000), 2); // 36000
        assertEquals(36000.0, finalTotal(rental), 0.001);
    }

    @Test
    @DisplayName("Total exactly 100000 gets the 10% discount")
    void exactThresholdGetsDiscount() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        addLine(rental, new Equipment("Paket Kamera", 100000), 1);
        assertEquals(90000.0, finalTotal(rental), 0.001);
    }

    @Test
    @DisplayName("Total above 100000 gets the 10% discount")
    void aboveThresholdGetsDiscount() throws Throwable {
        Object rental = newRental(new Renter("R001", "Sari Wulandari"));
        addLine(rental, new Equipment("Tripod", 18000), 5); // 90000
        addLine(rental, new Equipment("Lampu Studio", 12000), 5);   // 60000 -> 150000
        assertEquals(135000.0, finalTotal(rental), 0.001);
    }
}
