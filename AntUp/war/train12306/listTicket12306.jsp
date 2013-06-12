<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>		 		
</head>
<body>
	<table border="1">
	     <tr>
        		<th>流水号</th>
        		<th>车次</th>
        		<th>始发站</th>
        		<th>目的站</th>
        		<th>始发时间</th>
        		<th>到达时间</th>
        		<th>历时</th>
        		<th>商务座</th>
        		<th>特等座</th>
        		<th>一等座</th>
        		<th>二等座</th>        		
        		<th>高级软卧</th>
        		<th>软卧</th>
        		<th>硬卧</th>
        		<th>硬座</th>
        		<th>无座</th>
        		<th>其他</th>
        		<th>查询时间</th>
        	</tr> 
		<s:iterator id="ticket" value="tickets">
        	<tr>
        		<td><s:property value="ticketId"/></td>
        		<td><s:property value="trainNo"/></td>
        		<td><s:property value="fromStation"/></td>
        		<td><s:property value="toStation"/></td>
        		<td><s:property value="departureDate"/></td>
        		<td><s:property value="arrvialDate"/></td>
        		<td><s:property value="during"/></td>
        		<td><s:property value="businessClass"/></td>
        		<td><s:property value="specialClass"/></td>
        		<td><s:property value="firstClass"/></td>
        		<td><s:property value="secondClass"/></td>        		
        		<td><s:property value="seniorSoftSleepClass"/></td>
        		<td><s:property value="softSleepClass"/></td>
        		<td><s:property value="hardSleepClass"/></td>
        		<td><s:property value="hardSeatClass"/></td>
        		<td><s:property value="standing"/></td>
        		<td><s:property value="others"/></td>
        		<td><s:date name="insertTime" format="yyyy-MM-dd hh:mm:ss"/></td>
        	</tr>   
        </s:iterator>
     </table>
</body>
</html>