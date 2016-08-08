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
	
	$scope.colors = ["#9657a6", "#604d9e", "#2c3b6d", "#5c7598", "#716da6", "#9793c6", "#b8b7c2"];
})

.controller('lyricDiversityController', function(lyricDiversityService, $scope) {
	
	lyricDiversityService.getMostDiverseLyrics().then(function(result) {
		$scope.mostDiverse = result.data;
		console.log($scope.mostDiverse);
	})
	
	lyricDiversityService.getLeastDiverseLyrics().then(function(result) {
		$scope.leastDiverse = result.data;
		console.log($scope.leastDiverse);
	})
})