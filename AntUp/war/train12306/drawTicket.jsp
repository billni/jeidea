<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css"/>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript"><!--!window.jQuery && document.write('<script src=/js/jquery-1.10.2.min.js><\/script>');-></script>
<script src="http://libs.baidu.com/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript"><!--!window.Highcharts && document.write('<script src=/js/highcharts/highcharts.js><\/script>');-></script>
<script type="text/javascript" src="/js/decorate.js"></script>
<script type="text/javascript">
$(function () {	
	    var startDate = new Date("<s:property value='drawChartStartDate'/>");	    
	    alert(window.jQuery);
        var options = {
            chart: {
                zoomType: 'x',
                renderTo: 'container',
                spacingRight: 20
            },
            title: {               
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    'Click and drag in the plot area to zoom in' :
                    'Drag your finger over the plot to zoom in'
            },
            xAxis: {
                type: 'datetime',
                minRange: 4 * 3600 * 1000,
                title: {
                    text: null
                },
                minTickInterval : 15 * 60 * 1000
            },
            yAxis: {
                title: {
                    text: 'Ticket Count'
                }
            },
            tooltip: {
                shared: false
            },
            legend: {
                enabled: true
            },    
            series: [{
                type: 'line',
                name: 'T5-HardSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='t5HardSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'T5-SoftSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='t5SoftSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'T5-HardSeat',
                pointInterval:  900 * 1000,
                data: [<s:property value='t5HardSeatTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'T189-HardSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='t189HardSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'T189-SoftSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='t189SoftSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'T189-HardSeat',
                pointInterval:  900 * 1000,
                data: [<s:property value='t189HardSeatTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'K157-HardSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='k157HardSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'K157-SoftSleep',
                pointInterval:  900 * 1000,
                data: [<s:property value='k157SoftSleepTicketCountSpecialDate'/>]
            },
            {
                type: 'line',
                name: 'K157-HardSeat',
                pointInterval:  900 * 1000,
                data: [<s:property value='k157HardSeatTicketCountSpecialDate'/>]
            }
            
            ]
        };
        options.series[0].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[1].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[2].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());     
        options.series[3].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[4].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[5].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()); 
        options.series[6].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[7].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
        options.series[8].pointStart = Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()); 
        options.title.text = "<s:property value='drawChartEndDate'/>"+ " Ticket Change Trend";
        var chart = new Highcharts.Chart(options);
});​

</script>	 		
</head>
<body>
	<div id="body">
		<div id="site-logo"></div>
		<div id="site-menu"></div>
		<div id="site-content">
			<div id="container" style="width:100%; height:600px;"></div>
		</div>
	</div>	

</body>
</html>