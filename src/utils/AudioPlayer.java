package utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayer {

    private static MediaPlayer player;
    private static final ObjectProperty<MediaPlayer> currentPlayerProperty = new SimpleObjectProperty<>();

    private static final List<File> playlist = new ArrayList<>();
    private static int currentIndex = -1;

    public static void play(File file) {
        stop();
        if (file == null || !file.exists()) return;

        Media media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        currentPlayerProperty.set(player);
        player.play();
    }

    public static void setPlaylist(List<File> files, int indexToStart) {
        playlist.clear();
        playlist.addAll(files);
        currentIndex = indexToStart;
        if (indexToStart >= 0 && indexToStart < playlist.size()) {
            play(playlist.get(indexToStart));
        }
    }

    public static void playNext() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex + 1) % playlist.size();
        play(playlist.get(currentIndex));
    }

    public static void playPrevious() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        play(playlist.get(currentIndex));
    }

    public static void stop() {
        if (player != null) {
            player.stop();
        }
    }

    public static void pauseOrResume() {
        if (player != null) {
            if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                player.pause();
            } else {
                player.play();
            }
        }
    }

    public static MediaPlayer getPlayer() {
        return player;
    }

    public static ObjectProperty<MediaPlayer> playerProperty() {
        return currentPlayerProperty;
    }

    public static String getCurrentTrackName() {
        if (player == null || player.getMedia() == null) return "-";
        return new File(player.getMedia().getSource()).getName();
    }

    public static Duration getCurrentTime() {
        return player != null ? player.getCurrentTime() : Duration.ZERO;
    }

    public static Duration getTotalDuration() {
        return player != null ? player.getTotalDuration() : Duration.UNKNOWN;
    }

    public static void seek(Duration duration) {
        if (player != null) player.seek(duration);
    }

    public static void setVolume(double v) {
        if (player != null) player.setVolume(v);
    }
}
