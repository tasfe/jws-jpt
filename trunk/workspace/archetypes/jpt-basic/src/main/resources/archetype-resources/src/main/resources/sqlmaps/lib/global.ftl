<#assign NaN='$' + '{id.NaN}' />

<#function getProperty name>
	<#return '$' + '{' + name + '}' />
</#function>

<#function isNotPredefined args...>
	<#local t = '' />
	<#list args as arg>
		<#local t = arg + '.' />
	</#list>
	<#return t + 'predefined&lt;&gt;$' + '{flag.predefined}' />
</#function>

<#function isChkIDs args...>
	<#local t = '' />
	<#local id = 'id' />
	<#if args?size gt 0 && args[0]?has_content>
		<#local t = args[0] + '.' />
	</#if>
	<#if args?size gt 1>
		<#local id = args[1] />
	</#if>
	<#return '<isEmpty property="chkIDs">' + t + id + '=#id#</isEmpty><isNotEmpty open="(" close=")" property="chkIDs"><iterate property="chkIDs" conjunction=" OR ">' + t + id + '=#chkIDs[]#</iterate></isNotEmpty>' />
</#function>

<#function isChkIDs2 id>
	<#return  isChkIDs('',id) />
</#function>
