import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for SafeInputObj.
 * <p>
 * The methods are interactive, so each test uses the Scanner constructor to
 * feed canned keystrokes in from a String instead of the keyboard. That lets
 * the retry logic be tested: a bad entry followed by a good one should return
 * the good value.
 */
class SafeInputObjTest
{
    /**
     * Helper that builds a SafeInputObj reading from canned input.
     *
     * @param keystrokes the lines the "user" types, newline separated
     * @return a SafeInputObj wired to that input
     */
    private SafeInputObj feed(String keystrokes)
    {
        return new SafeInputObj(new Scanner(keystrokes));
    }

    @Test
    @DisplayName("Default constructor attaches a Scanner")
    void testDefaultConstructor()
    {
        SafeInputObj in = new SafeInputObj();
        assertNotNull(in.getPipe());
    }

    @Test
    @DisplayName("Scanner constructor uses the Scanner it is given")
    void testScannerConstructor()
    {
        Scanner s = new Scanner("hello\n");
        SafeInputObj in = new SafeInputObj(s);
        assertSame(s, in.getPipe());
    }

    @Test
    @DisplayName("setPipe replaces the input source")
    void testSetPipe()
    {
        SafeInputObj in = feed("first\n");
        Scanner replacement = new Scanner("second\n");
        in.setPipe(replacement);
        assertEquals("second", in.getNonZeroLenString("Enter a word"));
    }

    @Test
    @DisplayName("getNonZeroLenString skips empty entries")
    void testGetNonZeroLenString()
    {
        SafeInputObj in = feed("\n\nCincinnati\n");
        assertEquals("Cincinnati", in.getNonZeroLenString("Enter a city"));
    }

    @Test
    @DisplayName("getInt retries until a whole number arrives")
    void testGetInt()
    {
        SafeInputObj in = feed("abc\n42\n");
        assertEquals(42, in.getInt("Enter a number"));
    }

    @Test
    @DisplayName("getDouble retries until a number arrives")
    void testGetDouble()
    {
        SafeInputObj in = feed("xyz\n3.5\n");
        assertEquals(3.5, in.getDouble("Enter a number"), 0.001);
    }

    @Test
    @DisplayName("getRangedInt rejects values outside the range")
    void testGetRangedInt()
    {
        SafeInputObj in = feed("99\n0\n7\n");
        assertEquals(7, in.getRangedInt("Pick a number", 1, 10));
    }

    @Test
    @DisplayName("getRangedDouble rejects values outside the range")
    void testGetRangedDouble()
    {
        SafeInputObj in = feed("500.0\n12.75\n");
        assertEquals(12.75, in.getRangedDouble("Enter a price", 0, 100), 0.001);
    }

    @Test
    @DisplayName("getYNConfirm returns true for Y and false for N")
    void testGetYNConfirm()
    {
        assertTrue(feed("maybe\ny\n").getYNConfirm("Continue?"));
        assertFalse(feed("N\n").getYNConfirm("Continue?"));
    }

    @Test
    @DisplayName("getRegExString retries until the pattern matches")
    void testGetRegExString()
    {
        SafeInputObj in = feed("12-345-678\n123-45-6789\n");
        assertEquals("123-45-6789", in.getRegExString("Enter an SSN", "\\d{3}-\\d{2}-\\d{4}"));
    }

    @Test
    @DisplayName("toString reports that a pipe is attached")
    void testToString()
    {
        assertEquals("SafeInputObj{pipe attached=true}", feed("x\n").toString());
    }
}
