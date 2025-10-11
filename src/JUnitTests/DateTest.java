package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Util.Date;

class DateTest {

    @Test
    void testYearSmall() {
        Date date = new Date("01/23/999");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 1: Year too small");
    }

    @Test
    void testMonthNotInRange() {
        Date date = new Date("13/22/2025");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 2: Month not in range");
    }

    @Test
    void testDayNotInRange() {
        Date date = new Date("01/34/2024");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 3: Day not in range");
    }

    @Test
    void testNotALeapYear() {
        Date date = new Date("02/29/2100");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 4: Not a leap year");
    }

    @Test
    void testValidDate() {
        Date date = new Date("12/31/2024");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 5: Valid date");
    }

    @Test
    void testValidLeapYear() {
        Date date = new Date("02/29/2400");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        assertEquals(expectedOutput, actualOutput, "**Test case 6: Valid leap year");
    }
}