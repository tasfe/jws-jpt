${artifactId}

1、参考文档：
1.1、jquery
http://docs.jquery.com/Main_Page
1.2、jst
http://code.google.com/p/trimpath/wiki/JavaScriptTemplates
1.3、REST
http://brainstorm.javaeye.com/blog/post/416397
http://www.javaeye.com/topic/70113

2、页面命名规范：
2.1、页面命名遵守类似REST（Representational State Transfer）的风格。
2.2、原则上一个url由<名词>/<动词>[/<id>].html来标识，<名词>表示要操作的资源，<动词>表示对资源的操作，<id>标识某个资源。一般的CRUD（创建、读取、更新、删除）操作可抽象为下面7个操作：
a)、index		列出所有资源，例如user/index.html
b)、show		显示某个资源，例如user/show/123.html
c)、new			新建一个资源，例如user/new.html
d)、edit		修改某个资源，例如user/edit/123.html
e)、update		保存对某个资源的修改，例如user/update/123.html
f)、create		保存新建的资源，例如user/create.html
g)、destroy		删除某个资源，例如user/destroy/123.html
其中user为资源名，123为资源id（从上一部操作传递过来，或者从列表中选择得到）。
除了以上7种操作，其他操作可根据需要扩展，例如可用delete表示批量删除（user/delete.html）。
同时也要建立相应的<名词>标识的目录，对于类似user/show/123.html的url，实际对应的文件名为user/show.html。
对于不同逻辑同样的操作，不再继续细分资源，而是以前后缀的方式区分。原则是有前缀，则必须有后缀，有后缀可以没有前缀。例如user/index-s1.html、user/e-index-00.html。

3、页面元素命名规范
3.1、元素属性按照type、class、name、id、style、value、checked、selected、disabled、readonly次序设置
3.2、对于表单(form)及需要提交的表单元素(input、select、textarea)，必须同时为其指定name和id属性；不需要提交的表单元素不要指定name属性。列表中用于批量选择的input type="checkbox"的元素指定name="model.chkIDs"
3.3、元素name和id以单词大小写混写，小写开头的格式命名，class全部小写以-分隔单词

4、html代码规范
4.1、所有的地址均使用绝对地址，form的action如果为本页，也要将本页的绝对地址写上，不允许为空
4.2、input元素必须以 />结束，斜杠前有一个空格
4.3、form中的所有input type="hidden"要统一放在其他form元素的最前面，并用一个div把所有input type="hidden"包住
4.4、所有元素的事件不要写到html中，要在initialize方法中统一添加