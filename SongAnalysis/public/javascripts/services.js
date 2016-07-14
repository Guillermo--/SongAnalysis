angular.module('app')


.service('songListService', function($http) {
	this.getTrackList = function(resultNumber) {
		return $http.get('/getTop/'+resultNumber, {});
	}	
})

.service('wordCloudService', function($http) {
	this.getWordCloud = function(resultNumber) {
		return $http.get('/getWordCloud'+resultNumber, {});
	}
})