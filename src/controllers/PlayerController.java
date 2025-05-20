package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    @FXML private Label nowPlayingLabel;
    @FXML private Label timeLabel;
    @FXML private Slider progressSlider;
    @FXML private Slider volumeSlider;

    private static MediaPlayer player;
    private static final List<File> playlist = new ArrayList<>();
    private static int currentIndex = -1;

    @FXML
    public void initialize() {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (player != null) {
                player.setVolume(newVal.doubleValue());
            }
        });

        progressSlider.setOnMouseReleased(event -> {
            if (player != null) {
                player.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        Thread timer = new Thread(() -> {
            while (true) {
                Platform.runLater(this::updateProgressAndTime);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

    public static void playFile(File file) {
        if (file == null) return;
        if (!playlist.contains(file)) {
            playlist.add(file);
        }
        currentIndex = playlist.indexOf(file);
        setupPlayer(file);
    }

    private static void setupPlayer(File file) {
        if (player != null) {
            player.stop();
            player.dispose();
        }

        Media media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        player.play();

        player.setOnEndOfMedia(PlayerController::playNextStatic);
    }

    private static void playNextStatic() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex + 1) % playlist.size();
        setupPlayer(playlist.get(currentIndex));
    }

    private void updateProgressAndTime() {
        if (player != null && player.getStatus() == MediaPlayer.Status.PLAYING) {
            Duration current = player.getCurrentTime();
            Duration total = player.getTotalDuration();

            if (!progressSlider.isValueChanging()) {
                progressSlider.setMax(total.toSeconds());
                progressSlider.setValue(current.toSeconds());
            }

            timeLabel.setText(formatTime(current) + " / " + formatTime(total));
            nowPlayingLabel.setText("🎵 Сейчас играет: " + playlist.get(currentIndex).getName());
        }
    }

    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    private void togglePlay() {
        if (player == null) return;
        if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.pause();
        } else {
            player.play();
        }
    }

    @FXML
    private void stopPlayback() {
        if (player != null) player.stop();
    }

    @FXML
    private void playNext() {
        playNextStatic();
    }

    @FXML
    private void playPrevious() {
        if (playlist.isEmpty()) return;
        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        setupPlayer(playlist.get(currentIndex));
    }

    @FXML
    private void rewind10() {
        if (player != null) {
            Duration newTime = player.getCurrentTime().subtract(Duration.seconds(10));
            player.seek(newTime.lessThan(Duration.ZERO) ? Duration.ZERO : newTime);
        }
    }

    @FXML
    private void forward10() {
        if (player != null) {
            Duration newTime = player.getCurrentTime().add(Duration.seconds(10));
            if (newTime.lessThan(player.getTotalDuration()))
                player.seek(newTime);
        }
    }

    @FXML
    private void addSongManually() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        File selected = chooser.showOpenDialog(null);
        if (selected != null) {
            playFile(selected); // сразу воспроизводим
        }
    }
}
