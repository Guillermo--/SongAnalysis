angular.module('app')


.service('songListService', function($http) {
	this.getTrackList = function(resultNumber) {
		return $http.get('/getTop/'+resultNumber, {});
	}	
})

.service('wordCloudService', function($http) {
	this.getWordCloud = function(resultNumber) {
		return $http.get('/getWordCloud/'+resultNumber, {});
	}
})

.service('lyricDiversityService', function($http) {
	this.getMostDiverseLyrics = function() {
		return $http.get('/getMostDiverse/', {});
	}
	
	this.getLeastDiverseLyrics = function() {
		return $http.get('getLeastDiverse/', {});
	}
})