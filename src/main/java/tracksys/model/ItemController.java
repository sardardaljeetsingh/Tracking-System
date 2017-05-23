package tracksys.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {

	@RequestMapping(value ="/item/create",method = RequestMethod.POST)
	public ResponseEntity<Item>  create(@RequestBody Item item) {
		try {
			itemRepository.save(item);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@RequestMapping(value ="/item/getAll",method = RequestMethod.GET)
	public ResponseEntity<Iterable<Item>> getAllCompanies() {
		Iterable<Item> itemyList = null;
		try {
			itemyList = itemRepository.findAll();
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Item>>(itemyList, HttpStatus.OK);
	} 	


	@RequestMapping(value ="/itemtrans/create",method = RequestMethod.POST)
	public ResponseEntity<ItemTrasacation>  createItemTrans(@RequestBody ItemTrasacation itemTrasacation) {
		try {
			itemTransactionRepo.save(itemTrasacation);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ItemTrasacation>(itemTrasacation, HttpStatus.OK);
	}


	@RequestMapping(value ="/itemtrans/find-by-itemid/itemId",method = RequestMethod.GET)
	public ResponseEntity<Iterable<ItemTrasacation>> findbyCompany(@PathVariable("itemId") int itemId) {
		Iterable<ItemTrasacation> itemTransList = null;
		try {
			Item item = new Item();
			item.setId(itemId);
			itemTransList = itemTransactionRepo.findAllByItem(item);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<ItemTrasacation>>(itemTransList, HttpStatus.OK);
	}	

	@RequestMapping(value ="/itemtrans/find-by-itemName/itemName",method = RequestMethod.GET)
	public ResponseEntity<Iterable<ItemTrasacation>> findbyCompany(@PathVariable("itemName") String itemName) {
		Iterable<ItemTrasacation> itemTransList = null;
		try {
			itemTransList = itemTransactionRepo.findAllByName(itemName);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<ItemTrasacation>>(itemTransList, HttpStatus.OK);
	}


	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemTransactionRepo itemTransactionRepo;	  

}
