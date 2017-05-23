package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemTransactionRepo extends CrudRepository<ItemTrasacation, Integer> {

	public List<ItemTrasacation> findAllByItem(Item item);
	
	public List<ItemTrasacation> findAllByName(String itemName);

}
