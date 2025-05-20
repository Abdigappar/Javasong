import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.DBManager;
import utils.SceneSwitcher; // не забудь импорт

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DBManager.connect();

        // Устанавливаем основной Stage для SceneSwitcher
        SceneSwitcher.setPrimaryStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
