<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript">
$('#ss').searchbox({   
    searcher:function(value,name){   
        alert(value + "," + name)   
    },   
    menu:'#mm',   
   prompt:'Please Input Value'  
});  
</script>
<div align="center">
	<a href="" target="_top">注册</a>
	<font style="color:#37424A">&nbsp;|&nbsp;</font>
	<a onClick="" href="#">登录</a>	
	<br/>
	<br/>  
<input id="ss"></input>  
<div id="mm" style="width:120px">  
    <div data-options="name:'Google'">Google</div>  
    <div data-options="name:'Baidu'">Baidu</div>
</div> 
</div>