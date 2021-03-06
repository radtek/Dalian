<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/commonInclude.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
</head>

<script language="JavaScript">
var windowID = null;
var parentWindow = null;
var userIDInputID = null;
var userNameInputID = null;
var userTableIDInputID = null;
var orgCodeInputID = null;
var orgNameInputID = null;
var orgIDInputID = null;
var onOkMethod = null;
var rootid = "${rootid}"
var orgType = "${orgType}";
var orgLevel = "${orgLevel}";
var orgBizType = "${orgBizType}";
var userPositions = "${userPositions}";
var initFocusOrgId = "${initFocusOrgId}";
var validMessage = "${validMessage}";
var selectedUserNode = "${selectedUserNode}"; 
var searchParameter = "&orgType=" + orgType;
searchParameter += "&orgLevel=" + orgLevel;
searchParameter += "&orgBizType=" + orgBizType;
searchParameter += "&userPositions=" + userPositions;
var treeObject = null;

function doInit(paramArray) {
	$("#searchKey").bind("keydown", function(e) {
		try {
			if (e.keyCode == 13) {
				searchDict();
				event.returnValue = false;
			}
		}
		catch (err) {};
	});

	parentWindow = paramArray['parentWindow'];
	windowID = paramArray['windowID'];
	userIDInputID = paramArray['userIDInputID'];
	userNameInputID = paramArray['userNameInputID'];
	userTableIDInputID = paramArray['userTableIDInputID'];
	orgCodeInputID = paramArray['orgCodeInputID'];
	orgNameInputID = paramArray['orgNameInputID'];
	orgIDInputID = paramArray['orgIDInputID'];
	onOkMethod = paramArray['onOkMethod'];
	var desc = "";
	var maxMessageLength = 70;
	if (getGBLength(validMessage) > maxMessageLength) {
		desc = "当前可以选择&nbsp;<font color='red' title='"+ validMessage +"'>" + getGBLeft(validMessage, maxMessageLength) + "...</font>";
	}
	else {
		desc = "当前可以选择&nbsp;<font color='red'>"+ validMessage +"</font>";
	}
	document.getElementById("descTd").innerHTML = "说明："+ desc;
	
	$('#treeSelect').tree({
		
		data: ${treeJson},

		onClick: function(node) { // 点击结点事件
			var nodeType = node['nodeType'];
			if (nodeType == "01" || nodeType == "02") {
				var loaded = node.loaded;
				if (loaded == "0") {
					loadExpandNode(node); // 异步加载子节点数据
				}
				else {
					if (node.children) { // 点击中间结点自动展开与折叠当前结点
						if (node.state == 'closed') {
							$(this).tree('expand', node.target);
						}
						else {
							$(this).tree('collapse', node.target);
						}
					}
				}
			}
			else {
				if (node.children) { // 点击中间结点自动展开与折叠当前结点
					if (node.state == 'closed') {
						$(this).tree('expand', node.target);
					}
					else {
						$(this).tree('collapse', node.target);
					}
				}
			}
		}
		
	});	
	
	treeObject = $('#treeSelect');
	$(document).ready(function() {
		setSelectedOptions();
	});
}

function loadExpandNode(node) {
	var postData = {};
	postData['orgid'] = node['id'];
	postData['orgType'] = orgType;
	postData['orgLevel'] = orgLevel;
	postData['orgBizType'] = orgBizType;
	postData['userPositions'] = userPositions;
	$.ajax({
		url: contextPath + '/orgUserPublicSelect/expandNode',
		type: 'POST',
		data: postData
	}).done(function(result) {
		if (result) {
			result = parseReturn(result);
			treeObject.tree('append', {
				parent: node.target,
				data: result
			});
		}
		var nodeText = node['text'];
		nodeText = nodeText.replace("◢", "");
		treeObject.tree('update', {
			target: node.target,
			text: nodeText
		});
		node.loaded = "1";
	});
}

function ok_execute() {
	var node = treeObject.tree('getSelected');
	var validNodeSelected = false;
	if (node != null) {
		var nodeType = node['nodeType'];
		if (nodeType == "user") {
			validNodeSelected = true;
		}
	}
	if (validNodeSelected) {
		if (parentWindow.contentWindow) {
			parentWindow = parentWindow.contentWindow;
		}
		if (userIDInputID) {
			parentWindow.document.getElementById(userIDInputID).value = node['bizID'];
			parentWindow.$('#' + userIDInputID).validatebox('validate');
		}
		if (userNameInputID) {
			parentWindow.document.getElementById(userNameInputID).value = node['text'];
			parentWindow.$('#' + userNameInputID).validatebox('validate');
		}
		if (userTableIDInputID) {
			var userTableID = node['id'];
			var userTableID = userTableID.substr(userTableID.indexOf('_') + 1);
			parentWindow.document.getElementById(userTableIDInputID).value = userTableID;
			parentWindow.$('#' + userTableIDInputID).validatebox('validate');
		}
		var parentNode = treeObject.tree('getParent', node.target);
		if (orgCodeInputID) {
			parentWindow.document.getElementById(orgCodeInputID).value = parentNode['bizID'];
			parentWindow.$('#' + orgCodeInputID).validatebox('validate');
		}
		if (orgNameInputID) {
			parentWindow.document.getElementById(orgNameInputID).value = parentNode['text'];
			parentWindow.$('#' + orgNameInputID).validatebox('validate');
		}
		if (orgIDInputID) {
			parentWindow.document.getElementById(orgIDInputID).value = parentNode['id'];
			parentWindow.$('#' + orgIDInputID).validatebox('validate');
		}
	}
	else {
		$.messager.alert('系统信息','请选择一个人员！','error');
		return false;
	}
	return true;
}

function clear_execute() {
	treeObject.tree('select', null);
	if (userIDInputID) {
		parentWindow.document.getElementById(userIDInputID).value = "";
	}
	if (userNameInputID) {
		parentWindow.document.getElementById(userNameInputID).value = "";
	}
	if (userTableIDInputID) {
		parentWindow.document.getElementById(userTableIDInputID).value = "";
	}
	if (orgCodeInputID) {
		parentWindow.document.getElementById(orgCodeInputID).value = "";
	}
	if (orgNameInputID) {
		parentWindow.document.getElementById(orgNameInputID).value = "";
	}
	if (orgIDInputID) {
		parentWindow.document.getElementById(orgIDInputID).value = "";
	}
}

function closeWindow() {
	window.parent.$('#' + windowID).window('close');
}

function setSelectedOptions() {
	if (selectedUserNode != "") {
		var node = treeObject.tree('find', selectedUserNode);
		if (node != null) {
			treeObject.tree('expandTo', node.target);
			treeObject.tree('select', node.target);
			treeObject.tree('scrollTo', node.target);
		}
	}
	else {
		if (initFocusOrgId != "") {
			var node = treeObject.tree('find', initFocusOrgId);
			if (node != null) {
				treeObject.tree('expandTo', node.target);
				treeObject.tree('select', node.target);
				treeObject.tree('scrollTo', node.target);
			}
		}
	}
}

function body_onunload() {
	document.getElementById("treeDiv").innerHTML = "";
}

function searchDict() {
	var searchKeyValue = document.getElementById("searchKey").value;
	searchKeyValue = searchKeyValue.replace(/(^\s*)|(\s*$)/g, "");
	document.getElementById("searchKey").value = searchKeyValue;
	if (searchKeyValue != "") {
		var url = contextPath + '/orgUserPublicSelect/queryPublicOrgUserTreeSearchResult?rootid=' + rootid + searchParameter;
		var resultObject = treeObject.tree('serverSearchTreeNode', {searchKey:searchKeyValue.toUpperCase(), url: url});
		if (resultObject != null) {
			var parentPath = resultObject.parentPath;
			var tempArray = [];
			if (parentPath != "") {
				tempArray = parentPath.split(",");
			}
			tempArray.push(resultObject.id);
			var loadNode = null;
			var loadIdString = "";
			for (var i = 0; i < tempArray.length; i++) {
				var tempNode = treeObject.tree('find', tempArray[i]);
				if (tempNode != null) {
					var loaded = tempNode.loaded;
					if (loaded == "0") {
						loadNode = tempNode;
						for (var j = i; j < tempArray.length; j++) {
							if (tempArray[j].indexOf("_") == -1) { // 部门结点
								loadIdString += tempArray[j] + ",";
							}
						}
						if (loadIdString != "") {
							loadIdString = loadIdString.substring(0, loadIdString.length - 1);
						}
						break;
					}
				}
			}
			if (loadNode != null) {
				$.ajax({
					url: contextPath + '/orgUserPublicSelect/queryPublicOrgUserTreeSearchLoadJson',
					type: 'POST',
					async: false,
					data: "loadIdString=" + loadIdString + searchParameter
				}).done(function(result) {
					if (result) {
						result = parseReturn(result);
						treeObject.tree('append', {
							parent: loadNode.target,
							data: result
						});
					}
					var nodeText = loadNode['text'];
					nodeText = nodeText.replace("◢", "");
					treeObject.tree('update', {
						target: loadNode.target,
						text: nodeText
					});
					loadNode.loaded = "1";
				});
			}
			var locateNode = treeObject.tree('find', resultObject.id);
			if (locateNode != null) {
				treeObject.tree('expandTo', locateNode.target);
				treeObject.tree('scrollTo', locateNode.target);
				treeObject.tree('select', locateNode.target);
			}
		}
		else {
			$.messager.show({
				title: '搜索结果',
				msg: '搜索无匹配的结果！',
				timeout: 1500,
				showType: 'show'
			});
		}
	}
}

</script>

<body style="margin:20px 10px 10px 10px; overflow: hidden;" onunload="body_onunload();">

<div data-options="region:'center',split:true" style="width:760px; border-width: 0px;">
<table border="0" cellspacing="0" cellpadding="0" width="760" align="center">
<tbody>
	<tr><td>
		<table border="0" cellpadding="2" cellspacing="0" width="100%">
		<tr>
			<td align="left" id="tdunvalid" valign="top" width="100%">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td align="right" style="padding-bottom:1px;">
						<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>查询：&nbsp;<input id="searchKey" class="dictSearchInput" maxlength="20" style="width: 230px;" onfocus="this.select();" title="匹配部门名称、人员姓名"></td>
							<td style="padding-left:4px;"><input type="button" value="搜 索" onclick="searchDict()" class="dictSearchButton"></td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="right">
						<div id="treeDiv" class="easyui-panel" style="padding:5px; width: 760px; height: 248px;" onselectstart="return false;">
							<ul class="easyui-tree" id="treeSelect" data-options="method:'get',lines:true,searchServer:true"></ul>
						</div>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</td></tr>
	<tr><td align="center" id="descTd" valign="bottom" height="22">
	</td></tr>
</tbody>
</table>
</div>

</body>
</html>