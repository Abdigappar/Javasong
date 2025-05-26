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
import utils.PlaybackHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlaylistSongsController {

    @FXML private Label playlistTitle;
    @FXML private ListView<String> songList;

    private String playlistName;
    private StackPane contentPane;
    private final ObservableList<String> songs = FXCollections.observableArrayList();

    public void setPlaylist(String name) {
        this.playlistName = name;
        playlistTitle.setText("🎶 Плейлист: " + name);
        loadSongsFromFolder(name);
    }

    public void setContentPane(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    private void loadSongsFromFolder(String name) {
        File folder = new File("playlists/" + name);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, filename) -> filename.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                songs.clear();
                for (File file : files) {
                    songs.add(file.getName());
                }
            }
        }

        songList.setItems(songs);

        songList.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final MenuButton menuButton = new MenuButton("⋮");

            {
                MenuItem play = new MenuItem("▶ Воспроизвести");
                play.setOnAction(e -> {
                    File file = new File("playlists/" + playlistName + "/" + getItem());
                    AudioPlayer.play(file);
                });

                MenuItem remove = new MenuItem("Удалить из плейлиста");
                remove.setOnAction(e -> {
                    File file = new File("playlists/" + playlistName + "/" + getItem());
                    if (file.exists()) file.delete();
                    songs.remove(getItem());
                });

                MenuItem addToOther = new MenuItem("Добавить в другой плейлист");
                addToOther.setOnAction(e -> {
                    PlaybackHelper.showPlaylistChoiceDialog(getItem(), "playlists/" + playlistName);
                });

                MenuItem addToFavorites = new MenuItem("Добавить в любимые песни");
                addToFavorites.setOnAction(e -> {
                    try {
                        File song = new File("playlists/" + playlistName + "/" + getItem());
                        File target = new File("songs/" + song.getName());
                        java.nio.file.Files.copy(song.toPath(), target.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                MenuItem saveSong = new MenuItem("Сохранить");
                saveSong.setOnAction(e -> {
                    File source = new File("playlists/" + playlistName + "/" + getItem());
                    File target = new File("downloads/" + getItem());
                    try {
                        if (!target.getParentFile().exists()) target.getParentFile().mkdirs();
                        java.nio.file.Files.copy(source.toPath(), target.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                menuButton.getItems().addAll(play, addToOther, addToFavorites, saveSong, remove);

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
    }

    @FXML
    private void goBack() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/views/playlists.fxml"));
            if (contentPane != null) {
                contentPane.getChildren().setAll(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
