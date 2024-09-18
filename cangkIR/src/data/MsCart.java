package data;

import apps.DatabaseConnector;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class MsCart {
	public static TableView<MsCart> cartTable;
	public static ObservableList<MsCart> dataCart;
	
	public String UserID;
	public Integer Quantity;
	public String CupName;
	public String CupID;
	public Integer CupPrice;
	public Integer CupQty;
	public Integer Total;
	
	public String getId() {
		return DatabaseConnector.getCupID(getCupName());
	}
	
	public MsCart(String userID, String cupID, Integer quantity) {
		UserID = userID;
		CupID = cupID;
		Quantity = quantity;
	}
	
	public MsCart(String cupID, String cupName, Integer cupPrice, Integer cupQty, Integer total) {
		CupID = cupID;
		CupName = cupName;
		CupPrice = cupPrice;
		CupQty = cupQty;
		Total = total;
	}
	
	public static TableView<MsCart> getCartTable() {
		return cartTable;
	}

	public static void setCartTable(TableView<MsCart> cartTable) {
		MsCart.cartTable = cartTable;
	}
	
	public static ObservableList<MsCart> getDataCart() {
		return dataCart;
	}

	public static void setDataCart(ObservableList<MsCart> dataCart) {
		MsCart.dataCart = dataCart;
	}

	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getCupID() {
		return CupID;
	}
	public void setCupID(String cupID) {
		CupID = cupID;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public String getCupName() {
		return CupName;
	}

	public void setCupName(String cupName) {
		CupName = cupName;
	}

	public Integer getCupPrice() {
		return CupPrice;
	}

	public void setCupPrice(Integer cupPrice) {
		CupPrice = cupPrice;
	}

	public Integer getCupQty() {
		return CupQty;
	}

	public void setCupQty(Integer cupQty) {
		CupQty = cupQty;
	}

	public Integer getTotal() {
		return Total;
	}

	public void setTotal(Integer total) {
		Total = total;
	}
	
	
	
	
}
