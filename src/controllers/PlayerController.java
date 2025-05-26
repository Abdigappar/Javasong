package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import utils.AudioPlayer;

import java.io.File;

public class PlayerController {

    @FXML private Label nowPlayingLabel;
    @FXML private Label timeLabel;
    @FXML private Slider progressSlider;
    @FXML private Slider volumeSlider;

    private MediaPlayer player;

    @FXML
    public void initialize() {
        // 🔁 Когда запускается новая песня через AudioPlayer
        AudioPlayer.playerProperty().addListener((obs, oldPlayer, newPlayer) -> {
            if (newPlayer != null) {
                bindToPlayer(newPlayer);
            }
        });

        // Громкость
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            AudioPlayer.setVolume(newVal.doubleValue());
        });
    }

    private void bindToPlayer(MediaPlayer newPlayer) {
        this.player = newPlayer;

        nowPlayingLabel.setText("🎵 Сейчас играет: " + AudioPlayer.getCurrentTrackName());

        newPlayer.setOnReady(() -> {
            progressSlider.setMax(newPlayer.getTotalDuration().toSeconds());
        });

        newPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
                Platform.runLater(() -> {
                    progressSlider.setValue(newTime.toSeconds());
                    updateTimeLabel(newTime, newPlayer.getTotalDuration());
                });
            }
        });

        progressSlider.setOnMousePressed(e -> newPlayer.pause());
        progressSlider.setOnMouseReleased(e -> {
            newPlayer.seek(Duration.seconds(progressSlider.getValue()));
            newPlayer.play();
        });

        newPlayer.setOnEndOfMedia(() -> {
            // ты можешь тут вызвать AudioPlayer.playNext() если реализуешь очередь
        });
    }

    private void updateTimeLabel(Duration current, Duration total) {
        String time = formatTime(current) + " / " + formatTime(total);
        timeLabel.setText(time);
    }

    private String formatTime(Duration d) {
        int min = (int) d.toMinutes();
        int sec = (int) d.toSeconds() % 60;
        return String.format("%02d:%02d", min, sec);
    }

    @FXML private void togglePlay() {
        AudioPlayer.pauseOrResume();
    }

    @FXML private void stopPlayback() {
        AudioPlayer.stop();
    }

    @FXML private void rewind10() {
        if (player != null)
            player.seek(player.getCurrentTime().subtract(Duration.seconds(10)));
    }

    @FXML private void forward10() {
        if (player != null)
            player.seek(player.getCurrentTime().add(Duration.seconds(10)));
    }
    @FXML private void playPrevious() {
        AudioPlayer.playPrevious();
    }

    @FXML private void playNext() {
        AudioPlayer.playNext();
    }




    @FXML private void addSongManually() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            AudioPlayer.play(file);
        }
    }
}
