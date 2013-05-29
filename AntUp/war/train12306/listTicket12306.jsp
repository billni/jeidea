<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>		 		
</head>
<body>
	<table>
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
        		<td><s:property value="insertTime"/></td>
        	</tr>   
        </s:iterator>
     </table>
</body>
</html>