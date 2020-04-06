(function () {
    'use strict';
    angular
        .module('app.services')
        .directive('chart', chartCtrl);

    function chartCtrl() {
        return {
            restrict: 'E',
            template: '<div></div>',
            transclude: true,
            replace: true,
            scope: '=',
            link: function (scope, element, attrs) {
                console.log('oo', attrs, scope[attrs.formatter])
                var opt = {
                    chart: {
                        renderTo: element[0],
                        type: 'line',
                        marginRight: 130,
                        marginBottom: 40
                    },
                    title: {
                        text: attrs.title,
                        x: -20 //center
                    },
                    subtitle: {
                        text: attrs.subtitle,
                        x: -20
                    },
                    xAxis: {
                        //categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                        tickInterval: 1,
                        title: {
                            text: attrs.xname
                        }
                    },
                    plotOptions: {
                        series: {
                                step: 'left' // or 'center' or 'right'
                            },
                        lineWidth: 0.5
                    },
                    yAxis: {
                        title: {
                            text: attrs.yname
                        },
                        tickInterval: (attrs.yinterval) ? new Number(attrs.yinterval) : null,
                        max: attrs.ymax,
                        min: attrs.ymin
                    },
                    tooltip: {
                        formatter: scope[attrs.formatter] || function () {
                            return '<b>' + this.series.userOptions.name+ ' (' + this.y + ')' + '</b>';
                        }
                    },
                    legend: {
                        layout: 'horizontal',
                        align: 'left',
                        verticalAlign: 'top',
                        x: -10,
                        y: 100,
                        borderWidth: 0
                    },
                    series: [],
                    responsive: {
                        rules: [{
                            condition: {
                                maxWidth: 500
                            },
                            chartOptions: {
                                legend: {
                                    align: 'center',
                                    verticalAlign: 'bottom',
                                    layout: 'horizontal'
                                },
                                yAxis: {
                                    labels: {
                                        align: 'left',
                                        x: 0,
                                        y: -5
                                    },
                                    title: {
                                        text: null
                                    }
                                },
                                subtitle: {
                                    text: null
                                },
                                credits: {
                                    enabled: false
                                }
                            }
                        }]
                    }
                }


                //Update when charts data changes
                scope.$watch(function (scope) {
                    return JSON.stringify({
                        xAxis: {
                            categories: scope[attrs.xdata]
                        },
                        series: scope[attrs.ydata]
                    });
                }, function (news) {
                    console.log('ola');
                    news = JSON.parse(news);
                    if (!news.series) return;
                    angular.extend(opt, news);
                    console.log('opt.xAxis.title.text', opt)
                    var chart = new Highcharts.Chart(opt);
                });
            }
        }
    }

})();
