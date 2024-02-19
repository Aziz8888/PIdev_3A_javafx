/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionuser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author lordg
 */
public class mainfx extends Application {
    Stage stage ;
    @Override
    public void start(Stage primaryStage) {
        
        try {
           
            Parent root = FXMLLoader.load(getClass().getResource("../gui/sample.fxml"));
           // stage.getIcons().add(new Image("../gui/image/logo.png"));
            Scene scene = new Scene(root);
            Image logo = new Image(getClass().getResourceAsStream("../gui/image/logo.png"));
             primaryStage.getIcons().add(logo);
            //primaryStage.setTitle("assurtout");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(mainfx.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
