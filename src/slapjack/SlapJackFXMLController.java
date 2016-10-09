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
    public ArrayList<ImageView> masterPileCardImages;
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
        masterPileCardImages = new ArrayList<>();
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
        
        boolean playerCanTurn = true;
        
        if (slapJackDriver.masterDeck.size() > 0) 
        {
            if (slapJackDriver.masterDeck.get(slapJackDriver.masterDeck.size() -1).face() == Card.Face.JACK) 
            {
                playerCanTurn = false;
            }
        }
        
        if (slapJackDriver.getIsWinner())
            playerCanTurn = false;
        
        switch(letter){
            case "A":   if(slapJackDriver.player1.isPlayersTurn && playerCanTurn){
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
            case "K":   if(slapJackDriver.player2.isPlayersTurn && playerCanTurn){
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
                
                yLocation = playerCardImages.get(i).get(j).getTranslateY();
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
    
    // takes in the giver and receiver of the giveCard method.
    // animates the process of one player giving the other player their card
    public void animateGiveCard(int giver, int receiver){
        
    }
    
    // takes in the players hand and animates them shuffling their deck some how
    public void animateShuffleHand(int player){
        
    }
    
    //
    public void animateAddCardToPile(int player){
        
        //playerHands.get(i).getChildren().add(playerCardImages.get(i).get(j));
        
        //SequentialTransition sequence = new SequentialTransition();
//        double yLocation;
//        double xLocation;
//        
//        yLocation = playerCardImages.get(player).get(0).getTranslateY();
//        xLocation = playerCardImages.get(player).get(0).getTranslateX();
//        
//        masterPileCardImages.add(playerCardImages.get(player).remove(0)); // adding an imageView (card) from the players 
//        masterPile.getChildren().addAll(masterPileCardImages); // not sure about addAll
//        playerHands.get(player).getChildren().remove(playerHands.get(player).getChildren().size() - 1); // not sure if the particular child node will still be in the stackpane. I think this step is necessary though.
//        
//        TranslateTransition translate = new TranslateTransition(Duration.millis(1000), masterPileCardImages.get(0));
//            translate.setFromX(xLocation);
//            translate.setFromY(yLocation);
//            translate.setToX(masterPileCardImages.get(0).getX());
//            translate.setToY(masterPileCardImages.get(0).getY());
//            translate.setCycleCount(1);
//            translate.setAutoReverse(false);
//        
//        RotateTransition rotate = new RotateTransition(Duration.millis(1000), masterPileCardImages.get(0));
//            rotate.setByAngle((Math.random() * 100) * Math.pow(-1, Math.random() * 10));
//            rotate.setCycleCount(1);
//            rotate.setAutoReverse(false);
//        
//        ParallelTransition parallel = new ParallelTransition();
//        
//        parallel.getChildren().addAll(translate, rotate);
        
        //parallel.play();
    } 
    
    public void animateShowCurrentPlayer(int player){
        
    }
    
    public void animateDeclarationOfWinner(int player){
        
    }
    
    // animate the slapping
    // probably just fade in a slap image over the masterPile
    public void animateSlap(){
        
    }
    
    //animate giving the pile to who ever won that turn
    public void animateGivePileToPlayer(){
        
        
    }
    
    // sets up all sequential animations.
    // fromX -> x-coordinate the node is coming from
    // toX -> x-coordinate the node is going to
    // fromY -> y-coordinate the node is coming from
    // toY -> y-coordinate the node is going to
    // playerIndex -> index where the node is that is being moved
    // fromPlayer -> true indicates it is coming from the player, false indicates it is coming from elsewhere (the masterPile or the other player)
    private void prepareSequenceAnimation(double fromX, double toX, double fromY, double toY, int playerIndex, boolean fromPlayer){
        
        //not sure if this will work.
        
    }
    
    // same as prepareSequenceAnimation except that it is only a parallel transition animation
    private void prepareParallelAnimation(double From, double To, int playerIndex, boolean fromPlayer){
    
    }
    
    
}
