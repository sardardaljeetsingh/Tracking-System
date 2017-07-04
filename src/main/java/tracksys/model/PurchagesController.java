package tracksys.model;

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
		Item item = itemRepository.findOne(purchage.getItem().getId());
		item.setCurqundty(item.getCurqundty() + purchage.getQuandity());
		itemRepository.save(item);
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
}
