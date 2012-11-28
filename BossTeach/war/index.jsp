<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<title>Boss Teach and Boss Teacher</title>
	<%@ include file="js/commonHeaderFiles.jsp" %>
</head>
<body>
	<div class="easyui-layout" style="height:35px">
	   	<div id="hat" data-options="region:'center',border:false"></div>    
	</div>

	<div class="easyui-layout" style="height:750px;">
			<div data-options="region:'west',border:false" style="width:179px;"></div>     		
			<div data-options="region:'center',border:false">
				<div class="easyui-layout">
						<div data-options="region:'north',border:false" style="height:100px">
							<div class="easyui-layout">
								<div data-options="region:'west',border:false,href:'north/logo.html'" style="width:200px; height:100px"></div>      		
		    					<div data-options="region:'center',border:false"></div>
		    					<div data-options="region:'east',border:false" style="width:200px;height:100px"></div>
		    				</div>
		    			</div>
						<div data-options="region:'center',border:false">
							<div class="easyui-layout" >
								<div id="eye" data-options="region:'north',border:false" style="height:310px"></div>
								<div data-options="region:'center',border:false">
									<div data-options="region:'west',border:false,href:'center/news.html'" style="width:350px; height:300px;margin:15px"></div>     		
									<div data-options="region:'center',border:false,href:'center/consult.html'" style="height:300px; margin:15px"></div>
									<div data-options="region:'east',border:false,href:'center/knowledge.html'" style="width:350px; height:300px;margin:15px"></div>
								</div>
								<div data-options="region:'south'">
									view all news
								</div>
							</div>
						</div>	    				
				</div>					
			</div>
			<div data-options="region:'east',border:false" style="width:179px;"></div> 
	</div>
	
	<div class="easyui-layout">
	   	<div id="beard" data-options="region:'center',href:'south/bottom.html'"></div>    
	</div>
</body>
</html>