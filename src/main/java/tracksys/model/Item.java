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
@Table(name="item_dtl")
public class Item {
	
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getShade() {
		return shade;
	}

	public void setShade(String shade) {
		this.shade = shade;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getQuandity() {
		return quandity;
	}

	public void setQuandity(int quandity) {
		this.quandity = quandity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "name", nullable = false)
	private String name;

	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "groupid", nullable = false)
	private Group group;
	
	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "shade", nullable = false)
	private String shade;	
	
	@NotNull
	@Size(min = 3, max = 1000)
	@Column(name = "description", nullable = false)
	private String desc;		
	
	@NotNull
	@Size(min = 3, max = 1000)
	@Column(name = "uom", nullable = false)
	private String uom;	
	
	@NotNull
	@Size(min = 3, max = 1000)
	@Column(name = "totalquandity", nullable = false)
	private int quandity;	
	
	
}
