<#include "/lib/global.ftl" />
<#assign now="sysdate" />

<#function concat args>
	<#local s='' />
	<#list args as arg>
		<#local s=s+arg />
		<#if arg_has_next>
			<#local s=s+'||' />
		</#if>
	</#list>
	<#local s='('+s+')' />
	<#return s />
</#function>

<#function getKey tname>
	<#return 'SELECT s_${tname}.currval FROM dual' />
</#function>