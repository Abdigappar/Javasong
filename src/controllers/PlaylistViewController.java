package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import utils.AudioPlayer;

import java.io.File;
import java.io.IOException;

public class PlaylistViewController {

    @FXML private Label playlistTitle;
    @FXML private ListView<String> songsList;

    private String playlistName;

    public void setPlaylistName(String name) {
        this.playlistName = name;
        playlistTitle.setText("🎧 Плейлист: " + name);

        // пока просто подгружаем файлы из папки
        ObservableList<String> songs = FXCollections.observableArrayList();
        File folder = new File("songs/");
        if (folder.exists()) {
            File[] files = folder.listFiles((dir, fname) -> fname.endsWith(".mp3"));
            if (files != null) {
                for (File file : files) songs.add(file.getName());
            }
        }
        songsList.setItems(songs);

        songsList.setOnMouseClicked(event -> {
            String selected = songsList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                AudioPlayer.play(new File("songs/" + selected));
            }
        });
    }

    @FXML
    private void goBack() {
        try {
            Parent playlistsView = FXMLLoader.load(getClass().getResource("/views/playlists.fxml"));
            StackPane root = (StackPane) songsList.getScene().lookup("#contentPane");
            if (root != null) {
                root.getChildren().setAll(playlistsView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
