<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link type="text/css" media="all" rel="stylesheet" href="${base}/styles/default/basic.css" />
<link type="text/css" media="all" rel="stylesheet" href="${base}/styles/default/edit.css" />
<style type="text/css">
<!--
#filePlace {
	clear:both;
	padding-left:10px;
	width:150px;
	height:20px;	
}

#uploading  img {
	margin-right:10px;
	vertical-align:middle;
}

#fileWrapper {
	position:absolute;
}

#file  {
	width:310px;
}
-->
</style>

<script type="text/javascript" src="${base}/scripts/jquery.js?v=1.2.3"></script>
<script type="text/javascript" src="${base}/scripts/toolkit.js?v=1.0.0"></script>
<script type="text/javascript" src="${base}/scripts/validation.js?model=menu"></script>
<script type="text/javascript">
<!--
$(document).ready(function () {
	var offset = $('#filePlace').offset();
	$('#fileWrapper').css({
		'top':offset.top+'px',
		'left':'190px'
	});
	setupUploader({
		onUploadStart : function () {
			$('#uploading').show();
		},
		onUploadCompleted : function (data) {
			alert('上传成功！');
			$('#uploading').hide();
			$('#model_img').val(data.fileUrls[0].substr(13));
		}
	});
});
//-->
</script>

</head>
<body>
<div id="main">
<form name="uploadForm" id="uploadForm" method="post" enctype="multipart/form-data" action="${base}/server/upload/menuImage.html">
	<div>
		<input type="hidden" name="rootPath" value="/images/menu" />
	</div>
	<div id="fileWrapper">
		<input type="file" name="file" id="file" />
		<input type="button" id="upload" value="上传" />
	</div>
</form>
<form name="menuForm" id="menuForm" method="post" action="${base}/server/pages/menu/create.html">
	<div>
	
	</div>
	<ul>
		<li>
			<label for="name">名称:</label>
			<input type="text" class="text" name="model.name" id="model_name" value="" />
		</li>
		<li>
			<label for="url">地址:</label>
			<input type="text" class="text" name="model.url" id="model_url" value="" />
		</li>
		<li>
			<label for="suburl">获取子菜单地址:</label>
			<input type="text" class="text" name="model.suburl" id="model_suburl" value="" />
		</li>
		<li>
			<label for="img">图标:</label>
			<input type="text" class="text" name="model.img" id="model_img" value="" />
			<div id="filePlace"><div id="uploading" style="display:none;"><img src="${base}/styles/default/images/spinner.gif" />正在上传...</div></div>
		</li>
		<li>
			<label for="target">目标窗口:</label>
			<input type="text" class="text" name="model.target" id="model_target" value="" />
		</li>
		<li>
			<label for="parentId">上级菜单:</label>
			<select name="model.parentId" id="model_parentId">
				<option value=""></option>
				<#list root.topMenus! as topMenu>
				<option value="${topMenu.id}">${topMenu.name}</option>
				</#list>
			</select>
		</li>
		<li>
			<label for="displayOrder">显示次序:</label>
			<input type="text" class="text" name="model.displayOrder" id="model_displayOrder" value="" />
		</li>
		<li class="op">
			<input type="submit" class="jpt-ajax button" name="method:create" value="保存" />
			<input type="button" class="jpt-back button" value="返回" />
		</li>
	</ul>
</form>
</div>
</body>
</html>
