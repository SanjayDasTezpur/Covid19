(function () {
    'use strict';
    angular
        .module('app.services')
        .controller('navCtrl', navCtrl);

    function navCtrl() {
        var vm = this;
        vm.pageName = 'COVID-19 NE Dashboard';
        activate();
        ////////////////

        function activate() {
        }
    }
})();
