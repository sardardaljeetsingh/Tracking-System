package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {

	public List<Item> findAllByGroup(Group group);

}
