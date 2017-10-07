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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="transactions_item_dtls")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "itemdtlid", nullable = false)
	private ItemDetails itemDetails;
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "transitemid", nullable = false)
	private TrasactionItem trasactionItem;
	
	@Column(name = "quandity", nullable = false)
	private int quandity;
	

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
	@Column(name = "modifieddate", nullable = false)	
	private  java.util.Date modifiedDate;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemDetails getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(ItemDetails itemDetails) {
		this.itemDetails = itemDetails;
	}

	public int getQuandity() {
		return quandity;
	}

	public void setQuandity(int quandity) {
		this.quandity = quandity;
	}

	public TrasactionItem getTrasactionItem() {
		return trasactionItem;
	}

	public void setTrasactionItem(TrasactionItem trasactionItem) {
		this.trasactionItem = trasactionItem;
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
