package tracksys.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="company_user_previliges")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Previliges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String configuration;
	
	private String accountinfo;
	
	private String inventoryinfo;
	
	private String transactions;
	
	private String reports;
	
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

	
	//@OneToOne(cascade=CascadeType.ALL)  
	@ManyToOne 
	@JoinColumn(name = "userid", nullable = false)	
	private User user;
	
	//@OneToOne(cascade=CascadeType.ALL)  
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "companyid", nullable = false)	
	private Company company;	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountinfo() {
		return accountinfo;
	}

	public void setAccountinfo(String accountinfo) {
		this.accountinfo = accountinfo;
	}

	public String getInventoryinfo() {
		return inventoryinfo;
	}

	public void setInventoryinfo(String inventoryinfo) {
		this.inventoryinfo = inventoryinfo;
	}

	public String getTransactions() {
		return transactions;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
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
