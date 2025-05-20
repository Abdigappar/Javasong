package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import utils.AudioPlayer;

import java.io.File;
import java.io.IOException;

public class FavoritesController {

    @FXML
    private ListView<String> favoritesList;

    private ObservableList<String> favoriteSongs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Автоматически загружаем все .mp3 из папки songs/
        File folder = new File("songs/");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    favoriteSongs.add(file.getName());
                }
            }
        }

        favoritesList.setItems(favoriteSongs);

        // Кастомные ячейки с кнопкой удаления
        favoritesList.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final Button deleteButton = new Button("❌");

            {
                deleteButton.setOnAction(event -> favoriteSongs.remove(getItem()));
                content.setSpacing(10);
                HBox.setHgrow(songLabel, Priority.ALWAYS);
                content.getChildren().addAll(songLabel, deleteButton);
            }

            @Override
            protected void updateItem(String song, boolean empty) {
                super.updateItem(song, empty);
                if (empty || song == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    songLabel.setText(song);
                    setGraphic(content);
                }
            }
        });

        // Воспроизведение при клике
        favoritesList.setOnMouseClicked(event -> {
            String selected = favoritesList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                File file = new File("songs/" + selected);
                AudioPlayer.play(file);
            }
        });
    }

    @FXML
    private void clearFavorites() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Очистить список любимых песен?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                favoriteSongs.clear();
            }
        });
    }

    @FXML
    private void goToHome() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            StackPane root = (StackPane) favoritesList.getScene().lookup("#contentPane");
            if (root != null) {
                root.getChildren().setAll(homeView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
