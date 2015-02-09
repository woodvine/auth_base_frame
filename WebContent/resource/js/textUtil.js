/**
 * 文本框设置时如果对象为null返回""
 * 
 * @param value
 * @returns
 */
function formatText(value) {
	return value == null ? "" : value;
}

function formatBlankText(val ){
	if(val == null || val == undefined){
		return '';
	}
	return val;
}

var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
function fileChange(target,filetypes,sizeLimit) {
	var filepath = target.value;
	var index = -1;
	if (filepath) {
		var isnext = false;
		var fileend = filepath.substring(filepath.indexOf("."));
		if (filetypes && filetypes.length > 0) {
			for ( var i = 0; i < filetypes.length; i++) {
				if (filetypes[i] == fileend) {
					isnext = true;
					index = i;
					break;
				}
			}
		}
		if (!isnext) {
			alert("仅支持"+filetypes+"等类型的文件，请重新选择!");
			target.value = "";
			return false;
		}
	} else {
		return false;
	}
	
	var fileSize = 0;
	if (isIE && !target.files) {
		var filePath = target.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		if (!fileSystem.FileExists(filePath)) {
			alert("附件不存在，请重新输入！");
			return false;
		}
		var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}

	//文件大小校验
	var size = fileSize / 1024;
	var filemaxsize = sizeLimit[index]*1024;
	if (size > filemaxsize) {
		alert(filetypes[index]+"类型的附件大小不能大于" + filemaxsize / 1024 + "M！");
		target.value = "";
		return false;
	}
	if (size <= 0) {
		alert("附件大小不能为0M！");
		target.value = "";
		return false;
	}
}

/**
 * 当前时间yyyy-MM-dd HH:mm:ss
 */
function nowAsString(){
	var str = "";
	var myDate = new Date();
	str+=myDate.getFullYear(); //获取完整的年份(4位,1970-????)
	str+="-";
	str+=myDate.getMonth()+1; //获取当前月份(0-11,0代表1月)
	str+="-";
	str+=myDate.getDate(); //获取当前日(1-31)
	str+=" ";
	str+=myDate.getHours(); //获取当前小时数(0-23)
	str+=":";
	str+=myDate.getMinutes(); //获取当前分钟数(0-59)
	str+=":";
	str+=myDate.getSeconds(); //获取当前秒数(0-59)
	
	return str;
}

function formatValue(value){
	if(value==null){
		return "";
	}
	return value;
}

function getRole(code){
	var val ;
	switch(code){
	case "ROLE_ADMIN":
		val = 0;break;
	case "ROLE_MANAGER":
		val = 1;break;
	case "ROLE_EDITOR":
		val = 2;break;
	default:val = 0;
	}
	return val;
}

	/**
	 * 为什么在这个方法之后定义的function就调用不到了呢？？？
	 * 害的我只能把方法都放在它的前面。。。
	 * @returns
	 */
	String.prototype.isEmail = function()
	{
		return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this);
	};