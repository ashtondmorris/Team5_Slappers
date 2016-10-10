
package slapjack;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class SlapJack extends Application  {
    
    //public static SlapJackFXMLController controller;
    
    @Override
    public void start(Stage primaryStage) {
        try{
        //GridPane page = (GridPane) FXMLLoader.load(SlapJack.class.getResource("SlapJackFXML.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SlapJackFXML.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);
    
        SlapJackFXMLController controller = loader.getController();
        //controller.setStage(primaryStage);
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case A: controller.turn("A"); break;
                case S: controller.slap("S"); break;
                case K: controller.turn("K"); break;
                case L: controller.slap("L"); break;
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slap Jack");
        primaryStage.getIcons().add(new Image(getClass().getResource("images/Ace_Spades_Icon.png").toExternalForm()));
    
        primaryStage.setMinHeight(520);
        primaryStage.setMinWidth(700);
        
        primaryStage.show();
        
        } catch (Exception ex) {
            Logger.getLogger(SlapJack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
