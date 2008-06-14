安装需求
　　集成后的完整环境本是不需要安装的，但完整环境体积已经200多M，上传比较困难，故需要单独下载各个被集成的工具，其中包括：jdk(1.5以上),ant(1.6以上),maven2(2.0.7以上),eclipse(建议下载eclipse-platform-3.3.2-win32.zip + eclipse-JDT-3.3.2.zip)。随后将发布完整版下载地址，那样就不需要下载上述工具了。

安装步骤
1、下载jws-jpt1.0-src.zip，假设解压后的目录为<JWS-HOME>；
2、下载安装需求所列工具。将jdk安装后(如果机器上已经装了1.5以上的jdk，则无需再下载，直接拷贝此目录即可)的目录拷贝到<JWS-HOME>/tools/jdk，ant解压后拷贝到<JWS-HOME>/tools/ant，maven2解压后拷贝到<JWS-HOME>/tools/m2，eclipse解压后拷贝到<JWS-HOME>/tools/eclipse，最后的目录结构如下所示：
<JWS-HOME>
├─bin
├─docs
├─repository
│  ├─com
│  ├─fmpp
│  └─org
├─settings
│  ├─eclipse
│  │  └─.metadata
│  │      └─.plugins
│  │          ├─org.eclipse.core.runtime
│  │          │  └─.settings
│  │          ├─org.eclipse.debug.core
│  │          │  └─.launches
│  │          └─org.eclipse.debug.ui
│  └─m2
├─tools
│  ├─ant
│  │  ├─bin
│  │  └─lib
│  ├─eclipse
│  │  ├─configuration
│  │  ├─features
│  │  ├─plugins
│  │  └─readme
│  ├─jdk
│  │  ├─bin
│  │  ├─include
│  │  │  └─win32
│  │  ├─jre
│  │  └─lib
│  └─m2
└─workspace
    ├─archetypes
    │  └─jpt-basic
    │      ├─bin
    │      └─src
    │        └─main
    │            └─resources
    │                ├─archetype-resources
    │                │  ├─bin
    │                │  ├─docs
    │                │  │  ├─mysql
    │                │  │  │  └─5.0
    │                │  │  └─oracle
    │                │  │      └─9i
    │                │  └─src
    │                │      ├─main
    │                │      │  ├─java
    │                │      │  │  ├─commons
    │                │      │  │  ├─dao
    │                │      │  │  │  ├─dialect
    │                │      │  │  │  └─ibatis
    │                │      │  │  │      └─ext
    │                │      │  │  ├─model
    │                │      │  │  │  └─helper
    │                │      │  │  ├─security
    │                │      │  │  │  ├─ext
    │                │      │  │  │  ├─filter
    │                │      │  │  │  ├─intercept
    │                │      │  │  │  │  ├─support
    │                │      │  │  │  │  └─web
    │                │      │  │  │  ├─jcaptcha
    │                │      │  │  │  │  └─engine
    │                │      │  │  │  ├─resourcedetails
    │                │      │  │  │  └─service
    │                │      │  │  ├─service
    │                │      │  │  │  └─impl
    │                │      │  │  ├─util
    │                │      │  │  └─webapp
    │                │      │  │      ├─action
    │                │      │  │      ├─filter
    │                │      │  │      ├─interceptor
    │                │      │  │      ├─listener
    │                │      │  │      └─util
    │                │      │  ├─resources
    │                │      │  │  ├─model
    │                │      │  │  ├─sqlmaps
    │                │      │  │  │  ├─declare
    │                │      │  │  │  ├─dialect
    │                │      │  │  │  ├─lib
    │                │      │  │  │  └─statement
    │                │      │  │  └─webapp
    │                │      │  │      └─action
    │                │      │  └─webapp
    │                │      │      ├─cabs
    │                │      │      │  └─public
    │                │      │      ├─images
    │                │      │      │  └─menu
    │                │      │      ├─pages
    │                │      │      │  ├─constant
    │                │      │      │  ├─dictionary
    │                │      │      │  ├─menu
    │                │      │      │  ├─password
    │                │      │      │  ├─permission
    │                │      │      │  ├─resource
    │                │      │      │  ├─role
    │                │      │      │  └─user
    │                │      │      ├─scripts
    │                │      │      │  ├─jquery.ui
    │                │      │      │  │  └─themes
    │                │      │      │  │      ├─dark
    │                │      │      │  │      ├─flora
    │                │      │      │  │      │  └─i
    │                │      │      │  │      ├─light
    │                │      │      │  │      └─vista
    │                │      │      │  │          └─i
    │                │      │      │  └─jscalendar
    │                │      │      │      └─lang
    │                │      │      ├─server
    │                │      │      │  ├─freemarker
    │                │      │      │  ├─pages
    │                │      │      │  │  ├─constant
    │                │      │      │  │  ├─dictionary
    │                │      │      │  │  ├─menu
    │                │      │      │  │  ├─password
    │                │      │      │  │  ├─permission
    │                │      │      │  │  ├─resource
    │                │      │      │  │  ├─role
    │                │      │      │  │  └─user
    │                │      │      │  ├─public
    │                │      │      │  ├─scripts
    │                │      │      │  └─upload
    │                │      │      ├─styles
    │                │      │      │  └─default
    │                │      │      │      └─images
    │                │      │      └─WEB-INF
    │                │      ├─site
    │                │      └─test
    │                │          ├─java
    │                │          │  └─util
    │                │          └─resources
    │                │              └─template
    │                │                  └─lib
    │                └─META-INF
    │                    └─maven
    └─plugins
        └─generator
            ├─bin
            └─src
              └─main
                  └─scripts
3、由于随后的操作要从远程仓库下载依赖库，所以要确保已经联网，而且防火墙没有阻止<JWS-HOME>/tools/jdk/bin/java.exe；
4、执行<JWS-HOME>/workspace/plugins/generator/bin/install.bat安装自定义maven2插件；
5、执行<JWS-HOME>/workspace/archetypes/jpt-basic/bin/install.bat安装项目模板；
6、执行<JWS-HOME>/bin/generate.bat创建新项目。创建过程中提示选择模板，直接输入1，随后的提示直接回车（如果输入n，则会提示输入具体设置），则会按默认生成名为mytest的项目，数据库默认使用mysql，数据库名mytest，用户名mytest，密码mytest；
7、使用<JWS-HOME>/workspace/mytest/docs/mysql/5.0下的SQL脚本创建数据库；
8、执行<JWS-HOME>/workspace/mytest/bin/jetty.bat启动jetty服务器；
9、上述4,5,6,8由于要远程下载依赖库，所以比较慢，下载的依赖库大概70M，最后的<JWS-HOME>/repository的目录结构如下：
repository
├─ant
├─aspectj
├─backport-util-concurrent
├─biz
├─bsh
├─classworlds
├─com
├─commons-beanutils
├─commons-betwixt
├─commons-cli
├─commons-codec
├─commons-collections
├─commons-dbcp
├─commons-digester
├─commons-el
├─commons-fileupload
├─commons-io
├─commons-lang
├─commons-logging
├─commons-pool
├─commons-validator
├─dom4j
├─doxia
├─fmpp
├─freemarker
├─geronimo-spec
├─isorelax
├─javax
├─jaxen
├─jdom
├─jline
├─jmock
├─jpt
├─jtidy
├─junit
├─log4j
├─msv
├─mx4j
├─mysql
├─net
├─opensymphony
├─org
├─oro
├─plexus
├─qdox
├─relaxngDatatype
├─saxpath
├─stax
├─taglibs
├─velocity
├─xerces
├─xml-apis
└─xml-resolver
10、启动IE，输入http://localhost:8080/ 进入登录界面，登录用户administrator，密码123456。如果跳过步骤7，也可以直接访问<JWS-HOME>/workspace/mytest/src/main/webapp/index.html，浏览静态DEMO；
