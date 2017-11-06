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
import javax.transaction.Transactional;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

	@RequestMapping(value ="/create",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Transaction createTransaction
	(@RequestBody Transaction transaction) {
		
		updateLedgers(transaction);
		
		updateTransactionItems(transaction);
		
		return transactionRepository.save(transaction);
	}
	
	@RequestMapping(value ="/update",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Transaction editTransaction
	(@RequestBody Transaction transaction) {
		
		Transaction extTransaction = transactionRepository.findOne(transaction.getId());
		
		
		
		
		//if(transactionRepository.delete(transaction.getId())){
			editLedgers(transaction, extTransaction);
			
			editTransactionItems(transaction, extTransaction);
			
			//transactionRepository.delete(extTransaction.getId());
			
			return transactionRepository.save(transaction);
		//}
	}
	
	private void editLedgers(Transaction transaction, Transaction extTransaction){
		
		//Deduct amount from existing transactions
		//Transaction extTransaction = transactionRepository.findOne(transaction.getId());
		
		System.out.println("Ledger Details ----> " + extTransaction.getFromledger().getId() + " , " + extTransaction.getLedger().getId());
		
		Ledger extFromLedger = ledgerRepository.findOne(extTransaction.getFromledger().getId());
		Ledger extToLedger = ledgerRepository.findOne(extTransaction.getLedger().getId());
		
		extFromLedger.setCurbal(extFromLedger.getCurbal() - (extTransaction.getRate()));
		extToLedger.setCurbal(extToLedger.getCurbal() - (extTransaction.getRate()));				

		Ledger fromLedger = null;
		Ledger toLedger = null;
		
		if(extFromLedger.getId() != transaction.getFromledger().getId()) {
			fromLedger = ledgerRepository.findOne(transaction.getFromledger().getId());
		} else {
			fromLedger = extFromLedger;
		}
		
		if(extToLedger.getId() != transaction.getLedger().getId()) {
			toLedger = ledgerRepository.findOne(transaction.getFromledger().getId());
		} else {
			toLedger = extToLedger;
		}
		
		System.out.println("Update Ledgers ----------> " + fromLedger + " , " + toLedger);	
		
		fromLedger.setCurbal(fromLedger.getCurbal() + (transaction.getRate()));
		toLedger.setCurbal(toLedger.getCurbal() + (transaction.getRate()));				

		fromLedger.setModifiedDate(new java.util.Date());
		fromLedger.setModifiedUser(transaction.getModifiedUser());
		
		toLedger.setModifiedDate(new java.util.Date());
		toLedger.setModifiedUser(transaction.getModifiedUser());
		
		transaction.setLedger(toLedger);
		transaction.setFromledger(fromLedger);
		
		System.out.println("Ledgers Done ++++++++++++++++++++++++");
	}
	
	private void editTransactionItems(Transaction transaction, Transaction extTransaction){
		
		System.out.println("editTransactionItems start ++++++++++++++++++++++++");
		
		//Update the exisitng items with deducting the quantity
		List<TrasactionItem> extrasactionItemList = null;
		Item item = null;
		
		extrasactionItemList = extTransaction.getTrasactionItems();
		System.out.println("Existing items to be updated ----> " + extrasactionItemList.size());
		
		List<String> itemList = new ArrayList<String>(); 
		
		for(TrasactionItem extrasactionItem : extrasactionItemList){
			
			if(extrasactionItem.getItem().getName() != null && extrasactionItem.getItem().getName() != "") {
				
				item = itemRepository.findOne(extrasactionItem.getItem().getId());
				
				if(!itemList.contains(item.getName())) { //aviod multiple reoccurances of same item
					
					itemList.add(item.getName());
					
					System.out.println("Revert quantity for Item ------> " + item.getName() + " CQty : " + item.getCurqundty() + " OldQty : " + extrasactionItem.getQuandity() );
					
					if(extTransaction.getType() == 1 || extTransaction.getType() == 4) {
						//For Purchase and Sales Return , deduct quantity
						item.setCurqundty(item.getCurqundty() - extrasactionItem.getQuandity());
					} else {
						item.setCurqundty(item.getCurqundty() + extrasactionItem.getQuandity());
					}
					System.out.println("Reverted quantity for Item ------> " + extrasactionItem.getItem().getName() + " CQty : " + item.getCurqundty());
					
					item = itemRepository.save(item);
				}	
			} 				
		}	
		System.out.println("Update existing item quantity done ----------> " );
		
		//Add new quantity	
		List<TrasactionItem> trasactionItemList = transaction.getTrasactionItems();
		
		itemList = new ArrayList<String>(); 
		
		for(TrasactionItem trasactionItem : trasactionItemList){
			
			if(trasactionItem.getItem().getName() != null && trasactionItem.getItem().getName() != "") {
			
				item = itemRepository.findOne(trasactionItem.getItem().getId());
		
				if(!itemList.contains(item.getName())) { //aviod multiple reoccurances of same item
		
					itemList.add(item.getName());
				
					if(transaction.getType() == 1 || transaction.getType() == 4) {
						item.setCurqundty(item.getCurqundty() + trasactionItem.getQuandity());
					} else {
						item.setCurqundty(item.getCurqundty() - trasactionItem.getQuandity());
					}
					
					item.setModifiedUser(trasactionItem.getModifiedUser());
					item.setModifiedDate(new java.util.Date());
					
					System.out.println("Updated new quantity for Item ------> " + item.getName() + " CQty : " + item.getCurqundty());
			
					item = itemRepository.save(item);
					
					updateTransactionItemDtlsForEdit(transaction.getType(), trasactionItem, item);
					//updateTransactionItemDtls(transaction.getType(), trasactionItem, item);
					
					trasactionItem.setTransaction(transaction);
				}
			}
		}
		
		 
			System.out.println("editTransactionItems Done ++++++++++++++++++++++++");
		
	
	}
	
	
	private void updateTransactionItemDtlsForEdit(int transType, TrasactionItem trasactionItem,Item item){
		
		List<TransactionDetails> transactionDetailsLst =  null;
		
		if (trasactionItem.getItem().getName() != null && trasactionItem.getItem().getName() != "") {
			System.out.println("Checking -----> " + trasactionItem.getItem().getName());
			transactionDetailsLst = trasactionItem.getTransactionDetails();
			System.out.println("transactionDetailsLst size -----> " + transactionDetailsLst.size());
			
		
		} else {
			System.out.println("Null Transaction Item -----> " );
		}
		
		ItemDetails tempItemDtl = null;
		ItemDetails itemDetail = null;
		
		List dupList =  new ArrayList<String>(); 
		
		if(transactionDetailsLst != null) {
			for(TransactionDetails transactionDetails : transactionDetailsLst){
				
				if(transactionDetails != null) {
					
					itemDetail = transactionDetails.getItemDetails();
					
					if(!dupList.contains(String.valueOf(itemDetail.getId()))){
						
						dupList.add(String.valueOf(itemDetail.getId()));
					
						System.out.println("itemDetail cquantity ---------> " + itemDetail.getCurqundty());
						System.out.println("itemDetail quantity ---------> " + itemDetail.getQuandity());
						
						
						tempItemDtl = itemDtlsRepo.findOne(transactionDetails.getItemDetails().getId());
						System.out.println("tempItemDtl Cquantity ---------> " + tempItemDtl.getCurqundty());
						System.out.println("tempItemDtl quantity ---------> " + tempItemDtl.getQuandity());
						
						//For purchase transactions
						//tempItemDtl.setCurqundty(itemDetail.getCurqundty());
						
						//For sales transactions
						tempItemDtl.setCurqundty(itemDetail.getQuandity() - itemDetail.getCurqundty());
						
						tempItemDtl.setCurpices(itemDetail.getPices());
						tempItemDtl.setModifiedUser(trasactionItem.getModifiedUser());
						tempItemDtl.setModifiedDate(new java.util.Date());
						
						tempItemDtl = itemDtlsRepo.save(tempItemDtl);
					
						System.out.println("Saved itemDetail quantity ---------> " );
					
						transactionDetails.setItemDetails(tempItemDtl);
						//For purchase transaction
						//transactionDetails.setQuandity(tempItemDtl.getCurqundty() * tempItemDtl.getCurpices());
						
						//For Sales transaction
						transactionDetails.setQuandity(itemDetail.getCurqundty());
						transactionDetails.setTrasactionItem(trasactionItem);
						transactionDetails.setModifiedUser(trasactionItem.getModifiedUser());
						transactionDetails.setModifiedDate(new java.util.Date());
						
					}
				}
		
			}
	
			trasactionItem.setTransactionDetails(transactionDetailsLst);
				
			trasactionItem.setItem(item);
	
		}
		
		//trasactionItem.setTransactionDetails(transDtls);
		
	}
	
	
	
	
	private void updateLedgers(Transaction transaction){
		Ledger fromLedger = ledgerRepository.findOne(transaction.getFromledger().getId());
		Ledger toLedger = ledgerRepository.findOne(transaction.getLedger().getId());
		
		//changed to add the amounts on 10/12 and applied to all transactions. Earlier it was subtract and applicable for only sales and purchases.
		//if(transaction.getType() == 1 || transaction.getType() == 2){
			fromLedger.setCurbal(fromLedger.getCurbal() + (transaction.getRate()));
			toLedger.setCurbal(toLedger.getCurbal() + (transaction.getRate()));				
		//}else{
		//	fromLedger.setCurbal(fromLedger.getCurbal() + (transaction.getRate()));
		//	toLedger.setCurbal(toLedger.getCurbal() + (transaction.getRate()));			
		//}
		transaction.setLedger(toLedger);
		transaction.setFromledger(fromLedger);
	}
	
	private void updateTransactionItems(Transaction transaction){
		List<TrasactionItem> TrasactionItemList = transaction.getTrasactionItems();
		Item item = null;
		for(TrasactionItem trasactionItem : TrasactionItemList){
			item = itemRepository.findOne(trasactionItem.getItem().getId());
			if(transaction.getType() == 1 || transaction.getType() == 4){
				item.setCurqundty(item.getCurqundty() + trasactionItem.getQuandity());
			}else{
				item.setCurqundty(item.getCurqundty() - trasactionItem.getQuandity());
			}
			item = itemRepository.save(item);
			updateTransactionItemDtls(transaction.getType(), trasactionItem, item);
			trasactionItem.setTransaction(transaction);
		}
	}
	
	private void updateTransactionItemDtls(int transType, TrasactionItem trasactionItem,Item item){
		List<ItemDetails> itemDtls = trasactionItem.getItem().getItemDtls();
		trasactionItem.setTransactionDetails(new ArrayList<TransactionDetails>());
		ItemDetails tempItemDtl = null;
		TransactionDetails transactionDetails = null;
		if(itemDtls != null){
			for(ItemDetails itemDetail : itemDtls){
				switch(transType){
				case 1:  
					tempItemDtl = itemDetail;
					tempItemDtl.setCurqundty(itemDetail.getQuandity());
					tempItemDtl.setCurpices(itemDetail.getPices());
					tempItemDtl.setItem(item);
					break;
				case 2:  
					tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() - itemDetail.getCurqundty());
					//tempItemDtl.setCurpices(tempItemDtl.getCurpices()- itemDetail.getCurpices());
					
					break;
				case 3:  
					tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() - itemDetail.getCurqundty());
					//tempItemDtl.setCurpices(tempItemDtl.getCurpices()- itemDetail.getCurpices());
					
			
					break;					
				case 4:  
					//tempItemDtl = itemDtlsRepo.findOne(itemDetail.getId());
					//tempItemDtl.setCurqundty(tempItemDtl.getCurqundty() + itemDetail.getCurqundty());
					//tempItemDtl.setCurpices(tempItemDtl.getCurpices() + itemDetail.getCurpices());
					tempItemDtl = itemDetail;
					tempItemDtl.setCurqundty(itemDetail.getQuandity());
					tempItemDtl.setCurpices(itemDetail.getPices());
					tempItemDtl.setItem(item);
					
					break;
				}
				
				//tempItemDtl.setCreatedUser("admin");
				//tempItemDtl.setCreatedDate(new java.util.Date());
				//tempItemDtl.setModifiedUser("admin");
				//tempItemDtl.setModifiedDate(new java.util.Date());
				
				tempItemDtl = itemDtlsRepo.save(tempItemDtl);
				transactionDetails = new TransactionDetails();
				transactionDetails.setItemDetails(tempItemDtl);
				transactionDetails.setQuandity(itemDetail.getCurqundty() * itemDetail.getPices());
				transactionDetails.setTrasactionItem(trasactionItem);
				
				transactionDetails.setCreatedUser(tempItemDtl.getCreatedUser());
				transactionDetails.setCreatedDate(new java.util.Date());
				transactionDetails.setModifiedUser(tempItemDtl.getCreatedUser());
				transactionDetails.setModifiedDate(new java.util.Date());
				
		
				trasactionItem.getTransactionDetails().add(transactionDetails);
			}
		}	
		
		trasactionItem.setItem(item);
		
	}
	
	@RequestMapping(value ="/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Transaction> getAllTrsactions() {
		return transactionRepository.findAll();
	}	
	
	@RequestMapping(value ="/findAll-by-type/{companyId}/{type}",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Transaction> findAllTRsacionByType(@PathVariable("companyId") int companyId, @PathVariable("type") int type) {
		return transactionRepository.findAllByType_Company(companyId, type);
	}	
	
	@RequestMapping(value ="/find-by-id/{transId}",method = RequestMethod.GET)
	public  @ResponseBody Transaction getById(@PathVariable("transId") int transId) {
		System.out.println("Find Id called ---------> " + 	transId);
		try {
			return transactionRepository.findOne(transId);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}	
	
	@Autowired
	private ItemRepository itemRepository;	
	
	@Autowired
	private ItemDtlsRepo itemDtlsRepo;
	
	@Autowired
	private TransactionDetailsRepo transactionDetailsRepo;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private LedgerRepository ledgerRepository;
}
