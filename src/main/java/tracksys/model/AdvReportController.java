package tracksys.model;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.ArrayList;

import java.math.BigInteger;

@Controller
@RequestMapping("/advReportController")
public class AdvReportController {

	//@Autowired
	//private AdvReportRepository reportRepository;
	
	@PersistenceContext
	private EntityManager entityMng;

/*	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Report  create(@RequestBody Report report) {
		return reportRepository.save(report);
	}	
	
	@RequestMapping(value ="/update",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Report  update(@RequestBody Report report) {
		return reportRepository.save(entityMng.merge(report));
	}		
	
	@RequestMapping(value ="/delete/{agentId}",method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody boolean  delete(@PathVariable("agentId") int agentId) {
		reportRepository.delete(agentId);
		return true;
	}		
	
	
	@RequestMapping(value ="/find/{agentId}",method = RequestMethod.GET)
	public @ResponseBody Report findbyId(@PathVariable("agentId") int agentId) {
		return reportRepository.findOne(agentId);
	}
	*/	
	//	public List<Report> findAllTransType(int optType);
	
	
	
	//Method to return Item transaction numbers
	/*@RequestMapping(value ="/findAllItemTrans",method = RequestMethod.GET)
	public @ResponseBody Iterable<ItemReport> findAllItemTrans() {
		System.out.println("Test report called----------------->");
		return reportRepository.findAllByItemName();
	}*/
	
	//Method to return Item transaction numbers
	@RequestMapping(value ="/findAllItemTransNo",method = RequestMethod.GET)
	public @ResponseBody  Iterable<ItemReport> findAllItemTransNo() {
		System.out.println("Test report called----------------->");
		
			
		//Query q = entityMng.createNativeQuery("select (select name from item_dtl itmdtl JOIN transactions_item tranitm on tranitm.itemid = itmdtl.id) Itemname, (select count(itemid) from transactions_item tranitm JOIN transactions tr on tr.id = tranitm.transid and tr.transtype = 1) Purchases, (select count(itemid) from transactions_item tranitm JOIN transactions tr on tr.id = tranitm.transid and tr.transtype = 2) Sales, (select count(itemid) from transactions_item tranitm JOIN transactions tr on tr.id = tranitm.transid and tr.transtype = 3) PurchasesRtn, (select count(itemid) from transactions_item tranitm JOIN transactions tr on tr.id = tranitm.transid and tr.transtype = 4) SalesRtn from transactions_item tranitm group by Itemname");
		
		/*Query qry = entityMng.createNativeQuery("select itmdtl.name as itemname, (select sum(tritm.quandity) from transactions_item tritm JOIN transactions trn on trn.id = tritm.transid and trn.transtype = 1 JOIN item_dtl itmdtl on itmdtl.id = tritm.itemid) as Purchases, (select sum(tritm.quandity) 
		from transactions_item tritm JOIN transactions trn on trn.id = tritm.transid and trn.transtype = 2 JOIN item_dtl itmdtl on itmdtl.id = tritm.itemid)
		as Sales, (select sum(tritm.quandity) from transactions_item tritm JOIN transactions trn on trn.id = tritm.transid and trn.transtype = 3
		JOIN item_dtl itmdtl on itmdtl.id = tritm.itemid) as PurchaseRtn, (select sum(tritm.quandity)  from transactions_item tritm JOIN transactions trn on trn.id = tritm.transid and trn.transtype = 4 JOIN item_dtl itmdtl on itmdtl.id = tritm.itemid) as SalesRtn from  transactions_item tritm JOIN item_dtl itmdtl on tritm.itemid = itmdtl.id JOIN transactions trn on trn.id = tritm.transid group by  itemname");*/
		
		/*Query qry = entityMng.createNativeQuery("SELECT tran.itemid, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr " 
												+ " ON(tr.id  = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 1) Purchases, "
												+ " (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid "
												+ " and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 2 ) Sales, (SELECT SUM(tranitm.quandity) FROM "   
												+ " transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = "
												+ " tran.itemid) where TR.TRANSTYPE = 3 ) PurchasesRtn, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm " 
												+ " JOIN  transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = " + " 4) SalesRtn FROM transactions_item tran GROUP BY tran.itemid");*/
												
		Query qry = entityMng.createNativeQuery("SELECT itmdtl.name as itemname, itmdtl.initqundty as intlqty, itmdtl.curqundty as curqty, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr " 
											+ " ON(tr.id  = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 1) Purchases, "
											+ " (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid "
											+ " and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 2 ) Sales, (SELECT SUM(tranitm.quandity) FROM "   
											+ " transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = "
											+ " tran.itemid) where TR.TRANSTYPE = 3 ) PurchasesRtn, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm " 
											+ " JOIN  transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = " + " 4) SalesRtn FROM transactions_item tran JOIN item_dtl itmdtl on tran.itemid = itmdtl.id GROUP BY itemname, intlqty, curqty");
										
		
		List<Object[]> itemResults = qry.getResultList();
		List<ItemReport> itemReportList = new ArrayList<ItemReport>();
		
		System.out.println("Records fetched ---------> " + itemResults.size());
		
		for (Object[] itemResult: itemResults) {
			
			ItemReport itemReport = new ItemReport();
			
			itemReport.setItemName(itemResult[0].toString());
			itemReport.setIntlQty(checkNull(itemResult[1]));
			itemReport.setCurQty(checkNull(itemResult[2]));
			/*itemReport.setPurchaseCount(Double.valueOf((Double)itemResult[1]));
			itemReport.setSalesCount(Double.valueOf((Double)itemResult[2]));
			itemReport.setPurchaseRtnCount(Double.valueOf((Double)itemResult[3]));
			itemReport.setSalesRtnCount(Double.valueOf((Double)itemResult[4]));*/
			itemReport.setPurchaseCount(checkNull(itemResult[3]));
			itemReport.setSalesCount(checkNull(itemResult[4]));
			itemReport.setPurchaseRtnCount(checkNull(itemResult[5]));
			itemReport.setSalesRtnCount(checkNull(itemResult[6]));
			
			
			itemReportList.add(itemReport);
		}
		
		return itemReportList;
	}
	
	private double checkNull(Object result){
		if(result == null) {
			return Double.valueOf(0);
		} else {
			return Double.valueOf((Double)result);
		}
	}
	
}
