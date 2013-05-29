<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>		 		
</head>
<body>
	<table border="1" style="border-style: inset;">
	     <tr>
        		<td>流水号</td>
        		<td>车次</td>
        		<td>始发站</td>
        		<td>目的站</td>
        		<td>始发时间</td>
        		<td>到达时间</td>
        		<td>历时</td>
        		<td>商务座</td>
        		<td>特等座</td>
        		<td>一等座</td>
        		<td>二等座</td>        		
        		<td>高级软卧</td>
        		<td>软卧</td>
        		<td>硬卧</td>
        		<td>硬座</td>
        		<td>无座</td>
        		<td>其他</td>
        		<td>查询时间</td>
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