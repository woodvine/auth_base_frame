<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="base_include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>User List</title>
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/zTree_v3/css/demo.css" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=basePath %>resource/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" media="all" />
		
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery.ztree.exedit-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
		<script type='text/javascript' src="<%=basePath%>resource/js/textUtil.js"></script>
	</head>
	<script type="text/javascript">
		var path = '<%=basePath%>';
  		//执行添加操作标识
  		var isAdd = false;
  		var addType = 1;//添加类型：1，添加同级；2，添加子菜单
		
		var zNodes = ${zNodes};
		var curMenu = null, zTree_Menu = null;
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: this.onNodeCreated,
				beforeCollapse: this.highLight,
				beforeExpand: this.highLight,
				onClick: this.onClick,
				onRemove:this.onRemove
			}
		};
		
		//回调函数定义
		function highLight(treeId, node) {
			//高亮该节点
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.selectNode(node,false);
			
			//展示该节点信息
			showData(node); 
		}
		
		//点击事件，显示被选中菜单的详细信息
		function onClick(e, treeId, node) {
			showData(node); 
		}
		
		$(document).ready(function(){
		    if(zNodes==null||zNodes.length==0){
				$("#noMenusDiv").css("display","block");
  				$("#addBtnDiv").css("display","none");
  				$("#treeDemo").css("display","none");
  				$("#actionInfo").text("添加新菜单");
  				isAdd = true;
  				return;
			}
		    
		    //有菜单数据，则初始化zTree
			initZTree();
		});
	
		function initZTree(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			
			//判断是否有子节点（判空是必须的，否则会报错）
			var zTreeNodes = zTree_Menu.getNodes();
			if(zTreeNodes[0].children!=null&&zTreeNodes[0].children.length>0){
				curMenu = zTreeNodes[0].children[0];
			}else {
				curMenu = zTreeNodes[0];
			}
			//显示第一个菜单的信息
			highLight("treeDemo",curMenu);
		}
		
  		//显示菜单信息
  		function showData(menu){
  			if(menu==null){
  				return;
  			}
  			isAdd = false;
  			$("#actionInfo").text("查看菜单详情");
  			clear();
			$("#id").val(menu.id);
			$("#type").val(menu.type);
			$("#name").val(menu.name);
			$("#parentName").val(menu.name);
			$("#url").val(menu.path);
			$("#description").val(menu.description);
			$("#parentId").val(menu.pId);
  		}
  		
  		//添加菜单:type=1同级；2子菜单
  		function addMenu(type){
  			var info = "为编号为"+$("#id").val()+",名称为"+$("#parentName").val()+"的菜单"
  			if(type==1){
  				$("#actionInfo").text(info+"添加同级菜单");
  			}else{
  				$("#actionInfo").text(info+"添加子菜单");
  			}
  			
  			isAdd = true;
  			addType = type;
  			//数据清空
  			clear();
  		}
  		
  		//提交按钮
  		function sumbit(){
  			if(isAdd){
  				sumbitAdd();
  			}else{
  				//必要时，可以是修改操作
  				sumbitUpdate();
  			}
  		}
  		
  		function sumbitUpdate(){
			var description = $("#description").val();
			var name = $("#name").val();
			var url = $("#url").val();
			
			if(name==''){
				$("#msgName").text("菜单名称不能为空!");
				return;
			}
			if(url==''){
				$("#msgUrl").text("菜单URL不能为空!");
				return;
			}
			
			//根据添加类型，设置菜单的parentId
			var parentId = $("#parentId").val();
			$.ajax({
				type : 'POST',
				url : path+'updateMenu', //通过url传递name参数
				data : {
					id:$("#id").val(),
					parentId:parentId,
					description:description,
					url:url,
					name:name
					},
				dataType : 'json',
				success:function(data){
					//成功后，跳转到操作首页
					if(data.status){
						refresh();
					}else{
						refresh();
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
  		}
  		
  		function sumbitAdd(){
			var description = $("#description").val();
			var name = $("#name").val();
			var url = $("#url").val();
			
			if(name==''){
				$("#msgName").text("菜单名称不能为空!");
				return;
			}
			if(url==''){
				$("#msgUrl").text("菜单URL不能为空!");
				return;
			}
			
			//根据添加类型，设置菜单的parentId
			var parentId = '';
			if(addType=='1'){
				parentId = $("#parentId").val();
				if(parentId==''){
					parentId= '0';
				}
			}else{
				parentId = $("#id").val();
			}
			
			$.ajax({
				type : 'POST',
				url : path+'addMenu', //通过url传递name参数
				data : {
					parentId:parentId,
					description:description,
					url:url,
					name:name
					},
				dataType : 'json',
				success:function(data){
					//成功后，跳转到操作首页
					if(data.status){
						refresh();
					}else{
						refresh();
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
  		}
  		
  		function clear(){
			$("#name").val('');
			$("#url").val('');
			$("#description").val('');
  		}
  		
  		function deletemenu(){
  			var id  = $("#id").val();
  			var info = "您确认要删除编号为"+id+"的菜单及其子菜单吗？";
  			var choice = confirm(info, function() { }, null);
			if(!choice){
				return;
			}
			
  			$.ajax({
				type : 'POST',
				url : path+'deleteMenu', //通过url传递name参数
				data : {
						id:id
				},
				dataType : 'json',
				success:function(data){
					if(data.status){
						refresh();
					}else{
						refresh();
					}
				},
				error:function(e){
					alert("Net error ,try later.")
				}
			});
  		}
  		
  		//菜单结果发生变化，重新刷新页面
  		function refresh(){
  			window.location.href=path+"menuList";
  		}
	</script>
	
 <body onkeydown="if(event.keyCode == 13){sumbit();}">
	  <div class="shell">
			<div class="zTreeDemoBackground left" >
			    <div style="display:none;vertical-align:middle;" id="noMenusDiv">
					<span >目前系统没有菜单数据!</span><br/>
					<span >请在右侧区域添加新菜单.</span>
				</div>
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			
			<!-- 详情 -->
			<div class="box" style="float:left;width:70%;">
				<!-- Box Head -->
				<div class="box-head">
					<h4 class="left">菜单操作页</h4>
					<!-- 没有菜单时，下列按钮不可见 -->
					<div class="right" id="addBtnDiv">
						<a href="javascript:addMenu(1);" class="add-button"><span >添加同级菜单</span></a>
						<a href="javascript:addMenu(2);" class="add-button"><span >添加子菜单</span></a>
						<a href="javascript:deletemenu();" class="add-button"><span >删除菜单</span></a> 
					</div>
				</div>
				<!-- End Box Head -->
				
				<form action="" method="post">
					<!-- Form -->
					<div class="form">
							<input id="id" type="hidden" value="" />
							<input id="parentId" type="hidden" />
							<input id="parentName" type="hidden" />
							<input id="type" type="hidden" value="1"/>
							<p>
								<label>当前操作: <span id="actionInfo">查看菜单详情</span></label>
							</p>						
							<p>
								<label>菜单名称: <span id="msgName"></span></label>
								<input id="name" maxlength="30" type="text" class="field size5" />
							</p>	
							<p>
								<label>菜单URL: <span id="msgUrl"></span></label>
								<input id="url" maxlength="100" class="field size5"></input>
							</p>	
							<p>
								<label>功能描述: </label>
								<textarea id="description" maxlength="100" class="field size5" rows="3" cols="50" ></textarea>
							</p>	
					</div>
					<div class="buttons">
						<input type="button"  class="button" onclick="sumbit();" value="保存" />
						<input type="button"  class="button" onclick="cancel();" value="取消" />
					</div>
					<!-- End Form -->
				</form>
			</div>
		</div>
		
	</body>
</html>