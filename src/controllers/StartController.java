package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {

    @FXML private Button openLoginButton;  // свяжется с кнопкой "Войти"
    @FXML private Button openRegisterButton;  // свяжется с кнопкой "Регистрация"

    @FXML
    private void openLogin() {
        openWindow("/views/login.fxml", "Вход", openLoginButton);
    }

    @FXML
    private void openRegister() {
        openWindow("/views/register.fxml", "Регистрация", openRegisterButton);
    }

    private void openWindow(String fxml, String title, Button sourceButton) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Закрываем стартовое окно (получаем Stage по кнопке)
            Stage currentStage = (Stage) sourceButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
