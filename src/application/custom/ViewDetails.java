package application.custom;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ViewDetails extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("ViewDetailsModal.fxml"));
		FadeTransition ft = new FadeTransition(Duration.seconds(0.3));
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ScaleTransition st = new ScaleTransition(Duration.seconds(0.1));
		st.setFromX(0);
		st.setFromY(0);
		st.setToX(1);
		st.setToY(1);
		ParallelTransition pt = new ParallelTransition(root, ft, st);
		pt.play();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
