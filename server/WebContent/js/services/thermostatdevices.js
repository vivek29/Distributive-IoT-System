app.factory('thermostats', ['$http', function($http) {
  return $http.get('http://localhost:8080/com.273.lwm2m.servers/belkin/register/data1')
         .success(function(data) {
             return data;
         })
       
}]);