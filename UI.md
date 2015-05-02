# JWS-JPT User Interface #

---


## View UI ##
  * html + jst（纯客户端）

  * freemarker

  * jsp

## Javascript UI ##
　　如果html文件中引入了_<项目目录>_/src/main/webapp/scripts/global.js（依赖_<项目目录>_/src/main/webapp/scripts/jquery.js和_<项目目录>_/src/main/webapp/scripts/toolkit.js），则会按默认的控制流程调用下列Javascript接口方法实现。

  * boolean prepare (Object data)

  * void beforeParseJST (Object data)

  * void afterParseJST (Object data)

  * void initialize (Object data)

  * boolean before

&lt;XXX&gt;

_()_

  * boolean after

&lt;XXX&gt;

_(Object result)_

## Java UI ##
  * find

&lt;Model&gt;



  * get

&lt;Model&gt;



  * list

&lt;Model&gt;



  * page

&lt;Model&gt;



  * rand

&lt;Model&gt;



  * remove

&lt;Model&gt;



  * save

&lt;Model&gt;



  * saveOrUpdate

&lt;Model&gt;



  * total

&lt;Model&gt;



  * update

&lt;Model&gt;



## Sqlmap UI ##
  * 

&lt;name&gt;

_[.prev]._

&lt;n&gt;

_.i_

  * 

&lt;name&gt;

_[.prev]._

&lt;n&gt;

_.s_

  * 

&lt;name&gt;

_[.prev]._

&lt;n&gt;

_.u_

## Configuration UI ##
  * jpt.properties

  * jpt.model.dependencies.properties