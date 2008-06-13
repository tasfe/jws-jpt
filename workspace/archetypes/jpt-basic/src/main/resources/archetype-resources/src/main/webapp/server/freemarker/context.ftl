if (!window.JPT) window.JPT = {};
if (!window.JPT.ctx) window.JPT.ctx = {};

JPT.ctx.initList = [];
JPT.ctx.base = '${base}';
JPT.ctx.hasContainer = true;
JPT.ctx.msgtip = 'script';
JPT.ctx.rewrites = {
	'new' : 'editNew',
	'print' : 'show'
};
JPT.ctx.loginUser = {name:'${(loginUser.name)!}'};