package com.platform.base.pojo;

/**
 * 
 * @title       :ZTreeNode
 * @description :ZTree树形结构的节点定义
 * @update      :2015-1-21 下午4:25:32
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-21
 */
public class ZTreeNode {
	//树形节点核心树形
	private Integer id;
	private Integer pId;
	private String name;
	
	//允许复选框的熟悉结果标识节点是否被选中
	private boolean checked;
	
	//父节点时标识该节点是否展开
	private boolean open;
	
	//可编辑模式下，是否有右键操作
	private boolean noR;
	
	//有该属性时，点击浏览器会跳转到该url
	private String url;
	
	//其他辅助属性：自定义的，在回调事件中需要的数据
	private String description;
	private String path;
	private String type;
	
	public ZTreeNode(String name,Integer id,Integer pId,boolean checked,boolean open){
		this.name = name;
		this.id = id;
		this.pId = pId;
		this.checked = checked;
		this.open = open;
	}

	public ZTreeNode(String name, Integer id, Integer parentId) {
		this(name,id,parentId,false,false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isNoR() {
		return noR;
	}

	public void setNoR(boolean noR) {
		this.noR = noR;
	}
	
}
