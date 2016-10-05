package slapjack;

import java.util.*;
public class Deck extends GroupOfCards {
   
    //private ArrayList<Card> cards;
    
    public Deck(){    
        
        cards = new ArrayList<>();
        PlayingCards playingCards;
        for(Card.Suit s : Card.Suit.values())
        {
            for(Card.Face n : Card.Face.values()){
                Card c = new Card(s,n);
                cards.add(c);
            }
        }
        playingCards = new PlayingCards();
    } // end Deck constructor
    
    public void random21()
    {
        while(this.getDeck().size() > 21){
            this.getDeck().remove(0);
        }
    }
    
    public void shuffle(){
        Collections.shuffle(this.getDeck());
    }
    
    public ArrayList<Card> getDeck()
    {
        return cards;
    }
    
    public String toString(ArrayList<Card> cards){
        String theDeck = "";
        
        for(int i = 0; i < cards.size(); i++){
            theDeck += cards.get(i);
        }
        
        return theDeck;
    }
    
    public void displayDeck(){ // mainly for debugging
        System.out.println(this.toString(this.getDeck()));   
    }
    
}
