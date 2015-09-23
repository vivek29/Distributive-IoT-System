app.controller('LightSwitchController', function($scope, $rootScope, $http) {
	$scope.endpoint = $rootScope.endpoint2;
	$scope.objinstance = $rootScope.objinstance2;
	
	// get current status
	$http.get("http://192.168.56.1:8081/com.273.lwm2m.device/"+$scope.endpoint+"/observe/9/"+$scope.objinstance+"/ls")
    .success(function(data) {	 
        $scope.status = data;
        if($scope.status == "Off"){
        	var image = document.getElementById("power");
        	document.getElementById("temp").innerHTML = "Current Status: Power Off ";
	        image.src = "img/switch_off.jpg";
        }
        else if($scope.status == "On"){
        	var image = document.getElementById("power");
        	document.getElementById("temp").innerHTML = "Current Status: Power On ";
            image.src = "img/switch_on.jpg";
        }
    })

	
	$scope.changeStatus = function() {
		var image = document.getElementById("power");
		 if (image.src.match("off")) {
			 	document.getElementById("temp").innerHTML = "Current Status: Power On ";
		        image.src = "img/switch_on.jpg";
		        $scope.status = "On";
		    } 
		 else {
		    	document.getElementById("temp").innerHTML = "Current Status: Power Off ";
		        image.src = "img/switch_off.jpg";
		        $scope.status = "Off";
		    }
	
		 $http.get("http://192.168.56.1:8081/com.273.lwm2m.device/"+$scope.endpoint+"/observe/9/"+$scope.objinstance+"/ls/change/"+$scope.status)
		    .success(function(data) {
		    })
		  
		 		 
	}
	
})