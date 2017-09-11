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
@RequestMapping("/transactionItem")
public class TrasactionItemController {

	
	@RequestMapping(value ="/find-item/{itemId}",method = RequestMethod.GET)
	public  @ResponseBody int getByItem(@PathVariable("itemId") int itemId) {
		return trasactionItemRepository.findItem(itemId);
	}	
	
	
	@Autowired
	private TrasactionItemRepository trasactionItemRepository;
	
}
