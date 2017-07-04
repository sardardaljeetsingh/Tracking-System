package tracksys.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ItemController {

	@RequestMapping(value ="/item/create",method = RequestMethod.POST)
	public @ResponseBody Item  create(@RequestBody Item item) {
		List<ItemTrasacation> transList = item.getItemTrans();
		item.setItemTrans(null);
		item = itemRepository.save(item);
		if(transList != null){
			for(ItemTrasacation trasacation : transList){
				trasacation.setItem(item);
			}
		}
		itemTransactionRepo.save(item.getItemTrans());
		return itemRepository.findOne(item.getId());
	}

	@RequestMapping(value ="/item/getAll",method = RequestMethod.GET)
	public ResponseEntity<Iterable<Item>> getAllCompanies() {
		Iterable<Item> itemyList = null;
		try {
			itemyList = itemRepository.findAll();
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Iterable<Item>>(itemyList, HttpStatus.OK);
	} 	
	
	@RequestMapping(value ="/item/find-by-name/{itemname}",method = RequestMethod.GET)
	public ResponseEntity<Iterable<Item>> findAllByName(@PathVariable("itemname") String itemname) {
		Iterable<Item> itemList = null;
		try {
			itemList = itemRepository.findAllByName(itemname);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Item>>(itemList, HttpStatus.OK);
	}	
	
	@RequestMapping(value ="/itemtrans/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<ItemTrasacation> getAllItemtrans() {
		return itemTransactionRepo.findAll();
	}


	@RequestMapping(value ="/itemtrans/create",method = RequestMethod.POST)
	public @ResponseBody ItemTrasacation createItemTrans(@RequestBody ItemTrasacation itemTrasacation) {
			 return itemTransactionRepo.save(itemTrasacation);
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
