var globalScript = {};
function loadScripts(scriptsMap) {
	for ( var a = 0; a < scriptsMap.scripts.length; a++) {
		aScript = scriptsMap.scripts[a];
		if (!globalScript[aScript]) {
			jQuery.ajax({
				async : false,
				cache : true,
				url : (scriptsMap.prefix ? scriptsMap.prefix : "") + scriptsMap.base + '/' + aScript + '.js?' + new Date().getMonth() + new Date().getDay(),
				dataType : 'script',
				error : function() {
					alert('error loading ' + aScript);
				},
				success : function() {
					globalScript[aScript] = 'loaded';
				}
			});
		}
	}
}

function loadJsBusiness(target, prefix) {
	var base = '/xd-js';
	if (target === 'workspace') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.cookie' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_onload' ]
		});
	} else if (target === 'query') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.autocomplete-1.4.2', 'jquery.jgrowl_minimized', 'jquery.blockUI', 'jquery.cookie' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_query', 'xdams_ajax', 'xdams_document', 'xdams_help', 'xdams_new_gestione', 'xdams_info' ]
		});
	} else if (target === 'hierBrowser') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.autocomplete-1.4.2', 'jquery.jgrowl_minimized', 'jquery.blockUI', 'jquery.cookie','jquery.qtip.min' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_hierBrowser', 'xdams_document', 'xdams_query', 'xdams_ajax', 'xdams_titoli', 'xdams_new_gestione', 'xdams_info' ]
		});
	} else if (target === 'shortTab') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_document', 'xdams_ajax', 'xdams_titoli', 'xdams_info', 'xdams_new_gestione' ]
		});
	} else if (target === 'completeTab') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last' ]
		});
		loadScripts({
			'base' : base, 
			'prefix' : prefix,
			'scripts' : [ 'xdams_document', 'xdams_ajax', 'xdams_titoli', 'xdams_info', 'xdams_jquery', 'xdams_new_gestione', 'xdams_areaGest', 'tiny_mce/tiny_mce', 'tiny_mce/jquery.tinymce']
		});
	} else if (target === 'tree') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.bgiframe.min', 'jqModal', 'jquery.jgrowl_minimized', 'jquery.dimensions', 'jquery.cookie','jquery.qtip.min' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_hierBrowser', 'xdams_gestione', 'xdams_ajax', 'xdams_jquery', 'xdams_onload' ]
		});
	} else if (target === 'eraseMenu') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'sortingMenu') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'nativeXML') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'sh/shCore', 'sh/shBrushXml', 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'multiMove') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'multiMod') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'docEdit') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.autocomplete-1.4.2', 'jquery.bgiframe.min', 'jquery.url.packed', 'jquery.blockUI', 'jquery.jgrowl_minimized', 'FancyZoom', 'FancyZoomHTML']
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_ajax', 'xdams_docEdit', 'xdams_dateGest', 'xdams_textAreaGest', 'xdams_upload', 'xdams_flashRTE', 'xdams_areaGest', 'tiny_mce/tiny_mce', 'tiny_mce/jquery.tinymce' ]
		});
	} else if (target === 'lookup') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_lookup', 'xdams_info', 'xdams_new_gestione' ]
		});
	} else if (target === 'preInsert') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.autocomplete-1.4.2', 'jquery.bgiframe.min', 'jquery.url.packed', 'jquery.blockUI', 'jquery.jgrowl_minimized', 'FancyZoom', 'FancyZoomHTML' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_ajax', 'xdams_docEdit', 'xdams_dateGest', 'xdams_textAreaGest', 'xdams_upload', 'xdams_flashRTE', 'xdams_areaGest', 'tiny_mce/tiny_mce', 'tiny_mce/jquery.tinymce' ]
		});
	} else if (target === 'title') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery-ui-last', 'jquery.blockUI', 'jquery.bgiframe.min', 'jquery.dimensions', 'jqModal', 'jquery.jgrowl_minimized', 'jquery.cookie','jquery.qtip.min','jquery.jcarousel.min' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_gestione', 'xdams_document', 'xdams_query', 'xdams_ajax', 'xdams_tooltip', 'xdams_titoli', 'xdams_new_gestione', 'xdams_info', 'xdams_jquery', 'xdams_onload', 'xdams_photo_title' ]
		});
	} else if (target === 'pdfPrint') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI', 'jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'upload') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	} else if (target === 'vocabulary') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		});
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_idx' ]
		});
	} else if (target === 'modifyAuther') {
		loadScripts({
			'base' : base + "/jquery",
			'prefix' : prefix,
			'scripts' : [ 'jquery.blockUI','jquery-ui-last' ]
		}); 
		loadScripts({
			'base' : base,
			'prefix' : prefix,
			'scripts' : [ 'xdams_jquery', 'xdams_new_gestione' ]
		});
	}
	
	
	
}

String.prototype.trim = function() {
	return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};
String.prototype.startsWith = function(str) {
	return (this.match("^" + str) == str);
};
String.prototype.endsWith = function(str) {
	return (this.match(str + "$") == str);
};
