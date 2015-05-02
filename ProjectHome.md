## 项目简介 ##

---

　　**_J_** (java) **_W_** (web) **_S_** (studio)是一个集成、绿色、简洁的开发环境。JWS通过集成 maven2, ant, eclipse 使开发环境变成全绿色，甚至jdk都无需安装。这样开发团队就可以通过直接拷贝JWS实现开发环境的统一配置，缩短了项目的启动周期，方便了团队新成员的加入。由于JWS只是通过批处理使用所集成工具的核心功能，所以可看作是这些工具的最小组合，避免了由于安装大量插件造成IDE臃肿庞大、启动慢、配置复杂等问题。通过自定义maven插件实现的代码生成操作，灵活方便可扩展。JWS已经历3个内部稳定版本，支持Java代码的编译、打包、调试、执行等，目前只有WIN32下的批处理脚本，随后会增加LINUX下的。

　　**_J_** (java&javascript) **_P_** (project) **_T_** (template)是一个maven项目模板。通过优化组合Spring2, Struts2, IBATIS, Acegi Security, Freemarker, urlrewrite, JSON, JST, jQuery等优秀开源项目实现Web应用快速开发。通过模板生成的代码包含了基本的CRUD操作及客户端&服务端校验，规范了什么代码应该写在哪里，一切基于简单约定，无需任何配置。

　　可以下载 **[jws-jpt默认生成的mytest项目](http://jws-jpt.googlecode.com/files/mytest.rar)** 快速预览jws-jpt效果，下载后解压 mytest.rar，在_<解压目录>_/src/main/webapp/下是静态页面，同时也是DEMO，浏览 index.html 可以看到生成的功能，静态页面展示的与实际启动了Web服务器的效果完全一致，区别只在于静态页面的数据是写在.json文件中模拟数据。分页功能是默认都有的，mytest中只有用户信息的模拟数据符合分页条件，所以静态页面中只有在用户管理中可以看到分页控制。Firefox下浏览静态页面会由于ajax的安全限制看不到效果，请使用IE6以上浏览器。**注意不要阻止本地javascript的执行**。

**JPT所能解决的开发问题**

  * 客户端与服务端全解耦，使得静态DEMO挂上服务端就是实际系统，100%保证DEMO的完全重用，即最初的DEMO即是最终客户端，而模拟数据即是数据库设计的原型；

  * Web与HTTP全解耦，使得更容易与非Web程序兼容；

  * DAO与业务逻辑全解耦，真正体现D-A-O的实际职责。通过动态注入技术使得Service只在需要时才需添加；

  * 完全意义的COC零配置，默认的配置几乎可以适用所有的逻辑，非常特殊的逻辑亦可单独配置；

  * Freemarker与IBATIS的结合，简单而全面地封装了数据库差异，全面支持不同类型数据库，极大提高了SQL的重用程度；

  * 类似REST的URL驱动的开发模式保证增加一个功能要做的只是**“确定一个URL”**，**“写几个模型或叫VO类”**，**“写几个静态页面”**，**“写几条SQL语句”**，而这些又都可以用自定义的maven插件从数据库或xml自动生成；

  * 只需客户端技术和SQL技术即可完成绝大部分的应用逻辑，降低了对团队成员的技术要求，使任务更容易分配；

  * 由数据库结构生成的默认代码，保证开发的规范性和代码结构的一致性；

  * 只写真正需要的代码，全面平衡三层架构各层逻辑分布，去除各层之间传递性调用的尴尬；

  * 优化组合spring2, struts2, ibatis, freemarker, acegi, urlrewite, json, jst, jquery等优秀开源技术，全面提高系统的兼容性和可扩展性；

  * 默认实现了基于acegi security的用户管理，其中的资源缓存思想参考了[springside](http://www.springside.org.cn/)，在此向其团队成员致敬。

## 外部依赖 ##

---

> # [jws-jpt-full-all.rar](ftp://FTP_jws:123456@jws-jpt.jrepo.com/current/jws-jpt-full-all.rar) 已上传，下载了完整版就可跳过安装步骤1,2,3,4,5,9了。 #
> # 由于空间商的限制，最多允许8个线程同时在线，下载时请尽量少开线程。 #
> # 如果无法下载,说明进程满了,稍后再试,或者下载源码,按完整安装步骤配置。 #

　　~~集成后的完整环境本是不需要安装的，但完整环境体积已经200多M，上传比较困难，故需要单独下载各个被集成的工具，其中包括：~~
  * jdk (1.5以上，建议下载 [jdk-6u6-windows-i586-p.exe](https://cds.sun.com/is-bin/INTERSHOP.enfinity/WFS/CDS-CDS_Developer-Site/en_US/-/USD/ViewProductDetail-Start?ProductRef=jdk-6u6-oth-JPR@CDS-CDS_Developer))
  * ant (1.6以上，建议下载 [ant-1.7.0-bin.zip](http://apache.mirror.phpchina.com/ant/binaries/apache-ant-1.7.0-bin.zip))
  * maven2 (2.0.7以上，建议下载 [maven-2.0.9-bin.zip](http://apache.mirror.phpchina.com/maven/binaries/apache-maven-2.0.9-bin.zip))
  * eclipse (3.0以上，建议下载 [eclipse-platform-3.3.2-win32.zip](http://download.actuatechina.com/eclipse/eclipse/downloads/drops/R-3.3.2-200802211800/eclipse-platform-3.3.2-win32.zip) & [eclipse-JDT-3.3.2.zip](http://download.actuatechina.com/eclipse/eclipse/downloads/drops/R-3.3.2-200802211800/eclipse-JDT-3.3.2.zip))。
  * 数据库系统，目前默认生成的项目中包含了mysql和oracle9i的SQL脚本，默认使用mysql。
　　~~随后将发布完整版下载地址，那样就不需要下载上述工具了。~~

## 安装步骤 ##

---

1、下载jws-jpt1.0-src.zip，假设解压后的目录为

&lt;JWS-HOME&gt;

_；_

2、下载外部依赖所列工具。将jdk安装后(如果机器上已经装了1.5以上的jdk，则无需再下载，直接拷贝此目录即可)的目录拷贝到

&lt;JWS-HOME&gt;

_/tools/jdk，ant解压后拷贝到_

&lt;JWS-HOME&gt;

_/tools/ant，maven2解压后拷贝到_

&lt;JWS-HOME&gt;

_/tools/m2，eclipse解压后拷贝到_

&lt;JWS-HOME&gt;

_/tools/eclipse，最后_

&lt;JWS-HOME&gt;

_的目录结构如下：
```
<JWS-HOME>
├─bin
├─docs
├─repository
├─settings
├─tools
│　├─ant
│　│　├─bin
│　│　└─lib
│　├─eclipse
│　│　├─configuration
│　│　├─features
│　│　├─plugins
│　│　└─readme
│　├─jdk
│　│　├─bin
│　│　├─include
│　│　├─jre
│　│　└─lib
│　└─m2
└─workspace
```
3、由于随后的操作要从远程仓库下载依赖库，所以要确保已经联网，而且防火墙没有阻止_

&lt;JWS-HOME&gt;

_/tools/jdk/bin/java.exe；_

4、执行

&lt;JWS-HOME&gt;

_/workspace/plugins/generator/bin/install.bat安装自定义maven2插件；_

5、执行

&lt;JWS-HOME&gt;

_/workspace/archetypes/jpt-basic/bin/install.bat安装项目模板；_

6、执行

&lt;JWS-HOME&gt;

_/bin/generate.bat创建新项目。创建过程中提示选择模板，直接输入1，随后的提示直接回车（如果输入n，则会提示输入具体设置），则会按默认生成名为mytest的项目，数据库默认使用mysql，数据库名mytest，用户名mytest，密码mytest；_

7、使用

&lt;JWS-HOME&gt;

_/workspace/mytest/docs/mysql/5.0下的SQL脚本创建数据库；_

8、执行

&lt;JWS-HOME&gt;

_/workspace/mytest/bin/jetty.bat启动jetty服务器；_

9、上述4,5,6,8由于要远程下载依赖库，所以比较慢，下载的依赖库大概70M，最后

&lt;JWS-HOME&gt;

_的目录结构如下：
```
<JWS-HOME>
├─bin
├─docs
├─repository
│  ├─ant
│  ├─aspectj
│  ├─backport-util-concurrent
│  ├─biz
│  ├─bsh
│  ├─classworlds
│  ├─com
│  ├─commons-beanutils
│  ├─commons-betwixt
│  ├─commons-cli
│  ├─commons-codec
│  ├─commons-collections
│  ├─commons-dbcp
│  ├─commons-digester
│  ├─commons-el
│  ├─commons-fileupload
│  ├─commons-io
│  ├─commons-lang
│  ├─commons-logging
│  ├─commons-pool
│  ├─commons-validator
│  ├─dom4j
│  ├─doxia
│  ├─fmpp
│  ├─freemarker
│  ├─geronimo-spec
│  ├─isorelax
│  ├─javax
│  ├─jaxen
│  ├─jdom
│  ├─jline
│  ├─jmock
│  ├─jpt
│  ├─jtidy
│  ├─junit
│  ├─log4j
│  ├─msv
│  ├─mx4j
│  ├─mysql
│  ├─net
│  ├─opensymphony
│  ├─org
│  ├─oro
│  ├─plexus
│  ├─qdox
│  ├─relaxngDatatype
│  ├─saxpath
│  ├─stax
│  ├─taglibs
│  ├─velocity
│  ├─xerces
│  ├─xml-apis
│  └─xml-resolver
├─settings
├─tools
└─workspace
```
10、启动IE，输入 http://localhost:8080/ 进入登录界面，登录用户administrator，密码123456。如果跳过步骤7,8，也可以直接访问_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/webapp/index.html，浏览静态DEMO；_

11、如果要在eclipse中编辑代码，可执行

&lt;JWS-HOME&gt;

_/workspace/mytest/bin/create-eclipse-project.bat创建eclipse项目文件，然后执行_

&lt;JWS-HOME&gt;

_/install.bat配置eclipse工作区，最后执行_

&lt;JWS-HOME&gt;

_/eclipse.bat启动eclipse，导入mytest项目即可。_

## 自定义maven插件的使用 ##

---

**代码生成功能由maven插件实现，可通过批处理或在eclipse中直接调用**
  * **导出数据库结构为XML**
　　

&lt;JWS-HOME&gt;

_/workspace/mytest/bin/export.bat 可以从数据库结构导出XML到_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/test/resources/template/schema.xml，也可在eclipse中执行export扩展任务。
  * **从XML生成全套CRUD代码**
　　_

&lt;JWS-HOME&gt;

_/workspace/mytest/bin/custom-generate.bat 从_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/test/resources/template/schema.xml 生成全套的页面、sqlmaps、model和模型校验，如果目标目录存在相同文件，则不会覆盖而是将新文件保存到_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/tmp下对应目录，以方便开发者从中拷贝代码进行合并。此步同样对应eclipse中custom-generate扩展任务。
  * **从数据库生成全套CRUD代码**
　　_

&lt;JWS-HOME&gt;

_/workspace/mytest/bin/generate.bat 效果相当于上述两步的综合，不过不会生成schema.xml。对应eclipse中generate扩展任务。_

## 自定义项目模板 ##

---

_&lt;JWS-HOME&gt;_/workspace/mytest/src/test/resources/template/下的文件是代码模板，由freemarker解析，maven插件会根据这里的模板针对数据库表生成对应的代码文件。*****SQL模板_　　针对每张符合条件的数据库表将分别在

&lt;JWS-HOME&gt;_/workspace/mytest/src/main/resources/sqlmaps/declare 和 

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/resources/sqlmaps/statement 下生成T_

&lt;nnnModel&gt;

_.xml，其中nnn为三位数字是对数据库表统一分配的数字做为全局表别名，Model为从表名对应出的模型名。
  * **模型模板**
　　针对每张符合条件的数据库表将在_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/java/_

&lt;package&gt;

_/model下生成_

&lt;Model&gt;

_.java，其中package为生成项目时指定的java包名，这里为jpt，Model为从表名对应出的模型名。
  * **校验模板**
　　针对每张符合条件的数据库表将在_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/resources/_

&lt;package&gt;

_/model下生成_

&lt;Model&gt;

_-validation.xml，其中package为生成项目时指定的java包名，这里为jpt，Model为从表名对应出的模型名。
  * **页面模板**
　　针对每张符合条件的数据库表将在_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/webapps/pages/_

&lt;model&gt;

_下分别生成edit.html,index.html,new.html,show.html，在_

&lt;JWS-HOME&gt;

_/workspace/mytest/src/main/webapps/server/pages/_

&lt;model&gt;

_下分别生成create.json,delete.json,destroy.json,edit.json,index.json,show.json,update.json其中model为从表名对应出的模型名。
  * **其他模板**
　　可以使用freemarker+fmpp语法添加任意的新模板，适应不同类型应用。_

## 意见&建议 ##

---

**本项目完全来源于我实际使用的环境，可能侧重点会有所偏颇，真诚希望大家提出宝贵意见，联系方式：**
  * Email: duanaiguo@gmail.com
  * MSN: duanaiguo@hotmail.com
  * QQ: 394043733