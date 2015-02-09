var roleInfo = {};
var checkMenuIds = '';
// 标识勾选操作是否执行
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

// zNodes是具有层级关系的一维数组，元素属性列表(id,pId,name,open,checked)
var zNodes = [];

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), py = $("#py").attr(
			"checked") ? "p" : "", sy = $("#sy").attr("checked") ? "s" : "", pn = $(
			"#pn").attr("checked") ? "p" : "", sn = $("#sn").attr("checked") ? "s"
			: "", type = {
		"Y" : py + sy,
		"N" : pn + sn
	};
	/* zTree.setting.check.chkboxType = type; */
	// 复选框选中影响操作：check影响父节点；uncheck影响子节点
	zTree.setting.check.chkboxType = {
		"Y" : "p",
		"N" : "s"
	};
}

function initZTreeNode(){
	$("#grantRoleName").text(roleInfo.name);
	$("#grantRoleCode").text(roleInfo.code);
	$("#grantRoleDesc").text(roleInfo.description);

	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	setCheck();
	$("#py").bind("change", setCheck);
	$("#sy").bind("change", setCheck);
	$("#pn").bind("change", setCheck);
	$("#sn").bind("change", setCheck);
}

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

function getAllChecked() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes();
	for ( var i = 0; i < nodes.length; i++) {
		if (nodes[i].checked) {
			checkMenuIds += nodes[i].id + ",";
		}
	}
}

function saveGrant() {
	// 未重新勾选，权限没有发生变化不予处理
	if (!isOnCheckCalled) {
		return;
	}

	getAllChecked();
	// 判断是否有新选中
	if (checkMenuIds == '') {
		return;
	}

	var roleId = roleInfo.id;
	// 提交权限内容
	$.ajax({
		type : 'POST',
		url : path + 'saveRoleGrant', // 通过url传递name参数
		data : {
			roleId : roleId,
			menuIds : checkMenuIds
		},
		async:false,
		dataType : 'json',
		success : function(data) {
			if (data.status) {
				$("#resultInfo").text("权限信息保存成功!");
			} else {
				alert(data.description);
			}
		},
		error : function(e) {
			alert("Net error ,try later.");
		}
	});
}

function initRoleTree(roleId){
	$.ajax({
		type : 'POST',
		url : path + 'getRoleTreeData', // 通过url传递name参数
		data : {
			roleId : roleId
		},
		async:false,
		dataType : 'json',
		success : function(data) {
			if (data.status) {
				roleInfo = data.ext;
				zNodes = data.result;
				//初始化
				initZTreeNode();
			} else {
				alert(data.description);
			}
		},
		error : function(e) {
			alert("Net error ,try later.");
		}
	});
}
