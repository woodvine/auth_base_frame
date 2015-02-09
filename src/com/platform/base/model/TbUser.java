package com.platform.base.model;

import java.io.Serializable;

import com.platform.base.pojo.PaginationBean;

/**
 * 
 * @title       :TbUser
 * @description :用户表tb_user
 * @update      :2015-1-4 下午2:40:20
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-4
 */
public class TbUser extends PaginationBean 
	implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer status;
	
	//权限控制相关的属性
	private Integer groupId;//权限分组扩展字段
	private Integer roleId;//权限分组扩展字段
	
	private TbRole role;
	
	private String createTime;
	private String lastLoginTime;
	
	//用户个人信息
	private String loginName;
	private String userName;
	private String userPwd;
	private String userOldPwd;
	private String nickname;
	private String email;
	private String picUrl;
	
	//预留扩展字段
	private String ext;
	
	public String getUserOldPwd() {
		return userOldPwd;
	}

	public void setUserOldPwd(String userOldPwd) {
		this.userOldPwd = userOldPwd;
	}

	/** default constructor */
	public TbUser() {
	}

	/** minimal constructor */
	public TbUser(Integer id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public TbRole getRole() {
		return role;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setRole(TbRole role) {
		this.role = role;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("TbUser=[name="+userName);
		buffer.append(",id="+id);
		buffer.append(",roleId="+roleId);
		buffer.append(",loginName="+loginName);
		buffer.append("]");
		return buffer.toString();
	}
}