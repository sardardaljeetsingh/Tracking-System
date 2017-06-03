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
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name = "name", nullable = false)		
	private String name;
	@NotNull
	@Column(name = "alias", nullable = false)		
	private String alias;
	
	@ManyToOne(cascade=CascadeType.MERGE)  
	@JoinColumn(name = "groupid", nullable = false)	
	private AccGroup accGroup;
	
	@NotNull
	@Column(name = "mailingname")		
	private String mailingname;
	@NotNull
	@Column(name = "mailingaddress", nullable = false)		
	private String mailingaddress;
	@NotNull
	@Column(name = "mailingstate", nullable = false)		
	private String mailingstate;
	@NotNull
	@Column(name = "saletaxno", nullable = false)		
	private Long saletaxno;
	@NotNull
	@Column(name = "taxpan", nullable = false)		
	private Long taxpan;
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

}
