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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="transactions_item_dtls")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "itemdtlid", nullable = false)
	private ItemDetails itemDetails;
	
	@ManyToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "transitemid", nullable = false)
	private TrasactionItem trasactionItem;
	
	@Column(name = "quandity", nullable = false)
	private int quandity;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemDetails getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(ItemDetails itemDetails) {
		this.itemDetails = itemDetails;
	}

	public int getQuandity() {
		return quandity;
	}

	public void setQuandity(int quandity) {
		this.quandity = quandity;
	}

	public TrasactionItem getTrasactionItem() {
		return trasactionItem;
	}

	public void setTrasactionItem(TrasactionItem trasactionItem) {
		this.trasactionItem = trasactionItem;
	}
	
	
}
