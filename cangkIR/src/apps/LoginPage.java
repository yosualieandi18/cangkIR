package apps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.MsUser;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Application {
	Scene scene;
	BorderPane bp;
	GridPane gp;
	FlowPane fp1, fp2, fp3, fp4, fp5;
	Label loginLabel, username, password;
	public TextField usernameTf, passTf;
	Button loginButton;
	Hyperlink registerLink = new Hyperlink("Don't have an account yet? Register Here!");;
	Stage primaryStage;
	MsUser user;

	void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		fp1 = new FlowPane();
		fp2 = new FlowPane();
		fp3 = new FlowPane();
		fp4 = new FlowPane();
		fp5 = new FlowPane();

		loginLabel = new Label("Login");
		loginLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");

		username = new Label("Username");
		password = new Label("Password");

		usernameTf = new TextField();
		usernameTf.setPromptText("Input your username here");
		usernameTf.setMinWidth(500);

		passTf = new PasswordField();
		passTf.setPromptText("Input your password here");
		passTf.setMinWidth(500);

		loginButton = new Button("Login");
		loginButton.setMinHeight(30);
		loginButton.setMinWidth(90);
	}

	void layouting() {
		fp1.getChildren().add(loginLabel);
		fp1.setAlignment(Pos.BOTTOM_CENTER);

		fp2.getChildren().add(loginButton);
		fp2.setAlignment(Pos.BOTTOM_CENTER);

		fp3.getChildren().add(registerLink);
		fp3.setAlignment(Pos.TOP_CENTER);

		fp4.getChildren().addAll(username, usernameTf);
		fp4.setAlignment(Pos.CENTER_LEFT);
		fp4.setVgap(10);

		fp5.getChildren().addAll(password, passTf);
		fp5.setAlignment(Pos.CENTER_LEFT);
		fp5.setVgap(10);

		gp.setAlignment(Pos.CENTER);
		gp.add(fp1, 0, 0);
		gp.add(fp4, 0, 1);
		gp.add(fp5, 0, 2);
		gp.add(fp2, 0, 3);
		gp.add(fp3, 0, 5);
		gp.setVgap(20);
		bp.setCenter(gp);

		scene = new Scene(bp, 900, 600);
	}

	void validateLogIn() {
		StringBuilder message = new StringBuilder();
		HomePage home = new HomePage();
		CupManagementPage cm = new CupManagementPage();

		loginButton.setOnAction(e -> {
			MsUser.loggedInUsername = usernameTf.getText();
			user = new MsUser();
			boolean isValid = true;
			try {
				if (usernameTf.getText().isEmpty()) {
					isValid = false;
					message.append("Please fill out your username\n");
				}

				if (passTf.getText().isEmpty()) {
					isValid = false;
					message.append("Please fill out your password\n");
				}

				if (!isValid) {
					ShowErrorAlert(message.toString());
					message.setLength(0);
				} else {
					if (!(isUsernameExists(usernameTf.getText()) && isPassExists(passTf.getText()))) {
						ShowErrorAlert("Username or Password is Wrong!");
					} else {
						if (user.getRole().equals("Admin")) {
							cm.start(primaryStage);
						} else {
							home.start(primaryStage);
						}
						showSuccessAlert("Login Successful");
					}
				}
			} catch (Exception es) {
				es.printStackTrace();
			}
		});
	}

	void RegisterLinkAction() {
		registerLink.setOnAction(e -> {
			try {
				RegisterPage regist = new RegisterPage();
				regist.start(primaryStage);
			} catch (Exception es) {
				es.printStackTrace();
			}
		});
	}

	public boolean isUsernameExists(String username) {
		boolean exists = false;

		try {
			Connection connection = DatabaseConnector.connect();
			String sql = "SELECT * FROM msuser WHERE Username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exists = true;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public boolean isPassExists(String password) {
		boolean exists = false;

		try {
			Connection connection = DatabaseConnector.connect();
			String sql = "SELECT * FROM msuser WHERE UserPassword = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exists = true;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	void showSuccessAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Succes");
		alert.setContentText(message);
		alert.showAndWait();
	}

	void ShowErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Login Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initialize();
		layouting();
		validateLogIn();
		RegisterLinkAction();
		primaryStage.setScene(scene);
		primaryStage.setTitle("cangkIR");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

