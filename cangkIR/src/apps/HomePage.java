package apps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.MsCup;
import data.MsUser;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {

	String cupId;
	BorderPane bp;
	GridPane gp1, gp2;
	FlowPane fp1;
	VBox vbox;
	Menu menuFile;
	MenuBar menuBar;
	MenuItem home, cart, logOut;
	Stage primaryStage;
	Scene scene;
	Label cupTitle, cupNameLb, PriceLb;
	Button AddButton;
	Spinner<Integer> qty;
	SpinnerValueFactory<Integer> valueQty;
	TableView<MsCup> cupTable;
	TableColumn<MsCup, String> cupName;
	TableColumn<MsCup, Integer> cupPrice;
	ObservableList<MsCup> dataCup;

	void initialize() {
		bp = new BorderPane();
		gp1 = new GridPane();
		gp2 = new GridPane();
		fp1 = new FlowPane();
		vbox = new VBox();

		home = new MenuItem("Home");
		cart = new MenuItem("Cart");
		logOut = new MenuItem("Log Out");
		menuFile = new Menu("Menu");
		menuFile.getItems().addAll(home, cart, logOut);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);

		cupTitle = new Label("Cup List");
		cupTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		cupNameLb = new Label("Cup Name");
		cupNameLb.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		PriceLb = new Label("Price");
		PriceLb.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		qty = new Spinner<>();
		valueQty = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
		qty.setValueFactory(valueQty);

		AddButton = new Button("Add to Cart");
		AddButton.setMinSize(90, 30);

		cupName = new TableColumn<MsCup, String>("Cup Name");
		cupName.setPrefWidth(230);
		cupName.setCellValueFactory(new PropertyValueFactory<>("cupName"));

		cupPrice = new TableColumn<MsCup, Integer>("Cup Price");
		cupPrice.setPrefWidth(170);
		cupPrice.setCellValueFactory(new PropertyValueFactory<>("cupPrice"));

		cupTable = new TableView<>();
		cupTable.getColumns().addAll(cupName, cupPrice);
		cupTable.setPrefSize(400, 400);

		dataCup = FXCollections.observableArrayList();

		scene = new Scene(bp, 900, 600);
	}

	void layouting() {
		bp.setTop(menuBar);

		fp1.getChildren().add(AddButton);

		gp1.add(cupNameLb, 0, 0);
		gp1.add(qty, 0, 1);
		gp1.add(PriceLb, 0, 2);
		gp1.add(AddButton, 0, 3);
		gp1.setVgap(20);
		gp1.setAlignment(Pos.CENTER);

		gp2.add(cupTable, 0, 0);
		gp2.add(gp1, 1, 0);
		gp2.setHgap(10);

		vbox.getChildren().addAll(cupTitle, gp2);
		vbox.setSpacing(15);
		vbox.setPadding(new Insets(0, 0, 10, 10));
		vbox.setAlignment(Pos.BOTTOM_LEFT);

		bp.setBottom(vbox);
	}

	private void getCupData() {
		try {
			Connection connection = DatabaseConnector.connect();
			String query = "SELECT CupName, CupPrice FROM mscup";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String cupName = resultSet.getString("CupName");
				Integer cupPrice = resultSet.getInt("CupPrice");

				MsCup cup = new MsCup(cupName, cupPrice);
				dataCup.add(cup);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showCupData() {
		getCupData();
		cupTable.setItems(dataCup);
	}

	void setDisplay() {
		cupTable.setOnMouseClicked(event -> {
			String cupName = cupTable.getSelectionModel().getSelectedItem().getCupName();
			int cupPrice = cupTable.getSelectionModel().getSelectedItem().getCupPrice();
			int quantity = Integer.parseInt(qty.getValue().toString());
			int totalPrice = cupPrice * quantity;

			cupNameLb.setText(cupName);
			PriceLb.setText("Total Price: " + String.valueOf(totalPrice));
		});
	}

	void LoadMenu() {
		home.setOnAction(e -> {
			HomePage homePage = new HomePage();
			try {
				homePage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		cart.setOnAction(e -> {
			CartPage cartPage = new CartPage();
			try {
				cartPage.start(primaryStage);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		logOut.setOnAction(e -> {
			LoginPage loginPage = new LoginPage();
			try {
				loginPage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	void setAddAction() {
		MsUser user = new MsUser();
		StringBuilder message = new StringBuilder();
		String id = user.getUserId();
		
		AddButton.setOnAction(e -> {
			int quantity = qty.getValue();
			boolean isValid = true;
			if (cupTable.getSelectionModel().getSelectedItem() == null) {
				isValid = false;
				message.append("Please select a cup to be added\n");
			}
			
			if (!isValid) {
				ShowErrorAlert(message.toString());
				message.setLength(0);
			} else {
				String cupName = cupTable.getSelectionModel().getSelectedItem().getCupName();
				String cupId = DatabaseConnector.getCupID(cupName);
				DatabaseConnector.InsertCart(id, cupId, quantity, quantity);
				showSuccessAlert("Item successfully added to Cart!");
			}
			
			qty.getValueFactory().setValue(1);
		});
	}
	
	public boolean isCupIdExists(String cupName) {
		boolean exists = false;
		try {
			Connection connection = DatabaseConnector.connect();
			String sql = "SELECT CupID FROM mscup WHERE CupName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cupName);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				exists = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return exists;
    }

	void showSuccessAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Cart Info");
		alert.setContentText(message);
		alert.showAndWait();
	}

	void ShowErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Cart Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initialize();
		layouting();
		showCupData();
		setDisplay();
		LoadMenu();		
		setAddAction();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
