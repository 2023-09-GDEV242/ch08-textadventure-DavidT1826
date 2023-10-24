import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  David Thompson
 * @version 2023.10.22
 */

public class Game 
{
    private Parser parser;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player();
        createRooms();
    }
    
    /**
     * Create the items within the rooms.
     */
    private void createItems()
    {
        
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, computerLab, office, cafeteria, pool, 
        stadium, scienceLab, scienceClassroom, mathClassroom, backstage,
        historyClassroom, gym, lake;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        computerLab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cafeteria = new Room("in the cafeteria");
        pool = new Room("at the pool");
        stadium = new Room("at the stadium");
        scienceLab = new Room("in the science lab");
        scienceClassroom = new Room("in the science classroom");
        mathClassroom = new Room("in the math classroom");
        backstage = new Room("in the theater backstage");
        historyClassroom = new Room("in the history classroom");
        gym = new Room("in the gym");
        lake = new Room("at the lake");
        
        Item bread, chips, cake, burger, iceCream, candy, magicCookie;
        
        //Creat the items
        bread = new Item("piece of bread", 1);
        chips = new Item("bag of chips", 2);
        cake = new Item("piece of cake", 4);
        burger = new Item("burger", 3);
        iceCream = new Item("ice cream cone", 3);
        candy = new Item("candy bar", 2);
        magicCookie = new Item("magic cookie", 2);
        
        
        // initialise room exits and items
        outside.setExit("east", lake);
        outside.setExit("south", office);
        outside.setExit("west", pub);
        outside.setExit("north", stadium);
        
        lake.setExit("west", outside);
        lake.setExit("north", mathClassroom);
        lake.setItem(bread);
        
        mathClassroom.setExit("south", lake);
        mathClassroom.setExit("north", theater);
        
        theater.setExit("south", mathClassroom);
        theater.setExit("east", backstage);
        
        backstage.setExit("west", theater);
        backstage.setItem(iceCream);
        
        office.setExit("north", outside);
        office.setExit("east", cafeteria);
        office.setExit("west", computerLab);
        
        computerLab.setExit("east", office);
        
        cafeteria.setExit("west", office);
        cafeteria.setItem(cake);
        
        pub.setExit("east", outside);
        pub.setItem(burger);
        
        stadium.setExit("south", stadium);
        stadium.setExit("east", historyClassroom);
        stadium.setExit("north", gym);
        stadium.setItem(chips);
        
        historyClassroom.setExit("west", stadium);
        historyClassroom.setExit("north", scienceClassroom);
        
        scienceClassroom.setExit("south", historyClassroom);
        scienceClassroom.setExit("east", scienceLab);
        
        scienceLab.setExit("west", scienceClassroom);
        scienceLab.setItem(magicCookie);
        
        gym.setExit("south", stadium);
        gym.setExit("east", pool);
        gym.setItem(candy);
        
        pool.setExit("west", gym);


        player.changeRoom(outside); // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean quit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                quit = goRoom(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                back();
                break;
            
            case TAKE:
                take();
                break;
                
            case QUIT:
                quit = quit(command);
                break;
        }
        return quit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @return returns true if the player died due to running out of health.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
            return false;
        }
        else {
            player.getPrevRooms().push(player.getCurrentRoom());
            player.changeRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
            if(!player.getCurrentRoom().getItems().isEmpty()){
                for(Item item : player.getCurrentRoom().getItems()){
                    System.out.println(item.getLongDescription());
                }
            }
            player.healthDecrease();
            if(player.getHealth() == 0){
                System.out.println("Oh no! You died!");
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    /**
     * Simply look around the room. This will show you what the room's
     * description is as well as what exits we can currently go through.
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    /**
     * Eat a peice of food. This will restore health.
     */
    private void eat()
    {
        if(player.getHealth() == 10){
            System.out.println("You are not hungry right now,");
        }
        else{
            player.eatItem();
        }
    }
    
    /**
     * Go back to a previous room.
     * @return returns true if the player died due to runnint out of health.
     */
    private boolean back()
    {
        if(!player.getPrevRooms().empty()){
            player.changeRoom(player.getPrevRooms().pop());
            System.out.println(player.getCurrentRoom().getLongDescription());
            if(!player.getCurrentRoom().getItems().isEmpty()){
                for(Item item : player.getCurrentRoom().getItems()){
                    System.out.println(item.getLongDescription());
                }
            }
            player.healthDecrease();
            if(player.getHealth() == 0){
                System.out.println("Oh no! You died!");
                return true;
            }
            else{
                return false;
            }
        }
        else{
            System.out.println("You cannot go further back.");
            return false;
        }
        
    }
    
    /**
     * Pick up an item.
     */
    private void take()
    {
        if(player.getCurrentRoom().getItems().isEmpty()){
            System.out.println("There is nothing in this room to take.");
        }
        else{
            for(Item item : player.getCurrentRoom().getItems()){
                if(item.getWeight() >  player.getMaxWeight()){
                    System.out.println("Sorry, this item is too heavy.");
                }
                else{
                    player.addItem(item);
                    System.out.println("You added the " + 
                    item.getDescription() + " to your inventory.");
                }
            }
        }
    }
    /**
     * Print all items currently being carried.
     */
    private void items()
    {
        for(Item item : player.getItems()){
            System.out.println(item);
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * This is the main method of the class.
     */
    public void main(){
        play();
    }
}
