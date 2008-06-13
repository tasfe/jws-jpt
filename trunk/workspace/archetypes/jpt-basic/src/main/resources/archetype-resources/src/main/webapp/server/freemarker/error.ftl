{
	actionErrors : [
		<#list actionErrors! as err>
		'${err}'<#if err_has_next>,</#if>
		</#list>
	],
	fieldErrors : [
		<#list (fieldErrors?keys)! as err>
		'${fieldErrors[err]}'<#if err_has_next>,</#if>
		</#list>	
	]
}
