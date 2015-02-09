package com.platform.base.pojo;

/**
 * 
 * @title       :PaginationBean
 * @description :分页支持基础Bean
 * @update      :2015-1-8 上午10:09:12
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-8
 */
public class PaginationBean {
	/*
	 * 分页属性
	 */
	private int page = 1;     //当前页
	private int pageSize = 10;//每页总记录数
	private int total;    //总记录数
	private int totalPage;//总页数
	
	//查询开始行数：SQL中利用(limit rowStart,pageSize)分页
	private int rowStart;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		int mode = total%pageSize;
		if(mode==0){
			totalPage = total/pageSize;
		}else{
			totalPage = total/pageSize+1;
		}
		return totalPage;
	}

	public int getRowStart() {
		this.rowStart = (page-1)*pageSize;
		return rowStart;
	}

}
