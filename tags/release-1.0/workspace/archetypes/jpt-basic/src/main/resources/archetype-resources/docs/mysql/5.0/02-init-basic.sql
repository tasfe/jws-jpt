DELETE FROM t_menus;
DELETE FROM t_permissions;
DELETE FROM t_resources;
DELETE FROM t_roles;
DELETE FROM t_users;

DELETE FROM t_menu_permis;
DELETE FROM t_permis_res;
DELETE FROM t_role_permis;
DELETE FROM t_user_permis;
DELETE FROM t_user_role;

COMMIT;

INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
('100', '/100', '系统管理', null, null, null, null, null, '99999999');
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(101,'/100/101','用户管理','pages/user/index.html',null,'ico-1.gif',null,100,1);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(102,'/100/102','角色管理','pages/role/index.html',null,'ico-2.gif',null,100,2);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(103,'/100/103','权限管理','pages/permission/index.html',null,'ico-3.gif',null,100,3);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(104,'/100/104','资源管理','pages/resource/index.html',null,'ico-4.gif',null,100,4);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(105,'/100/105','菜单管理','pages/menu/index.html',null,'ico-5.gif',null,100,5);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(106,'/100/106','字典管理','pages/dictionary/list.html',null,'ico-6.gif',null,100,6);
INSERT INTO t_menus(id,code,name,url,suburl,img,target,parent_id,display_order)VALUES
(107,'/100/107','常量管理','pages/constant/index.html',null,'ico-7.gif',null,100,7);
COMMIT;

INSERT INTO t_permissions(id,name,authority)VALUES
(1,'全部权限','AUTH_ALL');
COMMIT;

INSERT INTO t_resources(id,name,url)VALUES
(1,'全部资源','/**');
COMMIT;

INSERT INTO t_roles(id,name,descr)VALUES
(1,'系统管理员','负责设置系统的各种运行参数。');
COMMIT;

INSERT INTO t_users(id,predefined,enabled,name,fullname,password)VALUES
(1,1,1,'administrator','系统管理员','7c4a8d09ca3762af61e59520943dc26494f8941b');
COMMIT;

INSERT INTO t_menu_permis(menu_id,permis_id)
SELECT id,1 FROM t_menus;
COMMIT;

INSERT INTO t_permis_res(permis_id,res_id)VALUES
(1,1);
COMMIT;

INSERT INTO t_role_permis(role_id,permis_id)VALUES
(1,1);
COMMIT;

INSERT INTO t_user_role(user_id,role_id,role_level)VALUES
(1,1,0);
COMMIT;