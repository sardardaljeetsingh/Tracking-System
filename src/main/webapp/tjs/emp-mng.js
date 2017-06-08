app.controller('employeesController', function($scope,$rootScope,$location,$http) {

    $rootScope.employees = [];
	$http.get(hostname+'/employee/findAll').
			then(function(response) {
				$rootScope.employees = response.data;
				$rootScope.curTab = 'empTab';
	});	
	
	$scope.createEmployee = function(){
		$rootScope.currentPage = 'createEmployee';
		$location.path("create-employee");//Redirect to Create Page
	}
	$scope.editEmployee = function(employee){
		$rootScope.currentPage = 'editEmployee';
		$location.path("edit-employee");//Redirect to Edit Page
		$rootScope.employee = employee ;
	}	
	
	
	$rootScope.curTab = 'empTab';
	console.log("   EMP Tab ");
	$rootScope.currentPage = "empList";
	console.log("   currentPage :  " + $rootScope.currentPage);
});	




app.controller('createEmployeeController', function($scope,$rootScope,$location,$http) {
	$scope.employee ={}; 
	$scope.optType = "create";
	$scope.submitclick = false;
	
	$scope.createEmployee = function(employee){
		if(!$scope.employeeform.$valid){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(employee);
			$http.post(hostname+'/employee/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$rootScope.currentPage = 'empList';
					$rootScope.employees.push(employee);
					$location.path("/show-employee");//Redirect to Show Emnpoyee GRID page
				  } catch (err) {
					alert(JSON.stringify(err));
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
			  });		
	}
	
});


app.controller('editEmployeeController', function($scope,$rootScope,$location,$http) {

    $scope.optType = "edit";
 	$scope.submitclick = false;

	$scope.editEmployee = function(employee){
		
		if(!$scope.employeeform.$valid){
			$scope.submitclick = true;
			return;
		}		
			var dataObj = JSON.stringify(employee);
			$http.post(hostname + '/employee/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$rootScope.currentPage = 'employeeList';
					$rootScope.employees.push(employee);
					$location.path("/show-employee");
				  } catch (err) {
					alert(JSON.stringify(err));
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
			  });
	}
	
});


