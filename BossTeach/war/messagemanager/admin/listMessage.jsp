<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>北京博胜天成管理咨询有限公司</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="北京博胜天成管理咨询有限公司,博胜天成,管理咨询,consultant,boss,teach,service"/>	 
	<meta name="description" content="boss teach and boss teacher"/>
	<meta name="pagename" content="contact"/>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/layout.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/default.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/calendar-time.js"></script>
	<script type="text/javascript" src="listMessage.js"></script>	
</head>
<body>
	<div id="header_tall">
		<div id="main">
			<!--header -->
			<div id="header">
				<div id="logo" class="h_logo"></div>
				<div id="menu"></div>
				<div class="padding">
				<fieldset>
				<form id="messageform">
				 <table  class="othertable">
					 <tr>
					    <td> 联系人：
					      <input name="message.visitor.name" id="customer" type="text"/>&nbsp;&nbsp;
					    </td>
					    <td>电子邮箱：
					      <input id="message.visitor.mail" name="email" type="text"/>&nbsp;&nbsp;
					    </td>
					    <td>联系电话：
					      <input name="message.visitor.fax" id="phone" type="text"/>&nbsp;&nbsp;
					    </td>
					    <td colspan="2">时间： <input  id="appointBeginDate" name="appointBeginDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
					        到 <input id="appointEndDate" name="appointEndDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
					    </td>
					    <td>&nbsp;&nbsp;<input id="submit" type="button" value="提交" class="button"></input></td>
					  </tr>
					</table>
					</form>
				</fieldset>
					<br />	  	 
				 <table id="messageListTable"></table>	 
				</div>	
			</div> 				
			<!--footer -->
			<div id="footer">			
			</div>
			<!--footer end-->
		</div>
	</div>
	

    

</body>
</html>