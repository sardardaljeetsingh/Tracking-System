app.controller('usersController', function($scope,$rootScope,$location,$http) {

    $rootScope.users = [];
	$http.get(hostname+'/user/findAll').
			then(function(response) {
				$rootScope.users = response.data;
	});	
	
	$scope.user ={}; 
	
	$scope.createUser = function(){
		$rootScope.currentPage = 'createUser';
		$location.path("create-user");
	}
	$scope.editUser = function(user){
		$rootScope.currentPage = 'editUser';
		console.log( " Edit User");
		$location.path("edit-user");
		$rootScope.user = user ;
	}
	
	$scope.performAction = function(user){
		$rootScope.currentPage = 'performAction';
		$rootScope.user = user;
		$location.path("perform-action");
	}
	$rootScope.curTab = 'userTab';
});

app.controller('createUserController', function($scope,$rootScope,$location,$http) {
	$scope.user ={}; 
	$scope.user.previliges = {};
	$scope.user.previliges.accountinfo = "true";
	$scope.user.previliges.inventoryinfo = "true";
	$scope.optType = "create";
	$scope.submitclick = false;
	
	$scope.createUser = function(user){
		if(!$scope.userform.$valid){
			$scope.submitclick = true;
			return;
		}

			var dataObj = JSON.stringify(user);
			$http.post(hostname+'/user/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$rootScope.currentPage = 'userList';
					$rootScope.users.push(user);
					$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
			  });		
	}
	
});


app.controller('editUserController', function($scope,$rootScope,$location,$http) {

    $scope.optType = "edit";
 	$scope.submitclick = false;

	$scope.editUser = function(user){
		
		if(!$scope.userform.$valid){
			$scope.submitclick = true;
			return;
		}		
			var dataObj = JSON.stringify(user);
			$http.post(hostname + '/user/create', dataObj, {
			  headers: {
				'Content-Type': 'application/json; charset=UTF-8'
			  },
			}).success(function(responseData) {
				  try {
					console.log(JSON.stringify(responseData));
					$rootScope.currentPage = 'companyList';
					$rootScope.users.push(user);
					$location.path("/show-user");
				  } catch (err) {
					alert(JSON.stringify(err));
				  }
			 }).error(function(data, status, headers, config) {
				console.log(JSON.stringify(data) +" headers : "+ JSON.stringify(headers) +"  status : " + status);
			  });
	}
	
});


app.controller('assignUsersController', function($scope,$rootScope,$location,$http) {

    $rootScope.users = [];
	$http.get(hostname+'/user/findAll').
			then(function(response) {
				$rootScope.users = response.data;
				$scope.assignedUsers = $rootScope.users;
				$scope.availableUsers = $rootScope.users;
	});	
	$scope.assignedUsers = $rootScope.users;
	$scope.availableUsers = $rootScope.users;
});

       $(function () {

            $('body').on('click', '.list-group .list-group-item', function () {
                $(this).toggleClass('active');
            });
            $('.list-arrows button').click(function () {
                var $button = $(this), actives = '';
                if ($button.hasClass('move-left')) {
                    actives = $('.list-right ul li.active');
                    actives.clone().appendTo('.list-left ul');
                    actives.remove();
                } else if ($button.hasClass('move-right')) {
                    actives = $('.list-left ul li.active');
                    actives.clone().appendTo('.list-right ul');
                    actives.remove();
                }
            });
            $('.dual-list .selector').click(function () {
                var $checkBox = $(this);
                if (!$checkBox.hasClass('selected')) {
                    $checkBox.addClass('selected').closest('.well').find('ul li:not(.active)').addClass('active');
                    $checkBox.children('i').removeClass('glyphicon-unchecked').addClass('glyphicon-check');
                } else {
                    $checkBox.removeClass('selected').closest('.well').find('ul li.active').removeClass('active');
                    $checkBox.children('i').removeClass('glyphicon-check').addClass('glyphicon-unchecked');
                }
            });
            $('[name="SearchDualList"]').keyup(function (e) {
                var code = e.keyCode || e.which;
                if (code == '9') return;
                if (code == '27') $(this).val(null);
                var $rows = $(this).closest('.dual-list').find('.list-group li');
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();
                $rows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });

        });	  
