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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="transactions_item")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TrasactionItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="trasactionItem",fetch = FetchType.EAGER)  
	private List<TransactionDetails> transactionDetails;	
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "transid", nullable = false)
	private Transaction transaction;		
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "itemid", nullable = false)
	private Item item;	
	
	@Column(name = "quandity", nullable = false)
	private int quandity;
	
	@NotNull
	@Column(name = "rate", nullable = false)
	private int rate;	
	
	@NotNull
	@Column(name = "purcrate", nullable = false)
	private int purcrate;	

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

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuandity() {
		return quandity;
	}

	public void setQuandity(int quandity) {
		this.quandity = quandity;
	}
	
	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getPurcrate() {
		return purcrate;
	}

	public void setPurcrate(int purcrate) {
		this.purcrate = purcrate;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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
