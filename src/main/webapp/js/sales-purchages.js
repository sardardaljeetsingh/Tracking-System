app.controller('PurchagesController', function($scope,$rootScope,$location,$http,ItemService,$filter,StockGrpSrvc,GenericSrvc) {

    $rootScope.items = [];
	
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
     GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.ledgers = response;
   });    
   
    $scope.stockGroups = [];
	StockGrpSrvc.getAllGroups($rootScope.company.id,function(response){
	  console.log(" StockGroup Response " + JSON.stringify(response));
	  $rootScope.stockGroups = response;
	  
		angular.forEach(response, function (group) {
			$scope.stockGroups[group.id] = group;
		});	  
		console.log($scope.stockGroups); 
   }); 

	$scope.showGrp = function(group,grpName){

		if(group.parent == 0){
			return grpName;
		}
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}
   
    $scope.curItems = [];
   	$scope.curItems[0] = {'quandity':1 ,'pices':1 };
	$scope.purchage = {};
	$scope.purchage.type = 1;
	$scope.purchage.voucher = "P"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	$scope.getTotal = function(){
		var total = 0;
		for(var i = 0; i < $scope.curItems.length; i++){
			var item = $scope.curItems[i];
			total += ( (+item.quandity) * (+item.pices) );
		}
		$scope.grandTotal = total;
		return total;
	}
   
   $scope.selectItems = function(){
	   $scope.showSplit = true;
   }
   
	$scope.purchanges = function(purchage){
		if(!$scope.purchagesform.$valid){
			$scope.submitclick = true;
			return;
		}
		
		purchage.item.itemDtls = $scope.curItems;
		var finalItemsDtls = [];
		purchage.transactionDetails = [];
		var count = 1;
		angular.forEach(purchage.item.itemDtls,function(itemTrans,index){
			if(itemTrans.quandity > 0 && itemTrans.pices > 0){
				itemTrans.name = purchage.item.name +"_" + purchage.item.shade + "_" + (count++) ;
				finalItemsDtls.push(itemTrans);
			}
		  
		});			
		purchage.item.itemDtls = finalItemsDtls;
		delete purchage.ledger['@id'];
		delete purchage.ledger.accGroup;
			var dataObj = JSON.stringify(purchage);
			console.log(dataObj);
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					//$rootScope.currentPage = 'userList';
					//$rootScope.users.push(user);
					
					 console.log(" Purchage Data : " + responseData);
					$scope.optStatus = 'Success';
					//$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });	
	}   
   
   
   
});

app.controller('SalesController', function($scope,$rootScope,$location,$http,ItemService,$filter,StockGrpSrvc,GenericSrvc) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
     GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.ledgers = response;
   });      
   
   $scope.stockGroups = [];
	StockGrpSrvc.getAllGroups($rootScope.company.id,function(response){
	  console.log(" StockGroup Response " + JSON.stringify(response));
	  $rootScope.stockGroups = response;
	  
		angular.forEach(response, function (group) {
			$scope.stockGroups[group.id] = group;
		});	  
		console.log($scope.stockGroups); 
   }); 

	$scope.showGrp = function(group,grpName){

		if(group.parent == 0){
			return grpName;
		}
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}	
	
   
	$scope.trasaction = {};
	$scope.trasaction.type = 2;
	$scope.trasaction.voucher = "S"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	$scope.invalidCount = false;
	$scope.getTotal = function(type){
		$scope.invalidCount = false;
		var total = 0;
		for(var i = 0; i < $scope.trasaction.item.itemDtls.length; i++){
			var item = $scope.trasaction.item.itemDtls[i];
			if(type==1){
				total += item.curqundty;
			}else{
				total += item.inputqundty;
				if(item.inputqundty > item.curqundty){
					$scope.invalidCount = true;
				}
			}
			
		}
		
		if(type==2)
		 $scope.grandTotal = total;
		
		return total;
	}
	
   $scope.getItemDetails = function(){
	   console.log(" Inside getItemDetails ");
	    ItemService.getItemDetails($scope.trasaction.item.id,function(response){
		  console.log(" getItemDetails Response " + JSON.stringify(response));
		  $rootScope.curItems = response;
			angular.forEach($rootScope.curItems,function(itemTrans,index){
			  itemTrans.inputqundty = 0;
			});			  
	   });
   }  	
   
   $scope.selectItems = function(){
	   $scope.showSplit = true;
   }  
   
   
	$scope.sales = function(sale){
		if(!$scope.salesform.$valid ){
			$scope.submitclick = true;
			return;
		}
           sale.quandity =  $scope.grandTotal;
		   var finalItemdtls = [];
			angular.forEach(sale.item.itemDtls,function(itemTrans,index){
				if(itemTrans.inputqundty > 0){
					itemTrans.curqundty = itemTrans.inputqundty ;
					finalItemdtls.push(itemTrans);
				}
			});	
		sale.item.itemDtls = finalItemdtls;
		sale.transactionDetails = [];
		     //console.log(sale.item.itemDtls);
		delete sale.ledger['@id'];
		delete sale.ledger.accGroup;
		
			var dataObj = JSON.stringify(sale);
			//console.log(dataObj);
			
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.optStatus = 'Success';
					//$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });		
	}   
   
   
   
});


app.controller('PurchaseReturnController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService,GenericSrvc,StockGrpSrvc,$filter) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
     GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.ledgers = response;
   });      
      
   
     $scope.transactions = [];
     GenericSrvc.getAll('/transactions/findAll-by-type/1',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.transactions = response;
   });   
   
     $rootScope.accgroups = [];
     AccGroupService.getAllGroupsByCompanyId($rootScope.company.id,function(response){
      console.log(" AccGroupService Response " + JSON.stringify(response));
	  $rootScope.accgroups = response;
	  $rootScope.curTab = 'companyTab';
   }); 
   
   $scope.selectItem = function(transaction){
	  console.log(" transaction Object " +   transaction.item);
	  if(transaction.item.id == null){
		  console.log(" transaction Object not found");
		  for(var i=$scope.transactions.length; i > 0; i--){
			  var tempTrans = $scope.transactions[i-1];
			  console.log( i+ "  "+ tempTrans);
				console.log("  @id " + tempTrans.item['@id']);
				if(tempTrans.item['@id'] == transaction.item){
					$scope.trasaction.voucher.item = tempTrans.item;
					transaction.item = tempTrans.item;
					break;
				}			  
		  }

	  }
	  
		angular.forEach($scope.trasaction.voucher.transactionDetails, function (transDetils) {
		   for(var j=0; j<transaction.item.itemDtls.length; j++ ){
			   var itemDel = transaction.item.itemDtls[j];
			 if(itemDel['@id'] == transDetils.itemDetails){
				 transDetils.curqundty = itemDel.curqundty;
				 transDetils.inputqundty = itemDel.curqundty;
				 
			 }
		   }
		});	  
	  
   }

   $scope.stockGroups = [];
	StockGrpSrvc.getAllGroups($rootScope.company.id,function(response){
	  console.log(" StockGroup Response " + JSON.stringify(response));
	  $rootScope.stockGroups = response;
	  
		angular.forEach(response, function (group) {
			$scope.stockGroups[group.id] = group;
		});	  
		console.log($scope.stockGroups); 
   }); 

	$scope.showGrp = function(group,grpName){

		if(group.parent == 0){
			return grpName;
		}
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}   
	
	
	$scope.invalidCount = false;
	$scope.getTotal = function(type){
		$scope.invalidCount = false;
		var total = 0;
		for(var i = 0; i < $scope.trasaction.voucher.transactionDetails.length; i++){
			var item = $scope.trasaction.voucher.transactionDetails[i];
			if(type==1){
				total += item.curqundty;
			}else{
				total += item.inputqundty;
				if(item.inputqundty > item.curqundty){
					$scope.invalidCount = true;
				}
			}
			
		}
		
		if(type==2)
		 $scope.grandTotal = total;
		else
		$scope.grandAvaTotal = total;
	
		return total;
	}	
   
	$scope.purchaseReturn = function(transaction){
		if(!$scope.purchagesform.$valid ){
			$scope.submitclick = true;
			return;
		}

		
           transaction.quandity =  $scope.grandTotal;
		   var finalItemdtls = [];
		   
		angular.forEach($scope.trasaction.voucher.transactionDetails, function (transDetils) {
		   for(var j=0; j<transaction.item.itemDtls.length; j++ ){
			   var itemDel = transaction.item.itemDtls[j];
			 if(itemDel['@id'] == transDetils.itemDetails && transDetils.inputqundty > 0){
				itemDel.curqundty = transDetils.inputqundty;
				finalItemdtls.push(itemDel);
			 }
		   }
		});	
			
		transaction.item.itemDtls = finalItemdtls;
		console.log(transaction.item.itemDtls);
		
		transaction.transactionDetails = [];
		transaction.desc = transaction.inputdesc;
		transaction.type = 3;
		transaction.voucher = "PR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);	
		delete transaction['@id'];
		delete transaction['id'];
		delete transaction.ledger['@id'];
		delete transaction.ledger.accGroup;
		//payment.voucher = 	payment.voucher.voucher		
		console.log(JSON.stringify(transaction));
		
		var dataObj = JSON.stringify(transaction);
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.optStatus = 'Success';
					//$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });	
	}   
   
   
   
});



app.controller('SaleReturnController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService,GenericSrvc,StockGrpSrvc,$filter) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
    $scope.ledgers = [];
     GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.ledgers = response;
   });      
     
   
     $scope.transactions = [];
     GenericSrvc.getAll('/transactions/findAll-by-type/2',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.transactions = response;
   });   
   
     $rootScope.accgroups = [];
     AccGroupService.getAllGroupsByCompanyId($rootScope.company.id,function(response){
      console.log(" AccGroupService Response " + JSON.stringify(response));
	  $rootScope.accgroups = response;
	  $rootScope.curTab = 'companyTab';
   }); 
   
   $scope.selectItem = function(transaction){
	  console.log(" transaction Object " +   transaction.item);
	  if(transaction.item.id == null){
		  console.log(" transaction Object not found");
		  for(var i=$scope.transactions.length; i > 0; i--){
			  var tempTrans = $scope.transactions[i-1];
			  console.log( i+ "  "+ tempTrans);
				console.log("  @id " + tempTrans.item['@id']);
				if(tempTrans.item['@id'] == transaction.item){
					$scope.trasaction.voucher.item = tempTrans.item;
					transaction.item = tempTrans.item;
					break;
				}			  
		  }

	  }
	  
		angular.forEach($scope.trasaction.voucher.transactionDetails, function (transDetils) {
			transDetils.inputqundty = transDetils.quandity;
		});	  
	  
   }

   $scope.stockGroups = [];
	StockGrpSrvc.getAllGroups($rootScope.company.id,function(response){
	  console.log(" StockGroup Response " + JSON.stringify(response));
	  $rootScope.stockGroups = response;
	  
		angular.forEach(response, function (group) {
			$scope.stockGroups[group.id] = group;
		});	  
		console.log($scope.stockGroups); 
   }); 

	$scope.showGrp = function(group,grpName){

		if(group.parent == 0){
			return grpName;
		}
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}   
	
	
	$scope.invalidCount = false;
	$scope.getTotal = function(type){
		$scope.invalidCount = false;
		var total = 0;
		for(var i = 0; i < $scope.trasaction.voucher.transactionDetails.length; i++){
			var item = $scope.trasaction.voucher.transactionDetails[i];
			if(type==1){
				total += item.curqundty;
			}else{
				total += item.inputqundty;
				if(item.inputqundty > item.quandity){
					$scope.invalidCount = true;
				}
			}
			
		}
		
		if(type==2)
		 $scope.grandTotal = total;
		else
		$scope.grandAvaTotal = total;
	
		return total;
	}	
   
	$scope.saleReturn = function(transaction){
		if(!$scope.purchagesform.$valid ){
			$scope.submitclick = true;
			return;
		}

		
           transaction.quandity =  $scope.grandTotal;
		   var finalItemdtls = [];
		   
		angular.forEach($scope.trasaction.voucher.transactionDetails, function (transDetils) {
		   for(var j=0; j<transaction.item.itemDtls.length; j++ ){
			   var itemDel = transaction.item.itemDtls[j];
			 if(itemDel['@id'] == transDetils.itemDetails && transDetils.inputqundty > 0){
				itemDel.curqundty = transDetils.inputqundty;
				finalItemdtls.push(itemDel);
			 }
		   }
		});	
			
		transaction.item.itemDtls = finalItemdtls;
		console.log(transaction.item.itemDtls);
		
		transaction.transactionDetails = [];
		transaction.desc = transaction.inputdesc;
		transaction.type = 4;
		transaction.voucher = "SR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);	
		delete transaction['@id'];
		delete transaction['id'];
		delete transaction.ledger['@id'];
		delete transaction.ledger.accGroup;
		//payment.voucher = 	payment.voucher.voucher		
		console.log(JSON.stringify(transaction));
		
		var dataObj = JSON.stringify(transaction);
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.optStatus = 'Success';
					//$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });	
	}   
   
   
   
});