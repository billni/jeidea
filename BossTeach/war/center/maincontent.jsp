<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/global.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>

<div class="easyui-layout">
	<div data-options="region:'north',border:false,href:'center/welcome.jsp'" style="height:200px;width:750px"></div>     			    
    <div data-options="region:'center',border:false">
		<div class="easyui-layout">
			<div data-options="region:'west',border:false,href:'center/news.jsp'" style="width:350px; height:300px;margin:15px"></div>     		
			<div data-options="region:'center',border:false,href:'center/consult.jsp'" style="height:300px; margin:15px"></div>
			<div data-options="region:'east',border:false,href:'center/knowledge.jsp'" style="width:350px; height:300px;margin:15px"></div>     				
  		</div>    
    </div>     			
</div>