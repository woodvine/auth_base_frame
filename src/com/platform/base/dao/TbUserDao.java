package com.platform.base.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.platform.base.model.TbMenu;
import com.platform.base.model.TbUser;
import com.platform.base.util.StringUtil;

/**
 * 
 * @title       :TbUserDao
 * @description :user dao
 * 				备注：20150115
 *              可以不继承SqlMapClientDaoSupport，直接用sqlMapClient的方法
 *              如果要使用模板，可以定义模板bean，设置器sqlMapClient属性就OK了。
 *              这个过时的类，总是看着很别扭。。。。。。。不用也罢
 * @update      :2015-1-6 上午11:27:19
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-6
 */
@SuppressWarnings("deprecation")
@Repository
public class TbUserDao  extends SqlMapClientDaoSupport{
	private Logger logger = Logger.getLogger(TbUserDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@PostConstruct
    public void initSqlMapClient(){
	     super.setSqlMapClient(sqlMapClient);
	} 
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbUser> queryByPage(TbUser user) {
		List<TbUser> list = null;
		try {
			list =this.getSqlMapClientTemplate().queryForList("user.queryUserByPage",user);
		} catch (Exception e) {
			logger.error("TbUserDao queryByPage error", e);
		}
		return list;
	}
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	public int queryTotalCount(TbUser user) {
		int totalCount = 0;
		try {
			totalCount =(Integer)this.getSqlMapClientTemplate().queryForObject("user.queryUserCount",user);
		} catch (Exception e) {
			logger.error("TbUserDao queryTotalCount error", e);
		}
		return totalCount;
	}
	
	public TbUser queryByName(String loginName){
		TbUser user = null;
		if(StringUtil.isEmpty(loginName)){
			return user;
		}
		
		try {
			user =(TbUser)this.getSqlMapClientTemplate().queryForObject("user.queryUserByName",loginName);
		} catch (Exception e) {
			logger.error("TbUserDao queryUserByName error", e);
		}
		
		return user;
	}
	
	public boolean insertUser(TbUser user){
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().insert("user.addUser",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao addUser error", e);
			return false;
		}
	}

	/**
	 * 判断某个用户是否存在
	 * @param loginName
	 * @return
	 */
	public boolean isUserExist(String loginName){
		boolean flag = false;
		if(StringUtil.isEmpty(loginName)){
			return flag;
		}
		
		try {
			int count = (Integer)this.getSqlMapClientTemplate().queryForObject("user.isUserExist",loginName);
			return !(count==0);
		} catch (Exception e) {
			logger.error("TbUserDao isUserExist error", e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMenu> queryAuthority(int roleId) {
		List<TbMenu> list = null;
		try {
			list =this.getSqlMapClientTemplate().queryForList("menu.selectMenuByRoleId",roleId);
		} catch (Exception e) {
			logger.error("TbUserDao queryAuthority error", e);
		}
		return list;
	}

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public boolean updateUser(TbUser user) {
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().update("user.updateUser",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao updateUserPwd error", e);
			return false;
		}
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public boolean updateUserLogintime(TbUser user) {
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().update("user.updateUserLogintime",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao updateUserLogintime error", e);
			return false;
		}
	}

	public TbUser queryById(Integer user) {
		if(user==null){
			return null;
		}
		
		try {
			return (TbUser)this.getSqlMapClientTemplate().queryForObject("user.queryUserById",user);
		} catch (Exception e) {
			logger.error("TbUserDao queryUserById error", e);
			return null;
		}
	}
}
