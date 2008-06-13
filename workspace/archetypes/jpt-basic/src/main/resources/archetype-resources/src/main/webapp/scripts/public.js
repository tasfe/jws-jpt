$(document).ready (function () {
	if (window.JPT && window.JPT.ctx && window.JPT.ctx.async) {
		window.JPT.ctx.initList.push(init);
	} else {
		init();
	}
});

function init () {
	$('#bookmarklink').click(function () {		
		addBookmark(document.title, window.location.href);
		return false;
	});
	$('#homelink').click(function () {
		setHomePage(this, window.location.href);
		return false;
	});
}