var doAfterSave;
function afterSave(){
	if(doAfterSave!=null){
	 	doAfterSave();
	}
}
function fillAllFieldForTest(){
	$("input[type='text']").each(function(){
		$(this).val("ciaooo");
	});
	$("select :last-child").each(function(){
		$(this).attr("selected", "true");
	});

	$("textarea").each(function(){
		$(this).val("ciaooo");
	});
}

function spreadModSave(actionParam , elementName, codeToFind){
	ritorno = false;
	if(actionParam=='insert'){
		$.ajax({ 
				  url: 'ManagingServlet',
				  data: {actionFlag:'spreadMod',makeAction:true,action:actionParam,physDoc:$('input[name=physDoc]').val()},
				  type: 'POST',
				  cache: false,
				  async: false,
				  beforeSend: function(){
				  		xDamsModalMessageParam(getLocalizedString('propagazione_relazioni_in_corso','propagazione relazioni in corso'),'100px','180px');
				  },success: function(data) {
    					xDamsModalMessageParam(getLocalizedString('relazioni_salvate_con_successo','relazioni salvate con successo'),'100px','180px');
    					$.unblockUI();
    					ritorno = true;
  				  },error: function(data) {
  				  		$.unblockUI();
  				  		xDamsModalAlert(getLocalizedString('Attenzione_errore_nel_salvataggio_delle_relazioni','Attenzione, errore nel salvataggio delle relazioni'));
  				  		alert(data.responseText);
    					ritorno = false;
  				  } 
		});
	}else if(actionParam=='delete'){
		$.ajax({
				  url: 'ManagingServlet',
				  data: {actionFlag:'spreadMod',makeAction:true,action:actionParam,physDoc:$('input[name=physDoc]').val(),elementName:elementName,codeToFind:codeToFind},
				  type: 'POST',
				  cache: false,
				  async: false,
				  beforeSend: function(){
				  		xDamsModalMessageParam(getLocalizedString('rimozione_delle_relazioni_in_corso','rimozione delle relazioni in corso'),'100px','180px')
				  },success: function(data) {
    					xDamsModalMessageParam(getLocalizedString('relazioni_rimosse_con_successo','relazioni rimosse con successo'),'100px','180px');
						$.unblockUI();
    					ritorno = true;
  				  },error: function(data) {
  				  		$.unblockUI();
  				  		xDamsModalAlert(getLocalizedString('Attenzione_errore_nella_rimozione_delle_relazioni','Attenzione, errore nella rimozione delle relazioni'));
  				  		alert(data.responseText);
  				  		ritorno = false;
  				  }    
		 });		
	}else if(actionParam=='deleteAll'){
		$.ajax({
				  url: 'ManagingServlet',
				  data: {actionFlag:'spreadMod',makeAction:true,action:actionParam,physDoc:$('input[name=physDoc]').val()},
				  type: 'POST',
				  cache: false,
				  async: false,
				  beforeSend: function(){
				  		xDamsModalMessageParam(getLocalizedString('rimozione_delle_relazioni_in_corso','rimozione delle relazioni in corso'),'100px','180px')
				  },success: function(data) {
    					xDamsModalMessageParam(getLocalizedString('relazioni_rimosse_con_successo','relazioni rimosse con successo'),'100px','180px');
						$.unblockUI();
    					ritorno = true;
  				  },error: function(data) {
  				  		$.unblockUI();
  				  		xDamsModalAlert(getLocalizedString('Attenzione_errore_nella_rimozione_delle_relazioni','Attenzione, errore nella rimozione delle relazioni'));
  				  		alert(data.responseText);
    					ritorno = false;
  				  }
  		});		
	}
	return ritorno;
}

function popagaValore(ilValore, campo01, campo02) {
	if (campo01 != null)
		document.theForm[campo01].value = ilValore
	if (campo02 != null)
		document.theForm[campo02].value = ilValore
	return false
}
function findInput(indice, aa, livelli) {
	var objInput = null;
	if (livelli != null) {
		for (i = 0; i < livelli; i++) {
			aa = aa.parentNode;
		}
		objInput = aa.getElementsByTagName("INPUT");
	} else {
		objInput = aa.parentNode.parentNode.parentNode.parentNode.getElementsByTagName("INPUT");
	}

	return objInput[indice];
}
function clearChar(theString, toTest, toSub) {
	while (theString.indexOf(toTest) > -1) {
		theString = theString.replace(toTest, toSub);
	}
	return theString;
}

function toIso(ilForm) {
	for (var i = 0; i < ilForm.length; i++) {
		ilTag = (ilForm.elements[i].tagName).toUpperCase();
		if (ilTag == 'INPUT' || ilTag == 'TEXTAREA') {
			appoggio = ilForm.elements[i].value;
			appoggio = clearChar(appoggio, String.fromCharCode(8217), "'");
			appoggio = clearChar(appoggio, String.fromCharCode(8221), '"');
			appoggio = clearChar(appoggio, String.fromCharCode(8220), '"');
			appoggio = clearChar(appoggio, String.fromCharCode(8212), '-');
			appoggio = clearChar(appoggio, String.fromCharCode(8211), '-');
			appoggio = clearChar(appoggio, String.fromCharCode(8222), '"');
			appoggio = clearChar(appoggio, String.fromCharCode(11), ' ');
			ilForm.elements[i].value = appoggio;
		}
	}
	return false;
}
function valorizzaLevel(obj, thePrefix) {
	if (obj.value == "fondo")
		document.theForm[thePrefix].value = 'fonds';
	else if (obj.value == "raccolta")
		document.theForm[thePrefix].value = 'collection';
	else if (obj.value == "serie")
		document.theForm[thePrefix].value = 'series';
	else if (obj.value == "sottoserie")
		document.theForm[thePrefix].value = 'subseries';
	else if (obj.value == "album")
		document.theForm[thePrefix].value = 'recordgrp';
	else if (obj.value == "servizio fotografico")
		document.theForm[thePrefix].value = 'file';
	else if (obj.value == "singola foto")
		document.theForm[thePrefix].value = 'item';
	else if (obj.value == "scatola")
		document.theForm[thePrefix].value = 'file';
	else if (obj.value == "sottofondo")
		document.theForm[thePrefix].value = 'subfonds';
	else if (obj.value == "serie tematica")
		document.theForm[thePrefix].value = 'file';
	else if (obj.value == "provino a contatto")
		document.theForm[thePrefix].value = 'item';
	else if (obj.value == "panorama")
		document.theForm[thePrefix].value = 'file';
	else if (obj.value == "raccolta")
		document.theForm[thePrefix].value = 'collection';	
	else
		document.theForm[thePrefix].value = '';
	return true;
}
function testaLevel(obj, thePrefix) {
	if (typeof (customTestaLevel) != 'undefined') {
		customTestaLevel(obj, thePrefix);
	} else {
		if (obj != null) {
			if (obj.value == 'otherlevel') {
				document.theForm[thePrefix + '.@otherlevel'].readOnly = false;
				document.theForm[thePrefix + '.@otherlevel'].focus();
			} else {
				document.theForm[thePrefix + '.@otherlevel'].readOnly = true;
				document.theForm[thePrefix + '.@otherlevel'].value = '';
			}
		}
	}
	return true;
}

function invalidaElementoAuther(obj) {
	if (obj.value != '')
		obj.style.backgroundColor = "#FF886A";
	else
		obj.style.backgroundColor = "#FFFFFF";
	for (var i = 0; i < obj.parentNode.childNodes.length; i++) {
		if (obj.parentNode.childNodes[i].name != null && obj.parentNode.childNodes[i].name != '' && obj.parentNode.childNodes[i].name != obj.name) {
			obj.parentNode.childNodes[i].value = "";
		}
	}
	return true;
}

function testaLingua(obj, thePrefix) { // TODO rendere generico
	laLinguaCode = document.theForm[thePrefix + '.@langcode'].value
	laLingua = ""
	if (laLinguaCode == 'ITA')
		laLingua = 'italiano';
	if (laLinguaCode == 'HEB')
		laLingua = 'ebraico';
	if (laLinguaCode == 'LAT')
		laLingua = 'latino';
	if (laLinguaCode == 'DUT')
		laLingua = 'olandese';
	if (laLinguaCode == 'FRE')
		laLingua = 'francese';
	if (laLinguaCode == 'ENG')
		laLingua = 'inglese';
	if (laLinguaCode == 'SPA')
		laLingua = 'spagnolo';
	if (laLinguaCode == 'GER')
		laLingua = 'tedesco';
	document.theForm[thePrefix + '.text()'].value = laLingua;
}

function senzaData(thePrefix) {
	try{document.theForm[thePrefix + '.@normal'].value = '';}catch(a){
		try{document.theForm[thePrefix + '.@standardDate'].value = '';}catch(a){}
	}
	document.theForm[thePrefix + '.text()'].value = 's.d.';
	document.theForm['GIORNO_INI' + thePrefix].value = '';
	document.theForm['MESE_INI' + thePrefix].value = '';
	document.theForm['ANNO_INI' + thePrefix].value = '';
	document.theForm['GIORNO_FIN' + thePrefix].value = '';
	document.theForm['MESE_FIN' + thePrefix].value = '';
	document.theForm['ANNO_FIN' + thePrefix].value = '';
	try{document.theForm[thePrefix + '.date[1]'].value = '';}catch(a){}
}

function visualizzaImg(obj, pathPrefix) {
	if (typeof (customVisualizzaImg) == 'undefined') {
		percorsoImg = obj.parentNode.getElementsByTagName("input")[0].value
		if (percorsoImg != '')
			window.open(pathPrefix + percorsoImg, 'imgWindow', 'status=yes,resizable=yes,scrollbars=yes,width=400, height=450');
	} else {
		customVisualizzaImg(obj, pathPrefix)
	}
	return false
}

function sostituisciTutto(laStringa, oldVal, newVal) {
	appoggio = laStringa;
	while (appoggio.indexOf(oldVal) != -1) {
		appoggio = appoggio.replace(oldVal, "?|?")
	}
	while (appoggio.indexOf('?|?') != -1) {
		appoggio = appoggio.replace('?|?', newVal)
	}
	return appoggio;
}
 
function performLookup(db_name, inputExtraQuery, inputSort, inputTitleRule, inputUdType, inputQuery, campoDescr, campiDaValorizzare, titleRule, theObj, theWebApp, flagXML, outputJSP) {
	campiDaValorizzare = "";
	valoreIniziale = "";
 	$(theObj).parents("table:first").find("input").each(function(i, v) {
		if (v.name != null && v.name != '') {
			if (i == 0) {
				valoreIniziale = v.value;
				campoDescr = v.name;
			}
			ilNome = sostituisciTutto(v.name, "'", "\\'");
			campiDaValorizzare += "'" + ilNome + "',";
		}
	});

	campiDaValorizzare = campiDaValorizzare.substring(0, campiDaValorizzare.length - 1);
	document.lookupMain.inputExtraQuery.value = inputExtraQuery;
	document.lookupMain.inputQuery.value = inputQuery;
	document.lookupMain.inputSort.value = inputSort;
	document.lookupMain.inputTitleRule.value = inputTitleRule;
	document.lookupMain.inputUdType.value = inputUdType;
	document.lookupMain.titleRule.value = titleRule;
 	if (flagXML != '' && flagXML != null){
		document.lookupMain.flagXML.value = flagXML;
	}else{
 		document.lookupMain.flagXML.value = 'false';
	}	
	if (outputJSP != '' && outputJSP != null){
		document.lookupMain.jspOutPut.value = outputJSP;
	}else{
		document.lookupMain.jspOutPut.value = '';
	}	
	if (theObj.parentNode.childNodes[0] != null) {
		document.lookupMain.inputStrQuery.value = valoreIniziale;
		document.lookupMain.campiDaValorizzare.value = campiDaValorizzare;
	} else {
		document.lookupMain.inputStrQuery.value = document.theForm[campoDescr].value;
		document.lookupMain.campiDaValorizzare.value = campiDaValorizzare;
	}
 
	document.lookupMain.action = globalOption.lookupAction+db_name+".html"; 
	window.open('', 'lookupWindow', 'status=yes,resizable=yes,scrollbars=yes,width=400, height=450');
	document.lookupMain.target = "lookupWindow";
	document.lookupMain.submit();
}

 
function ripristinaForm(msg) {
	if (confirm(msg)) {
		document.theForm.reset();
		return true;
	} else
		return false;
}

function chiudiPagina(msg) {
	$.blockUI( {
		message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1><input type="button" id="yes" value=" [ chiudi ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ annulla ]" /> </div> ',
		css : {
			height : '100px',
			width : '275px'
		}
	});
	$('#yes').click(function() {
		self.close();
		return true;
	});
	$('#no').click(function() {
		$.unblockUI();
		return false;
	});
}

function submitForm(theForm, toCheck, msg, ilTarget, postSave, goServlet,extraFunction) {
	tinyMCE.triggerSave();
	if (msg != null && msg != '') {
		hideAllLayers()
		$.blockUI( {
			message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1><input type="button" id="yes" value=" [ si ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ no ]" /> </div> ',
			css : {
				height : '100px',
				width : '275px'
			}
		});
		$('#yes').click(function() {
			$.blockUI( {
				message : '<h1><img src="' + globalOption.frontPath + '/img/busy.gif" />salvataggio in corso...</h1>'
			});
			doSubmitForm(true, theForm, toCheck, msg, ilTarget, postSave, goServlet,extraFunction);
			showLastLayer()
		});
		$('#no').click(function() {
			doSubmitForm(false, theForm, toCheck, msg, ilTarget, postSave, goServlet,extraFunction);
			showLastLayer()
		});

	} else {
		xDamsModalMessage(getLocalizedString('salvataggio_rapido_in_corso','salvataggio rapido in corso')+'...');
		doSubmitForm(true, theForm, toCheck, msg, ilTarget, postSave, goServlet,extraFunction);
	}
 
}

function doSubmitForm(ilTest, theForm, toCheck, msg, ilTarget, postSave, goServlet,extraFunction) {
	ritorno = true;
	rapidMode = false;
	//il deve essere prima il 'doSaveAndClose.jsp|scheda.jsp'
	var originalPostSave = postSave; 
	if(originalPostSave.indexOf("|")!=-1){
		postSave = originalPostSave.substring(0,originalPostSave.indexOf("|"));
	}
	
	//alert("postSave "+postSave);
	//alert("goServlet "+goServlet);
	 
	theForm['go'].value = postSave;
	theForm['goServlet'].value = goServlet;
	if (ilTarget != null) {
		theForm.target = ilTarget;
		if (ilTarget == '_blank') {
			rapidMode = true
			theForm.target = "schedaSalvaRapida";
		}
	}
	if (!salvaRTA()) {
		return false;
	}
	if (ilTest) {
		var toCheckArray = new Array;
		if (toCheck != null && toCheck != '') {
			toCheckArray = toCheck.split(";");
			for (i = 0; i < toCheckArray.length; i++) {
				nomeTmp = toCheckArray[i]
				if (nomeTmp.indexOf("|") > 0) {
					toCheckArrayArray = nomeTmp.split("\|");
					ritornoTemp = false;
					messaggio = "";
					for (ia = 0; ia < toCheckArrayArray.length; ia++) {
						messaggio += toCheckArrayArray[ia] + "\n";
						if (theForm[toCheckArrayArray[ia]] != null) {
							if (theForm[toCheckArrayArray[ia]].value != "") {
								ritornoTemp = true;
								break;
							}
						} else {
							ritornoTemp = true;
							break;
						}
					}
					if (!ritornoTemp) {
						xDamsAlert(getLocalizedString('Attenzione','Attenzione')+": \n" + messaggio.replace(/\./gi, '/') + ' = ""');
						$.unblockUI();
						return true;
					}
				} else {
					if (nomeTmp.indexOf("?") > 0) {
						toCheckArrayArray = nomeTmp.split("?");
						ritornoTemp = false;
						messaggio = "";
						for (i = 0; i < toCheckArrayArray.length; i++) {
							messaggio += toCheckArrayArray[i] + "\n";
							if (theForm[toCheckArrayArray[i]] != null) {
								if (theForm[toCheckArrayArray[i]].value != "") {
									ritornoTemp = true;

								} else {
									ritornoTemp = false;
									break
								}
							} else {
								ritornoTemp = true;
								break
							}
						}
						if (!ritornoTemp) {
							xDamsAlert(getLocalizedString('Attenzione','Attenzione')+": \n" + messaggio.replace(/\./gi, '/') + ' = ""');
							$.unblockUI();
							return true;
						}
					} else {
						if (theForm[toCheckArray[i]] != null) {
							if (theForm[toCheckArray[i]].value == "") {
								ritorno = false;
								xDamsAlert(getLocalizedString('Attenzione','Attenzione')+": \n" + toCheckArray[i].replace(/\./gi, '/') + ' = ""');
								$.unblockUI();
								break;
							}
						}
					}
				}
			}
		}
		if (ritorno) {
			if (rapidMode) {
				schedaSalvaRapidaWin = window.open('', 'schedaSalvaRapida', 'width=10,height=10');
				theForm.submit();
				$.unblockUI();
				if(originalPostSave.indexOf("|")!=-1){
					postSave = originalPostSave.substring(originalPostSave.indexOf("|")+1);
					theForm['go'].value = postSave;
					if(extraFunction!=null && extraFunction!='undefined'){
 					//alert("QUIIIIIIIIIIIII");
	//					alert(theForm['goServlet'].value);
	//					alert(theForm.action);
						doAfterSave = function(){
							if(eval(extraFunction)){
								//alert("QUAAAAAAAAAAAAAA");
								document.location=theForm['goServlet'].value+'?go='+theForm['go'].value+'&pos='+theForm['pos'].value+'&selid='+theForm['selid'].value+'&physDoc='+theForm['physDoc'].value;
							}
							
						};
					}else{
					doAfterSave = function(){document.location=theForm['goServlet'].value+'?go='+theForm['go'].value+'&pos='+theForm['pos'].value+'&selid='+theForm['selid'].value+'&physDoc='+theForm['physDoc'].value;};
					}
					
				}else{
					if(extraFunction!=null && extraFunction!='undefined'){
						doAfterSave = function(){eval(extraFunction);};
					}
				}
			} else {
				theForm.submit();
			}
				
		
		// alert(extraFunction);

		}
		return true;
	} else {
		$.unblockUI();
		return false;
	}
}

function setResponsabile(azione, elementoText, elementoData, elementoPersname, msg, valAction, valName) {
	ilNumero = elementoText.substring(elementoText.indexOf("[") + 1, elementoText.indexOf("]"));
	ilNumeroInt = parseInt(ilNumero, 10);
	ilNumeroInt--;

	elementoDataPrecedente = elementoData.substring(0, elementoData.indexOf("[") + 1);
	elementoDataPrecedente += ilNumeroInt + '';
	elementoDataPrecedente += elementoData.substring(elementoData.indexOf("]"), elementoData.length);

	elementoPersnamePrecedente = elementoPersname.substring(0, elementoPersname.indexOf("[") + 1);
	elementoPersnamePrecedente += ilNumeroInt + '';
	elementoPersnamePrecedente += elementoPersname.substring(elementoPersname.indexOf("]"), elementoPersname.length);

//	if (window.opener == null) {
//		if (window.opener.parent.frames.data == null) {
//			alert(msg)
//			self.close()
//			return

//		}
//	} else {

		laDataPrecedente = "";
		if (document.theForm[elementoDataPrecedente] != null)
			laDataPrecedente = document.theForm[elementoDataPrecedente].value;

		nomeCognomePrecedente = "";
		if (document.theForm[elementoPersnamePrecedente] != null)
			nomeCognomePrecedente = document.theForm[elementoPersnamePrecedente].value;

		if (laData.indexOf(laDataPrecedente) == 0 && nomeCognome.indexOf(nomeCognomePrecedente) == 0) {
			document.theForm[elementoText].value = '';
			document.theForm[elementoData].value = '';
			document.theForm[elementoPersname].value = '';
		} else {
			if (valAction != null && valName != null) {
				if (valAction == azione && valName == nomeCognome)
					return

			}
			document.theForm[elementoText].value = azione;
			document.theForm[elementoData].value = laData;
			document.theForm[elementoPersname].value = nomeCognome;
		}
//	}
}

function suggerisciLevel(ilLivello) {
	if (document.theForm['.c.@level'] != null && ilLivello != '') {
		document.theForm['.c.@level'].value = ilLivello;
		if (ilLivello == 'otherlevel')
			document.theForm['.c.@otherlevel'].disabled = '';
	}
	return false;
}

function normalizzaVoce(obj) {

	theName = obj.name;

	if (theName.indexOf('.text()') != -1)
		theName = theName.substring(0, lastIndexOf(theName, "."));

	thePrefix = theName.substring(0, lastIndexOf(theName, "."));

	listaElementi = obj.parentNode.parentNode.parentNode.getElementsByTagName("input");

	part01 = "";
	part02 = "";
	part03 = "";

	for (i = 0; i < listaElementi.length; i++) {
		if ((listaElementi[i].name).indexOf('@type=\'first\'') != -1)
			part01 = listaElementi[i].value;
		if ((listaElementi[i].name).indexOf('@type=\'ord\'') != -1)
			part02 = listaElementi[i].value;
		if ((listaElementi[i].name).indexOf('@type=\'add\'') != -1)
			part03 = listaElementi[i].value;
	}
	for (i = 0; i < listaElementi.length; i++) {
		if ((listaElementi[i].name).indexOf('normal') != -1) {
			listaElementi[i].value = ritornaVoce(part01, part02, part03);
		}

	}

}

function normalizzaVoceEACCPF(obj) {
	theName = obj.name
	if (theName.indexOf('.text()') != -1)
		theName = theName.substring(0, lastIndexOf(theName, "."));

	thePrefix = theName.substring(0, lastIndexOf(theName, "."));
	listaElementi = obj.parentNode.parentNode.parentNode.getElementsByTagName("input");
	part01 = "";
	part02 = "";
	part03 = "";
	for (i = 0; i < listaElementi.length; i++) {
		if ((listaElementi[i].name).indexOf('@localType=\'prime\'') != -1)
			part01 = listaElementi[i].value;
		if ((listaElementi[i].name).indexOf('@localType=\'ord\'') != -1)
			part02 = listaElementi[i].value;
		if ((listaElementi[i].name).indexOf('@localType=\'ulterior\'') != -1)
			part03 = listaElementi[i].value;
	}
	for (i = 0; i < listaElementi.length; i++) {
		if ((listaElementi[i].name).indexOf('normal') != -1) {
			listaElementi[i].value = ritornaVoce(part01, part02, part03);
		}
	}
}

function ritornaVoce(part01, part02, part03) {

	if (typeof (customRitornaVoce) != 'undefined') {
		laStringa = customRitornaVoce(part01, part02, part03);
		return laStringa;
	} else {
		laStringa = part02;
		if (laStringa != '' && part01 != '')
			laStringa += ', ';

		laStringa += part01;

		if (laStringa != '' && part03 != '')
			laStringa += ' (' + part03 + ')';

		return laStringa;
	}
}
function modificaFormaAutorizzata(prefix) {
	var obj = null;
	try{
		if(prefix.indexOf('.eac.')!=-1){
			obj = document.theForm[prefix + '.@normal'].value;
		}else if(prefix.indexOf('.eac-cpf.')!=-1){
			obj = document.theForm[prefix+'.part\[@localType=\'normal\'\].text()'].value;
		}else{
			obj = document.theForm[prefix].value;
		}
		}catch(e){
			//nel caso non sia contemplato
			obj = document.theForm[prefix].value;
		}
	
	var ilLemma = escape(escape(obj));
	var ilLemmaCode = "";
	try{
	    ilLemmaCode = document.theForm['.eac.eacheader.eacid.text()'].value; // TODO
	}catch(ee){
		ilLemmaCode = document.theForm['.eac-cpf.control.recordId.text()'].value; //TODO generalizzare
	} 
	var physDoc = document.theForm['physDoc'].value;
	if(ilLemmaCode!=""){
		urlWin =  globalOption.contextPath+ "/" + globalOption.theArch + "/managing.html?actionFlag=modifyAuther&codeToFind=" + ilLemmaCode + "&prefix=" + escape(prefix) + "&nameToFind=" + (ilLemma)+"&physDoc="+physDoc;
		//window.open(percorso, 'theAuther', 'width=400,height=450,scrollbars=yes')
	//	var jsonObject = eval('(' + jsonValues + ')');
		var widthProto = "450";
		var heightProto = "400";
		var statusBar= "proietta modifica";

		$('#aDialog').remove();
		$('body').append('<div id="aDialog"></div>');
	 
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

	return false;
}

/* ADD - REMOVE */
function addInstance(instance, insert) {
	jclone(instance, insert);
}
function copyInstance(instance, insert) {
	var father;
	var after;
	if (insert) {
		father = $(instance).parents("[id^='sezione_']:first");
	} else {
		father = $(instance).parents("span:eq(3)").nextAll("span[id^='sezione_']:first");
	}

	var parentSpanID = $(instance).parents("span:eq(1)").attr('id');
	// alert(parentSpanID);
	parentSpanID = parentSpanID.replace('control_', '').replace('[', '.').replace(']', '');
	// alert(parentSpanID);
	// var parentSpanCont = parentSpanID.replace('--',
	// '[@');//((((parentSpanID.replace('.', '/')).replace('_',
	// '\'')).replace('-', '[')).replace('-', ']')).replace('-', '
	// ').replace('-', '@').replace('-', '=').replace('-', '(').replace('-',
	// ')');
	// alert(parentSpanCont);
	valueToCopy = new Array();
	$(instance).parents("span[id^='" + parentSpanID + "']:first").find("*").each(function() {
		// alert($(this).attr('name'));
			// if($(this).attr('name').indexOf(prefix)!=-1){
			// alert($(this).val());
			if ($(this).is("input,textarea,select")) {
				if ($(this).is("input:radio") && $(this).attr("checked")) {
					valueToCopy.push("checked," + $(this).val());
				} else {
					valueToCopy.push($(this).val());
				}
			}
			// }
		});

	var lastChild = father.children(":last");
	// alert(valueToCopy)
	// alert(valueToCopy.length)

	var newNode = lastChild.clone(true);// .appendTo(id);
	// alert(newNode.val());
	var count = 0;
	newNode.find("*").each(function(v, i) {
		jelement = $(this);
		if (jelement.attr("id") != undefined) {
			var theId = jelement.attr("id");

			var nextId = theId;

			if (/\.\d+$/.test(theId)) {
				var n = parseInt(theId.substring(lastIndexOf(theId, ".") + 1));
				n++;
				nextId = theId.substring(0, lastIndexOf(theId, ".") + 1) + n;
			} else if (/\[\d+\]$/.test(theId)) {
				nextId = fixBracketXPathIndex(nextId);
			} else if (/\_text\d*$/.test(theId)) {
				var n = parseInt(theId.substring(lastIndexOf(theId, "_text") + "_text".length));
				n++;
				nextId = theId.substring(0, lastIndexOf(theId, "_text") + "_text".length + 1) + n;
			}

			if (theId != nextId)
				$(this).attr("id", nextId);
		}

		if (jelement.is("input,textarea,select")) {
			jelement.css( {
				backgroundColor : "#FFFFFF"
			});

			var theName = jelement.attr("name");
			theName = theName != undefined ? theName : "";
			var theTitle = jelement.attr("title");
			theTitle = theTitle != undefined ? theTitle : "";

			var newName = "";
			var newTitle = "";

			newName = fixBracketXPathIndex(theName);
			newTitle = fixBracketXPathIndex(theTitle);

			if (newName != theName)
				jelement.attr("name", newName);
			if (newTitle != theTitle)
				jelement.attr("title", newTitle);

			jelement.removeAttr("autocomplete");
			jelement.removeAttr("jqac");

			jelement.unbind("keydown");
			jelement.unbind("keypress");
			jelement.unbind("keyup");
			jelement.unbind("blur");
			if (jelement.is("input:radio") && valueToCopy[count].indexOf("checked,") != -1) {
				jelement.val(valueToCopy[count].replace("checked,", ""));
				jelement.attr("checked", "checked");
			} else {
				jelement.val(valueToCopy[count]);
			}

			// jelement.val('');
			count++;
		}

		if (jelement.children().length == 0 && jelement.text() != undefined && trim(jelement.text()) != "") {
			var theText = jelement.text();
			theText = arrangeString(theText, undefined, /\((\d+)\)/g);

			jelement.text(theText);
		}

		if (jelement.attr("onclick") != undefined && false) {
			var theClick = String($(this).attr("onclick"));
			if (theClick.substr(0, "function".length) == "function") {
				theClick = trim(theClick.substring(theClick.indexOf("{") + 1, lastIndexOf(theClick, "}")));
			}

			theClick = arrangeString(theClick);
			// jelement.attr("onclick", String(theClick));
			jelement.click(function() {
				eval(theClick);
			});
		}

	});

	if (insert) {
		if (after) {
			newNode.insertAfter($(instance).parents("span:eq(2)"));
		} else {
			newNode.insertBefore($(instance).parents("span:eq(2)"));
		}
		arrange(newNode);
	} else {
		newNode.appendTo(father);
		arrange(newNode);
	}

	return newNode;
}
function removeInstance(instance) {
	jremove(instance);
}
function removeAttach(instance) {
	jremoveAttach(instance);
}
function removeInstanceSpread(instance,elementName,unRemove) {
	jremoveSpread(instance,elementName,unRemove);
}

function jremoveAttach(instance) {
	var jelement = $(instance).parents("span:eq(1)").parent(":first");
	var flagRimuovi = true;
	// alert(jelement.html());
	var isFull = false;
	jelement.find("input,textarea,select").each(function() {
		tempOnClick = $(this).next().attr('onclick') + "";
		inputValue = $(this).val();
		if (inputValue != '') {
			isFull = true;
		}
	});
	// alert(jelement.siblings().length);

	if (jelement.siblings().length > 0 || isFull) {
		jelement.find("input,textarea,select").each(function() {
			tempOnClick = $(this).next().attr('onclick') + "";
			var inputValue = $(this).val();
			if ($(this).val() != '' && tempOnClick != null && (tempOnClick).indexOf("allega") != -1) {
				// alert("QUI "+inputValue);
				var tempArr = tempOnClick.split(",")[3];
				tempArr = tempArr.replace(/'/gi, '');
				msg = getLocalizedString('attenzione_si_sta_tentando_di_eliminarebr_una_sezione_che_contiente_un_allegato_digitale_continuare','attenzione si sta tentando di eliminare<br/> una sezione che contiente un allegato digitale, continuare' )+"?";
				$.blockUI( {
					message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1><input type="button" id="yes" value=" [ si ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ no ]" /> </div> ',
					css : {
						height : '100px',
						width : '275px'
					}
				});
				$('#yes').click(function() {
					/*
					 * browseFTPMove browseFTPResize simple uploadSimple
					 * uploadResize
					 */
					// alert(tempArr);
						if (tempArr != null && (tempArr == 'uploadResize' || tempArr == 'uploadSimple' || tempArr == 'simple' || tempArr == 'browseFTPResize' || tempArr == 'browseFTPMove')) {
							msg = getLocalizedString('attendere_rimozione_in_corso','attendere rimozione in corso');
							$.blockUI( {
								message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1></div> ',
								css : {
									height : '100px',
									width : '275px'
								}
							});
							// alert("QUA "+inputValue);
							$.post("AjaxServlet", {
								flagTipologia : 'deleteAttachFile',
								physDoc : $.url.param("physDoc"),
								fileName : inputValue,
								uploadType : tempArr
							}, function(data, textStatus) {
								// alert(data);
									if (textStatus == 'success' && data.indexOf("succes") != -1) {
										removeElement(jelement);
										xDamsModalAlert(getLocalizedString('allegato_digitale_eliminato_correttamente','allegato digitale eliminato correttamente')+'<br /><br />');
										msg = getLocalizedString('allegato_digitale_eliminato_correttamente','allegato digitale eliminato correttamente')+'<br /><br />';
										$.blockUI( {
											message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1><input type="button" id="close" value=" [ chiudi ]" /></div> ',
											css : {
												height : '100px',
												width : '275px'
											}
										});
										$('#close').click(function() {
											$.unblockUI();
										});
										salvaRapido();
									} else {
										msg = data + "<br/>" +getLocalizedString('eliminare_ugualmente_la_sezione','eliminare ugualmente la sezione')+"?";
										$.blockUI( {
											message : '<div id="question" style="cursor: default"><h1>' + msg + '</h1><input type="button" id="yes" value=" [ si ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ no ]" /> </div> ',
											css : {
												height : '100px',
												width : '275px'
											}
										});
										$('#yes').click(function() {
											removeElement(jelement);
											salvaRapido();
											$.unblockUI();
										});
										$('#no').click(function() {
											$.unblockUI();
										});
									}
								}, 'text');
						}

					});
				$('#no').click(function() {
					$.unblockUI();
					return false;
				});
			}
		});
	} else {
		jelement.find("input,textarea,select").each(function() {
			$(this).val('');
		});
		xDamsAlert(getLocalizedString('Almeno_unistanza_deve_rimanere','Almeno un\'istanza deve rimanere')+"!");
	}
}
function salvaRapido() {
	toIso(document.theForm);
	submitForm(document.theForm, document.theForm.campiObbligatori.value, null, '_blank', 'doSaveAndClose.jsp', 'ServletEditing');
}

function removeElement(jelement) {
	var sibling = jelement.siblings()[0];
	if (sibling != null) {
		jelement.remove();
		arrange(sibling);
	} else {
		jelement.find("input,textarea,select").each(function() {
			$(this).val('');
		});
	}

}

function lastIndexOf(str, srch, startIdx) {
	var idx = -1;
	var startIndex = startIdx != undefined ? startIdx : str.length - 1;

	for (idx = startIndex; idx >= 0; idx--) {
		if (idx + srch.length <= str.length) {
			if (str.substr(idx, srch.length) === srch)
				break;
		}
	}

	return idx;
}

function trim(stringa) {
	while (stringa.substring(0, 1) == ' ' || stringa.substring(0, 1) == '\n') {
		stringa = stringa.substring(1, stringa.length);
	}
	while (stringa.substring(stringa.length - 1, stringa.length) == ' ' || stringa.substring(stringa.length - 1, stringa.length) == '\n') {
		stringa = stringa.substring(0, stringa.length - 1);
	}

	return stringa;
}

function fixBracketXPathIndex(str, newNumber) {
	var lastOpenBracket = lastIndexOf(str, "[");
	var lastCloseBracket = lastIndexOf(str, "]");

	
	//ADD 02/12/2014
	if(str.split("[").length > 1 ){
		result = str;
		    while(result.indexOf('[')!=-1){
				lastOpenBracket = result.indexOf("[");
				lastCloseBracket = result.indexOf("]");
				var toTest = result.substring(lastOpenBracket+1,lastCloseBracket);
				if(!isNaN(toTest)){
				    toTest = parseInt(toTest,10)+1;
				    break;
				}else{
					result = result.substring(0,lastOpenBracket)+'~'+result.substring(lastOpenBracket+1,lastCloseBracket)+'~'+result.substring(lastCloseBracket+1,result.length);
				}

			}
	}	
	
	
	if (lastOpenBracket == -1 || lastCloseBracket == -1)
		return str;

	var bracketsContent = str.substring(lastOpenBracket + 1, lastCloseBracket);

	var doNext = true;
	while (doNext && lastOpenBracket != -1) {
		doNext = !(/^\d+$/.test(bracketsContent));
		if (!doNext) {
			var num;
			if (newNumber !== undefined) {
				num = newNumber;
			} else {
				num = parseInt(bracketsContent, 10);
				num++;
			}

			num = String(num);

			while (bracketsContent.indexOf("0") === 0 && num.length < bracketsContent.length) {
				num = "0" + num;
			}

			str = str.substring(0, lastOpenBracket + 1) + num + str.substring(lastCloseBracket);
		} else {
			lastOpenBracket = lastIndexOf(str, "[", lastOpenBracket - 1);
			lastCloseBracket = lastIndexOf(str, "]", lastCloseBracket - 1);
			bracketsContent = str.substring(lastOpenBracket + 1, lastCloseBracket);
		}
	}

	return str;
}

function arrangeString(str, newNumber, regexp) {
	var myRe = regexp !== undefined ? regexp : /\[(\d+)\][^\[\]]*?'/g;
	var myResults = myRe.exec(str);

	var resultStr = "";
	var startIdx = 0;
	while (myResults !== null) {
		var bracketContentStartIdx = myResults.index + 1;
		var bracketContentEndIdx = myResults.index + 1 + String(myResults[1]).length;
		var bracketContent = str.substring(bracketContentStartIdx, bracketContentEndIdx);
		var newBracketContent = newNumber !== undefined ? String(newNumber) : String(parseInt(bracketContent) + 1);

		while (bracketContent.indexOf("0") === 0 && newBracketContent.length < bracketContent.length) {
			newBracketContent = "0" + newBracketContent;
		}

		resultStr += str.substring(startIdx, bracketContentStartIdx) + newBracketContent;
		startIdx = bracketContentEndIdx;

		myResults = myRe.exec(str);

		if (myResults === null) {
			resultStr += str.substring(startIdx);
		}
	}

	return resultStr != "" ? resultStr : str;
}

function fromName2Title() {
	$("input,textarea,select").each(function() {
		$(this).mouseover(function() {
			theName = $(this).attr("name");
			if (theName == null)
				theName = $(this).parents("[name]:first").attr("name");
			if (theName != null)
				$(this).attr("title", theName.replace(/\./g, '/'));
		})
		$(this).focus(function() {
			setCurrInput($(this).get(0))
		})

	})
}

function jclone(instance, insert, after) {
	var father;

	if (insert) {
		father = $(instance).parents("[id^='sezione_']:first");
	} else {
		father = $(instance).parents("span:eq(3)").nextAll("span[id^='sezione_']:first");
	}

	var lastChild = father.children(":last");
	var newNode = lastChild.clone(true);// .appendTo(id);

	newNode.find("*").each(function() {
		jelement = $(this);
		if (jelement.attr("id") != undefined) {
			var theId = jelement.attr("id");

			var nextId = theId;

			if (/\.\d+$/.test(theId)) {
				var n = parseInt(theId.substring(lastIndexOf(theId, ".") + 1));
				n++;
				nextId = theId.substring(0, lastIndexOf(theId, ".") + 1) + n;
			} else if (/\[\d+\]$/.test(theId)) {
				nextId = fixBracketXPathIndex(nextId);
			} else if (/\_text\d*$/.test(theId)) {
				var n = parseInt(theId.substring(lastIndexOf(theId, "_text") + "_text".length));
				n++;
				nextId = theId.substring(0, lastIndexOf(theId, "_text") + "_text".length + 1) + n;
			}

			if (theId != nextId)
				$(this).attr("id", nextId);
		}

		if (jelement.is("input,textarea,select")) {
			jelement.css( {
				backgroundColor : "#FFFFFF"
			});

			var theName = jelement.attr("name");
			theName = theName != undefined ? theName : "";
			var theTitle = jelement.attr("title");
			theTitle = theTitle != undefined ? theTitle : "";

			var newName = "";
			var newTitle = "";

			newName = fixBracketXPathIndex(theName);
			newTitle = fixBracketXPathIndex(theTitle);

			if (newName != theName)
				jelement.attr("name", newName);
			if (newTitle != theTitle)
				jelement.attr("title", newTitle);

			jelement.removeAttr("autocomplete");
			jelement.removeAttr("jqac");

			jelement.unbind("keydown");
			jelement.unbind("keypress");
			jelement.unbind("keyup");
			jelement.unbind("blur");
			if (jelement.attr('type') != 'radio') {
				jelement.val('');

			} else {
				jelement.get(0).checked = false;
			}
			// jelement.val('');
		}

		if (jelement.children().length == 0 && jelement.text() != undefined && trim(jelement.text()) != "") {
			var theText = jelement.text();
			theText = arrangeString(theText, undefined, /\((\d+)\)/g);

			jelement.text(theText);
		}

		if (jelement.attr("onclick") != undefined && false) {
			var theClick = String($(this).attr("onclick"));
			if (theClick.substr(0, "function".length) == "function") {
				theClick = trim(theClick.substring(theClick.indexOf("{") + 1, lastIndexOf(theClick, "}")));
			}

			theClick = arrangeString(theClick);
			// jelement.attr("onclick", String(theClick));
			jelement.click(function() {
				eval(theClick);
			});
		}
	});

	if (insert) {
		if (after) {
			newNode.insertAfter($(instance).parents("span:eq(2)"));
		} else {
			newNode.insertBefore($(instance).parents("span:eq(2)"));
		}

		arrange(newNode);
	} else {
		newNode.appendTo(father);
		arrange(newNode);
	}

	return newNode;
}


function jremoveSpread(instance,elementName,unRemove) {
	var jelement = $(instance).parents("span:eq(1)").parent(":first");
	if (jelement.siblings().length > 0) {
		var sibling = jelement.siblings()[0];
		$(jelement).find("input").each(function(i, v) {
		if (v.name != null && v.name != '') {
 			if(i==1){
				if(v.value!=""){
					if(spreadModSave('delete',elementName,v.value)){
						if(!unRemove){
						   jelement.remove();
						}else{
						jelement.find("input,textarea,select").each(function(i, v) {
						    $(this).val('');
						});						
						}
						doAfterSave = null;
						submitForm(document.theForm,document.theForm.campiObbligatori.value,null,'_blank','doSaveAndClose.jsp','ServletEditing');
					}
				}
			}
		 }
		})
		arrange(sibling);
	} else {
		jelement.find("input,textarea,select").each(function(i, v) {
			//alert(v.name+"="+v.value);
			if (v.name != null && v.name != '') {
				if(v.value!=""){
					if(spreadModSave('delete',elementName,v.value)){
						jelement.remove();
						doAfterSave = null;
						submitForm(document.theForm,document.theForm.campiObbligatori.value,null,'_blank','doSaveAndClose.jsp','ServletEditing');
					}
				}				
			}
			$(this).val('');
		});
		xDamsAlert(getLocalizedString('Almeno_unistanza_deve_rimanere','Almeno un\'istanza deve rimanere')+"!");
	}
}

function jremove(instance) {
	var jelement = $(instance).parents("span:eq(1)").parent(":first");
	if (jelement.siblings().length > 0) {
		var sibling = jelement.siblings()[0];
		jelement.remove();
		arrange(sibling);
	} else {
		jelement.find("input,textarea,select").each(function() {
			$(this).val('');
		});
		xDamsAlert(getLocalizedString('Almeno_unistanza_deve_rimanere','Almeno un\'istanza deve rimanere')+"!");
	}
}

function arrange(instance) {
	var curIndex = 1;
	// console.debug($(instance).parents("[@id^='sezione_']:first").attr('id'));
	$(instance).parents("[id^='sezione_']:first").find(">span").each(function() {
		$(this).find("*").each(function() {
			var jelement = $(this);
			if (jelement.attr("id") != undefined) {
				var theId = jelement.attr("id");

				var nextId = theId;

				if (/\.\d+$/.test(theId)) {
					nextId = theId.replace(/\.\d+$/, "." + curIndex);
				} else if (/\[\d+\]$/.test(theId)) {
					nextId = theId.replace(/\[\d+\]$/, "[" + curIndex + "]");
				} else if (/\_text\d*$/.test(theId)) {
					nextId = theId.substring(0, lastIndexOf(theId, "_text") + "_text".length + 1) + curIndex;
				}

				if (theId != nextId)
					$(this).attr("id", nextId);
			}

			if (jelement.is("input,textarea,select")) {
				var theName = jelement.attr("name");
				theName = theName != undefined ? theName : "";
				var theTitle = jelement.attr("title");
				theTitle = theTitle != undefined ? theTitle : "";

				var newName = "";
				var newTitle = "";

				// newName = theName.replace(/\[\d+\]([^\[]*$)/, "[" + curIndex
				// + "]$1");
				// newTitle = theTitle.replace(/\[\d+\]([^\[]*$)/, "[" +
				// curIndex + "]$1");

				newName = fixBracketXPathIndex(theName, curIndex);
				newTitle = fixBracketXPathIndex(theTitle, curIndex);

				if (newName != theName)
					jelement.attr("name", newName);
				if (newTitle != theTitle)
					jelement.attr("title", newTitle);
			}

			//if (jelement.children().length == 0 && jelement.text() != undefined && trim(jelement.text()) != "") {
			//modificato simone 07042011
			if (jelement.children().length == 0 && jelement.text() != undefined && trim(jelement.text()) != "" && !jelement.is("textarea")) {
				var theText = jelement.text();
				theText = arrangeString(theText, curIndex, /\((\d+)\)/g);
				jelement.text(theText);
			}

			if (jelement.attr("onclick") != undefined && false) {
				var theClick = $(this).attr("onclick");
				if (theClick.indexOf("function") == 0) {
					theClick = trim(theClick.substring(theClick.indexOf("{") + 1, lastIndexOf(theClick, "}")));
				}

				theClick = arrangeString(theClick, curIndex);

				// jelement.attr("onclick", String(theClick));
				jelement.click(function() {
					eval(theClick);
				});
			}
		});

		curIndex++;
	});
}