<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script src="<%=request.getContextPath() %>/js/highcharts/highcharts.js"></script>	
<script type="text/javascript">
$(function () { 
    var options = {
	        chart: {
	            renderTo: 'container',
	            type: 'spline'
	        },
	        title: {
	            text: 'Ticket Statistic'
	        },
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: 'TrainNo'
	            }
	        },
	        series: [{}]
	    };

    $.ajax({
    	dataType: "json",
    	url: "/train12306/drawTicket.action",
    	success: function(data) {
	        options.series[0].data = data;
	        var chart = new Highcharts.Chart(options);
		}
    });
});​

</script>	 		
</head>
<body>
<div id="container" style="width:100%; height:400px;"></div>
<a href="<%=request.getContextPath() %>/train12306/crawlTicket.action">Crawl Ticket</a><br/>
<a href="<%=request.getContextPath() %>/train12306/listTicket.action">List Tickets</a><br/>
<a href="<%=request.getContextPath() %>/train12306/extractData.action">Extract Data</a><br/>
<a href="<%=request.getContextPath() %>/train12306/listExtractData.action">List Extract Data</a>
</body>
</html>