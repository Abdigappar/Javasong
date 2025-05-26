package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import utils.PlaybackHelper;

import java.io.File;
import java.io.IOException;

public class FavoritesController {

    @FXML
    private ListView<String> favoritesList;

    private final ObservableList<String> favoriteSongs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadSongsFromFolder();
        favoritesList.setItems(favoriteSongs);

        favoritesList.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final MenuButton menuButton = new MenuButton("⋮");

            {
                // 🔹 Добавить в плейлист
                MenuItem addToPlaylist = new MenuItem("Добавить в плейлист");
                addToPlaylist.setOnAction(e -> {
                    PlaybackHelper.showPlaylistChoiceDialog(getItem(), "songs");
                });

                // 🔸 Удалить из любимых
                MenuItem removeItem = new MenuItem("Удалить из любимых");
                removeItem.setOnAction(e -> {
                    favoriteSongs.remove(getItem());
                });

                menuButton.getItems().addAll(addToPlaylist, removeItem);

                content.setSpacing(10);
                HBox.setHgrow(songLabel, Priority.ALWAYS);
                content.getChildren().addAll(songLabel, menuButton);
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

        PlaybackHelper.attachPlaybackHandler(favoritesList);
    }

    private void loadSongsFromFolder() {
        File folder = new File("songs/");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    favoriteSongs.add(file.getName());
                }
            }
        }
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
