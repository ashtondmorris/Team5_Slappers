package slapjack;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

    public boolean isPlaying; // whether or not the player is still in the round
    public ArrayList<Card> hand; // the player's hand
    public boolean slapped; // flips when a player has slapped the pile
    public boolean isPlayersTurn; // determines if the player can turn their card
    
    //instantiates the new hand
    public Player(){
        
    }
    
    // if the player has cards,
    // gives a card from their hand to the player whose card they slapped.
    public Card giveCard(){
        // needs more code
        return hand.remove(0);
    }
    
    
    //called when the player presses the key for slapping
    public void slapCard(){
        slapped = true;
    }
    
    // for shuffling the players hand when they've won a hand or if another player
    // mistakenly slapped their card in the pile
    public void shuffleHand(){
        Collections.shuffle(hand);
    }
    
    //only if it's the player's turn (isPlayersTurn == true)
    public Card turnCard(){
        return hand.remove(0);
    }
}
