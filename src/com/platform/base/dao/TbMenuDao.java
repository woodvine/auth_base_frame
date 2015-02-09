package com.platform.base.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.platform.base.model.TbMenu;

@Repository
public class TbMenuDao {
	private Logger logger = Logger.getLogger(TbMenuDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	public TbMenu queryMenuById(Integer id) {
		TbMenu result = null;
		if(id==null){
			return null;
		}
		
		try {
			result = (TbMenu)this.sqlMapClient.queryForObject("menu.selectMenuById",id);
		} catch (Exception e) {
			logger.error("TbMenuDao queryMenuById error", e);
		}
		return result;
	}

	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbMenu> queryAllMenus(TbMenu menu) {
		List<TbMenu> list = null;
		TbMenu condition = menu;
		if(condition==null){
			condition = new TbMenu();
		}
		
		try {
			list =this.sqlMapClient.queryForList("menu.selectAllMenus",condition);
		} catch (Exception e) {
			logger.error("TbMenuDao queryByPage error", e);
		}
		return list;
	}
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbMenu> queryAllMenuIds() {
		List<TbMenu> list = null;
		try {
			list =this.sqlMapClient.queryForList("menu.selectAllMenuIds");
		} catch (Exception e) {
			logger.error("TbMenuDao queryByPage error", e);
		}
		return list;
	}

	public int queryTotal(TbMenu menu) {
		Integer count = 0;
		try {
			count =(Integer) this.sqlMapClient.queryForObject("menu.selectMenuCount",menu);
		} catch (Exception e) {
			logger.error("TbMenuDao queryTotal error", e);
		}
		return count;
	}

	public void addMenu(TbMenu menu) {
		if(menu==null){
			return;
		}
		
		try {
			this.sqlMapClient.insert("menu.addMenu",menu);
		} catch (SQLException execption) {
			logger.error("TbMenuDao addMenu error", execption);
		}
		
	}

	/**
	 * 删除菜单：先删除以之为外键的表记录；再删除当前记录
	 * @param id
	 */
	public void deleteMenu(Integer id) {
		if(id==null){
			return;
		}
		
		try {
			//关联
			this.sqlMapClient.delete("menu.deleteMenuRole",id);
			//子菜单
			this.sqlMapClient.delete("menu.deleteChildren",id);
			//self
			this.sqlMapClient.delete("menu.deleteMenu",id);
		} catch (SQLException execption) {
			logger.error("TbMenuDao deleteMenu error", execption);
		}
	}

	public void updateMenu(TbMenu menu) {
		if(menu==null||menu.getId()==null){
			return;
		}
		
		try {
			this.sqlMapClient.delete("menu.updateMenu",menu);
		} catch (SQLException execption) {
			logger.error("TbMenuDao updateMenu error", execption);
		}
	}
}
