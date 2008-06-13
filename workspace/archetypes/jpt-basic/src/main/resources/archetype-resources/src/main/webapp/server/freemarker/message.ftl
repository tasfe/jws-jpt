{
	actionMessages: [
		<#list actionMessages! as msg>'${msg}'<#if msg_has_next>,</#if></#list>
	],
	id: '${(model.id?c)!}'
}
