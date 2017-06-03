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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="item_dtl")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
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

	
/*	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "groupid", nullable = false)
	private StockGroup stockGroup;*/
	
	@NotNull
	@Column(name = "groupid")
	private int stockGroup;	
	
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
	@Column(name = "totalquandity", nullable = false)
	private int quandity;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="item",fetch = FetchType.EAGER)  
	private List<ItemTrasacation> itemTrans;

	public int getStockGroup() {
		return stockGroup;
	}

	public void setStockGroup(int stockGroup) {
		this.stockGroup = stockGroup;
	}

	public List<ItemTrasacation> getItemTrans() {
		return itemTrans;
	}

	public void setItemTrans(List<ItemTrasacation> itemTrans) {
		this.itemTrans = itemTrans;
	}

/*	public StockGroup getStockGroup() {
		return stockGroup;
	}

	public void setStockGroup(StockGroup stockGroup) {
		this.stockGroup = stockGroup;
	}	*/
	
	
}
