(function () {
    'use strict';
    angular
        .module('app.services')
        .factory('webApi', webApi);

    function webApi($http) {
        var ALL_EMP = '/api/nestate/district';
        //var REG_EMP = '/api/' + API_VERSION + '/register/employee';
        var service = {
            getAllNEState: getAllNEState,
        };

        return service;

        function getAllNEState() {
            return $http.get(ALL_EMP);
        }
    }

})();
