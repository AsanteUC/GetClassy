import java.util.Locale;
import java.util.Objects;

/**
 * Represents a single Product data record.
 * <p>
 * Like Person, this is a data class: it holds the fields for one product and
 * knows how to render itself as CSV, JSON and XML for file storage.
 *
 * @author Your Name
 * @version 1.0
 */
public class Product
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
