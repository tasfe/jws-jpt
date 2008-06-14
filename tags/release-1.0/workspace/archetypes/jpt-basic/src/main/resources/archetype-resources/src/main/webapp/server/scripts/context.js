if (!window.JPT) window.JPT = {};
if (!window.JPT.ctx) window.JPT.ctx = {};

JPT.ctx.initList = [];
JPT.ctx.base = document.location.href.replace(/^(.*?)\/pages\/\w+\/.+$/, '$1')
									 .replace(/^(.*?)\/[^\/]+\.html.*$/, '$1')
									 .replace(/^http:\/\/[^\/]+/, '');
JPT.ctx.msgtip = 'script';
JPT.ctx.rewrites = {
	'new' : 'editNew',
	'print' : 'show'
};
JPT.ctx.loginUser = {name:'administrator'};