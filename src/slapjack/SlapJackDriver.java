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
    ArrayList<Boolean> firstSlap;
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
        makeDecks(); // will be called in the controller
        
        player1 = new Player(1);
        player2 = new Player(2);
        firstSlap = new ArrayList<>();
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2); // 
        firstSlap.add(player1.slapped);
        firstSlap.add(player2.slapped);
        dealCards(); // will be called in the controller
        
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
        for(int i = 0; i < numDecks; i++){
            Deck deck = new Deck();
            deck.shuffle();
            System.out.println(deck.getDeck().toString());
            for(int j = 0; j < 52; j++){
                masterDeck.add(deck.getDeck().remove(0));
            }
        }
        
        System.out.println(masterDeck.size());
    }
    
    // deals a card to each player's hand until there are no more cards left in the masterDeck
    public void dealCards(){
        System.out.println("calling dealCards()");
        
        for(int i = 0; i < 52/numPlayers; i++){
            for(int j = 0; j < numPlayers; j++){
                players.get(j).setCardInHand(masterDeck.remove(0));
            }
        }
        
        
        System.out.println(players.get(0).getHand().size());
        System.out.println(players.get(1).getHand().size());
      
    }
    
    //called when a player slaps the pile. Method checks the top card of the pile for a jack.
    //if the top card was a jack, that player wins that pile,
    //otherwise the player who slapped the non-jack has to give a card to the player
    //whose card they slapped.
    public void checkPile(int player){
        Player.canSlap = false;
        int size = masterDeck.size();
    
        if(masterDeck.get(masterDeck.size() -1).face() == Card.Face.JACK)
        {
            System.out.println(player + " slapped a jack");
            for (int i = 0; i < size; i++)
            {
               players.get(player).setCardInHand(masterDeck.remove(0));
               System.out.print(players.get(player).getHand().get(players.get(player).getHand().size()-1).toString());
            } 
               
            players.get(player).shuffleHand();
        }
        
        else
        {
            switch (player)
            {
                case 0:
                    if(player1.giveCard(player2))
                    System.out.println(player + " give card");
                    else
                        player2.

                    break;
                case 1:
                    player2.giveCard(player1);
                    System.out.println(player + " give card");

                    break;
            }
            
        }
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
        if (player1.isWinner)
            declareWinner(0);
        else if (player2.isWinner)
            declareWinner(1);
    }
    
    //gets the player who won and declares them the winner
    private void declareWinner(int player){
        
    }
    
    public void addToDeck(int player)
    {
       System.out.println(players.get(player).getHand().get(0).toString());
       masterDeck.add(players.get(player).getCardFromHand());
       
    }
    
}
