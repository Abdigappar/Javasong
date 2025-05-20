package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class AudioPlayer {

    private static MediaPlayer player;
    private static String currentTrack = "";

    public static void play(File file) {
        stop();
        if (file == null || !file.exists()) return;

        Media media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        player.play();
        currentTrack = file.getName();
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

    public static String getCurrentTrack() {
        return currentTrack;
    }

    public static void seek(Duration duration) {
        if (player != null) player.seek(duration);
    }

    public static Duration getCurrentTime() {
        return player != null ? player.getCurrentTime() : Duration.ZERO;
    }

    public static Duration getTotalDuration() {
        return player != null ? player.getTotalDuration() : Duration.ZERO;
    }

    public static void setVolume(double volume) {
        if (player != null) {
            player.setVolume(volume);
        }
    }

    public static boolean isPlaying() {
        return player != null && player.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public static void setOnEndOfMedia(Runnable action) {
        if (player != null) {
            player.setOnEndOfMedia(action);
        }
    }

    public static void addTimeListener(Runnable onTimeUpdate) {
        if (player != null) {
            player.currentTimeProperty().addListener((obs, oldTime, newTime) -> onTimeUpdate.run());
        }
    }

    public static void setOnReady(Runnable onReady) {
        if (player != null) {
            player.setOnReady(onReady);
        }
    }
}
