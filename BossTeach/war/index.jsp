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
			filter: alpha( style=1,opacity=25,finishOpacity=100,startX=10,finishX=50,startY=50,finishY=100);																
		}
	</style>
</head>
<body class="easyui-layout" >
    <div data-options="region:'north',border:false" style="height:130px;width:100%">    
    	<div class="easyui-layout">     	 
    		<div data-options="region:'north',border:false" style="height:100px;width:100%">
    			<div class="easyui-layout">
     				<div data-options="region:'west',border:false,href:'north/logo.jsp'" style="width:200px; height:100px"></div>      		
     				<div data-options="region:'center',border:false,href:'north/slogan.jsp'" style="width:500px;height:100px;"></div>
     				<div data-options="region:'east',border:false,href:'north/search.jsp'" style="width:300px;height:100px"></div>     				
     			</div>     			
     		</div>
     		<div data-options="region:'center',border:false" class="north-center"></div>
     	</div>
    </div>    
     <div data-options="region:'center',border:false" style="width:1024px">
     	<div class="easyui-layout" data-options="fit:true">     		
     		<div data-options="region:'west',border:false,href:'center/about.jsp'" style="width:200px"></div> 
     		<div data-options="region:'center',border:false,href:'center/maincontent.jsp'"></div>     		 	
     	</div>
     </div>  
    <div data-options="region:'south',border:false,href:'south/bottom.jsp'" style="height:100px;"></div>
</body>
</html>