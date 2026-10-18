import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Product records from a CSV text file, builds a Product object from each
 * record, stores them in an ArrayList and displays the list as a table.
 *
 * @author Your Name
 * @version 1.0
 */
public class ProductReader
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
