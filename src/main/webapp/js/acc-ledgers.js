app.controller('ledgerController', function($scope,$rootScope,$location,$http) {
	$rootScope.ledgers = [];
	$scope.message ='';
	$rootScope.currentPage = 'viewLedgers';
	$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
	then(function(response) 
	{
		$rootScope.ledgers = response.data;
		//$scope.groups = response.data;
		console.log(" Ledgers Length : " + $scope.ledgers.length)			
	});	

	$scope.editLedger = function(ledger){
		$rootScope.currentPage = 'editLedger';
		console.log( " Edit ledger");
		$rootScope.ledger = ledger ;
		console.log("Edit ledger ---> " + JSON.stringify($rootScope.ledger));
				
		$scope.optType = "edit";
		$location.path("edit-ledger");
	}
	
	$scope.deleteLedger = function(ledger){
		console.log( " delete ledger");
		$scope.message ='';
		var flag = confirm("Are you sure you want to delete this Ledger ?");
		if(flag){	
			var url = hostname + '/ledger/delete/'+ledger.id;
			console.log(url);
			$http.delete(url, '', {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.message = 'Ledger '+ledger.name + ' deleted successfully.';
					$scope.optStatus = 'Success';
					$http.get(hostname + '/ledger/find-by-company/'+$scope.company.id).
							then(function(response) {
								$rootScope.ledgers = response.data;
					});	
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
					$scope.message = ' Failed to delete Ledger' +ledger.name ;
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
				$scope.message = ' Failed to delete Ledger' +ledger.name ;
			  });			
		}	
	}
	
		$scope.searchLedger = function(){
		if(document.ledgerForm.ledgerName.value == ''){
			alert("Please enter Ledger name to search");
			return;
		}
		var urlForm = hostname + '/ledger/find-by-name-search/' + $scope.company.id + '/' + document.ledgerForm.ledgerName.value;
		$scope.searchMessage = '';
		//alert("Item selected ---> " + urlForm);
		
		
		$http.get(urlForm).
		then(function(response) 
		{
			$rootScope.ledgers = response.data;
			console.log(" Ledger search : " + JSON.stringify($rootScope.ledgers));
            
			if($rootScope.ledgers.length == 0){
				$scope.searchMessage = "No Ledgers found";
			}
			//console.log(" items Length : " + $scope.items.length);
        	//console.log(" items : " + JSON.stringify($scope.items));
            
			//$rootScope.currentPage = 'showStockItems';			
		});			
		
	}
	

	
	
	
	
	$scope.cancelLedger = function(){
		$rootScope.currentPage = 'performAction';
		$location.path("perform-action");
		
	}
});	


app.controller('createLedgerController', function($scope,$rootScope,$location,$http) {

	$scope.ledger = {};
	$scope.optType = "create";
	$scope.submitclick = false;
	$rootScope.currentPage = 'createLedgers';

	$scope.ledger.mailingstate = $rootScope.states[31].id;

	console.log(" createLedgers company.id "  + $scope.company.id );
	$http.get(hostname + '/accgroup/find-by-company/'+$scope.company.id).
	then(function(response) 
	{
		$scope.groups = response.data;
		//$scope.groups = response.data;
		console.log(" createLedgers Group Length : " + $scope.groups.length)			
	});	
	
	$scope.createLedger = function(ledger){
		if(!$scope.ledgerform.$valid){
			$scope.submitclick = true;
			return;
		}
		
		var groupId = ledger.accGroup.id;		
		ledger.accGroup = {};
		ledger.accGroup.id = groupId;
		ledger.alias = ledger.name;
		
		ledger.createdUser = $rootScope.loggedUser.username;
		ledger.createdDate = new Date();
		ledger.modifiedUser = $rootScope.loggedUser.username;
		ledger.modifiedDate = new Date();
		
		//ledger.accGroup.id = ledger.groupid;
			var dataObj = JSON.stringify(ledger);
			console.log("Ledger create ----> " +  dataObj );
			$http.post(hostname+'/ledger/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					//$rootScope.currentPage = 'ledgerList';
					//$rootScope.ledgers.push(ledger);
					//$location.path("/view-ledgers");
					$scope.ledger = {};
					$scope.submitclick = false;
					$scope.ledger.mailingstate = $rootScope.states[31].id;
					$scope.optStatus = 'Success';
					$scope.ledgerform.$setPristine();
					
					//alert("Ledger created successfully.");
					//$location.path("/show-user");
					//$scope.agentform.$setPristine();
				    //$location.path("/perform-action");
				
					
					
					} catch (err) {
					console.log("Ledger create error ---> " + JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });		
	}	
	
	$scope.cancelLedger = function(){
		$rootScope.currentPage ='createLedgers';
		$location.path("perform-action");
	}	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.ledgerform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});
	
	
	
});	


app.controller('editLedgerController', function($scope,$rootScope,$location,$http) {

    $scope.optType = "edit";
 	$scope.submitclick = false;
    //$scope.ledger.groupid = $scope.ledger.accGroup.id;
	
	$http.get(hostname + '/accgroup/find-by-company/'+$scope.company.id).
	then(function(response) 
	{
		$scope.groups = response.data;
		//$scope.groups = response.data;
		console.log(" createLedgers Group Length : " + $scope.groups.length)			
	});	
	
	$scope.editLedger = function(ledger){
		
		if(!$scope.ledgerform.$valid){
			$scope.submitclick = true;
			return;
		}	
		ledger.alias = ledger.name;
			var groupId = ledger.accGroup.id;		
		    ledger.accGroup = {};
			ledger.accGroup.id = groupId;
			
			ledger.modifiedUser = $rootScope.loggedUser.username;
			ledger.modifiedDate = new Date();
			
			var dataObj = JSON.stringify(ledger);
			console.log( " editLedger : " + dataObj);
			$http.post(hostname + '/ledger/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$rootScope.currentPage = 'viewLedgers';
					//$rootScope.ledgers.push(user);
					//$location.path("/view-ledgers");
					$scope.optStatus = 'Success';
					$scope.ledgerform.$setPristine();
					//alert("Ledger updated successfully.");
					//$location.path("/view-ledgers");
					
					
				  } catch (err) {
					console.log("Ledger edit error ---> " + JSON.stringify(err));
					$scope.optStatus = 'Failed';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
			  });
	}

	$scope.cancelLedger = function(){
		$rootScope.currentPage ='viewLedgers';
		$location.path("/view-ledgers");
	}	
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.ledgerform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});
	
});