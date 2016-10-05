package slapjack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
    
    private final Suit suit;
    private final Face face;
    
    Image cardImageUp;
     
   // attributes/variables  to track the face and suit of cards
    public Card(Suit suit,Face face){
        this.suit = suit;
        this.face = face;    
        
        if(suit.equals(Card.Suit.CLUBS)){
            if(face.name().equals("ACE") || face.name().equals("JACK") || face.name().equals("QUEEN") || face.name().equals("KING"))
                cardImageUp = CardImageSet.cardFace.get(face.name().toLowerCase() + "_of_clubs");
            else
                cardImageUp = CardImageSet.cardFace.get(face + "_of_clubs");
        }else if(suit.equals(Card.Suit.DIAMONDS)){
            if(face.name().equals("ACE") || face.name().equals("JACK") || face.name().equals("QUEEN") || face.name().equals("KING"))
                cardImageUp = CardImageSet.cardFace.get(face.name().toLowerCase() + "_of_diamonds");
            else
                cardImageUp = CardImageSet.cardFace.get(face + "_of_diamonds");
        }else if(suit.equals(Card.Suit.HEARTS)){
            if(face.name().equals("ACE") || face.name().equals("JACK") || face.name().equals("QUEEN") || face.name().equals("KING"))
                cardImageUp = CardImageSet.cardFace.get(face.name().toLowerCase() + "_of_hearts");
            else
                cardImageUp = CardImageSet.cardFace.get(face + "_of_hearts");
        }else if(suit.equals(Card.Suit.SPADES)){
            if(face.name().equals("ACE") || face.name().equals("JACK") || face.name().equals("QUEEN") || face.name().equals("KING"))
                cardImageUp = CardImageSet.cardFace.get(face.name().toLowerCase() + "_of_spades");
            else
                cardImageUp = CardImageSet.cardFace.get(face + "_of_spades");
        }
        
        setImage(CardImageSet.cardBack);
    }
    
    public void flipToFace(){
        setImage(cardImageUp);
    }
    
    public void flipToBack(){
        setImage(CardImageSet.cardBack);
    }
    
    public Suit suit(){ 
        return suit; 
    }
    
    public Face face(){
        return face;
    }
    
    @Override
    public String toString(){
        return face + " of " + suit + "\n"; 
    }
    
//    public Suit getSuit(){
//        return this.suit;
//    }
//    
//    public Face getNum(){
//        return this.face;
//    }
    
    public enum Suit{
        CLUBS("Clubs"), SPADES("Spades"), HEARTS("Hearts"), DIAMONDS("Diamonds");
        
        private String value;
        
        Suit(final String value){
            this.value = value;
        }
        
        public String getValue(){
            return value;
        }
        
        @Override
        public String toString(){
            return this.getValue();
        }
                
    }

    public enum Face{
        ACE("Ace"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("Jack"), QUEEN("Queen"), KING("King");

        private String value;

        private Face(final String value)
        { 
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String newVal){
            this.value = newVal;
        }

        @Override
        public String toString() {
            return this.getValue();
        }   
    } // end Face         
} // end Card