package data;

import apps.DatabaseConnector;

public class MsCourier {
	public static String selectedCourier;
	String CourierID = DatabaseConnector.getCourierID(selectedCourier);
	String CourierName;
	Integer CourierPrice;
	
	public String getCourierID() {
		return CourierID;
	}
	public void setCourierID(String courierID) {
		CourierID = courierID;
	}
	public String getCourierName() {
		return CourierName;
	}
	public void setCourierName(String courierName) {
		CourierName = courierName;
	}
	public Integer getCourierPrice() {
		return CourierPrice;
	}
	public void setCourierPrice(Integer courierPrice) {
		CourierPrice = courierPrice;
	}
	
	

}
