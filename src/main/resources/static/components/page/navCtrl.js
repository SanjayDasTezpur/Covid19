(function () {
    'use strict';
    angular
        .module('app.services')
        .controller('navCtrl', navCtrl);

    function navCtrl($location) {
        var vm = this;
        vm.pageName = 'COVID-19 North-East India';
        vm.homeGo = homeGo;
        activate();

        ////////////////

        function activate() {
        }

        function homeGo() {
            console.log('Loading Home Page')
            $location.href('/');
        }
    }
})();
