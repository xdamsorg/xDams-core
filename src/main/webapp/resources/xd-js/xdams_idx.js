var values, vocType;
function setVocParams(theValues, theVocType) {
	values = theValues;
	vocType = theVocType;
}

function tutte(totali) {
	document.successivoForm.vocabolario_maxresult.value = totali;
	document.successivoForm.vocabolario_start_param.value = " ";
	document.successivoForm.submit();
}

function toVoc(/* input */obj) {
	start_param = obj.value;
	if (start_param.indexOf(" ") != 0 && vocType == 'double') {
		start_param = " " + start_param;

	}
	document.vocabolarioForm['vocabolario_start_param'].value = start_param;
	obj.value = start_param;
	return true;
}
function toVocKeyCount(/* input */obj) {
	ilTest = true;
	keyCount = obj.value;
	if (keyCount == 99999) {
		if (confirm(getLocalizedString('Attenzione_la_richiesta_potrebbe_richiedere_qualche_minuto_confermare', 'Attenzione: la richiesta potrebbe richiedere qualche minuto, confermare')+"?")) {
		 
			start_param = '0';
			if (start_param.indexOf(" ") != 0 && vocType == 'double') {
				start_param = " " + start_param;
			}
			document.vocabolarioForm['vocabolario_start_param'].value = start_param;
			ilTest = true;
		}
	}
	if (ilTest) {
		document.vocabolarioForm['vocabolario_maxresult'].value = keyCount;
		document.vocabolarioForm.submit();
		return true;
	} else {
		return false;
	}
}
function setVocKeyCount(ilVal) {
	optionArr = document.getElementById('selectVis').getElementsByTagName('option');
	for (var i = 0; i < optionArr.length; i++) {
		if (optionArr[i].value == '99999') {
			optionArr[i].innerHTML = ilVal;
			// optionArr[i].value = ilVal;
		}
	}
}

function submitFormExport(id) {
	ilTest = true;
 
	if (confirm(getLocalizedString('Attenzione_la_richiesta_potrebbe_richiedere_qualche_minuto_confermare', 'Attenzione: la richiesta potrebbe richiedere qualche minuto, confermare')+"?")) {
		start_param = '0';
		if (start_param.indexOf(" ") != 0 && vocType == 'double') {
			start_param = " " + start_param;
		}
		document.vocabolarioForm['vocabolario_start_param'].value = start_param;
		ilTest = true;
 		 document.vocabolarioForm.target = '_new'; 
		document.vocabolarioForm['isExport'].value = 'true';
		document.vocabolarioForm['vocabolario_maxresult'].value = 99999;
		document.vocabolarioForm.submit();
		self.close();
	} else {
 
	} 
}

function submitForm(id) {
	var tocks = new Array;
	if (values != '') {
		tocks = values.split('~');
	}
	toFill = '';
	var apex = '"';
	if (vocType == 'number')
		apex = '';
	for (var i = 0; i < document.fillForm.elements.length; i++) {
		if (document.fillForm.elements[i].type == 'checkbox') {
			if (document.fillForm.elements[i].checked == true) {
				appoCk = document.fillForm.elements[i].value;
				// alert(appoCk);
				if (i == 0 || toFill == '') {
					insert = true;
					if (values != '') {
						for (var x = 0; x < tocks.length; x++) {

							if (appoCk == tocks[x]) {
								insert = false;
								break;
							}
						}
					}
					if (insert == true)
						toFill = toFill + apex + document.fillForm.elements[i].value + apex;
				} else {
					insert = true;
					if (values != '') {
						for (var x = 0; x < tocks.length; x++) {
							if (appoCk == tocks[x]) {
								insert = false;
								break;
							}
						}
					}
					if (insert == true)
						toFill = toFill + ' OR ' + apex + document.fillForm.elements[i].value + apex;
				}
			}
		}

	}

	if (values != '') {
		for (var x = 0; x < tocks.length; x++) {
			if (toFill == '') {
				if (tocks[x] != '')
					toFill = toFill + apex + tocks[x] + apex;
			} else {
				if (tocks[x] != '')
					toFill = toFill + ' or ' + apex + tocks[x] + apex;
			}
		}
	}
	window.opener.document.getElementById(id).value = toFill;
	window.close();
}

function fillCheckBox(values) {
 
	if (values != '') {
		var tocks = new Array;
		tocks = values.split('~');
		for (var i = 0; i < document.fillForm.elements.length; i++) {
			if (document.fillForm.elements[i].type == 'checkbox') {
				appoCk = document.fillForm.elements[i].value;
				for (var x = 0; x < tocks.length; x++) {

					if (appoCk == tocks[x]) {
						document.fillForm.elements[i].checked = true;
						break;
					}

				}
			}
		}
		for (var i = 0; i < document.precedenteForm.elements.length; i++) {
			if (document.precedenteForm.elements[i].type == 'checkbox') {
				if (document.precedenteForm.elements[i].name.indexOf('remove_') == -1) {
					appoCk = document.precedenteForm.elements[i].value;
					for (var x = 0; x < tocks.length; x++) {

						if (appoCk == tocks[x]) {
							document.precedenteForm.elements[i].checked = true;
							break;
						}

					}
				}
			}
		}
		for (var i = 0; i < document.successivoForm.elements.length; i++) {
			if (document.successivoForm.elements[i].type == 'checkbox') {
				if (document.successivoForm.elements[i].name.indexOf('remove_') == -1) {
					appoCk = document.successivoForm.elements[i].value;
					for (var x = 0; x < tocks.length; x++) {
						if (appoCk == tocks[x]) {
							document.successivoForm.elements[i].checked = true;
							break;
						}
					}
				}
			}
		}
		for (var i = 0; i < document.vocabolarioForm.elements.length; i++) {
			if (document.vocabolarioForm.elements[i].type == 'checkbox') {
				if (document.vocabolarioForm.elements[i].name.indexOf('remove_') == -1) {
					appoCk = document.vocabolarioForm.elements[i].value;
					for (var x = 0; x < tocks.length; x++) {
						if (appoCk == tocks[x]) {
							document.vocabolarioForm.elements[i].checked = true;
							break;
						}
					}
				}
			}
		}
	}
}
function setChecked(ck, nome) {
 	if (ck.checked == false) {
		document.getElementById('remove_' + nome + 'p').checked = true;
		document.getElementById('remove_' + nome + 's').checked = true;
		document.getElementById('remove_' + nome + 'd').checked = true;
	} else {
		document.getElementById('remove_' + nome + 'p').checked = false;
		document.getElementById('remove_' + nome + 's').checked = false;
		document.getElementById('remove_' + nome + 'd').checked = false;
	}
	document.getElementById(nome + 'p').checked = ck.checked;
	document.getElementById(nome + 's').checked = ck.checked;
	document.getElementById(nome + 'd').checked = ck.checked;
}

function trim(wstr) {
	if (wstr == null)
		return null;
	if (wstr == "")
		return "";
	var vuoto = true;
	wstr += " ";
	for ( var i = wstr.length - 1; i + 1; i--)
		if (wstr.substring(i, i + 1) != " ") {
			vuoto = false;
			break;
		}
	if (vuoto)
		return "";
	else
		return wstr.substring(0, i + 1);
}

function submitVocabolario() {
	if (trim(document.vocabolarioForm.vocabolario_start_param.value) != '') {
		if (document.vocabolarioForm.vocabolario_tipology.value == "double") {
			document.vocabolarioForm.vocabolario_start_param.value = " " + trim(document.vocabolarioForm.vocabolario_start_param.value);
		}
		document.vocabolarioForm.submit();
	} else {
		alert('inserire il termine di ricerca');
	}
}
function successivo(start_param) {
	document.successivoForm.vocabolario_start_param.value = start_param;
	document.successivoForm.submit();
}

function precedente(start_param) {
	document.precedenteForm.vocabolario_start_param.value = start_param;
	document.precedenteForm.submit();
}
