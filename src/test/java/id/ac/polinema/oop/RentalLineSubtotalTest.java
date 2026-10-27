package id.ac.polinema.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * RentalLine does not exist in the starter code, so this test finds it
 * via reflection instead of referencing it directly.
 */
class RentalLineSubtotalTest {

    private static Object newRentalLine(Equipment item, int days) throws Exception {
        Class<?> c;
        try {
            c = Class.forName("id.ac.polinema.oop.RentalLine");
        } catch (ClassNotFoundException e) {
            return fail("Class RentalLine not found — create it as described in the README/UML diagram");
        }
        try {
            return c.getConstructor(Equipment.class, int.class).newInstance(item, days);
        } catch (NoSuchMethodException e) {
            return fail("Constructor RentalLine(Equipment, int) not found");
        }
    }

    private static Object invoke(Object target, String name) throws Throwable {
        Method m;
        try {
            m = target.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            return fail("Method " + name + "() not found on RentalLine");
        }
        try {
            return m.invoke(target);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    @DisplayName("getSubtotal returns daily rate times days")
    void subtotalIsDailyRateTimesQuantity() throws Throwable {
        Equipment tripod = new Equipment("Tripod", 18000);
        Object rentalLine = newRentalLine(tripod, 3);
        assertEquals(54000.0, (double) invoke(rentalLine, "getSubtotal"), 0.001);
    }

    @Test
    @DisplayName("getEquipment returns the same Equipment object (association)")
    void getEquipmentReturnsSameObject() throws Throwable {
        Equipment lampu = new Equipment("Lampu Studio", 12000);
        Object rentalLine = newRentalLine(lampu, 1);
        assertSame(lampu, invoke(rentalLine, "getEquipment"));
    }

    @Test
    @DisplayName("getDays returns the stored days")
    void getDaysReturnsQuantity() throws Throwable {
        Equipment lampu = new Equipment("Lampu Studio", 12000);
        Object rentalLine = newRentalLine(lampu, 4);
        assertEquals(4, (int) invoke(rentalLine, "getDays"));
    }
}
