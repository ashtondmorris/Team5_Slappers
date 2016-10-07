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
    public ArrayList<ImageView> player1CardImages;
    public ArrayList<ImageView> player2CardImages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initiate();
    }    
    
    private void initiate() {
        player1CardImages = new ArrayList<>(); // I had to put these (player1CardImage and player2CardImage instantiation) above the instantiation of the slapJackDriver object, because when i instantiate the driver object it never reached the code below it. This is because when I instantiate the driver, the driver starts running it's code which includes using the controller which instantiated it (this controller). Basically nothing after the driver instantiation gets executed because the process goes through to the driver and never comes back.
        player2CardImages = new ArrayList<>();
        slapJackDriver = new SlapJackDriver(this); 
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
                      if(Player.canSlap == true)
                      slapJackDriver.checkPile(0);
                      
                        break;
            case "L": slapJackDriver.player2.slapCard();  
                      if(Player.canSlap == true)
                      slapJackDriver.checkPile(1);
                       break;
        }
    }
    
    // calls the particular player's turn method
    public void turn(String letter){
        switch(letter){
            case "A":   if(slapJackDriver.player1.isPlayersTurn){
                            slapJackDriver.player1.turnCard();
                            slapJackDriver.addToDeck(0);
                            slapJackDriver.changeTurn(1); // change whose turn it is
                        } else {
                            System.out.println(slapJackDriver.player1.isPlayersTurn);
                        }break;
            case "K":   if(slapJackDriver.player2.isPlayersTurn){
                            slapJackDriver.player2.turnCard();
                            slapJackDriver.addToDeck(1);
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
    public void updateCardImages(ArrayList<Player> players){
        
        player1Hand.getChildren().clear(); // clear the stackpane if there was anything there
        player2Hand.getChildren().clear(); // clear the stackpane if there was anything there

            for(int i = 0; i < 2; i++){
                for(int j = 0; j < players.get(i).getHand().size(); j++){
                    players.get(i).getHand().get(j).flipToFace();  
                    iView = new ImageView();
                    iView.setImage(players.get(i).getHand().get(j).getImage());                   
                    iView.setFitWidth(40);
                    iView.setPreserveRatio(true);
                    iView.setSmooth(true);
                    //iView.setTranslateY();
                    switch(i){
                        case 0: player1CardImages.add(iView); break;
                        case 1: player2CardImages.add(iView); break;
                    }
                }
            }
    } // end updateCardImages()
    
    // takes in the giver and receiver of the giveCard method.
    // animates the process of one player giving the other player their card
    public void animateGiveCard(Player giver, Player receiver){
        
    }
    
    // takes in the players and animates the process of dealing out the cards
    public void animateDealCards(ArrayList<Player> players){
        updateCardImages(players);
        System.out.println("Dealing cards animation...");
        System.out.println("Player 1's top card: " + players.get(0).getHand().get(0).toString());
        System.out.println("Player1CardImages top card: " + player1CardImages.get(0).toString());
        player1Hand.getChildren().add(player1CardImages.get(0));
        // do animation
    }
    
    // takes in the players hand and animates them shuffling their deck some how
    public void animateShuffleHand(Player player){
        
    }
    
    //
    public void animateAddCardToPile(Player player){
        
    } 
    
    public void animateShowCurrentPlayer(int currentPlayer){
        
    }
    
    public void animateDeclarationOfWinner(Player player){
        
    }
    
}
