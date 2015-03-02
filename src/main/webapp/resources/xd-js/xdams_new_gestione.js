var winDialog = null;
function apriWinProtoGest(theArchName, actionFlag, statusBar, physDoc, selId, pos, extraParams, jsonValues) {

	top.doApriWinProtoGest(theArchName, actionFlag, statusBar, physDoc, selId, pos, extraParams, jsonValues);
	/*
	 * if(parent!=null){ //alert("SONO IN IF parent!=null");
	 * parent.doApriWinProtoGest(theArchName,actionFlag,statusBar,physDoc,selId,pos,extraParams,jsonValues);
	 * }else{ //alert("SONO IN ELSEEEE parent==null");
	 * doApriWinProtoGest(theArchName,actionFlag,statusBar,physDoc,selId,pos,extraParams,jsonValues); }
	 */
}

function doApriWinProtoGest(theArchName, actionFlag, statusBar, physDoc, selId, pos, extraParams, jsonValues) {
	var extraField = "";
	if (extraParams != null && extraParams != "") {
		extraField = extractParam(extraParams);
	}
	// alert("thePage " + theArchName);
	var urlWin = "../../" + theArchName + "/managing.html?actionFlag=" + actionFlag + "&physDoc=" + physDoc + "&selid=" + selId + "&pos=" + pos + extraField;
	var jsonObject = eval('(' + jsonValues + ')');
	var widthProto = "450";
	var heightProto = "400";
	if (jsonObject != null) {
		if (jsonObject.width) {
			widthProto = jsonObject.width;
		}
		if (jsonObject.height) {
			heightProto = jsonObject.height;
		}

	}
	$('#aDialog').remove();
	$('body').append('<div id="aDialog"></div>');
 
	// chiudiDialog();

	var anIframe = $('<iframe style="border: 0px;" width="100%" height="100%"></iframe>');
	anIframe.attr('src', urlWin);
	$('#aDialog').html(anIframe);
	$('#aDialog').dialog({
		autoOpen : true,
		modal : false,
		height : heightProto ? heightProto : 150,
		width : widthProto ? widthProto : 250,
		resizable : false,
		title : statusBar ? statusBar : 'xDams dialog'
	});
}

function apriWinGest(thePage, actionFlag, statusBar, physDoc, selId, pos, extraParams, jsonValues) {
	var urlWin = thePage + "?actionFlag=" + actionFlag + "&physDoc=" + physDoc + "&selid=" + selId + "&pos=" + pos;
	window.open(urlWin, 'edtWind', 'toolbar=yes,location=no,status=yes,scrollbars=yes,resizable=yes,width=400,height=400');
}

function newWinProtoGest(urlWin,theArchName, actionFlag, statusBar, physDoc, selId, pos, extraParams, jsonValues) {
	var extraField = "";
	if (extraParams != null && extraParams != "") {
		extraField = extractParam(extraParams);
	}
	// alert("thePage " + theArchName);
	//var urlWin = "../../" + theArchName + "/managing.html?actionFlag=" + actionFlag + "&physDoc=" + physDoc + "&selid=" + selId + "&pos=" + pos + extraField;
	var jsonObject = eval('(' + jsonValues + ')');
	var widthProto = "450";
	var heightProto = "400";
	if (jsonObject != null) {
		if (jsonObject.width) {
			widthProto = jsonObject.width;
		}
		if (jsonObject.height) {
			heightProto = jsonObject.height;
		}

	}
	$('#aDialog').remove();
	$('body').append('<div id="aDialog"></div>');
 
	// chiudiDialog();

	var anIframe = $('<iframe style="border: 0px;" width="100%" height="100%"></iframe>');
	anIframe.attr('src', urlWin);
	$('#aDialog').html(anIframe);
	$('#aDialog').dialog({
		autoOpen : true,
		modal : false,
		height : heightProto ? heightProto : 150,
		width : widthProto ? widthProto : 250,
		resizable : false,
		title : statusBar ? statusBar : 'xDams dialog'
	});
}
function chiudiDialog() {
	$('#aDialog').dialog('destroy');
	$('#aDialog').remove();
}

function chiudiThisWin() {
	top.chiudiDialog();
}
function reloadLocation() {
	if (top.leftArea != null && top.leftArea.location != null){
		top.leftArea.location.reload();
	}else{
		if(top.document["lookupForm"]){
			top.document["lookupForm"].selId.value="";
			top.document["lookupForm"].submit();
		}else{
			top.location.reload();
		}
	}
}

function openInfoDialog() {
	winDialog = Dialog;
	top.winDialog.info(getLocalizedString('Attendere','Attendere'), {
		width : 250,
		height : 100
	});
}

function closeInfoDialog() {
	top.winDialog.closeInfo();
}

function extractParam(extPar) {
	try {
		return "&" + jQuery.param(extPar);
	} catch (e) {
		return '';
	}

}
try {
	$(function() {
		var old = $.ui.dialog.prototype._create;
		$.ui.dialog.prototype._create = function(d) {
			old.call(this, d);
			var self = this, options = self.options, oldHeight = options.height, oldWidth = options.width, uiDialogTitlebarFull = $('<a href="#"></a>').addClass('ui-dialog-titlebar-full ' + 'ui-corner-all').attr('role', 'button').hover(function() {
				uiDialogTitlebarFull.addClass('ui-state-hover');
			}, function() {
				uiDialogTitlebarFull.removeClass('ui-state-hover');
			}).toggle(function() {
				self._setOptions({
					height : parseInt(window.innerHeight - 10, 10),
					width : parseInt(window.innerWidth - 30, 10)
				});
				self._position('center');
				return false;
			}, function() {
				self._setOptions({
					height : parseInt(oldHeight, 10),
					width : parseInt(oldWidth, 10)
				});
				self._position('center');
				return false;
			}).focus(function() {
				uiDialogTitlebarFull.addClass('ui-state-focus');
			}).blur(function() {
				uiDialogTitlebarFull.removeClass('ui-state-focus');
			}).appendTo(self.uiDialogTitlebar),

			uiDialogTitlebarFullText = $('<span></span>').addClass('ui-icon ' + 'ui-icon-newwin').text(options.fullText).appendTo(uiDialogTitlebarFull)
		};
	})()
} catch (e) {
};