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
			var loginName = $("#loginName").val();
			if(loginName==''){
				$("#msgLoginName").text("请输入登录名！");
				$("#loginName").focus();
				return;
			}
			
			var userPwd = $("#userPwd").val();
			if(userPwd==''){
				$("#msgPwd").text("请输入登录密码！");
				$("#userPwd").focus();
				return;
			}
			var userName = $("#userName").val();
			if(userName==''){
				$("#msgUserName").text("请输入用户名！");
				$("#userName").focus();
				return;
			}
			
			var email = $("#email").val();
			if(email==''||!email.isEmail()){
				$("#msgEmail").text("请输入有效邮箱例如:xxx@xxx.com！");
				$("#email").focus();
				return;
			}
			
			//提交后台Action
			$.ajax({
				type : 'POST',
				url : path+'submitAdd', //通过url传递name参数
				data : {
					loginName:loginName,
					userPwd:userPwd,
					userName:userName,
					email:email,
					nickname:$("#nickname").val(),
					status:1
					},
				dataType : 'json',
				success:function(data){
					if(data.status){
						//跳转到登陆页面
						document.location.href = path+"views/user_list.jsp";
					}else{
						$("#resultMsg").text(data.description);
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
		}
		
		function cancle(){
			$("#resultMsg").text('');
			history.back(-1);
		}
	</script>
	
	<body class="shell" onkeydown="if(event.keyCode == 13){sumbit();}">
		<!-- Box -->
		<div class="box">
			<!-- Box Head -->
			<div class="box-head">
				<h2>新用户注册</h2>
			</div>
			<!-- End Box Head -->
			
			<form action="" method="post">
				<!-- Form -->
				<div class="form">
					<span class="req">不能为空,最长30个字符</span>
					<label>登陆名: <span style="color:red;" id="msgLoginName">(系统登陆账号:必填)</span></label>
					<input maxlength="30" type="text" class="field size4" id="loginName"/>
					
					<span class="req">不能为空,最长30个字符</span>
					<label>密&nbsp;码 :<span style="color:red;" id="msgPwd">(必填)</span></label>
					<input maxlength="30" type="password" class="field size4" id="userPwd"/>
					
					<span class="req">不能为空,最长50个字符</span>
					<label>用户名: <span style="color:red;" id="msgUserName">(标识您的身份的:必填)</span></label>
					<input maxlength="50" type="text" class="field size4" id="userName"/>
					
					<span class="req">最长30个字符</span>
					<label>昵&nbsp;称: <span style="color:red;" id="msgNickName">(选填)</span></label>
					<input maxlength="30" type="text" class="field size4" id="nickname"/>
					
					<span class="req">不能为空,最长100个字符</span>
					<label>邮&nbsp;箱 :<span style="color:red;" id="msgEmail">(密码找回使用:必填)</span></label>
					<input maxlength="100" type="text" class="field size4" id="email"/>
					<br/>
					<h2><span id="resultMsg" style="color:red;"></span></h2>
					<br/>
				</div>
				<!-- End Form -->
				
				<!-- Form Buttons -->
				<div class="buttons">
					<input type="button" onClick="sumbit();" class="button" value="注册" />
					<input type="button" onClick="cancle();" class="button" value="取消" />
				</div>
				<!-- End Form Buttons -->
			</form>
		</div>
		<!-- End Box -->
	</body>
</html>