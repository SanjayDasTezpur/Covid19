(function (){
        angular.module('app.home')
            .config(['$routeProvider', function config($routeProvider) {
                $routeProvider.
                when('/', {
                    templateUrl : "/components/home/home.html",
                    controller : "homeCtrl as vm"
                });
            }
            ]);
    }
)();
