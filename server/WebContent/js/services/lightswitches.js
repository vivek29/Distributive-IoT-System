app.factory('lightswitches', ['$http', function($http) {
  return $http.get('http://localhost:8080/com.273.lwm2m.servers/belkin/register/data2')
         .success(function(data) {	 
             return data;
         })

}]);