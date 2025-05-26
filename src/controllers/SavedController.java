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
import utils.PlaybackHelper;

import java.io.File;
import java.io.IOException;

public class SavedController {

    @FXML
    private ListView<String> savedList;

    private final ObservableList<String> savedSongs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadSongsFromFolder();
        savedList.setItems(savedSongs);

        savedList.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final MenuButton menuButton = new MenuButton("⋮");

            {
                MenuItem addToPlaylist = new MenuItem("Добавить в плейлист");
                addToPlaylist.setOnAction(e -> {
                    PlaybackHelper.showPlaylistChoiceDialog(getItem(), "songs");
                });

                MenuItem removeItem = new MenuItem("Удалить из сохранённых");
                removeItem.setOnAction(e -> {
                    getListView().getItems().remove(getItem()); // правильный доступ к listView
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

        PlaybackHelper.attachPlaybackHandler(savedList);
    }

    private void loadSongsFromFolder() {
        File folder = new File("songs/");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    savedSongs.add(file.getName());
                }
            }
        }
    }

    @FXML
    private void clearSaved() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить все сохранённые песни?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                savedSongs.clear();
            }
        });
    }

    @FXML
    private void goToHome() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            StackPane root = (StackPane) savedList.getScene().lookup("#contentPane");
            if (root != null) {
                root.getChildren().setAll(homeView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
