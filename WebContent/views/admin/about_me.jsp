<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>权限控制基础模块</title>
	<link rel="stylesheet" href="<%=basePath%>resource/css/style.css" type="text/css" media="all" />
</head>
<body>
<div class="info">
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;菜单权限管理应用说明</p>
		<p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1)执行初始化脚本文件init_db.sql，该文件位于目录WebContent/version中。
		</p>
		<p >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)修改数据库连接配置文件：src/config/jdbc_mysql.properties，设置数据库主机地址及用户名和密码。
		</p>
		<p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3)启动应用，进入菜单管理页面，添加系统菜单。
		</p>
		<p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4)进入角色管理页面，添加系统角色。
		</p>
		<p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5)在角色管理页面，角色操作中点击权限管理，关联角色和菜单。
		</p>
		<p>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6)系统用户相关的操作由UserService类提供,用户密码存储和校验以MD5加密方式进行,使用时只需引用该模块jar文件。
	   菜单权限基础功能jar为bh_auth_V20150126.jar
		</p>
	</div>
</body>
</html>