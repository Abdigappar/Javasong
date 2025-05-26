package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import utils.PlaybackHelper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class HomeController {

    @FXML private ListView<String> topChartList;
    @FXML private ListView<String> recommendationsList;
    @FXML private ListView<String> newReleasesList;

    private final Set<String> favorites = new HashSet<>();
    private ObservableList<String> allSongs;

    @FXML
    public void initialize() {
        allSongs = loadSongsFromFolder();

        setupList(topChartList);
        setupList(recommendationsList);
        setupList(newReleasesList);
    }

    private ObservableList<String> loadSongsFromFolder() {
        ObservableList<String> songs = FXCollections.observableArrayList();
        File folder = new File("songs/");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    songs.add(file.getName());
                }
            }
        }
        return songs;
    }

    private void setupList(ListView<String> listView) {
        listView.setItems(allSongs);

        listView.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final MenuButton menuButton = new MenuButton("⋮");

            {
                MenuItem addToPlaylist = new MenuItem("Добавить в плейлист");
                addToPlaylist.setOnAction(e -> {
                    PlaybackHelper.showPlaylistChoiceDialog(getItem(), "songs");
                });

                MenuItem addToFavorites = new MenuItem("Добавить в любимые");
                addToFavorites.setOnAction(e -> {
                    favorites.add(getItem());
                    showInfo("Добавлено в любимые: " + getItem());
                });

                menuButton.getItems().addAll(addToPlaylist, addToFavorites);

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

        PlaybackHelper.attachPlaybackHandler(listView);
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
