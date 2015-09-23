app.controller('LoginController', function($scope, $location) {
	
	$scope.username = '';
	$scope.password = '';
	
	$scope.loginSuccess = function() {
		if($scope.username=="vivek" && $scope.password=="123"){
			$location.path('/home');
		}
		else{
			alert("The username or password wasn't correct..");
		}
	}
	
})