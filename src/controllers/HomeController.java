package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HomeController {

    @FXML private ListView<String> topChartList;
    @FXML private ListView<String> recommendationsList;
    @FXML private ListView<String> newReleasesList;

    @FXML
    public void initialize() {
        loadTopCharts();
        loadRecommendations();
        loadNewReleases();
    }

    private void loadTopCharts() {
        ObservableList<String> top = FXCollections.observableArrayList(
                "Weekend — Blinding Lights",
                "Billie Eilish — Happier Than Ever",
                "Drake — One Dance"
        );
        topChartList.setItems(top);
    }

    private void loadRecommendations() {
        ObservableList<String> recs = FXCollections.observableArrayList(
                "Imagine Dragons — Believer",
                "Adele — Easy On Me",
                "Coldplay — Viva La Vida"
        );
        recommendationsList.setItems(recs);
    }

    private void loadNewReleases() {
        ObservableList<String> newTracks = FXCollections.observableArrayList(
                "New Artist — New Track 1",
                "New Artist — New Track 2"
        );
        newReleasesList.setItems(newTracks);
    }
}
