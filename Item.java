
/**
 * This is where all of the items in the game will be stored.
 * 
 * @author: David Thompson
 * @version: 2023.10.22
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
     * Return the description of an item
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Return the weight of an item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Returns a string that shows what the item's description and weight 
     * is.
     */
    public String getLongDescription()
    {
        return "In the room there is a " + description + " with a weight of "
        + weight;
    }
}
