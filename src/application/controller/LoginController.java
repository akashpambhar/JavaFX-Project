package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.Dashboard;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

	@FXML
	private BorderPane login;

	@FXML
	private VBox loginForm;

	@FXML
	private Label msgWrongCredentials;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnExit;

	@FXML
	private Label lnkToRegister;

	@FXML
	private VBox registerForm;

	@FXML
	private Label msgFieldsEmpty;

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtRegUserName;

	@FXML
	private Label msgUserName;

	@FXML
	private PasswordField txtRegPassword;

	@FXML
	private Label msgPassword;

	@FXML
	private DatePicker birthDate;

	@FXML
	private RadioButton isMale;

	@FXML
	private ToggleGroup gender;

	@FXML
	private RadioButton isFemale;

	@FXML
	private Button btnRegister;

	@FXML
	private Label lnkToLogin;

	@FXML
	private void initialize() {
		registerForm.setVisible(false);
		lnkToRegister.setOnMouseClicked(event -> openRegisterForm());
		lnkToLogin.setOnMouseClicked(event -> openLoginForm());
		txtFirstName.focusedProperty().addListener(listener -> {
			capitalizeFirst(txtFirstName);
		});
		txtLastName.focusedProperty().addListener(listener -> {
			capitalizeFirst(txtLastName);
		});
		txtRegUserName.focusedProperty().addListener(listener -> {
			msgUserName.setVisible(!validateUserName());
		});
		txtRegPassword.textProperty().addListener(listener -> {
			validatePassword();
		});
		btnExit.setOnAction(event -> {
			((Stage) login.getScene().getWindow()).close();
		});
	}

	@FXML
	public void register(ActionEvent event) {

		RadioButton rb = (RadioButton) gender.getSelectedToggle();
		if (msgPassword.getText().equals("Cannot use '!' in password")) {
			autoHide(msgFieldsEmpty, "Password cannot contain '!'", Duration.seconds(5));
		} else if (!txtFirstName.getText().isEmpty() && !txtLastName.getText().isEmpty()
				&& !txtRegUserName.getText().isEmpty() && !txtRegPassword.getText().isEmpty()
				&& !birthDate.getValue().equals(null) && rb != null && validateUserName() && validatePassword()) {
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
				String sql = "INSERT INTO `javafx_db`.`names` (username,password,firstname,lastname,birthdate,gender) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, txtRegUserName.getText());
				ps.setString(2, txtRegPassword.getText());
				ps.setString(3, txtFirstName.getText());
				ps.setString(4, txtLastName.getText());
				ps.setString(5, birthDate.getValue().toString());
				ps.setString(6, rb.getText());
				ps.executeUpdate();

				ps.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			txtFirstName.clear();
			txtLastName.clear();
			txtRegUserName.clear();
			msgUserName.setText("");
			txtRegPassword.setText("");
			msgPassword.setText("");
			birthDate.setValue(null);
			isMale.setSelected(false);
			isFemale.setSelected(false);

			openLoginForm();
		} else {
			autoHide(msgFieldsEmpty, "Fill all the fields", Duration.seconds(5));
		}

	}

	private boolean validateUserName() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
			ResultSet rs = null;
			String sql = "SELECT * FROM `javafx_db`.`names`";

			rs = con.createStatement().executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("username").equals(txtRegUserName.getText())) {
					rs.close();
					txtRegUserName.requestFocus();
					return false;
				}
			}
			rs.close();
			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@FXML
	public void verifyLoginCredentials(ActionEvent event) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_db", "root", "password");
			ResultSet rs = null;
			String sql = "SELECT * FROM `javafx_db`.`names`";

			rs = con.createStatement().executeQuery(sql);

			while (rs.next()) {
				if (rs.getString("username").equals(txtUserName.getText())
						&& rs.getString("password").equals(txtPassword.getText())) {
					Stage stage = (Stage) login.getScene().getWindow();
					stage.setUserData(txtUserName.getText());
					new Dashboard().start(stage);
					break;
				} else {
					autoHide(msgWrongCredentials, "Username/Password is wrong", Duration.seconds(3));
				}
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validatePassword() {
		String pwd = txtRegPassword.getText();
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])([^!])(?=\\S+$).{8,20}$";
		if (pwd.contains("!")) {
			msgPassword.setText("Cannot use '!' in password");
			return false;
		} else {
			if (pwd.matches(regex)) {
				msgPassword.setText("Strong Password");
			} else {
				msgPassword.setText("Weak Password");
			}
			return true;
		}
	}

	private void autoHide(Label label, String text, Duration duration) {
		label.setText(text);
		label.setVisible(true);
		PauseTransition pause = new PauseTransition(duration);
		pause.setOnFinished(e -> {
			label.setVisible(false);
		});
		pause.play();
	}

	private void capitalizeFirst(TextField field) {
		if (!field.getText().isEmpty()) {
			field.setText(field.getText().substring(0, 1).toUpperCase() + field.getText().substring(1).toLowerCase());
		}
	}

	private void openRegisterForm() {
		registerForm.setVisible(true);

		TranslateTransition registerUp = new TranslateTransition(Duration.seconds(1), registerForm);
		registerUp.setToY(0);
		registerUp.play();

		TranslateTransition loginUp = new TranslateTransition(Duration.seconds(1), loginForm);
		loginUp.setToY(-1000);
		loginUp.play();
		loginUp.setOnFinished(event -> {
			loginForm.setVisible(false);
		});
	}

	private void openLoginForm() {
		loginForm.setVisible(true);

		TranslateTransition loginDown = new TranslateTransition(Duration.seconds(1), loginForm);
		loginDown.setToY(0);
		loginDown.play();

		TranslateTransition registerDown = new TranslateTransition(Duration.seconds(1), registerForm);
		registerDown.setToY(1000);
		registerDown.play();
		registerDown.setOnFinished(event -> {
			registerForm.setVisible(false);
		});
	}
}
