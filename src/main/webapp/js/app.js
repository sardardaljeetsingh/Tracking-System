var app = angular.module("trackingApp", ["ngRoute"]);
app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider
	.when('/',{templateUrl:'/inline-login.html'})
	.when('/home',{templateUrl:'/inline-home.html'})
	.when('/submitRequest'	,
	      { 
		    controller: 'submitRequestController',
		    templateUrl :'/inline-submitRequest.html',
		  })
	.when('/companies'	,
	      { 
		    controller: 'companiesController',
		    templateUrl :'/inline-companies.html',
		  })
	.when('/employees'	,
	      { 
		    controller: 'employeesController',
		    templateUrl :'/inline-employees.html',
		  })
	.when('/viewRequests'	,
	      { 
		    controller: 'viewRequestsController',
		    templateUrl :'/inline-viewRequests.html',
		  })	
	.when('/viewAdminRequests'	,
	      { 
		    controller: 'viewRequestsController',
		    templateUrl :'/inline-admin-viewRequests.html',
		  })		  
	.when('/reports',{template:'This is the Report Route'});
}]); 
//
app.controller('loginController', function($scope,$location) {
    
	$scope.user = {};
	$scope.user.isValidLogin = false;
	
	 $scope.login = function(){
		if($scope.user.name == "admin"){
			$scope.user.type = "admin";
		}else{
			$scope.user.type = "user";
		}
		console.log($location.path());
		$location.path("home");
		$scope.user.isValidLogin = true;
	 };
});


app.controller('submitRequestController', function($scope) {
	$scope.trackingreq = {};
	$scope.trackingreq.status = "done";
	$scope.employees = [ {id:1, "name":"pavan"} , {id:2, "name":"daljeet"} ];
	$scope.customers = [ {id:1, "name":"Cognizant"} , {id:2, "name":"IBM"} ];
});
  
app.controller('companiesController', function($scope) {
	$scope.companies = [];
	$scope.company = { "inputname" :""} ;
	$scope.createCompany = function(company){
		$scope.addCompany = false;
		$scope.selectedCompany = false;
		$scope.companies.push(company);
		$scope.company = {} ;
		$scope.insertUpdStatus="Company Created Successflly";
	};
	$scope.editCompany = function(){
		$scope.addCompany = false;
		$scope.selectedCompany = false;
		$scope.company = {} ;
		$scope.insertUpdStatus="Company Updated Successflly";
	};	
});

app.controller('employeesController', function($scope) {
	$scope.employees = [];
	$scope.employee = { } ;
	$scope.createEmployee = function(employee){
		$scope.addEmployee = false;
		$scope.selectedEmployee = false;
		$scope.employees.push(employee);
		$scope.employee = {} ;
		$scope.insertUpdStatus="Employee Created Successflly";
	};
	$scope.editEmployee = function(){
		$scope.addEmployee = false;
		$scope.selectedEmployee = false;
		$scope.employee = {} ;
		$scope.insertUpdStatus="Employee Updated Successflly";
	};	
});

app.controller('viewRequestsController', function($scope) {
	
});

