package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TransactionDetailsRepo extends CrudRepository<TransactionDetails, Integer> {

	public List<TransactionDetails> findAllByTrasactionItem(TrasactionItem trasactionItem);
}
