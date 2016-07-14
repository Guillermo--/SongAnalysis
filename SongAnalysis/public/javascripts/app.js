angular.module('app', ['ngRoute', 'angular-jqcloud'])

.config(['$routeProvider', '$locationProvider', '$sceDelegateProvider', '$httpProvider', function($routeProvider, $locationProvider, $sceDelegateProvider, $httpProvider) {
	
	$routeProvider  
	.when('/', {
        templateUrl: '/assets/pages/main.html'
	})
	.when('/top', {
		templateUrl: '/assets/pages/topTracks.html'
	})
}])




//------------------------- ROUTING FUNCTIONS ----------------------------

.service('routeService', function($location){
	return{
		go : function(path) {
			$location.path(path);
		}
	}
})

.controller('routeController', function(routeService, $scope){
	$scope.go = function(path) {
		routeService.go(path);
	}
});
