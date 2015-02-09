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
		<script type='text/javascript' src="<%=basePath%>resource/pagination/pagination.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		var userList = [];
		$(document).ready(function(){
			queryUsers();
			initRoleSelect();
		});
		
		function queryUsers(){
			$.ajax({
				type : 'POST',
				url : path+'queryUsers', //通过url传递name参数
				data : {
					    userName:$("#userName").val(),
						page:_currentPage,
						pageSize:_pageSize
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						userList = data.result;
						showTable(data.result);
						//调用分页插件，初始化分页Div
						pageShow("queryUsers",data.ext.total);
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
			if(list==null||list.length==0){
				content+="<tr><td colspan='9'>没有相关数据!</td></tr>";
			}else{
				for(var i = 0;i<list.length;i++){
					var user = list[i];
					//奇偶交错样式
					if(i%2==0){
						content+="<tr>";
					}else{
						content+="<tr class='odd'>";
					}
					/* content+="<td><input type='checkbox' class='checkbox' value='"+user.id+"'/></td>"; */
					/* content+="<td>"+formatValue(user.loginName)+"</td>"; */
					content+="<td>"+formatValue(user.userName)+"</td>";
					content+="<td>"+formatValue(user.email)+"</td>";
					content+="<td>"+formatStatus(user.status,1)+"</td>";
					/* content+="<td>"+formatValue(user.createTime)+"</td>"; */
					content+="<td>"+formatValue(user.lastLoginTime)+"</td>";
					content+='<td>'+formatOperation(user,i)+'</td>';
					content+="</tr>";
				}
			}
			
			$("#tableData").html(content);
		}
		
		function formatValue(value){
			if(value==null){
				return "";
			}
			return value;
		}
		
		function formatStatus(status,type){
			switch(type){
			case 1:
				if(status==1){
					return "正常";
				}else{
					return "已禁用";
				}
			case 2:
				if(status==1){
					return "禁用";
				}else{
					return "启用";
				}
			}
		}
		
		//操作连接拼接
		function formatOperation(user,index){
			var status = user.status
			var statusTable = formatStatus(status,2);
			var str = '';
			if(status==0){//启用
				 str += "<a class='ico del' href='javascript:updateStatus(1,\""+user.id+"\");'>";
			}else{//禁用
				 str += "<a class='ico del' href='javascript:updateStatus(0,\""+user.id+"\");'>";
			}
			str+=statusTable+"</a>";
			str+="<a class='ico edit' href='javascript:resetPwd(\""+user.id+"\");'>重置密码</a>";
			str+="<a class='ico edit' href='javascript:grantRole(\""+index+"\");'>授权</a>";
			return str;
		}
		
		function updateStatus(status,id){
			$.ajax({
				type : 'POST',
				url : path+'modifyUserInfo', //通过url传递name参数
				data : {
						id:id,
						status:status
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						queryUsers();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function resetPwd(id){
			$.ajax({
				type : 'POST',
				url : path+'modifyUserInfo', //通过url传递name参数
				data : {
						id:id,
						userPwd:'123456',
						modifyType:2
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						queryUsers();
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function addUser(){
			window.location.href=path+"views/user_add.jsp";
		}
		
		function grantRole(index){
			var user = userList[index];
			//显示用户信息
			$("#userId").val(user.id);
			$("#curuserName").val(user.userName);
			$("#loginName").val(user.loginName);
			$("#roleId").val(user.roleId);
			//弹出信息框
			$("#div_op").show();
			$(".mask").show();
		}
		
		function initRoleSelect(){
			var roleList = [];
			//请求获取role列表
			$.ajax({
				type : 'POST',
				url : path+'roleList', //通过url传递name参数
				data : {
					},
				dataType : 'json',
				async:false,
				success:function(data){
					if(data.status){
						roleList = data.result;
					}else{
						alert(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
			
			//设置下拉框
			var roleItem = $("#roleId");
			roleItem.find("option").remove();
			roleItem.append("<option value=''>--</option>");
			if (roleList == null || roleList.length < 1) {
				return '';
			}
			
			for ( var i = 0; i < roleList.length; i++) {
				var role = roleList[i];
				roleItem.append("<option value='"+role.id+"'>"+ role.name + "</option>");
			}
		}

		function sumbit() {
			if($("#roleId").val()==''){
				$("#roleId").focus();
				$("#resultMsg").text("指定用户角色不能为空!");
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : path+'grantUserRole', //通过url传递name参数
				data : {
					   userId:$("#userId").val(),
					   roleId:$("#roleId").val()
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						cancel();
						queryUsers();
					}else{
						$("#resultMsg").text(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}

		function cancel() {
			$("#div_op").hide();
			$(".mask").hide();
			$("#resultMsg").text('');
		}
	</script>
	
	<body>
		<div class="shell">
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">用户查询</h2>
						<div class="right">
							<label>用户名</label>
							<input type="text" class="field small-field" id="userName"/>
							<input type="button" class="button" value="查询" onClick="javascript:queryUsers();"/>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table  width="100%" border="0" cellspacing="0" cellpadding="0">
						  <thead>
						  	<tr>
								<th colspan="5">
									<a href="javascript:addUser();" class="add-button"><span>添加新用户</span></a>
								</th>
							</tr>
							<tr>
								<th width="20">用户名</th>
								<th width="25%">邮箱</th>
								<th width="10%">状态</th>
								<th width="25%">最后登录时间</th>
								<th width="20%" class="ac">操作</th>
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
				<!-- End Box -->
		</div>
	    
	    <!-- 弹出框DIV，两个同时显示，mask负责将背景灰掉；div_op负责显示 -->
	    <div class="mask" style="display:none;"></div>
	    <div class="box"  style="display:none;position:absolute;top:50%;left:50%;width: 520px;height:auto;z-index: 999; margin: -150px 0px 0px -260px;" id="div_op">
			<!-- Box Head -->
			<div class="box-head">
				<h2>指定用户角色</h2>
			</div>
			<!-- End Box Head -->
			
			<form action="" method="post">
				<!-- Form -->
				<div class="form">
					<input type="hidden" id="userId" />
					<label>登陆名称: </label>
					<input id="loginName"  readonly type="text" class="field size5"/>
					
					<label>用户名称: </label>
					<input id="curuserName" readonly type="text" class="field size5"/>
					
					<label>用户角色: </label>
					<select id="roleId" name="roleId" class="field size5"  >
				        </select>
					<br/>
					<h2><span id="resultMsg" style="color:red;"></span></h2>
					<br/>
				</div>
				<!-- End Form -->
				
				<!-- Form Buttons -->
				<div class="buttons">
					<input type="button" onClick="sumbit();" class="button" value="保存" />
					<input type="button" onClick="cancel();" class="button" value="取消" />
				</div>
				<!-- End Form Buttons -->
			</form>
		</div>	
	
	</body>
</html>