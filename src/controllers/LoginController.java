package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    public void handleLogin() {
        DBManager.connect();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DBManager.authenticate(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Музыкальный проигрыватель");
                stage.setScene(new Scene(root));
                stage.show();

                // закрыть окно логина
                ((Stage) usernameField.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Неверный логин или пароль.");
        }
    }
}
