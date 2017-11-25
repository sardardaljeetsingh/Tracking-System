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
@RequestMapping("/reportController")
public class ReportController {

	@Autowired
	private ReportRepository reportRepository;
	
	@PersistenceContext
	private EntityManager entityMng;

/*	
	
	
	@RequestMapping(value ="/findAllTransType/{optType}",method = RequestMethod.GET)
	  public @ResponseBody List<Report> findAllTransType(@PathVariable("optType") int optType) {
		List<Report> reportList = null;
		//if(optType.equals("P")){
			reportList = reportRepository.findAllByType(optType);//new ArrayList<>();
		//} else {
			//reportList = reportRepository.findAllTransType(2);//new ArrayList<>();
		//}
		
		return reportList; 		

	  } 	  
	
		
	/**
	Method for search
	*/
	@RequestMapping(value ="/find-by-name-search/{optType}/{voucherNumber}",method = RequestMethod.GET)
	public ResponseEntity<Iterable<Report>> findByNameSearch(@PathVariable("optType") int optType, @PathVariable("voucherNumber") String voucherNumber) {
		Iterable<Report> reportList = null;
		try {
			reportList = reportRepository.findByTypeAndVoucherIgnoreCaseContaining(optType,voucherNumber);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Report>>(reportList, HttpStatus.OK);
	}	
		
		
		
		
	@RequestMapping(value ="/findAll",method = RequestMethod.GET)
	public @ResponseBody Iterable<Report> findAll() {
		return reportRepository.findAll();

	
	
	}
	
	
	@RequestMapping(value ="/findAllItemTransNo",method = RequestMethod.GET)
	public @ResponseBody  Iterable<ItemReport> findAllItemTransNo() {
		System.out.println("Item report called----------------->");
		
			
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
												+ " JOIN  transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = " + " 4) SalesRtn FROM transactions_item tran GROUP BY tran.itemid");
												
		Query qry = entityMng.createNativeQuery("SELECT itmdtl.name as itemname, itmdtl.initqundty as intlqty, itmdtl.curqundty as curqty, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr " 
											+ " ON(tr.id  = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 1) Purchases, "
											+ " (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid "
											+ " and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = 2 ) Sales, (SELECT SUM(tranitm.quandity) FROM "   
											+ " transactions_item tranitm JOIN transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = "
											+ " tran.itemid) where TR.TRANSTYPE = 3 ) PurchasesRtn, (SELECT SUM(tranitm.quandity) FROM transactions_item tranitm " 
											+ " JOIN  transactions tr ON(tr.id = tranitm.transid and tranitm.itemid = tran.itemid) where TR.TRANSTYPE = " + " 4) SalesRtn FROM transactions_item tran JOIN item_dtl itmdtl on tran.itemid = itmdtl.id GROUP BY itemname, intlqty, curqty");*/
		
		Query qry = entityMng.createNativeQuery("SELECT itm.name, itm.initqundty, itm.curqundty, SUM(IF(tr.transtype = 1, tran.quandity, 0)) AS Purchases,"
        + " SUM(IF(tr.transtype = 2, tran.quandity, 0)) AS Sales, SUM(IF(tr.transtype = 3, tran.quandity, 0)) AS PurchasesRtn, "
        + " SUM(IF(tr.transtype = 4, tran.quandity, 0)) AS SalesRtn FROM transactions_item AS tran "
        + " JOIN item_dtl AS itm ON itm.id = tran.itemid JOIN transactions AS tr ON tran.transid = tr.id GROUP BY tran.itemid");
			
		
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
	
	
	@RequestMapping(value ="/findAllLedgerTransNo",method = RequestMethod.GET)
	public @ResponseBody  Iterable<ItemReport> findAllLedgerTransNo() {
		System.out.println("Ledger report called----------------->");
		
										
		
		/*Query qry = entityMng.createNativeQuery("select ldgr.name as ldgrname, (select sum(tr.rate) from transactions tr JOIN ledger ldgr on (ldgr.id = "
		+ " tr.fromledger and tr.fromledger = tran.fromledger) where tr.transtype = 1) Purchases, (select sum(tr.rate) from transactions tr JOIN ledger ldgr on (ldgr.id = tr.fromledger  and tr.fromledger = tran.fromledger) where tr.transtype = 2) Sales, (select sum(tr.rate) from transactions tr JOIN ledger ldgr on (ldgr.id = tr.fromledger  and tr.fromledger = tran.fromledger) where tr.transtype = 3) PurchaseRtn, (select sum(tr.rate) from transactions tr JOIN ledger ldgr on (ldgr.id = tr.fromledger  and tr.fromledger = tran.fromledger) where tr.transtype = 4) SalesRtn from transactions tran JOIN ledger ldgr on ldgr.id = tran.fromledger Group by ldgrname");*/
		
		Query qry = entityMng.createNativeQuery("Select ldgr.name, SUM(IF(tr.transtype = 1, tr.rate, 0)) AS Purchases,"
        + " SUM(IF(tr.transtype = 2, tr.rate, 0)) AS Sales, SUM(IF(tr.transtype = 3, tr.rate, 0)) AS PurchasesRtn,"
        + " SUM(IF(tr.transtype = 4, tr.rate, 0)) AS SalesRtn from transactions AS tr "
		+ " JOIN ledger AS ldgr on ldgr.id = tr.fromledger GROUP BY tr.fromledger");
	
		
		
		List<Object[]> itemResults = qry.getResultList();
		List<ItemReport> itemReportList = new ArrayList<ItemReport>();
		
		System.out.println("Records fetched ---------> " + itemResults.size());
		
		for (Object[] itemResult: itemResults) {
			
			ItemReport itemReport = new ItemReport();
			itemReport.setItemName(itemResult[0].toString());
			itemReport.setPurchaseCount(checkNull(itemResult[1]));
			itemReport.setSalesCount(checkNull(itemResult[2]));
			itemReport.setPurchaseRtnCount(checkNull(itemResult[3]));
			itemReport.setSalesRtnCount(checkNull(itemResult[4]));
			
			
			itemReportList.add(itemReport);
		}
		
		return itemReportList;
	}
	
	
	
	@RequestMapping(value ="/findAllAgentTransNo",method = RequestMethod.GET)
	public @ResponseBody  Iterable<ItemReport> findAllAgentTransNo() {
		System.out.println("Agent report called----------------->");
		
		
		/*Query qry = entityMng.createNativeQuery("select agnt.name as agentname, (select sum(rate) from transactions tr JOIN agents agnt on (agnt.id = "
		+ " tr.agentid and tr.agentid = tran.agentid) where tr.transtype = 2) Sales, (((select sum(rate) from transactions tr JOIN agents agnt on (agnt.id = "
		+ " tr.agentid and tr.agentid = tran.agentid) where tr.transtype = 2) * agnt.commission)/100) SalesCom,(select sum(rate) from transactions tr  JOIN agents agnt on (agnt.id = tr.agentid  and tr.agentid = tran.agentid)  where tr.transtype = 4) SalesRtn, (((select sum(rate) from transactions tr JOIN agents agnt on (agnt.id = "
		+ " tr.agentid  and tr.agentid = tran.agentid) where tr.transtype = 4) * agnt.commission)/100) SalesRtnCom from  transactions tran JOIN agents agnt on agnt.id = tran.agentid group by agentname" );*/
		
		Query qry = entityMng.createNativeQuery("Select agnt.name, SUM(IF(tr.transtype = 2, tr.rate, 0)) AS Sales,"
		+ " (SUM(IF(tr.transtype = 2, tr.rate, 0)) * agnt.commission/100) AS SalesCom, SUM(IF(tr.transtype = 4, tr.rate, 0)) AS SalesRtn,"
		+ " (SUM(IF(tr.transtype = 4, tr.rate, 0))* agnt.commission/100) AS SalesRtnCom from transactions AS tr "
		+ " JOIN agents AS agnt on agnt.id = tr.agentid GROUP BY tr.agentid");
		
		List<Object[]> itemResults = qry.getResultList();
		List<ItemReport> itemReportList = new ArrayList<ItemReport>();
		
		System.out.println("Records fetched ---------> " + itemResults.size());
		
		for (Object[] itemResult: itemResults) {
			
			ItemReport itemReport = new ItemReport();
			itemReport.setItemName(itemResult[0].toString());
			itemReport.setPurchaseCount(checkNull(itemResult[1]));
			itemReport.setSalesCount(checkNull(itemResult[2]));
			itemReport.setPurchaseRtnCount(checkNull(itemResult[3]));
			itemReport.setSalesRtnCount(checkNull(itemResult[4]));
			
			
			itemReportList.add(itemReport);
		}
		
		return itemReportList;
	}
	
	
	@RequestMapping(value ="/findAllTransNo",method = RequestMethod.GET)
	public @ResponseBody  Iterable<ItemReport> findAllTransNo() {
		System.out.println("Transaction Summary report called----------------->");
		
		
		//Query qry = entityMng.createNativeQuery("select sum(rate) from transactions tr where tr.transtype = 1, select sum(rate) from transactions tr where tr.transtype = 2, select sum(rate) from transactions tr where tr.transtype = 3, select sum(rate) from transactions tr where tr.transtype = 4");
		
		Query qry = entityMng.createNativeQuery("select SUM(DECODE(TRANSTYPE,1,RATE)),SUM(DECODE(TRANSTYPE,2,RATE)),SUM(DECODE(TRANSTYPE,3,RATE)),"
		+ " sum(decode(transtype,4,rate)) from TRANSACTIONS");
		
		
		List<Object[]> itemResults = qry.getResultList();
		List<ItemReport> itemReportList = new ArrayList<ItemReport>();
		
		System.out.println("Records fetched ---------> " + itemResults.size());
		
		for (Object[] itemResult: itemResults) {
			
			ItemReport itemReport = new ItemReport();
			//itemReport.setItemName(itemResult[0].toString());
			itemReport.setPurchaseCount(checkNull(itemResult[0]));
			itemReport.setSalesCount(checkNull(itemResult[1]));
			itemReport.setPurchaseRtnCount(checkNull(itemResult[2]));
			itemReport.setSalesRtnCount(checkNull(itemResult[3]));
			
			
			itemReportList.add(itemReport);
		}
		
		return itemReportList;
	}
	
}
