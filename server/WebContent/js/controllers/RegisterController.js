app.controller('RegisterController', function($scope, $location, $http) {
	$scope.endpoint = '';
	$scope.objinstance = '';
	
	$scope.processForm = function() {
		  $http({
		  method  : 'POST',
		  url     : "belkin/register/device/"+$scope.endpoint,
		  data    : $.param({
		        'objinstance' : $scope.objinstance
		  }),  
		  headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
		 }).success(function (data) {
			 $scope.result = data;
			 if($scope.result=="success"){
				 $location.path('/success');
			 }
			 else{
				 alert("The Device Endpoint Name or Server Object Instance ID you entered is not valid..");
			 }
		 })
		 .error(function(data,status) {
			 alert("Server failed to access the data, status=" + status);
		 });
	}
})