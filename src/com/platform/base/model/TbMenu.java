package com.platform.base.model;

import java.io.Serializable;
import java.util.List;

import com.platform.base.pojo.PaginationBean;

/**
 * 
 * @title       :TbMenu
 * @description :菜单权限TbMenu
 * @update      :2015-1-4 下午2:39:23
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-4
 */
public class TbMenu extends PaginationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//DESC是mysql的关键字
	private String description;
	private String name;
	private String url;
	private List<TbMenu> children;//子菜单列表
	private Integer id;
	private Integer parentId =0;  //父菜单Id
	private Integer typeOrder;
	
	/**
	 * 权限类型：从设计角度分为menu、function、file、element
	 * function(功能点)、file(文件资源)、element(页面元素)，
	 * 需要进行拦截并根据用户权限判断是否放行
	 * 菜单项(menu)，决定了用户操作界面，不需要拦截
	 * 本应用仅实现了菜单类型的权限
	 */
	private String  type;    
	
	private String ext;//扩展字段

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TbMenu> getChildren() {
		return children;
	}

	public void setChildren(List<TbMenu> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "TbMenu [desc=" + description
				+ ", name=" + name + ", url="
				+ url + ", id=" + id + ",parentId="+parentId+", order=" + typeOrder + "]";
	}

}
