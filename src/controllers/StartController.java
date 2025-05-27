package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private void openLogin() {
        openWindow("/views/login.fxml", "Вход");
    }

    @FXML
    private void openRegister() {
        openWindow("/views/register.fxml", "Регистрация");
    }

    private void openWindow(String fxml, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Закрываем стартовое окно
            Stage current = (Stage) stage.getScene().getWindow();
            if (current != null) current.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
