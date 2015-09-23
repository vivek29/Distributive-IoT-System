app.controller('ThermoController', function($scope, $rootScope, $http) {
	
	$scope.endpoint = $rootScope.endpoint1;
	$scope.objinstance = $rootScope.objinstance1;
	
	$scope.getValue = function() {
		
			$http({
				  method  : 'GET',
				  url     : "http://192.168.56.1:8081/com.273.lwm2m.device/"+$scope.endpoint+"/observe/8/"+$scope.objinstance+"/thermo",
				  headers : { 'Content-Type': 'text/plain' }  // set the headers so angular passing info as form data (not request payload)
				 }).success(function (data) {
						 $scope.value = data;
						 document.getElementById("temp").innerHTML = "Current Temperature:  "+$scope.value+'\xB0'+"F";
				 })
				 .error(function(data,status) {
					 alert("Failed to get the value from device, status=" + status);
				 });
			}
})