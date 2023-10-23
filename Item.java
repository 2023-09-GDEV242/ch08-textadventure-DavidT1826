
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weight)
    {
        // initialise instance variables
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Returns a string that shows what the item's description and weight 
     * is.
     */
    public String getDescription()
    {
        return "In the room there is a " + description + " with a weight of "
        + weight;
    }
}
