package com.platform.base.pojo;

/**
 * 
 * @title       :ActionResponse
 * @description :Action处理返回结果
 * @update      :2015-1-8 上午10:05:24
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-8
 */
public class ActionResponse {
	//处理结果
	private boolean status;
	
	//结果描述
	private String description;
	
	//返回数据
	private Object result;
	
	//扩展字段
	private Object ext;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getExt() {
		return ext;
	}

	public void setExt(Object ext) {
		this.ext = ext;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
