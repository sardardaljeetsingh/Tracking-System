package tracksys.model;

import java.util.List;


import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="acc_group_dtl")
public class AccGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

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

	
	//@ManyToOne(cascade=CascadeType.MERGE)  
	@ManyToOne  
	@JoinColumn(name = "companyid", nullable = false)
	private Company company;	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@NotNull
	@Column(name = "parentid", nullable = false)
	private Integer parent;	

	public AccGroup() { }

	public AccGroup(int id) { 
		this.id = id;
	}

	public AccGroup(String name, Company company, Integer parent, String user) {
		super();
		this.name = name;
		this.company = company;
		this.parent = parent;
		this.createdUser = user;
		this.createdDate = new java.util.Date();
		this.modifiedUser = user;
		this.modifiedDate = new java.util.Date();
	}

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

	public Integer getParent() {
		return parent != null ? parent : 0;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="accGroup",fetch = FetchType.EAGER)  
	private List<Ledger> ledger;
	
}
