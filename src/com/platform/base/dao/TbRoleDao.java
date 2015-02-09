package com.platform.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.platform.base.model.TbRole;

/**
 * 
 * @title       :TbRoleDao
 * @description :DAO类，需要标准Repository，否则不会自动依赖注入的
 * @update      :2015-1-5 下午3:03:04
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-5
 */
@Repository
public class TbRoleDao {
	private Logger logger = Logger.getLogger(TbRoleDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbRole> queryAll(TbRole role) {
		List<TbRole> list = null;
		try {
			list =this.sqlMapClient.queryForList("role.queryAllRoles",role);
		} catch (Exception e) {
			logger.error("TbRoleDao queryAll error", e);
		}
		return list;
	}
	
	public TbRole queryById(Integer id) {
		if(id==null){
			return null;
		}
		TbRole role = null;
		try {
			role =(TbRole)this.sqlMapClient.queryForObject("role.queryRoleById",id);
		} catch (Exception e) {
			logger.error("TbRoleDao queryById error", e);
		}
		return role;
	}
	
	/**
	 * 角色授权
	 * @param roleId
	 * @param menuId
	 */
	public void combineRoleAndMenu(Integer roleId,Integer menuId){
		if(roleId==null||menuId==null){
			return;
		}
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("roleId", roleId);
		map.put("menuId",menuId );
		
		//插入
		try {
			this.sqlMapClient.insert("role.combineRoleAndMenu",map);
		} catch (Exception e) {
			logger.error("TbRoleDao queryById error", e);
		}
	}
	
	public int queryTotal(TbRole role) {
		Integer count = 0;
		try {
			count =(Integer) this.sqlMapClient.queryForObject("role.selectRoleCount",role);
		} catch (Exception e) {
			logger.error("TbRoleDao queryTotal error", e);
		}
		return count;
	}

	public void addRole(TbRole role) {
		if(role==null){
			return ;
		}
		
		try {
			this.sqlMapClient.insert("role.addRole",role);
		} catch (Exception e) {
			logger.error("TbRoleDao addRole error", e);
		}
	}

	public void updateRoleStatus(TbRole role) {
		if(role==null){
			return ;
		}
		
		try {
			this.sqlMapClient.insert("role.updateRoleStatus",role);
		} catch (Exception e) {
			logger.error("TbRoleDao updateRoleStatus error", e);
		}
	}

	public void deleteRole(Integer roleId) {
		if(roleId==null){
			return ;
		}
		
		try {
			//先删除关联表
			this.sqlMapClient.delete("role.uncombineRoleAndMenu",roleId);
			//再删除角色记录
			this.sqlMapClient.delete("role.deleteRoleById",roleId);
		} catch (Exception e) {
			logger.error("TbRoleDao deleteRole error", e);
		}
	}

	public void uncombineRoleAndMenu(Integer roleId) {
		if(roleId==null){
			return ;
		}
		
		try {
			this.sqlMapClient.delete("role.uncombineRoleAndMenu",roleId);
		} catch (Exception e) {
			logger.error("TbRoleDao deleteRole error", e);
		}
	}
}
