

app.controller('AgentCreateController', function($scope,$rootScope,$location,$http,UserPrevService) {
	$scope.agent ={}; 
	$scope.optType = "create";
	$scope.submitclick = false;
	$rootScope.currentPage ='createAgent';
	
	$scope.createAgent = function(agent){
		if(!$scope.agentform.$valid){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(agent);
			$http.post(hostname+'/agent/create', dataObj, {
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
	
	$scope.clear = function(){
		$scope.agent ={}; 
		$scope.optStatus = null;
		$scope.submitclick = false;		
	}	
	
});


app.controller('AgentEditController', function($scope,$rootScope,$location,$http,UserPrevService) {

    $scope.optType = "edit";
 	$scope.submitclick = false;
    $rootScope.currentPage ='editAgent';

	$scope.editAgent = function(agent){
		
		if(!$scope.agentform.$valid){
			$scope.submitclick = true;
			return;
		}		
			var dataObj = JSON.stringify(agent);
			$http.post(hostname + '/agent/update', dataObj, {
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
	
	$scope.cancelAgent = function(){
		$rootScope.currentPage ='showAgents';
		console.log("  Cancel Agent");
		$location.path("view-agents");
	}	
	
});

app.controller('AgentsController', function($scope,$rootScope,$location,$http) {

    $rootScope.agents = [];
	$rootScope.currentPage ='showAgents';
	$http.get(hostname+'/agent/findAll').
			then(function(response) {
				$rootScope.agents = response.data;
				//$rootScope.curTab = 'userTab';
	});	
	
	$scope.agent ={}; 
	
	$scope.editAgent = function(agent){
		$rootScope.currentPage ='editAgent';
		console.log( " Edit User");
		$location.path("edit-agent");
		$rootScope.agent = agent ;
	}
	
	
	//$rootScope.curTab = 'userTab';
	//console.log("   User Tab ");
	//$rootScope.currentPage = "userList";
	//console.log("   currentPage :  " + $rootScope.currentPage);
});
