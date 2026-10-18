import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for the Product class.
 * Covers both constructors, every setter, and the added methods.
 * Getter methods are not tested, as directed in the lab.
 */
class ProductTest
{
    private Product p;         // built with the full constructor
    private Product pNoDesc;   // built with the overloaded constructor

    @BeforeEach
    void setUp()
    {
        p = new Product("Widget", "A small blue widget", "123456", 4.99);
        pNoDesc = new Product("Gadget", "654321", 19.50);
    }

    @Test
    @DisplayName("Full constructor stores every field")
    void testFullConstructor()
    {
        String expected = "Product{name='Widget', description='A small blue widget', ID='123456', cost=4.99}";
        assertEquals(expected, p.toString());
    }

    @Test
    @DisplayName("Overloaded constructor leaves the description empty")
    void testOverloadedConstructor()
    {
        String expected = "Product{name='Gadget', description='', ID='654321', cost=19.50}";
        assertEquals(expected, pNoDesc.toString());
    }

    @Test
    @DisplayName("Copy constructor produces an equal but separate object")
    void testCopyConstructor()
    {
        Product copy = new Product(p);
        assertEquals(p, copy);
        assertNotSame(p, copy);
    }

    @Test
    @DisplayName("setName changes the product name")
    void testSetName()
    {
        p.setName("Sprocket");
        assertEquals("Sprocket", p.getName());
    }

    @Test
    @DisplayName("setDescription changes the description")
    void testSetDescription()
    {
        p.setDescription("A large red widget");
        assertEquals("A large red widget", p.getDescription());
    }

    @Test
    @DisplayName("setCost accepts a value of zero or more")
    void testSetCostValid()
    {
        p.setCost(9.25);
        assertEquals(9.25, p.getCost(), 0.001);
    }

    @Test
    @DisplayName("setCost rejects a negative value")
    void testSetCostNegative()
    {
        p.setCost(-5.00);
        assertEquals(4.99, p.getCost(), 0.001);
    }

    @Test
    @DisplayName("formattedCost shows two decimal places with a dollar sign")
    void testFormattedCost()
    {
        assertEquals("$4.99", p.formattedCost());
        assertEquals("$19.50", pNoDesc.formattedCost());
    }

    @Test
    @DisplayName("toCSV builds the comma separated record")
    void testToCSV()
    {
        assertEquals("Widget,A small blue widget,123456,4.99", p.toCSV());
    }

    @Test
    @DisplayName("toJSON builds a JSON object literal")
    void testToJSON()
    {
        String expected = "{\"name\":\"Widget\",\"description\":\"A small blue widget\","
                + "\"ID\":\"123456\",\"cost\":4.99}";
        assertEquals(expected, p.toJSON());
    }

    @Test
    @DisplayName("toXML builds a Product element")
    void testToXML()
    {
        String expected = "<Product><name>Widget</name><description>A small blue widget</description>"
                + "<ID>123456</ID><cost>4.99</cost></Product>";
        assertEquals(expected, p.toXML());
    }

    @Test
    @DisplayName("equals is true for matching field values and false otherwise")
    void testEquals()
    {
        Product same = new Product("Widget", "A small blue widget", "123456", 4.99);
        Product different = new Product("Widget", "A small blue widget", "999999", 4.99);

        assertEquals(p, same);
        assertNotEquals(p, different);
        assertNotEquals(p, pNoDesc);
        assertNotEquals(p, null);
    }

    @Test
    @DisplayName("Equal Product objects share a hash code")
    void testHashCode()
    {
        Product same = new Product("Widget", "A small blue widget", "123456", 4.99);
        assertEquals(p.hashCode(), same.hashCode());
    }
}
