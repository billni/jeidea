<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Stand On Cloud</title>
<link type="text/css" rel="stylesheet" href="/css/style.css"/>
<script type="text/javascript" src="/js/decorate.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		if (window.jQuery==undefined) {
			document.write("<script src=/js/jquery-1.10.2.min.js><\/script>");
	     }
		layoutinit();
	 }
</script>
</head>

<body>
	<div id="body">
		<div id="site-logo"></div>
		<div id="site-menu"></div>
		<div id="site-content"></div>
		<div id="site-footer"></div>
	</div>
</body>
</html>
