var app = angular.module('BelkinApp', ['ngRoute']);
app.config(function ($routeProvider) { 
	  $routeProvider 
	    .when('/', { 
	    	controller: 'LoginController', 
	    	templateUrl: 'views/login.html' 
	    })
	     .when('/home', { 
	    	controller: 'HomeController', 
	    	templateUrl: 'views/home.html' 
	    })
	    .when('/register', { 
	    	controller: 'RegisterController', 
	    	templateUrl: 'views/register.html' 
	    })
	    .when('/success', { 
	    	controller: 'RegisterSuccessController',
	    	templateUrl: 'views/registersuccess.html' 
	    })
	    .when('/manage', { 
	    	controller: 'ManageController', 
	    	templateUrl: 'views/manage.html' 
	    })
	    .when('/thermo/:id', { 
	    	controller: 'ThermoController', 
	    	templateUrl: 'views/thermo.html' 
	    })
	     .when('/lightswitch/:id', { 
	    	controller: 'LightSwitchController', 
	    	templateUrl: 'views/lightswitch.html' 
	    })
	      .when('/deregister', { 
	    	controller: 'DeregisterController', 
	    	templateUrl: 'views/deregister.html' 
	    })
	    .otherwise({ 
	    	redirectTo: '/index.html' 
	    }); 
	});



