<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/commonInclude.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title></title>
</head>

<body> 
<div class="easyui-layout" data-options="fit:true">
    <form action="" id="queryForm" name="queryForm" method="post">
	    <div data-options="region:'center',split:true" style="padding: 6px; border-width: 0px;">
	    	<table border="0" cellpadding="0" cellspacing="10" width="100%" align="center">
	    	<tr class="dialogTr">
		    	<td width="30%" class="dialogTd" align="right">参数类别：</td>
		    	<td width="70%" class="dialogTd"><input class="easyui-combobox" type="text" id="cslb" name="cslb" style="width:200px;"
					data-options="url: contextPath + '/common/dict/D_SYS_CSLB.js',valueField:'id',textField:'text',selectOnNavigation:false,method:'get'"/></td>
	    	</tr>
	    	<tr class="dialogTr">
		    	<td width="30%" class="dialogTd" align="right">参数名称：</td>
		    	<td width="70%" class="dialogTd"><input class="easyui-validatebox" type="text" name="csmc" style="width:200px;"/>（模糊查询）</td>
	    	</tr>
	    	</table>
	    </div>
    </form>
</div>
<script type="text/javascript">
function doInit(paramArray) {
}

function beforeSubmit() {
}
</script>

</body>
</html>
