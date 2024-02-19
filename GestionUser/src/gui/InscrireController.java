/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Userservices;

/**
 * FXML Controller class
 *
 * @author Aziz
 */
public class InscrireController implements Initializable {

    @FXML
    private Button button_sign_up;
    @FXML
    private Button button_login;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
@FXML
private void ajouter(ActionEvent event) {
String email = tf_email.getText();
String lastname = tf_lastname.getText();
String password = tf_password.getText();
   if (email.isEmpty() || !email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
    // Si l'email est vide ou invalide
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Email invalide");
    alert.setContentText("Veuillez saisir un email valide.");
    alert.showAndWait();
    return;
}

if (lastname.isEmpty() || !lastname.matches("[a-zA-Z]+")) {
    // Si le nom est vide ou contient des caractères non alphabétiques
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Nom invalide");
    alert.setContentText("Veuillez saisir un nom valide (lettres uniquement).");
    alert.showAndWait();
    return;
}

if (password.isEmpty() || password.length() < 8 || !password.matches(".*[A-Z]+.*") || !password.matches(".*[a-z]+.*") || !password.matches(".*[0-9]+.*")) {
    // Si le mot de passe est vide, trop court ou ne contient pas de lettres majuscules, minuscules et chiffres
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Mot de passe invalide");
    alert.setContentText("Veuillez saisir un mot de passe valide (8 caractères minimum, avec au moins une lettre majuscule, une lettre minuscule et un chiffre).");
    alert.showAndWait();
    return;
}

Userservices us = new Userservices();
User s = new User(email, lastname, password, "[]");
us.ajouter(s);

Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Succès");
alert.setHeaderText("Utilisateur ajouté");
alert.setContentText("L'utilisateur a été ajouté avec succès.");
alert.showAndWait();
}
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sample.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                tf_username.clear();
               
                // Obtenir la scène actuelle
                Stage currentStage = (Stage) button_login.getScene().getWindow();

                // Afficher la nouvelle scène
                currentStage.setScene(scene);
                currentStage.show();
    }
  
    
}
