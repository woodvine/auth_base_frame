<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/zTree_v3/css/demo.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" media="all" />
		
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/textUtil.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		var roleInfo = ${roleInfo};
		var checkMenuIds = '';
		//标识勾选操作是否执行
		var isOnCheckCalled = false;
		var setting = {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onCheck : onCheck
			}
		};

		//zNodes是具有层级关系的一维数组，元素属性列表(id,pId,name,open,checked)
		var zNodes = ${zNodes};

		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), py = $("#py").attr(
					"checked") ? "p" : "", sy = $("#sy").attr("checked") ? "s"
					: "", pn = $("#pn").attr("checked") ? "p" : "", sn = $(
					"#sn").attr("checked") ? "s" : "", type = {
				"Y" : py + sy,
				"N" : pn + sn
			};
			/* zTree.setting.check.chkboxType = type; */
			//复选框选中影响操作：check影响父节点；uncheck影响子节点
			zTree.setting.check.chkboxType = {"Y" : "p","N" : "s"};
		}

		$(document).ready(function() {
			$("#name").val(roleInfo.name);
			$("#desc").val(roleInfo.description);

			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});

		function onCheck(e, treeId, treeNode) {
			isOnCheckCalled = true;
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes(true);
			for ( var i = 0; i < nodes.length; i++) {
				nodes[i].checked = true;
			}
			
			nodes = treeObj.getCheckedNodes(false);
			for ( var i = 0; i < nodes.length; i++) {
				nodes[i].checked = false;
			}
		}

		function getAllChecked(){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes();
			for ( var i = 0; i < nodes.length; i++) {
				if(nodes[i].checked){
					checkMenuIds += nodes[i].id + ",";
				}
			}
		}
		
		function commit() {
			//未重新勾选，权限没有发生变化不予处理
			if(!isOnCheckCalled){
				$("#resultInfo").text("未检测到权限信息变化!");
				return;
			}
			
			getAllChecked();
			//判断是否有新选中
			if(checkMenuIds==''){
				$("#resultInfo").text("请选择菜单!");
				return;
			}
			
			var roleId = roleInfo.id;
			//提交权限内容
			$.ajax({
				type : 'POST',
				url : path+'saveRoleGrant', //通过url传递name参数
				data : {
						roleId:roleId,
						menuIds:checkMenuIds
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						/* window.location.href=path+"roleGrant?roleId="+roleId; */
						$("#resultInfo").text("权限信息保存成功!");
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function goback(){
			window.location.href=path+"roleManage";
		}
	</script>
 <body onkeydown="if(event.keyCode == 13){commit();}">
	<div class="shell">
			<!-- Box -->
				<div class="box" style="width:80%;">
					<!-- Box Head -->
					<div class="box-head">
							<h2 class="left">角色权限管理(请勾选所需的菜单，点击保存按钮或者敲击Enter键完成保存)</h2>
					</div>
					<form action="" method="post">
							<!-- Form -->
							<div class="form">
								<label>角色名称: </label>
								<input id="name"  readonly type="text" class="field size5"/>
								
								<label>角色描述: </label>
								<input id="desc" readonly type="text" class="field size5"/>
								<label>菜单选择: </label>
							</div>
						    <div class="zTreeDemoBackground" style="margin-left:100px;height:300px;">
								<ul id="treeDemo" class="ztree" style="height:80%;"></ul>
							</div>
							<div class="buttons">
									<span id="resultInfo" style="color:green;"></span>
									<span>&nbsp;&nbsp;&nbsp;</span>
									<input type="button" onClick="commit();"  class="button" value="保存" />
									<input type="button" onClick="goback();"  class="button" value="返回" />
							</div>
					</form>
				</div>
			<!-- End Box -->
		</div>
		
	</body>
</html>