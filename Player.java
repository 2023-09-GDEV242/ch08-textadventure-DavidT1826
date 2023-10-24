import java.util.Stack;
import java.util.ArrayList;
/**
 * This class represents the player character you will be controling
 * throughout the game
 * 
 * @author: David Thompson
 * @version: 2023.10.22
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> prevRooms;
    private ArrayList<Item> items;
    private int maxWeight;
    private int currentWeight;
    private int health;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        prevRooms = new Stack<>();
        items = new ArrayList<>();
        maxWeight = 5;
        currentWeight = 0;
        health = 7;
    }
    
    /**
     * Return the room the player is currently in 
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Return the list of previous rooms
     */
    public Stack<Room> getPrevRooms()
    {
        return prevRooms;
    }
    
    /**
     * Return the list of items the player is holding.
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    /**
     * Return the player's max weight.
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }
    
    /**
     * Add an item to the list of items the player is holding
     * @param the item to be added
     */
    public void addItem(Item item)
    {
        items.add(item);
        currentWeight += item.getWeight();
    }
    
    /**
     * Eat the first item in your inventory.
     */
    public void eatItem()
    {
        System.out.println("You have eaten the " + 
        items.get(0).getDescription() + ". You have recovered one health.");
        if(items.get(0).getDescription() == "magic cookie"){
            System.out.println("You can now cary more stuff!");
            maxWeight = 10;
        }
        items.remove(0);
        health += 1;
    }
   
    /**
     * Cause the player to enter a new room
     * @param the room the player is entering
     */
    public void changeRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    
    /**
     * Return the player's health
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * This causes the player's health to go down.
     */
    public void healthDecrease()
    {
        health -= 1;
        System.out.println("Your health is now " + health);
    }
}
