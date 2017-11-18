package tracksys.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;


/*@NamedNativeQuery(name="ItemReportMapping",
				query="select (select name from item_dtl where id = tritm.itemid) as itemname, (select count(itemid) from tritm where tr.transtype = 1) as Purchases, (select count(itemid) from tritm where tr.transtype = 2) as Sales, (select count(itemid) from tritm where tr.transtype = 3) as Purchase_Return, (select count(itemid) from tritm where tr.transtype = 1) as Sales_Return from transactions tr, transaction_items tritm group by tr.transtype, tritm.itemid", 
				resultSetMapping="ItemReportMapping")

@SqlResultSetMapping(
        name = "ItemReportMapping",
        classes = @ConstructorResult(
                targetClass = ItemReport.class,
                columns = {
                    @ColumnResult(name = "itemname"),
                    @ColumnResult(name = "Purchases"),
                    @ColumnResult(name = "Sales"),
					@ColumnResult(name = "Purchase_Return"),
					@ColumnResult(name = "Sales_Return")}))*/
					

//@Entity					
public class ItemReport {

	
	private String itemName;
	private double intlQty;
	private double curQty;
	private double purchaseCount;
	private double saleCount;
	private double purchaseRtnCount;
	private double salesRtnCount;
	
	
	/*public ItemReport(String itemName, double purchaseCount, double saleCount, double purchaseRtnCount, double salesRtnCount) {
		this.itemName = itemName;
		this.purchaseCount = purchaseCount;
		this.saleCount = saleCount;
		this.purchaseRtnCount = purchaseRtnCount;
		this.salesRtnCount = salesRtnCount;
	}*/
	
	public String getItemName(){
		return itemName;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public double getIntlQty(){
		return intlQty;
	}
	
	public void setIntlQty(double intlQty){
		this.intlQty = intlQty;
	}
	
	public double getCurQty(){
		return curQty;
	}
	
	public void setCurQty(double curQty){
		this.curQty = curQty;
	}
	
	public double getPurchaseCount(){
		return purchaseCount;
	}
	
	public void setPurchaseCount(double purchaseCount){
		this.purchaseCount = purchaseCount;
	}
	

	public double getSalesCount(){
		return saleCount;
	}
	
	public void setSalesCount(double saleCount){
		this.saleCount = saleCount;
	}
	
	public double getPurchaseRtnCount(){
		return purchaseRtnCount;
	}
	
	public void setPurchaseRtnCount(double purchaseRtnCount){
		this.purchaseRtnCount = purchaseRtnCount;
	}
	

	public double getSalesRtnCount(){
		return salesRtnCount;
	}
	
	public void setSalesRtnCount(double salesRtnCount){
		this.salesRtnCount = salesRtnCount;
	}
	
}