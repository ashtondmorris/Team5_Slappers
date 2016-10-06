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

    //public Deck deck;
    
    public int numDecks = 1; // number of decks
    public int numPlayers = 2;
    ArrayList<Card> masterDeck; // deck containing each of the 52 card decks. Also used as the temporary pile.
    ArrayList<Player> players; // so that we can loop through the players and change whose turn it is.
    boolean isWinner = false;
    Player player1;
    Player player2;
    //Player player3;
    //Player player4;
    
    public SlapJackDriver(){
        playGame();
    }
    
    // starts a new game of SlapJack and deals with the gameplay
    private void playGame(){
        PlayingCards p = new PlayingCards();
        masterDeck = new ArrayList<>();
        makeDecks();
        
        player1 = new Player(1);
        player2 = new Player(2);
        
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2); // 
        dealCards();
        
        players.get(0).isPlayersTurn = true; // initially, it is player 1's turn.
//        player3 = new Player();
//        player4 = new Player();
        //continue game logic
        // should run while there is no winner
    }
    
    // loop until you've instantiated all decks requested (numDecks)
    // and added them to the masterDeck
    // and shuffled the masterDeck
    private void makeDecks(){
        System.out.println("calling makeDecks()");
        for(int i = 0; i < numPlayers; i++){
            Deck deck = new Deck();
            for(int j = 0; j < deck.getDeck().size(); j++){
                masterDeck.add(deck.getDeck().remove(0));
            }
        }
    }
    
    // deals a card to each player's hand until there are no more cards left in the masterDeck
    public void dealCards(){
        System.out.println("calling dealCards()");
        for(int i = 0; i < masterDeck.size(); i++){
            for(int j = 0; j < players.size(); j++){
                players.get(j).hand.add(masterDeck.remove(0));
            }
        }
    }
    
    //called when a player slaps the pile. Method checks the top card of the pile for a jack.
    //if the top card was a jack, that player wins that pile,
    //otherwise the player who slapped the non-jack has to give a card to the player
    //whose card they slapped.
    public void checkPile(){
        
    }
    
    // will be called whenever a player calls their turnCard method
    public void changeTurn(int nextPlayer){
        players.get(nextPlayer).isPlayersTurn = true;
        resetHasSlapped();
    }
    
    private void resetHasSlapped(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).slapped = false;
        }
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
