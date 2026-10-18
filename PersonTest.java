import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for the Person class.
 * Covers both constructors, every setter, and the added methods.
 * Getter methods are not tested, as directed in the lab.
 */
class PersonTest
{
    private Person p;        // built with the full constructor
    private Person pNoTitle; // built with the overloaded constructor

    @BeforeEach
    void setUp()
    {
        p = new Person("Jane", "Doe", "123456", "Dr.", 1985);
        pNoTitle = new Person("John", "Smith", "654321", 1999);
    }

    @Test
    @DisplayName("Full constructor stores every field")
    void testFullConstructor()
    {
        String expected = "Person{firstName='Jane', lastName='Doe', ID='123456', title='Dr.', YOB=1985}";
        assertEquals(expected, p.toString());
    }

    @Test
    @DisplayName("Overloaded constructor leaves the title empty")
    void testOverloadedConstructor()
    {
        String expected = "Person{firstName='John', lastName='Smith', ID='654321', title='', YOB=1999}";
        assertEquals(expected, pNoTitle.toString());
    }

    @Test
    @DisplayName("Copy constructor produces an equal but separate object")
    void testCopyConstructor()
    {
        Person copy = new Person(p);
        assertEquals(p, copy);
        assertNotSame(p, copy);
    }

    @Test
    @DisplayName("setFirstName changes the first name")
    void testSetFirstName()
    {
        p.setFirstName("Janet");
        assertEquals("Janet Doe", p.fullName());
    }

    @Test
    @DisplayName("setLastName changes the last name")
    void testSetLastName()
    {
        p.setLastName("Roe");
        assertEquals("Jane Roe", p.fullName());
    }

    @Test
    @DisplayName("setTitle changes the title")
    void testSetTitle()
    {
        p.setTitle("Prof.");
        assertEquals("Prof. Jane Doe", p.formalName());
    }

    @Test
    @DisplayName("setYOB accepts a year inside the legal range")
    void testSetYOBInRange()
    {
        p.setYOB(2000);
        assertEquals(2000, p.getYOB());
    }

    @Test
    @DisplayName("setYOB rejects a year outside the legal range")
    void testSetYOBOutOfRange()
    {
        p.setYOB(1800);
        assertEquals(1985, p.getYOB());
        p.setYOB(2050);
        assertEquals(1985, p.getYOB());
    }

    @Test
    @DisplayName("fullName is first name space last name")
    void testFullName()
    {
        assertEquals("Jane Doe", p.fullName());
    }

    @Test
    @DisplayName("formalName is title space full name")
    void testFormalName()
    {
        assertEquals("Dr. Jane Doe", p.formalName());
    }

    @Test
    @DisplayName("getAge with a supplied year subtracts the year of birth")
    void testGetAgeForYear()
    {
        assertEquals("35", p.getAge(2020));
        assertEquals("0", p.getAge(1985));
    }

    @Test
    @DisplayName("getAge with no argument uses the current year")
    void testGetAgeCurrentYear()
    {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String expected = String.valueOf(currentYear - 1985);
        assertEquals(expected, p.getAge());
    }

    @Test
    @DisplayName("toCSV builds the comma separated record")
    void testToCSV()
    {
        assertEquals("Jane,Doe,123456,Dr.,1985", p.toCSV());
    }

    @Test
    @DisplayName("toJSON builds a JSON object literal")
    void testToJSON()
    {
        String expected = "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"ID\":\"123456\",\"title\":\"Dr.\",\"YOB\":1985}";
        assertEquals(expected, p.toJSON());
    }

    @Test
    @DisplayName("toXML builds a Person element")
    void testToXML()
    {
        String expected = "<Person><firstName>Jane</firstName><lastName>Doe</lastName>"
                + "<ID>123456</ID><title>Dr.</title><YOB>1985</YOB></Person>";
        assertEquals(expected, p.toXML());
    }

    @Test
    @DisplayName("equals is true for matching field values and false otherwise")
    void testEquals()
    {
        Person same = new Person("Jane", "Doe", "123456", "Dr.", 1985);
        Person different = new Person("Jane", "Doe", "999999", "Dr.", 1985);

        assertEquals(p, same);
        assertNotEquals(p, different);
        assertNotEquals(p, pNoTitle);
        assertNotEquals(p, null);
    }

    @Test
    @DisplayName("Equal Person objects share a hash code")
    void testHashCode()
    {
        Person same = new Person("Jane", "Doe", "123456", "Dr.", 1985);
        assertEquals(p.hashCode(), same.hashCode());
    }
}
