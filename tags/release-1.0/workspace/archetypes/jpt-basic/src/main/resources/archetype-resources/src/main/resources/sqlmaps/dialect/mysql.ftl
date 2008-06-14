<#include "/lib/global.ftl" />
<#assign now='now()' />

<#function concat args>
	<#local s='concat(' />
	<#list args as arg>
		<#local s=s+arg />
		<#if arg_has_next>
			<#local s=s+',' />
		</#if>
	</#list>
	<#local s=s+')' />
	<#return s />
</#function>

<#function getKey tname>
	<#return 'SELECT last_insert_id();' />
</#function>