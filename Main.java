import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

//Person.java

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
class Person
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
     * The title is set to an empty String.
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
     * Copy constructor. Produces an independent Person with the same field values.
     *
     * @param p the Person to copy
     */
    public Person(Person p)
    {
        this(p.firstName, p.lastName, p.ID, p.title, p.YOB);
    }

    /**
     * @return the person's given name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @return the person's family name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @return the unchanging identifier for this person
     */
    public String getID()
    {
        return ID;
    }

    /**
     * @return the name prefix such as "Mr." or "Dr."
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return the four digit year of birth
     */
    public int getYOB()
    {
        return YOB;
    }

    /**
     * Sets the person's given name.
     *
     * @param firstName the new given name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Sets the person's family name.
     *
     * @param lastName the new family name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Sets the name prefix.
     *
     * @param title the new title, e.g. "Prof."
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Sets the year of birth. Values outside the legal range are ignored so the
     * object can never hold an impossible year.
     *
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
     *
     * @return the full name, for example "Jane Doe"
     */
    public String fullName()
    {
        return firstName + " " + lastName;
    }

    /**
     * Builds the person's name as title, a space, then the full name.
     *
     * @return the formal name, for example "Dr. Jane Doe"
     */
    public String formalName()
    {
        return title + " " + fullName();
    }

    /**
     * Calculates the person's age using the current calendar year.
     *
     * @return the age in years as a String
     */
    public String getAge()
    {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getAge(currentYear);
    }

    /**
     * Calculates the person's age for any given year.
     *
     * @param year the year to measure the age against
     * @return the age in years as a String
     */
    public String getAge(int year)
    {
        return String.valueOf(year - YOB);
    }

    /**
     * Renders this Person as one comma separated value record, ready to be
     * written as a single line of a CSV text file.
     *
     * @return the record as firstName,lastName,ID,title,YOB
     */
    public String toCSV()
    {
        return firstName + "," + lastName + "," + ID + "," + title + "," + YOB;
    }

    /**
     * Renders this Person as a JSON object literal.
     *
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
     *
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
     * Human readable rendering of this Person, used for console display.
     *
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
     *
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
     *
     * @return the hash code for this Person
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(firstName, lastName, ID, title, YOB);
    }
}

//Product.java

/**
 * Represents a single Product data record.
 * <p>
 * Like Person, this is a data class: it holds the fields for one product and
 * knows how to render itself as CSV, JSON and XML for file storage.
 *
 * @author Your Name
 * @version 1.0
 */
class Product
{
    private String name;
    private String description;
    private final String ID;   // never changes once assigned: no setter
    private double cost;

    /**
     * Full constructor: builds a Product from all four fields.
     *
     * @param name        the product name
     * @param description a short description of the product
     * @param ID          the unchanging product identifier
     * @param cost        the product cost in dollars
     */
    public Product(String name, String description, String ID, double cost)
    {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    /**
     * Overloaded constructor for a product with no description yet.
     * The description is set to an empty String.
     *
     * @param name the product name
     * @param ID   the unchanging product identifier
     * @param cost the product cost in dollars
     */
    public Product(String name, String ID, double cost)
    {
        this(name, "", ID, cost);
    }

    /**
     * Copy constructor. Produces an independent Product with the same values.
     *
     * @param p the Product to copy
     */
    public Product(Product p)
    {
        this(p.name, p.description, p.ID, p.cost);
    }

    /**
     * @return the product name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the product description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return the unchanging product identifier
     */
    public String getID()
    {
        return ID;
    }

    /**
     * @return the product cost in dollars
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * Sets the product name.
     *
     * @param name the new product name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the product description.
     *
     * @param description the new description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the product cost. Negative values are ignored so a product can never
     * hold an impossible price.
     *
     * @param cost the new cost in dollars, zero or greater
     */
    public void setCost(double cost)
    {
        if (cost >= 0.0)
        {
            this.cost = cost;
        }
    }

    // There is deliberately no setID(): the ID should never change.

    /**
     * Formats the cost as a currency style String with two decimal places.
     *
     * @return the cost, for example "$4.99"
     */
    public String formattedCost()
    {
        return String.format(Locale.US, "$%.2f", cost);
    }

    /**
     * Renders this Product as one comma separated value record, ready to be
     * written as a single line of a CSV text file.
     *
     * @return the record as name,description,ID,cost
     */
    public String toCSV()
    {
        return name + "," + description + "," + ID + "," + String.format(Locale.US, "%.2f", cost);
    }

    /**
     * Renders this Product as a JSON object literal.
     *
     * @return a JSON String describing this Product
     */
    public String toJSON()
    {
        return "{\"name\":\"" + name + "\","
                + "\"description\":\"" + description + "\","
                + "\"ID\":\"" + ID + "\","
                + "\"cost\":" + String.format(Locale.US, "%.2f", cost) + "}";
    }

    /**
     * Renders this Product as an XML element.
     *
     * @return an XML String describing this Product
     */
    public String toXML()
    {
        return "<Product>"
                + "<name>" + name + "</name>"
                + "<description>" + description + "</description>"
                + "<ID>" + ID + "</ID>"
                + "<cost>" + String.format(Locale.US, "%.2f", cost) + "</cost>"
                + "</Product>";
    }

    /**
     * Human readable rendering of this Product, used for console display.
     *
     * @return a readable String showing every field
     */
    @Override
    public String toString()
    {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ID='" + ID + '\'' +
                ", cost=" + String.format(Locale.US, "%.2f", cost) +
                '}';
    }

    /**
     * Two Product objects are equal when every field matches.
     *
     * @param o the object to compare against
     * @return true when o is a Product holding the same field values
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.cost, cost) == 0
                && Objects.equals(name, product.name)
                && Objects.equals(description, product.description)
                && Objects.equals(ID, product.ID);
    }

    /**
     * Hash code consistent with equals().
     *
     * @return the hash code for this Product
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(name, description, ID, cost);
    }
}

//SafeInputObj.java

/**
 * Object version of Tom Wulf's SafeInput console library.
 * <p>
 * Every method from SafeInput.java is carried over with the static keyword
 * removed and the pipe parameter removed, since each instance now owns its
 * own Scanner (pipe) as a class variable. The code inside each method is
 * otherwise unchanged, it just refers to the instance's pipe field.
 *
 * @author Your Name
 * @version 1.0
 */
class SafeInputObj
{
    private static Scanner pipe;

    /**
     * Default constructor. Sets the Scanner pipe to System.in.
     */
    public SafeInputObj()
    {
        this.pipe = new Scanner(System.in);
    }

    /**
     * Constructor that accepts a Scanner to use as the input source.
     * @param scanner the Scanner this object will read from
     */
    public SafeInputObj(Scanner scanner)
    {
        this.pipe = scanner;
    }

    /**
     * @return the Scanner this object reads from
     */
    public Scanner getPipe()
    {
        return pipe;
    }

    /**
     * Replaces the input source used by this object.
     * @param pipe the new Scanner to read from
     */
    public void setPipe(Scanner pipe)
    {
        this.pipe = pipe;
    }

    /**
     * Describes this SafeInputObj.
     * @return a readable String naming the class and whether a pipe is attached
     */
    @Override
    public String toString()
    {
        return "SafeInputObj{pipe attached=" + (pipe != null) + "}";
    }

    /**
     * Get a String which contains at least one character
     *
     * @param in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public String getNonZeroLenString(Scanner in, String prompt)
    {
        String retString = "";
        do
        {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        }while(retString.length() == 0); // until we have some characters
        return retString;
    }

    /**
     * Get an int value within a specified numeric range
     *
     * @param in
     * @param prompt - input prompt msg should not include range info
     * @param low    - low end of inclusive range
     * @param high   - high end of inclusive range
     * @return - int value within the inclusive range
     */
    public int getRangedInt(Scanner in, String prompt, int low, int high)
    {
        int retVal = 0;
        String trash = "";
        boolean done = false;
        do
        {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if(pipe.hasNextInt())
            {
                retVal = pipe.nextInt();
                pipe.nextLine();
                if(retVal >= low && retVal <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("\nNumber is out of range [" + low + "-" +
                            high + "]: " + retVal);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an int: " + trash);
            }
        }while(!done);
        return retVal;
    }

    /**
     * Get an int value with no constraints
     * @param prompt - input prompt msg should not include range info
     * @return - unconstrained int value
     */
    public int getInt(String prompt)
    {
        int retVal = 0;
        String trash = "";
        boolean done = false;
        do
        {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextInt())
            {
                retVal = pipe.nextInt();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an int: " + trash);
            }
        }while(!done);
        return retVal;
    }

    /**
     * get a double value within an inclusive range
     *
     * @param in
     * @param prompt - input prompt msg should not contain range info
     * @param low    - low value inclusive
     * @param high   - high value inclusive
     * @return - double value within the specified inclusive range
     */
    public double getRangedDouble(Scanner in, String prompt, int low, int high)
    {
        double retVal = 0;
        String trash = "";
        boolean done = false;
        do
        {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if(pipe.hasNextDouble())
            {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                if(retVal >= low && retVal <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("\nNumber is out of range [" + low + "-" +
                            high + "]: " + retVal);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        }while(!done);
        return retVal;
    }

    /**
     * Get an unconstrained double value
     * @param prompt - input prompt msg should not contain range info
     * @return - an unconstrained double value
     */
    public double getDouble(String prompt)
    {
        double retVal = 0;
        String trash = "";
        boolean done = false;
        do
        {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextDouble())
            {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        }while(!done);
        return retVal;
    }

    /**
     * Get a [Y/N] confirmation from the user
     *
     * @param in
     * @param prompt -input prompt msg for user does not need [Y/N]
     * @return - true for yes false for no
     */
    public static boolean getYNConfirm(Scanner in, String prompt)
    {
        boolean retVal = true;
        String response = "";
        boolean gotAVal = false;
        do
        {
            System.out.print("\n" + prompt + " [Y/N] ");
            response = pipe.nextLine();
            if(response.equalsIgnoreCase("Y"))
            {
                gotAVal = true;
                retVal = true;
            }
            else if(response.equalsIgnoreCase("N"))
            {
                gotAVal = true;
                retVal = false;
            }
            else
            {
                System.out.println("You must answere [Y/N]! " + response );
            }
        }while(!gotAVal);
        return retVal;
    }

    /**
     * Get a string that matches a RegEx pattern! This is a very powerful method
     *
     * @param in
     * @param prompt       - prompt for user
     * @param regExPattern - java style RegEx pattern to constrain the input
     * @return a String that matches the RegEx pattern supplied
     */
    public String getRegExString(Scanner in, String prompt, String regExPattern)
    {
        String response = "";
        boolean gotAVal = false;
        do
        {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();
            if(response.matches(regExPattern))
            {
                gotAVal = true;
            }
            else
            {
                System.out.println("\n" + response + " must match the pattern " +
                        regExPattern);
                System.out.println("Try again!");
            }
        }while(!gotAVal);
        return response;
    }
}

//PersonGenerator.java

/**
 * Collects Person records from the console, stores them as Person objects in an
 * ArrayList, then writes the whole list to a CSV text file using toCSV().
 *
 * @author Your Name
 * @version 1.0
 */
class PersonGenerator
{
    /**
     * Program entry point.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<Person> people = new ArrayList<>();
        boolean moreData = true;

        System.out.println("===== Person Generator =====");

        while (moreData)
        {
            SafeInputObj SafeInput = new SafeInputObj();
            String firstName = SafeInput.getNonZeroLenString(in, "Enter the first name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter the last name");
            String ID = SafeInput.getRegExString(in, "Enter the 6 digit ID", "\\d{6}");
            String title = SafeInput.getNonZeroLenString(in, "Enter the title [Mr. Mrs. Ms. Dr. Prof.]");
            int YOB = SafeInput.getRangedInt(in, "Enter the year of birth", Person.MIN_YOB, Person.MAX_YOB);

            // Build the object as soon as the fields are collected
            Person p = new Person(firstName, lastName, ID, title, YOB);
            people.add(p);

            System.out.println("\nAdded: " + p.formalName() + "  age " + p.getAge());
            moreData = SafeInputObj.getYNConfirm(in, "Do you want to enter another person?");
        }

        writeFile(people);
        System.out.println("\nWrote " + people.size() + " person record(s).");
    }

    /**
     * Writes every Person in the list to a chosen CSV file, one record per line.
     *
     * @param people the list of Person objects to persist
     */
    private static void writeFile(ArrayList<Person> people)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save the Person CSV file");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "csv"));
        chooser.setCurrentDirectory(new File("."));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No file chosen, nothing was saved.");
            return;
        }

        Path file = chooser.getSelectedFile().toPath();

        try
        {
            ArrayList<String> lines = new ArrayList<>();
            for (Person p : people)
            {
                lines.add(p.toCSV());
            }
            Files.write(file, lines);
            System.out.println("Saved to: " + file.toAbsolutePath());
        }
        catch (IOException e)
        {
            System.out.println("Could not write the file: " + e.getMessage());
        }
    }
}

//PersonReader.java

/**
 * Reads Person records from a CSV text file, builds a Person object from each
 * record, stores them in an ArrayList and displays the list as a table.
 *
 * @author Your Name
 * @version 1.0
 */
class PersonReader
{
    /**
     * Program entry point.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        ArrayList<Person> people = readFile();

        if (people.isEmpty())
        {
            System.out.println("No person records were loaded.");
            return;
        }

        displayTable(people);

        // Show the other two data formats for the first record
        Person first = people.get(0);
        System.out.println("\nFirst record as JSON: " + first.toJSON());
        System.out.println("First record as XML : " + first.toXML());
        System.out.println("First record raw    : " + first.toString());
    }

    /**
     * Prompts for a CSV file and loads it into a list of Person objects.
     *
     * @return the ArrayList of Person objects built from the file
     */
    private static ArrayList<Person> readFile()
    {
        ArrayList<Person> people = new ArrayList<>();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open the Person CSV file");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "csv"));
        chooser.setCurrentDirectory(new File("."));

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No file chosen.");
            return people;
        }

        Path file = chooser.getSelectedFile().toPath();

        try
        {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines)
            {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5)
                {
                    System.out.println("Skipping malformed record: " + line);
                    continue;
                }

                Person p = new Person(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                        parts[3].trim(), Integer.parseInt(parts[4].trim()));
                people.add(p);
            }
        }
        catch (IOException e)
        {
            System.out.println("Could not read the file: " + e.getMessage());
        }

        return people;
    }

    /**
     * Prints the loaded Person objects as a formatted console table.
     *
     * @param people the list of Person objects to display
     */
    private static void displayTable(ArrayList<Person> people)
    {
        System.out.println("===== Person Records =====");
        System.out.printf("%-12s %-12s %-8s %-8s %-6s %-5s%n",
                "First", "Last", "ID", "Title", "YOB", "Age");
        System.out.println("-".repeat(60));

        for (Person p : people)
        {
            System.out.printf("%-12s %-12s %-8s %-8s %-6d %-5s%n",
                    p.getFirstName(), p.getLastName(), p.getID(),
                    p.getTitle(), p.getYOB(), p.getAge());
        }
    }
}

//ProductGenerator.java

/**
 * Collects Product records from the console, stores them as Product objects in
 * an ArrayList, then writes the whole list to a CSV text file using toCSV().
 *
 * @author Your Name
 * @version 1.0
 */
class ProductGenerator
{
    /**
     * Program entry point.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();
        boolean moreData = true;

        System.out.println("===== Product Generator =====");

        while (moreData)
        {
            SafeInputObj SafeInput = new SafeInputObj();
            String name = SafeInput.getNonZeroLenString(in, "Enter the product name");
            String description = SafeInput.getNonZeroLenString(in, "Enter the description");
            String ID = SafeInput.getRegExString(in, "Enter the 6 digit product ID", "\\d{6}");
            double cost = SafeInput.getRangedDouble(in, "Enter the cost", 0, 100000);

            // Build the object as soon as the fields are collected
            Product p = new Product(name, description, ID, cost);
            products.add(p);

            System.out.println("\nAdded: " + p.getName() + " at " + p.formattedCost());
            moreData = SafeInput.getYNConfirm(in, "Do you want to enter another product?");
        }

        writeFile(products);
        System.out.println("\nWrote " + products.size() + " product record(s).");
    }

    /**
     * Writes every Product in the list to a chosen CSV file, one record per line.
     *
     * @param products the list of Product objects to persist
     */
    private static void writeFile(ArrayList<Product> products)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save the Product CSV file");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "csv"));
        chooser.setCurrentDirectory(new File("."));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No file chosen, nothing was saved.");
            return;
        }

        Path file = chooser.getSelectedFile().toPath();

        try
        {
            ArrayList<String> lines = new ArrayList<>();
            for (Product p : products)
            {
                lines.add(p.toCSV());
            }
            Files.write(file, lines);
            System.out.println("Saved to: " + file.toAbsolutePath());
        }
        catch (IOException e)
        {
            System.out.println("Could not write the file: " + e.getMessage());
        }
    }
}

//ProductReader.java

/**
 * Reads Product records from a CSV text file, builds a Product object from each
 * record, stores them in an ArrayList and displays the list as a table.
 *
 * @author Your Name
 * @version 1.0
 */
class ProductReader
{
    /**
     * Program entry point.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        ArrayList<Product> products = readFile();

        if (products.isEmpty())
        {
            System.out.println("No product records were loaded.");
            return;
        }

        displayTable(products);

        Product first = products.get(0);
        System.out.println("\nFirst record as JSON: " + first.toJSON());
        System.out.println("First record as XML : " + first.toXML());
        System.out.println("First record raw    : " + first.toString());
    }

    /**
     * Prompts for a CSV file and loads it into a list of Product objects.
     *
     * @return the ArrayList of Product objects built from the file
     */
    private static ArrayList<Product> readFile()
    {
        ArrayList<Product> products = new ArrayList<>();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open the Product CSV file");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "csv"));
        chooser.setCurrentDirectory(new File("."));

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No file chosen.");
            return products;
        }

        Path file = chooser.getSelectedFile().toPath();

        try
        {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines)
            {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 4)
                {
                    System.out.println("Skipping malformed record: " + line);
                    continue;
                }

                Product p = new Product(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                        Double.parseDouble(parts[3].trim()));
                products.add(p);
            }
        }
        catch (IOException e)
        {
            System.out.println("Could not read the file: " + e.getMessage());
        }

        return products;
    }

    /**
     * Prints the loaded Product objects as a formatted console table.
     *
     * @param products the list of Product objects to display
     */
    private static void displayTable(ArrayList<Product> products)
    {
        System.out.println("===== Product Records =====");
        System.out.printf("%-18s %-28s %-8s %10s%n", "Name", "Description", "ID", "Cost");
        System.out.println("-".repeat(70));

        for (Product p : products)
        {
            System.out.printf("%-18s %-28s %-8s %10s%n",
                    p.getName(), p.getDescription(), p.getID(), p.formattedCost());
        }
    }
}

//ObjInputTest.java
/**
 * Short driver program that exercises every method of SafeInputObj so the
 * console output can be captured for the lab screen shots.
 *
 * @author Your Name
 * @version 1.0
 */
class ObjInputTest
{
    /**
     * Program entry point.
     * @param args command line arguments, unused
     */
    public static void main(String[] args)
    {
        SafeInputObj input = new SafeInputObj();   // default constructor uses System.in

        System.out.println("===== SafeInputObj Test =====");

        Scanner in = null;
        String name = input.getNonZeroLenString(in, "Enter your name");
        System.out.println("getNonZeroLenString returned: " + name);

        int anyInt = input.getInt("Enter any whole number");
        System.out.println("getInt returned: " + anyInt);

        double anyDouble = input.getDouble("Enter any decimal number");
        System.out.println("getDouble returned: " + anyDouble);

        int ranged = input.getRangedInt(in, "Enter a number", 1, 10);
        System.out.println("getRangedInt returned: " + ranged);

        double rangedD = input.getRangedDouble(in, "Enter a price", 0, 100);
        System.out.println("getRangedDouble returned: " + rangedD);

        String ssn = input.getRegExString(in, "Enter an SSN in the form xxx-xx-xxxx", "\\d{3}-\\d{2}-\\d{4}");
        System.out.println("getRegExString returned: " + ssn);

        boolean confirm = input.getYNConfirm(in, "Did all of the methods work?");
        System.out.println("getYNConfirm returned: " + confirm);

        System.out.println("toString returned: " + input);

        System.out.println("===== All SafeInputObj methods tested =====");
    }
}