<#if action.model?has_content>
function showErrors (errors) {
	if (JPT.ctx.msgtip == 'script') {
		var errorText = '';
		for (var i = 0; i < errors.length; i++) {
			errorText += errors[i][1] + '\n';
		}		
		alert(errorText);
	}
}

<#include "/server/scripts/validation.js" />
<#assign validate="validate${action.model?cap_first}" />
<#if validateForm?has_content>
${validateForm}
<#else>

function ${validate}Form () {
	return ${validate}Fields(this);
}
</#if>

function ${validate}Fields (form) {
	var errors = [];
<#list action.validators as validator>
<#assign lc=JptLocalizedTextUtil.getInstance(action.getTexts('ApplicationResources')) />
<#assign fieldText=lc.getText(validator.fieldName) />
<#assign fieldName=validator.fieldName?replace('fields.' + model + '.', 'model.') />
	if (form.elements['${fieldName}']) {
        var field = form.elements['${fieldName}'];        
<#if validator.validatorType = 'required'>
		var error = '${lc.getText("errors.required",[fieldText])?js_string}';
		if (($(field).is('select') && field.selectedIndex == -1) ||
			($(field).is(':checkbox') && !$(field).is(':checked')) ||
			(field.value == '')) {
			errors.push([field, error]);
        }
<#elseif validator.validatorType = 'requiredstring'>
		var error = '${lc.getText("errors.required",[fieldText])?js_string}';
        if (field.value != null && field.value.trim() == '') {
            errors.push([field, error]);
        }
<#elseif validator.validatorType = 'stringlength'>
<#if validator.minLength gt -1>
		var error = '${lc.getText("errors.minlength",[fieldText,validator.minLength])?js_string}';
<#else>
		var error = '${lc.getText("errors.maxlength",[fieldText,validator.maxLength])?js_string}';
</#if>
        if (field.value != null) {
            var value = field.value;
<#if validator.trim>
			value = value.trim();
</#if>
            if (value.length > 0 && (
               (${validator.minLength} > -1 && value.length < ${validator.minLength}) ||
               (${validator.maxLength} > -1 && value.length > ${validator.maxLength}))) {
                errors.push([field, error]);
            }
        } 
<#elseif validator.validatorType = 'regex'>
		var error = '${lc.getText(validator.messageKey,[fieldText])?js_string}';
        if (field.value != null && !field.value.match('${validator.expression?js_string}')) {
            errors.push([field, error]);
        }
<#elseif validator.validatorType = 'email'>
		var error = '${lc.getText("errors.email",[fieldText])?js_string}';
        if (field.value != null && field.value.length > 0 && !field.value.isEmail()) {
            errors.push([field, error]);
        }
<#elseif validator.validatorType = 'url'>
		var error = '${lc.getText("errors.url",[fieldText])?js_string}';
        if (field.value != null && field.value.length > 0 && !field.value.isUrl()) { 
            errors.push([field, error]);
        }
<#elseif validator.validatorType = 'int'>
		var error = '${lc.getText("errors.int",[fieldText])?js_string}';
        if (field.value != null && field.value.length > 0) {
            if (!field.value.isInt() ||
				<#if validator.min?exists>parseInt(field.value) < ${validator.min}<#else>false</#if> ||
                <#if validator.max?exists>parseInt(field.value) > ${validator.max}<#else>false</#if>) {
                errors.push([field, error]);
            }
        }
<#elseif validator.validatorType = 'double'>
		var error = '${lc.getText("errors.double",[fieldText])?js_string}';
        if (field.value != null && field.value.length > 0) {
            if (!field.value.isFloat() ||
				<#if validator.minInclusive?exists>value < ${validator.minInclusive}<#else>false</#if> ||
                <#if validator.maxInclusive?exists>value > ${validator.maxInclusive}<#else>false</#if> ||
                <#if validator.minExclusive?exists>value <= ${validator.minExclusive}<#else>false</#if> ||
                <#if validator.maxExclusive?exists>value >= ${validator.maxExclusive}<#else>false</#if>) {
                errors.push([field, error]);
            }
        }
</#if>
    }
</#list>
	if (errors.length > 0) {
		showErrors(errors);
		return false;
	}
	return true;
}
</#if>