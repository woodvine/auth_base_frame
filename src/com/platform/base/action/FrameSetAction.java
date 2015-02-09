package com.platform.base.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.platform.base.model.TbUser;
import com.platform.base.pojo.ActionResponse;
import com.platform.base.service.UserService;
import com.platform.base.util.StringUtil;
import com.platform.base.util.SysConstant;

/**
 * 
 * @title       :FrameSetAction
 * @description :框架首页Action
 *               1 header框架
 *               2 菜单展示
 * @update      :2015-1-6 上午10:42:52
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-6
 */
@Controller
@RequestMapping(value="/frame")
public class FrameSetAction {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/index")
	public ModelAndView index(HttpSession session){
		ModelAndView view = new ModelAndView("admin/index");
		return view;
	}
	
	@RequestMapping(value="/header")
	public ModelAndView header(HttpSession session){
		ModelAndView view = new ModelAndView("admin/main_header");
		List<HeaderMenu> list = new ArrayList<HeaderMenu>();
		
		TbUser user = (TbUser)session.getAttribute(SysConstant.CURRENT_USER);
		ActionResponse response = userService.queryAuthority(user);
		if(response.isStatus()){
			view.addObject("authoritys",StringUtil.toJson(response.getResult()));
		}else{
			view.addObject("authoritys",StringUtil.toJson(list));
		}
		view.addObject(SysConstant.CURRENT_USER,StringUtil.toJson(user));
		
		return view;
	}
	
	@RequestMapping(value="/aboutMe")
	public ModelAndView aboutMe(){
		ModelAndView view = new ModelAndView("admin/about_me");
		return view;
	}
	
	@RequestMapping(value="/footer")
	public ModelAndView footer(){
		ModelAndView view = new ModelAndView("admin/main_footer");
		return view;
	}
	
   //内部类（展示Header的菜单）
   class HeaderMenu{
		private int id;
		private String name;
		private String url;

		public HeaderMenu(int id, String name, String url) {
			this.name = name;
			this.url = url;
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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
	}
}
