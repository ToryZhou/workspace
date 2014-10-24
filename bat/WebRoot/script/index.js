$(document).ready(function() {
	page.getWebsite();
});

var getData = (function() {
	function sendAjax(url, data, success, fail, type, closeLoading) {
		type = type || 'POST';
		if (type == 'POST') {
			// data = JSON.stringify(data);
		}
		var BASEURL = '/bat';
		$.ajax({
			url : BASEURL + url,
			cache : false,
			type : type,
			data : data,
			success : function(data) {
				var d = JSON.parse(data);
				if (d["code"] == 0) {
					success(d);
				} else if (d["code"] == -10000) {
					LoginStart();
				} else if (d["code"] == -20000) {

				} else {
					alert(d['codeInfo']);
					if (fail) {
						fail(d);
					}
				}
			},
			error : function(d) {
				if (fail) {
					fail(d);
				}
			}
		});
	}

	return {
		getAction : function(data, success, fail) {
			var url = '/Action/query.zt';
			sendAjax(url, data, success, fail, 'POST');
		},
		getWebsite : function(data, success, fail) {
			var url = '/Website/query.zt';
			sendAjax(url, data, success, fail, 'POST');
		}
	};

})();


var page = {
	getWebsite : function() {
		getData.getWebsite({}, function(data) {
			if(0!=data.code){
				alert(data.codeInfo);
			}
			var html = [];
			var _d = data.pagenation.entityList;
			html.push('<input type="button" value="添加" onclick="page.show(\'light\');" /> <br /><br />');
			for ( var i = 0, max = _d.length; i < max; i++) {
				html.push('<a target="_blank" href="' + _d[i].sWebsiteUrl
						+ '">' + _d[i].sWebsiteName + '</a>');
				html.push('<br /><br />');
			}
			$('.myList').html(html.join(''));
		});
	},
	show : function(tag) {
		var light = document.getElementById(tag);
		var fade = document.getElementById('fade');
		light.style.display = 'block';
		fade.style.display = 'block';
	},
	hide : function(tag) {
		var light = document.getElementById(tag);
		var fade = document.getElementById('fade');
		light.style.display = 'none';
		fade.style.display = 'none';
	}
};