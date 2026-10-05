import java.util.Locale;
import java.util.Objects;

/**
 * Represents a salaried Worker: pay is a fixed fraction of an annual salary
 * every week, regardless of hours actually worked. The hoursWorked parameter
 * on calculateWeeklyPay/displayWeeklyPay is kept only so this class can be
 * used polymorphically wherever a Worker is expected.
 *
 * @author Your Name
 * @version 1.0
 */
public class SalaryWorker extends Worker
{
    private double annualSalary;

    /** Weeks per year the annual salary is divided across. */
    public static final double WEEKS_PER_YEAR = 52.0;

    /**
     * Full constructor: builds a SalaryWorker from all five Person/salary
     * fields, calling Worker's constructor for the inherited fields.
     * Worker's hourlyPayRate is not meaningful for a salaried employee, so
     * it is passed through as 0.0.
     *
     * @param firstName    the person's given name
     * @param lastName     the person's family name
     * @param ID           the unchanging identifier
     * @param title        the name prefix, e.g. "Mr." or "Dr."
     * @param YOB          the four digit year of birth
     * @param annualSalary the fixed annual salary in dollars
     */
    public SalaryWorker(String firstName, String lastName, String ID, String title, int YOB, double annualSalary)
    {
        super(firstName, lastName, ID, title, YOB, 0.0);
        this.annualSalary = annualSalary;
    }

    /**
     * Overloaded constructor for a SalaryWorker with no title prefix.
     *
     * @param firstName    the person's given name
     * @param lastName     the person's family name
     * @param ID           the unchanging identifier
     * @param YOB          the four digit year of birth
     * @param annualSalary the fixed annual salary in dollars
     */
    public SalaryWorker(String firstName, String lastName, String ID, int YOB, double annualSalary)
    {
        super(firstName, lastName, ID, YOB, 0.0);
        this.annualSalary = annualSalary;
    }

    /**
     * Copy constructor.
     * @param s the SalaryWorker to copy
     */
    public SalaryWorker(SalaryWorker s)
    {
        super(s);
        this.annualSalary = s.annualSalary;
    }

    /**
     * @return the fixed annual salary in dollars
     */
    public double getAnnualSalary()
    {
        return annualSalary;
    }

    /**
     * Sets the annual salary. Negative values are ignored.
     * @param annualSalary the new annual salary in dollars, zero or greater
     */
    public void setAnnualSalary(double annualSalary)
    {
        if (annualSalary >= 0.0)
        {
            this.annualSalary = annualSalary;
        }
    }

    /**
     * Calculates the pay for one week: always one fifty-second of the annual
     * salary. The hoursWorked parameter is intentionally unused here, kept
     * only so this method can override Worker's version polymorphically.
     *
     * @param hoursWorked ignored for a salaried worker
     * @return the fixed weekly pay
     */
    @Override
    public double calculateWeeklyPay(double hoursWorked)
    {
        return annualSalary / WEEKS_PER_YEAR;
    }

    /**
     * Prints this week's pay to the console, noting that it is a fixed
     * fraction of the annual salary rather than dependent on hours worked.
     *
     * @param hoursWorked ignored for a salaried worker
     */
    @Override
    public void displayWeeklyPay(double hoursWorked)
    {
        double weeklyPay = calculateWeeklyPay(hoursWorked);
        System.out.printf("%s: Weekly pay = $%.2f (1/52 of annual salary $%.2f, hours worked not a factor)%n",
                fullName(), weeklyPay, annualSalary);
    }

    /**
     * Renders this SalaryWorker as one comma separated value record: every
     * inherited Person field followed by the annual salary. The inherited,
     * unused hourlyPayRate field is intentionally left out here.
     *
     * @return the record as firstName,lastName,ID,title,YOB,annualSalary
     */
    @Override
    public String toCSV()
    {
        return getFirstName() + "," + getLastName() + "," + getID() + "," + getTitle() + "," + getYOB()
                + "," + String.format(Locale.US, "%.2f", annualSalary);
    }

    /**
     * Renders this SalaryWorker as a JSON object literal, including the
     * annual salary alongside every inherited Person field.
     *
     * @return a JSON String describing this SalaryWorker
     */
    @Override
    public String toJSON()
    {
        return "{\"firstName\":\"" + getFirstName() + "\","
                + "\"lastName\":\"" + getLastName() + "\","
                + "\"ID\":\"" + getID() + "\","
                + "\"title\":\"" + getTitle() + "\","
                + "\"YOB\":" + getYOB() + ","
                + "\"annualSalary\":" + String.format(Locale.US, "%.2f", annualSalary) + "}";
    }

    /**
     * Renders this SalaryWorker as an XML element, including the annual
     * salary alongside every inherited Person field.
     *
     * @return an XML String describing this SalaryWorker
     */
    @Override
    public String toXML()
    {
        return "<SalaryWorker>"
                + "<firstName>" + getFirstName() + "</firstName>"
                + "<lastName>" + getLastName() + "</lastName>"
                + "<ID>" + getID() + "</ID>"
                + "<title>" + getTitle() + "</title>"
                + "<YOB>" + getYOB() + "</YOB>"
                + "<annualSalary>" + String.format(Locale.US, "%.2f", annualSalary) + "</annualSalary>"
                + "</SalaryWorker>";
    }

    /**
     * Human readable rendering of this SalaryWorker. Built independently of
     * Worker's toString() since the inherited hourlyPayRate is not
     * meaningful for a salaried employee.
     *
     * @return a readable String showing every field, inherited and own
     */
    @Override
    public String toString()
    {
        return "SalaryWorker{firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", ID='" + getID() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", YOB=" + getYOB() +
                ", annualSalary=" + String.format(Locale.US, "%.2f", annualSalary) +
                '}';
    }

    /**
     * Two SalaryWorker objects are equal when they are the same runtime
     * type, every inherited field matches, and the annual salary matches.
     *
     * @param o the object to compare against
     * @return true when o is a SalaryWorker holding the same field values
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SalaryWorker that = (SalaryWorker) o;
        return Double.compare(that.annualSalary, annualSalary) == 0;
    }

    /**
     * Hash code consistent with equals().
     * @return the hash code for this SalaryWorker
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), annualSalary);
    }
}