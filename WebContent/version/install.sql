/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.37-log : Database - broad_auth_model
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`broad_auth_model` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `broad_auth_model`;

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `name` varchar(30) NOT NULL COMMENT '操作名称',
  `url` varchar(100) NOT NULL COMMENT '功能地址',
  `t_order` int(2) DEFAULT NULL COMMENT '功能序号',
  `type` int(1) DEFAULT NULL COMMENT '1:菜单；2：功能函数',
  `parent_id` int(20) unsigned DEFAULT '0' COMMENT '父级菜单编号（只有两级功能）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tb_menu` */

insert  into `tb_menu`(`id`,`description`,`name`,`url`,`t_order`,`type`,`parent_id`) values (1,'首页','首页','frame/aboutMe',NULL,NULL,0),(2,'菜单管理','菜单管理','menuList',NULL,NULL,0),(3,'权限管理','权限管理','roleManage',NULL,NULL,0),(4,'用户管理','用户管理','userManage',NULL,NULL,0);

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` smallint(1) DEFAULT '1' COMMENT '状态：默认1正常，0：禁用',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码（用于审批流程扩展的字段)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

insert  into `tb_role`(`id`,`role_name`,`role_desc`,`status`,`role_code`) values (1,'超级管理员','超级管理员',1,'SuperAdmin'),(2,'Admin','admin',1,'管理员'),(3,'测试人员A','测试专用角色',1,'Test');

/*Table structure for table `tb_role_menu` */

DROP TABLE IF EXISTS `tb_role_menu`;

CREATE TABLE `tb_role_menu` (
  `role_id` int(20) NOT NULL COMMENT '角色id',
  `menu_id` int(20) NOT NULL COMMENT '菜单Id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_menu` */

insert  into `tb_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '记录主键，无实际意义',
  `login_name` varchar(30) NOT NULL COMMENT '登陆用户名（必须唯一）',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `user_pwd` varchar(100) NOT NULL COMMENT '登陆密码（存储密文）',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `pic_url` varchar(100) DEFAULT NULL COMMENT '用户头像地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址（找回密码需要使用）',
  `status` int(1) DEFAULT '1' COMMENT '状态（默认1：正常；0：禁用）',
  `role_id` int(10) DEFAULT NULL COMMENT '关联的角色ID',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `last_login_time` int(11) DEFAULT NULL COMMENT '上次登陆时间:Unix时间11位的整数',
  `old_pwd` varchar(30) DEFAULT NULL COMMENT '原密码：密码修改时验证使用',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`login_name`,`user_name`,`user_pwd`,`nickname`,`pic_url`,`email`,`status`,`role_id`,`create_time`,`last_login_time`,`old_pwd`) values (1,'SuperAdmin','SuperAdmin','e10adc3949ba59abbe56e057f20f883e','SuperAdmin',NULL,'admin@126.com',1,1,1422322775,1423449388,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
