package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.SceneSwitcher;

public class PlaylistsController {

    @FXML private ListView<String> playlistList;
    @FXML private TextField newPlaylistField;

    private final ObservableList<String> playlists = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Инициализируем список, при необходимости можно загрузить из базы
        playlists.addAll("Workout", "Chill Vibes", "Classics");
        playlistList.setItems(playlists);
    }

    @FXML
    private void addPlaylist() {
        String name = newPlaylistField.getText().trim();
        if (!name.isEmpty() && !playlists.contains(name)) {
            playlists.add(name);
            newPlaylistField.clear();
        }
    }

    @FXML
    private void deleteSelected() {
        String selected = playlistList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            playlists.remove(selected);
        }
    }

    @FXML
    private void goToHome() {
        SceneSwitcher.switchTo("main.fxml");
    }
}
