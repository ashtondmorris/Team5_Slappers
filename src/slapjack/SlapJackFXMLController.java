package slapjack;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
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
    @FXML
        ImageView diamondTurn1;
    @FXML
        ImageView diamondTurn2;
    @FXML
        ImageView keys1;
    @FXML
        ImageView keys2;
    @FXML
        Button playAgainButton;
    
    private SlapJackDriver slapJackDriver;
    private Stage stage;
    private Scene scene;
    public ArrayList<ImageView> player1CardImages;
    public ArrayList<ImageView> player2CardImages;
    public ArrayList<ImageView> masterPileCardImages;
    public ArrayList<ArrayList<ImageView>> playerCardImages;
    public ArrayList<StackPane> playerHands;
    
    AudioClip cardPlace1;
    AudioClip cardSlide1;
    AudioClip cardShuffle;
    AudioClip FireImpact;
    
    // add reset
    // add controls/rules

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
        cardShuffle = new AudioClip(getClass().getResource("sounds/cardShuffle.wav").toExternalForm());
        cardPlace1 = new AudioClip(getClass().getResource("sounds/cardPlace1.wav").toExternalForm());
        cardSlide1 = new AudioClip(getClass().getResource("sounds/cardSlide1.wav").toExternalForm());
        FireImpact = new AudioClip(getClass().getResource("sounds/Fire_Impact1.wav").toExternalForm());
        slapJackDriver = new SlapJackDriver(this); 
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
                    iView.setOpacity(0);
                    
                    playerCardImages.get(i).add(iView);
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
        
        System.out.print("Player 1's top card: " + players.get(0).getHand().get(players.get(0).getHand().size() - 1).toString());
        System.out.print("Player 2's top card: " + players.get(1).getHand().get(players.get(1).getHand().size() - 1).toString());
               
        for(int j = 0; j < playerCardImages.get(0).size(); j++){ // each card
            for(int i = 0; i < playerCardImages.size(); i++){ // each player
                
                System.out.print("Player "+ i +"'s card: " + players.get(i).getHand().get(j).toString());
        
                
                playerHands.get(i).getChildren().add(playerCardImages.get(i).get(j)); 
                
                yLocation = playerCardImages.get(i).get(j).getTranslateY();
                xLocation = playerCardImages.get(i).get(j).getTranslateX();
                        
                TranslateTransition translate = new TranslateTransition(Duration.millis(100), playerCardImages.get(i).get(j));
                    translate.setFromX(Math.pow(-1, i) * 130);
                    translate.setFromY(-170);
                    translate.setToX(xLocation);
                    translate.setToY(yLocation);
                    translate.setCycleCount(1);
                    translate.setAutoReverse(false);

                RotateTransition rotate = new RotateTransition(Duration.millis(100), playerCardImages.get(i).get(j));
                    rotate.setByAngle((Math.random() * 2) * Math.pow(-1, i));
                    rotate.setCycleCount(1);
                    rotate.setAutoReverse(false);
                    
                FadeTransition fade = new FadeTransition(Duration.millis(100), playerCardImages.get(i).get(j));
                    fade.setToValue(1);
                    fade.setCycleCount(1);
                    fade.setAutoReverse(false);
                
                
                ParallelTransition parallelTranny = new ParallelTransition();

                parallelTranny.getChildren().addAll(translate, rotate, fade);
                sequence.getChildren().add(parallelTranny);
            }
        }
        cardShuffle.setCycleCount(4);
        cardShuffle.setRate(2);
        cardShuffle.play();
                
        sequence.play();
        sequence.setOnFinished(e -> cardShuffle.stop());
        // do animation
    }
    
    // takes in the giver and receiver of the giveCard method.
    // animates the process of one player giving the other player their card
    public void animateGiveCard(int giver, int receiver){
        
        playerCardImages.get(receiver).add(0, playerCardImages.get(giver).remove(playerCardImages.get(giver).size() - 1));
        playerHands.get(receiver).getChildren().add(0, playerHands.get(giver).getChildren().remove(playerHands.get(giver).getChildren().size() - 1));
        
        TranslateTransition translate = new TranslateTransition(Duration.millis(1500), playerCardImages.get(receiver).get(playerCardImages.get(receiver).size() - 1));
            if(receiver == 0){
                translate.setFromX(playerHands.get(giver).getWidth() * 2.9);
            }
            else{
                translate.setFromX(playerHands.get(giver).getWidth() * -2.9);
            }
            
            translate.setFromY(0);
            translate.setToX(playerHands.get(receiver).getChildren().get(0).getTranslateX());
            translate.setToY(playerHands.get(receiver).getChildren().get(0).getTranslateY());
            
            
            translate.play();
    }
    
    // takes in the players hand and animates them shuffling their deck some how
    public void animateShuffleHand(int player){
        // ehhhhh
    }
    
    //
    public void animateAddCardToPile(int player, ArrayList<Card> master){
        
        //playerHands.get(i).getChildren().add(playerCardImages.get(i).get(j));
        
        SequentialTransition sequence = new SequentialTransition();
        
        // getting the face image into the master pile stackpane
        master.get(master.size() - 1).flipToFace();
        
        ImageView iView = new ImageView();
            iView.setFitWidth(80);
            iView.setPreserveRatio(true);
            iView.setSmooth(true);
            iView.setImage(master.get(master.size() - 1).getImage());
            
        masterPileCardImages.add(iView);
        masterPile.getChildren().add(masterPileCardImages.get(masterPileCardImages.size() - 1));
        
        // might be removing the wrong end of these 2 things
        if(!playerCardImages.get(player).isEmpty()){
            playerCardImages.get(player).remove(playerCardImages.get(player).size() - 1); // removing the card from the player's image arraylist //something bad happened on this line
        } else {
            System.err.println("something went wrong on line 265 of SlapJackFXMLController.java");
        }
        playerHands.get(player).getChildren().remove(playerHands.get(player).getChildren().size() - 1); // removing the image child from the stackpane
        
        
        //masterPileCardImages.add(playerCardImages.get(player).remove(0)); // adding an imageView (card) from the players 
        //masterPile.getChildren().add(masterPileCardImages.get(masterPileCardImages.size() - 1)); // not sure about addAll
        //playerHands.get(player).getChildren().remove(playerHands.get(player).getChildren().size() - 1); // not sure if the particular child node will still be in the stackpane. I think this step is necessary though.
        
        TranslateTransition translate = new TranslateTransition(Duration.millis(200), masterPileCardImages.get(masterPileCardImages.size() - 1));
            if(player == 0){
                translate.setFromX(masterPileCardImages.get(0).getX() - 60);
            }
            else {
                translate.setFromX(masterPileCardImages.get(0).getX() + 60);
            }
            translate.setFromY(masterPileCardImages.get(0).getY());
            translate.setToX(masterPileCardImages.get(0).getX() + (Math.random()*24 * Math.pow(-1, (player + 1))));
            translate.setToY(masterPileCardImages.get(0).getY() + (Math.random()*4 * Math.pow(-1, (player + 1))));
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
        
        RotateTransition rotate = new RotateTransition(Duration.millis(200), masterPileCardImages.get(masterPileCardImages.size() - 1));
            rotate.setByAngle((Math.random() * 50) * Math.pow(-1, (player + 1) * 3));
            rotate.setCycleCount(1);
            rotate.setAutoReverse(false);
        
        ParallelTransition parallel = new ParallelTransition();
        
        parallel.getChildren().addAll(translate, rotate);
        
        cardPlace1.play();
        parallel.play();
    } 
    
    public void animateShowCurrentPlayer(int player){
        
        ImageView diamondTurn; // = new ImageView();
        double fromAngle;
        double toAngle;
        
        if(player == 0){
            diamondTurn = diamondTurn1;
            toAngle = 0;
            fromAngle = 90;
            diamondTurn2.setOpacity(0);
        } else {
            diamondTurn = diamondTurn2;
            toAngle = 0;
            fromAngle = -90;
            diamondTurn1.setOpacity(0);
        }
        
        FadeTransition fade = new FadeTransition(Duration.millis(200), diamondTurn);
            fade.setFromValue(0);
            fade.setToValue(1);
            //fade.setOnFinished(e -> diamondTurn.setOpacity(0));
        
        RotateTransition rotate = new RotateTransition(Duration.millis(200), diamondTurn);
            rotate.setFromAngle(fromAngle);
            rotate.setToAngle(toAngle);
            rotate.setCycleCount(1);
            rotate.setAutoReverse(false);
        
        ParallelTransition parallel = new ParallelTransition();
        
        parallel.getChildren().addAll(fade, rotate);
        
        parallel.play();
        
    }
    
    public void animateDeclarationOfWinner(int player){
        
        ArrayList<ImageView> winner;
        StackPane winnerStackPane;
        ParallelTransition parallel;
        
        if(!player1CardImages.isEmpty()){
            winner = player1CardImages;
            winnerStackPane = playerHands.get(0);
        } else {
            winner = player2CardImages;
            winnerStackPane = playerHands.get(1);
        }
        
        //Math.pow(-1, (i + Math.random() * 10)) * Math.random() * 400
        for(int i = 0; i < winner.size(); i++){
            TranslateTransition translate = new TranslateTransition(Duration.millis(500), winnerStackPane.getChildren().get(winnerStackPane.getChildren().size() - (i + 1)));
                translate.setFromX(0);
                translate.setFromY(0);
                translate.setToX(Math.pow(-1, i) * Math.random()*350);
                translate.setToY(Math.pow(-1, i) * Math.random()*350);
                translate.setAutoReverse(false);
                translate.setCycleCount(1);
            
            FadeTransition fade = new FadeTransition(Duration.millis(500), winnerStackPane.getChildren().get(winnerStackPane.getChildren().size() - (i + 1)));
                fade.setFromValue(1);
                fade.setToValue(0);
                fade.setAutoReverse(false);
                fade.setCycleCount(1);
            
            RotateTransition rotate = new RotateTransition(Duration.millis(500), winnerStackPane.getChildren().get(winnerStackPane.getChildren().size() - (i + 1)));
                rotate.setToAngle(Math.random() * 360);
                
            //winnerStackPane.getChildren().remove(winnerStackPane.getChildren().size() - 1);
                parallel = new ParallelTransition();
                parallel.getChildren().addAll(translate, fade, rotate);
                parallel.setDelay(Duration.millis(1000));
                parallel.play();
                parallel.setOnFinished(e -> reset());
        }
    }
    
    private void reset(){
        FadeTransition fade = new FadeTransition(Duration.millis(300), playAgainButton);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        
        playAgainButton.setOnAction(e -> buttonAction());
        
    }
    
    private void buttonAction(){
        slapJackDriver = new SlapJackDriver(this); 
        FadeTransition fade = new FadeTransition(Duration.millis(300), playAgainButton);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }
    
    // animate the slapping
    // probably just fade in a slap image over the masterPile
    public void animateSlap(int player){
        
        ImageView iView = new ImageView();
            iView.setImage(new Image(getClass().getResource("images/slap.png").toExternalForm()));
            iView.setFitWidth(150);
            iView.setPreserveRatio(true);
            iView.setSmooth(true);
            iView.setOpacity(1);
            iView.setTranslateY(player4Hand.getHeight() * -1.7);
        
        player4Hand.getChildren().add(iView);
            
        FadeTransition fade = new FadeTransition(Duration.millis(1000), iView);
            fade.setToValue(0);
            fade.setAutoReverse(false);
        
        FireImpact.play();    
        fade.play();
        fade.setOnFinished(e -> player4Hand.getChildren().remove(iView));
    }
    
    //animate giving the pile to who ever won that turn
    public void animateGivePileToPlayer(int playerIndex, Player player){
        System.out.println("giving pile to player...(animation)");
        player.getHand().get(player.getHand().size() - 1).flipToBack();
        
        SequentialTransition sequence = new SequentialTransition();
        
        ImageView iView = new ImageView();
            iView.setImage(player.getHand().get(player.getHand().size() - 1).getImage());
            iView.setFitWidth(80);
            iView.setPreserveRatio(true);
            iView.setSmooth(true);
            
        playerCardImages.get(playerIndex).add(iView);
        playerHands.get(playerIndex).getChildren().add(playerCardImages.get(playerIndex).get(playerCardImages.get(playerIndex).size() - 1));
        
        masterPileCardImages.remove(masterPileCardImages.size() - 1); // removing the card from the masterPile's image arraylist
        masterPile.getChildren().remove(masterPile.getChildren().size() - 1); // removing the image child from the stackpane
        
        
        TranslateTransition translate = new TranslateTransition(Duration.millis(800 - Math.random() * 300), playerCardImages.get(playerIndex).get(playerCardImages.get(playerIndex).size() - 1));
            if(playerIndex == 0){
                translate.setFromX((playerHands.get(0).getWidth()) * 4 / 2.5);
            }
            else {
                translate.setFromX((playerHands.get(0).getWidth()) * 4 / -2.5);
            }

            translate.setFromY(0);
            translate.setToX(playerCardImages.get(playerIndex).get(0).getX());
            translate.setToY(playerCardImages.get(playerIndex).get(0).getY());
            translate.setCycleCount(1);
            translate.setAutoReverse(false);
        
        RotateTransition rotate = new RotateTransition(Duration.millis(400), playerCardImages.get(playerIndex).get(playerCardImages.get(playerIndex).size() - 1));
            rotate.setToAngle(0);
            rotate.setCycleCount(1);
            rotate.setAutoReverse(false);
        
        ParallelTransition parallel = new ParallelTransition();
        
        parallel.getChildren().addAll(translate, rotate);
        
        parallel.play();
        cardSlide1.play();
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




/* CREDITS
Some of the sounds in this project were created by ViRiX Dreamcore (David McKee) soundcloud.com/virix
*/