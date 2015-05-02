# JWS-JPT FAQ #

---


## 这个项目能做什么？ ##
　　jws是一个开发环境。通过一些脚本和一些默认约定及配置，将WEB项目的编码、编译、打包、测试、调试简洁化，IDE用的是eclipse，但只用最核心的代码编辑等(JDT)功能，避免了由于安装大量插件拖慢eclipse，空占系统内存。

　　jws是一个即拷即用的绿色环境。内嵌了jdk、ant、maven、eclipse，不用担心因为环境变量和别的系统冲突而导致莫名其妙的错误。默认集成了svn、maven的eclipse插件，由于eclipse只使用了platform和JDT，没有别的插件，CVS插件也不包括，当然CVS还是很常用的，你可以单独加进来。目前只支持WIN32环境。

　　jpt是从实际项目中抽出来的一个针对maven的项目模板，目的是将web开发过程细化为用“**纯静态**页面实现表现层”、“Java类实现业务逻辑”、“SQL语句实现数据存取”。三步之间没有直接的耦合，只是通过命名约定关联起来。通过默认的通配配置和spring动态注入实现基于约定的零配置。零配置不是不能配置，而是默认配置和约定可以适用几乎所有情形，非常特殊的可以单独配置。

　　jpt将Web开发中表现层完全剥离出来成为客户端，单独测试、单独演进。服务端逻辑完全独立于HTTP，即使没做过Web开发的程序员也可以轻松实现。数据库操作则完全SQL化。这样开发过程将变成这样：

  * 销售接单子；

  * 系统分析员简单分析下需求初步确定数据结构；

  * 页面设计者使用jws从数据结构生成初始项目并带有默认的crud功能；

  * 页面设计者在系统分析员或项目经理指导下与美工配合基于jpt设计页面，形成Demo界面，与客户沟通重复迭代此步；

  * 客户确认需求；

  * 页面设计者与美工配合进一步美化界面（**注意：此时的页面代码基本就是系统最终的客户端代码，可能会稍有变化的是数据字段的命名**），同时系统分析员与其他开发人员根据界面功能设计功能，根据Demo的模拟数据设计数据库；

  * 程序员开始实现功能（**注意：此时的代码都是纯逻辑的，程序员可以不熟悉html、css、javascript等客户端技术，而且熟悉sql的可以专门负责sql实现**），测试组开始做测试用例，客服组开始编写用户手册，系统分析员、页面设计者和美工开始参与其他项目；

  * 核心功能实现后，提交用户确认，进一步迭代需求。

## subversion下载报错怎么办？ ##
　　如果使用者在WindowsXP操作系统下，在用eclipse插件或者TortoiseSVN客户端下载源代码时，可能会出现如下错误：

　　RA layer request failed

　　svn: REPORT request failed on '/work/!svn/vcc/default'

　　svn: REPORT of '/work/!svn/vcc/default': Could not read response body:

　　改成https连接方式就可以避免了。<sup>_</sup>

## 我下载源代码后，在执行第6步时，出现Searching repository for plugin with prefix:'archetype'问题，是什么原因？ ##
　　由于源码中没有包含依赖的jar库，需要从远程仓库下载，此时如果防火墙阻止了

<JWS\_HOME>

_/tools/jdk/bin/java.exe，就会报此错误。如果使用的是Windows自带的防火墙，可以打开“控制面板-->Windows防火墙”，选择“例外”标签，点击“添加程序”，把_

&lt;JWS-HOME&gt;

/tools/jdk/bin/java.exe选中即可。如果是第三方产品，可以在其相应的防火墙配置中对

&lt;JWS-HOME&gt;

/tools/jdk/bin/java.exe放行即可。

## 我自己有开发工具，我希望用自己的myeclipse、jdk、ant、m2，只用该开发框架可以吗，如何实现？ ##
　　eclipse、jdk、ant、m2这些工具是在

<JWS\_HOME>

/bin/setvars.bat中指定的路径，相应的生成的项目下也有bin/setvars.bat,也要做相应的修改。不过用此框架不建议使用myeclipse，因为本框架中只有编辑java代码会用eclipse来检查语法错误，进行重构，调试以及查看jetty控制台输出等，其他功能根本用不上，甚至JDT都显得有点大。装了myeclipse没有几斤内存是跑不起来的。所以除了jdk可以考虑用本地的（也不推荐），其他都推荐用jws的。我比较喜欢下面的组合：

  * 编辑、调试java用JDT，主要用其重构功能；
  * 编辑html,css用Dreamweaver；
  * 编辑js文件、sqlmap映射文件用notepad++。

## 为什么执行自己生成的sql脚本产生的数据库记录是乱码？ ##
　　项目带的sql文件是UTF-8格式，mysql客户端编码格式需要是UTF-8才能正确执行。可以在my.ini中设置客户端编码如下：
```
[mysql]

default-character-set=utf8
```
　　或者在启动mysql命令时指定。**注意不要直接把sql文件内容拷贝到命令行执行，而要使用加载文件的方式。例如：mysql -uroot -p mytest<02-init-basic.sql，sql文件可以用相对路径或绝对路径，只要保证能在指定的路径找到文件即可。**Navicat for MySQL对UTF-8支持很好，推荐使用。当然也可以用记事本把sql文件另存为ANSI格式。

## 如何将生成的项目导入eclipse ##
　　首先要执行<项目目录>/bin/create-eclipse-project.bat创建eclipse项目文件，然后执行

<JWS\_HOME>

/bin/install.bat设置工作区，这时会在

<JWS\_HOME>

/workspace下生成.metadata文件夹，最后执行

<JWS\_HOME>

/bin/eclipse.bat启动eclipse，选择"File">"Import..."将项目导入eclipse。

## jpt如何封装多种数据库间的差异 ##
　　简单说是用freemarker在编译期间对iBATIS的sqlmap文件进行了预处理，根据pom文件中设置的数据库类型有选择的组合差异性sql。例如“字符串连接”操作，在mysql是concat函数，oracle是||，而sqlserver是+，那么就可以分别在mysql.ftl,oracle.ftl和sqlserver.ftl中用freemarker语法定义concat函数，函数的实现就是针对具体数据的特殊操作。而在sqlmap中进行字符串连接的地方用freemarker语法调用concat函数。编译是自定义的maven插件会根据pom文件中设置的数据库类型来决定包含那个ftl文件。

## jpt如何把一个url对应到一条sql？ ##
  * 客户端javascript对url做统一处理，转换成符合规范的服务端url，并传递必要(method)参数；

  * urlrewrite过滤此url，提取操作参数，转换成类似_<操作类型>_/_<结果类型>_/_<模型>_/_<操作>_的请求转给struts2的action处理；

  * action根据_<操作>_确定要执行的方法，根据_<模型>_确定将请求数据封装成什么对象传给service，根据_<操作类型>_确定传给service什么样的附加参数，根据_<结果类型>_确定result的类型（json或html）；

  * service根据_<模型>_和方法本身确定sql的名字；

  * iBATIS根据名字匹配sql。