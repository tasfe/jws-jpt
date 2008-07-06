<#include "../lib/global.ftl" />
<#assign now='GETDATE()' />
<#assign currYear='YEAR(GETDATE())' />

<#function atoi str>
	<#return 'CAST(${str} AS int)' />
</#function>

<#function char i>
	<#return 'CHAR(${i})' />
</#function>

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

<#function itoa i>
	<#return 'CAST(${i} AS varchar(50))'>
</#function>

<#function isNull check replacement>
	<#return 'ISNULL(${check},${replacement})' />
</#function>

<#function substring str start len)>
	<#return 'SUBSTRING(${str},${start},${len})' />
</#function>

<#function left str len>
	<#return 'LEFT(${str},${len})' />
</#function>

<#function right str len>
	<#return 'RIGHT(${str},${len})' />
</#function>

<#function top n sql>
	<#return sql?replace('^\\s*SELECT\\s','SELECT TOP ${n}','ri') />
</#function>

<#function year date>
	<#return 'YEAR(${date})' />
</#function>