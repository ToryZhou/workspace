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
		},
		saveWebsite : function(data, success, fail) {
			var url = '/Website/saveOrUpdate.zt';
			sendAjax(url, data, success, fail, 'POST');
		},
		deleteWebsite : function(data, success, fail) {
			var url = '/Website/delete.zt';
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
			html.push('<table>');
			for ( var i = 0, max = _d.length; i < max; i++) {
				html.push('<tr>');
				html.push('<td><a target="_blank" href="' + _d[i].sWebsiteUrl
						+ '">' + _d[i].sWebsiteName + '</a></td>');
				html.push('<td><input type="button" value="删除" onclick="page.deleteWebsite('+_d[i].nWebsiteId+')"></td />');
				html.push('</tr>');
			}
			html.push('</table>');
			html.push('<input type="button" value="添加" onclick="page.show(\'light\');" /> <br /><br />');
			$('.myList').html(html.join(''));
		});
	},
	saveWebsite:function(){
		var name = document.getElementById('sWebsiteName').value;
		var url = document.getElementById('sWebsiteUrl').value;
		getData.saveWebsite({sParameters:'[{"sWebsiteName":"'+name+'","sWebsiteUrl":"'+url+'"}]'},function(data){
			page.hide('light');
			page.getWebsite();
		});
	},
	deleteWebsite:function(id){
		getData.deleteWebsite({nIds:id},function(data){
			page.getWebsite();
		});
	},
	show : function(tag) {
		var light = document.getElementById(tag);
		var fade = document.getElementById('fade');
		light.style.display = 'block';
		fade.style.display = 'block';
		var html=[];
		html.push("<p>");
		html.push("名称：<input id='sWebsiteName' type='text' style='width:300px;' />");
		html.push("</p>");
		html.push("<p>");
		html.push("网址：<input id='sWebsiteUrl' type='text' style='width:300px;' />");
		html.push("</p>");
		html.push("<p>");
		html.push("<input type='button' value='确定' onclick='page.saveWebsite();'/>");
		html.push("<input type='button' value='取消' onclick='page.hide(\"light\");'/>");
		html.push("</p>");
		$('.con').html(html.join(''));
	},
	
	hide : function(tag) {
		var light = document.getElementById(tag);
		var fade = document.getElementById('fade');
		light.style.display = 'none';
		fade.style.display = 'none';
	}
};