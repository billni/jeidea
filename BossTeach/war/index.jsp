<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<title>Boss Teach and Boss Teacher</title>
<head>
	<%@ include file="js/commonHeaderFiles.jsp" %>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:130px">    
    	<div class="easyui-layout">     	 
    		<div data-options="region:'north',border:false" style="height:100px;">
    			<div class="easyui-layout">
     				<div data-options="region:'west',border:false,href:'north/logo.jsp'" style="width:200px; height:100px"></div>      		
     				<div data-options="region:'center',border:false,href:'north/slogan.jsp'" style="height:100px;"></div>
     				<div data-options="region:'east',border:false,href:'north/search.jsp'" style="width:300px;height:100px"></div>     				
     			</div>
     		</div>
     		<div data-options="region:'center',border:false" class="north-center"></div>
     	</div>
    </div>    
     <div data-options="region:'center',border:false">
     	<div class="easyui-layout" data-options="fit:true">     		
     		<div data-options="region:'west',border:false,href:'center/about.jsp'" style="width:200px; padding:0px 5px"></div> 
     		<div data-options="region:'center',border:false,href:'center/maincontent.jsp'" style="padding:0px 10px 10px 10px"></div>     		 	
     	</div>
     </div>  
    <div data-options="region:'south',border:false,href:'south/bottom.jsp'" style="height:100px"></div>
</body>
</html>