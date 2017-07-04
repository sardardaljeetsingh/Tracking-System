package tracksys.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sales")
public class SalesController {

	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Sale createPurchage
	(@RequestBody Sale sale) {
		Item item = itemRepository.findOne(sale.getItem().getId());
		item.setCurqundty(item.getCurqundty() - sale.getQuandity());
		itemRepository.save(item);		
		sale.setItem(item);
		return salesRepository.save(sale);
	}
	
	@RequestMapping(value ="/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<Sale> getAllSales() {
		return salesRepository.findAll();
	}	
	
	@Autowired
	private ItemRepository itemRepository;	
	
	
	@Autowired
	private SalesRepository salesRepository;		
}
