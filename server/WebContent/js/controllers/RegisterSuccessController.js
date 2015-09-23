app.controller('RegisterSuccessController', function($scope, $location) {
	$scope.redirect = function(){
		$location.path('/home');
	}
})