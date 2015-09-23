app.controller('DeregisterController', function($scope, $location) {
	$scope.redirect = function(){
		$location.path('/home');
	}
})