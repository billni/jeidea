<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>  
<html>  
<head>  
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
   <meta name="keywords" content="jquery,ui,easy,easyui,web">  
   <meta name="description" content="easyui help you build your web page easily!">  
   <title>jQuery EasyUI CRUD Demo</title>  
   <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">  
   <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">  
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo/demo.css">  
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>  
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/train12306/listTrainTicket.js"></script>         
      <style type="text/css">  
        #fm{  
            margin:0;  
            padding:10px 30px;  
        }  
        .ftitle{  
            font-size:14px;  
            font-weight:bold;  
            padding:5px 0;  
            margin-bottom:10px;  
            border-bottom:1px solid #ccc;  
        }  
        .fitem{  
            margin-bottom:5px;  
        }  
        .fitem label{  
            display:inline-block;  
            width:80px;  
        }  
    </style> 
</head>  
<body>  
    <h2>Basic CRUD Application</h2>  
    <div class="demo-info" style="margin-bottom:10px">  
        <div class="demo-tip icon-tip">&nbsp;</div>  
        <div>Click the buttons on datagrid toolbar to do crud actions.</div>  
    </div>  
      
    <table id="dg" title="My Users" class="easyui-datagrid" style="width:700px;height:250px"  
            url="get_users.php"  
            toolbar="#toolbar" pagination="true"  
            rownumbers="true" fitColumns="true" singleSelect="true">  
        <thead>  
            <tr>  
                <th field="firstname" width="50">First Name</th>  
                <th field="lastname" width="50">Last Name</th>  
                <th field="phone" width="50">Phone</th>  
                <th field="email" width="50">Email</th>  
            </tr>  
        </thead>  
    </table>  
    <div id="toolbar">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>  
    </div>  
      
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
            closed="true" buttons="#dlg-buttons">  
        <div class="ftitle">User Information</div>  
        <form id="fm" method="post" novalidate>  
            <div class="fitem">  
                <label>First Name:</label>  
                <input name="firstname" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Last Name:</label>  
                <input name="lastname" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Phone:</label>  
                <input name="phone">  
            </div>  
            <div class="fitem">  
                <label>Email:</label>  
                <input name="email" class="easyui-validatebox" validType="email">  
            </div>  
        </form>  
    </div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>  
    </div>  
   
   
</body>  
</html>