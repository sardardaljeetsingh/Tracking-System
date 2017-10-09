package tracksys.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

	@RequestMapping(value ="/item/create",method = RequestMethod.POST)
	public @ResponseBody Item  create(@RequestBody Item item) {
		List<ItemDetails> itemDtlsList = item.getItemDtls();
		item.setItemDtls(null);
		item = itemRepository.save(item);
		if(itemDtlsList != null){
			for(ItemDetails itemDetail : itemDtlsList){
				itemDetail.setItem(item);
				itemDtlsRepo.save(itemDetail);
			}
		}
		//item.setItemDtls(itemDtlsList);
		//item = itemRepository.save(item);
		return itemRepository.findOne(item.getId());
	}

	/**
	Method to update the item details
	*/
	
	@RequestMapping(value ="/item/update",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Item  update(@RequestBody Item item) {
		List<ItemDetails> itemDtlsList = item.getItemDtls();
		ItemDetails tempItemDtl = null;
		item.setItemDtls(null);
		item = itemRepository.save(item);
		//System.out.println("item dlt list ------->" + itemDtlsList.size());
		if(itemDtlsList != null){
			for(ItemDetails itemDetail : itemDtlsList){
				tempItemDtl = itemDetail;
				//System.out.println("item ID ------->" + itemDetail.getId() + " " + tempItemDtl.getId());
				tempItemDtl.setCurqundty(itemDetail.getQuandity());
				tempItemDtl.setCurpices(itemDetail.getPices());
				tempItemDtl.setItem(item);
				itemDtlsRepo.save(entityMng.merge(tempItemDtl));
			}
		}
		//item.setItemDtls(itemDtlsList);
		//item = itemRepository.save(item);
		return itemRepository.findOne(item.getId());
	}

	/**
	Method to delete the item
	*/
	
	@RequestMapping(value ="/item/delete/{itemid}",method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody boolean  delete(@PathVariable("itemid") int itemid) {
		itemRepository.delete(itemid);
		return true;
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
	
	@RequestMapping(value ="/item/find-by-id/{itemid}",method = RequestMethod.GET)
	public ResponseEntity<Item> findByItemId(@PathVariable("itemid") int itemid) {
		Item item = null;
		try {
			item = itemRepository.findOne(itemid);
			item.setItemDtls(itemDtlsRepo.findAllByItem(item));
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
	
	  @RequestMapping(value ="/item/find-by-company/{companyId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<Item>> findbyCompany(@PathVariable("companyId") int companyId) {
		  Iterable<Item> itemList = null;
	    try {
	    	itemList = itemRepository.findByStockGroup_Company(companyId);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Item>>(itemList, HttpStatus.OK);
	}	
	
	/**
	Method not used and can be deleted 
	*/
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
	
	
	/**
	Method for search
	*/
	@RequestMapping(value ="/item/find-by-name-search/{companyId}/{itemname}",method = RequestMethod.GET)
	public ResponseEntity<Iterable<Item>> findByNameSearch(@PathVariable("companyId") int companyId, @PathVariable("itemname") String itemname) {
		Iterable<Item> itemList = null;
		try {
			itemList = itemRepository.findByStockGroup_Company_ItemName(companyId,itemname);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Item>>(itemList, HttpStatus.OK);
	}	
	
	@RequestMapping(value ="/itemtrans/getAll",method = RequestMethod.GET)
	public  @ResponseBody Iterable<ItemDetails> getAllItemtrans() {
		return itemDtlsRepo.findAll();
	}


	@RequestMapping(value ="/itemtrans/create",method = RequestMethod.POST)
	public @ResponseBody ItemDetails createItemTrans(@RequestBody ItemDetails itemTrasacation) {
			 return itemDtlsRepo.save(itemTrasacation);
	}


	@RequestMapping(value ="/itemtrans/find-by-itemid/itemId",method = RequestMethod.GET)
	public ResponseEntity<Iterable<ItemDetails>> findbyItemId(@PathVariable("itemId") int itemId) {
		Iterable<ItemDetails> itemTransList = null;
		try {
			Item item = new Item();
			item.setId(itemId);
			itemTransList = itemDtlsRepo.findAllByItem(item);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<ItemDetails>>(itemTransList, HttpStatus.OK);
	}	

	@RequestMapping(value ="/itemtrans/find-by-itemName/itemName",method = RequestMethod.GET)
	public ResponseEntity<Iterable<ItemDetails>> findbyCompany(@PathVariable("itemName") String itemName) {
		Iterable<ItemDetails> itemTransList = null;
		try {
			itemTransList = itemDtlsRepo.findAllByName(itemName);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<ItemDetails>>(itemTransList, HttpStatus.OK);
	}


	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemDtlsRepo itemDtlsRepo;	  

	@PersistenceContext
	private EntityManager entityMng;
}
