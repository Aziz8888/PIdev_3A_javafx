/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entity.User;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import outils.MyDB;

/**
 * FXML Controller class
 *
 * @author Aziz
 */
public class LoginController implements Initializable {

    @FXML
    private Button button_logout;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_email;
    @FXML
    private Button button_add;
    @FXML
    private Button button_delete;
    @FXML
    private RadioButton rb_admin;
    @FXML
    private RadioButton rb_client;
    @FXML
    private Button button_update;
    @FXML
    private TableView<User> tv_user;
    @FXML
    private TableColumn<User,Integer> col_id;
    @FXML
    private TableColumn<User,String> col_lastname;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private TableColumn<User, String> col_email;
    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TextField recherche;
    @FXML
    private Label test;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
      test.setText(SampleController.u.getLastname());
      
    ObservableList<User> users = FXCollections.observableArrayList();
    MyDB db = MyDB.createorgetInstance();
    try {
        Statement stmt = db.getCon().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id,lastname, email, password, roles FROM user");
        while (rs.next()) {
            users.add(new User( rs.getInt("id"),rs.getString("email"), rs.getString("lastname"), rs.getString("password"), rs.getString("roles")));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    tv_user.setItems(users);
    }
    
    @FXML
private void addUser(ActionEvent event) {
    String lastname = tf_lastname.getText().trim();
    String email = tf_email.getText().trim();
    String password = tf_password.getText().trim();
    String role = (rb_admin.isSelected()) ? "admin" : "client";

    // Vérifier le format du champ Last name
    if (!lastname.matches("^[a-zA-Z]+$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Last name should contain only letters.");
        alert.showAndWait();
        return;
    }

    // Vérifier le format du champ Email
    if (!email.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,3}$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Email should be in the format 'user@example.com'.");
        alert.showAndWait();
        return;
    }

    // Vérifier le format du champ Password
    if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Password should contain at least one letter, one number, and no special characters.");
        alert.showAndWait();
        return;
    }

    // Ajouter l'utilisateur s'il a passé les contrôles de saisie
    User user = new User(lastname, email, password, role);
    MyDB db = MyDB.createorgetInstance();
    String query = "INSERT INTO user(lastname, email, password, roles) VALUES ('" + lastname + "', '" + email + "', '" + password + "', '" + role + "')";
    try {
        Statement stmt = db.getCon().createStatement();
        int rs = stmt.executeUpdate(query);
        if (rs == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("User added successfully");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error adding user");
        alert.setContentText("An error occurred while adding the user to the database.");
        alert.showAndWait();
    }

      User newUser = new User(email, lastname, password, role);
    tv_user.getItems().add(newUser);
    
    // Effacer les champs du formulaire d'ajout
    tf_lastname.setText("");
    tf_email.setText("");
    tf_password.setText("");
    rb_client.setSelected(true);
}
@FXML
private void deleteUser(ActionEvent event) {
    User user = tv_user.getSelectionModel().getSelectedItem();
    if (user == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Select a User");
        alert.setHeaderText("Please select a user from the table to delete.");
        alert.showAndWait();
        return;
    }

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Are you sure you want to delete this user?");
    alert.setContentText(user.getLastname()+ " will be deleted from the database.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appfirst", "root", "");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM user WHERE id = ?");
            stmt.setInt(1, user.getId());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("User deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("User has been deleted successfully.");
                successAlert.showAndWait();
                loadUsers(); // recharge la table
            } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Delete failed");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to delete user.");
                errorAlert.showAndWait();
                loadUsers(); // recharge la table view
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Database error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while trying to delete user.");
            errorAlert.showAndWait();
        }
    } 
}


private ObservableList<User> users;








@FXML
private void handleLogout(ActionEvent event) throws IOException {
    // Charger la vue de connexion
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sample.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);

    // Obtenir la scène actuelle
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Afficher la nouvelle scène
    currentStage.setScene(scene);
    currentStage.show();
}
@FXML
private void updateUser(ActionEvent event) {
    User user = tv_user.getSelectionModel().getSelectedItem();
    if (user == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Select a User");
        alert.setHeaderText("Please select a user from the table to update.");
        alert.showAndWait();
        return;
    }
    String lastname = tf_lastname.getText().trim();
    String email = tf_email.getText().trim();
    String password = tf_password.getText().trim();
    String role = (rb_admin.isSelected()) ? "admin" : "client";

    // Vérifier le format du champ Last name
    if (!lastname.matches("^[a-zA-Z]+$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Last name should contain only letters.");
        alert.showAndWait();
        return;
    }

    // Vérifier le format du champ Email
    if (!email.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,3}$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Email should be in the format 'user@example.com'.");
        alert.showAndWait();
        return;
    }

    // Vérifier le format du champ Password
    if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$")) {
        // Afficher un message d'erreur si le format est invalide
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid input");
        alert.setContentText("Password should contain at least one letter, one number, and no special characters.");
        alert.showAndWait();
        return;
    }

    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appfirst", "root", "");
        PreparedStatement stmt = conn.prepareStatement("UPDATE user SET lastname = ?, email = ?, password = ?, roles = ? WHERE id = ?");
        stmt.setString(1, lastname);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, role);
        stmt.setInt(5, user.getId());
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User updated");
            alert.setHeaderText(null);
            alert.setContentText("User information has been updated successfully.");
            alert.showAndWait();
            clearFields();
            loadUsers();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User update failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update user information.");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while trying to update user information.");
        alert.showAndWait();
    }
}

    // Mettre à jour l'utilisateur s'il a passé les contrôles de saisie
 
     @FXML
    void generateQRCode(ActionEvent event) {
        // Récupérer l'utilisateur sélectionné dans la tv_user
        User selectedUser = tv_user.getSelectionModel().getSelectedItem();

        // Vérifier que l'utilisateur est sélectionné
        if (selectedUser == null) {
            // Afficher un message d'erreur si aucun utilisateur n'est sélectionné
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun utilisateur sélectionné");
            alert.setContentText("Sélectionnez un utilisateur dans la liste pour générer le code QR.");
            alert.showAndWait();
            return;
        }

        // Créer une chaîne de texte formatée avec les informations de l'utilisateur
        String text = String.format("Nom: %s\nEmail: %s\nRôle: %s", selectedUser.getLastname(), selectedUser.getEmail(),
                selectedUser.getRoles());

        // Générer le code QR à partir de la chaîne de texte
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            // Afficher un message d'erreur si la génération du code QR a échoué
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la génération du code QR");
            alert.setContentText("Une erreur s'est produite lors de la génération du code QR. Veuillez réessayer.");
            alert.showAndWait();
            return;
        }

        // Convertir le code QR en image
        BufferedImage qrImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();
        Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 200);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // Afficher le code QR dans une nouvelle fenêtre
        Stage qrStage = new Stage();
        qrStage.setTitle("Code QR");
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(qrImage, null));
        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        qrStage.setScene(new Scene(pane, 220, 220));
        qrStage.show();
    }



private void clearFields() {
    tf_lastname.setText("");
    tf_email.setText("");
    tf_password.setText("");
 rb_client.setSelected(true);
}
private void loadUsers() throws SQLException {
    // Connexion à la base de données
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appfirst", "root", "");

        stmt = conn.prepareStatement("SELECT * FROM user");

        rs = stmt.executeQuery();

        // Effacer la table existante
        tv_user.getItems().clear();

        // Parcourir les résultats et ajouter chaque utilisateur à la table
        while (rs.next()) {
            User user = new User(rs.getInt("id"),rs.getString("lastname"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
            tv_user.getItems().add(user);
            
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}

    @FXML
  

    private void recherche(ActionEvent event) throws SQLException{
    // Initialiser la liste d'utilisateurs
    users = FXCollections.observableArrayList();

    // Charger les utilisateurs au démarrage de l'application
    loadUsers();

    // Ajouter un listener sur le champ de recherche
    recherche.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        try {
            users.clear();
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appfirst", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE lastname LIKE ?");
            stmt.setString(1, newValue + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User(rs.getInt("id"),rs.getString("lastname"), rs.getString("email"), rs.getString("password"), rs.getString("roles"));
                users.add(user);
            }
            
            tv_user.setItems(users);
        } catch (SQLException ex) {
        }
    });
}
}
    
     
 

    
