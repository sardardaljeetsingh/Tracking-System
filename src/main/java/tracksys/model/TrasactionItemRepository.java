package tracksys.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TrasactionItemRepository extends CrudRepository<TrasactionItem, Integer> {
	
	@Query(value = "select count(*) from transactions_item trans "
			+ "where trans.itemid=?1", nativeQuery = true)
	public int findItem(int itemid);
}

