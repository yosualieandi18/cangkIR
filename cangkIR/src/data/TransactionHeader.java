package data;

public class TransactionHeader {

	public static Integer asuransi;
	String TransactionID;
	String UserID;
	String CourierID;
	String TransactionDate;
	Integer Insurance = asuransi;
	
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getCourierID() {
		return CourierID;
	}
	public void setCourierID(String courierID) {
		CourierID = courierID;
	}
	public String getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}
	public Integer getInsurance() {
		return Insurance;
	}
	public void setInsurance(Integer insurance) {
		Insurance = insurance;
	}
}