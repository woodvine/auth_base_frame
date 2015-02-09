<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<% 
     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Welcome to WoodVine</title>
	<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" type="text/css" media="all" />
	<link rel="icon" href="<%=basePath%>resource/images/msg-ok.gif" type="image/gif" >
	<script type="text/javascript" src="<%=basePath%>resource/js/DateFormat.js"></script>
	<script type='text/javascript' src="<%=basePath%>resource/js/jquery-1.8.2.min.js"></script>
</head>
<script type="text/javascript">
	var authoritys = ${authoritys};
	//一级菜单变量
	var topMenus = [];
	var path = '<%=basePath%>';
	var user = ${currentUser};
	var userName = '';
	$(document).ready(function(){
		//顶端的操作连接		
		loadToolbar();
		//真正的菜单
		loadMenus(authoritys);
	});
	
	function loadToolbar(){
		var flag = true;
		if(user==null||user.userName==null||user.userName==''){
		}else{
			//显示用户操作
			userName = user.userName;
			flag = false;
		}
		
		var titleStr = userName+' 您好，今天是' + getSystemcnLongDate();
		var toolBar = '';
		if(flag){
			toolBar +='<strong >'+titleStr+'</strong>';
			toolBar +='<span>&nbsp;|&nbsp;</span>';
			toolBar +='<a href="javascript:login();">登陆</a>';
		}else{
			toolBar +='<strong >'+titleStr+'</strong>';
			toolBar +='<span>&nbsp;|&nbsp;</span>';
			toolBar +='<a href="javascript:changePwd();">修改密码</a>';
			toolBar +='<span>&nbsp;|&nbsp;</span>';
			toolBar +='<a href="javascript:loginout();">退出</a>';
		}
		
		$("#top-navigation").html(toolBar);
	}
	
	//初始化菜单
	function loadMenus(authoritys){
		var categoryView =  $("#topMenuUl");
		$("#topMenuUl >li").remove();
		
		//一级菜单查找
		for(var i = 0;i<authoritys.length;i++){
			var authority = authoritys[i];
			if(authority.parentId==0){
				topMenus.push(authority);
			}
		}
		
		//拼接一级菜单,遍历拼接到topMenuUl
		var str = "";
		for(var i = 0;i<topMenus.length;i++){
			var authority = topMenus[i];
			str+=formatTopMenu(authority,i==0);
		}
		categoryView.append(str);
	}
	
	//拼接一级菜单内容
	function formatTopMenu(authority,isSelected){
		var id = authority.id;
		var children = getSubMenus(id);
		var hasNoChild = children.length==0;
		var str = "<li><a id='"+id+"' href='javascript:changeTopMenu("+id+","+hasNoChild+",\""+authority.url+"\")'";
		if(isSelected){
			str += " class='active'";
			if(children.length==0){
				//设置操作区域的菜单
				window.parent.document.getElementById("container").
					src=path+authority.url;
			}else{
				//继续拼接该菜单的子菜单
				loadSubMenus(id);
			}
		}
		str+="><span>"+authority.name+"</span></a></li>";
		return str;
	}
	
	//获取子菜单
	function getSubMenus(id){
		var children = [];
		for(var i = 0;i<authoritys.length;i++){
			var authority = authoritys[i];
			if(authority.parentId==id){
				children.push(authority);
			}
		}
		return children;
	}
	
	/*
	 *  一级菜单点击事件
	 *   1)没有子菜单，则操作区域点击事件跳转到对应的URL
	 *   2)有子菜单，则加载子菜单
	*/
	function changeTopMenu(id,hasNoChild,url){
		//无子菜单，设置操作区域src为该菜单路径
		if(hasNoChild){
			window.parent.document.getElementById("container").src=path+url;
		}
		
		//移除原选中样式
		$("#topMenuUl >li >a").removeClass()
		//重置选中菜单的样式
		$("#"+id).addClass("active");
		//加载子菜单（如果没有字菜单，则清理子菜单列表）
		loadSubMenus(id);
	}
	
	//加载某个菜单的子菜单到二级菜单列表
	function loadSubMenus(id){
		//清理上一次的菜单
		var categoryView =  $("#subMenuUl");
		$("#subMenuUl >li").remove();
		
		//查找子菜单
		var children = getSubMenus(id);
		if(children.length==0){
			return;
		}
		
		//重新修正当前菜单的子菜单
		var str = "";
		for(var i =0;i<children.length;i++){
			var authority = children[i];
			str+=formatSubMenu(authority,i==0);
		}
		categoryView.append(str);
	}
	
	//拼接二级菜单内容
	function formatSubMenu(authority,isSelected){
		var id = authority.id;
		var str = "<li><a id='"+id+"' href='javascript:changeSubMenu("+id+",\""+authority.url+"\")'";
		if(isSelected){
			str += " class='active'";
			//设置操作区域的菜单
			window.parent.document.getElementById("container").
				src=path+authority.url;
		}
		str+="><span>"+authority.name+"</span></a></li>";
		return str;
	}
	
	//二级菜单点击事件:操作区域src执行其对应的URL
	function changeSubMenu(id,url){
		window.parent.document.getElementById("container").src=path+url;
		//移除原选中样式
		$("#subMenuUl >li >a").removeClass()
		//重置选中菜单的样式
		$("#"+id).addClass("active");
	}
	
	//退出
	function loginout(){
		$.ajax({
			type : 'POST',
			url : path+'logout', //通过url传递name参数
			data : {
			},
			dataType : 'json',
			success:function(data){
				if(data.status){
					//跳转到登陆页面
					if(window.parent.frames.length >0) {
						window.parent.document.location.href = path+"login.jsp";
					}else{
						document.location.href = path+"login.jsp";
					}
					
				}else{
					$("#resultMsg").text(data.description);
				}
			},
			error:function(e){
				alert("Net error ,try later.")
			}
		});
	}
	
	function changePwd(){
		//导航为首页
		$("#topMenuUl >li >a").removeClass()
		//重置选中菜单的样式
		$("#topMenuUl >li >a").eq(0).addClass("active");
		//操作区域定位到修改密码
		self.parent.frames["container"].location = path+"views/admin/user_update.jsp?id="+user.id;
	}
</script>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- 用户登录信息栏 -->
		<div id="top">
			<b class="logo">博汇权限控制基础模块</b>
			<div id="top-navigation">
			</div>
		</div>

		<!-- 一级菜单 -->
		<div id="navigation">
			<ul id="topMenuUl">
			</ul>
		</div>
		
		<!-- 二级子菜单 -->
		<div id="subnav">
			<ul id="subMenuUl">
			</ul>
		</div>
	</div>
</div>
<!-- End Header -->
</body>
</html>