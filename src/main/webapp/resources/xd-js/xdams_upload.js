var $dialog = null;

function allega(theRecord, theField, thePrefix, theUploadMode, originalFileName, xPathPrefix, physDoc) {
	percorsoImg = theField.parentNode.getElementsByTagName("input")[0].name;
	// alert("thePrefix "+thePrefix);
	if (isNaN(parseInt(physDoc, 10)) || parseInt(physDoc, 10) == 0
			|| parseInt(physDoc, 10) == -1) {
		alert(getLocalizedString('Attenzione_physDoc_non_settato','Attenzione physDoc non settato')+"." + physDoc);
		return false;
	}

	/*
	 * alert("percorsoImg "+percorsoImg); alert("theDb "+theDb);
	 * alert("theRecord "+theRecord); alert("theField "+theField);
	 * alert("theMode "+theMode); alert("thePrefix "+thePrefix);
	 * alert("theUploadMode "+theUploadMode);
	 */
	if (theUploadMode.indexOf('browseFTP') != -1) {
		dispatchBrowseFTP(percorsoImg, thePrefix, theField, theUploadMode, theRecord, originalFileName, xPathPrefix, physDoc);
	} else if (theUploadMode.indexOf('upload') != -1) {
		dispatchUpload(percorsoImg, thePrefix, theField, theUploadMode, theRecord, originalFileName, xPathPrefix, physDoc);
	} 
	return false;
}

function dispatchUpload(nomeField, nomeSpan, thisObject, actionFlag, theRecord,	originalFileName, xPathPrefix, physDoc) {
	var originalFileNameParam = "";
	if (originalFileName != null) {
		originalFileNameParam = "&amp;flagOriginalFileName=true&amp;destOriginalFileName=" + originalFileName + "&amp;xPathPrefix=" + xPathPrefix;
	} else {
		originalFileNameParam = "";
	}
	if (actionFlag.indexOf('Simple') != -1) {
		uploadSimple(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	} else if (actionFlag.indexOf('Resize') != -1) {
		uploadResize(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	}
}

function uploadSimple(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc) {
	percorso =  globalOption.contextPath+"/upload/" + globalOption.theArch + "/uploadMenu.html?destField=" + escape(nomeField)+"&amp;idRecord="+theRecord+"&amp;physDoc="+physDoc+"&amp;uploadType=simple"+originalFileNameParam;
	if($dialog!==null){chiudiDialog();}
 	$dialog = $('<div></div>').html('<iframe style="border: 0px; " src="' + percorso + '" width="100%" height="100%"></iframe>').dialog({autoOpen: false,modal: false,height: 280,width: 558});
 	$dialog.dialog({ modal: true });
 	$dialog.dialog('open');
}

function chiudiDialog(){
	 $dialog.dialog('close');
	 $dialog=null;
}

function uploadResize(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc) {
	/*
	alert("nomeField="+nomeField);
	alert("nomeSpan="+nomeSpan);
	alert("thisObject="+thisObject);
	alert("actionFlag="+actionFlag);
	alert("theRecord="+theRecord);
	alert("originalFileNameParam="+originalFileNameParam);
	alert("physDoc="+physDoc);
	*/
 	percorso =  globalOption.contextPath+"/upload/" + globalOption.theArch + "/uploadMenu.html?destField=" + escape(nomeField)+"&amp;idRecord="+theRecord+"&amp;physDoc="+physDoc+"&amp;uploadType=resize"+originalFileNameParam;
	if($dialog!==null){chiudiDialog();}
 	$dialog = $('<div></div>').html('<iframe style="border: 0px; " src="' + percorso + '" width="100%" height="100%"></iframe>').dialog({autoOpen: false,modal: false,height: 280,width: 558});
 	$dialog.dialog({ modal: true });
 	$dialog.dialog('open');
}

function dispatchBrowseFTP(nomeField, nomeSpan, thisObject, actionFlag,
		theRecord, originalFileName, xPathPrefix, physDoc) {
	/*
	 * alert("nomeSpan "+nomeSpan); alert("thisObject "+thisObject);
	 * alert("actionFlag "+actionFlag); alert("theRecord "+theRecord);
	 * 
	 */
	var originalFileNameParam = "";
	if (originalFileName != null) {
		// alert(" VUOI originalFileName "+originalFileName);
		originalFileNameParam = "&flagOriginalFileName=true&destOriginalFileName="
				+ originalFileName + "&xPathPrefix=" + xPathPrefix;
	} else {
		// alert(" NON VUOI originalFileName "+originalFileName);
		originalFileNameParam = "";
	}
	if (actionFlag.indexOf('Move') != -1) {
		browseFTPMove(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	} else if (actionFlag.indexOf('Resize') != -1) {
		browseFTPResize(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	} else if (actionFlag.indexOf('Match') != -1) {
		browseFTPMatch(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	}
}
function browseFTPMatch(nomeField, nomeSpan, thisObject, actionFlag, theRecord,
		originalFileNameParam, physDoc) {
	percorso = "./FtpBrowsingServlet?idRecord=" + theRecord
			+ "&azione=match&destSpan=" + escape(nomeSpan) + "&dest="
			+ escape(nomeField) + "&physDoc=" + physDoc + originalFileNameParam;
	popUpload = window.open(percorso, '_uploadFoto', 'width=620,height=520');
}
function browseFTPResize(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc) {
	// alert( thisObject.name);
	// alert( thisObject.id);
	percorso = "./FtpBrowsingServlet?idRecord=" + theRecord + "&azione=moveResize&destSpan=" + escape(nomeSpan) + "&dest="+ escape(nomeField) + "&physDoc=" + physDoc + originalFileNameParam;
	popUpload = window.open(percorso, '_uploadFoto', 'width=620,height=520');
}

function browseFTPMove(nomeField, nomeSpan, thisObject, actionFlag, theRecord,
		originalFileNameParam, physDoc) {
	// percorso="./FtpBrowsingServlet?idRecord="+theRecord+"&azione=move&destSpan="+escape(nomeSpan)+"&dest="+escape(percorsoImg)
	percorso = "./FtpBrowsingServlet?idRecord=" + theRecord
			+ "&azione=move&destSpan=" + escape(nomeSpan) + "&dest="
			+ escape(nomeField) + "&physDoc=" + physDoc + originalFileNameParam;
	popUpload = window.open(percorso, '_uploadFoto', 'width=620,height=520');
	return false;
}

campoUpload = "";
sezioneUpload = "";
/*function sfoglia(theDb, theField, thePrefix) {
	percorsoImg = theField.parentNode.getElementsByTagName("input")[0];
	campoUpload = percorsoImg.name;
	sezioneUpload = thePrefix;
	document.imgForm.db.value = theDb;
	document.imgForm.url.value = percorsoImg.value;
	document.imgForm.thePrefix.value = thePrefix;
	ftpNavigazione = window.open('', 'ftpNavigazione', '');
	document.imgForm.submit();
	return false;
}*/

function associate(theRecord, theField, thePrefix, theUploadMode, originalFileName, xPathPrefix, physDoc) {
	percorsoImg = theField.parentNode.getElementsByTagName("input")[0].name;
	if (isNaN(parseInt(physDoc, 10)) || parseInt(physDoc, 10) == 0
			|| parseInt(physDoc, 10) == -1) {
		alert(getLocalizedString('Attenzione_physDoc_non_settato','Attenzione physDoc non settato')+"." + physDoc);
		return false;
	}
	if (theUploadMode.indexOf('associate') != -1) {
		dispatchAssociate(percorsoImg, thePrefix, theField, theUploadMode, theRecord, originalFileName, xPathPrefix, physDoc);
	} 
}

function dispatchAssociate(nomeField, nomeSpan, thisObject, actionFlag, theRecord,	originalFileName, xPathPrefix, physDoc) {
	var originalFileNameParam = "";
	if (originalFileName != null) {
		originalFileNameParam = "&amp;flagOriginalFileName=true&amp;destOriginalFileName=" + originalFileName + "&amp;xPathPrefix=" + xPathPrefix;
	} else {
		originalFileNameParam = "";
	}
	if (actionFlag.indexOf('Simple') != -1) {
		associateSimple(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc);
	}  
}

function associateSimple(nomeField, nomeSpan, thisObject, actionFlag, theRecord, originalFileNameParam, physDoc) {
	percorso =  globalOption.contextPath+"/associate/" + globalOption.theArch + "/associateMenu.html?destField=" + escape(nomeField)+"&amp;idRecord="+theRecord+"&amp;physDoc="+physDoc+"&amp;uploadType=associate"+originalFileNameParam;
	if($dialog!==null){chiudiDialog();}
	//devo cambiare la modalità di apertura, devo usare quella di vis xml.
 	  $dialog = $('<div></div>').html('<iframe style="border: 0px; " src="' + percorso + '" width="100%" height="100%"></iframe>').dialog({autoOpen: false,modal: false, height: 558,width: 558,resizable : true});
 	  $dialog.dialog({ modal: true,resizable : true });
 	  $dialog.dialog('open');
 	
 
 	
 	
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
function salvaFoto(theValue, thePath) {
	for (var i = 0; i < document.theForm.length; i++) {
		if (document.theForm.elements[i].name == thePath)
			document.theForm.elements[i].value = theValue;
	}

	if (thePath.indexOf('dao') > 0) {
		// TODO: gestire pi? gruppi
		prefix = thePath.substring(0, thePath.lastIndexOf("[") + 1);
		suffix = thePath.substring(thePath.lastIndexOf("]"), thePath.length);
		idElement = prefix.substring(0, prefix.indexOf("daoloc[")) + '@id';
		if (document.theForm[idElement] != null)
			document.theForm[idElement].value = 'ID001';
	}
}

function salvaFotoMulti(fotoArray, thePath, spanId) {
	var generatedPath = $("[@name=\"" + thePath + "\"]:first");
	var container = generatedPath.parents("[@id^='" + spanId + "']");
	var instance = container.find("[@id^='control_" + spanId + "'] > a");
	var generatedPathIndex = -1;
	$.each(fotoArray, function(i, v) {
		if (i == fotoArray.length - 1)
			return false;
		var generatedName = v;

		if (i > 0) {
			var newNode = jclone(instance, true, false);
			generatedPath = $(newNode.find("[@id^='" + spanId + "']").find("input,textarea,select").get(generatedPathIndex));
			generatedPath.val(generatedName);
		} else {
			generatedPathIndex = container.find("input,textarea,select").index(generatedPath.get(0));
			generatedPath.val(generatedName);
		}
	});
}

function salvaFotoMultiAndOriginal(fotoArray, thePath, spanId, originalArray, thePathOriginal) {
	var generatedPath = $("[@name=\"" + thePath + "\"]:first");
	var originalPath = $("[@name=\"" + thePathOriginal + "\"]:first");
	var container = generatedPath.parents("[@id^='" + spanId + "']");
	var instance = container.find("[@id^='control_" + spanId + "'] > a");
	var generatedPathIndex = -1;
	var originalPathIndex = -1;
	generatedPathIndex = container.find("input,textarea,select").index(generatedPath.get(0));
	originalPathIndex = container.find("input,textarea,select").index(originalPath.get(0));
	$.each(fotoArray, function(i, v) {
		if (i == fotoArray.length - 1)
			return false;
		var generatedName = v;
		var originalName = originalArray[i];
		// alert("aaa "+i);
		// alert("fotoArray.length-1 "+(fotoArray.length-1));
		if (i < (fotoArray.length - 2)) {
			var newNode = jclone(instance, true, true);
			generatedPath = $(newNode.find("[@id^='" + spanId + "']").find("input,textarea,select").get(generatedPathIndex));
			originalPath = $(newNode.find("[@id^='" + spanId + "']").find("input,textarea,select").get(originalPathIndex));
			generatedPath.val(generatedName);
			originalPath.val(originalName);
		} else {
			$("[@name=\"" + thePath + "\"]:first").val(generatedName);
			$("[@name=\"" + thePathOriginal + "\"]:first").val(originalName);
		}
	});
}
