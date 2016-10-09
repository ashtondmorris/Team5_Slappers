package slapjack;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;


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
    public ArrayList<ArrayList<ImageView>> playerCardImages;
    public ArrayList<StackPane> playerHands;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initiate();
    }    
    
    private void initiate() {
        player1CardImages = new ArrayList<>(); // I had to put these (player1CardImage and player2CardImage instantiation) above the instantiation of the slapJackDriver object, because when i instantiate the driver object it never reached the code below it. This is because when I instantiate the driver, the driver starts running it's code which includes using the controller which instantiated it (this controller). Basically nothing after the driver instantiation gets executed because the process goes through to the driver and never comes back.
        player2CardImages = new ArrayList<>();
        playerCardImages = new ArrayList<>();
        playerHands = new ArrayList<>();
        playerCardImages.add(player1CardImages);
        playerCardImages.add(player2CardImages);
        playerHands.add(player1Hand);
        playerHands.add(player2Hand);
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
            case "A":   if(slapJackDriver.player1.isPlayersTurn && !slapJackDriver.getIsWinner()){
                            slapJackDriver.player1.turnCard();
                            slapJackDriver.addToDeck(0);
                            // change whose turn it is
                            if (slapJackDriver.player2.getCardCount() == 0) {
                                slapJackDriver.changeTurn(0);
                            } else {
                                slapJackDriver.changeTurn(1);
                            }
                        } else {
//                            System.out.println(slapJackDriver.player1.isPlayersTurn);
                              System.out.println("You cannot turn a card at this time");
                        }break;
            case "K":   if(slapJackDriver.player2.isPlayersTurn && !slapJackDriver.getIsWinner()){
                            slapJackDriver.player2.turnCard();
                            slapJackDriver.addToDeck(1);
                            // change whose turn it is
                            if (slapJackDriver.player1.getCardCount() == 0) {
                                slapJackDriver.changeTurn(1);
                            } else {
                                slapJackDriver.changeTurn(0);
                            }
                        } else {
//                            System.out.println(slapJackDriver.player2.isPlayersTurn);
                            System.out.println("You cannot turn a card at this time");
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
        player1CardImages.clear();
        player2CardImages.clear();

            for(int i = 0; i < 2; i++){
                for(int j = 0; j < players.get(i).getHand().size(); j++){
                    players.get(i).getHand().get(j).flipToBack();  
                    iView = new ImageView();
                    iView.setImage(players.get(i).getHand().get(j).getImage());                   
                    iView.setFitWidth(80);
                    iView.setPreserveRatio(true);
                    iView.setSmooth(true);
                    //iView.setTranslateY();
                    playerCardImages.get(i).add(iView);
//                    switch(i){
//                        case 0: player1CardImages.add(iView); break;
//                        case 1: player2CardImages.add(iView); break;
//                    }
                }
            }
    } // end updateCardImages()
    
    // takes in the giver and receiver of the giveCard method.
    // animates the process of one player giving the other player their card
    public void animateGiveCard(Player giver, Player receiver){
        
    }
    
    // takes in the players and animates the process of dealing out the cards
    public void animateDealCards(ArrayList<Player> players){
        
        double yLocation;
        double xLocation;

        SequentialTransition sequence = new SequentialTransition();
        updateCardImages(players);
        
        System.out.println("Dealing cards animation...");
        
        System.out.print("Player 1's top card: " + players.get(0).getHand().get(0).toString());
        System.out.print("Player 2's top card: " + players.get(1).getHand().get(0).toString());
               
        for(int j = 0; j < playerCardImages.get(0).size(); j++){ // each card
            for(int i = 0; i < playerCardImages.size(); i++){ // each player
                
                System.out.print("Player "+ i +"'s card: " + players.get(i).getHand().get(j).toString());
        
                playerHands.get(i).getChildren().add(playerCardImages.get(i).get(j));
                
                yLocation = playerCardImages.get(i).get(j).getTranslateX();
                xLocation = playerCardImages.get(i).get(j).getTranslateX();
                        
                TranslateTransition translate = new TranslateTransition(Duration.millis(100), playerCardImages.get(i).get(j));
                translate.setFromX(Math.pow(-1,i)*130);
                translate.setFromY(-170);
                translate.setToX(xLocation);
                translate.setToY(yLocation);
                translate.setCycleCount(1);
                translate.setAutoReverse(false);

                RotateTransition rotate = new RotateTransition(Duration.millis(100), playerCardImages.get(i).get(j));
                rotate.setByAngle((Math.random() * 2) * Math.pow(-1, i));
                rotate.setCycleCount(1);
                rotate.setAutoReverse(false);
                
                
                ParallelTransition parallelTranny = new ParallelTransition();

                parallelTranny.getChildren().addAll(translate, rotate);
                sequence.getChildren().add(parallelTranny);
            }
            
        }
        sequence.play();
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
