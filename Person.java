import java.util.Calendar;
import java.util.Objects;

/**
 * Represents a single Person data record.
 * <p>
 * This is a data class: it knows how to hold the field values for one person,
 * how to compare itself to another Person, and how to render itself in the
 * three common text data formats (CSV, JSON, XML) used for file storage.
 *
 * @author Your Name
 * @version 1.0
 */
public class Person
{
    private String firstName;
    private String lastName;
    private final String ID;   // never changes once assigned: no setter
    private String title;      // prefix such as Mr. Mrs. Ms. Prof. Dr. Hon.
    private int YOB;           // year of birth, valid range 1940 - 2010

    /** Lowest legal year of birth. */
    public static final int MIN_YOB = 1940;
    /** Highest legal year of birth. */
    public static final int MAX_YOB = 2010;

    /**
     * Full constructor: builds a Person from all five fields.
     *
     * @param firstName the person's given name
     * @param lastName  the person's family name
     * @param ID        the unchanging identifier (sequence of digits)
     * @param title     the name prefix, e.g. "Mr." or "Dr."
     * @param YOB       the four digit year of birth, 1940 - 2010
     */
    public Person(String firstName, String lastName, String ID, String title, int YOB)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.title = title;
        this.YOB = YOB;
    }

    /**
     * Overloaded constructor for records that carry no title prefix.
     *
     * @param firstName the person's given name
     * @param lastName  the person's family name
     * @param ID        the unchanging identifier
     * @param YOB       the four digit year of birth
     */
    public Person(String firstName, String lastName, String ID, int YOB)
    {
        this(firstName, lastName, ID, "", YOB);
    }

    /**
     * Copy constructor.
     * @param p the Person to copy
     */
    public Person(Person p)
    {
        this(p.firstName, p.lastName, p.ID, p.title, p.YOB);
    }

    /** @return the person's given name */
    public String getFirstName() { return firstName; }

    /** @return the person's family name */
    public String getLastName() { return lastName; }

    /** @return the unchanging identifier for this person */
    public String getID() { return ID; }

    /** @return the name prefix such as "Mr." or "Dr." */
    public String getTitle() { return title; }

    /** @return the four digit year of birth */
    public int getYOB() { return YOB; }

    /**
     * Sets the person's given name.
     * @param firstName the new given name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Sets the person's family name.
     * @param lastName the new family name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Sets the name prefix.
     * @param title the new title, e.g. "Prof."
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets the year of birth. Values outside the legal range are ignored.
     * @param YOB the new year of birth, must be 1940 - 2010
     */
    public void setYOB(int YOB)
    {
        if (YOB >= MIN_YOB && YOB <= MAX_YOB)
        {
            this.YOB = YOB;
        }
    }

    // There is deliberately no setID(): the ID should never change.

    /**
     * Builds the person's name as first name, a space, then last name.
     * @return the full name, for example "Jane Doe"
     */
    public String fullName()
    {
        return firstName + " " + lastName;
    }

    /**
     * Builds the person's name as title, a space, then the full name.
     * @return the formal name, for example "Dr. Jane Doe"
     */
    public String formalName()
    {
        return title + " " + fullName();
    }

    /**
     * Calculates the person's age using the current calendar year.
     * @return the age in years as a String
     */
    public String getAge()
    {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getAge(currentYear);
    }

    /**
     * Calculates the person's age for any given year.
     * @param year the year to measure the age against
     * @return the age in years as a String
     */
    public String getAge(int year)
    {
        return String.valueOf(year - YOB);
    }

    /**
     * Renders this Person as one comma separated value record.
     * @return the record as firstName,lastName,ID,title,YOB
     */
    public String toCSV()
    {
        return firstName + "," + lastName + "," + ID + "," + title + "," + YOB;
    }

    /**
     * Renders this Person as a JSON object literal.
     * @return a JSON String describing this Person
     */
    public String toJSON()
    {
        return "{\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"ID\":\"" + ID + "\","
                + "\"title\":\"" + title + "\","
                + "\"YOB\":" + YOB + "}";
    }

    /**
     * Renders this Person as an XML element.
     * @return an XML String describing this Person
     */
    public String toXML()
    {
        return "<Person>"
                + "<firstName>" + firstName + "</firstName>"
                + "<lastName>" + lastName + "</lastName>"
                + "<ID>" + ID + "</ID>"
                + "<title>" + title + "</title>"
                + "<YOB>" + YOB + "</YOB>"
                + "</Person>";
    }

    /**
     * Human readable rendering of this Person.
     * @return a readable String showing every field
     */
    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID='" + ID + '\'' +
                ", title='" + title + '\'' +
                ", YOB=" + YOB +
                '}';
    }

    /**
     * Two Person objects are equal when every field matches.
     * @param o the object to compare against
     * @return true when o is a Person holding the same field values
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return YOB == person.YOB
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(ID, person.ID)
                && Objects.equals(title, person.title);
    }

    /**
     * Hash code consistent with equals().
     * @return the hash code for this Person
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(firstName, lastName, ID, title, YOB);
    }
}
