package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Dashboard extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("fxml/Dashboard.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
