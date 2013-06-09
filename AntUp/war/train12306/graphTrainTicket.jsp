<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<script src="/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
<script src="/js/highcharts/highcharts.js"></script>	
<script type="text/javascript">
$(function () { 
    $('#container').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Fruit Consumption'
        },
        xAxis: {
            categories: ['Apples', 'Bananas', 'Tomatos', 'Oranges']
        },
        yAxis: {
            title: {
                text: 'Fruit eaten'
            }
        },
        series: [{
            name: 'Jane',
            data: [1, 2, {y: 3, marker: { fillColor: '#BF0B23', radius: 10 }}, 4]
        }, {
            name: 'John',
            data: [5, 7, {y: 6, marker: { fillColor: '#BF0B23', radius: 10 }}, 3]
        }]
    });
});​

</script>	 		
</head>
<body>
<div id="container" style="width:100%; height:400px;"></div>
</body>
</html>