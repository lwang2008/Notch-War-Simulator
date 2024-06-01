import java.util.LinkedList;

public class Player extends Queue<Card> {
    // Constructor
    public Player() {
        super();  // Initializes the linked list structure from Queue
    }

    // You might add additional methods specific to the player if needed,
    // for example, methods to handle player stats, scoring, etc.

    // Example method: Check if the player can continue playing
    public boolean canPlay() {
        return !this.isEmpty();
    }

    // Add any methods that you find necessary for game logic enhancement,
    // like handling specific game scenarios, printing player details, etc.
}