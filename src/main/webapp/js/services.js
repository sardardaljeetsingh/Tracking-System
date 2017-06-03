app.factory('CompanyService',function ($http){
var factory = {};
   
   factory.getAllCompanies = function(user,callback){
	   
	  var URL =  user.type == 'admin' ? hostname+'/company/getAll' : hostname+'/company/find-by-userid/'+user.id;
	  console.log('Company Fetch URL : ' + URL);
     $http.get(URL)
	 .success(function (response) {
         callback(response);
      });
   }

   
   return factory;
});