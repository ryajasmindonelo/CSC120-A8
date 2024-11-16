import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents a detective game where the player investigates areas, collects clues, and solves a mystery.
 **/
public class DetectiveGame implements Contract{
    private Map<String, Integer> evidenceLocations;
    private Stack<String> moves;
    private int userPoints;
    

    /**
     * Initializes the game with predefined evidence locations, a moves stack, and initial user points set to 0.
     **/
    public DetectiveGame(){
        evidenceLocations = new HashMap<>();
        evidenceLocations.put("house", 2);
        evidenceLocations.put("backyard", 2);
        evidenceLocations.put("shed", 1);
        evidenceLocations.put("tree", 1);
        evidenceLocations.put("pet house", 1);
        moves = new Stack<>();
        userPoints = 0;
    }

    /**
     * Simulates grabbing an item, which increases user points based on the item's length.
     * @param item The name of the item to grab.
     **/
    public void grab(String item){
        System.out.println("Grabbing " + item);
        int itemLength = item.length();
        userPoints += itemLength;
        moves.push("grab");
        moves.push(item);
        System.out.println("User points:" + itemLength);
    }

    /**
     * Simulates dropping an item, which decreases user points based on the item's length.
     * @param item The name of the item to drop.
     * @return A message indicating the item was dropped.
     **/
    public String drop(String item){
        System.out.println("Dropping " + item);
        int itemLength = item.length();
        userPoints -= itemLength;
        moves.push("drop");
        moves.push(item);
        System.out.println("User points:" + itemLength);
        return "Dropped " + item;
    }

    /**
     * Examines a specific area for clues and displays the number of clues found.
     * @param area The name of the area to examine.
     **/
    public void examine(String area){
        System.out.println("Examining " + area);
        if (evidenceLocations.containsKey(area)){
            int clueCount = evidenceLocations.get(area);
            System.out.println("You found " + clueCount + " clue(s) in the " + area + ".");
        } else {
            System.out.println("No clues in this area.");
        }
        System.out.println("User points:" + userPoints);
    }

    /**
     * Uses an item, decreasing user points by 1.
     * @param item The name of the item to use.
     **/
    public void use(String item){
        userPoints -= 1;
        System.out.println("Used " + item + ". User points decreased by 1. User points: " + userPoints);
    }

    /**
     * Simulates walking in a given direction.
     * @param direction The direction to walk (e.g., "north", "south").
     * @return True if the direction is valid, false otherwise.
     */
    public boolean walk(String direction){
        boolean isWalking = false;
        if (direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("south") ||
            direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("west")){
            System.out.println("Walking " + direction);
            isWalking = true;
        } else {
            System.out.println("Invalid direction");
        }
        return isWalking;
    }

    /**
     * Simulates flying to specific coordinates to deploy a drone.
     * @param x The x-coordinate to fly to.
     * @param y The y-coordinate to fly to.
     * @return True if the coordinates are valid, false otherwise.
     **/
    public boolean fly(int x, int y){
        boolean isFlying = false;
        if (x >= 0 && y >= 0){
            System.out.println("Flying to coordinates (" + x + ", " + y + ") to deploy a drone and scan the place.");
            isFlying = true;
        } else {
            System.out.println("Invalid coordinates");
        }
        return isFlying;
    }

    /**
     * Simulates resting, which increases user points by 5.
     **/
    public void rest(){
        System.out.println("Resting");
        userPoints += 5;
        moves.push("rest");
        moves.push("2");
        System.out.println("You gained 5 points for resting. Take a break when needed, you're doing a good job!");
    }

    /**
     * Undoes the last action performed in the game.
     **/
    public void undo(){
        System.out.println("Undoing last action");
        if (!moves.isEmpty()) {
            String priorAction = moves.pop();
            if (priorAction.equals("grab")){
                String item = moves.pop();
                drop(item);
                System.out.println("Prior action undone: Dropped " + item);
            }else if (priorAction.equals("drop")){
                String item = moves.pop();
                grab(item);
                System.out.println("Prior action undone: Grabbed " + item);
            }else if (priorAction.equals("rest")){
                int points = Integer.parseInt(moves.pop());
                userPoints -= points;
                System.out.println("Prior action undone: Subtracted " + points + " experience points");
            }else{
                System.out.println("Unable to undo last action: Unknown action type");
            }
        }else{
            System.out.println("No actions to undo, sorry");
        }
    }

    /**
     * Simulates shrinking, which allows slipping under a gate and increases user points.
     * @return The number of experience points gained from shrinking.
     **/
    public Number shrink(){
        System.out.println("Shrinking to slip under the gate into the yard with clues.");
        int experienceGained = 3;
        userPoints += experienceGained;
        System.out.println("You gained " + experienceGained + " experience points for squeezing under the gate. Current experience points: " + userPoints);
        return experienceGained;
    }

    /**
     * Simulates growing, which allows reaching higher places and increases user points.
     * @return The number of experience points gained from growing.
     **/
    public Number grow(){
        System.out.println("Growing to reach a tree branch and find a suspicious torn piece of fabric.");
        int experienceGained = 5;
        userPoints += experienceGained;
        System.out.println("You gained " + experienceGained + " experience points for finding the fabric on the tree branch. Current experience points: " + userPoints);
        return experienceGained;
    }

    /**
     * Sets the user's overall points.
     * @param overallPoints The new overall points for the user.
     **/
    public void overallPoints(int overallPoints){
        this.userPoints = overallPoints;
    }
    
    /**
     * Retrieves the user's overall points.
     * @return The user's overall points.
     **/
    public int getoverallPoints(){
        return userPoints;
    }

    public static void main(String[] args){
        DetectiveGame game = new DetectiveGame();

        System.out.println("You are in your backyard where a commotion was heard. " +
                           "Your goal is to find out what happened by investigating the surrounding areas. " +
                           "First, you spot some footprints leading to your neighbor’s garden. " +
                           "The items you find and the choices you make will reveal clues to solve the mystery happening." + 
                           "\n");

        game.grab("magnifying glass");
        game.grab("drone");
        game.examine("backyard");
        game.examine("pet house");
        game.rest();
        game.use("bat");
        game.use("drone");
        game.walk("north");
        game.walk("south");
        game.fly(5, 5);
        game.rest();
        game.shrink();
        game.grow();
        game.undo();

        System.out.println("Overall points: " + game.getoverallPoints());
    }
}
