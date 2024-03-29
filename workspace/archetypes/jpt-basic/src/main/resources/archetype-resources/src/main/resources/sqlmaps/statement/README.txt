一、基本规范：
1、对sqlmap文件的注释统一写在此文件中，如有必要，sqlmap文件中可加简短的英文注释。

2、sqlmap文件中不同类型的逻辑块，应以如下的注释符号分隔：
<!--
*************************************************************************************************************
*
*************************************************************************************************************
-->
在中间的一行可以加入简短的英文注释。

3、在具体的sqlmap中不应出现针对特定数据库的语句，针对特定数据库的语句应以SQL片段的形式写在dialect下对应数据库的sqlmap文件中。

4、针对ORM映射的SQL语句的字段和结果类型的声明应统一放在SQLDeclare.xml中。

5、类型别名直接写在sql-map-config.xml中。

二、文件注释：
1、SQLDeclare.xml
-----------------------------------------------------------------------------------------------
|
|	id以小写s开头表示只针对单表，以小写c开头表示针对多表，其中c1\w*表示多表单记录，c2\w*表示多表多记录，其中\w*与正则表达式中同义。
|
-----------------------------------------------------------------------------------------------
|	表别名				|	物理表名					|	字段别名前缀
-----------------------------------------------------------------------------------------------
|	m					|	t_menus					|	menu_
|	p					|	t_permissions			|	permis_
|	rs					|	t_resources				|	res_
|	r					|	t_roles					|	role_
|	u					|	t_users					|	user_
-----------------------------------------------------------------------------------------------

三、常量引用：

1、常量规范
SQL语句中需要引用的常量，例如某个标志值或者特定的初始化数据等，应统一以属性的方式定义在jpt.properties中，并在下面的小节（2、引用的常量）给出说明文字。在SQL中以${常量名}的方式引用。

2、引用的常量
flag.default_level			默认优先级
flag.predefined				预定义标志
flag.predefined2			预定义标志2