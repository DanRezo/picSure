angular.module('storeViewModule').component(
		'storeView',
		{
			templateUrl : 'ng/app/user/storeView/store/store.component.html',
			controller : function($routeParams, storeService, $scope,
					$location, $filter) {
				var vm = this;
				var storeId = $routeParams.id;
				vm.stores = [];
				vm.storeInventory = [];
				vm.showStore = true;
				vm.cart = [];
				vm.showConfirmationButton = false;
				vm.store = [];
				

				vm.cartItemsExists = function() {
					if (vm.cart.length > 0) {
						return true;
					} else
						return false;
				}
				
				vm.store = function() {
					storeService.show($routeParams.id).then(function(res){
						vm.store = res.data;
						console.log(vm.store)
					})
				}
				

				vm.showCart = function() {
					vm.showStore = false;
					vm.getCart();
				}

				vm.getCart = function() {
					storeService.showCart().then(function(res) {
						vm.cart = res.data;
						vm.cartItemCount = vm.cart.cartItems.length;
					})
				}

				var navCartRoute = function() {
					if ($location.path().split('/')[3] == 'cart') {
						vm.showCart()
					}
				}

				navCartRoute();

				vm.backToStore = function() {
					vm.showStore = true;
				}

				storeService.index().then(function(res) {
					vm.stores = res.data;
				})

				storeService.showStoreInventory($routeParams.id).then(
						function(res) {
							console.log(res.data);
							vm.storeInventory = res.data;
						})

				vm.addToCart = function(dates, item) {
					storeService.addToCart(dates, item.id).then(function(res) {
						vm.getCart();
					})
				}

				vm.removeItem = function(ci) {
					storeService.removeCartItem(ci.id).then(function(res) {
						vm.getCart();
					})
				}

				vm.createReservation = function() {
					storeService.bookReservation(storeId).then(function(res) {
						storeService.emptyCart().then(function() {
							vm.getCart();
						});
						vm.showConfirmationButton = true;
					})
				}

				vm.continueButton = function() {
					$location.path('/user/userReservations');
				}

				vm.numDaysItem = function(ci) {
					var timestamp1 = new Date(ci.timeOut).getDate();
					var timestamp2 = new Date(ci.timeIn).getDate();
			
					var diff = timestamp2 - timestamp1;
					if (diff >= 2) {
						return diff;
					} else {
						diff = 1;
					}
					return diff;
				}

				vm.totalCostItem = function(ci) {
					return vm.numDaysItem(ci) * ci.inventoryItem.rentalRate;
				}

				vm.getCart();
				vm.store();

			},
			controllerAs : 'vm',
			bindings : {
				store : '<',
				goBack : '&',
				onUpdate : '&'
			}
		})
