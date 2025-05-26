package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import utils.SceneSwitcher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PlaylistsController {

    @FXML private ListView<String> playlistList;
    @FXML private TextField newPlaylistField;

    private final ObservableList<String> playlists = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadPlaylists();
        playlistList.setItems(playlists);
        playlistList.setOnMouseClicked(this::handlePlaylistClick);
    }

    private void loadPlaylists() {
        File folder = new File("playlists/");
        if (folder.exists() && folder.isDirectory()) {
            String[] names = folder.list((dir, name) -> name.endsWith(".txt"));
            if (names != null) {
                Arrays.stream(names)
                        .map(name -> name.replace(".txt", ""))
                        .forEach(playlists::add);
            }
        }
    }

    private void handlePlaylistClick(MouseEvent event) {
        String playlist = playlistList.getSelectionModel().getSelectedItem();
        if (playlist != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist_songs.fxml"));
                Parent view = loader.load();

                PlaylistSongsController controller = loader.getController();
                controller.setPlaylist(playlist);
                controller.setContentPane((StackPane) playlistList.getScene().lookup("#contentPane"));

                StackPane root = (StackPane) playlistList.getScene().lookup("#contentPane");
                if (root != null) {
                    root.getChildren().setAll(view);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addPlaylist() {
        String name = newPlaylistField.getText().trim();
        if (!name.isEmpty() && !playlists.contains(name)) {
            try {
                File folder = new File("playlists");
                if (!folder.exists()) folder.mkdirs();
                File file = new File(folder, name + ".txt");
                if (file.createNewFile()) {
                    playlists.add(name);
                    newPlaylistField.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteSelected() {
        String selected = playlistList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            File file = new File("playlists/" + selected + ".txt");
            if (file.exists()) file.delete();
            playlists.remove(selected);
        }
    }

    @FXML
    private void goToHome() {
        SceneSwitcher.switchTo("main.fxml");
    }
}
