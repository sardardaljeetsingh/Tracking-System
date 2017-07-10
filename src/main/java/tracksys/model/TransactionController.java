package tracksys.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Transaction createTransaction
	(@RequestBody Transaction transaction) {
		List<ItemDetails> itemDtls = transaction.getItem().getItemDtls();
		ItemDetails tempItemDtl = null;
		TransactionDetails transactionDetails = null;
		transaction.setTransactionDetails(new ArrayList<TransactionDetails>());
		
		Item item = itemRepository.findOne(transaction.getItem().getId());
		Ledger ledger = ledgerRepository.findOne(transaction.getLedger().getId());
		
		switch(transaction.getType()){
		case 1:  
			item.setCurqundty(item.getCurqundty() + transaction.getQuandity());
			ledger.setCurbal(ledger.getCurbal() + (transaction.getQuandity() * transaction.getRate()));
			break;
		case 2:  
			item.setCurqundty(item.getCurqundty() - transaction.getQuandity());
			ledger.setCurbal(ledger.getCurbal() - (transaction.getQuandity() * transaction.getRate()));
			break;
		case 3:  
			item.setCurqundty(item.getCurqundty() - transaction.getQuandity());
			ledger.setCurbal(ledger.getCurbal() - (transaction.getQuandity() * transaction.getRate()));
			break;
		case 4:  
			item.setCurqundty(item.getCurqundty() + transaction.getQuandity());
			ledger.setCurbal(ledger.getCurbal() + (transaction.getQuandity() * transaction.getRate()));
			break;
		}
		itemRepository.save(item);
		ledgerRepository.save(ledger);
		
		if(itemDtls != null){
			for(ItemDetails itemDetail : itemDtls){
				switch(transaction.getType()){
				case 1:  
					tempItemDtl = itemDetail;
					tempItemDtl.setCurqundty(itemDetail.getQuandity());
					tempItemDtl.setCurpices(itemDetail.getPices());
					tempItemDtl.setItem(item);
					break;
				case 2:  
					tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() - itemDetail.getCurqundty());
					tempItemDtl.setCurpices(tempItemDtl.getCurpices()- itemDetail.getCurpices());
					break;
				case 3:  
					tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() - itemDetail.getCurqundty());
					tempItemDtl.setCurpices(tempItemDtl.getCurpices()- itemDetail.getCurpices());
					break;					
				case 4:  
					tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() + itemDetail.getCurqundty());
					tempItemDtl.setCurpices(tempItemDtl.getCurpices() + itemDetail.getCurpices());
					break;
				}				
				tempItemDtl = itemDtlsRepo.save(tempItemDtl);
				transactionDetails = new TransactionDetails();
				transactionDetails.setItemDetails(tempItemDtl);
				transactionDetails.setQuandity(itemDetail.getCurqundty());
				transactionDetails.setTransaction(transaction);
				transaction.getTransactionDetails().add(transactionDetails);
			}
		}
			
		transaction.setItem(item);
		transaction.setLedger(ledger);
		return transactionRepository.save(transaction);
	}
	
	@RequestMapping(value ="/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Transaction> getAllTrsactions() {
		return transactionRepository.findAll();
	}	
	
	@RequestMapping(value ="/findAll-by-type/{type}",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Transaction> findAllTRsacionByType(@PathVariable("type") int type) {
		return transactionRepository.findAllByType(type);
	}		
	
	@Autowired
	private ItemRepository itemRepository;	
	
	@Autowired
	private ItemDtlsRepo itemDtlsRepo;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private LedgerRepository ledgerRepository;
}
