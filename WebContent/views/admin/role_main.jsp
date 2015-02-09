<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/pagination/pagination.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/textUtil.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/pagination/pagination.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		var rolesList = '';
		var emptyMenu = ${emptyMenu};
		$(document).ready(function(){
			queryList();
		});
		
		function queryList(){
			$.ajax({
				type : 'POST',
				url : path+'roleList', //通过url传递name参数
				data : {
						page:_currentPage,
						pageSize:_pageSize
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						rolesList = data.result;
						showTable(data.result);
						//调用分页插件，初始化分页Div
						pageShow("queryList",data.ext.total);
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function showTable(list){
			var content = "";
			if(list!=null&&list.length>0){
				for(var i = 0;i<list.length;i++){
					var role = list[i];
					//奇偶交错样式
					if(i%2==0){
						content+="<tr>";
					}else{
						content+="<tr class='odd'>";
					}
					content+="<td>"+formatValue(role.name)+"</td>";
					content+="<td>"+role.code+"</td>";
					content+="<td>"+formatValue(role.description)+"</td>";
					var status = role.status==1?'正常':'已禁用';
					content+="<td>"+status+"</td>";
					content+='<td>'+formatOperation(role,i)+'</td>';
					content+="</tr>";
				}
			}
			$("#tableData").html(content);
		}
		
		//操作连接拼接
		function formatOperation(role,index){
			var str = '';
			str+= "<a class='ico edit' href='javascript:grantRole(\""+role.id+"\");'><span>权限管理</span></a>";
			if(role.status==1){
				str+= "<a class='ico ban' href='javascript:updateRoleStatus(\""+role.id+"\",\"0\");'><span>&nbsp;&nbsp;禁用</span></a>";
			}else{
				str+= "<a class='ico unban' href='javascript:updateRoleStatus(\""+role.id+"\",\"1\");'><span>&nbsp;&nbsp;启用</span></a>";
			}
			str+= "&nbsp;&nbsp;<a class='ico del' href='javascript:deleteRole(\""+role.id+"\");'><span>删除</span></a>";
			return str;
		}
		
		function grantRole(roleId){
			if(emptyMenu){
				alert("当前系统没有菜单数据，请先进入菜单管理页面添加菜单!");
				return;
			}
			window.location.href=path+"roleGrant?roleId="+roleId;
		}
		
		function addRole(){
			$(".mask").show();
			$("#addDiv").show();
		}
		
		function cancel(){
			$("#name").val('');
			$("#description").val('');
			$("#code").val('');
			$(".mask").hide();
			$("#addDiv").hide();
		}
		
		function commit(){
			var name = $("#name").val();
			if(name==''){
				$("#msgName").text("角色名称不能为空!");
				$("#name").focus();
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : path+'addRole', //通过url传递name参数
				data : {
						description:$("#description").val(),
						code:$("#code").val(),
						name:name,
						status:1
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						cancel();
						queryList();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function updateRoleStatus(id,status){
			$.ajax({
				type : 'POST',
				url : path+'updateRoleStatus', //通过url传递name参数
				data : {
						id:id,
						status:status
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						queryList();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function deleteRole(id){
			$.ajax({
				type : 'POST',
				url : path+'deleteRole', //通过url传递name参数
				data : {
						id:id
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						queryList();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
	</script>
	
 <body>
	<div class="shell">
			<!-- Box -->
			<div class="box">
				<!-- Box Head -->
				<div class="box-head">
					<h2 class="left">角色列表</h2>
				</div>
				<!-- End Box Head -->	

				<!-- Table -->
				<div class="table">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0">
					  <thead>
					  	<tr>
							<th colspan="5">
								<a href="javascript:addRole();" class="add-button"><span>添加新角色</span></a>
							</th>
						</tr>
						<tr>
							<th width="25%">名称</th>
							<th width="18%">编码</th>
							<th width="25%">描述</th>
							<th width="8%">当前状态</th>
							<th width="24%" class="ac">操作列表</th>
						</tr>
					  </thead>
					  <tbody id="tableData"></tbody>
					</table>
					
					
					<!-- Pagging Div-->
					<div id="pageShow">
					</div>
				</div>
				<!-- Table -->
			</div>
		</div>
		<!-- 弹出框DIV，两个同时显示，mask负责将背景灰掉；div_op负责显示 -->
	    <div class="mask" style="display:none;"></div>
	    <div class="box"  style="display:none;position:absolute;top:50%;left:50%;width: 520px;height:auto;z-index: 999; margin: -150px 0px 0px -260px;" id="addDiv">
				<!-- Box Head -->
				<div class="box-head">
					<h2 class="left">添加新角色(点击保存按钮或者敲击Enter键完成保存)</h2>
				</div>
				
				<form action="" method="post">
					<!-- Form -->
					<div class="form" onkeydown="if(event.keyCode == 13){commit();}">
						<label>名称:<span style="color:red;" id="msgName">(必填)</span></label>
						<input maxlength="30" type="text" class="field size5" id="name"/>
						<label>编码:<span style="color:red;" id="msgName">(选填,包含工作流控制的应用中可利用该字段扩展)</span></label>
						<input maxlength="30" type="text" class="field size5" id="code"/>
						<label>描述:</label>
						<input maxlength="30" type="text" class="field size5" id="description"/>
					</div>
					<div class="buttons">
						<input type="button" onClick="commit();"  class="button" value="保存" />
						<input type="button" onClick="cancel();"  class="button" value="取消" />
					</div>
				</form>
		</div>
		<!-- End Box -->
	</body>
</html>