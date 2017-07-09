package tracksys.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/purchage")
public class PurchagesController {

	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Purchage createPurchage
	(@RequestBody Purchage purchage) {
		ItemDetails tempTrans = null;
		List<ItemDetails> transList = purchage.getItem().getItemDtls();
		Item item = itemRepository.findOne(purchage.getItem().getId());
		item.setCurqundty(item.getCurqundty() + purchage.getQuandity());
		item = itemRepository.save(item);
		
		if(transList != null){
			for(ItemDetails trasacation : transList){
				tempTrans = itemTransactionRepo.findOne(trasacation.getId());
				tempTrans.setCurqundty(trasacation.getQuandity());
				tempTrans.setCurpices(trasacation.getCurpices());
				tempTrans.setItem(item);
				itemTransactionRepo.save(tempTrans);
			}
		}
		
		purchage.setItem(item);
		return purchageRepository.save(purchage);
	}
	
	@RequestMapping(value ="/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Purchage> getAllPurchages() {
		return purchageRepository.findAll();
	}	
	
	@Autowired
	private ItemRepository itemRepository;	
	
	
	@Autowired
	private PurchageRepository purchageRepository;	
	
	@Autowired
	private ItemDtlsRepo itemTransactionRepo;
}
