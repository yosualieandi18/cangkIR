package data;

public class MsCup {

	public String CupID;
	public String CupName;
	public Integer CupPrice;
	public Integer CupQty;
	public Integer Total;

	public MsCup(String cupName, Integer cupPrice) {
		CupName = cupName;
		CupPrice = cupPrice;
	}
	
	public MsCup(String cupName, Integer cupPrice, Integer cupQty, Integer total) {
		CupName = cupName;
		CupPrice = cupPrice;
		CupQty = cupQty;
		Total = total;
	}
	
	public String getCupID() {
		return CupID;
	}

	public void setCupID(String cupID) {
		CupID = cupID;
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
