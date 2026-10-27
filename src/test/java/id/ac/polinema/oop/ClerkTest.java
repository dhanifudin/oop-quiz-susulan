package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Clerk and Rental do not exist in the starter code, so this test finds
 * them via reflection instead of referencing them directly.
 */
class ClerkTest {

    private static Class<?> rentalClass() {
        try {
            return Class.forName("id.ac.polinema.oop.Rental");
        } catch (ClassNotFoundException e) {
            return fail("Class Rental not found — create it as described in the README/UML diagram");
        }
    }

    private static Object newRentalWithTotal48000() throws Throwable {
        Object rental;
        try {
            rental = rentalClass().getConstructor(Renter.class)
                    .newInstance(new Renter("R001", "Sari Wulandari"));
        } catch (NoSuchMethodException e) {
            return fail("Constructor Rental(Renter) not found");
        }
        try {
            Method addLine = rental.getClass().getMethod("addLine", Equipment.class, int.class);
            addLine.invoke(rental, new Equipment("Tripod", 18000), 2); // 36000
            addLine.invoke(rental, new Equipment("Lampu Studio", 12000), 1);   // 12000
        } catch (NoSuchMethodException e) {
            return fail("Method addLine(Equipment, int) not found on Rental");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return rental;
    }

    private static Object newClerk() throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.Clerk");
        } catch (ClassNotFoundException e) {
            return fail("Class Clerk not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            return fail("Clerk needs a public no-argument constructor");
        }
    }

    private static double calculateChange(Object clerk, Object rental, double cash) throws Throwable {
        Method m;
        try {
            m = clerk.getClass().getMethod("calculateChange", rentalClass(), double.class);
        } catch (NoSuchMethodException e) {
            return fail("Method calculateChange(Rental, double) not found on Clerk — "
                    + "Clerk only DEPENDS on Rental (parameter), it must not store it");
        }
        try {
            return (double) m.invoke(clerk, rental, cash);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("Class Clerk exists with calculateChange(Rental, double)")
    void classAndMethodExist() throws Throwable {
        Object clerk = newClerk();
        Object rental = newRentalWithTotal48000();
        calculateChange(clerk, rental, 50000);
    }

    @Test
    @DisplayName("calculateChange returns cash minus final total")
    void changeIsCashMinusFinalTotal() throws Throwable {
        Object clerk = newClerk();
        Object rental = newRentalWithTotal48000(); // no discount, final total 48000
        assertEquals(2000.0, calculateChange(clerk, rental, 50000), 0.001);
    }

    @Test
    @DisplayName("Insufficient cash returns a negative change")
    void insufficientCashReturnsNegative() throws Throwable {
        Object clerk = newClerk();
        Object rental = newRentalWithTotal48000();
        assertEquals(-8000.0, calculateChange(clerk, rental, 40000), 0.001);
    }
}
