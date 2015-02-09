package com.platform.base.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.base.model.TbUser;
import com.platform.base.pojo.ActionResponse;
import com.platform.base.service.UserService;
import com.platform.base.util.SysConstant;

/**
 * 
 * @title       :UserAction
 * @description :用户管理相关的Action
 * @update      :2015-1-26 上午9:15:40
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-26
 */
@Controller
public class UserAction {
	@Autowired
	private UserService userService;
	
	/**
	 * 登陆
	 * @param loginName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public ActionResponse login(String loginName,String userPwd,HttpSession session){
		ActionResponse response = userService.login(loginName, userPwd);
		if(response.isStatus()){
			response.setExt("frame/index");
			session.setAttribute(SysConstant.CURRENT_USER,response.getResult());
		}
		
		return response;
	}
	
	/**
	 * 输入验证码的登陆
	 * @param loginName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loginNeedValidate")
	@ResponseBody
	public ActionResponse loginNeedValidate(String validateCode,
			String loginName,String userPwd,HttpSession session){
		//验证码验证
		ActionResponse response = new ActionResponse();
		String savedValidateCode = (String)session.getAttribute(
				SysConstant.VALIDATE_CODE);
		if(savedValidateCode==null||!savedValidateCode.equalsIgnoreCase(validateCode)){
			response.setDescription("验证码错误!");
			return response;
		}
		
		//用户身份验证
		response = userService.login(loginName, userPwd);
		if(response.isStatus()){
			response.setExt("frame/index");
			session.setAttribute(SysConstant.CURRENT_USER,response.getResult());
		}
		
		return response;
	}
	
	/**
	 * 退出
	 * @param loginName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public ActionResponse logout(HttpSession session){
		session.setAttribute(SysConstant.CURRENT_USER,null);
		ActionResponse response = new ActionResponse();
		response.setStatus(true);
		return response;
	}
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/toAddUser")
	public ModelAndView toAddUser(){
		ModelAndView view =  new ModelAndView("admin/user_add");
		return view;
	}
	
	/**
	 * 提交注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/submitAdd")
	@ResponseBody
	public ActionResponse submitAdd(TbUser user){
		return userService.register(user);
	}
	
	/**
	 * 注册结果返回，由页面选择跳转或不予处理
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/userManage")
	@ResponseBody
	public ModelAndView userManage(){
		ModelAndView view = new ModelAndView("admin/user_list");
		return view;
	}
	
	/**
	 * 注册结果返回，由页面选择跳转或不予处理
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/queryUsers")
	@ResponseBody
	public ActionResponse queryUsers(TbUser user){
		return userService.queryByPage(user);
	}
	
	/**
	 * 用户信息修改，默认为修改用户信息
	 * modifyType 
	 *         0或者空,修改用户基本信息; 1,修改密码(用户操作);
	 *         2,重置密码(系统管理员操作)
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/modifyUserInfo")
	@ResponseBody
	public ActionResponse modifyUserInfo(TbUser user,Integer modifyType){
		if(modifyType==null){
			modifyType = SysConstant.CHANGE_INFO;
		}
		return userService.modifyUserInfo(user,modifyType);
	}
	
	/**
	 * 为用户授权角色
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/grantUserRole")
	@ResponseBody
	public ActionResponse grantUserRole(Integer userId,Integer roleId){
		ActionResponse r = new ActionResponse();
		if(userId==null||roleId==null){
			r.setDescription("指定用户角色为空!");
		}else{
			userService.grantUserRole(userId,roleId);
			r.setStatus(true);
		}
		return r;
	}
	
}
