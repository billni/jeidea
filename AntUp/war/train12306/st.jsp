<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>		 	
	<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>  
	<script src="http://code.highcharts.com/highcharts.js"></script>	
</head>
<body>
	<table border="1">
	     <tr>
        		<th>流水号</th>
        		<th>ticketId</th>
        		<th>车次</th>
        		<th>始发时间</th>
        		<th>座位等级</th>
        		<th>数量</th>
        		<th>查询时间</th>
        	</tr> 
		<s:iterator id="ticket" value="tickets" status="st">
        	<tr>
        		<td><s:property value="#st.index"/></td>
        		<td><s:property value="ticketId"/></td>
        		<td><s:property value="trainNo"/></td>        		
        		<td><s:property value="departureDate"/></td>
        		<td><s:property value="grade"/></td>
        		<td><s:property value="count"/></td>
        		<td><s:date name="insertTime" format="yyyy-MM-dd hh:mm:ss"/></td>
        	</tr>   
        </s:iterator>
     </table>
</body>
</html>