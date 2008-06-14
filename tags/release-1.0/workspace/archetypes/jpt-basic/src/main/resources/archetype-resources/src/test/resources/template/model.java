#set($ftl_st="#")
#set($ftl_var="$")
<${ftl_st}import "/lib/global.ftl" as g />
<@pp.dropOutputFile />
<${ftl_st}list doc.database.table as t>
<${ftl_st}assign tname=t.@name?lower_case />
<${ftl_st}assign model=g.getModel(tname) />
<${ftl_st}if model?has_content>
<${ftl_st}assign filename="/java/${packageInPathFormat}/model/${ftl_var}{model?cap_first}.java" />
<${ftl_st}if pp.outputFileExists(filename)>
<${ftl_st}assign filename="/tmp/java/${packageInPathFormat}/model/${ftl_var}{model?cap_first}.java" />
</${ftl_st}if>
<@pp.changeOutputFile name=filename />
package ${package}.model;

import java.io.Serializable;

public class ${ftl_var}{model?cap_first} extends BaseObject implements Serializable {
<${ftl_st}list t.column as c>
<${ftl_st}assign dbtype=c.@type?upper_case />
<${ftl_st}assign fname=g.getFname(c.@name) />
<${ftl_st}if fname!="id">
	
	private ${ftl_var}{typeMapping[dbtype]} ${ftl_var}{fname};
</${ftl_st}if>
</${ftl_st}list>
<${ftl_st}list t.column as c>
<${ftl_st}assign dbtype=c.@type?upper_case />
<${ftl_st}assign fname=g.getFname(c.@name) />
<${ftl_st}if fname!="id">
	
	public ${ftl_var}{typeMapping[dbtype]} get${ftl_var}{fname?cap_first}() {
		return ${ftl_var}{fname};
	}
	
	public void set${ftl_var}{fname?cap_first}(${ftl_var}{typeMapping[dbtype]} ${ftl_var}{fname}) {
		this.${ftl_var}{fname} = ${ftl_var}{fname};
	}
</${ftl_st}if>
</${ftl_st}list>
}
</${ftl_st}if>
</${ftl_st}list>