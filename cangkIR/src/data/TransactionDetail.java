package data;

public class TransactionDetail {

	String TransactionID;
	String CupID;
	Integer Quantity;
	
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
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
}
