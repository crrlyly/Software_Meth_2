package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Util.Date;
import fleet.*;


/**
 * This class tests the compareTo method of the vehicle super class with all of the vehicle subclasses.
 *
 * @author Alec Cotler
 */

public class VehicleCompareToTest {

    // Campus comparison determines outcome
    @Test
    void testCompareToNegativeByCampus() {
        Vehicle v1 = new Sedan("12345S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Camden);
        Vehicle v2 = new Sedan("67890S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Newark);
        assertTrue(v1.compareTo(v2) < 0, "Expected Camden < Newark by city name");
    }

    // Make comparison determines outcome (same campus)
    @Test
    void testCompareToNegativeByMake() {
        Vehicle v1 = new Utility("11111D", new Date("01/01/2024"), Make.CHEVY, 2000, Campus.Busch);
        Vehicle v2 = new Utility("22222D", new Date("01/01/2024"), Make.TOYOTA, 2000, Campus.Cook);
        assertTrue(v1.compareTo(v2) < 0, "Expected CHEVY < TOYOTA by make name");
    }

    // Date comparison determines outcome (same campus & make)
    @Test
    void testCompareToNegativeByDate() {
        Vehicle v1 = new Truck("33333X", new Date("01/01/2020"), Make.HONDA, 3000, Campus.Newark);
        Vehicle v2 = new Truck("44444X", new Date("01/01/2024"), Make.HONDA, 3000, Campus.Newark);
        assertTrue(v1.compareTo(v2) < 0, "Expected earlier date < later date");
    }


    @Test
    void testCompareToEqualSedan() {
        Vehicle v1 = new Sedan("55555S", new Date("06/01/2024"), Make.HONDA, 4000, Campus.Camden);
        Vehicle v2 = new Sedan("55555S", new Date("06/01/2024"), Make.HONDA, 4000, Campus.Camden);
        assertEquals(0, v1.compareTo(v2), "Expected identical Sedans to compare equal");
    }

    @Test
    void testCompareToEqualUtility() {
        Vehicle v1 = new Utility("66666D", new Date("07/01/2024"), Make.FORD, 4000, Campus.Newark);
        Vehicle v2 = new Utility("66666D", new Date("07/01/2024"), Make.FORD, 4000, Campus.Newark);
        assertEquals(0, v1.compareTo(v2), "Expected identical Utilities to compare equal");
    }

    @Test
    void testCompareToEqualTruck() {
        Vehicle v1 = new Truck("77777X", new Date("08/01/2024"), Make.CHEVY, 4000, Campus.Livingston);
        Vehicle v2 = new Truck("77777X", new Date("08/01/2024"), Make.CHEVY, 4000, Campus.Cook);
        assertEquals(0, v1.compareTo(v2), "Expected identical Trucks to compare equal");
    }



    // Campus comparison determines outcome
    @Test
    void testCompareToPositiveByCampus() {
        Vehicle v1 = new Sedan("88888S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Newark);
        Vehicle v2 = new Sedan("99999S", new Date("01/01/2024"), Make.FORD, 1000, Campus.Camden);
        assertTrue(v1.compareTo(v2) > 0, "Expected Newark > Camden by city name");
    }

    // Make comparison determines outcome
    @Test
    void testCompareToPositiveByMake() {
        Vehicle v1 = new Utility("11112D", new Date("01/01/2024"), Make.TOYOTA, 2000, Campus.Cook);
        Vehicle v2 = new Utility("22223D", new Date("01/01/2024"), Make.CHEVY, 2000, Campus.Livingston);
        assertTrue(v1.compareTo(v2) > 0, "Expected TOYOTA > CHEVY by make name");
    }

    // Date comparison determines outcome
    @Test
    void testCompareToPositiveByDate() {
        Vehicle v1 = new Truck("33334X", new Date("01/01/2025"), Make.HONDA, 3000, Campus.Newark);
        Vehicle v2 = new Truck("44445X", new Date("01/01/2020"), Make.HONDA, 3000, Campus.Newark);
        assertTrue(v1.compareTo(v2) > 0, "Expected later date > earlier date");
    }
}
