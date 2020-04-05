(function () {
    'use strict';
    angular
        .module('app.services')
        .factory('webApi', webApi);

    function webApi($http) {
        var ALL_NE_DATA_API = '/api/nestate/district';
        var GRAPH_API = '/api/nestate/graphplot';
        //var REG_EMP = '/api/' + API_VERSION + '/register/employee';
        var service = {
            getAllNEState: getAllNEState,
            getGraphData: getGraphData,
        };

        return service;

        function getAllNEState() {
            return $http.get(ALL_NE_DATA_API);
        }

        function getGraphData() {
            return $http.get(GRAPH_API);
        }
    }

})();
