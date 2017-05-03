angular.module('appModule', [ 'ngRoute', 'static', 'nav', 'authModule' ]).config(
		function($routeProvider) {
			$routeProvider.when('/', {
				template : '<home></home>'
			}).when('/contact', {
				template : '<contact></contact>'
			}).when('/register', {
				template : '<register></register>'
			}).when('/login', {
				template : '<login></login>'
			}).otherwise({
				template : '<error></error>'
			})
		});
