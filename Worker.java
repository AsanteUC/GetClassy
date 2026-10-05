import java.util.Locale;
import java.util.Objects;

/**
 * Represents an hourly Worker: a Person who is paid by the hour, with
 * time-and-a-half overtime for anything worked past 40 hours in a week.
 *
 * @author Your Name
 * @version 1.0
 */
public class Worker extends Person
{
    private double hourlyPayRate;

    /** Hours per week before overtime kicks in. */
    public static final double REGULAR_HOURS_CAP = 40.0;
    /** Multiplier applied to the hourly rate for overtime hours. */
    public static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Full constructor: builds a Worker from all six fields, calling
     * Person's constructor for the inherited fields.
     *
     * @param firstName     the person's given name
     * @param lastName      the person's family name
     * @param ID            the unchanging identifier
     * @param title         the name prefix, e.g. "Mr." or "Dr."
     * @param YOB           the four digit year of birth
     * @param hourlyPayRate the pay rate in dollars per hour
     */
    public Worker(String firstName, String lastName, String ID, String title, int YOB, double hourlyPayRate)
    {
        super(firstName, lastName, ID, title, YOB);
        this.hourlyPayRate = hourlyPayRate;
    }

    /**
     * Overloaded constructor for a Worker with no title prefix.
     *
     * @param firstName     the person's given name
     * @param lastName      the person's family name
     * @param ID            the unchanging identifier
     * @param YOB           the four digit year of birth
     * @param hourlyPayRate the pay rate in dollars per hour
     */
    public Worker(String firstName, String lastName, String ID, int YOB, double hourlyPayRate)
    {
        super(firstName, lastName, ID, YOB);
        this.hourlyPayRate = hourlyPayRate;
    }

    /**
     * Copy constructor.
     * @param w the Worker to copy
     */
    public Worker(Worker w)
    {
        super(w);
        this.hourlyPayRate = w.hourlyPayRate;
    }

    /**
     * @return the hourly pay rate in dollars
     */
    public double getHourlyPayRate()
    {
        return hourlyPayRate;
    }

    /**
     * Sets the hourly pay rate. Negative values are ignored so the rate can
     * never go below zero.
     *
     * @param hourlyPayRate the new pay rate in dollars per hour, zero or greater
     */
    public void setHourlyPayRate(double hourlyPayRate)
    {
        if (hourlyPayRate >= 0.0)
        {
            this.hourlyPayRate = hourlyPayRate;
        }
    }

    /**
     * Calculates the total pay for one week. Hours up to 40 are paid at the
     * hourly rate; any hours beyond 40 are paid at time and a half.
     *
     * @param hoursWorked the number of hours worked this week
     * @return the total pay for the week
     */
    public double calculateWeeklyPay(double hoursWorked)
    {
        if (hoursWorked <= REGULAR_HOURS_CAP)
        {
            return hoursWorked * hourlyPayRate;
        }
        else
        {
            double regularPay = REGULAR_HOURS_CAP * hourlyPayRate;
            double overtimeHours = hoursWorked - REGULAR_HOURS_CAP;
            double overtimePay = overtimeHours * hourlyPayRate * OVERTIME_MULTIPLIER;
            return regularPay + overtimePay;
        }
    }

    /**
     * Prints a breakdown of this week's pay to the console: regular hours and
     * pay, overtime hours and pay, and the combined total.
     *
     * @param hoursWorked the number of hours worked this week
     */
    public void displayWeeklyPay(double hoursWorked)
    {
        double regularHours = Math.min(hoursWorked, REGULAR_HOURS_CAP);
        double regularPay = regularHours * hourlyPayRate;
        double overtimeHours = Math.max(0.0, hoursWorked - REGULAR_HOURS_CAP);
        double overtimePay = overtimeHours * hourlyPayRate * OVERTIME_MULTIPLIER;
        double totalPay = regularPay + overtimePay;

        System.out.printf("%s: %.1f regular hrs @ $%.2f/hr = $%.2f%n",
                fullName(), regularHours, hourlyPayRate, regularPay);
        System.out.printf("%s: %.1f overtime hrs @ $%.2f/hr = $%.2f%n",
                fullName(), overtimeHours, hourlyPayRate * OVERTIME_MULTIPLIER, overtimePay);
        System.out.printf("%s: Total weekly pay = $%.2f%n", fullName(), totalPay);
    }

    /**
     * Renders this Worker as one comma separated value record: every
     * inherited Person field followed by the hourly pay rate.
     *
     * @return the record as firstName,lastName,ID,title,YOB,hourlyPayRate
     */
    public String toCSV()
    {
        return super.toCSV() + "," + String.format(Locale.US, "%.2f", hourlyPayRate);
    }

    /**
     * Renders this Worker as a JSON object literal, including the hourly
     * pay rate alongside every inherited Person field.
     *
     * @return a JSON String describing this Worker
     */
    public String toJSON()
    {
        return "{\"firstName\":\"" + getFirstName() + "\","
                + "\"lastName\":\"" + getLastName() + "\","
                + "\"ID\":\"" + getID() + "\","
                + "\"title\":\"" + getTitle() + "\","
                + "\"YOB\":" + getYOB() + ","
                + "\"hourlyPayRate\":" + String.format(Locale.US, "%.2f", hourlyPayRate) + "}";
    }

    /**
     * Renders this Worker as an XML element, including the hourly pay rate
     * alongside every inherited Person field.
     *
     * @return an XML String describing this Worker
     */
    public String toXML()
    {
        return "<Worker>"
                + "<firstName>" + getFirstName() + "</firstName>"
                + "<lastName>" + getLastName() + "</lastName>"
                + "<ID>" + getID() + "</ID>"
                + "<title>" + getTitle() + "</title>"
                + "<YOB>" + getYOB() + "</YOB>"
                + "<hourlyPayRate>" + String.format(Locale.US, "%.2f", hourlyPayRate) + "</hourlyPayRate>"
                + "</Worker>";
    }

    /**
     * Human readable rendering of this Worker, built on top of Person's.
     * @return a readable String showing every field, inherited and own
     */
    @Override
    public String toString()
    {
        return "Worker{" + super.toString() + ", hourlyPayRate=" +
                String.format(Locale.US, "%.2f", hourlyPayRate) + "}";
    }

    /**
     * Two Worker objects are equal when they are the same runtime type,
     * every inherited Person field matches, and the hourly pay rate matches.
     *
     * @param o the object to compare against
     * @return true when o is a Worker holding the same field values
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Worker worker = (Worker) o;
        return Double.compare(worker.hourlyPayRate, hourlyPayRate) == 0;
    }

    /**
     * Hash code consistent with equals().
     * @return the hash code for this Worker
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), hourlyPayRate);
    }
}

