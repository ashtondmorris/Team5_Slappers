package slapjack;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

    public boolean isPlaying; // whether or not the player is still in the round
    public ArrayList<Card> hand; // the player's hand
    public boolean slapped; // flips when a player has slapped the pile
    public boolean isPlayersTurn = false; // determines if the player can turn their card
    private final int playerNumber;
    
    //instantiates the new hand
    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        hand = new ArrayList<>();
    }
    
    public int getPlayerNumber(){
        return playerNumber;
    }
    // if the player has cards,
    // gives a card from their hand to the player whose card they slapped.
    public Card giveCard(){
        // needs more code
        return hand.remove(0);
    }
    
    
    //called when the player presses the key for slapping
    //this method will set their slapped boolean to true
    public void slapCard(){
        if(slapped != true){
            System.out.println(playerNumber + " slapped");
            slapped = true;
        }
        else{
            System.out.println(playerNumber + " has already slapped this turn");
        }
    }
    
    // for shuffling the players hand when they've won a hand or if another player
    // mistakenly slapped their card in the pile
    public void shuffleHand(){
        Collections.shuffle(hand);
    }
    
    //only if it's the player's turn (isPlayersTurn == true)
    public Card turnCard(){
        if(isPlayersTurn){
            System.out.println(playerNumber + " turned their card");
            isPlayersTurn = false;
        } 
        //return hand.remove(0); 
        return null;
    }
}
