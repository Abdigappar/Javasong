package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class SongLoader {

    public static ObservableList<String> loadSongsFromFolder(String folderPath) {
        ObservableList<String> songs = FXCollections.observableArrayList();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    songs.add(file.getName()); // добавляем просто имя файла
                }
            }
        }

        return songs;
    }
}
