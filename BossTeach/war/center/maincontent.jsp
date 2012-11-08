<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.masonry.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/center/maincontent.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.corner.js"></script>
<style>
	.item {
		width:200px;
		height:200px;
		padding:10px;
		margin:10px;
		display: inline;
		filter: alpha(style=1,opacity=25,finishOpacity=100,startX=100,finishX=50,startY=100,finishY=50);
		background-color: skyblue;			
	}
</style>
<div>
	<div class="item">新闻</div>
	<div class="item">产品</div>
	<div class="item">服务</div>
	<div class="item">咨询</div>	
	<div class="item">知识管理</div>	
	<div class="item"></div>
	<div class="item"></div>				
</div>