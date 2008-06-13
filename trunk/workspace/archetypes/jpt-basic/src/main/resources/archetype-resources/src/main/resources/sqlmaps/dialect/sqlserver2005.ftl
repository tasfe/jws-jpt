<#include "/lib/global.ftl" />
<#assign now='now()' />

<#function concat args>
	<#local s='(' />
	<#list args as arg>
		<#local s=s+arg />
		<#if arg_has_next>
			<#local s=s+'+' />
		</#if>
	</#list>
	<#local s=s+')' />
	<#return s />
</#function>

<#function getKey tname>
	<#return 'SELECT @@IDENTITY' />
</#function>