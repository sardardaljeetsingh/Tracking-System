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
@Table(name="group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "companyid", nullable = false)
	private Company company;	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@NotNull
	@Column(name = "parent", nullable = false)
	private int parent;	

	public Group() { }

	public Group(int id) { 
		this.id = id;
	}

	public Group(String name, Company company, int parent) {
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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}	  
}
