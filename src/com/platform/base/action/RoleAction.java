package com.platform.base.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.base.dao.TbMenuDao;
import com.platform.base.dao.TbRoleDao;
import com.platform.base.model.TbMenu;
import com.platform.base.model.TbRole;
import com.platform.base.pojo.ActionResponse;
import com.platform.base.pojo.ZTreeNode;
import com.platform.base.util.StringUtil;

/**
 * 
 * @title       :RoleAction
 * @description :角色管理相关的Action（包括：添加、删除、修改状态、关联菜单 ...）
 * @update      :2015-1-21 上午10:22:25
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-21
 */
@Controller
public class RoleAction {
	@Autowired
	private TbMenuDao menuDao;
	@Autowired
	private TbRoleDao roleDao;
	
	@RequestMapping(value="roleManage")
	@ResponseBody
	public ModelAndView roleManage(){
		ModelAndView view = new ModelAndView("admin/role_main");
		
		int count = menuDao.queryTotal(null);
		view.addObject("emptyMenu",count==0);
		return view;
	}
	
	@RequestMapping(value="roleList")
	@ResponseBody
	public ActionResponse roleList(TbRole role){
		ActionResponse r = new ActionResponse();
		List<TbRole> roles = roleDao.queryAll(role);
		int count = roleDao.queryTotal(null);
		if(roles!=null){
			r.setStatus(true);
			r.setResult(roles);
			role.setTotal(count);
			r.setExt(role);
		}else{
			r.setStatus(false);
			r.setDescription("没有相关记录.");
		}
		return r;
	}
	
	@RequestMapping(value="addRole")
	@ResponseBody
	public ActionResponse addRole(TbRole role){
		ActionResponse r = new ActionResponse();
		roleDao.addRole(role);
		r.setStatus(true);
		return r;
	}
	
	/**
	 * 更改角色状态：不需要某些角色又不想删除时，可以用该功能
	 * @param role
	 * @return
	 */
	@RequestMapping(value="updateRoleStatus")
	@ResponseBody
	public ActionResponse updateRoleStatus(TbRole role){
		ActionResponse r = new ActionResponse();
		roleDao.updateRoleStatus(role);
		r.setStatus(true);
		return r;
	}
	
	/**
	 * 直接删除某个角色
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteRole")
	@ResponseBody
	public ActionResponse deleteRole(Integer id){
		ActionResponse r = new ActionResponse();
		roleDao.deleteRole(id);
		r.setStatus(true);
		return r;
	}
	
	/**
	 * 获取角色详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getRole")
	@ResponseBody
	public ActionResponse getRole(Integer id){
		ActionResponse r = new ActionResponse();
		TbRole role = roleDao.queryById(id);
		if(role!=null){
			r.setResult(role);
			r.setStatus(true);
		}else{
			r.setDescription("没有相关记录!");
		}
		return r;
	}
	
	/**
	 * 角色权限管理：查询所有的菜单，并定位到权限管理页面
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="roleGrant")
	@ResponseBody
	public ModelAndView roleGrant(Integer roleId){
		ModelAndView view = new ModelAndView("admin/role_grant");
		TbRole role = roleDao.queryById(roleId);
		if(role!=null){
			String json = StringUtil.toJson(role);
			view.addObject("roleInfo",json);
		}
		
		//所有菜单信息	
		List<TbMenu> menus = menuDao.queryAllMenuIds();
		List<ZTreeNode> nodes = new ArrayList<ZTreeNode>();
		List<Integer> roleMenus = role.getMenus();
		for (TbMenu menu : menus) {
			Integer parentId = menu.getParentId();
			Integer id = menu.getId();
			boolean open = (parentId==0);
			boolean checked = roleMenus.contains(id);
			ZTreeNode node = new ZTreeNode(menu.getName(), id,
					parentId, checked, open);
			nodes.add(node);
		}
		
		view.addObject("zNodes",StringUtil.toJson(nodes));
		return view;
	}
	
	/**
	 * 角色权限管理：查询所有的菜单
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="getRoleTreeData")
	@ResponseBody
	public ActionResponse getRoleTreeData(Integer roleId){
		ActionResponse response = new ActionResponse();
		TbRole role = roleDao.queryById(roleId);
		if(role!=null){
			response.setExt(role);
		}
		
		//所有菜单信息	
		List<TbMenu> menus = menuDao.queryAllMenuIds();
		List<ZTreeNode> nodes = new ArrayList<ZTreeNode>();
		List<Integer> roleMenus = role.getMenus();
		for (TbMenu menu : menus) {
			Integer parentId = menu.getParentId();
			Integer id = menu.getId();
			boolean open = (parentId==0);
			boolean checked = roleMenus.contains(id);
			ZTreeNode node = new ZTreeNode(menu.getName(), id,
					parentId, checked, open);
			nodes.add(node);
		}
		
		response.setStatus(true);
		response.setResult(nodes);
		return response;
	}
	
	/**
	 * 保存角色权限：关联角色表和菜单表
	 * @description 简化操作，直接取消原理的关联关系后重新设置关联
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value="saveRoleGrant")
	@ResponseBody
	public ActionResponse saveRoleGrant(Integer roleId,String menuIds){
		ActionResponse r = new ActionResponse();
		//先清除原来的映射关系
		roleDao.uncombineRoleAndMenu(roleId);
		//再重新添加新的映射关系
		String[] ids = menuIds.split(",");
		if(ids!=null&&ids.length>0){
			for(String id:ids){
				roleDao.combineRoleAndMenu(roleId, Integer.parseInt(id));
			}
		}
		r.setStatus(true);
		r.setDescription("权限修改成功");
		return r;
	}
}
