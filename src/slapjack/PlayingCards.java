package slapjack;

import java.util.HashMap;
import javafx.scene.image.Image;

public class PlayingCards{

    
    public PlayingCards(){
        
        CardImageSet.cardBack = new Image(getClass().getResource("/assignment2/images/card_back.png").toExternalForm());
        CardImageSet.cardFace = new HashMap<>();
        String[] fileNames = {"_of_clubs", "_of_diamonds", "_of_hearts", "_of_spades"};
        
        for(int i = 0; i < 4; i++){
            for(int j = 1; j < 14; j++){
                String fileName;
                Image cardFace;
                String key;
                if(j == 1){
                fileName = "ace"
                            + fileNames[i]
                            + ".png";
                cardFace = new Image(getClass().getResource("/assignment2/images/" + fileName).toExternalForm());
                key = "ace" + fileNames[i];
                }else if(j == 11){
                fileName =  "jack"
                            + fileNames[i]
                            + ".png";
                cardFace = new Image(getClass().getResource("/assignment2/images/" + fileName).toExternalForm());
                key = "jack" + fileNames[i];
                }else if(j == 12){
                fileName =  "queen"
                            + fileNames[i]
                            + ".png";
                cardFace = new Image(getClass().getResource("/assignment2/images/" + fileName).toExternalForm());
                key = "queen" + fileNames[i];
                }else if(j == 13){
                fileName =  "king"
                            + fileNames[i]
                            + ".png";
                cardFace = new Image(getClass().getResource("/assignment2/images/" + fileName).toExternalForm());
                key = "king" + fileNames[i];
                }
                else{
                fileName =  j
                            + fileNames[i]
                            + ".png";
                cardFace = new Image(getClass().getResource("/assignment2/images/" + fileName).toExternalForm());
                key = j + fileNames[i];
                }
                CardImageSet.cardFace.put(key, cardFace);
            }
        }
    }
}
