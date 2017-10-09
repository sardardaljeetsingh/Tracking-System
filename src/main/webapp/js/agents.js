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

		agent.company = {};
		agent.company.id = $scope.company.id;
		agent.createdUser = $rootScope.loggedUser.username;
		agent.createdDate = new Date();
		agent.modifiedUser = $rootScope.loggedUser.username;
		agent.modifiedDate = new Date();
		
			
			var dataObj = JSON.stringify(agent);
			$http.post(hostname+'/agent/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					//$scope.optStatus = 'Success';
					$scope.submitclick = false;	
					$scope.message = 'Agent ' +agent.name + ' creation done successfully.';
					alert($scope.message);
					$scope.agentform.$setPristine();
					$location.path("/perform-action");
				
					//$scope.agent ={}; 
					//$scope.optStatus = null;
					//$scope.submitclick = false;	
					//$scope.agentform.$setPristine();
				  
				  
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
					$scope.message = ' Failed to create agent';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
				$scope.message = ' Failed to create agent';
			  });		
	}
	
	$scope.cancelAgent = function(){
		$rootScope.currentPage ='showAgents';
		console.log("  Cancel Agent");
		$location.path("perform-action");
	}	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.agentform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});
	
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

			agent.modifiedUser = $rootScope.loggedUser.username;
			agent.modifiedDate = new Date();

			var dataObj = JSON.stringify(agent);
			$http.post(hostname + '/agent/update', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					//$scope.optStatus = 'Success';
					$scope.message = 'Agent '+agent.name + ' edit done successfully.';
					alert($scope.message);
					//$location.path("/show-user");
					$scope.agentform.$setPristine();
				    $location.path("/view-agents");
				
				  //$scope.submitclick = false;	
					//$scope.message = 'Agent ' +agent.name + ' creation done successfully.';
					//alert($scope.message);
					//$scope.agentform.$setPristine();
					
				  
				  
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
					$scope.message = ' Failed to edit agent';
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
				$scope.message = ' Failed to edit agent';
			  });
	}
	
	$scope.cancelAgent = function(){
		$rootScope.currentPage ='showAgents';
		console.log("  Cancel Agent");
		$location.path("view-agents");
	}	
	
	
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.agentform.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});
	
});

app.controller('AgentsController', function($scope,$rootScope,$location,$http) {

    $rootScope.agents = [];
	$rootScope.currentPage ='showAgents';
	$http.get(hostname+'/agent/find-by-company/'+$scope.company.id).
			then(function(response) {
				$rootScope.agents = response.data;
				//$rootScope.curTab = 'userTab';
	});	
	
	
	$scope.searchAgentName = function(){
		if(document.agentForm.agentName.value == ''){
			alert("Please enter Agent name to search");
			return;
		}
		var urlForm = hostname + '/agent/find-by-name-search/' + $scope.company.id + '/' + document.agentForm.agentName.value;
		console.log("Agent URL ---> " + urlForm);
		$scope.agents = null; 
		$scope.searchMessage = '';
		//alert("Item selected ---> " + urlForm);
		
		
		$http.get(urlForm).
		then(function(response) 
		{
			$scope.agents = response.data;
			console.log(" Agent search result --> " + JSON.stringify($scope.agents));
            
			if($scope.agents.length == 0){
				$scope.searchMessage = "No Agent found";
			}
			//console.log(" items Length : " + $scope.items.length);
        	//console.log(" items : " + JSON.stringify($scope.items));
            
			//$rootScope.currentPage = 'showStockItems';			
		});			
		
	}
	
	
	
	
	$scope.agent ={}; 
	$scope.message ='';
	$scope.optStatus = null;
	
	$scope.editAgent = function(agent){
		$rootScope.currentPage ='editAgent';
		$scope.message ='';
		console.log( " Edit User");
		$location.path("edit-agent");
		$rootScope.agent = agent ;
	}

	$scope.cancelAgent = function(){
		$rootScope.currentPage = 'performAction';
		$location.path("perform-action");
		
	}
	
	$scope.deleteAgent = function(agent){
		console.log( " delete Agent");
		$scope.message ='';
		var flag = confirm("Are you sure you want to delete this Agent ?");
		if(flag){
			
			
			var dataObj = JSON.stringify(agent);
			var url = hostname + '/agent/delete/'+agent.id;
			console.log(url);
			$http.delete(url, dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$scope.message = 'Agent '+agent.name + ' deleted successfully.';
					$scope.optStatus = 'Success';
					$http.get(hostname+'/agent/find-by-company/'+$scope.company.id).
							then(function(response) {
								$rootScope.agents = response.data;
					});	
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
					$scope.message = ' Failed to delete agent' +agent.name ;
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
				$scope.message = ' Failed to delete agent' +agent.name ;
			  });			
		}
		
		
	}	
	
	//$rootScope.curTab = 'userTab';
	//console.log("   User Tab ");
	//$rootScope.currentPage = "userList";
	//console.log("   currentPage :  " + $rootScope.currentPage);
});