package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class SavedController {

    @FXML
    private ListView<String> savedList;

    private final ObservableList<String> savedSongs = FXCollections.observableArrayList(
            "Lana Del Rey — Young and Beautiful",
            "Ed Sheeran — Perfect",
            "Sam Smith — Stay With Me"
    );

    @FXML
    public void initialize() {
        savedList.setItems(savedSongs);

        savedList.setCellFactory(list -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label songLabel = new Label();
            private final Button deleteButton = new Button("❌");

            {
                deleteButton.setOnAction(event -> savedSongs.remove(getItem()));
                content.setSpacing(10);
                HBox.setHgrow(songLabel, Priority.ALWAYS);
                content.getChildren().addAll(songLabel, deleteButton);
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
    private void clearSaved() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить все сохранённые песни?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                savedSongs.clear();
            }
        });
    }

    @FXML
    private void goToHome() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
            StackPane root = (StackPane) savedList.getScene().lookup("#contentPane");
            if (root != null) {
                root.getChildren().setAll(homeView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
