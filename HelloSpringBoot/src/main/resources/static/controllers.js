

var productsApp = angular.module('productsApp', []);

productsApp.controller('productsCtrl', function($scope, $http) {
	$scope.initProducts = function(uri) {
		$scope.refreshProducts(uri);
	};

	$scope.refreshProducts = function(uri) {
		$http.get(uri).success(function(data) {
			$scope.products = data;
		});
	};

});

function responseHandler(response, event) {
	//console.log(response);
	// {mfrId: 'REI', description: 'Left Hinge', price: 4500, qtyOnHand: 12, prodcutId: '2A44L', …}
}

function callProduct(mfrId, productId, the_uri, callback=responseHandler) {
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {

			var timdstamp_e = new Date();
			/////console.log("END[" + timdstamp_e + "]");
			xhr.timestamp_e = timdstamp_e.getTime();


			/////console.log("ELAPSED_TIME [" + (xhr.timestamp_e - xhr.timestamp_s) + "]");
			callback(xhr.response, event);
		}
	}

	var jsonData = { "mfrId": mfrId, "productId": productId };

	// ProductRestController 호출
	// with FILE EXTENSION the_uri = window.location.protocol + "//" + window.location.host + window.location.pathname + the_uri + '.pwkjson';
	the_uri = window.location.protocol + "//" + window.location.host + the_uri;
	
	/////console.log(the_uri);

	xhr.open("GET", the_uri, true); // true for asynchronous

	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.setRequestHeader("mfr_id", mfrId);
	xhr.setRequestHeader("product_id", productId);

	// controllers.js:53  Uncaught DOMException: Failed to set the 'responseType' property on 'XMLHttpRequest': 
	//The response type cannot be changed for synchronous requests made from a document.
	xhr.responseType = "json";

	var timestamp_s = new Date();
	/////console.log("START[" + timestamp_s + "]");
	/////console.log("START[" + timestamp_s.getTime() + "]");
	/////console.dir(timestamp_s);

	xhr.timestamp_s = timestamp_s.getTime();
	/////console.dir(xhr);
	
	// GET
	//xhr.send(null);
	xhr.send(JSON.stringify(jsonData)); // if 500 error
}