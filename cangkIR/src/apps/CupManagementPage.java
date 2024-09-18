package apps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.MsCup;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class CupManagementPage extends Application {

	BorderPane bp;
	MenuBar mb;
	MenuItem logOut, cup;
	Menu menu;
	Scene scene;
	Stage primaryStage;
	Label title, cups, price;
	FlowPane fp, fp1, fp2, fp3;
	GridPane gp, gp1;
	Button btnadd, btnupdate, btnremove;
	TextField cuptf, pricetf;
	TableView<MsCup> cupTable;
	TableColumn<MsCup, String> cupName;
	TableColumn<MsCup, Integer> cupPrice;
	ObservableList<MsCup> dataCup;
	TextFormatter<Integer> priceFormat;

	void initialize() {
		bp = new BorderPane();
		fp = new FlowPane();
		fp1 = new FlowPane();
		fp2 = new FlowPane();
		fp3 = new FlowPane();
		gp = new GridPane();
		gp1 = new GridPane();
		mb = new MenuBar();
		menu = new Menu("Menu");
		logOut = new MenuItem("Log Out");
		cup = new MenuItem("Cup Management");

		title = new Label("Cup Management");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		cups = new Label("Cup Name");
		cuptf = new TextField();
		cuptf.setMinWidth(450);
		cuptf.setPromptText("Input cup name here");

		price = new Label("Cup Price");
		pricetf = new TextField();
		pricetf.setMinWidth(450);
		pricetf.setPromptText("Input cup price here");
		
		priceFormat = new TextFormatter<>(new IntegerStringConverter());
		pricetf.setTextFormatter(priceFormat);

		cupName = new TableColumn<MsCup, String>("Cup Name");
		cupName.setPrefWidth(200);
		cupName.setCellValueFactory(new PropertyValueFactory<>("cupName"));

		cupPrice = new TableColumn<MsCup, Integer>("Cup Price");
		cupPrice.setPrefWidth(200);
		cupPrice.setCellValueFactory(new PropertyValueFactory<>("cupPrice"));

		cupTable = new TableView<>();
		cupTable.getColumns().addAll(cupName, cupPrice);
		cupTable.setPrefSize(300, 400);

		btnadd = new Button("Add Cup");
		btnadd.setMinSize(150, 45);

		btnupdate = new Button("Update Price");
		btnupdate.setMinSize(150, 45);

		btnremove = new Button("Remove Cup");
		btnremove.setMinSize(150, 45);

		menu.getItems().addAll(cup, logOut);
		mb.getMenus().add(menu);

		dataCup = FXCollections.observableArrayList();

		scene = new Scene(bp, 900, 600);
	}

	void layout() {
		bp.setTop(mb);
		fp.getChildren().add(title);
		fp.setAlignment(Pos.CENTER_LEFT);

		fp1.getChildren().addAll(cups, cuptf);
		fp1.setVgap(15);
		fp2.getChildren().addAll(price, pricetf);
		fp2.setVgap(15);

		gp.add(fp, 0, 0);
		gp.add(cupTable, 0, 1);
		gp.add(gp1, 1, 1);
		gp.setHgap(20);
		gp.setVgap(15);

		gp1.add(fp1, 0, 0);
		gp1.add(fp2, 0, 1);
		gp1.add(btnadd, 0, 2);
		gp1.add(btnupdate, 0, 3);
		gp1.add(btnremove, 0, 4);
		gp1.setVgap(20);

		gp.setPadding(new Insets(0, 0, 30, 10));
		gp1.setAlignment(Pos.CENTER);
		bp.setBottom(gp);
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
		dataCup.clear();
		getCupData();
		cupTable.setItems(dataCup);
	}

	void LoadMenu() {
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

	public void ButtonAction() {
		StringBuilder message = new StringBuilder();
		
		cupTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
			cuptf.setText(newValue == null ? "" : newValue.getCupName());
            pricetf.setText(newValue == null ? "" : Integer.toString(newValue.getCupPrice()));
		});
		
		btnadd.setOnAction(e -> {
			boolean isValid = true;
			if (cuptf.getText().isEmpty()) {
				isValid = false;
				message.append("Please fill out the cup name\n");
			} else {
				if (isCupExists(cuptf.getText())) {
					isValid = false;
					message.append("Cup Already Exists\n");
				}
			}

			if (pricetf.getText().isEmpty()) {
				isValid = false;
				message.append("Please fill out the cup price\n");
			} else {
				if (Integer.parseInt(pricetf.getText()) < 5000 || Integer.parseInt(pricetf.getText()) > 1000000) {
					isValid = false;
					message.append("Cup price must be 5000 - 1000000\n");
				}
			}
			
			if (!isValid) {
				ShowErrorAlert(message.toString());
				message.setLength(0);
			} else {
				String CupId = null;
				try {
					CupId = generateCupID();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String CupName = cuptf.getText();
				Integer CupPrice = Integer.parseInt(pricetf.getText());
				
				showSuccessAlert("Cup Succesfully Added");
				DatabaseConnector.InsertNewCup(CupId, CupName, CupPrice);
				showCupData();
				cupTable.refresh();
				
				cuptf.setText("");
				pricetf.setText("");
			}
		});
		
		btnupdate.setOnAction(e -> {
			MsCup selectedCup = cupTable.getSelectionModel().getSelectedItem();
			if (selectedCup != null) {
				DatabaseConnector dc = new DatabaseConnector();
				String selectedName = cupTable.getSelectionModel().getSelectedItem().getCupName();
				Integer cupPrice = Integer.parseInt(pricetf.getText());
				String cupId = dc.getCupID(selectedName);
				
				dc.updateCupData(cupPrice, cupId);
				showCupData();
				cupTable.refresh();
				showSuccessAlert("Cup succesfully updated!");
				
				cuptf.setText("");
				pricetf.setText("");
				
			} else {
				ShowErrorAlert("Please select a cup from the table to be updated");
			}
		});
		
		btnremove.setOnAction(e -> {
			MsCup selectedCup = cupTable.getSelectionModel().getSelectedItem();
			if (selectedCup != null) {
				DatabaseConnector dc = new DatabaseConnector();
				String selectedName = cupTable.getSelectionModel().getSelectedItem().getCupName();
				String cupId = dc.getCupID(selectedName);
				
				dc.deleteCupData(cupId);
				showSuccessAlert("Cup succesfully deleted!");
				
				cupTable.getItems().remove(selectedCup);
				cupTable.refresh();
				
			} else {
				ShowErrorAlert("Please select cup you want to delete");
			}
		});
	}
	
	public boolean isCupExists(String cupName) {
		boolean exists = false;
		try {
			Connection connection = DatabaseConnector.connect();
			String sql = "SELECT * FROM mscup WHERE CupName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cupName);

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
	
	public String generateCupID() throws SQLException {
		String nextID = null;
		
		try {
		Connection connection = DatabaseConnector.connect();
		String sql = "SELECT MAX(CupID) FROM mscup";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		if (resultSet.next()) {
			String lastID = resultSet.getString(1);
			
			int num = Integer.parseInt(lastID.substring(2));
			num++;
			nextID = "CU" + String.format("%03d", num);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextID;
		
	}
	

	void showSuccessAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Cup Management");
		alert.setContentText(message);
		alert.showAndWait();
	}

	void ShowErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Cup Management");
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
		showCupData();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}