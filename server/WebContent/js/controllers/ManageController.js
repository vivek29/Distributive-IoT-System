app.controller('ManageController', ['$scope','$rootScope','thermostats','lightswitches','$http','$location', function($scope, $rootScope, thermostats, lightswitches, $http, $location, $routescope) {

	thermostats.success(function(data) {
		var temp1 = data;
		var temp2 = temp1.substring(1,temp1.length-1).split("&");
		var devices1 = Array();
		
		for(i=0;i<temp2.length;i++){
	//	console.log(temp2[i].replace("_id","id"));
			
		devices1.push(JSON.parse(temp2[i].replace("_id","id")));
		}
		$scope.thermostats = devices1;
  });
	
	lightswitches.success(function(data) {

		var temp3 = data;
		var temp4 = temp3.substring(1,temp3.length-1).split("&");
		var devices2 = Array();
		
		for(i=0;i<temp4.length;i++){
	//	console.log(temp2[i].replace("_id","id"));
			
		devices2.push(JSON.parse(temp4[i].replace("_id","id")));
		}
		$scope.lightswitches = devices2;
	});
	
	$scope.manageThermostat = function(endpoint,objinstance) {
		$rootScope.endpoint1 = endpoint;
		$rootScope.objinstance1 = objinstance;
		$location.path('/thermo/:id');
	}
	
	$scope.manageLightSwitch = function(endpoint,objinstance) {
		$rootScope.endpoint2 = endpoint;
		$rootScope.objinstance2 = objinstance;
		$location.path('/lightswitch/:id');
	}
	
	$scope.deregister = function(endpoint,objinstance) {
	//	console.log(endpoint);
	//	console.log(objinstance);
		if (window.confirm("Are you sure you want to deregister this device?")) { 
		$http({
			  method  : 'DELETE',
			  url     : "belkin/register/device/"+endpoint+"/"+objinstance,
			  headers : { 'Content-Type': 'text/plain' }  // set the headers so angular passing info as form data (not request payload)
			 }).success(function () {
					 $location.path('/deregister');
			 })
			 .error(function(data,status) {
				 alert("Failed to deregister the device, status=" + status);
			 });
		}
		
	}
}]);