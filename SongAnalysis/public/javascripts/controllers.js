angular.module('app')

.controller('songListController', function(songListService, $scope) {
	$scope.resultTracks = [];

	songListService.getTrackList(10).then(function(result) {
		$scope.resultTracks = result.data;
	});
})

.controller('wordCloudController', function(wordCloudService, $scope) {
	$scope.words = [];
	
	wordCloudService.getWordCloud(10).then(function(result) {
		var wordCloudArray = result.data.array;
		$scope.words = wordCloudArray.filter(function(e){ return e === 0 || e });
	});
	

	
	
})
