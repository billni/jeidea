<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<link href="/css/style.css" rel="stylesheet" type="text/css" />		 		
</head>
<body>
	<form action="<%=request.getContextPath()%>/train12306/convertData.action" method="post">
	<table border="">
	     <tr>
        		<td>
        			<textarea name="data" rows="15" cols="20" style="height:800px; width:900px">        		
        			</textarea>
        		</td>
		 </tr>
     </table>
     <input type="submit" value="submit">&nbsp;&nbsp;
     <input type="reset" value="reset">
     </form> 
</body>
</html>