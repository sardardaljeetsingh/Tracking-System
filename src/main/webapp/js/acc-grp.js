app.controller('accGroupController', function($scope,$rootScope,$location,$http) {
	$rootScope.currentPage = 'createAccountGroups';
	
	$scope.singlegroups = [];
	$scope.singlegroup ={};
    $scope.singlegroup.newgroup	= "";
	$scope.multigroups =[];	
	
	//Groups data received from backend
	console.log(" Fetching Account group for Company Id : " + $scope.company.id );
	$http.get(hostname + '/accgroup/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
			$scope.groups = response.data;
			//$scope.groups = response.data;
			console.log(" Groupth Length : " + $scope.groups.length)			
			var count = 1;
			angular.forEach($scope.groups, function (group) 
			{
				//change on 9/28
				//if(group.parent <= 6){
				if(count <= 6){
				  $scope.singlegroups.push(group);
				  count++;	
				}
			});	
		});	
						  



	$scope.setChildrenData = function(selGroup,grplevel){
		$scope.multiGrpMsg = "";
		$scope.singleGrpMsg = "";		
		    var selGrpId = (selGroup != null && selGroup.id != null ) ? selGroup.id : -1;
			var children = [];
			angular.forEach($scope.groups, function (group) {
				 if(group.parent == selGrpId && group.name != selGroup.name){
					children.push(group);
					//$scope.multigroups[grplevel].children.push(group);
				 }
			});	
			
			console.log(" AccountGRP before : "+ $scope.multigroups.length);
			var tempGrps = [];
			for(i=0;i<grplevel; i++){
				
				if($scope.multigroups[i] != null){
					tempGrps[i]  = $scope.multigroups[i];
				}else{
				 tempGrps[i] = {};
				 tempGrps[i].children = [];
				}
			}
			$scope.multigroups = tempGrps;
			if(selGrpId > 0){
				$scope.multigroups[grplevel] = {};
				$scope.multigroups[grplevel].children = children;
			}
			
			var grpHyrhy = "";
			if($scope.singlegroup.selGroup != null)
				grpHyrhy = $scope.singlegroup.selGroup.name + " > ";
			
			angular.forEach($scope.multigroups, function (multiGrp) {
				 if(multiGrp.selGroup != null)
					 grpHyrhy = grpHyrhy + multiGrp.selGroup.name + " > " ;
			});
			$scope.grpHierarchy = grpHyrhy;
			
		   // $scope.multigroups[grplevel].children.push(children);
			console.log("AccountGRP After  :" + $scope.multigroups.length  +"   "+ children);
	}
	
	$scope.addGroup = function(){
		var newgroup = {};
		var selGroup = $scope.groupName;
		if(selGroup== null || selGroup.length == 0 || $scope.singlegroup.selGroup == null || $scope.singlegroup.selGroup <= 0 ){
			$scope.submitclick = true;
			return;
		}
		newgroup.name = selGroup;
		var grplevel = 0;
		if($scope.singlegroup.selGroup != null)
			grplevel++;
		
		angular.forEach($scope.multigroups, function (multiGrp) {
			 if(multiGrp.selGroup != null)
				 grplevel++;
		});	

		console.log( grplevel );
		console.log( selGroup );
		//newgroup.id = $scope.groups.length;
		newgroup.parent = 1;
		newgroup.company = {};
		newgroup.company.id = $scope.company.id;
		
		if(grplevel == 0){
			//$scope.groups.push(newgroup);
		}else if(grplevel == 1){
			newgroup.parent = $scope.singlegroup.selGroup.id;
		}else{
			newgroup.parent = $scope.multigroups[grplevel-2].selGroup.id;
		}		
		
		
		newgroup.createdUser = $rootScope.loggedUser.username;
		newgroup.createdDate = new Date();
		newgroup.modifiedUser = $rootScope.loggedUser.username;
		newgroup.modifiedDate = new Date();
		
		
		var dataObj = JSON.stringify(newgroup);
		console.log(dataObj);
		
		
		
		$http.post(hostname + '/accgroup/create', dataObj, {
		  headers: {
			'Content-Type': 'application/json; charset=UTF-8'
		  },
		}).success(function(responseData) {
			console.log("AccountGRP Response : " + responseData.id);
			  try {
                newgroup.id = responseData.id;
					if(grplevel == 0){
						$scope.singlegroups.push(newgroup);
						$scope.groups.push(newgroup);
						$scope.singleGrpMsg = " ' "+ newgroup.name + " ' account created successfully."  ;
					}else if(grplevel == 1){
						$scope.multigroups[0].children.push(newgroup);
						$scope.groups.push(newgroup);	
						$scope.singleGrpMsg = " ' "+ newgroup.name + " ' group created successfully."  ;						
					}else{
						$scope.multigroups[grplevel-1].children.push(newgroup);
						$scope.groups.push(newgroup);
						$scope.singleGrpMsg = " ' "+ newgroup.name + " ' group created successfully."  ;						
					}	

					$scope.groupName = "";
					$scope.grpHierarchy ="";
					$scope.submitclick = false;
					$scope.singlegroup.selGroup = null;
					$scope.accountgroup.$setPristine();
					console.log(" success "+ $scope.singleGrpMsg);
					
					//$scope.message = 'Agent '+agent.name + ' edit done successfully.';
					//alert($scope.singleGrpMsg);
					//$location.path("/show-user");
					$scope.agentform.$setPristine();
				    //$location.path("/perform-action");
				
			  
			  } catch (err) {
				console.log("Account group create error ---> " + JSON.stringify(err));
			  }
		 }).error(function(data, status, headers, config) {
			console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
		  });		
	

		//console.log(" AccountGRP " + newgroup);
	}

		$scope.cancelGroup = function(){
			$rootScope.currentPage ='showAgents';
			$location.path("perform-action");
		}
    
	$scope.$on('$locationChangeStart',function(event,next,current) {
		if($scope.accountgroup.$dirty){
			if(confirm("Please save the changes before moving to another page")){
				event.preventDefault();
			}
		}
	});
	

	
						  
});


app.controller('showAccountGroupsController', function($scope,$rootScope,$location,$http) {
		$rootScope.currentPage = 'showAccountGroups';
		console.log(" AccountGRP company.id "  + $scope.company.id );
	    $http.get(hostname + '/accgroup/find-by-company/'+$scope.company.id).
		then(function(response) 
		{
			$scope.groups = response.data;
			//$scope.groups = response.data;
			console.log(" AccountGRP Length : " + $scope.groups.length)	;
            $rootScope.currentPage = 'showAccountGroups';			
		});	

	$scope.deleteAccGrp = function(accgroup){
		console.log( " delete accgroup");
		$scope.message ='';
		var flag = confirm("Are you sure you want to delete this Account Group ?");
		if(flag){	
			var url = hostname + '/accgroup/delete/'+accgroup.id;
			console.log(url);
			$http.delete(url, '', {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					  
					console.log("Account group delete ---> " + JSON.stringify(responseData));
					$scope.message = 'Account Group '+accgroup.name + ' deleted successfully.';
					$scope.optStatus = 'Success';

					//$rootScope.currentPage = 'showAccountGroups';
					//$location.path("/view-account-groups");
					//$route.reload();
					
					$http.get(hostname + '/accgroup/find-by-company/'+$scope.company.id).
					then(function(response) 
					{
						$scope.groups = response.data;
					});	
					
				  } catch (err) {
					alert(JSON.stringify(err));
					$scope.optStatus = 'Failed';
					$scope.message = ' Failed to delete Account Group' +accgroup.name ;
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
				$scope.optStatus = 'Failed';
				$scope.message = ' Failed to delete Account Group' +accgroup.name ;
			  });			
		}	
	}
	
	$scope.searchAccountGroup = function(){
		if(document.accountGroupForm.accountGroupName.value == ''){
			alert("Please enter Account Group name to search");
			return;
		}
		var urlForm = hostname + '/accgroup/find-by-name-search/' + $scope.company.id + '/' + accountGroupForm.accountGroupName.value;
		$scope.groups = null; 
		$scope.searchMessage = '';
		//alert("Item selected ---> " + urlForm);
		
		
		$http.get(urlForm).
		then(function(response) 
		{
			$scope.groups = response.data;
			console.log(" Account Group search : " + JSON.stringify($scope.groups));
            
			if($scope.groups.length == 0){
				$scope.searchMessage = "No Account Group found";
			}
			//console.log(" items Length : " + $scope.items.length);
        	//console.log(" items : " + JSON.stringify($scope.items));
            
			//$rootScope.currentPage = 'showStockItems';			
		});			
		
	}
	
	
	
	
	$scope.cancelAccountGroup = function(){
		$rootScope.currentPage = 'performAction';
		$location.path("perform-action");
		
	}
		
});
