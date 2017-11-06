app.controller('PurchagesController', function($scope,$rootScope,$location,$http,ItemService,$filter,StockGrpSrvc,GenericSrvc) {

    $rootScope.items = [];
	$scope.currency = $scope.company.currencesymbol;
	
	$scope.GSTapplicable = false;
	$scope.showSCGST = false;
	
	$scope.showIGST = false;
	
	ItemService.getAllItems($scope.company.id,function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
  
     
	 $scope.ledgers = [];
	 $scope.partyACNameForPurchase = [];
	 $scope.partyACNameForSales = [];
	 $scope.PurchaseACName = [];
	 $scope.SalesACName = [];
	 
	
	
	console.log(" Fetching Ledgers for Company Id : " + $scope.company.id );
	$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
	
	 
     //GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Ledger Trsaction Response " + JSON.stringify(response.data));
	  $scope.ledgers = response.data;
	 

	 
		
	 angular.forEach($scope.ledgers, function (ledger) {
		 	if(ledger.accGroup.parent == 5 || ledger.accGroup.parent == 6 ){
				$scope.partyACNameForSales.push(ledger);
				$scope.partyACNameForPurchase.push(ledger);
			} 
			if(ledger.accGroup.parent == 1 ){
				$scope.partyACNameForPurchase.push(ledger);
			}
			if(ledger.accGroup.parent == 2 ){
				$scope.partyACNameForSales.push(ledger);
			}
			if(ledger.accGroup.parent == 3 ){
				$scope.PurchaseACName.push(ledger);
			}
			if(ledger.accGroup.parent == 4 ){
				$scope.SalesACName.push(ledger);
			} 
		});
		
	
	  
	  
   });  

    $scope.agents = [];
     GenericSrvc.getAll('/agent/findAll',function(response){
      console.log(" Agents Trsaction Response " + JSON.stringify(response));
	  $scope.agents = response;
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

	//changes on 9/29
	$scope.showGrp = function(group,grpName){
		if(group.parent == 1){
			return grpName + " " + group.name;
		} 
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}
   
   $scope.purchaseTotal = function(purchage){
	    var totalTransQuandity = 0;
		var totalTransPieces = 0;
		var totalTransPrice = 0;
		var validQuandity = true;
		angular.forEach(purchage.trasactionItems,function(curTrasItem,index){
			var total = 0;
			
			angular.forEach(curTrasItem.curItems,function(item,index){
				total += ( (+item.quandity) * (+item.pices) );
				totalTransPieces += (+item.pices);
			
			});
			if(total != (+curTrasItem.quandity)){
			 	validQuandity = false;
			}
			totalTransQuandity += (+curTrasItem.quandity);
			
			if($rootScope.returnType == 'TR_P'){
				totalTransPrice += (+curTrasItem.quandity) * (+curTrasItem.purcrate);
			} else {
				totalTransPrice += (+curTrasItem.quandity) * (+curTrasItem.rate);
			}
		
		});
	
	  purchage.totalPieces = isNaN(totalTransPieces)? 0: totalTransPieces;	
	  purchage.quandity = isNaN(totalTransQuandity)? 0: totalTransQuandity;
	  purchage.rate = isNaN(totalTransPrice)? 0: totalTransPrice ;
	  console.log(isNaN(totalTransPrice) +"  "+ totalTransPrice);
	  console.log("pieces --> " + isNaN(totalTransPieces) +"  "+ totalTransPieces);
	  purchage.validQuandity = validQuandity;
	}
   
   $scope.getTotal = function(curTrasItem){
		var total = 0;
		for(var i = 0; i < curTrasItem.curItems.length; i++){
			var item = curTrasItem.curItems[i];
			total += ( (+item.quandity) * (+item.pices) );
		}
		curTrasItem.grandTotal = total;
		return total;
	}
	
	$scope.purchage = {};
	$scope.curItems = [];
	$scope.curItems[0] = {'quandity':0,'pices':0 };
	
	
		
	//code added for using this js for both purchases and sales return
	if($rootScope.returnType == 'TR_P') {
		$scope.purchage.type = 1;
		$scope.purchage.voucher = "P"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	} else {
		$scope.purchage.type = 4;
		$scope.purchage.voucher = "SR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	}
	
	$scope.purchage.trasactionItems = [];
	$scope.purchage.trasactionItems.push({});
	
	  $scope.purchage.inputTrnsDate = new Date(
		 $rootScope.transDate.getFullYear(),
		 $rootScope.transDate.getMonth(),
		 $rootScope.transDate.getDate()
	  );	
  
  
     
  
   $scope.removeItem = function(index,purchage){
	   purchage.trasactionItems.splice(index, 1);
	   if(purchage.trasactionItems.length == 0){
		  $scope.purchage.trasactionItems.push({}); 
	   }
   } 
   $scope.addItem = function(purchage){
	   angular.forEach(purchage.trasactionItems,function(transItem,index1){
		   transItem.showSplit=false;
		});  
		purchage.trasactionItems.push({})		
   }
   //purchage.trasactionItems.push({})
   
	$scope.purchanges = function(purchage){
		
		if(!($scope.purchagesform.$valid && purchage.validQuandity)){
			$scope.submitclick = true;
			return;
		} 
		
		angular.forEach(purchage.trasactionItems,function(transItem,index1){
			transItem.transactionDetails = [];
			var finalItemsDtls = [];
			var count = 1;
			angular.forEach(transItem.curItems,function(itemTrans,index2){
				if(itemTrans.quandity > 0 && itemTrans.pices > 0){
					//itemTrans.name = transItem.item.name +"_" + transItem.item.shade + "_" + index1 +"_"+(count++) ;
					//finalItemsDtls.push(itemTrans);
					  for(i=0;i<itemTrans.pices;i++){
						  var itemDtl = {};
						  itemDtl.name = transItem.item.name +"_" + transItem.item.shade + "_" + index2  + "_" + (i+1) ;
						  itemDtl.quandity = itemTrans.quandity ;
						  itemDtl.curqundty = itemTrans.quandity ;
						  
						  itemDtl.createdUser = $rootScope.loggedUser.username;
						  itemDtl.createdDate = new Date();
						  itemDtl.modifiedUser = $rootScope.loggedUser.username;
						  itemDtl.modifiedDate = new Date();
											  
						  itemDtl.pices =1;
						  itemDtl.curpices =1;
						  finalItemsDtls.push(itemDtl);
					  }					
					
				}
			  
			});
            transItem.item.itemDtls = finalItemsDtls;
		
			transItem.createdUser = $rootScope.loggedUser.username;
			transItem.createdDate = new Date();
			transItem.modifiedUser = $rootScope.loggedUser.username;
			transItem.modifiedDate = new Date();
			
			transItem.item.modifiedUser = $rootScope.loggedUser.username;
			transItem.item.modifiedDate = new Date();
				
			
			transItem.item.itemDtls.createdUser = $rootScope.loggedUser.username;
			transItem.item.itemDtls.createdDate = new Date();
			transItem.item.itemDtls.modifiedUser = $rootScope.loggedUser.username;
			transItem.item.itemDtls.modifiedDate = new Date();
			
			console.log("transItem.item.itemDtls------> "+ transItem.item.itemDtls);
			delete transItem.item['@id'];	
			delete transItem.item.stockGroup['@id'];				
		});
		
		delete purchage.ledger['@id'];
		delete purchage.ledger.accGroup;
        delete purchage.fromledger['@id'];
	    delete purchage.fromledger.accGroup;	
        
		purchage.transdate	= $filter('date')($scope.purchage.inputTrnsDate,'MM/dd/yyyy');	
		
		purchage.createdUser = $rootScope.loggedUser.username;
		purchage.createdDate = new Date();
		purchage.modifiedUser = $rootScope.loggedUser.username;
		purchage.modifiedDate = new Date();
		
		
		
		//console.log(purchage.trasactionItems);
			var dataObj = JSON.stringify(purchage);
			console.log("Purchase details tobe stored --> " + dataObj);
			
			//return;
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log("Data stored success --------> " + JSON.stringify(responseData));
					//$rootScope.currentPage = 'userList';
					//$rootScope.users.push(user);
					
					//console.log(" Purchage Data : " + responseData);
					$scope.optStatus = 'Success';
					
					$scope.curItems = [];
					$scope.curItems[0] = {'quandity':1 ,'pices':1 };
					$scope.purchage = {};
					
						
						$scope.GSTapplicable = false;
						$scope.showSCGST = false;
						$scope.showIGST = false;
						$rootScope.transType = '';
						$scope.purchagesform.$setPristine();
					
						
					if($scope.returnType == 'TR_P') {
						alert("Purchase order created successfully.");
					} else {
						alert("Sales return order created successfully.");
					}
					//$scope.agentform.$setPristine();
					$location.path("/perform-action");
				
					
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
   
   
   

	$scope.cancelPurchase = function(){
		$rootScope.currentPage ='performAction';
		//console.log("  Cancel Agent");
		$location.path("perform-action");
	}	
	
	//changes on 9/29
	$scope.callGST = function(purchaseForm){
		//if(purchaseForm.fromledger != null && purchaseForm.fromledger.accGroup.parent == 1){
		if(purchaseForm.fromledger != null){
			$scope.GSTapplicable = true;
			if($scope.company.state == purchaseForm.fromledger.mailingstate){
				$scope.showSCGST = true;
				$scope.showIGST = false;
			} else {
				$scope.showSCGST = false;
				$scope.showIGST = true;
			}
		} else {
			$scope.GSTapplicable = false;
			$scope.showSCGST = false;
			$scope.showIGST = false;
		}
		
	}
	
	
	
	//added below methods for datepicker
	
	 // Disable weekend selection
	  function disabled(data) {
			var date = data.date,
			mode = data.mode;
			return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
		
	 $scope.dateOptions = {
		dateDisabled: disabled,
		formatYear: 'yy',
		maxDate: new Date(new Date().getFullYear()+1, 2, 31),
		minDate: new Date(new Date().getFullYear(),3,1),
		startingDay: 1
	};
	
	 $scope.format = 'dd/MM/yyyy';
	 
	 $scope.open1 = function() {
		$scope.popup1.opened = true;
	};

	$scope.open2 = function() {
		$scope.popup2.opened = true;
	};
	
	$scope.popup1 = {
		opened: false
	};

	$scope.popup2 = {
		opened: false
	};
	
	$scope.invalidDate = false;
	$scope.checkDate = function(dateSel) {
		
		try {
			
			var lgnDate = new Date(dateSel);		
		
			var lgDate = lgnDate.getDate() +"/"+ (lgnDate.getMonth()+1) +"/"+ lgnDate.getFullYear();
	
			var pattern = /[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}/i;
		
			/*if(!pattern.test(lgDate)) {
				alert("Invalid date format ");
				dateSel = '';
				return;
			} */
		} catch (err) {
			alert("Error --> Invalid date format ");
			$scope.invalidDate = true;
			console.log("Date parse error --> " + JSON.stringify(err));
			return;
		  }	
		
		var cmpyearStart1 = new Date($scope.company.yearstart);
		var cmpBookStart1 = new Date($scope.company.booksstart);
			
		var cmpyearStart = cmpyearStart1.getDate() +"/"+ (cmpyearStart1.getMonth()+1) +"/"+ cmpyearStart1.getFullYear();
		var cmpBookStart = cmpBookStart1.getDate() +"/"+ (cmpBookStart1.getMonth()+1) +"/"+ cmpBookStart1.getFullYear();
			
		if (!(cmpBookStart1 <= lgnDate)) {
			alert("Selected date of " + lgDate + " cannot be prior to Company book start date of " + cmpBookStart);
			$scope.invalidDate = true;
			return;
		}
	
	}
	
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.purchagesform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});

	
});

app.controller('SalesController', function($scope,$rootScope,$location,$http,ItemService,$filter,StockGrpSrvc,GenericSrvc) {

    $rootScope.items = [];
	$scope.currency = $scope.company.currencesymbol;

	$scope.GSTapplicable = false;
	$scope.showSCGST = false;
	$scope.showIGST = false;
	
	ItemService.getAllItems($scope.company.id,function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
    $scope.ledgers = [];
	 $scope.partyACNameForPurchase = [];
	 $scope.partyACNameForSales = [];
	 $scope.PurchaseACName = [];
	 $scope.SalesACName = [];
	 
    console.log(" Fetching Ledgers for Company Id : " + $scope.company.id );
	$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
	
	 
     //GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Ledger Trsaction Response " + JSON.stringify(response.data));
	  $scope.ledgers = response.data;
   
   
		angular.forEach($scope.ledgers, function (ledger) {
		 	if(ledger.accGroup.parent == 5 || ledger.accGroup.parent == 6 ){
				$scope.partyACNameForSales.push(ledger);
				$scope.partyACNameForPurchase.push(ledger);
			} 
			if(ledger.accGroup.parent == 1 ){
				$scope.partyACNameForPurchase.push(ledger);
			}
			if(ledger.accGroup.parent == 2 ){
				$scope.partyACNameForSales.push(ledger);
			}
			if(ledger.accGroup.parent == 3 ){
				$scope.PurchaseACName.push(ledger);
			}
			if(ledger.accGroup.parent == 4 ){
				$scope.SalesACName.push(ledger);
			} 
		});
   
   
   
   
   
   });   

    $scope.agents = [];
     GenericSrvc.getAll('/agent/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.agents = response;
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

	/*
	$scope.showGrp = function(group,grpName){

		if(group.parent == 0){
			return grpName;
		}
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}*/
	//changes on 9/29
	$scope.showGrp = function(group,grpName){
		if(group.parent == 1){
			return grpName + " " + group.name;
		} 
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}
	
	
 
     $scope.curItems = [];
   	$scope.curItems[0] = {'quandity':1 ,'pices':1 };
	$scope.trasaction = {};
	
	if($rootScope.returnType == 'TR_S') {
		$scope.trasaction.type = 2;
		$scope.trasaction.voucher = "S"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	} else {
		$scope.trasaction.type = 3;
		$scope.trasaction.voucher = "PR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
	}
	
	$scope.trasaction.trasactionItems = [];
	$scope.trasaction.trasactionItems.push({});
	
	  $scope.trasaction.inputTrnsDate = new Date(
		 $rootScope.transDate.getFullYear(),
		 $rootScope.transDate.getMonth(),
		 $rootScope.transDate.getDate()
	  );	
	
	$scope.invalidCount = false;
	$scope.getTotal = function(curTrasItem,type){
		var total = 0;
		for(var i = 0; i < curTrasItem.item.itemDtls.length; i++){
			var item = curTrasItem.item.itemDtls[i];
			//total += ( (+item.quandity) * (+item.pices) );
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
		 curTrasItem.grandTotal = total;
	 
		return total;
	}
	
	$scope.purchaseTotal = function(trasaction){
		var totalTransQuandity = 0;
		var totalTransPieces = 0;
		var totalTransPrice = 0;
		var validQuandity = true;
		angular.forEach(trasaction.trasactionItems,function(curTrasItem,index){
			var total = 0;
			if(curTrasItem.item!= null && curTrasItem.item.itemDtls != null ){
				angular.forEach(curTrasItem.item.itemDtls,function(item,index){
					total += ( (+item.inputqundty) * (+item.pices) );
					totalTransPieces += (+item.pices);
				});
				if(total != (+curTrasItem.quandity)){
					validQuandity = false;
				}
				totalTransQuandity += (+total);
				if($rootScope.returnType == 'TR_S'){
					totalTransPrice += (+total) * (+curTrasItem.rate);
				} else {
					totalTransPrice += (+total) * (+curTrasItem.purcrate);
				}
			}
		});
	  trasaction.totalPieces = isNaN(totalTransPieces)? 0: totalTransPieces;		
      trasaction.quandity = isNaN(totalTransQuandity)? 0: totalTransQuandity;
	  trasaction.rate = isNaN(totalTransPrice)? 0: totalTransPrice ;
	  console.log(isNaN(totalTransPrice) +"  "+ totalTransPrice);
	  trasaction.validQuandity = validQuandity;
	}	
   
   $scope.removeItem = function(index,trasaction){
	   trasaction.trasactionItems.splice(index, 1);
	   if(trasaction.trasactionItems.length == 0){
		  $scope.trasaction.trasactionItems.push({}); 
	   }
   }   
   
   $scope.addItem = function(trasaction){
	   angular.forEach(trasaction.trasactionItems,function(transItem,index1){
		   transItem.showSplit=false;
		});  
		trasaction.trasactionItems.push({})		
   }   
   
	$scope.sales = function(sale){
		if(! ( $scope.salesform.$valid && sale.validQuandity) ){
			$scope.submitclick = true;
			return;
		}
        /*   sale.quandity =  $scope.grandTotal;
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
        delete sale.fromledger['@id'];
	    delete sale.fromledger.accGroup;		
		
		sale.transdate	= $filter('date')($scope.trasaction.transdate,'MM/dd/yyyy');*/

		angular.forEach(sale.trasactionItems,function(transItem,index1){
			transItem.transactionDetails = [];
			var finalItemsDtls = [];
			var count = 1;
			
			angular.forEach(transItem.item.itemDtls,function(itemTrans,index2){
				if(itemTrans.inputqundty > 0){
					itemTrans.curqundty = itemTrans.inputqundty ;
					
					delete itemTrans.item;	
					delete itemTrans['@id'];
					finalItemsDtls.push(itemTrans);
					
				}
			  
			});
            transItem.item.itemDtls = finalItemsDtls;
	
			//code added on 10/6 for audit purpose
			transItem.createdUser = $rootScope.loggedUser.username;
			transItem.createdDate = new Date();
			transItem.modifiedUser = $rootScope.loggedUser.username;
			transItem.modifiedDate = new Date();
			
			transItem.item.modifiedUser = $rootScope.loggedUser.username;
			transItem.item.modifiedDate = new Date();
			
	
	

			console.log(transItem.item.itemDtls);
			delete transItem.item['@id'];	
			delete transItem.item.stockGroup['@id'];				

//...................................................................................................
		    	
			
			//transItem.item.itemDtls.createdUser = $rootScope.loggedUser.username;
			//transItem.item.itemDtls.createdDate = new Date();
			//transItem.item.itemDtls.modifiedUser = $rootScope.loggedUser.username;
			//transItem.item.itemDtls.modifiedDate = new Date();
			
		
	






//....................................................................................................			







	});
		
		delete sale.ledger['@id'];
		delete sale.ledger.accGroup;
        delete sale.fromledger['@id'];
	    delete sale.fromledger.accGroup;	
        sale.transdate	= $filter('date')(sale.inputTrnsDate,'MM/dd/yyyy');	
		
		sale.createdUser = $rootScope.loggedUser.username;
		sale.createdDate = new Date();
		sale.modifiedUser = $rootScope.loggedUser.username;
		sale.modifiedDate = new Date();
	
		
		
		
		
			var dataObj = JSON.stringify(sale);
			console.log("Sales data tobe stored ----> " + dataObj); 
				

		//console.log(dataObj);
			
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log("Sales success ----------> " + JSON.stringify(responseData));
					//$scope.optStatus = 'Success';
					//$location.path("/show-user");
					
					 //$scope.curItems = [];
					//$scope.curItems[0] = {'quandity':1 ,'pices':1 };
					//$scope.trasaction = {};
					
					
					//$scope.trasaction.type = 2;
					//$scope.trasaction.voucher = "P"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
					
					/*
					
					
					if($rootScope.returnType == 'TR_S') {
						$scope.trasaction.type = 2;
						$scope.trasaction.voucher = "S"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
					} else {
						$scope.trasaction.type = 3;
						$scope.trasaction.voucher = "PR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);
					}
					
					$scope.trasaction.trasactionItems = [];
					$scope.trasaction.trasactionItems.push({});
					
					  $scope.trasaction.inputTrnsDate = new Date(
						 $rootScope.transDate.getFullYear(),
						 $rootScope.transDate.getMonth(),
						 $rootScope.transDate.getDate()
					  );	

					   ItemService.getAllItems($scope.company.id,function(response){
						  $rootScope.items = response;
					   });					  
					
					 GenericSrvc.getAll('/ledger/findAll',function(response){
							$scope.ledgers = response;
						});

					*/
					
					//$scope.GSTapplicable = false;
					//$scope.showSCGST = false;
					//$scope.showIGST = false;
					$scope.salesform.$setPristine();

					
					
					if($scope.returnType == 'TR_S') {
						alert("Sales order created successfully.");
					} else {
						alert("Purchase return order created successfully.");
					}
					
					//$scope.agentform.$setPristine();
					$location.path("/perform-action");
				
					
				  } catch (err) {
					alert("Sales Error ----------> " + JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log("Sales Error ----------> " + JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });		
	}   
   
    /*
	$scope.selectedSGenres = ['Sundry Debtors','Cash','Bank'];

    $scope.filterForSales = function(option) {
		return ($scope.selectedSGenres.indexOf(option.accGroup.name) !== -1);
    };

   $scope.selectedPRGenres = ['Sundry Creditors','Cash','Bank'];

    $scope.filterForPurchases = function(option) {
		return ($scope.selectedPRGenres.indexOf(option.accGroup.name) !== -1);
    };*/

	
	$scope.cancelSale = function(){
		$rootScope.currentPage ='performAction';
		//console.log("  Cancel Agent");
		$location.path("perform-action");
	}	
	
	
	//changes on 9/29
	$scope.callGST = function(purchaseForm){
		//if(purchaseForm.fromledger != null && purchaseForm.fromledger.accGroup.parent == 2){
		if(purchaseForm.fromledger != null){	
			$scope.GSTapplicable = true;
			if($scope.company.state == purchaseForm.fromledger.mailingstate){
				$scope.showSCGST = true;
				$scope.showIGST = false;
			} else {
				$scope.showSCGST = false;
				$scope.showIGST = true;
			}
		} else {
			$scope.GSTapplicable = false;
			$scope.showSCGST = false;
			$scope.showIGST = false;
		}
		
	}
	
	/*
	$scope.fillData = function(currentItem){
		
		if(currentItem != null && currentItem != undefined) {
			$scope.itemHSN = currentItem.itemHSN;
			$scope.shade = currentItem.shade;
			$scope.curqundty = currentItem.curqundty;
			$scope.stockGroup = $scope.showGrp($scope.stockGroups[currentItem.stockGroup],'');
		} else {
			$scope.itemHSN = '';
			$scope.shade = '';
			$scope.curqundty = '';
			$scope.stockGroup = '';
		
		}
		
	}*/

	
	//added below methods for datepicker
	
	 // Disable weekend selection
	  function disabled(data) {
			var date = data.date,
			mode = data.mode;
			return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
		
	 $scope.dateOptions = {
		dateDisabled: disabled,
		formatYear: 'yy',
		maxDate: new Date(new Date().getFullYear()+1, 2, 31),
		minDate: new Date(new Date().getFullYear(),3,1),
		startingDay: 1
	};
	
	 $scope.format = 'dd/MM/yyyy';
	 
	 $scope.open1 = function() {
		$scope.popup1.opened = true;
	};

	$scope.open2 = function() {
		$scope.popup2.opened = true;
	};
	
	$scope.popup1 = {
		opened: false
	};

	$scope.popup2 = {
		opened: false
	};
	
	$scope.invalidDate = false;
	$scope.checkDate = function(dateSel) {
		
		try {
			
			var lgnDate = new Date(dateSel);		
		
			var lgDate = lgnDate.getDate() +"/"+ (lgnDate.getMonth()+1) +"/"+ lgnDate.getFullYear();
	
			var pattern = /[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}/i;
		
			/*if(!pattern.test(lgDate)) {
				alert("Invalid date format ");
				dateSel = '';
				return;
			} */
		} catch (err) {
			alert("Error --> Invalid date format ");
			$scope.invalidDate = true;
			console.log("Date parse error --> " + JSON.stringify(err));
			return;
		  }	
		
		var cmpyearStart1 = new Date($scope.company.yearstart);
		var cmpBookStart1 = new Date($scope.company.booksstart);
			
		var cmpyearStart = cmpyearStart1.getDate() +"/"+ (cmpyearStart1.getMonth()+1) +"/"+ cmpyearStart1.getFullYear();
		var cmpBookStart = cmpBookStart1.getDate() +"/"+ (cmpBookStart1.getMonth()+1) +"/"+ cmpBookStart1.getFullYear();
			
		if (!(cmpBookStart1 <= lgnDate)) {
			alert("Selected date of " + lgDate + " cannot be prior to Company book start date of " + cmpBookStart);
			$scope.invalidDate = true;
			return;
		}
	
	}
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.salesform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});

   
});


app.controller('PurchaseReturnController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService,GenericSrvc,StockGrpSrvc,$filter) {

   $scope.trasaction ={};
   $scope.trasaction.inputTrnsDate = new Date(
	 $rootScope.transDate.getFullYear(),
	 $rootScope.transDate.getMonth(),
	 $rootScope.transDate.getDate()
  );

    $rootScope.items = [];
     ItemService.getAllItems($scope.company.id,function(response){
      //console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
     GenericSrvc.getAll('/ledger/findAll',function(response){
      //console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.ledgers = response;
   });      
      
   
     $scope.transactions = [];
     GenericSrvc.getAll('/transactions/findAll-by-type/'+ $scope.company.id + '/1',function(response){
      //console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.transactions = response;
	  
	  
		  var itemArray = [];
		  angular.forEach($scope.transactions, function (transaction) {
			  console.log(" Trsaction Response BF ---> " + JSON.stringify(response));
			  angular.forEach(transaction.trasactionItems, function (transItem) {
				  //console.log('transItem.item ' + transItem.item );	
				  if(transItem.item != null && transItem.item.id != null){
					  itemArray[transItem.item["@id"]] = transItem.item;
				  }

				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					 
					  //console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item);
					  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id != null){
						  itemArray[transDetils.itemDetails.item["@id"]] = transDetils.itemDetails.item;
					  }		 
				 });	
			 });	
        });
		
		  angular.forEach($scope.transactions, function (transaction) {		
			angular.forEach(transaction.trasactionItems, function (transItem) {
				  if(transItem.item != null && transItem.item.id == null){
					  transItem.item = itemArray[transItem.item];
				  }
				 // console.log('transItem.item ' + transItem.item +' transItem.item.id '+ transItem.item.id);
				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id == null){
						  transDetils.itemDetails.item = itemArray[transDetils.itemDetails.item];
					  }		 
				 });
				// console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item +' transDetils.itemDetails.item.id '+ transDetils.itemDetails.item.id);		 
			 });	  
	    });
		  console.log(" Trsaction Response AF---> " + JSON.stringify(response));
	  
   });   
   
   
     $rootScope.accgroups = [];
     AccGroupService.getAllGroupsByCompanyId($rootScope.company.id,function(response){
      console.log(" AccGroupService Response " + JSON.stringify(response));
	  $rootScope.accgroups = response;
	  $rootScope.curTab = 'companyTab';
   }); 
   
   $scope.selectItem = function(transaction){
	   
	   angular.forEach(transaction.trasactionItems, function (transItem) {
		    transItem.showSplit=true;
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
	$scope.getTotal = function(curTrasItem,type){
		$scope.invalidCount = false;
		var total = 0;
		for(var i = 0; i < curTrasItem.transactionDetails.length; i++){
			var item = curTrasItem.transactionDetails[i];
			if(type==1){
				total += item.itemDetails.curqundty;
			}else{
				total += item.itemDetails.inputqundty;
				if(item.itemDetails.inputqundty > item.itemDetails.curqundty){
					$scope.invalidCount = true;
				}
			}
			
		}
		
		if(type==2)
		 curTrasItem.grandTotal = total;
	
		return total;
	}	
	
	$scope.purchaseTotal = function(trasaction){
		var totalTransQuandity = 0;
		var totalTransPrice = 0;
		var validQuandity = true;
		if(trasaction == null || trasaction.trasactionItems == null){
			return;
		}
		//console.log('purchaseTotal '+ trasaction.trasactionItems);
		angular.forEach(trasaction.trasactionItems,function(curTrasItem,index){
			var total = 0;
			//console.log('curTrasItem ' + curTrasItem );
			//console.log('curTrasItem.transactionDetails.length ' + curTrasItem.transactionDetails.length );
			
			for(var i = 0; i < curTrasItem.transactionDetails.length; i++){
				var item = curTrasItem.transactionDetails[i];
				//console.log('item ' + JSON.stringify(item));
				if(item!= null && item.itemDetails != null ){
					total += ( (+item.itemDetails.inputqundty) * (+item.itemDetails.pices) );
					if((+item.itemDetails.inputqundty) > (+item.itemDetails.curqundty) ){
						validQuandity = false;
					}
                }
			}
				totalTransQuandity += (+total);
				totalTransPrice += (+total) * (+curTrasItem.purcrate);

		});
      trasaction.quandity = isNaN(totalTransQuandity)? 0: totalTransQuandity;
	  trasaction.rate = isNaN(totalTransPrice)? 0: totalTransPrice ;
	  //console.log(isNaN(totalTransPrice) +"  "+ totalTransPrice);
	  trasaction.validQuandity = validQuandity;
	}	
   
	$scope.purchaseReturn = function(transObj){
		if(! ( $scope.purchagesform.$valid && transObj.validQuandity) ){
			$scope.submitclick = true;
			return;
		}
		
		var finalTrasactionItems = [];
		angular.forEach(transObj.trasactionItems,function(transItem,index1){
			
			var finalItemsDtls = [];
			if(transItem.grandTotal > 0){
				angular.forEach(transItem.transactionDetails,function(transDtls,index2){
					if(transDtls.itemDetails.inputqundty > 0){
						transDtls.itemDetails.curqundty = transDtls.itemDetails.inputqundty ;
						delete transDtls.itemDetails.item;	
						delete transDtls.itemDetails['@id'];
						finalItemsDtls.push(transDtls.itemDetails);
					}
				  
				});
				transItem.item.itemDtls = finalItemsDtls;
				transItem.transactionDetails = [];
				transItem.quandity = transItem.grandTotal;
				console.log(transItem.item.itemDtls);
				delete transItem.item['@id'];	
				delete transItem.item.stockGroup['@id'];
				delete transItem['@id'];	
				delete transItem.id;
				delete transItem.transaction;
				finalTrasactionItems.push(transItem);				
			}
				
		});
		transObj.trasactionItems = finalTrasactionItems;
		delete transObj['@id'];	
		delete transObj.id;
		delete transObj.ledger['@id'];
		delete transObj.ledger.accGroup;
        delete transObj.fromledger['@id'];
	    delete transObj.fromledger.accGroup;	
        transObj.transdate = $filter('date')($scope.trasaction.inputTrnsDate,'MM/dd/yyyy');	
		//console.log(transObj.transdate +"   "+ transObj.inputTrnsDate); 
		transObj.type = 3;
		//transObj.voucher = "PR"+  $filter('date')(new Date(), 'MMddyy') + Math.round((Math.random() * 1000) * 1000);			
		
		console.log(transObj); 
		
		
			var dataObj = JSON.stringify(transObj);
			console.log("Purchase return data to be stored ----> " + dataObj);
			//return ;
			$http.post(hostname+'/transactions/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.optStatus = 'Success';

					
						 GenericSrvc.getAll('/transactions/findAll-by-type/'+ $scope.company.id + '/1',function(response){
						  //console.log(" Trsaction Response " + JSON.stringify(response));
						  $scope.transactions = response;
							  var itemArray = [];
							  angular.forEach($scope.transactions, function (transaction) {
								  
								  angular.forEach(transaction.trasactionItems, function (transItem) {
									  //console.log('transItem.item ' + transItem.item );	
									  if(transItem.item != null && transItem.item.id != null){
										  itemArray[transItem.item["@id"]] = transItem.item;
									  }

									 angular.forEach(transItem.transactionDetails, function (transDetils) {
										 
										  //console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item);
										  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id != null){
											  itemArray[transDetils.itemDetails.item["@id"]] = transDetils.itemDetails.item;
										  }		 
									 });	
								 });	
							});
							
							  angular.forEach($scope.transactions, function (transaction) {		
								angular.forEach(transaction.trasactionItems, function (transItem) {
									  if(transItem.item != null && transItem.item.id == null){
										  transItem.item = itemArray[transItem.item];
									  }
									 // console.log('transItem.item ' + transItem.item +' transItem.item.id '+ transItem.item.id);
									 angular.forEach(transItem.transactionDetails, function (transDetils) {
										  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id == null){
											  transDetils.itemDetails.item = itemArray[transDetils.itemDetails.item];
										  }		 
									 });
									// console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item +' transDetils.itemDetails.item.id '+ transDetils.itemDetails.item.id);		 
								 });	  
							});
						  
					   }); 					
					
					$scope.submitclick = false;
					$scope.trasaction.voucher = null;
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


   $scope.trasaction ={};
   $scope.trasaction.transdate = new Date();

   /*$scope.maxDate = new Date(
     $scope.myDate.getFullYear(),
     $scope.myDate.getMonth() + 2,
     $scope.myDate.getDate()
  );*/

   $scope.onlyWeekendsPredicate = function(date) {
    var day = date.getDay();
    return day === 0 || day === 6;
  };

    $rootScope.items = [];
     ItemService.getAllItems($scope.company.id,function(response){
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
     GenericSrvc.getAll('/transactions/findAll-by-type/'+ $scope.company.id + '/2',function(response){
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
		delete transaction.fromledger['@id'];
		delete transaction.fromledger.accGroup;	

		transaction.transdate	= $filter('date')($scope.trasaction.transdate,'MM/dd/yyyy');
		
		transaction.createdUser = $rootScope.loggedUser.username;
		transaction.createdDate = new Date();
		transaction.modifiedUser = $rootScope.loggedUser.username;
		transaction.modifiedDate = new Date();
		
		
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


app.controller('EditTransactionController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService,GenericSrvc,StockGrpSrvc,$filter) {
	
	
	
  
   if($rootScope.transType == 1) {
      $scope.returnType = 'TR_P';
   } else {
		$scope.returnType = 'TR_SR';
   }
    
   
	 
   $rootScope.items = [];
	$scope.currency = $scope.company.currencesymbol;
	
	$scope.GSTapplicable = false;
	$scope.showSCGST = false;
	
	$scope.showIGST = false;
	
	ItemService.getAllItems($scope.company.id,function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
  
     
	 $scope.ledgers = [];
	 $scope.partyACNameForPurchase = [];
	 $scope.partyACNameForSales = [];
	 $scope.PurchaseACName = [];
	 $scope.SalesACName = [];
	 
	
	
	console.log(" Fetching Ledgers for Company Id : " + $scope.company.id );
	$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
	
	 
     //GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Ledger Trsaction Response " + JSON.stringify(response.data));
	  $scope.ledgers = response.data;
	 

	 
		
	 angular.forEach($scope.ledgers, function (ledger) {
		 	if(ledger.accGroup.parent == 5 || ledger.accGroup.parent == 6 ){
				$scope.partyACNameForSales.push(ledger);
				$scope.partyACNameForPurchase.push(ledger);
			} 
			if(ledger.accGroup.parent == 1 ){
				$scope.partyACNameForPurchase.push(ledger);
			}
			if(ledger.accGroup.parent == 2 ){
				$scope.partyACNameForSales.push(ledger);
			}
			if(ledger.accGroup.parent == 3 ){
				$scope.PurchaseACName.push(ledger);
			}
			if(ledger.accGroup.parent == 4 ){
				$scope.SalesACName.push(ledger);
			} 
		});
		
	
	  
	  
   });  

    $scope.agents = [];
     GenericSrvc.getAll('/agent/findAll',function(response){
      console.log(" Agents Trsaction Response " + JSON.stringify(response));
	  $scope.agents = response;
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

	//changes on 9/29
	$scope.showGrp = function(group,grpName){
		if(group.parent == 1){
			return grpName + " " + group.name;
		} 
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}
   
 
   
   $scope.purchaseTotal = function(purchage){
		
	
		
		var totalTransQuandity = 0;
		var totalTransPieces = 0;
		var totalTransPrice = 0;
		var validQuandity = true;
		
		if(purchage != null && purchage != undefined) {
			
		
			angular.forEach(purchage.trasactionItems,function(curTrasItem,index){
				var total = 0;
				//if(curTrasItem.item.name != null) {
					angular.forEach(curTrasItem.transactionDetails,function(item,index){
						if(item.itemDetails != null && item.itemDetails != undefined) {
							total += ( (+item.itemDetails.curqundty) * (+item.itemDetails.pices) );
							totalTransPieces += (+item.itemDetails.pices);
						}
					});
				//	alert("totalTransPieces " + totalTransPieces);
					//alert("curTrasItem.quandity " + curTrasItem.quandity);
					
					if(curTrasItem.quandity != null && curTrasItem.quandity != undefined) {
						if(total != (+curTrasItem.quandity)){
							validQuandity = false;
						}
						totalTransQuandity += (+curTrasItem.quandity);
						
							
						if($scope.returnType == 'TR_P'){
							totalTransPrice += (+curTrasItem.quandity) * (+curTrasItem.purcrate);
							//totalTransPrice += (+totalTransQuandity) * (curTrasItem.purcrate);
						} else {
							totalTransPrice += (curTrasItem.quandity) * (curTrasItem.rate);
							//totalTransPrice += (+totalTransQuandity) * (curTrasItem.rate);
						}
					}
				//}
			});
		}
	
	  purchage.totalPieces = isNaN(totalTransPieces)? 0: totalTransPieces;	
	  purchage.quandity = isNaN(totalTransQuandity)? 0: totalTransQuandity;
	  purchage.rate = isNaN(totalTransPrice)? 0: totalTransPrice ;
	  console.log(isNaN(totalTransPrice) +"  "+ totalTransPrice);
	  console.log("pieces --> " + isNaN(totalTransPieces) +"  "+ totalTransPieces);
	  purchage.validQuandity = validQuandity;
	}
   
   $scope.getTotal = function(curTrasItem){
		var total = 0;
		for(var i = 0; i < curTrasItem.curItems.length; i++){
			var item = curTrasItem.curItems[i];
			total += ( (+item.quandity) * (+item.pices) );
		}
		curTrasItem.grandTotal = total;
		return total;
	}
	
	
	
	$scope.getEditTotal = function(curTrasItem){
		var total = 0;
		
		if(curTrasItem != null && curTrasItem != undefined) {
			for(var i = 0; i < curTrasItem.transactionDetails.length; i++){
				var item = curTrasItem.transactionDetails[i];
				if(item.itemDetails != null && item.itemDetails != undefined) {
					total += ( (+item.itemDetails.curqundty) * (+item.itemDetails.pices) );
					//total  += item.itemDetails.quandity;
				}
			}
			curTrasItem.grandTotal = total;
		}		
		return total;
	}
   
   //changes on 9/29
	$scope.callGST = function(purchage){
	
		//if(purchaseForm.fromledger != null && purchaseForm.fromledger.accGroup.parent == 1){
		if(purchage.fromledger != null){
			$scope.GSTapplicable = true;
			if($scope.company.state == purchage.fromledger.mailingstate){
				$scope.showSCGST = true;
				$scope.showIGST = false;
			} else {
				$scope.showSCGST = false;
				$scope.showIGST = true;
			}
		} else {
			$scope.GSTapplicable = false;
			$scope.showSCGST = false;
			$scope.showIGST = false;
		}
		
	}
   
   $scope.selectTransaction =  function(purchage) {
	
		angular.forEach(purchage.trasactionItems, function (transItem) {
			transItem.showSplit=true;
		});
	
		 //console.log(" Edit Trsaction Response ---> " + JSON.stringify(purchage));
	}
   
   
   $scope.purchage = {};
	
      $http.get(hostname + '/transactions/find-by-id/'+ $rootScope.transId).then(function(response) {
	  
	  
	  console.log(" Trsaction Response from DB ----> " + JSON.stringify(response.data));
	 
	// $scope.purchages = response;
	  $scope.purchage = response.data
	  
		  var itemArray = [];
		  //angular.forEach($scope.purchages, function (transaction) {
			  //console.log(" Trsaction Response BF ---> " + JSON.stringify(response));
			  angular.forEach($scope.purchage.trasactionItems, function (transItem) {
				  //console.log('transItem.item ' + transItem.item );	
				  if(transItem.item != null && transItem.item.id != null){
					  itemArray[transItem.item["@id"]] = transItem.item;
				  }

				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					 
					  //console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item);
					 if(transDetils.itemDetails != null && transDetils.itemDetails != undefined) {
						if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id != null){
							itemArray[transDetils.itemDetails.item["@id"]] = transDetils.itemDetails.item;
						}
					 }						
				 });	
			 });	
        //});
		
		 // angular.forEach($scope.purchages, function (transaction) {		
			angular.forEach($scope.purchage.trasactionItems, function (transItem) {
				  if(transItem.item != null && transItem.item.id == null){
					  transItem.item = itemArray[transItem.item];
				  }
				 // console.log('transItem.item ' + transItem.item +' transItem.item.id '+ transItem.item.id);
				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					   if(transDetils.itemDetails != null && transDetils.itemDetails != undefined) {
							if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id == null){
								transDetils.itemDetails.item = itemArray[transDetils.itemDetails.item];
							}
					   }						
				 });
				// console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item +' transDetils.itemDetails.item.id '+ transDetils.itemDetails.item.id);		 
			 });	  
	    //});
		  console.log(" Trsaction Response AF---> " + JSON.stringify($scope.purchage));
	  
	  
	  	angular.forEach($scope.purchage.trasactionItems, function (transItem) {
			transItem.showSplit=true;
		});
	
	  $scope.callGST($scope.purchage);
	  $scope.purchaseTotal($scope.purchage);
      
	  var transDate = new Date($scope.purchage.transdate);
	  $scope.purchage.inputTrnsDate = new Date(transDate.getFullYear(),transDate.getMonth(),transDate.getDate());
	  
   }); 
   
   //$scope.selectTransaction($scope.purchage);
 
	
  
  //Edit flow -- setting form data ends here
  
  
   $scope.removeItem = function(index,purchage){
	   purchage.trasactionItems.splice(index, 1);
	   if(purchage.trasactionItems.length == 0){
		  $scope.purchage.trasactionItems.push({}); 
	   }
   } 
   $scope.addItem = function(purchage){
	   angular.forEach(purchage.trasactionItems,function(transItem,index1){
		   transItem.showSplit=false;
		});  
		purchage.trasactionItems.push({})		
   }
   //purchage.trasactionItems.push({})
   
	$scope.purchanges = function(purchage){
		//alert("purchage.validQuandity  1 -> " + purchage.validQuandity + " Form " + $scope.purchagesform.$valid);
		
		$scope.purchaseTotal(purchage);	
		
		if(!($scope.purchagesform.$valid && purchage.validQuandity)){
			$scope.submitclick = true;
			return;
		} 
		
		//alert("purchage.validQuandity  2-> " + purchage.validQuandity + " Form " + $scope.purchagesform.$valid);
	
			
		angular.forEach(purchage.trasactionItems,function(transItem,index1){
			//transItem.transactionDetails = [];
			//console.log("Getting into 1 ---------> " );
			
			if(transItem.item != null && transItem.item != undefined) {
			
				var finalItemsDtls = [];
				var count = 1;
				//angular.forEach(transItem.curItems,function(itemTrans,index2){
				angular.forEach(transItem.transactionDetails,function(itemTrans,index2){	
				
					if(itemTrans.itemDetails.quandity > 0 && itemTrans.itemDetails.pices > 0){
					
						//console.log("Getting into 2 ---------> " );
						//itemTrans.name = transItem.item.name +"_" + transItem.item.shade + "_" + index1 +"_"+(count++) ;
						//finalItemsDtls.push(itemTrans);
						  for(i=0;i<itemTrans.itemDetails.pices;i++){
							  
							  //console.log("adding quandity ---------> " + itemTrans.itemDetails.quandity);
							  var itemDtl = {};
							  
							  if(itemTrans.itemDetails.id == null || itemTrans.itemDetails.id == undefined){
									itemDtl.createdUser = $rootScope.loggedUser.username;
									itemDtl.createdDate = new Date();
							  } else {
								
								itemDtl.id = itemTrans.itemDetails.id;			
							  }	
								
								itemDtl.name = transItem.item.name +"_" + transItem.item.shade + "_" + index2  + "_" + (i+1) ;
								itemDtl.quandity = itemTrans.itemDetails.quandity ;
								itemDtl.curqundty = itemTrans.itemDetails.curqundty ;
							  
							  itemDtl.modifiedUser = $rootScope.loggedUser.username;
							  itemDtl.modifiedDate = new Date();
												  
							  itemDtl.pices =1;
							  itemDtl.curpices =1;
							  finalItemsDtls.push(itemDtl);
						  }					
						
					}
				  
				});

			
				transItem.item.itemDtls = finalItemsDtls;
				
				transItem.item.itemDtls.createdUser = $rootScope.loggedUser.username;
				transItem.item.itemDtls.createdDate = new Date();
				transItem.item.itemDtls.modifiedUser = $rootScope.loggedUser.username;
				transItem.item.itemDtls.modifiedDate = new Date();
				
				transItem.item.modifiedUser = $rootScope.loggedUser.username;
				transItem.item.modifiedDate = new Date();

				transItem.createdUser = $rootScope.loggedUser.username;
				transItem.createdDate = new Date();
				transItem.modifiedUser = $rootScope.loggedUser.username;
				transItem.modifiedDate = new Date();
				
				
					
				
				
				
				//console.log("transItem.item.itemDtls------> "+ transItem.item.itemDtls);
				//delete transItem['@id'];
				//delete transItem['id'];
				delete transItem.item['@id'];	
				delete transItem.item.stockGroup['@id'];				
			}
		});
		
		delete purchage.ledger['@id'];
		delete purchage.ledger.accGroup;
       delete purchage.fromledger['@id'];
	    delete purchage.fromledger.accGroup;	
		//delete purchage['@id'];
        
		purchage.transdate	= $filter('date')($scope.purchage.inputTrnsDate,'MM/dd/yyyy');	
		
		purchage.createdUser = $rootScope.loggedUser.username;
		purchage.createdDate = new Date();
		purchage.modifiedUser = $rootScope.loggedUser.username;
		purchage.modifiedDate = new Date();
		
		
		
		//console.log(purchage.trasactionItems);
			var dataObj = JSON.stringify(purchage);
			console.log("Purchase edit details tobe stored --> " + dataObj);
			
			//return;
			
			
			
			
			$http.post(hostname+'/transactions/update', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					  
					console.log("Success -- > " + JSON.stringify(responseData));
					//$rootScope.currentPage = 'userList';
					//$rootScope.users.push(user);
					
					 //console.log(" Purchage Data : " + responseData);
					$scope.optStatus = 'Success';
					
					$scope.curItems = [];
					$scope.curItems[0] = {'quandity':1 ,'pices':1 };
					$scope.purchage = {};
					
					
						
						$scope.GSTapplicable = false;
						$scope.showSCGST = false;
						$scope.showIGST = false;
						$rootScope.transType = '';
						$scope.purchagesform.$setPristine();
					
						
					if($scope.returnType == 'TR_P') {
						alert("Purchase order updated successfully.");
					} else {
						alert("Sales return order updated successfully.");
					}
					//$scope.agentform.$setPristine();
					$location.path("/perform-action");
				
					
					//$location.path("/show-user");
				  } catch (err) {
					alert("Error in Purchase Update ---> " + JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log("Error in Purchase Update --->  " + JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });	
	}   
   
   
   

	$scope.cancelPurchase = function(){
		$rootScope.currentPage ='performAction';
		//console.log("  Cancel Agent");
		$location.path("/showReport");
	}	
	
	
	
	//added below methods for datepicker
	
	 // Disable weekend selection
	  function disabled(data) {
			var date = data.date,
			mode = data.mode;
			return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
		
	 $scope.dateOptions = {
		dateDisabled: disabled,
		formatYear: 'yy',
		maxDate: new Date(new Date().getFullYear()+1, 2, 31),
		minDate: new Date(new Date().getFullYear(),3,1),
		startingDay: 1
	};
	
	 $scope.format = 'dd/MM/yyyy';
	 
	 $scope.open1 = function() {
		$scope.popup1.opened = true;
	};

	$scope.open2 = function() {
		$scope.popup2.opened = true;
	};
	
	$scope.popup1 = {
		opened: false
	};

	$scope.popup2 = {
		opened: false
	};
	
	$scope.invalidDate = false;
	$scope.checkDate = function(dateSel) {
		
		try {
			
			var lgnDate = new Date(dateSel);		
		
			var lgDate = lgnDate.getDate() +"/"+ (lgnDate.getMonth()+1) +"/"+ lgnDate.getFullYear();
	
			var pattern = /[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}/i;
		
			/*if(!pattern.test(lgDate)) {
				alert("Invalid date format ");
				dateSel = '';
				return;
			} */
		} catch (err) {
			alert("Error --> Invalid date format ");
			$scope.invalidDate = true;
			console.log("Date parse error --> " + JSON.stringify(err));
			return;
		  }	
		
		var cmpyearStart1 = new Date($scope.company.yearstart);
		var cmpBookStart1 = new Date($scope.company.booksstart);
			
		var cmpyearStart = cmpyearStart1.getDate() +"/"+ (cmpyearStart1.getMonth()+1) +"/"+ cmpyearStart1.getFullYear();
		var cmpBookStart = cmpBookStart1.getDate() +"/"+ (cmpBookStart1.getMonth()+1) +"/"+ cmpBookStart1.getFullYear();
			
		if (!(cmpBookStart1 <= lgnDate)) {
			alert("Selected date of " + lgDate + " cannot be prior to Company book start date of " + cmpBookStart);
			$scope.invalidDate = true;
			return;
		}
	
	}
	
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.purchagesform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});

	
   
});


app.controller('EditSalesTransactionController', function($scope,$rootScope,$location,$http,ItemService,$filter,StockGrpSrvc,GenericSrvc) {

  
   if($rootScope.transType == 2) {
      $scope.returnType = 'TR_S';
   } else {
		$scope.returnType = 'TR_PR';
   }   

   $rootScope.items = [];
	$scope.currency = $scope.company.currencesymbol;

	$scope.GSTapplicable = false;
	$scope.showSCGST = false;
	$scope.showIGST = false;
	
	ItemService.getAllItems($scope.company.id,function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
   $scope.ledgers = [];
    $scope.ledgers = [];
	 $scope.partyACNameForPurchase = [];
	 $scope.partyACNameForSales = [];
	 $scope.PurchaseACName = [];
	 $scope.SalesACName = [];
	 
    console.log(" Fetching Ledgers for Company Id : " + $scope.company.id );
	$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
	
	 
     //GenericSrvc.getAll('/ledger/findAll',function(response){
      console.log(" Ledger Trsaction Response " + JSON.stringify(response.data));
	  $scope.ledgers = response.data;
   
   
		angular.forEach($scope.ledgers, function (ledger) {
		 	if(ledger.accGroup.parent == 5 || ledger.accGroup.parent == 6 ){
				$scope.partyACNameForSales.push(ledger);
				$scope.partyACNameForPurchase.push(ledger);
			} 
			if(ledger.accGroup.parent == 1 ){
				$scope.partyACNameForPurchase.push(ledger);
			}
			if(ledger.accGroup.parent == 2 ){
				$scope.partyACNameForSales.push(ledger);
			}
			if(ledger.accGroup.parent == 3 ){
				$scope.PurchaseACName.push(ledger);
			}
			if(ledger.accGroup.parent == 4 ){
				$scope.SalesACName.push(ledger);
			} 
		});
   
   
   
   
   
   });   

    $scope.agents = [];
     GenericSrvc.getAll('/agent/findAll',function(response){
      console.log(" Trsaction Response " + JSON.stringify(response));
	  $scope.agents = response;
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

	
	//changes on 9/29
	$scope.showGrp = function(group,grpName){
		if(group.parent == 1){
			return grpName + " " + group.name;
		} 
		return $scope.showGrp($scope.stockGroups[group.parent],  group.name +" > "+ grpName );
	}
	
	
	$scope.invalidCount = false;
	$scope.getTotal = function(curTrasItem,type){
		var total = 0;
		for(var i = 0; i < curTrasItem.transactionDetails.length; i++){
			//var item = curTrasItem.item.itemDtls[i];
			var item = curTrasItem.transactionDetails[i];
			//total += ( (+item.quandity) * (+item.pices) );
			if(	item.itemDetails != null) {
				if(type==1){
					total += item.itemDetails.curqundty;
				}else{
					//total += item.itemDetails.inputqundty;
					total += item.quandity;
					if(item.itemDetails.inputqundty > item.itemDetails.curqundty){
						$scope.invalidCount = true;
					}
				}
			}
			
		}
		
		if(type==2)
		 curTrasItem.grandTotal = total;
	 
		return total;
	}
	
	
	
	$scope.purchaseTotal = function(trasaction){
		var totalTransQuandity = 0;
		var totalTransPieces = 0;
		var totalTransPrice = 0;
		var validQuandity = true;
		
		if(trasaction != null && trasaction != undefined) {
	
			angular.forEach(trasaction.trasactionItems,function(curTrasItem,index){
				var total = 0;
			
			if(curTrasItem.transactionDetails != null){
				
					angular.forEach(curTrasItem.transactionDetails,function(item,index){
						//total += ( (+item.itemDetails.inputqundty) * (+item.itemDetails.pices) );
						
						if(	item.itemDetails != null) {
							total += ( (+item.quandity) * (+item.itemDetails.pices) );
							totalTransPieces += (+item.itemDetails.pices);
						}
					
					});
					//alert("total  " + total);
					if(total != (+curTrasItem.quandity)){
						validQuandity = false;
					}
					totalTransQuandity += (+total);
					
					if($rootScope.returnType == 'TR_S'){
						totalTransPrice += (+total) * (+curTrasItem.rate);
					} else {
						totalTransPrice += (+total) * (+curTrasItem.purcrate);
					}
				}
			});
		 
		 
		  trasaction.totalPieces = isNaN(totalTransPieces)? 0: totalTransPieces;		
		  trasaction.quandity = isNaN(totalTransQuandity)? 0: totalTransQuandity;
		  trasaction.rate = isNaN(totalTransPrice)? 0: totalTransPrice ;
		 console.log(isNaN(totalTransPrice) +" ----------> "+ totalTransPrice + " ............ " + isNaN(totalTransQuandity) + " ---> " + totalTransQuandity);
		  trasaction.validQuandity = validQuandity;
		
			
		}	
	}
	
   
   //changes on 9/29
	$scope.callGST = function(purchaseForm){
		//if(purchaseForm.fromledger != null && purchaseForm.fromledger.accGroup.parent == 2){
		if(purchaseForm.fromledger != null){	
			$scope.GSTapplicable = true;
			if($scope.company.state == purchaseForm.fromledger.mailingstate){
				$scope.showSCGST = true;
				$scope.showIGST = false;
			} else {
				$scope.showSCGST = false;
				$scope.showIGST = true;
			}
		} else {
			$scope.GSTapplicable = false;
			$scope.showSCGST = false;
			$scope.showIGST = false;
		}
		
	}
   
   //$scope.transactions = [];
//	$scope.sales = [];
  //   GenericSrvc.getAll('/transactions/findAll-by-type/'+ $scope.company.id + '/' + $rootScope.transType,function(response){
    
    $scope.sale = {};
	
	$http.get(hostname + '/transactions/find-by-id/'+ $rootScope.transId).then(function(response) {
	  
	  
	  console.log(" Trsaction Response from DB ----> " + JSON.stringify(response.data));
	 
	// $scope.purchages = response;
	  $scope.sale = response.data

	 // $scope.sales = response;
	  
	  
		  var itemArray = [];
		  //angular.forEach($scope.sales, function (transaction) {
			//  console.log(" Trsaction Response BF ---> " + JSON.stringify(response));
			  angular.forEach($scope.sale.trasactionItems, function (transItem) {
				  //console.log('transItem.item ' + transItem.item );	
				  if(transItem.item != null && transItem.item.id != null){
					  itemArray[transItem.item["@id"]] = transItem.item;
				  }

				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					 if(transDetils.itemDetails != null) {
					  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id != null){
						  itemArray[transDetils.itemDetails.item["@id"]] = transDetils.itemDetails.item;
					  }		 
					 }
				 });	
			 });	
        //});
		
		  //angular.forEach($scope.sales, function (transaction) {		
			angular.forEach($scope.sale.trasactionItems, function (transItem) {
				  if(transItem.item != null && transItem.item.id == null){
					  transItem.item = itemArray[transItem.item];
				  }
				 // console.log('transItem.item ' + transItem.item +' transItem.item.id '+ transItem.item.id);
				 angular.forEach(transItem.transactionDetails, function (transDetils) {
					  if(transDetils.itemDetails != null) {
					  if(transDetils.itemDetails.item != null && transDetils.itemDetails.item.id == null){
						  transDetils.itemDetails.item = itemArray[transDetils.itemDetails.item];
					  }		 
					  }
				 });
				// console.log('transDetils.itemDetails.item ' + transDetils.itemDetails.item +' transDetils.itemDetails.item.id '+ transDetils.itemDetails.item.id);		 
			 });	  
	    //});
		 // console.log(" Trsaction Response AF---> " + JSON.stringify($scope.sale));
	  
	  
		angular.forEach($scope.sale.trasactionItems, function (transItem) {
			transItem.showSplit=true;
		});
	
		$scope.purchaseTotal($scope.sale);
		$scope.callGST($scope.sale);

   
     var transDate = new Date($scope.sale.transdate);
	  $scope.sale.inputTrnsDate = new Date(transDate.getFullYear(),transDate.getMonth(),transDate.getDate());
	
   
   
   
   }); 
   
   
   $scope.removeItem = function(index,trasaction){
	   trasaction.trasactionItems.splice(index, 1);
	   if(trasaction.trasactionItems.length == 0){
		  $scope.trasaction.trasactionItems.push({}); 
	   }
   }   
   
   $scope.addItem = function(trasaction){
	   angular.forEach(trasaction.trasactionItems,function(transItem,index1){
		   transItem.showSplit=false;
		});  
		trasaction.trasactionItems.push({})		
   }   
   
	$scope.sales = function(sale){
		if(! ( $scope.salesform.$valid && sale.validQuandity) ){
			$scope.submitclick = true;
			return;
		}
       

		/*angular.forEach(sale.trasactionItems,function(transItem,index1){
			transItem.transactionDetails = [];
			var finalItemsDtls = [];
			var count = 1;
			//angular.forEach(transItem.item.itemDtls,function(itemTrans,index2){
			angular.forEach(transItem.transactionDetails,function(itemTrans,index2){
				if(itemTrans.quandity > 0){
					itemTrans.itemDetails.curqundty = itemTrans.quandity ;
					//delete itemTrans.item;	
					delete itemTrans['@id'];
					//finalItemsDtls.push(itemTrans);
					
				}
			  
			});
            //transItem.item.itemDtls = finalItemsDtls;
	
			//code added on 10/6 for audit purpose
			//transItem.createdUser = $rootScope.loggedUser.username;
			//transItem.createdDate = new Date();
			transItem.modifiedUser = $rootScope.loggedUser.username;
			transItem.modifiedDate = new Date();
			
			//transItem.item.modifiedUser = $rootScope.loggedUser.username;
			//transItem.item.modifiedDate = new Date();
			
	
	

			//console.log(transItem.item.itemDtls);
			
			//delete transItem.item['@id'];	
			//delete transItem.item.stockGroup['@id'];				

//...................................................................................................
		    	
			
			//transItem.item.itemDtls.createdUser = $rootScope.loggedUser.username;
			//transItem.item.itemDtls.createdDate = new Date();
			//transItem.item.itemDtls.modifiedUser = $rootScope.loggedUser.username;
			//transItem.item.itemDtls.modifiedDate = new Date();
			
	

	});*/
		
		angular.forEach(sale.trasactionItems,function(transItem,index1){
			//transItem.transactionDetails = [];
			//console.log("Getting into 1 ---------> " );
			
			if(transItem.item != null && transItem.item != undefined) {
			
				var finalItemsDtls = [];
				var count = 1;
				//angular.forEach(transItem.curItems,function(itemTrans,index2){
				angular.forEach(transItem.transactionDetails,function(itemTrans,index2){	
				
					if(itemTrans.quandity > 0){
						alert("itemTrans.quandity ----> " + itemTrans.quandity);
					
						//console.log("Getting into 2 ---------> " );
						//itemTrans.name = transItem.item.name +"_" + transItem.item.shade + "_" + index1 +"_"+(count++) ;
						//finalItemsDtls.push(itemTrans);
						  for(i=0;i<itemTrans.itemDetails.pices;i++){
							  
							  //console.log("adding quandity ---------> " + itemTrans.itemDetails.quandity);
							  var itemDtl = {};
							  
							  if(itemTrans.itemDetails.id == null || itemTrans.itemDetails.id == undefined){
									itemDtl.createdUser = $rootScope.loggedUser.username;
									itemDtl.createdDate = new Date();
							  } else {
								
								itemDtl.id = itemTrans.itemDetails.id;			
							  }	
								
								//itemDtl.name = transItem.item.name +"_" + transItem.item.shade + "_" + index2  + "_" + (i+1) ;
								//itemDtl.quandity = itemTrans.quandity ;
								//itemDtl.curqundty = itemTrans.itemDetails.curqundty ;
								itemDtl.curqundty = itemTrans.quandity ;
								
							  
							  itemDtl.modifiedUser = $rootScope.loggedUser.username;
							  itemDtl.modifiedDate = new Date();
												  
							  itemDtl.pices =1;
							  itemDtl.curpices =1;
							  finalItemsDtls.push(itemDtl);
						  }					
						
					}
				  
				});

			
				transItem.item.itemDtls = finalItemsDtls;
				
				transItem.item.itemDtls.createdUser = $rootScope.loggedUser.username;
				transItem.item.itemDtls.createdDate = new Date();
				transItem.item.itemDtls.modifiedUser = $rootScope.loggedUser.username;
				transItem.item.itemDtls.modifiedDate = new Date();
				
				transItem.item.modifiedUser = $rootScope.loggedUser.username;
				transItem.item.modifiedDate = new Date();

				transItem.createdUser = $rootScope.loggedUser.username;
				transItem.createdDate = new Date();
				transItem.modifiedUser = $rootScope.loggedUser.username;
				transItem.modifiedDate = new Date();
				
				
					
				
				
				
				//console.log("transItem.item.itemDtls------> "+ transItem.item.itemDtls);
				//delete transItem['@id'];
				//delete transItem['id'];
				delete transItem.item['@id'];	
				delete transItem.item.stockGroup['@id'];				
			}
		});
		
		
		
		
		
		delete sale.ledger['@id'];
		delete sale.ledger.accGroup;
        delete sale.fromledger['@id'];
	    delete sale.fromledger.accGroup;	
        sale.transdate	= $filter('date')(sale.inputTrnsDate,'MM/dd/yyyy');	
		
		//sale.createdUser = $rootScope.loggedUser.username;
		//sale.createdDate = new Date();
		sale.modifiedUser = $rootScope.loggedUser.username;
		sale.modifiedDate = new Date();
	
		
		
		
		
			var dataObj = JSON.stringify(sale);
			console.log("Sales data tobe stored ----> " + dataObj); 
				

		//console.log(dataObj);
			
			$http.post(hostname+'/transactions/update', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log("Sales success ----------> " + JSON.stringify(responseData));
					
					$scope.salesform.$setPristine();

					
					
					if($scope.returnType == 'TR_S') {
						alert("Sales order updated successfully.");
					} else {
						alert("Purchase return order updated successfully.");
					}
					
					//$scope.agentform.$setPristine();
					$location.path("/perform-action");
				
					
				  } catch (err) {
					alert("Sales Error ----------> " + JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log("Sales Error ----------> " + JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });		
	}   
   
  
	
	$scope.cancelSale = function(){
		$rootScope.currentPage ='performAction';
		//console.log("  Cancel Agent");
		$location.path("perform-action");
	}	
	
	
	
	
	
	
	//added below methods for datepicker
	
	 // Disable weekend selection
	  function disabled(data) {
			var date = data.date,
			mode = data.mode;
			return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	  }
		
	 $scope.dateOptions = {
		dateDisabled: disabled,
		formatYear: 'yy',
		maxDate: new Date(new Date().getFullYear()+1, 2, 31),
		minDate: new Date(new Date().getFullYear(),3,1),
		startingDay: 1
	};
	
	 $scope.format = 'dd/MM/yyyy';
	 
	 $scope.open1 = function() {
		$scope.popup1.opened = true;
	};

	$scope.open2 = function() {
		$scope.popup2.opened = true;
	};
	
	$scope.popup1 = {
		opened: false
	};

	$scope.popup2 = {
		opened: false
	};
	
	$scope.invalidDate = false;
	$scope.checkDate = function(dateSel) {
		
		try {
			
			var lgnDate = new Date(dateSel);		
		
			var lgDate = lgnDate.getDate() +"/"+ (lgnDate.getMonth()+1) +"/"+ lgnDate.getFullYear();
	
			var pattern = /[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}/i;
		
			/*if(!pattern.test(lgDate)) {
				alert("Invalid date format ");
				dateSel = '';
				return;
			} */
		} catch (err) {
			alert("Error --> Invalid date format ");
			$scope.invalidDate = true;
			console.log("Date parse error --> " + JSON.stringify(err));
			return;
		  }	
		
		var cmpyearStart1 = new Date($scope.company.yearstart);
		var cmpBookStart1 = new Date($scope.company.booksstart);
			
		var cmpyearStart = cmpyearStart1.getDate() +"/"+ (cmpyearStart1.getMonth()+1) +"/"+ cmpyearStart1.getFullYear();
		var cmpBookStart = cmpBookStart1.getDate() +"/"+ (cmpBookStart1.getMonth()+1) +"/"+ cmpBookStart1.getFullYear();
			
		if (!(cmpBookStart1 <= lgnDate)) {
			alert("Selected date of " + lgDate + " cannot be prior to Company book start date of " + cmpBookStart);
			$scope.invalidDate = true;
			return;
		}
	
	}
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.salesform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});

   
});

