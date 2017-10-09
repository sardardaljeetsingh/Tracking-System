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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="transactions")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "transtype", nullable = false)
	private int type;
	
	//@ManyToOne(cascade=CascadeType.ALL)  
	@ManyToOne
	@JoinColumn(name = "fromledger", nullable = false)
	private Ledger fromledger;		
	
	//@ManyToOne(cascade=CascadeType.ALL)  
	@ManyToOne
	@JoinColumn(name = "ledgerid", nullable = false)
	private Ledger ledger;	
	
	@Column(name = "quandity", nullable = false)
	private float quandity;
	
	@NotNull
	@Column(name = "rate", nullable = false)
	private float rate;
	

	@NotNull
	@Column(name = "voucher", nullable = false)
	private String voucher;	
	
	@NotNull
	@Size(min = 1, max = 1000)
	@Column(name = "description", nullable = false)
	private String desc;
	
	@NotNull
	@Column(name = "transdate", nullable = false)	
	private String transdate;
	
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

	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="transaction",fetch = FetchType.EAGER)  
	private List<TrasactionItem> trasactionItems;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getQuandity() {
		return quandity;
	}

	public void setQuandity(float quandity) {
		this.quandity = quandity;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}

	public Ledger getFromledger() {
		return fromledger;
	}

	public void setFromledger(Ledger fromledger) {
		this.fromledger = fromledger;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public List<TrasactionItem> getTrasactionItems() {
		return trasactionItems;
	}

	public void setTrasactionItems(List<TrasactionItem> trasactionItems) {
		this.trasactionItems = trasactionItems;
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
