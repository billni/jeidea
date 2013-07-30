<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
<script src="<%=request.getContextPath() %>/js/highcharts/highcharts.js"></script>	
<script type="text/javascript">
$(function () {
	    var t5HardSleepTicketCount = "<s:property value='t5HardSleepTicketCount'/>";
	    var startDate = new Date("<s:property value='drawChartStartDate'/>");
	    
        $('#container').highcharts({
            chart: {
                zoomType: 'x',
                spacingRight: 20
            },
            title: {
                text: 'Ticket Change Trend'
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    'Click and drag in the plot area to zoom in' :
                    'Drag your finger over the plot to zoom in'
            },
            xAxis: {
                type: 'datetime',
                minRange: 12 * 3600 * 1000,
                title: {
                    text: null
                },
                minTickInterval : 15 *60 * 1000
            },
            yAxis: {
                title: {
                    text: 'Ticket Count'
                }
            },
            tooltip: {
                shared: true
            },
            legend: {
                enabled: true
            },    
            series: [{
                type: 'line',
                name: 'T5-HardSleep',
                pointInterval:  900 * 1000,
                pointStart: Date.UTC(startDate.getYear(), startDate.getMonth(), startDate.getDate()),
                data: t5HardSleepTicketCount
            }]
        });
});​

</script>	 		
</head>
<body>
<div id="container" style="width:100%; height:400px;"></div>
</body>
</html>