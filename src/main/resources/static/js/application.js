var highlightFn = function() {
	document.querySelectorAll('pre code').forEach((block) => {
	    hljs.highlightBlock(block);
	  });
}

var app = angular.module('app', ['ngRoute']);
 
// routes
app.config(["$routeProvider", function($routeProvider) {
    $routeProvider
    
        .when('/home', {
            templateUrl : '../partials/home.html',
            controller  : 'appController'
        })
        
        .when('/standalone', {
            templateUrl : '../partials/standalone.html',
            controller  : 'standaloneController'
        })
 
        .when('/message', {
            templateUrl : '../partials/message.html',
            controller  : 'messageController'
        })
        
        .when('/discover', {
            templateUrl : '../partials/discover.html',
            controller  : 'discoverController'
        })
        
        .when('/validate', {
            templateUrl : '../partials/validate.html',
            controller  : 'validateController'
        })
        .when('/services', {
        	 templateUrl : '../partials/services.html',
        	 controller  : 'appController'
        })
        .otherwise({
            redirectTo: '/home'
        });
    
 
}]);
 
// create the controller and inject Angular's $scope
app.controller('appController',  ['$scope', '$location','$location', '$anchorScroll',function($scope, $location){ 
	$scope.init = function () { 
		$location.path('#/home');
	};
	
	$scope.scroll = function () { 
		$location.hash('services');
	    $anchorScroll();
	};
}]);
 
app.controller('standaloneController', ['$scope', '$http', '$interval', '$location', function($scope, $http, $interval, $location) {
	
	$scope.testSuccess = false; 
	
	$scope.update = function() {
		$scope.name = $scope.epattern.name
		$scope.pattern = $scope.epattern.pattern
		$scope.test = $scope.epattern.testText
		$scope.desc = $scope.epattern.description
		$scope.testFn();
	}
	
	$scope.loadFn = function() {
		$http({
			method : 'GET',
			url : '/dataapi/standAlones?size=1000000',
			data : null,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config) {
			$scope.epatterns = data._embedded.standAlones;
		}).error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data
			}));
		});
	};
	
	$scope.deleteFn = function() {
		
		var oldData = $scope.epattern;
	
			$scope.respShow = false;
			
			$http({
				method : 'DELETE',
				url : '/dataapi/standAlones/' + oldData.idStandAlone,
				data : null,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Removed successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
	};
	
	$scope.resetForm = function(form) {
		$scope.name = null
		$scope.pattern = null
		$scope.test = null
		$scope.desc = null
		$scope.testResults = null
		$scope.respShow = false;
		$scope.loadFn();
	}
	
	$scope.loadFn();
    
	$scope.save = function() {
		
		if ($scope.epattern == null) {
			$scope.saveNew();
		} else {
			$scope.saveUpdate();
		}
		
		$http({
			method : 'GET',
			url : '/clearcache',
			data : null,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config) {
			console.log('cache cleared');
		});
	};
	
	$scope.saveUpdate = function() {
		var oldData = $scope.epattern;
		
		var postdata = {
				idStandAlone : oldData.idStandAlone,
				name: $scope.name,
				pattern: $scope.pattern,
				testText: $scope.test,
				description: $scope.desc
			};
		
			$scope.respShow = false;
			
			$http({
				method : 'PUT',
				url : '/dataapi/standAlones/' + oldData.idStandAlone,
				data : postdata,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Data updated successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		
	} 
	
	$scope.saveNew = function() {
		var postdata = {
				name: $scope.name,
				pattern: $scope.pattern,
				testText: $scope.test,
				description: $scope.desc
			};
			
			$scope.respShow = false;
			
			$http({
				method : 'POST',
				url : '/dataapi/standAlones',
				data : postdata,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Data saved successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		
	} 
	
	
	$scope.testFn = function() {
		var postdata = {
			testPattern: $scope.pattern,
			testMessage: $scope.test
		};
		
		$scope.respShow = false;
		
		$http({
			method : 'POST',
			url : '/savalidate',
			data : postdata,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config) {
			$scope.respShow = true;
			if (data.responseCode == 0) {
				$scope.testSuccess = true; 
				$scope.testResults = data.testResults;
				$scope.response = 'Tested grok successfully';
			}  else {
				$scope.testSuccess = false;
				$scope.testResults = data.testResults;
				$scope.response = data.responseMsg;
			}

		}).error(function(data, status, headers, config) {
			$scope.response = data
			$scope.respShow = true;
			console.log("failure message: " + JSON.stringify({
				data
			}));
		});
	};
	
}]);
 
app.controller('messageController', ['$scope', '$http', '$interval', '$location', function($scope, $http, $interval, $location) {
$scope.testSuccess = false; 
	
	$scope.update = function() {
		$scope.name = $scope.epattern.name
		$scope.pattern = $scope.epattern.pattern
		$scope.test = $scope.epattern.testText
		$scope.desc = $scope.epattern.description
		$scope.testFn();
	}
	
	$scope.loadFn = function() {
		$http({
			method : 'GET',
			url : '/dataapi/messagePatterns',
			data : null,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config) {
			$scope.epatterns = data._embedded.messagePatterns;
		}).error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data
			}));
		});
	};
	
	$scope.deleteFn = function() {
		
		var oldData = $scope.epattern;
	
			$scope.respShow = false;
			
			$http({
				method : 'DELETE',
				url : '/dataapi/messagePatterns/' + oldData.idMessagePattern,
				data : null,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Removed successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
	};
	
	$scope.resetForm = function(form) {
		$scope.name = null
		$scope.pattern = null
		$scope.test = null
		$scope.desc = null
		$scope.testResults = null
		$scope.respShow = false;
		$scope.loadFn();
	}
	
	$scope.loadFn();
    
	$scope.save = function() {
		
		if ($scope.epattern == null) {
			$scope.saveNew();
		} else {
			$scope.saveUpdate();
		}
		
	};
	
	$scope.saveUpdate = function() {
		var oldData = $scope.epattern;
		
		var postdata = {
				idStandAlone : oldData.idMessagePattern,
				name: $scope.name,
				pattern: $scope.pattern,
				testText: $scope.test,
				description: $scope.desc
			};
		
			$scope.respShow = false;
			
			$http({
				method : 'PUT',
				url : '/dataapi/messagePatterns/' + oldData.idMessagePattern,
				data : postdata,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Data updated successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		
	} 
	
	$scope.saveNew = function() {
		var postdata = {
				name: $scope.name,
				pattern: $scope.pattern,
				testText: $scope.test,
				description: $scope.desc
			};
			
			$scope.respShow = false;
			
			$http({
				method : 'POST',
				url : '/dataapi/messagePatterns',
				data : postdata,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.response = 'Data saved successfully';
				$scope.resetForm();
				$scope.respShow = true;
				
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		
	} 
	
	
	$scope.testFn = function() {
		var postdata = {
			testPattern: $scope.pattern,
			testMessage: $scope.test
		};
		
		$scope.respShow = false;
		
		$http({
			method : 'POST',
			url : '/validate',
			data : postdata,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config) {
			$scope.respShow = true;
			if (data.responseCode == 0) {
				$scope.testSuccess = true; 
				$scope.testResults = data.testResults;
				$scope.response = 'Tested Message Grok successfully';
			} else {
				$scope.testSuccess = false;
				$scope.testResults = data.testResults;
				$scope.response = data.responseMsg;
			} 
		}).error(function(data, status, headers, config) {
			$scope.response = data
			$scope.respShow = true;
			console.log("failure message: " + JSON.stringify({
				data
			}));
		});
	};
}]);

app.controller('discoverController', function($scope) {
    $scope.info = 'Discover';
});

app.controller('validateController', ['$scope', '$http', '$interval', '$location', function($scope, $http, $interval, $location) {
		$scope.testSuccess = false; 
		$scope.responseXML = true;
		
		$scope.update = function() {
			
		}
		
		$scope.loadFn = function() {
			$http({
				method : 'GET',
				url : '/dataapi/messagePatterns',
				data : null,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.epatterns = data._embedded.messagePatterns;
			}).error(function(data, status, headers, config) {
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		};
		
		
		$scope.resetForm = function(form) {
			$scope.testResults = null
			$scope.respShow = false;
			$scope.loadFn();
		}
		
		$scope.loadFn();
	    
	
		$scope.testFn = function() {
			var resp = "json"
			if($scope.responseXML) { 
				resp = "xml" 
			}
			var postdata = {
				uniqueIdentifier: $scope.epattern.name,
				testMessage: $scope.test,
				responseFormat : resp
			};
			
			$scope.respShow = false;
			
			$http({
				method : 'POST',
				url : '/validate',
				data : postdata,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				$scope.respShow = true;
				if (data.responseCode == 0) {
					$scope.testSuccess = true; 
					$scope.testResults = data.testResults;
					$scope.response = 'Tested Message Grok successfully';
					//highlightFn();
				} else if (data.responseCode == 1){
					$scope.testSuccess = false;
					$scope.testResults = data.testResults;
					$scope.response = 'Match not found'
				} else if (data.responseCode == 2){
					$scope.testSuccess = false;
					$scope.testResults = data.testResults;
					$scope.response = 'Partial Match Found'
				}
			}).error(function(data, status, headers, config) {
				$scope.response = data
				$scope.respShow = true;
				console.log("failure message: " + JSON.stringify({
					data
				}));
			});
		};
	}]);


