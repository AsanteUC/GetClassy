import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Collects Product records from the console, stores them as Product objects in
 * an ArrayList, then writes the whole list to a CSV text file using toCSV().
 *
 * @author Your Name
 * @version 1.0
 */
public class ProductGenerator
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
