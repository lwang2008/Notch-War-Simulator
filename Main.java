import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        //Players and Deck initialization and dealing of cards are done already
    
        Queue<Card> player0 = new Queue<Card>();
        Queue<Card> player1 = new Queue<Card>();
        deal(player0, player1);  // Deal method distributes cards to players

        playNotchWar(player0, player1);
    }

    public static void playNotchWar(Queue<Card> player0, Queue<Card> player1) {
        //While players still have cards, keep playing rounds
        while (!player0.isEmpty() && !player1.isEmpty()) {
            playRound(player0, player1);
        }

        //If player1 runs out, player0 wins and vice versa
        if (player1.isEmpty()) {
            System.out.println("The winner is... Player 0!");
        } else {
            System.out.println("The winner is... Player 1!");
        }

    }

    public static void playRound(Queue<Card> player0, Queue<Card> player1) {
        
        //Takes the top cards in each player's hand
        Card card0 = player0.get();
        Card card1 = player1.get();

        int difference = Math.abs(card0.getRank() - card1.getRank());

        if (difference == 1) {  // Notch rule
            // The lower card wins
            if (card0.getRank() < card1.getRank()) {
                winRound(player0, card0, card1);
            } else {
                winRound(player1, card0, card1);
            }
            //Output which cards were played and notch
            System.out.println(card0 + " versus " + card1 + " (NOTCHED!)");
        } else if (card0.compareTo(card1) > 0) {  // Higher card wins
            System.out.println(card0 + " versus " + card1);
            winRound(player0, card0, card1);
        } else if (card0.compareTo(card1) < 0) {
            System.out.println(card0 + " versus " + card1);
            winRound(player1, card1, card0);
        } else {
            // Handle a tie situation
            LinkedList<Card> total = new LinkedList<>();
            //Create a new LinkedList called total to represent the war pile, place the initial cards into the pile first.
            
            total.add(card0);
            total.add(card1);
            //Call war with the 2 players' hands, the war pile (containing the two initial cards), and the two initial cards as the last cards that were face up
            war(player0, player1, total, card0, card1);
        }
        //Display the number of cards each player has after each round
        displayPlayerStatus(player0, player1);
    }

    //puts both cards in the winner's hand
    public static void winRound(Queue<Card> winningPlayer, Card winnerCard, Card loserCard) {
        winningPlayer.put(winnerCard);
        winningPlayer.put(loserCard);
    }

    //War method takes in the players', decks, the war pile, and the last cards that are facing up in case one player runs out of cards
    public static void war(Queue<Card> player0, Queue<Card> player1, LinkedList<Card> totalPile, Card lastfaceup0, Card lastfaceup1) {
        System.out.println(lastfaceup0 + " versus " + lastfaceup1);
        System.out.println("WAR!");
        // Collect three cards face down from each player, or as many as they have left
        int faceDownCards = 3;
        for (int i = 0; i < faceDownCards; i++) {
            if (!player0.isEmpty()) {
                Card x = player0.get();
                totalPile.add(x);
            }
            if (!player1.isEmpty()) {
                Card y = player1.get();
                totalPile.add(y);
            }
        }

        // Both players should provide one card face up; if they run out, use the last face up card
        boolean a = false;
        boolean b = false;
        if(!player0.isEmpty()){
            a=true;
        }
        if(!player1.isEmpty()){
            b=true;
        }
        Card faceUp1 = player0.isEmpty() ? lastfaceup0 : player0.get();
        Card faceUp2 = player1.isEmpty() ? lastfaceup1 : player1.get();

        if(a){
            totalPile.add(faceUp1);
        }
        if(b){
            totalPile.add(faceUp2);
        }
        int difference = Math.abs(faceUp1.getRank() - faceUp2.getRank());
        // Determine the outcome of the war, taking notch rule into account
        if (faceUp1.compareTo(faceUp2) > 0) {
            // Player 0 wins the war
            if(difference==1){
                totalPile.forEach(card -> player1.put(card));
                System.out.println(faceUp1 + " versus " + faceUp2 + " (Notched WAR!)");
            }
            else{
                totalPile.forEach(card -> player0.put(card));
                System.out.println(faceUp1 + " versus " + faceUp2);
            }
            
            
        } else if (faceUp1.compareTo(faceUp2) < 0) {
            // Player 1 wins the war
            if(difference==1){
                totalPile.forEach(card -> player0.put(card));
                System.out.println(faceUp1 + " versus " + faceUp2 + " (Notched WAR!)");
            }
            else{
                totalPile.forEach(card -> player1.put(card));
                System.out.println(faceUp1 + " versus " + faceUp2);
            }
            
        } else {
            // If another tie occurs and both players are out of cards, then there is a tie
            // Otherwise, call war again with the current war pile and the last cards that were facing up
            if (player0.isEmpty() && player1.isEmpty()) {
                System.out.println("Tie because both ran out during war.");
            } 
            else{
                war(player0, player1, totalPile, faceUp1, faceUp1);
            }

        }
    }
    public static void deal(Queue<Card> player0, Queue<Card> player1) {
        Deck deck = new Deck();  // Assuming Deck class is correctly implemented
        deck.shuffle();
        //Deal the shuffled deck to each of the players one at a time
        for (int i = 0; i < 52; i++) {
            if (i % 2 == 0) player0.put(deck.getCard());
            else player1.put(deck.getCard());
        }
    }

    private static void displayPlayerStatus(Queue<Card> player0, Queue<Card> player1) {
        //Output the number of cards each player has
        System.out.println("Player 0 has " + player0.size() + ", " + "Player 1 has " + player1.size());
    }

}
