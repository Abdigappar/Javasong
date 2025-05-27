package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Заполните все поля!");
            return;
        }

        DBManager.connect();
        try {
            Connection conn = DBManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            // Переход в главное окно
            Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Музыкальный проигрыватель");
            stage.setScene(new Scene(root));
            stage.show();

            // Закрываем окно регистрации
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            statusLabel.setText("Ошибка: пользователь уже существует?");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Вход");
            stage.setScene(new Scene(loginView));
            stage.show();

            // Закрыть текущее окно
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
