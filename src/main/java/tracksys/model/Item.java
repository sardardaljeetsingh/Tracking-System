package tracksys.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="item_dtl")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Item {
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setItemHSN(String itemHSN) {
		this.itemHSN = itemHSN;
	}

	public String getItemHSN() {
		return itemHSN;
	}

	
	public float getrateGST() {
		return rateGST;
	}
	
	
	public void setRateGST(float rateGST) {
		this.rateGST = rateGST;
	}
	
	public String getShade() {
		return shade;
	}

	public void setShade(String shade) {
		this.shade = shade;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public float getQuandity() {
		return quandity;
	}

	public void setQuandity(float quandity) {
		this.quandity = quandity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "name", nullable = false)
	private String name;

	
	/*@ManyToOne(cascade=CascadeType.MERGE)  
	@JoinColumn(name = "groupid", nullable = false)
	private int stockGroup;
	*/
	
	@NotNull
	@Column(name = "groupid")
	private int stockGroup;	
	
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "itemHSN", nullable = false)
	private String itemHSN;	

	@NotNull
	@Column(name = "GSTRate", nullable = false)
	private float rateGST;	

	
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "shade", nullable = false)
	private String shade;	
	
	@NotNull
	@Size(min = 1, max = 1000)
	@Column(name = "description", nullable = false)
	private String desc;		
	
	@NotNull
	@Size(min = 1, max = 1000)
	@Column(name = "uom", nullable = false)
	private String uom;	
	
	@NotNull
	@Column(name = "initqundty", nullable = false)
	private float quandity;
	
	@NotNull
	@Column(name = "createduser", nullable = false)	
	private String createdUser;
	
	@NotNull
	@Column(name = "createddate", nullable = false)	
	private java.util.Date createdDate;
	
	@NotNull
	@Column(name = "modifieduser", nullable = false)	
	private String modifiedUser;
	
	@NotNull
	@Column(name = "modifieddate", nullable = true)	
	private  java.util.Date modifiedDate;

	
	
	
	public float getCurqundty() {
		return curqundty;
	}

	public void setCurqundty(float curqundty) {
		this.curqundty = curqundty;
	}

	@NotNull
	@Column(name = "curqundty", nullable = false)
	private float curqundty;	
	
	@NotNull
	@Column(name = "rate", nullable = false)
	private float rate;	
	
	@NotNull
	@Column(name = "purcrate", nullable = false)
	private float purcrate;		
	
	public float getPurcrate() {
		return purcrate;
	}

	public void setPurcrate(float purcrate) {
		this.purcrate = purcrate;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="item",fetch = FetchType.EAGER)  
	private List<ItemDetails> itemDtls;

	
	public int getStockGroup() {
		return stockGroup;
	}

	public void setStockGroup(int stockGroup) {
		this.stockGroup = stockGroup;
	}

	public List<ItemDetails> getItemDtls() {
		return itemDtls;
	}

	public void setItemDtls(List<ItemDetails> itemDtls) {
		this.itemDtls = itemDtls;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}	
	
	
/*

	public StockGroup getStockGroup() {
		return stockGroup;
	}

	public void setStockGroup(StockGroup stockGroup) {
		this.stockGroup = stockGroup;
	}	
	
	*/
	
}
