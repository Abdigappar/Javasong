package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalAudioPlayer {
    private static MediaPlayer player;
    private static final List<File> playlist = new ArrayList<>();
    private static int currentIndex = 0;

    public static void play(File file) {
        stop();

        Media media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        player.play();

        if (!playlist.contains(file)) playlist.add(file);
        currentIndex = playlist.indexOf(file);
    }

    public static void stop() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }

    public static MediaPlayer getPlayer() {
        return player;
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

    public static void rewind10() {
        if (player != null) {
            Duration newTime = player.getCurrentTime().subtract(Duration.seconds(10));
            player.seek(newTime.lessThan(Duration.ZERO) ? Duration.ZERO : newTime);
        }
    }

    public static void forward10() {
        if (player != null) {
            Duration newTime = player.getCurrentTime().add(Duration.seconds(10));
            if (newTime.lessThan(player.getTotalDuration()))
                player.seek(newTime);
        }
    }

    public static void setVolume(double volume) {
        if (player != null) {
            player.setVolume(volume);
        }
    }
}
