<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta name="author" content="miaoff 2012-3-31" />
<meta name="copyright" content="Copyright 2011 eXtremeTeam" />
<title>合众人寿电销系统</title>

<%@ include file="../common/commonHeaderFiles.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/setDataSort.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/verify/simpleCheck.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/setFullEmp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/setDataSource.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/calendar-time.js"></script>
<script type="text/javascript" src="customerSearch.js"></script>
</head>

<body >
<fieldset>
 <table  class="othertable">
	 <tr>
	    <td> 姓&nbsp;&nbsp;名：
	      <input name="txtName" id="txtName" type="text" value="" maxlength="5" size="20" style="width:100px"/>
	    </td>
	    <td> &nbsp;手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：
	      <input id="txtMobilePhone" name="txtMobilePhone" type="text"   style="width:80px"/>
	    </td>
	    <td>&nbsp;身&nbsp;份&nbsp;证：
	      <input name="txtCardNo" id="txtCardNo" type="text" size="20"  style="width:100px"/>
	    </td>
        <td>数据来源：
	      <input id="ds" name="ds" type="text" value=""/>
          <input id="dv" type="hidden" value="">
          <img src="<%=request.getContextPath()%>/images/deptemp.png" onClick="setDataSource()" title="选择数据来源" alt="选择数据来源" align="center" />
	    </td>
	     <td>数据分类：
	     	<input name = "dsort" id = "dsort" type="text" value=""  style="width:85px"/>       
            <input name = "dsortval" id = "dsortval" type="hidden" value="" /> 
            <a onclick="setDataSort()" >
            <input type="image" class="datetime" src="<%=request.getContextPath()%>/images/qijinyong.gif" alt="选择数据分类"/>&nbsp;</a>
	    </td>
	 
	  </tr>
	  <tr>
	    <td>范&nbsp;&nbsp;围：
	        <input name="empname" id="empname" type="text" value="" style="width:105px"/>       
            <input name="empid" id="empid" type="hidden" value="" /> 
            <a onclick="setFullEmp()" >
              <input type="image" class="datetime" src="<%=request.getContextPath()%>/images/deptemp.png" alt="选择范围" 
              		 title="请选择部门" align="middle" />
            </a>   
	    </td>
	    <td>&nbsp;是否成交：
	      <select name="isNotSuccess"  id="isNotSuccess" style="width:80px">
	          <option value="">请选择</option>
	          <option value="1">是</option>
	          <option value="0">否</option>
	      </select>
	    </td>
	    <td>&nbsp;产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品：
	      <select name="saleProduct" id="saleProduct" style="width:100px">
	          <option selected="selected" value="">--全部--</option>
	          <s:iterator id="saleProduct" value="saleProduct">
                 <option value="<s:property value="id"/>"><s:property value="name"/></option>
              </s:iterator>
	      </select>
	    </td>
	    <td> 销售进程：
	    <select id="saleProcessBase"></select>
			<div id="sp">			
			    
				 <s:iterator id="saleProcessBase" value="saleProcessBase">	           
	             <input type="checkbox" name="saleProcess" value="<s:property value="id" />"><span><s:property value="name" /></span><br/>
	          </s:iterator>
			</div>
	      
	    </td>
	    <td>地&nbsp;&nbsp;区：
	      <select name="region" id="region" style="width:80px">
	          <option selected="selected" value="">--全部--</option>
	        <s:iterator id= "dateArea" value="dateArea">
			<option value="<s:property value="id"/>">
				<s:property value="name"/>
			</option>
		    </s:iterator>
	      </select>
	    </td>
	  </tr>
	  <tr>
	    <td colspan="6">
	        预约时间：
	       <input  id="appointBeginDate" name="appointBeginDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
	        到
	       <input id="appointEndDate" name="appointEndDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
	       &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
	        分配时间：
	       <input id="distributedBeginDate" name="distributedBeginDate" style="width:100px" value="" onclick="setdatetime(this)" readonly/>
	         到
	       <input id="distributedEndDate" name="distributedEndDate" style="width:100px" value="" onclick="setdatetime(this)" readonly/> 
	      &nbsp;&nbsp; 
	      回收日至：
	       <input id="lastReturnDate" name="lastReturnDate"  style="width:120px" value="" onclick="setdatetime(this)" readonly/>
		    
	    </td>
	  </tr>
	  <tr> 
	  <td colspan="4">
	      调配时间：
	       <input id="routineBeginDate" name="routineBeginDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
	         到
	       <input id="routineEndDate" name="routineEndDate" style="width:100px" value="" onclick="setdatetime(this)" readonly/> 
	      &nbsp;&nbsp; 最后接通时间：
	       <input id="lastCallBeginDate" name="lastCallBeginDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
		   到
	       <input id="lastCallEndDate" name="lastCallEndDate"  style="width:100px" value="" onclick="setdatetime(this)" readonly/>
		 
		</td>
		<td>
		<input id="empRoleid" type="hidden" value="<s:property value='EmpRoleId'/>" />
		  <input name="queryBtn" id="queryBtn" type="button" onclick="customerSearchFtn();" class="inputbutton" value="检&nbsp;&nbsp;&nbsp;索" />
	    </td>
	  </tr>  
	</table>
	</fieldset>
	<br />	
  	
 <jsp:include page="../common/thirdPermission.jsp" flush="true"/>
 
 <table id="customerSearchTable"></table>
	       
</body>
</html>
