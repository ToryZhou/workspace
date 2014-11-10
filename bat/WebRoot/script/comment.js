$(document).ready(function() {
	page.queryComment();
	page.message();
	page.saveVisit();
});

var getData = (function() {
	function sendAjax(url, data, success, fail, type, closeLoading) {
		type = type || 'POST';
		if (type == 'POST') {
			// data = JSON.stringify(data);
		}
		var BASEURL = '';
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
		queryComment : function(data, success, fail) {
			var url = '/Comment/query.zt';
			sendAjax(url, data, success, fail, 'POST');
		},
		saveComment : function(data, success, fail) {
			var url = '/Comment/saveOrUpdate.zt';
			sendAjax(url, data, success, fail, 'POST');
		},
		saveVisit : function(data, success, fail) {
			var url = '/saveVisit.zt';
			sendAjax(url, data, success, fail, 'POST');
		}
	};
})();

var page = {
	queryComment : function() {
		getData.queryComment({},
				function(data) {
					if (0 != data.code) {
						alert(data.codeInfo);
					}
					var html = [];
					var _d = data.pagenation.entityList;
					html.push('<table>');
					for (var i = 0, max = _d.length; i < max; i++) {
						html.push('<tr>');
						html.push('<td width="80%">' + _d[i].sCommentContent
								+ '</td>');
						html.push('<td>' + _d[i].sCommentTime + '</td>');
						html.push('<tr>');
						html.push('</tr>');
					}
					html.push('</table>');
					$('.commentList').html(html.join(''));
				});
	},

	saveComment : function() {
		var textarea = $('.textarea').val();
		if (textarea.length <= 0) {
			alert('输入信息不可以为空！');
			return;
		}
		getData.saveComment({
			sParameters : '[{"sCommentContent":"' + textarea + '"}]'
		}, function(data) {
			page.queryComment();
			$('.textarea').val('');
		});
	},
	message : function() {
		var html = [];
		html.push("<p>留言：</p>");
		html.push("<p><textarea class='textarea' /></p>");
		html
				.push("<p><input type='button' value='留下脚印' onclick='page.saveComment();'/></p>");
		$('.comment').html(html.join(''));
	},

	saveVisit : function() {
		getData.saveVisit({}, function() {
		});
	}
};