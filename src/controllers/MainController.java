package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private TextField searchField;
    @FXML private StackPane contentPane;

    @FXML
    public void initialize() {
        goToHome();  // загрузка домашнего экрана при старте
    }

    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void goToHome() {
        loadView("home.fxml");
    }

    @FXML public void goToFavorites() {
        loadView("favorites.fxml");
    }

    @FXML public void goToSaved() {
        loadView("saved.fxml");
    }

    @FXML public void goToPlaylists() {
        loadView("playlists.fxml");
    }
}
