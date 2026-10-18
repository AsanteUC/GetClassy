/**
 * Short driver program that exercises every method of SafeInputObj so the
 * console output can be captured for the lab screen shots.
 *
 * @author Your Name
 * @version 1.0
 */
public class ObjInputTest
{
    /**
     * Program entry point.
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        SafeInputObj input = new SafeInputObj();   // default constructor uses System.in

        System.out.println("===== SafeInputObj Test =====");

        String name = input.getNonZeroLenString("Enter your name");
        System.out.println("getNonZeroLenString returned: " + name);

        int anyInt = input.getInt("Enter any whole number");
        System.out.println("getInt returned: " + anyInt);

        double anyDouble = input.getDouble("Enter any decimal number");
        System.out.println("getDouble returned: " + anyDouble);

        int ranged = input.getRangedInt("Enter a number", 1, 10);
        System.out.println("getRangedInt returned: " + ranged);

        double rangedD = input.getRangedDouble("Enter a price", 0, 100);
        System.out.println("getRangedDouble returned: " + rangedD);

        String ssn = input.getRegExString("Enter an SSN in the form xxx-xx-xxxx", "\\d{3}-\\d{2}-\\d{4}");
        System.out.println("getRegExString returned: " + ssn);

        boolean confirm = input.getYNConfirm("Did all of the methods work?");
        System.out.println("getYNConfirm returned: " + confirm);

        System.out.println("toString returned: " + input);

        System.out.println("===== All SafeInputObj methods tested =====");
    }
}
