package apps;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.MsCart;
import data.MsCourier;
import data.MsUser;
import data.TransactionHeader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;

public class ConfirmationPage extends Application {

	Stage primaryStage;
	Scene scene;
	Label confirmLb;
	Button yesBtn, noBtn;
	BorderPane bp;
	VBox vbox;
	HBox hbox;
	Window wp;
	TableView<MsCart> cartTable;
	ObservableList<MsCart> dataCart;

	public void init() {
		bp = new BorderPane();
		vbox = new VBox();
		hbox = new HBox();
		wp = new Window("Checkout confirmation");

		yesBtn = new Button("Yes");
		yesBtn.setPrefSize(100, 30);
		noBtn = new Button("No");
		noBtn.setPrefSize(100, 30);

		confirmLb = new Label("Are you sure you want to purchase?i");
		confirmLb.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		cartTable = new TableView<>();
		dataCart = FXCollections.observableArrayList();

		scene = new Scene(bp, 900, 600);
	}

	void layout() {
		hbox.getChildren().addAll(yesBtn, noBtn);
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(confirmLb, hbox);
		vbox.setSpacing(15);
		vbox.setAlignment(Pos.CENTER);

		wp.setContentPane(vbox);

		bp.setCenter(wp);
	}

	void ButtonAction() {
		yesBtn.setOnAction(e -> {
			DatabaseConnector dc = new DatabaseConnector();
			MsUser user = new MsUser();
			MsCourier mcou = new MsCourier();
			TransactionHeader th = new TransactionHeader();

			String trId = null;
			String userId = user.getUserId();
			String couId = mcou.getCourierID();
			String curDate = java.time.LocalDate.now().toString();
			Integer insurance = th.getInsurance();
			cartTable = MsCart.getCartTable();
			dataCart = MsCart.getDataCart();
			cartTable.setItems(dataCart);

			try {
				trId = generateID();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			DatabaseConnector.InsertTrHeader(trId, userId, couId, curDate, insurance);

			for (MsCart cart : cartTable.getItems()) {
				String a = cart.getCupID();
				Integer b = cart.getCupQty();
				DatabaseConnector.InsertTrDetail(trId, a, b);
			}

			dc.EmptyCartData(userId);
			CartPage cp = new CartPage();
			cp.start(primaryStage);
			showSuccessAlert("Checkout Succesful");

		});

		noBtn.setOnAction(e -> {
			CartPage cp = new CartPage();
			cp.start(primaryStage);
		});
	}

	public String generateID() throws SQLException {
		String nextID = null;
		
		try {
		Connection connection = DatabaseConnector.connect();
		String sql = "SELECT MAX(TransactionID) FROM transactionheader";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		if (resultSet.next()) {
			String lastID = resultSet.getString(1);
			
			int num = Integer.parseInt(lastID.substring(2));
			num++;
			nextID = "TR" + String.format("%03d", num);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextID;
		
	}

	void showSuccessAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Checkout Information");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		init();
		layout();
		ButtonAction();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}