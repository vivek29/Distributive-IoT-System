app.controller('HomeController', ['$scope', function($scope) { 	
	$scope.products = [ 
	{ 
	 name: 'Belkin August Thermostat', 
	 cover: 'img/thermostat.jpg'	 
	}, 
	{
	 name: 'Belkin WeMo Light Switch', 
	 cover: 'img/lightswitch.jpg' 	
	},
	{
	 name: 'Belkin WeMo Door & Window Sensor', 
	 cover: 'img/doorwindowsensor.jpg' 			
	}
	]
}])