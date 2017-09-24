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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="ledger")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public int getOpbal() {
		return opbal;
	}
	public void setOpbal(int opbal) {
		this.opbal = opbal;
	}
	public int getCurbal() {
		return curbal;
	}
	public void setCurbal(int curbal) {
		this.curbal = curbal;
	}
	@NotNull
	@Column(name = "name", nullable = false)		
	private String name;
	@NotNull
	@Column(name = "alias", nullable = false)		
	private String alias;
	
	//@ManyToOne(cascade=CascadeType.MERGE)  
	@ManyToOne
	@JoinColumn(name = "groupid", nullable = false)	
	private AccGroup accGroup;
	
	@NotNull
	@Column(name = "mailingname", nullable = true)		
	private String mailingname;
	@NotNull
	@Column(name = "mailingaddress", nullable = true)		
	private String mailingaddress;
	@NotNull
	@Column(name = "mailingstate", nullable = true)		
	private String mailingstate;
	@NotNull
	@Column(name = "saletaxno", nullable = true)		
	private Long saletaxno;
	@NotNull
	@Column(name = "taxpan", nullable = true)		
	private Long taxpan;
	
	@NotNull
	@Column(name = "opbal", nullable = false)		
	private int opbal;
	
	@NotNull
	@Column(name = "curbal", nullable = false)		
	private int curbal;	
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMailingname() {
		return mailingname;
	}
	public void setMailingname(String mailingname) {
		this.mailingname = mailingname;
	}
	public String getMailingaddress() {
		return mailingaddress;
	}
	public void setMailingaddress(String mailingaddress) {
		this.mailingaddress = mailingaddress;
	}
	public String getMailingstate() {
		return mailingstate;
	}
	public void setMailingstate(String mailingstate) {
		this.mailingstate = mailingstate;
	}
	public Long getSaletaxno() {
		return saletaxno;
	}
	public void setSaletaxno(Long saletaxno) {
		this.saletaxno = saletaxno;
	}
	public Long getTaxpan() {
		return taxpan;
	}
	public void setTaxpan(Long taxpan) {
		this.taxpan = taxpan;
	}
	public AccGroup getAccGroup() {
		return accGroup;
	}
	public void setAccGroup(AccGroup accGroup) {
		this.accGroup = accGroup;
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
