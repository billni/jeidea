$(function(){
	$("#submit").click(function(){
		listMessage();
	});
});

function listMessage(){ 
  	$('#messageListTable').datagrid({
	    loadMsg: '正在处理，请稍待...',
	    iconCls: 'icon-datashare',//图标 
		scrollbarSize:450,
		striped: true,
		url: '/messagemanager/listMessage.action', // + $("#messageform").formSerialize(),
//		url: 'attendanceManage.json',
		pageNumber:1,		
		singleSelect: true,
		pageSize:15,
		pageList:[15,30,45,60],		
		columns:[[
			{field:'name',title:'联系人',width:100},
			{field:'fax',title:'电话', width:100},
			{field:'email',title:'电子邮件',width:200},		
			{field:'createdDate',title:'提交日期',width:150},
			{field:'content',title:'提交日期留言',width:300}			
		]],
		onDblClickRow: function(rowIndex,rowData){		    
		},
		pagination:true,
		rownumbers:true	
	});
}