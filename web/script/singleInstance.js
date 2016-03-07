var wasid;
var dsinfo;

$(document).ready(function () {
    $("#WASParam").change(function () {
        if ($("#WASParam").val() == "dsPoolSize") {
            fetchDataSource();
        } else {
            $("#dsInfo").html("");
        }
    });

    $("#wasServerId").change(function () {
        $("#WASParam").html("");
        var str = "<option value='memUsage'>内存使用量</option><option value='threadPoolSize'>活动线程数</option><option value='dsPoolSize'>连接池大小</option>";
        $("#WASParam").html(str);
        $("#dsInfo").html("");
    });

    function fetchDataSource() {
        var url = "wlsJDBC/get.htm";
        var optionStr = "";
        $.ajax({
            url: url,
            type: "post",
            dataType: 'json',
            data: {
                'wasServerId': $("#wasServerId").val()
            },
            success: function (dsList) {
                var data = dsList.data;
                for (var i = 0; i < data.length; i++) {
                    optionStr += "<option value='" + data[i].dsName + "'>" + data[i].dsName + "</option>";
                }
                $("#dsInfo").html(optionStr);
            }
        });
    }

    var wasDSPoolInterval;
    var wasThreadPoolInterval;
    var wasMemUsageInterval;
    
    
      function renderWASDSPoolSizeChart() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart;
        var options = {
            chart: {
                renderTo: 'detailInfo',
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var series = this.series;
                        wasMemUsageInterval = setInterval(function () {
                            var url = "wlsMonitor/dsPoolSizeChart.htm";
                            $.ajax({
                                url: url,
                                //type: "POST",
                                cache: true,
                                dataType: "json",
                                data: {
                                    'wlsServerId': wasid,
                					'dsInfo': dsinfo
                                },
                                ifModified: false,
                                success: function (result) {
                                	//console.log("result: " + result);
                                    var x = (new Date()).getTime();
                                    var y = result;
                                    series[0].addPoint([x, parseFloat(y)], true, true);
                                }
                            });
                        }, 3000);
                    }
                }
            },
            title: {
                text: '性能监控'
            },
            subtitle: {
                text: '连接池当前容量'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 100
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                startOnTick: true, //为true时，设置min才有效
                min: 0, //minorTickInterval: 'auto',// y轴样式 网格的形式
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                crosshairs: true,
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>值：' +
                        Highcharts.numberFormat(this.y, 2) + '<br/>时间：' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x);
                }
            },
            credits: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function () {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: this.pageX,
                                        y: this.pageY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: '值：' + this.y + ':<br/>时间：' +
                                        Highcharts.dateFormat('%Y-%m-%e %H:%M:%S', this.x),
                                    width: 200
                                });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },
            series: [
                {
                    name: 'jdbcPoolSize(Count)'
                }
            ]
        };

        var url = "wlsMonitor/dsPoolSizeChart.htm";
        $.ajax({
            url: url,
            //type: "POST",
            cache: true,
            dataType: "json",
            data: {
                'wlsServerId': wasid,
                'dsInfo': dsinfo
                
            },
            ifModified: false,
            success: function (result) {
                var paiBan = [];
                var time = (new Date()).getTime();
                for (var i = -29; i <= 0; i++) {
                    paiBan.push([
                            time + i * 10000,
                        0
                    ]);
                }
                options.series[0].data = paiBan;
                chart = new Highcharts.Chart(options);
            }
        });
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    function renderWASDSPoolSizeChart1() {
  
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart;
        var options = {
            chart: {
                renderTo: 'detailInfo',
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        chart = this;
                        var series = this.series;
                        wasDSPoolInterval = setInterval(function () {
                            var url = "wlsMonitor/dsPoolSizeChart.htm";
                            $.ajax({
                                url: url,
                                //type: "POST",
                                cache: true,
                                dataType: "json",
                                data: {
                                    'wlsServerId': wasid,
                                    'dsName': dsInfo
                                },
                                ifModified: false,
                                success: function (result) {
                                    $("#showMsg").html(result);
                                    var x = (new Date()).getTime();
                                    var y = result;
                                    series[0].addPoint([x, parseFloat(y)], true, true);
                                }
                            });
                        }, 3000);
                    }
                }
            },
            title: {
                text: '性能监控'
            },
            subtitle: {
                text: '连接池大小'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 100
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                startOnTick: true,//为true时，设置min才有效
                min: 0,
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                crosshairs: true,
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>值：' + Highcharts.numberFormat(this.y, 2) + '<br/>时间' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x);
                }
            },
            credits: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function () {
                                hs.htmlExpand(
                                    null,
                                    {
                                        pageOrigin: {
                                            x: this.pageX,
                                            y: this.pageY
                                        },
                                        headingText: this.series.name,
                                        maincontentText: '值：' + this.y + ':<br/>时间：' + Highcharts.dateFormat('%Y-%m-%e %H:%M:%S', this.x),
                                        width: 200
                                    });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },
            series: [
                {
                    name: 'JdbcPoolSize'
                }
            ]
        };

        var url = "wlsMonitor/dsPoolSizeChart.htm";
        $.ajax({
            url: url,
            //type: "POST",
            cache: true,
            dataType: "json",
            data: {
                'wlsServerId': wasid,
                'dsInfo': dsinfo
            },
            ifModified: false,
            success: function (result) {
                var paiBan = [];
                var time = (new Date()).getTime();
                for (var i = -29; i <= 0; i++) {
                    paiBan.push([ time + i * 10000, 0 ]);
                }
                options.series[0].data = paiBan;
                chart = new Highcharts.Chart(options);
            }
        });
    }

    function renderWASMemUsageChart() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart;
        var options = {
            chart: {
                renderTo: 'detailInfo',
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var series = this.series;
                        wasMemUsageInterval = setInterval(function () {
                            var url = "wlsMonitor/memUsageChart.htm";
                            $.ajax({
                                url: url,
                                //type: "POST",
                                cache: true,
                                dataType: "json",
                                data: {
                                    'wasServerId': wasid
                                },
                                ifModified: false,
                                success: function (result) {
                                	//console.log("result: " + result);
                                    var x = (new Date()).getTime();
                                    var y = result;
                                    series[0].addPoint([x, parseFloat(y)], true, true);
                                }
                            });
                        }, 3000);
                    }
                }
            },
            title: {
                text: '性能监控'
            },
            subtitle: {
                text: '虚拟机使用内存量'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 100
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                startOnTick: true, //为true时，设置min才有效
                min: 0, //minorTickInterval: 'auto',// y轴样式 网格的形式
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                crosshairs: true,
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>值：' +
                        Highcharts.numberFormat(this.y, 2) + '<br/>时间：' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x);
                }
            },
            credits: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function () {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: this.pageX,
                                        y: this.pageY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: '值：' + this.y + ':<br/>时间：' +
                                        Highcharts.dateFormat('%Y-%m-%e %H:%M:%S', this.x),
                                    width: 200
                                });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },
            series: [
                {
                    name: 'UsedMemory(MB)'
                }
            ]
        };

        var url = "wlsMonitor/memUsageChart.htm";
        $.ajax({
            url: url,
            //type: "POST",
            cache: true,
            dataType: "json",
            data: {
                'wasServerId': wasid
                
            },
            ifModified: false,
            success: function (result) {
                var paiBan = [];
                var time = (new Date()).getTime();
                for (var i = -29; i <= 0; i++) {
                    paiBan.push([
                            time + i * 10000,
                        0
                    ]);
                }
                options.series[0].data = paiBan;
                chart = new Highcharts.Chart(options);
            }
        });
    }

    function renderWASThreadPoolSizeChart() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart;
        var options = {
            chart: {
                renderTo: 'detailInfo',
                type: 'spline',
                marginRight: 10,
                events: {
                    load: function () {
                        var series = this.series;
                        wasThreadPoolInterval = setInterval(function () {
                            var url = "wlsMonitor/threadActiveChart.htm";
                            $.ajax({
                                url: url,
                                type: "get",
                                cache: true,
                                dataType: "json",
                                data: {
                                    'wasServerId': wasid
                                },
                                ifModified: false,
                                success: function (result) {
                                    //$("#showMsg").html(result.activewebcontainer);
                                    var x = (new Date()).getTime();
                                    var y = result;//.activewebcontainer;
                                    series[0].addPoint([x, parseFloat(y)], true, true);
                                }
                            });
                        }, 3000);
                    }
                }
            },
            title: {
                text: '性能监控'
            },
            subtitle: {
                text: '活动线程数'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 100
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                startOnTick: true, //为true时，设置min才有效
                min: 0,
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                crosshairs: true,
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>值：' +
                        Highcharts.numberFormat(this.y, 2) + '<br/>时间：' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x);
                }
            },
            credits: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function () {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: this.pageX,
                                        y: this.pageY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: '值：' + this.y + ':<br/>时间：' +
                                        Highcharts.dateFormat('%Y-%m-%e %H:%M:%S', this.x),
                                    width: 200
                                });
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    }
                }
            },
            series: [
                {
                    name: 'WebContainerPoolSize'
                }
            ]
        };

        var url = "wlsMonitor/threadActiveChart.htm";
        $.ajax({
            url: url,
            type: "get",
            cache: true,
            dataType: "json",
            data: {
                'wasServerId': wasid
            },
            ifModified: false,
            success: function (result) {
                var paiBan = [];
                var time = (new Date()).getTime();
                for (var i = -19; i <= 0; i++) {
                    paiBan.push([
                            time + i * 10000,
                        0
                    ]);
                }
                options.series[0].data = paiBan;
                chart = new Highcharts.Chart(options);
            }
        });
    }

    $("#monitorBtn").click(function () {
        var wasParam = $("#WASParam").val();
        clearInterval(wasDSPoolInterval);
        clearInterval(wasThreadPoolInterval);
        clearInterval(wasMemUsageInterval);
        if ($("#dsInfo").val() && wasParam == "dsPoolSize") {
        	
        	dsinfo = $("#dsInfo").val();
        	
        	wasid = $("#wasServerId").val();
        	
            renderWASDSPoolSizeChart();
        }
        if (wasParam && wasParam == "threadPoolSize") {
        	wasid = $("#wasServerId").val();
            renderWASThreadPoolSizeChart();
        }
        if (wasParam && wasParam == "memUsage") {
        	wasid = $("#wasServerId").val();
        	
            renderWASMemUsageChart();
        }
    });
});
