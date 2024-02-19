/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Userservices;

/**
 * FXML Controller class
 *
 * @author Aziz
 */
public class SampleController implements Initializable {

    @FXML
    private TextField tf_username;
    private TextField tf_password;
    @FXML
    private Button button_login;
    @FXML
    private Button button_sign_up;
    @FXML
    private PasswordField pf_password;
    public static User u ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleLogin(ActionEvent event) throws SQLException, IOException {
        String username = tf_username.getText();
        String password = pf_password.getText();

        // Vérifier si les champs d'utilisateur et de mot de passe sont vides
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Le nom d'utilisateur et le mot de passe sont obligatoires.");
            return;
        }

       /* Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appfirst", "root", "");

            stmt = conn.prepareStatement("SELECT * FROM user WHERE lastname = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Connexion réussie
                System.out.println("Connexion réussie!");
               u=(User) rs;
                // Charger la vue "login.fxml"
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                tf_username.clear();
                pf_password.clear();
                // Obtenir la scène actuelle
                Stage currentStage = (Stage) button_login.getScene().getWindow();

                // Afficher la nouvelle scène
                currentStage.setScene(scene);
                currentStage.show();
            } else {
                // Mauvais nom d'utilisateur ou mot de passe
                System.out.println("Nom d'utilisateur ou mot de passe invalide.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }*/
       Userservices us = new Userservices();
       u= us.getUser(username, password);
       if(u!=null){
               FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                tf_username.clear();
                pf_password.clear();
                // Obtenir la scène actuelle
                Stage currentStage = (Stage) button_login.getScene().getWindow();

                // Afficher la nouvelle scène
                currentStage.setScene(scene);
                currentStage.show();
       }
    }
    
    


    @FXML
    private void handlebutton_sign_up(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/inscrire.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                tf_username.clear();
                pf_password.clear();
                // Obtenir la scène actuelle
                Stage currentStage = (Stage) button_sign_up.getScene().getWindow();

                // Afficher la nouvelle scène
                currentStage.setScene(scene);
                currentStage.show();
}
    }
    

