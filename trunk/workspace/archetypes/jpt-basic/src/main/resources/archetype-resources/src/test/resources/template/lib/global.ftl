<#global tableMapping={
	'user' : '001',
	'role' : '002',
	'permission' : '003',
	'resource' : '004',
	'menu' : '005',
	'dictionary' : '006',
	'constant' : '007'
} />

<#global modelMapping={
	't_dictionary' : 'dictionary'
} />

<#global typeMapping={
	'BIT' : 'boolean',
	'TINYINT' : 'boolean',
	'SMALLINT' : 'Integer',
	'INTEGER' : 'Integer',
	'BIGINT' : 'Long',
	'FLOAT' : 'Double',
	'DOUBLE' : 'Double',
	'REAL' : 'Double',
	'NUMERIC' : 'Double',
	'DECIMAL' : 'Double',
	'CHAR' : 'String',
	'VARCHAR' : 'String',
	'LONGVARCHAR' : 'String',
	'DATE' : 'java.util.Date',
	'TIME' : 'java.util.Date',
	'TIMESTAMP' : 'java.util.Date',
	'BINARY' : 'byte[]',
	'VARBINARY' : 'byte[]',
	'LONGVARBINARY' : 'byte[]',
	'BLOB' : 'byte[]',
	'CLOB' : 'String'
} />

<#global typeValueMapping={
	'BIT' : '0',
	'TINYINT' : '0',
	'SMALLINT' : '0',
	'INTEGER' : '0',
	'BIGINT' : '0',
	'FLOAT' : '0',
	'DOUBLE' : '0',
	'REAL' : '0',
	'NUMERIC' : '0',
	'DECIMAL' : '0',
	'CHAR' : '',
	'VARCHAR' : '',
	'LONGVARCHAR' : '',
	'DATE' : '\'new Date()\'',
	'TIME' : '\'new Date()\'',
	'TIMESTAMP' : '\'new Date()\'',
	'BINARY' : '',
	'VARBINARY' : '',
	'LONGVARBINARY' : '',
	'BLOB' : '',
	'CLOB' : ''
} />

<#global typeValidatorMapping={
	'BIT' : 'int',
	'TINYINT' : 'int',
	'SMALLINT' : 'int',
	'INTEGER' : 'int',
	'BIGINT' : 'int',
	'FLOAT' : 'double',
	'DOUBLE' : 'double',
	'REAL' : 'double',
	'NUMERIC' : 'double',
	'DECIMAL' : 'double',
	'CHAR' : '',
	'VARCHAR' : '',
	'LONGVARCHAR' : '',
	'DATE' : '',
	'TIME' : '',
	'TIMESTAMP' : '',
	'BINARY' : 'int',
	'VARBINARY' : 'int',
	'LONGVARBINARY' : 'int',
	'BLOB' : '',
	'CLOB' : ''
} />

<#function getModel tname>
	<#if modelMapping[tname]?has_content>
		<#return modelMapping[tname] />
	</#if>
	<#if tname?starts_with("t_") && tname?ends_with("s") && tname?last_index_of("_")==1>
		<#return tname?substring(2,tname?length-1) />
	</#if>
	<#return "" />
</#function>

<#function getTcode model i>
	<#if tableMapping[model]?has_content>
		<#return tableMapping[model] />
	</#if>
	<#return i?c />
</#function>

<#function getFname cname>
	<#return (((cname?replace("_"," "))?capitalize)?replace(" ",""))?uncap_first />
</#function>