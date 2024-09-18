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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CartPage extends Application {

	BorderPane bp;
	MenuBar mb;
	MenuItem home, cart, logOut;
	Menu menuFile;
	Scene scene;
	Stage primaryStage;
	Label title, deleteItem, courier, courierPrice, totalPrice, cblabel;
	FlowPane fp, fp1, fp2, fp3;
	GridPane gp, gp1;
	Button del, checkout;
	CheckBox cb;
	HBox hbx;
	ComboBox<String> box;
	
	public TableView<MsCart> cartTable;
	TableColumn<MsCart, String> cupNameCol;
	TableColumn<MsCart, Integer> cupPriceCol;
	TableColumn<MsCart, Integer> qtyCol;
	TableColumn<MsCart, Integer> totalCol;
	ObservableList<MsCart> dataCart;

	MsUser user = new MsUser();

	void initialize() {
		bp = new BorderPane();
		fp = new FlowPane();
		fp1 = new FlowPane();
		fp2 = new FlowPane();
		fp3 = new FlowPane();
		gp = new GridPane();
		gp1 = new GridPane();
		mb = new MenuBar();
		menuFile = new Menu("Menu");
		home = new MenuItem("Home");
		cart = new MenuItem("Cart");
		logOut = new MenuItem("Log Out");

		title = new Label("vncnt's Cart");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		cupNameCol = new TableColumn<MsCart, String>("Cup Name");
		cupNameCol.setPrefWidth(140);
		cupNameCol.setCellValueFactory(new PropertyValueFactory<>("CupName"));

		cupPriceCol = new TableColumn<MsCart, Integer>("Cup Price");
		cupPriceCol.setPrefWidth(80);
		cupPriceCol.setCellValueFactory(new PropertyValueFactory<>("CupPrice"));

		qtyCol = new TableColumn<MsCart, Integer>("Quantity");
		qtyCol.setPrefWidth(80);
		qtyCol.setCellValueFactory(new PropertyValueFactory<>("CupQty"));

		totalCol = new TableColumn<MsCart, Integer>("Total");
		totalCol.setPrefWidth(100);
		totalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));

		cartTable = new TableView<>();
		cartTable.getColumns().addAll(cupNameCol, cupPriceCol, qtyCol, totalCol);
		cartTable.setPrefSize(300, 400);

		dataCart = FXCollections.observableArrayList();

		deleteItem = new Label("Delete Item");
		deleteItem.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		del = new Button("Delete Item");
		del.setMinSize(120, 35);

		courier = new Label("Courier");
		courier.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		box = new ComboBox<>();
		box.getItems().addAll("IRX", "JNA", "TAKA", "LoinParcel", "JINJA");
		box.setPromptText("Pick a courier");

		courierPrice = new Label("Courier Price:");
		courierPrice.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		hbx = new HBox();
		hbx.setSpacing(10);
		cb = new CheckBox("Use Delivery Insurance");

		totalPrice = new Label("Total Price:");
		totalPrice.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
		checkout = new Button("Checkout");
		checkout.setMinSize(120, 40);

		menuFile.getItems().addAll(home, cart, logOut);
		mb.getMenus().add(menuFile);
		scene = new Scene(bp, 900, 600);
	}

	void layout() {
		MsUser user = new MsUser();

		bp.setTop(mb);
		fp.getChildren().add(title);
		title.setText(user.getUsername() + "'s Cart");
		fp.setAlignment(Pos.CENTER_LEFT);

		gp.add(fp, 0, 0);
		gp.add(cartTable, 0, 1);
		gp.add(gp1, 1, 1);
		gp.setHgap(20);

		gp1.add(deleteItem, 0, 0);
		gp1.add(del, 0, 1);
		gp1.add(courier, 0, 2);
		gp1.add(box, 0, 3);
		gp1.add(courierPrice, 0, 4);
		gp1.add(cb, 0, 5);
		gp1.add(totalPrice, 0, 6);
		gp1.add(checkout, 0, 7);
		gp1.setVgap(15);
		gp.setPadding(new Insets(0, 0, 30, 10));

		gp1.setAlignment(Pos.CENTER_RIGHT);
		bp.setBottom(gp);

	}

	public void getCartData() {
		try {
			Connection connection = DatabaseConnector.connect();
			String query = "SELECT cart.CupID, CupName, CupPrice, Quantity FROM mscup"
					+ " JOIN cart ON mscup.CupID = cart.CupID JOIN msuser ON cart.UserID = msuser.UserID WHERE cart.UserID = '"
					+ user.getUserId() + "'" + " GROUP BY CupName, CupPrice";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String cupId = resultSet.getString("cart.CupID");
				String cupName = resultSet.getString("CupName");
				Integer cupPrice = resultSet.getInt("CupPrice");
				Integer qty = resultSet.getInt("Quantity");
				Integer totalPrice = cupPrice * qty;

				MsCart cup = new MsCart(cupId, cupName, cupPrice, qty, totalPrice);
				dataCart.add(cup);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showCartData() {
		dataCart.clear();
		getCartData();
		cartTable.setItems(dataCart);
		MsCart.dataCart = dataCart;
		MsCart.cartTable = cartTable;
		
		Integer bb = calculateTotalPrice();
		if (dataCart.isEmpty()) {
			totalPrice.setText("Total Price: " + 0);
		} else {
			totalPrice.setText("Total Price: " + bb);
		}
	}

	public int calculateTotalPrice() {
		int total = 0;
		for (MsCart cart : cartTable.getItems()) {
			total += cart.getTotal();
		}
		return total;
	}

	void ButtonAction() {
		StringBuilder message = new StringBuilder();
		MsUser user = new MsUser();
		del.setOnAction(e -> {
			MsCart selectedCart = cartTable.getSelectionModel().getSelectedItem();
			if (selectedCart != null) {
				DatabaseConnector dc = new DatabaseConnector();
				String cupName = cartTable.getSelectionModel().getSelectedItem().getCupName();
				String cupID = dc.getCupID(cupName);
				dc.deleteData(cupID);
				showSuccessAlert("Cart succesfully deleted");

				cartTable.getItems().remove(selectedCart);
				showCartData();
				cartTable.refresh();
			} else {
				ShowErrorAlert("Deletion Error", "Please select the item you want to delete");
			}
		});

		checkout.setOnAction(e -> {
			String selectedItem = box.getSelectionModel().getSelectedItem();
			if (dataCart.isEmpty()) {
				ShowErrorAlert("Checkout Error", "The cart is Empty");
			} else {
				if (selectedItem != null && !selectedItem.isEmpty()) {
					if (cb.isSelected()) {
						Integer insurance = 1;
						TransactionHeader.asuransi = insurance;
					} else {
						Integer insurance = 0;
						TransactionHeader.asuransi = insurance;
					}
					
					ConfirmationPage cp = new ConfirmationPage();
					try {
						cp.start(primaryStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					ShowErrorAlert("Checkout Error", "You must pick a courier!");
				}
			}

		});

		box.setOnAction(e -> {
			String priceName = box.getSelectionModel().getSelectedItem();
			MsCourier.selectedCourier = priceName;
			String a = DatabaseConnector.getCourierPrice(priceName);
			Integer b = calculateTotalPrice();
			Integer c = calculateTotalPrice() + Integer.parseInt(a);
			Integer d = calculateTotalPrice() + Integer.parseInt(a) + 2000;
			if (priceName != null) {
				courierPrice.setText("Courier Price: " + a);
				totalPrice.setText("Total Price: " + c);
			} else {
				totalPrice.setText("Total Price: " + b);
			}

			cb.setOnAction(es -> {
				if (cb.isSelected()) {
					totalPrice.setText("Total Price: " + d);
				} else {
					totalPrice.setText("Total Price: " + c);
				}
			});
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

	void showSuccessAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Deletion Information");
		alert.setContentText(message);
		alert.showAndWait();
	}

	void ShowErrorAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
		layout();
		LoadMenu();
		ButtonAction();
		showCartData();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}