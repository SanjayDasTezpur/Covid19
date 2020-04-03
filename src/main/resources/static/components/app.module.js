(function() {
    agGrid.initialiseAgGridWithAngular1(angular);
    angular.module('app', ['ngRoute','agGrid','app.home','app.services']);
})();
