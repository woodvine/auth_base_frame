package com.platform.base.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.platform.base.dao.TbMenuDao;
import com.platform.base.model.TbMenu;
import com.platform.base.pojo.ActionResponse;
import com.platform.base.pojo.ZTreeNode;
import com.platform.base.util.StringUtil;

/**
 * @title       :MenuAction
 * @description :
 * @update      :2015-1-21 上午10:22:39
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-21
 */
@Controller
public class MenuAction {
	@Autowired
	private TbMenuDao menuDao;
	
	/**
	 * 基于zTree的树形结果（可以无限层级添加子菜单）
	 * @return
	 */
	@RequestMapping(value="menuList")
	@ResponseBody
	public ModelAndView menuList(){
		ModelAndView view = new ModelAndView("admin/menu_list");
		//查询所有的菜单记录
		List<TbMenu> list = menuDao.queryAllMenus(null);
		
		//构造zTree.js需要的树形结构数据源
		List<ZTreeNode> nodes = new ArrayList<ZTreeNode>();
		if(list!=null&&list.size()>0){
			for(int i = 0,j=list.size();i<j;i++){
				TbMenu menu = list.get(i);
				Integer parentId = menu.getParentId();
				Integer id = menu.getId();
				ZTreeNode node = new ZTreeNode(menu.getName(), id,
						parentId,false,true);
				//辅助属性
				node.setDescription(menu.getDescription());
				node.setPath(menu.getUrl());
				node.setType(menu.getType());
				nodes.add(node);
			}
		}
		
		//查询出所有的菜单节点，由zTree递归遍历完成层级关系设定
		view.addObject("zNodes",StringUtil.toJson(nodes));
		return view;
	}
	
	@RequestMapping(value="addMenu")
	@ResponseBody
	public ActionResponse addMenu(TbMenu menu){
		ActionResponse r = new ActionResponse();
		menuDao.addMenu(menu);
		r.setStatus(true);
		r.setDescription("添加新菜单成功.");
		return r;
	}
	
	@RequestMapping(value="deleteMenu")
	@ResponseBody
	public ActionResponse deleteMenu(Integer id){
		ActionResponse r = new ActionResponse();
		menuDao.deleteMenu(id);
		r.setDescription("删除菜单成功.");
		r.setStatus(true);
		return r;
	}
	
	@RequestMapping(value="getMenu")
	@ResponseBody
	public ActionResponse getMenu(Integer id){
		ActionResponse r = new ActionResponse();
		TbMenu menu = menuDao.queryMenuById(id);
		if(menu!=null){
			r.setStatus(true);
			r.setResult(menu);
		}else{
			r.setStatus(false);
			r.setDescription("没有相关记录.");
		}
		return r;
	}
	
	@RequestMapping(value="updateMenu")
	@ResponseBody
	public ActionResponse updateMenu(TbMenu menu){
		ActionResponse r = new ActionResponse();
		menuDao.updateMenu(menu);
		r.setStatus(true);
		r.setDescription("保存菜单成功.");
		return r;
	}
}
