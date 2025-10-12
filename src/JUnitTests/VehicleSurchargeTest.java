package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Util.Date;
import fleet.*;

/**
 * This class tests the Surcharge methods of all of the vehicle subclasses.
 *
 * @author Alec Cotler
 */

public class VehicleSurchargeTest {
    @Test
    void testNoSurchargeSedan() {
        Sedan sedan = new Sedan("12345S", new Date("01/01/2020"), Make.TOYOTA, 1000, Campus.Busch);
        assertEquals(-1, sedan.surcharge(100, false));
    }

    @Test
    void testNoSurchargeUtility() {
        Utility utility = new Utility("54321D", new Date("01/01/2020"), Make.FORD, 2000, Campus.Cook);
        assertEquals(-1, utility.surcharge(100, false));
    }

    @Test
    void testNoSurchargeTruck() {
        Truck truck = new Truck("67890X", new Date("01/01/2020"), Make.CHEVY, 3000, Campus.Newark);
        assertEquals(-1, truck.surcharge(100, false));
    }


    @Test
    void testSurchargeUnderMaxSedan() {
        Sedan sedan = new Sedan("11111S", new Date("01/01/2020"), Make.HONDA, 500, Campus.Livingston);
        // .25 * 100 = 25 < 32.99
        assertEquals(25.0, sedan.surcharge(100, true));
    }

    @Test
    void testSurchargeUnderMaxUtility() {
        Utility utility = new Utility("22222D", new Date("01/01/2020"), Make.FORD, 500, Campus.Cook);
        // .3 * 100 = 30 < 35.99
        assertEquals(30.0, utility.surcharge(100, true));
    }


    @Test
    void testSurchargeOverMaxSedan() {
        Sedan sedan = new Sedan("33333S", new Date("01/01/2020"), Make.TOYOTA, 1000, Campus.Busch);
        // .25 * 200 = 50 > 32.99, capped at 32.99
        assertEquals(32.99, sedan.surcharge(200, true));
    }

    @Test
    void testSurchargeOverMaxUtility() {
        Utility utility = new Utility("44444D", new Date("01/01/2020"), Make.FORD, 1000, Campus.Livingston);
        // .3 * 200 = 60 > 35.99, capped at 35.99
        assertEquals(35.99, utility.surcharge(200, true));
    }


    @Test
    void testTruckSurcharge() {
        Truck truck = new Truck("55555X", new Date("01/01/2020"), Make.CHEVY, 1000, Campus.Camden);
        assertEquals(39.99, truck.surcharge(150, true));
    }
}
