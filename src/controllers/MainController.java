package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML private TextField searchField;
    @FXML private ListView<String> topChartList;
    @FXML private ListView<String> recommendationsList;
    @FXML private ListView<String> newReleasesList;
    @FXML private StackPane contentPane;

    @FXML
    public void initialize() {
        loadView("home.fxml"); // загружаем домашнюю вкладку
    }

    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/views/" + fxml));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToHome() {
        loadView("home.fxml");
    }

    @FXML
    public void goToFavorites() {
        loadView("favorites.fxml");
    }

    @FXML
    public void goToSaved() {
        loadView("saved.fxml");
    }

    @FXML
    public void goToPlaylists() {
        loadView("playlists.fxml");
    }
}
