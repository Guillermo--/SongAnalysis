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
	
	$scope.colors = ["#917ee7", "#7f68e3", "#6c53df", "#5a3ddb", "#4828d7", "#4124c2", "3920ac", "#2b1881"];
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