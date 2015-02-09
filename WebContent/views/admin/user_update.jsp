<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/textUtil.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
		function sumbit(){
			var userOldPwd = $("#userOldPwd").val();
			if(userOldPwd==''){
				$("#msgOldPwd").text("原始密码不能为空！");
				$("#userOldPwd").focus();
				return;
			}
			
			var userPwd = $("#newPwd").val();
			if(userPwd==''){
				$("#msgnewPwd").text("新密码不能为空！");
				$("#newPwd").focus();
				return;
			}
			var confirmPwd = $("#confirmPwd").val();
			if(confirmPwd==''){
				$("#confirmMsg").text("确认密码不能为空！");
				$("#confirmPwd").focus();
				return;
			}else if(confirmPwd!=userPwd){
				$("#confirmMsg").text("确认密码和新密码不一致！");
				$("#confirmPwd").focus();
				return;
			}
			
			//提交后台Action
			var id = ${param.id};
			$.ajax({
				type : 'POST',
				url : path+'modifyUserInfo', //通过url传递name参数
				data : {
					id:id,
					userPwd:userPwd,
					userOldPwd:userOldPwd,
					modifyType:1
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						$("#resultMsg").text("密码修改成功!");
					}else{
						$("#resultMsg").text(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function cancel(){
			 history.back();
		}
	</script>
	
	<body class="shell" onkeydown="if(event.keyCode == 13){sumbit();}">
		<!-- Box -->
		<div class="box">
			<!-- Box Head -->
			<div class="box-head">
				<h2>用户修改密码</h2>
			</div>
			<!-- End Box Head -->
			
			<form action="" method="post">
				<!-- Form -->
				<div class="form">
					<span class="req">不能为空,最长30个字符</span>
					<label>原始密码 :<span style="color:red;" id="msgOldPwd">(必填)</span></label>
					<input maxlength="30" type="password" class="field size4" id="userOldPwd"/>
					
					<span class="req">不能为空,最长30个字符</span>
					<label>新密称&nbsp;&nbsp;&nbsp;: <span style="color:red;" id="msgnewPwd">(必填)</span></label>
					<input maxlength="30" type="password" class="field size4" id="newPwd"/>
					<span class="req">不能为空,最长30个字符</span>
					<label>确认密码: <span style="color:red;" id="confirmMsg">(必填)</span></label>
					<input maxlength="30" type="password" class="field size4" id="confirmPwd"/>
					<br/>
					<h2><span id="resultMsg" style="color:red;"></span></h2>
					<br/>
				</div>
				<!-- End Form -->
				
				<!-- Form Buttons -->
				<div class="buttons">
					<input type="button" onClick="sumbit();" class="button" value="保存" />
					<input type="button" onClick="cancel();" class="button" value="返回" />
				</div>
				<!-- End Form Buttons -->
			</form>
		</div>
		<!-- End Box -->
	</body>
</html>