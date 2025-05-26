package utils;

import javafx.scene.control.ChoiceDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PlaylistHelper {

    public static void showAddToPlaylistDialog(String songName) {
        List<String> playlistNames = loadPlaylistNames();

        if (playlistNames.isEmpty()) {
            System.out.println("Нет плейлистов для добавления.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(playlistNames.get(0), playlistNames);
        dialog.setTitle("Добавить в плейлист");
        dialog.setHeaderText("Выберите плейлист для добавления:");
        dialog.setContentText("Плейлист:");

        dialog.showAndWait().ifPresent(selected -> {
            File file = new File("playlists/" + selected + ".txt");
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(songName + "\n");
                System.out.println("Добавлено в плейлист: " + selected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<String> loadPlaylistNames() {
        File folder = new File("playlists/");
        List<String> names = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    names.add(file.getName().replace(".txt", ""));
                }
            }
        }
        return names;
    }
}
