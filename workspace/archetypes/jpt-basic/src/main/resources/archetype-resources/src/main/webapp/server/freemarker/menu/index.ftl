<#include '../global.ftl' />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link type="text/css" media="all" rel="stylesheet" href="${base}/styles/default/basic.css" />
<link type="text/css" media="all" rel="stylesheet" href="${base}/styles/default/list.css" />

<script type="text/javascript" src="${base}/scripts/jquery.js?v=1.2.3"></script>
<script type="text/javascript" src="${base}/scripts/toolkit.js?v=1.0.0"></script>

</head>
<body>
<div id="main">
<div class="toolbar">
	<a href="${base}/server/pages/menu/editNew.html?method:editNew="><img src="${base}/styles/default/images/ico-add-1.gif" />增加</a>
	<a href="${base}/server/pages/menu/delete.html?method:delete=" class="jpt-ajax"><img src="${base}/styles/default/images/ico-remove-1.gif" />批量删除</a>
</div>
<form name="listForm" id="listForm" method="post" action="${base}/server/pages/menu/index.html" class="jpt-list-form">
	<div>
		<input type="hidden" name="pager.offset" id="offset" value="${root.pager.offset}" />
		<input type="hidden" name="pager.limit" id="limit" value="${root.pager.limit}" />
		<input type="hidden" id="totalRows" value="${root.pager.totalRows}" />
	</div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="jpt-list-table">
		<thead>
			<tr>
				<th class="jpt-list-seq">#</th>
				<th class="jpt-list-chk"><input type="checkbox" id="chkAll" /></th>
				<th>名称</th>
				<th>地址</th>
				<th>上级菜单</th>
				<th class="op">操作</th>
			</tr>
		</thead>
		<tbody>
<#list root.menus! as menu>
			<tr>
				<td class="jpt-list-seq">${menu_index + ((pager.offset)!1)}</td>
				<td class="jpt-list-chk"><input type="checkbox" name="model.chkIDs" value="${menu.id?c}" /></td>
				<td><#if (menu.name)?has_content>${menu.name}<#else>&nbsp;</#if></td>
				<td><#if (menu.url)?has_content>${menu.url}<#else>&nbsp;</#if></td>
				<td><#if (menu.parentName)?has_content>${menu.parentName}<#else>&nbsp;</#if></td>
				<td class="op">
					<a href="${base}/server/pages/menu/edit/${menu.id?c}.html?method:edit=" class="jpt-ajax"><img src="${base}/styles/default/images/ico-edit-1.gif" />修改</a>
					<a href="${base}/server/pages/menu/destroy/${menu.id?c}.html?method:destroy=" class="jpt-ajax"><img src="${base}/styles/default/images/ico-remove-2.gif" />删除</a>
				</td>
			</tr>
</#list>
		</tbody>
		<tfoot>
			<tr><td colspan="6"><div class="jpt-pager"></div></td></tr>
		</tfoot>	
	</table>
</form>
</div>
</body>
</html>
