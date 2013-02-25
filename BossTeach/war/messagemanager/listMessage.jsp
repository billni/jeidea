<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>北京博胜天成管理咨询有限公司</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="北京博胜天成管理咨询有限公司,博胜天成,管理咨询,consultant,boss,teach,service"/>	 
	<meta name="description" content="boss teach and boss teacher"/>

	<link type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
	<link type="text/css" href="<%=request.getContextPath()%>/css/layout.css"/>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/footer.js"></script>	
</head>
<body>
<table class="tableother" id="inoutput">
  	<s:iterator id="messages" value="messages" status="status">
  <tr height="30">
    <td>&nbsp;
	    <s:property value="messageId"/> : <s:property value="content"/>	    	     
	 </td>	 
  </tr>
  </s:iterator>       
</table>
</body>
</html>