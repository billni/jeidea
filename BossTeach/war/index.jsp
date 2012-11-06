<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<title>Boss Teach and Boss Teacher</title>
<head>
	<%@ include file="js/commonHeaderFiles.jsp" %>
	<style type="text/css">
		.north-center {
			height:30px;
			background-image:url(img/bg.jpg);
			background-repeat:repeat;															
		}
	</style>
</head>
<body class="easyui-layout">
    <div data-options="region:'north'" style="height:130px;width:100%">    
    	<div class="easyui-layout">     	 
    		<div data-options="region:'north'" style="height:100px;width:100%">
    			<div class="easyui-layout">
     				<div data-options="region:'west',href:'north/logo.jsp'" style="width:200px; height:100px"></div>      		
     				<div data-options="region:'center',href:'north/slogan.jsp'" style="width:600px;height:100px;"></div>
     				<div data-options="region:'east',href:'north/search.jsp'" style="width:200px;height:100px"></div>     				
     			</div>     			
     		</div>
     		<div data-options="region:'center'" class="north-center"></div>
     	</div>
    </div>    
     <div data-options="region:'center'" style="width:1024px">
     	<div class="easyui-layout" data-options="fit:true">     		
     		<div data-options="region:'west'" style="width:200px"></div> 
     		<div data-options="region:'center',href:'center/navigator.jsp'" style="background:#eee;"></div>     		 	
     	</div>
     </div>  
    <div data-options="region:'south'" style="height:100px;"></div>
</body>
</html>