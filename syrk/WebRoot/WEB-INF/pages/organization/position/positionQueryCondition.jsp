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
	    	<table border="0" cellpadding="0" cellspacing="10" width="816" align="center">
	    	<tr class="dialogTr">
		    	<td width="30%" class="dialogTd" align="right">岗位ID：</td>
		    	<td width="70%" class="dialogTd"><input class="easyui-validatebox" type="text" name="posid" maxlength="100" style="width:200px;" 
		    		data-options="validType:['letterOrNum'],charSet:'halfUpper',invalidMessage:'岗位ID必须为字母或数字组成，请重新输入！'"/>（模糊查询）</td>
	    	</tr>
	    	<tr class="dialogTr">
		    	<td width="30%" class="dialogTd" align="right">岗位名称：</td>
		    	<td width="70%" class="dialogTd"><input class="easyui-validatebox" type="text" name="posname" maxlength="20" style="width:200px;"/>（模糊查询）</td>
	    	</tr>
	    	<tr class="dialogTr">
		    	<td width="30%" class="dialogTd" align="right">岗位描述：</td>
		    	<td width="70%" class="dialogTd"><input class="easyui-validatebox" type="text" name="posdesc" maxlength="200" style="width:200px;"/>（模糊查询）</td>
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
