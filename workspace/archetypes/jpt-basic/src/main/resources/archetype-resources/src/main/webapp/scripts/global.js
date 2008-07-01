(function ($) {

	$(document).ready (function () {
		
		loadContext();
		
		if ($.isFunction(window.prepare)) {
			var data = {
				pager : {
					offset : 0,
					limit : 10,
					totalRows : 0
				}
			};
			if (!window.prepare(data)) {
				showData(data);
				return;
			}
		}
		
		JPT.ctx.async = true;
		
		var method = getMethod(window.location.href);
		var serverUrl = getServerUrl(window.location.href, method);
		$.ajax({
			type : 'get',
			url : serverUrl,
			success : function (data, status) {
				showData(data || {});
			},
			error : (new AjaxError()).getHandler(function (xml, status, e) {
				showData({});
			}),
			data : 'method:' + method + '=',
			dataType : 'json'
		});
	});

	function showData (data) {
	
		parseJST(data);
		
		if (!JPT.ctx.hasContainer) {
			$('form').each(function () {
				this.action = rewriteURL(this.action);					
			});
		}
		
		$('.jpt-list-form').each(function () {
			this.method = 'get';					
		});
		
		$('.jpt-list-table tbody tr:odd').addClass('odd');
		$('.jpt-list-table tbody tr').mouseover(function () {
			$(this).addClass('over');
		}).mouseout(function () {
			$(this).removeClass("over");
		});
	
		$('#chkAll').click(function () {
			$('input[@name=model.chkIDs]').attr('checked', this.checked);
		});
		
		$('a.jpt-ajax').each(function () {
			if (/delete\.html/.test(this.href)) {
				$(this).click(function () {
					try {
						if (confirm(JPT.ctx.lang['tip.del'])) {
							if(!$('input[@name=model.chkIDs]').is(':checked')) {
								alert(JPT.ctx.lang['tip.chk']);
							} else {
								if ($('input[@name=model.chkIDs]').length == $('input[@name=model.chkIDs]:checked').length) {
									var pager = getPager();
									if (pager.isLast) {
										$('#offset').val(Math.max(1, pager.offset - pager.limit));
									}
								}
								remove(this.href);
							}
						}
					} catch (e) {
						alert(e.message);
					}
					return false;
				});
			}
			if (/destroy\/\d+\.html/.test(this.href)) {
				$(this).click(function () {
					try {
						if (confirm(JPT.ctx.lang['tip.del'])) {
							if ($('input[@name=model.chkIDs]').length == 1) {
								var pager = getPager();
								if (pager.isLast) {
									$('#offset').val(Math.max(1, pager.offset - pager.limit));
								}
							}
							remove(this.href);
						}
					} catch (e) {
						alert(e.message);
					}
					return false;
				});
			}
			if (!JPT.ctx.hasContainer) {
				this.href = rewriteURL(this.href);
			}
		});
		
		$('input[@type=submit].jpt-ajax').click(function () {
			try {
				if (beforeClick(this)) {
					submit(this);
				}
			} catch (e) {
				alert(e.message);
			}
			return false;
		});
		
		$('input[@type=button].jpt-back').click(function () {
			history.back();
		});
		
		if (JPT.ctx.initList) {
			$.each(JPT.ctx.initList, function(){
				this();
			});
			JPT.ctx.initList = null;
		}
	}

	function remove (url) {
		var method = getMethod(url);
		var serverUrl = getServerUrl(url, method);
		request(
			serverUrl,
			'method:' + method,
			$('form.jpt-list-form').serialize(),
			'',
			function () {
				$('form.jpt-list-form').submit();
			}
		);
	}
	
	function submit (el) {	
		var method;
		if (el.name.indexOf('method:') == 0) {
			method = el.name.substr(7);
		} else {
			method = getMethod(el.form.action);
		}
		var serverUrl = getServerUrl(el.form.action, method);
		request(
			serverUrl,
			'method:' + method,
			$(el.form).serialize(),
			$(el).attr('message'),
			{
				create : function (result) {				
					if(!afterClick(el, [result])) {
						el.form.reset();
					}
				},
				update : function (result) {
					afterClick(el, [result]);
				}
			}[method]
		);
	}
	
	function request (serverUrl, method, data, message, afterCallback) {
		$.ajax({
			type: 'post',
			url: serverUrl,
			success: function (result, status) {
				if (handleResult(result, status, message) && afterCallback) {
					afterCallback(result);
				}
			},
			error: (new AjaxError()).getHandler(function (xml, status, e) {
				if (xml && xml.responseText.indexOf('j_acegi_security_check') != -1) {
					alert(JPT.ctx.lang['tip.timeout']);
					window.top.location = JPT.ctx.base + '/j_acegi_logout';
				} else {
					alert(JPT.ctx.lang['tip.ajax.fail']);
				}
			}),
			data: method + '=&' + data,
			dataType: 'json'
		});
	}

	function rewriteURL (url) {
		return url.replace(/(\/(\d+)\.html)/, '.html?id=$2');
	}

})(jQuery);