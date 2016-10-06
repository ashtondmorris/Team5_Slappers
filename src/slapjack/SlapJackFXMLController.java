package slapjack;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class SlapJackFXMLController implements Initializable {

    @FXML 
        StackPane masterPile;
    @FXML
        StackPane player1Hand;
    @FXML
        StackPane player2Hand;
    @FXML
        StackPane player3Hand;
    @FXML
        StackPane player4Hand;
    @FXML
        GridPane gridPane;
    @FXML
        ImageView iView;
    
    private SlapJackDriver slapJackDriver;
    private Stage stage;
    private Scene scene;
    private ArrayList<ImageView> player1CardImages;
    private ArrayList<ImageView> player2CardImages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initiate();
    }    
    
    private void initiate() {

        slapJackDriver = new SlapJackDriver();
        player1CardImages = new ArrayList<>();
        player2CardImages = new ArrayList<>();
        //player1Hand.setOnMouseClicked(e -> slap()); // just testing lambda expression and seeing if i have access to the correct instance of the fxml objects
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public void setScene(Scene scene){
        this.scene = scene;
    }
    
    
    // calls the particular player's slap method.
    public void slap(String letter){
        //System.out.println("slap");
        switch(letter){
            case "S": slapJackDriver.player1.slapCard();
                      slapJackDriver.checkPile();
                        break;
            case "L": slapJackDriver.player2.slapCard(); break;
        }
    }
    
    // calls the particular player's turn method
    public void turn(String letter){
        switch(letter){
            case "A":   if(slapJackDriver.player1.isPlayersTurn){
                            slapJackDriver.player1.turnCard();
                            slapJackDriver.changeTurn(1); // change whose turn it is
                        } else {
                            System.out.println(slapJackDriver.player1.isPlayersTurn);
                        }break;
            case "K":   if(slapJackDriver.player2.isPlayersTurn){
                            slapJackDriver.player2.turnCard();
                            slapJackDriver.changeTurn(0); // change whose turn it is
                        } else {
                            System.out.println(slapJackDriver.player2.isPlayersTurn);
                        } break;
        }
    }
    
    //this method sets each card image to each card in each player's hand,
    // and puts each player's card's images into an arraylist so that
    // we can easily animate each event at a later time from a different method.
    //this method will only be called once when the players have their cards the first time.
    private void updateCardImages(){
        
        player1Hand.getChildren().clear(); // clear the stackpane if there was anything there
        player2Hand.getChildren().clear(); // clear the stackpane if there was anything there

            for(int i = 0; i < slapJackDriver.numPlayers; i++){
                for(int j = 0; j < slapJackDriver.players.get(i).hand.size(); j++){
                    slapJackDriver.players.get(i).hand.get(j).flipToBack();  
                    iView = new ImageView();
                    iView.setImage(slapJackDriver.players.get(i).hand.get(j).getImage());                   
                    iView.setFitWidth(40);
                    iView.setPreserveRatio(true);
                    iView.setSmooth(true);
                    //iView.setTranslateY();
                    switch(i){
                        case 0: player1CardImages.add(0, iView); break;
                        case 1: player2CardImages.add(0, iView); break;
                    }
                }
            }
    }
    
    
}
