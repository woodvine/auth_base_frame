<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="views/admin/base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Welcome to BoadVedio</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/jquery.cookie.js"></script>
	</head>
	<script type="text/javascript">
	var path = '<%=basePath%>';
	$(document).ready(function() {
		//按下回车键自动登录
		 $("#loginName").focus();
	});
	
	function login(){
		var name = $("#loginName").val();
		var pwd = $("#userPwd").val();
		if(name ==''){
			$("#msg").html("用户名不能为空!");
			return;
		}
		if(pwd ==''){
			$("#msg").html("密码不能为空!");
			return;
		}
		
		//数据提交
		$.ajax({
			type : 'POST',
			url : path+'login', //通过url传递name参数
			data : {
					loginName:name,
					userPwd:pwd
				},
			dataType : 'json',
			success:function(data){
				if(data.status){
					window.location.href= path+data.ext;
				}else{
					$("#msg").html(data.description);
				}
			},
			error:function(e){
				alert("Net error ,try later.")
			}
		});
	}
	
</script>
	<body onkeydown="if(event.keyCode == 13){login();}">
  		<div class="login_main">
			<!-- Box Head -->
			<div class="box-head" style="height:40px;">
				<h2 class="logo">用户登录</h2>
			</div>
			<!-- End Box Head -->
			
			<form id="loginForm" method="post">
				<!-- Form -->
				 <ul>
					<li><span style="float:left;">用户名:</span>
					<input id="loginName" name="loginName" type="text" value="" class="field size4" style="margin-left:50px; "/></li>
					<li><span style="float:left;">密&nbsp;&nbsp;码 :</span>
					<input id="userPwd" name="userPwd" type="password" value="" class="field size4" style="margin-left:50px; "/></li>
				    <li style="margin-top:30px;margin-left:50px;">
   						<div id="msg" style="font-size:12px;color:red; height:15px;text-align:center;">${msg }</div>
   					</li>
					<li>
						<input type="button" class="button" style="margin-left:150px;" value="登陆" onclick="login();"/>
						<input type="button" class="button" style="margin-left:10px;" value="忘记密码" onclick="register();"/>
					</li>
				</ul>
				<!-- End Form -->
			</form>
		</div>
	</body>
</html>