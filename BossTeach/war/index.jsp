<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<%@ include file="js/commonHeaderFiles.jsp" %>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:100%;height:100%">  
    <div data-options="region:'north'" style="height:100px;"></div>    
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>  
    <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>  
    <div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>  
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;"></div>  
</div>
</body>
</html>