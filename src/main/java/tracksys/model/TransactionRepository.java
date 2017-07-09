package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	public List<Transaction> findAllByType(int type);
	public Transaction findByVoucher(String voucher);
}

