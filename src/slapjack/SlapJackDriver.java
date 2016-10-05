/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slapjack;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// new comment for commit example

/**
 *
 * @author doebo
 */
public class SlapJackDriver {

    Deck deck;
    public int numDecks; // number of decks
    public int numPlayers;
    ArrayList<Card> masterDeck; // deck containing each of the 52 card decks. Also used as the temporary pile.
    boolean isWinner;
    
    public SlapJackDriver(){
        playGame();
    }
    
    // starts a new game of SlapJack and deals with the gameplay
    private void playGame(){
        reset();
        makeDecks();
        dealCards();
        //continue game logic
        // should run while there is no winner
    }
    
    // loop until you've instantiated all decks requested (numDecks)
    // and added them to the masterDeck
    // and shuffled the masterDeck
    private void makeDecks(){
        
    }
    
    // deals a card to each player's hand until there are no more cards left in the masterDeck
    public void dealCards(){
        
    }
    
    //reset all parameters for a new game
    private void reset(){
        
    }
    
    //called when a player slaps the pile. Method checks the top card of the pile for a jack.
    //if the top card was a jack, that player wins that pile,
    //otherwise the player who slapped the non-jack has to give a card to the player
    //whose card they slapped.
    private void checkPile(){
        
    }
    
    //checks to see if there is a player with all the cards from masterDeck
    //if there is a winner,
    //sets all players' isPlaying to false
    //calls declare winner
    private void isWinner(){
        
    }
    
    //gets the player who won and declares them the winner
    private void declareWinner(){
        
    }
}
