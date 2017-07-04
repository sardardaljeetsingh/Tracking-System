app.controller('PurchagesController', function($scope,$rootScope,$location,$http,ItemService) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
	$scope.purchanges = function(purchage){
		if(!$scope.purchagesform.$valid){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(purchage);
			$http.post(hostname+'/purchage/create', dataObj, {
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

app.controller('SalesController', function($scope,$rootScope,$location,$http,ItemService) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
	$scope.sales = function(sale){
		if(!$scope.salesform.$valid  || sale.item.curqundty < sale.quandity ){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(sale);
			$http.post(hostname+'/sales/create', dataObj, {
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

app.controller('ReceiptsController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
     $rootScope.accgroups = [];
     AccGroupService.getAllGroupsByCompanyId($rootScope.company.id,function(response){
      console.log(" AccGroupService Response " + JSON.stringify(response));
	  $rootScope.accgroups = response;
	  $rootScope.curTab = 'companyTab';
   });  
   
	$scope.receipts = function(receipt){
		if(!$scope.receiptform.$valid){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(receipt);
			$http.post(hostname+'/purchage/create', dataObj, {
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



app.controller('PaymentsController', function($scope,$rootScope,$location,$http,ItemService,AccGroupService) {

    $rootScope.items = [];
     ItemService.getAllItems(function(response){
      console.log(" ItemService Response " + JSON.stringify(response));
	  $rootScope.items = response;
	  $rootScope.curTab = 'companyTab';
   });
   
     $rootScope.accgroups = [];
     AccGroupService.getAllGroupsByCompanyId($rootScope.company.id,function(response){
      console.log(" AccGroupService Response " + JSON.stringify(response));
	  $rootScope.accgroups = response;
	  $rootScope.curTab = 'companyTab';
   });  
   
	$scope.payments = function(payment){
		if(!$scope.paymentsform.$valid || payment.item.curqundty < payment.quandity ){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(payment);
			$http.post(hostname+'/sales/create', dataObj, {
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