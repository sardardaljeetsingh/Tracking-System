package tracksys.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="item_dtl_trans")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ItemDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "name", nullable = false)
	private String name;	
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "itemid", nullable = false)
	private Item item;
	
	@Column(name = "quandity", nullable = false)
	private float quandity;
	
	@NotNull
	@Column(name = "curqundty", nullable = false)
	private float curqundty;		
	
	@Column(name = "pices", nullable = false)
	private float pices;
	
	@Column(name = "curpices", nullable = false)
	private float curpices;	

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



	
	@JsonInclude()
	@Transient
	private float inputqundty;	

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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public float getQuandity() {
		return quandity;
	}

	public void setQuandity(float quandity) {
		this.quandity = quandity;
	}

	public float getPices() {
		return pices;
	}

	public void setPices(float pices) {
		this.pices = pices;
	}

	public float getCurqundty() {
		return curqundty;
	}

	public void setCurqundty(float curqundty) {
		this.curqundty = curqundty;
	}

	public float getCurpices() {
		return curpices;
	}

	public void setCurpices(float curpices) {
		this.curpices = curpices;
	}

	public float getInputqundty() {
		return inputqundty;
	}

	public void setInputqundty(float inputqundty) {
		this.inputqundty = inputqundty;
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
	


	
}
