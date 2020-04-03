(function () {
    'use strict';
    angular
        .module('app.home')
        .controller('homeCtrl', homeCtrl);

    function homeCtrl(webApi) {
        var vm = this;
        vm.gridApi = undefined;
        vm.pageName = 'Covid-19 NE Updates';
        vm.employeeList = [];
        vm.dataLength = 0;
        vm.states = [];
        vm.allResponseData = {};
        vm.loadState = loadState;
        var colDef = [
            {headerName: 'District', field: 'distric', width: 100, filter: 'agTextColumnFilter'},
            {headerName: 'Total Case', field: 'totalCase', width: 100, filter: 'agTextColumnFilter'},
            {headerName: 'New Case', field: 'newCase', width: 100, filter: 'agTextColumnFilter'}
        ];

        activate();

        ////////////////

        function activate() {
            createGrid();
            _setupMainView();
        }

        function _setupMainView() {
            getAllNEState();
        }

        function loadState(state) {
            var value = byState(state);
            vm.gridApi.api.setRowData([]);
            vm.gridApi.api.setRowData(value);
            console.log(value);
        }

        function getAllNEState() {
            webApi.getAllNEState().then(function (response) {
                var gridValue = [];
                if (response) {
                    vm.allResponseData = response.data;
                    var allState = response.data.data;
                    vm.lastUpdatedTime = response.data.lastUpdateTime;
                    var statesName = Object.keys(allState);
                    vm.states = statesName;
                    statesName.forEach(function (stateName) {
                        var districtData = allState[stateName].districtData;
                        var disricNames = Object.keys(districtData);
                        disricNames.forEach(function (dis) {
                            var obj = {};
                            obj.state = stateName;
                            obj.distric = dis;
                            obj.totalCase = districtData[dis].confirmed;
                            obj.newCase = districtData[dis].delta.confirmed;

                            gridValue.push(obj);
                        });
                    });
                    vm.gridOptions.data = gridValue;
                    vm.gridOptions.rowData = gridValue;
                    vm.dataLength = vm.gridOptions.data.length;
                }
            });
        }

        function byState(stateName) {
            vm.state = stateName;
            var gridValue = [];
            var allState = vm.allResponseData.data;
            vm.active = vm.allResponseData.overview[stateName].active;
            vm.confirmed = vm.allResponseData.overview[stateName].confirmed;
            vm.death = vm.allResponseData.overview[stateName].deaths;
            var districtData = allState[stateName].districtData;
            var disricNames = Object.keys(districtData);
            disricNames.forEach(function (dis) {
                var obj = {};
                obj.state = stateName;
                obj.distric = dis;
                obj.totalCase = districtData[dis].confirmed;
                obj.newCase = districtData[dis].delta.confirmed;

                gridValue.push(obj);
            });

            return gridValue;
        }

        function createGrid() {
            vm.gridOptions = {
                defaultColDef: {
                    resizable: true,
                    sortable: true,
                    filter: true,
                },
                floatingFilter: true,
                onGridReady: getGrid,
                debug: true,
                columnDefs: colDef,
                rowData: []
            };
        }

        function getGrid(gridApi) {
            vm.gridApi = gridApi;
            gridApi.api.sizeColumnsToFit();
        }
    }
})();
