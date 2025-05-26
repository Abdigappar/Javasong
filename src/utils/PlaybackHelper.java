package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PlaybackHelper {

    public static void attachPlaybackHandler(ListView<String> listView) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    File file = new File("songs/" + selected);

                    // Загружаем весь список mp3-файлов
                    File folder = new File("songs/");
                    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
                    List<File> allSongs = files != null ? Arrays.asList(files) : new ArrayList<>();

                    // Находим индекс текущей песни
                    int index = allSongs.indexOf(file);

                    // Воспроизводим с настройкой плейлиста
                    AudioPlayer.setPlaylist(allSongs, index);
                }
            }
        });
    }

    public static void showPlaylistChoiceDialog(String songName, String folderName) {
        File playlistsDir = new File("playlists/");
        if (!playlistsDir.exists() || !playlistsDir.isDirectory()) {
            showError("Папка плейлистов не найдена.");
            return;
        }

        String[] playlistFiles = playlistsDir.list((dir, name) -> name.endsWith(".txt"));
        if (playlistFiles == null || playlistFiles.length == 0) {
            showError("Нет доступных плейлистов для добавления.");
            return;
        }

        List<String> playlistNames = Arrays.stream(playlistFiles)
                .map(name -> name.replace(".txt", ""))
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(playlistNames.get(0), playlistNames);
        dialog.setTitle("Добавить в плейлист");
        dialog.setHeaderText("Выберите плейлист для добавления:");
        dialog.setContentText("Плейлист:");

        dialog.showAndWait().ifPresent(playlist -> {
            File source = new File(folderName + "/" + songName);
            File targetDir = new File("playlists/" + playlist);
            if (!targetDir.exists()) targetDir.mkdirs();

            File target = new File(targetDir, songName);

            try {
                java.nio.file.Files.copy(
                        source.toPath(),
                        target.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                showError("Ошибка при добавлении песни в плейлист: " + e.getMessage());
            }
        });
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
