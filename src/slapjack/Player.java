package slapjack;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

    public boolean isPlaying; // whether or not the player is still in the round
    public boolean slapped; // flips when a player has slapped the pile
    public boolean isPlayersTurn = false; // determines if the player can turn their card
    public static boolean canSlap = false; //determines which player slapped the card first
    private final int playerNumber;
    private ArrayList<Card> hand; // the player's hand
    
    //instantiates the new hand
    public Player(int playerNumber){
        this.playerNumber = playerNumber;
        hand = new ArrayList<>();
    }
    
    public int getPlayerNumber(){
        return playerNumber;
    }
    
    // if the player has cards,
    // gives a card the calling objects hand to the player whose card they slapped.
    // how to use: from driver class, player1.giveCard(player2);
    public void giveCard(Player player){
        // needs more code
        if(!this.hand.isEmpty()){ // if this players hand is not empty
            player.setCardInHand(this.hand.remove(0));
        } else {
            // call a method or do something for when this player is out of cards
        }
    }
    
    public ArrayList<Card> getHand(){
        return this.hand;
    }
    
    public Card getCardFromHand(){
        if(!this.hand.isEmpty()){ // if this players hand is not empty
            return this.hand.remove(0);
        }
        else{
            return null; // here we need to do something for when a player has no more cards
        }
    }
    
    public void setCardInHand(Card card){
        this.hand.add(card);
    }
    
    //called when the player presses the key for slapping
    //this method will set their slapped boolean to true
    public void slapCard(){
        if(slapped != true && canSlap == true){
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
            canSlap = true;
        } 
        //return hand.remove(0); 
        return null;
    }
}
