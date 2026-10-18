import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Person records from a CSV text file, builds a Person object from each
 * record, stores them in an ArrayList and displays the list as a table.
 *
 * @author Your Name
 * @version 1.0
 */
public class PersonReader
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
