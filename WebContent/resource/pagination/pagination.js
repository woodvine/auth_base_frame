/**
 * 分页插件说明：分页DIV的id=pageShow 
 * 分页内容 :
 * <div class="pagging" id="pageShow" > 
 *   <div class="left">总页数，当前页数</div> 
 *   <div class="right"> 
 *       <a href="#">上一页</a> 
 *       <a  href="#">首页</a> 
 *       <span>...</span> 
 *       <a href="#">2</a> 
 *       <a href="#">3</a> 
 *       <a href="#">4</a> 
 *       <span>...</span> 
 *       <a href="#">尾页</a> 
 *       <a href="#">下一页</a>
 *    </div> 
 * </div>
 */

var total=0;     //记录总数
var _currentPage=1;//当前显示的是第几页
var _pageSize=10;  //每页显示的记录总数
var _totalPage=0;  //记录的总页数
var remainder;    //计算总页数的余数
var funName ;     //分页回调方法
//分页按钮显示的数目，超过11页后以当前页为界，前5页后5页
var pageBtnCount = 11;

/**
 * 分页展示函数
 * @param functionName
 * @param count
 */
function pageShow(functionName,count){
	funName = functionName+"()";
	total=count;
	remainder=total%_pageSize;
	if(remainder==0){
		_totalPage=total/_pageSize;
	}else{
		_totalPage=parseInt(total/_pageSize)+1;
	}
	
	//分页控件内容
	var content="";
	if(count==0){
		content+="<div class='left'>没有相关记录!</div>";
	}else{
		content+="<div class='left'>总记录数"+total+",总页数"+_totalPage+",每页"+_pageSize+"条,当前是第"+_currentPage+"页</div>";
		content+="<div class='right'>";
		
		//拼接上、下页
		content+='<a onclick="javascript:onPage()">上一页</a>';
		content+=formatPageBtn();
		content+='<a onclick="javascript:nextPage()">下一页</a>';
		content+='</div>';
	}
	
	//注意：拼脚本调用方法的参数问题：数组类型的参数不需要''的
	$("#pageShow").html(content);
	$("#pageShow").addClass("pagging");
}

/**
 * 拼接分页按钮
 */
function formatPageBtn() {
	var content = "";
	//少于11页，则全部显示
	if (_totalPage <= pageBtnCount) {
		for ( var i = 1; i <= _totalPage; i++) {
			content += '<a onclick="javascript:go(' + i + ');">' + i + '</a>';
		}
		return content;
	}

	// 首页
	content += '<a onclick="javascript:go(' + 1 + ');">' + 1 + '</a>';

	// 头11页
	var lastShow = _totalPage - pageBtnCount;
	if (_currentPage <= pageBtnCount) {
		for ( var i = 2; i <= pageBtnCount; i++) {
			content += '<a onclick="javascript:go(' + i + ');">' + i + '</a>';
		}
		content += '<span>...</span>';
	}
	// 最后11页
	else if (_currentPage > lastShow) {
		content += '<span>...</span>';
		for ( var i = lastShow; i < _totalPage; i++) {
			content += '<a onclick="javascript:go(' + i + ');">' + i + '</a>';
		}
	} 
	//中间，只显示11页
	else {
		// 首页后添加...
		content += '<span>...</span>';
		var midShow = (pageBtnCount - 1) / 2;
		for ( var i = _currentPage - midShow; i <= _currentPage + midShow; i++) {
			content += '<a onclick="javascript:go(' + i + ');">' + i + '</a>';
		}
		// 尾页之前添加...
		content += '<span>...</span>';
	}

	//尾页
	content += '<a onclick="javascript:go(' + _totalPage + ');">' + _totalPage
			+ '</a>';
	return content;
}

function nextPage(){
	_currentPage=_currentPage+1;
	if(_currentPage>_totalPage){
		_currentPage = _totalPage;
	}
	
	eval(funName);
}

function onPage(){
	_currentPage=_currentPage-1;
	if(_currentPage==0){
		_currentPage = 1;
	}
	eval(funName);
}

function firstPage(functionName){
	_currentPage=1;
	functionName=functionName+"()";
	eval(functionName);
}

function lastPage(functionName){
	_currentPage=_totalPage;
	functionName=functionName+"()";
	eval(functionName);
}

function go(jumpPage){
	_currentPage = jumpPage;
	eval(funName);
}
