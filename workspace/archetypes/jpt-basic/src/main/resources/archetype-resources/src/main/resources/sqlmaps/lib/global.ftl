<#assign NaN='$' + '{id.NaN}' />

<#function isNotPredefined args...>
	<#local t = '' />
	<#list args as arg>
		<#local t = arg + '.' />
	</#list>
	<#return t + 'predefined&lt;&gt;$' + '{flag.predefined}' />
</#function>

<#function isChkIDs args...>
	<#local t = '' />
	<#list args as arg>
		<#local t = arg + '.' />
	</#list>
	<#return '<isEmpty property="chkIDs">' + t + 'id=#id#</isEmpty><isNotEmpty open="(" close=")" property="chkIDs"><iterate property="chkIDs" conjunction=" OR ">' + t + 'id=#chkIDs[]#</iterate></isNotEmpty>' />
</#function>