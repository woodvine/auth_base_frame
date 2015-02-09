package com.platform.base.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.base.dao.TbUserDao;
import com.platform.base.model.TbMenu;
import com.platform.base.model.TbUser;
import com.platform.base.pojo.ActionResponse;
import com.platform.base.util.DateTimeUtil;
import com.platform.base.util.EncryptUtil;
import com.platform.base.util.SysConstant;

/**
 * 
 * @title       :UserService
 * @description :用户相关的操作
 * 			           注册、 登陆、退出、权限查询、密码修改（涉及原密码校验）/信息修改
 * @update      :2015-1-26 上午9:26:27
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-26
 */
@Service
public class UserService {
	@Autowired
	private TbUserDao userDao;

	/**
	 * 登陆操作
	 * @param loginName
	 * @param userPwd
	 * @return
	 */
	public ActionResponse login(String loginName,String userPwd){
		ActionResponse response = new ActionResponse();
		TbUser user = userDao.queryByName(loginName);
		if(user==null){
			response.setDescription("用户名和密码不一致!");
		}else if(user.getStatus()==0){
			response.setDescription("该用户已被禁用，如有疑问请致电管理员:58132588！");
		}else if(EncryptUtil.checkByMD5(userPwd, user.getUserPwd())){
			//校验密码
			response.setResult(user);
			response.setStatus(true);
			
			//修改登录时间
			user.setLastLoginTime(DateTimeUtil.nowAsString());
			userDao.updateUserLogintime(user);
		}else{
			response.setDescription("用户名和密码不一致！");
		}
		
		return response;
	}

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public ActionResponse register(TbUser user) {
		ActionResponse response = new ActionResponse();
		String loginName = user.getLoginName();
		if(StringUtils.isEmpty(loginName)){
			response.setDescription("登陆名为不能为空，请重新填写!");
			return response;
		}
		
		//添加之前先校验
		boolean isUserExist = userDao.isUserExist(loginName);
		if(isUserExist){
			response.setDescription("登陆名为"+loginName+"的用户已存在，请重新填写!");
		}else{
			//加密密码信息后存储
			String encPwd = EncryptUtil.encryptByMD5(user.getUserPwd());
			//修正密码为密文
			user.setUserPwd(encPwd);
			boolean flag = userDao.insertUser(user);
			if(flag){
				response.setStatus(true);
			}else{
				response.setDescription("添加用户失败，稍后请重试!");
			}
		}
		return response;
	}

	/**
	 * 用户信息修改，如果是密码修改需要提供原密码进行校验
	 * @param user
	 * @param modifyType 
	 * 		    修改类型1 :密码修改;2:其他信息修改
	 * @return
	 */
	public ActionResponse modifyUserInfo(TbUser user,int modifyType) {
		ActionResponse response = new ActionResponse();
		//密码修改，需判断原密码
		if(SysConstant.CHANGE_PWD==modifyType){
			//原记录
			TbUser oldRecord = userDao.queryById(user.getId());
			String oldPlain = user.getUserOldPwd();
			String oldCyper = oldRecord.getUserPwd();
			if(EncryptUtil.checkByMD5(oldPlain, oldCyper)){
				String newCyper = EncryptUtil.encryptByMD5(user.getUserOldPwd());
				user.setUserPwd(newCyper);
				userDao.updateUser(user);
				response.setStatus(true);
			}else{
				response.setDescription("原密码输入错误,密码修改失败!");
			}
		}else if(SysConstant.RESET_PWD==modifyType){
			String newCyper = EncryptUtil.encryptByMD5(user.getUserPwd());
			user.setUserPwd(newCyper);
			userDao.updateUser(user);
			response.setStatus(true);
		}else{
			userDao.updateUser(user);
			response.setStatus(true);
		}
	    return response;
	}

	public ActionResponse queryByPage(TbUser user){
		ActionResponse response = new ActionResponse();
		int totalCount = userDao.queryTotalCount(user);
		List<TbUser> list = userDao.queryByPage(user);
		user.setTotal(totalCount);
		response.setDescription("OK");
		response.setStatus(true);
		response.setResult(list);
		response.setExt(user);//返回分页信息
		return response;
	}

	/**
	 * 查询用户的菜单权限
	 * @param user
	 * @return
	 */
	public ActionResponse queryAuthority(TbUser user) {
		ActionResponse response = new ActionResponse();
		List<TbMenu> list = userDao.queryAuthority(user.getRoleId());
		response.setResult(list);
		response.setStatus(true);
		return response;
	}

	/**
	 * 修改用户角色信息
	 * @param user
	 */
	public void grantUserRole(Integer userId,Integer roleId) {
		TbUser user = new TbUser();
		user.setId(userId);
		user.setRoleId(roleId);
		userDao.updateUser(user);
	}
}
