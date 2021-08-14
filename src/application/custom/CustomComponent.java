package application.custom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomComponent extends HBox {

	@FXML
	private Label lblUsername;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnView;

	public String userName, firstName, lastName, birthDate, gender;

	public CustomComponent(String userName, String firstName, String lastName, String birthDate, String gender) {
		super();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Compo.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;

		this.lblUsername.setText(userName);
	}

	@FXML
	public void initialize() throws Exception {
		btnView.setOnAction(event -> {
			viewDetails();
		});
		btnDelete.setOnAction(event -> {
			deleteDetails();
		});
	}

	private void viewDetails() {
		Stage newStage = new Stage();
		newStage.setUserData(this);
		try {
			new ViewDetails().start(newStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteDetails() {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
			String sql = "DELETE FROM `javafx_db`.`names` WHERE username=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, lblUsername.getText());
			int res = ps.executeUpdate();

			if (res > 0) {
				((VBox) this.getParent()).getChildren().remove(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
