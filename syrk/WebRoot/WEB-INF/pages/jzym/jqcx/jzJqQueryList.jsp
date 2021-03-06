<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.founder.framework.config.SystemConfig" %>
<%@include file="/WEB-INF/pages/commonInclude.jsp"%>
<%@ page import="com.founder.framework.base.entity.SessionBean"%>
<%
    SessionBean userInfo = (SessionBean)session.getAttribute("userSession");
    String userOrgCode = "";
    if(userInfo!=null){
        userOrgCode = userInfo.getUserOrgCode();
    }
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>警情查询</title>
</head>
<body class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,border:false">   
            <div data-options="region:'center',split:true,title:'',border:false" style="height:auto">
				<div id="datagridToolbar" style="padding:0px;height:auto">
				<form id="queryForm">
				<table border="0" cellpadding="0" cellspacing="10" width="100%"	align="center">
					<tr class="dialogTr">
				        <!-- 210200000000 -->
						<td width="20%" class="dialogTd" align="right">管辖市(县)局：</td>
						<td width="30%" class="dialogTd"><input id="cc1" class="easyui-combobox" value=""
							         data-options="valueField: 'id',textField: 'text',width:130,url: '<%=basePath %>gzjk/queryOrg',
							         onSelect: function(rec){var url = '<%=basePath %>gzjk/queryOrgLower?orgCode='+rec.id;$('#cc2').combobox('reload', url);}"/>
			            </td>
			            </tr>
			            <tr class="dialogTr">
						<td width="20%" class="dialogTd" align="right">管辖派出所：</td>
						<td width="30%" class="dialogTd">
							<input id="cc2" class="easyui-combobox"
							data-options="valueField:'id', width:130, textField:'text',onSelect: function(rec1){var url = '<%=basePath %>gzjk/queryOrgLowest?orgCode='+rec1.id;$('#cc3').combobox('reload',url);}" />
						</td>
						</tr>
						<tr class="dialogTr">
							<td width="20%" class="dialogTd" align="right">管辖责任区：</td>
							<td width="80%" class="dialogTd" colspan="3">
								<input id="cc3" class="easyui-combobox"
							data-options="valueField:'id', width:130, textField:'text'" />
							</td>
						</tr>
						<tr class="dialogTr">
					        <td width="10%" nowrap="nowrap" align="right">报警日期起：</td>
							<td width="20%">
								<input type="text" name="fqsj" id="fqsj" class="easyui-validatebox"  style="width:130px" 
								 	onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d'})" data-options="required:false,tipPosition:'left',
								 	validType:['date[\'yyyy-MM-dd\']']" style="" />
						</tr>
						<tr class="dialogTr">
							<td width="10%" nowrap="nowrap" align="right">报警日期止：</td>
							<td width="20%">
								<input type="text" name="jssj" id="jssj" class="easyui-validatebox" style="width:130px" 
								 	onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d'})" data-options="required:false,tipPosition:'left',
								 	validType:['date[\'yyyy-MM-dd\']']" style="" />
							</td>
						</tr>
						<tr class="dialogTr" style="display: none;">
						    	<td width="40%" class="dialogTd" align="right">警情类别：</td>
						    	<td width="60%" class="dialogTd">
						    	 
									<input class="easyui-combobox" type="text" id="jqlb" name="jqlb" style="width:130px;" 
									data-options="url: contextPath + '/common/dict/D_JCJ_JQLBDM.js',valueField:'id',textField:'text',selectOnNavigation:false,method:'get'"/>
								<!--
									<input class="easyui-combotree" type="text" id="jqlb" name="jqlb" style="width:130px;"  value="${entity.jqlbdm }"
										data-options="required:true,panelWidth:1230,url: contextPath + '/common/dict/D_JCJ_JQLBDM.js',multiple:false,method:'get',editable:true,lines:true" />
										-->
								</td>
						</tr>
						<tr class="dialogTr">
							    <td width="40%" class="dialogTd" align="right">警情地址：</td>
						    	<td width="60%" class="dialogTd"><input type="text" name="jqfsdz" id ="jqfsdz" style="width:130px;" class="easyui-validatebox" 
						    		data-options="required:false,validType:'maxLength[20]'"   /></td>
									
					   	</tr>
					<tr class="dialogTr">
						<td class="dialogTd" align="center" colspan="4">
								<hr>
								<a href="javascript:void(0)" class="easyui-linkbutton" 
								 	iconCls="icon-reset" onclick="resetButton()">重置</a>
								 &nbsp;
								<a href="javascript:void(0)" class="easyui-linkbutton" 
								 iconCls="icon-search" onclick="queryButton()">查询</a>
					</td>
				</tr>					
				</table>
				</form>
				</div>
			</div>
        </div>
    </div>   
    
    <input type="hidden" id ="zbz" value="">
    <input type="hidden" id ="type" value="">
    <input type="hidden" id ="mapRadius" value="">


</body>
<script type="text/javascript">
<!--
//init
$(function(){
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth()+1+'';
	if(m.length != 2){
		m = '0'+m;
	}
	var d = date.getDate()+'';
	if(d.length != 2){
		d = '0'+d;
	}
	var last = y + "-" + m + "-" + d;
	document.getElementById("fqsj").value = last;
	document.getElementById("jssj").value = last;
});

//查询按钮
function queryButton(){
	var jqlb = document.getElementById("jqlb").value;
	var fqsj = document.getElementById("fqsj").value;
	var jssj = document.getElementById("jssj").value;
	var jqfsdz = document.getElementById("jqfsdz").value;
	var cc1 = document.getElementById("cc1").value;
	var cc2 = document.getElementById("cc2").value;
	var cc3 = document.getElementById("cc3").value;
	var orgcode = "";
	if(cc3!=null && cc3!=''){
		orgcode = cc3;
	} else if(cc2!=null && cc2!=''){
		orgcode = cc2;
	} else if(cc1!=null && cc1!=''){
		orgcode = cc1;
	} else {
		orgcode = <%=userOrgCode%>;
	}
	//var orgcode = parent.frames.orgcodeTotal;
	var parm = "?fqsj=" + fqsj + "&jssj=" + jssj + "&jqlbdm=" + jqlb + "&jqdz=" + jqfsdz + "&gxdwdm=" + orgcode;
	//alert(parm)
	window.parent.zdParm(parm, "jq");
}

//重置按钮
function resetButton(){
	$("#queryForm").form("reset");
}
-->
</script>  

</html>