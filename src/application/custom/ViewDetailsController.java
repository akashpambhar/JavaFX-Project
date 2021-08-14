package application.custom;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewDetailsController {

	@FXML
	private Pane dlgviewDetails;

	@FXML
	private Label viewUserName;

	@FXML
	private Label viewFirstName;

	@FXML
	private Label viewLastName;

	@FXML
	private Label viewGender;

	@FXML
	private Label viewBirthDate;
	
	@FXML
    private Button btnClose;

	private Stage currentStage;

	@FXML
	public void initialize() throws Exception {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				currentStage = (Stage) dlgviewDetails.getScene().getWindow();
				CustomComponent sharedData = (CustomComponent) currentStage.getUserData();
				viewUserName.setText(sharedData.getUserName());
				viewFirstName.setText(sharedData.getFirstName());
				viewLastName.setText(sharedData.getLastName());
				viewBirthDate.setText(sharedData.getBirthDate());
				viewGender.setText(sharedData.getGender());
			}
		});
		
		btnClose.setOnAction(event -> {
			currentStage.close();
		});
	}

}
