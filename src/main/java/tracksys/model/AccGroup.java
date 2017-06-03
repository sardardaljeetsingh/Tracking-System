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

	@ManyToOne(cascade=CascadeType.MERGE)  
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

	public AccGroup(String name, Company company, Integer parent) {
		super();
		this.name = name;
		this.company = company;
		this.parent = parent;
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
}
