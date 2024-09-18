package apps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterPage extends Application {
	Scene scene;
	BorderPane bp;
	GridPane gp1, gp2;
	FlowPane fp1, fp2, fp3, fp4, fp5, fp6;
	Label registerLabel, username, email, password, genderLabel;
	TextField usernameTf, emailTf;
	PasswordField passTf;
	ToggleGroup tg;
	RadioButton male, female;
	Button RegisterButton;
	Hyperlink loginLink;
	Stage primaryStage;

	public void initialize() {

		bp = new BorderPane();
		gp1 = new GridPane();
		gp2 = new GridPane();
		fp1 = new FlowPane();
		fp2 = new FlowPane();
		fp3 = new FlowPane();
		fp4 = new FlowPane();
		fp5 = new FlowPane();
		fp6 = new FlowPane();

		registerLabel = new Label("Register");
		registerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px;");
		username = new Label("Username");
		email = new Label("Email");
		password = new Label("Password");

		usernameTf = new TextField();
		usernameTf.setPromptText("Input your username here");
		usernameTf.setMinWidth(500);
		emailTf = new TextField();
		emailTf.setPromptText("Input your email here");
		emailTf.setMinWidth(500);
		passTf = new PasswordField();
		passTf.setPromptText("Input your password here");
		passTf.setMinWidth(500);

		genderLabel = new Label("Gender");
		genderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		tg = new ToggleGroup();
		male = new RadioButton("Male");
		male.setToggleGroup(tg);
		female = new RadioButton("Female");
		female.setToggleGroup(tg);

		RegisterButton = new Button("Register");
		RegisterButton.setMinHeight(30);
		RegisterButton.setMinWidth(90);

		loginLink = new Hyperlink("Already have an account? Click here to login");

	}

	public void layouting() {
		fp1.getChildren().add(registerLabel);
		fp1.setAlignment(Pos.BOTTOM_CENTER);

		fp2.getChildren().addAll(username, usernameTf, email, emailTf, password, passTf);
		fp2.setAlignment(Pos.CENTER_LEFT);
		fp2.setVgap(10);

		fp3.getChildren().add(genderLabel);
		fp3.setAlignment(Pos.CENTER_LEFT);

		gp1.add(male, 0, 0);
		gp1.add(female, 1, 0);
		gp1.setHgap(10);
		fp4.getChildren().add(gp1);

		fp5.getChildren().add(RegisterButton);
		fp5.setAlignment(Pos.BOTTOM_CENTER);

		fp6.getChildren().add(loginLink);
		fp6.setAlignment(Pos.TOP_CENTER);

		gp2.setAlignment(Pos.CENTER);
		gp2.add(fp1, 0, 0);
		gp2.add(fp2, 0, 1);
		gp2.add(fp3, 0, 2);
		gp2.add(fp4, 0, 3);
		gp2.add(fp5, 0, 4);
		gp2.add(fp6, 0, 5);
		gp2.setVgap(20);
		bp.setCenter(gp2);

		scene = new Scene(bp, 900, 600);
	}

	void validateRegist() {
		StringBuilder message = new StringBuilder();

		RegisterButton.setOnAction(e -> {
			boolean isValid = true;
			if (usernameTf.getText().isEmpty()) {
				isValid = false;
				message.append("Please fill out your username!\n");
			}

			if (isUsernameExists(usernameTf.getText())) {
				isValid = false;
				message.append("Please choose a different username\n");
			}

			if (emailTf.getText().isEmpty()) {
				isValid = false;
				message.append("Please fill out your email!\n");
			} else {
				if (isEmailExists(emailTf.getText())) {
					isValid = false;
					message.append("Please choose a different email\n");
				} else {
					if (!emailTf.getText().endsWith("@gmail.com")) {
						isValid = false;
						message.append("Make sure your email ends with @gmail.com\n");
					}
				}
			}

			if (passTf.getText().isEmpty()) {
				isValid = false;
				message.append("Please fill out your Password!\n");
			} else {
				if (passTf.getText().length() < 8 || passTf.getText().length() > 15) {
					isValid = false;
					message.append("Make sure your password has a length of 8-15 characters\n");
				} else {
					if (!isAlphaNumeric(passTf.getText())) {
						isValid = false;
						message.append("Password must be Aplhanumeric\n");
					}
				}
			}

			if (tg.getSelectedToggle() == null) {
				isValid = false;
				message.append("Please choose your gender!\n");
			}

			if (!isValid) {
				ShowErrorAlert(message.toString());
				message.setLength(0);
			} else {
				String userId = null;
				try {
					userId = generateID();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				String userName = usernameTf.getText() ;
				String emails = emailTf.getText();
				String pass = passTf.getText();
				String gender = ((RadioButton) tg.getSelectedToggle()).getText();
				String role;
				
				if (usernameTf.getText().contains("admin")) {
					role = "Admin";
				} else {
					role = "User";
				}
				
				showSuccessAlert("Account Succesful registered");
				DatabaseConnector.insertDataUser(userId, userName, emails, pass, gender, role);
				LoginPage login = new LoginPage();
				try {
					login.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public String generateID() throws SQLException {
		String nextID = null;
		
		try {
		Connection connection = DatabaseConnector.connect();
		String sql = "SELECT MAX(UserID) FROM msuser";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		if (resultSet.next()) {
			String lastID = resultSet.getString(1);
			
			int num = Integer.parseInt(lastID.substring(2));
			num++;
			nextID = "US" + String.format("%03d", num);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextID;
		
	}

	void LoginLinkAction() {
		loginLink.setOnAction(e -> {
			try {
				LoginPage login = new LoginPage();
				login.start(primaryStage);
			} catch (Exception es) {
				es.printStackTrace();
			}
		});
	}
	
	private static boolean isAlphaNumeric(String str) {
        boolean isNumeric = false;
        boolean isAlpha = false;
        
        for (int i = 0; i < str.length(); i++) {
			if (Character.isAlphabetic(str.charAt(i))) {
				isAlpha = true;
			}
			if (Character.isDigit(str.charAt(i))) {
				isNumeric = true;
			}
		}
        
        return isNumeric && isAlpha;
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

	public boolean isEmailExists(String email) {
		boolean exists = false;

		try {
			Connection connection = DatabaseConnector.connect();
			String sql = "SELECT * FROM msuser WHERE UserEmail = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);

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
		alert.setHeaderText("Register Succesful");
		alert.setContentText(message);
		alert.showAndWait();
	}

	void ShowErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Register Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initialize();
		layouting();
		validateRegist();
		LoginLinkAction();
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
