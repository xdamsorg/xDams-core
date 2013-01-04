var $dialog = null;

function allega(theRecord, theField, thePrefix, theUploadMode, originalFileName, xPathPrefix, physDoc) {
	percorsoImg = theField.parentNode.getElementsByTagName("input")[0].name;
	// alert("thePrefix "+thePrefix);
	if (isNaN(parseInt(physDoc, 10)) || parseInt(physDoc, 10) == 0
			|| parseInt(physDoc, 10) == -1) {
		alert("Attenzione physDoc non settato." + physDoc);
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
 	percorso =  globalOption.contextPath+"/upload/" + globalOption.theArch + "/uploadMenu.html?destField=" + escape(nomeField)+"&amp;idRecord="+theRecord+"&amp;physDoc="+physDoc+"&amp;uploadType=resize";
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
function sfoglia(theDb, theField, thePrefix) {
	percorsoImg = theField.parentNode.getElementsByTagName("input")[0];
	campoUpload = percorsoImg.name;
	sezioneUpload = thePrefix;
	document.imgForm.db.value = theDb;
	document.imgForm.url.value = percorsoImg.value;
	document.imgForm.thePrefix.value = thePrefix;
	ftpNavigazione = window.open('', 'ftpNavigazione', '');
	document.imgForm.submit();
	return false;
}
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
/*
 * function salvaFotoMulti(fotoArray,thePath,spanId){ firstElementIdx = 0;
 * for(i=0;i<document.theForm.length;i++){ if(document.theForm.elements[i].name ==
 * thePath){ document.theForm.elements[i].value = fotoArray[0]; firstElementIdx =
 * i; } } contaArray = 1; prefix =
 * thePath.substring(0,thePath.lastIndexOf("[")+1); suffix =
 * thePath.substring(thePath.lastIndexOf("]"),thePath.length); ilNumero =
 * parseInt(thePath.substring(thePath.lastIndexOf("[")+1,thePath.lastIndexOf("]")),10);
 * ilNumero++; // alert(fotoArray) //alert(thePath) //alert(spanId)
 * //alert('prefix '+prefix + '\nsuffix '+ suffix + '\nilNumero '+ilNumero)
 * if(isNaN(ilNumero)){ salvaFoto(fotoArray[0],thePath); return; } for(i=1;i<fotoArray.length;i++){
 * found = false; for(a=firstElementIdx;a<document.theForm.length;a++){
 * if(document.theForm.elements[a].name == prefix+ilNumero+suffix){
 * if(document.theForm.elements[a].value == ''){
 * document.theForm.elements[a].value = fotoArray[contaArray]; contaArray++;
 * firstElementIdx = a; found = true; break; }else{ ilNumero++ a=firstElementIdx } } }
 * if(!found){ i--; if(spanId.indexOf('dao')>0){ addInstanceNastedDaoGrp(spanId,
 * 1, null) }else{ addInstance(spanId, 1, null) } }else{ //TODO: gestire pi?
 * gruppi if(spanId.indexOf('dao')>0){ idElement =
 * prefix.substring(0,prefix.indexOf("daoloc["))+'@id'
 * document.theForm[idElement].value = 'ID001' }
 *  } } }
 */
/*
 * function
 * salvaFotoMultiAndOriginal(fotoArray,thePath,spanId,originalArray,thePathOriginal){
 * firstElementIdx = 0; for(i=0;i<document.theForm.length;i++){
 * if(document.theForm.elements[i].name == thePath){
 * document.theForm.elements[i].value = fotoArray[0]; firstElementIdx = i; }
 * if(document.theForm.elements[i].name == thePathOriginal){
 * document.theForm.elements[i].value = originalArray[0]; } } contaArray = 1;
 * prefix = thePath.substring(0,thePath.lastIndexOf("[")+1); suffix =
 * thePath.substring(thePath.lastIndexOf("]"),thePath.length); ilNumero =
 * parseInt(thePath.substring(thePath.lastIndexOf("[")+1,thePath.lastIndexOf("]")),10);
 * ilNumero++;
 * 
 * suffixOriginal =
 * thePathOriginal.substring(thePathOriginal.lastIndexOf("]"),thePathOriginal.length);
 * //ilNumeroOriginal =
 * parseInt(thePathOriginal.substring(thePathOriginal.lastIndexOf("[")+1,thePathOriginal.lastIndexOf("]")),10);
 * //ilNumeroOriginal++;
 * 
 * //alert(fotoArray) //alert(thePath) //alert(spanId) //alert('prefix '+prefix +
 * '\nsuffix '+ suffix + '\nilNumero '+ilNumero) if(isNaN(ilNumero)){
 * salvaFoto(fotoArray[0],thePath); return; } for(i=1;i<fotoArray.length;i++){
 * found = false; var indiceOriginal = -1; for(a=firstElementIdx;a<document.theForm.length;a++){
 * if(document.theForm.elements[a].name == prefix+(ilNumero)+suffixOriginal){
 * if(document.theForm.elements[a].value == ''){ indiceOriginal = a; } }
 * if(document.theForm.elements[a].name == prefix+ilNumero+suffix){
 * if(document.theForm.elements[a].value == ''){ //alert("a = "+a+" --->
 * indiceOriginal = "+indiceOriginal); document.theForm.elements[a].value =
 * fotoArray[contaArray]; document.theForm.elements[indiceOriginal].value =
 * originalArray[contaArray]; contaArray++; firstElementIdx = a; found = true;
 * break; }else{ ilNumero++ a=firstElementIdx } } }
 * 
 * if(!found){ i--; if(spanId.indexOf('dao')>0){ addInstanceNastedDaoGrp(spanId,
 * 1, null) } else{ addInstance(spanId, 1, null) } } else{
 * 
 * //TODO: gestire pi? gruppi if(spanId.indexOf('dao')>0){ idElement =
 * prefix.substring(0,prefix.indexOf("daoloc["))+'@id'
 * document.theForm[idElement].value = 'ID001' } } } }
 */

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
