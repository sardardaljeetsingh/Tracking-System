package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemDtlsRepo extends CrudRepository<ItemDetails, Integer> {

	public List<ItemDetails> findAllByItem(Item item);
	
	public List<ItemDetails> findAllByName(String itemName);

}
