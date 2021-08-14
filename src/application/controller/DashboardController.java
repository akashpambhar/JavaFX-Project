package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import application.custom.CustomComponent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox vBox;

	@FXML
	private HBox welcomeBox;

	@FXML
	private Label wlcmMsg;

	@FXML
	private VBox detailsBox;

	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblLastName;

	@FXML
	private Label lblBirthDate;

	@FXML
	private Label lblGender;

	@FXML
	private Button btnLogOut;

	@FXML
	public void initialize() throws Exception {

		setWelcomeMessage();

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
		ResultSet rs = null;
		String sql = "SELECT * FROM `javafx_db`.`names`";

		rs = con.createStatement().executeQuery(sql);

		while (rs.next()) {
			CustomComponent hb = new CustomComponent(rs.getString("username"), rs.getString("firstname"),
					rs.getString("lastname"), rs.getString("birthdate"), rs.getString("gender"));
			vBox.getChildren().add(hb);
		}
		rs.close();
		con.close();
		
		btnLogOut.setOnAction(event -> {
			((Stage) btnLogOut.getScene().getWindow()).close();
		});
	}

	private void setWelcomeMessage() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Stage stage = (Stage) wlcmMsg.getScene().getWindow();
				wlcmMsg.setText("Welcome, " + (String) stage.getUserData() + "!");
				setUserDetails((String) stage.getUserData());
			}
		});
	}

	private void setUserDetails(String usr) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
			ResultSet rs = null;
			String sql = "SELECT * FROM `javafx_db`.`names`";

			rs = con.createStatement().executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("username").equals(usr)) {
					lblFirstName.setText(rs.getString("firstname"));
					lblLastName.setText(rs.getString("lastname"));
					lblBirthDate.setText(rs.getString("birthdate"));
					lblGender.setText(rs.getString("gender"));
					break;
				}
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
