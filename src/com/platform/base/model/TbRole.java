package com.platform.base.model;

import java.io.Serializable;
import java.util.List;

import com.platform.base.pojo.PaginationBean;

/**
 * 
 * @title :TbRole
 * @description :角色表tb_role
 * @update :2015-1-4 下午2:39:40
 * @author :wang_ll
 * @version :1.0.0
 * @since :2015-1-4
 */
public class TbRole extends PaginationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String  description;
	private String  name;
	private Integer status;//状态标识：1正常、0禁用
	private String  code;  //角色的标识码（用于具有审批流程的应用）
	private List<Integer> menus;//角色关联的菜单
	
	//预留扩展字段
	private String ext;

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public List<Integer> getMenus() {
		return menus;
	}

	public void setMenus(List<Integer> menus) {
		this.menus = menus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("TbRole=[name="+name);
		buffer.append(",id="+id);
		buffer.append(",desc="+description);
		buffer.append(",code="+code);
		buffer.append("]");
		return buffer.toString();
	}
}
