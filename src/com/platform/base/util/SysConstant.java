package com.platform.base.util;

/**
 * 
 * @title       :SysConstant
 * @description :系统常量定义
 * @update      :2015-1-26 上午9:29:04
 * @author      :172.17.5.73
 * @version     :1.0.0
 * @since       :2015-1-26
 */
public class SysConstant {
	 	//====================Session用户key=============================
		public static final String CURRENT_USER = "currentUser";
		public static final String VALIDATE_CODE = "validateCode";
		//====================Session用户key=============================
		
		//===================用户信息更改的类型===============================
		public static final int CHANGE_INFO = 0;  //修改其他信息
		public static final int CHANGE_PWD  = 1;  //修改密码
		public static final int RESET_PWD = 2;//重置密码
		//===================用户信息更改的类型===============================

}
