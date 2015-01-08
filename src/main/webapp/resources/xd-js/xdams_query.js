/* controllato da Diego il 30/10/2007 */
function cerca(frase,theForm){
	theForm.qlphrase.value=frase;
	theForm.submit();
}
 
function cambiaCriterio(criterio,theForm){
	for(i=0;i<theForm.length;i++){
		if(theForm.elements[i].name == 'sorting'){
			if(criterio == 'XML'){
				while(theForm.elements[i].value.indexOf("xml")>-1){
					theForm.elements[i].value = theForm.elements[i].value.replace('xml',criterio)
				}
				while(theForm.elements[i].value.indexOf("ud")>-1){
					theForm.elements[i].value = theForm.elements[i].value.replace('ud','UD')
				}				
			}
			if(criterio == 'xml')
				while(theForm.elements[i].value.indexOf("XML")>-1){
					theForm.elements[i].value = theForm.elements[i].value.replace('XML',criterio)
				}
				while(theForm.elements[i].value.indexOf("UD")>-1){
					theForm.elements[i].value = theForm.elements[i].value.replace('UD','ud')

				}										
			//alert(theForm.elements[i].value)
		}
	}
return true;
}

function query(theForm,msg){
	ritorno = false;
	ritornoElemento = false;
	elementoQlPhrase = "";
	for(var i=0;i<document.theForm.length;i++){
		if(document.theForm.elements[i].name.indexOf('[') == 0 && document.theForm.elements[i].value !=''){
			ritornoElemento = true;
			if(document.theForm.elements[i].value.indexOf("xquery:")==0){
				document.theForm.qlphrase.value = document.theForm.elements[i].value.substring(7);
				document.theForm.elements[i].value = '';
				}
		}else{
			if(document.theForm.elements[i].name == 'anno_ini'){
				ritorno = findDate(theForm,'collect',theForm['dataField'],'');
			}
		}
	}
	if(ritorno || ritornoElemento || document.theForm.qlphrase.value != ''){
		document.theForm.submit();
	}
	else
		alert(msg);
	return true;
}
function findIt(thInput){
	laFrase = thInput.value;
	//document.theForm.reset();
	document.theForm.qlphrase.value = laFrase;
	//document.theForm.submit();
	return true;
}
function findItErase(theArch){
	window.open("eraseDbSession.jsp?db="+theArch,"gestioneErase","toolbar=0,status=0,width=10,height=10,scrollbars=no,resizable=no,status=no");
	return true;
}
function apriIdx(objVoc, maxresult, theArch, varAlias, varTipology, filterdList, startParam,num_docsParam){
 	alias=varAlias;
	typology=varTipology;
	start_param=objVoc.value;
	return_id=objVoc.id;
	num_docs = ""; 
	phrase = document.getElementById('queryBeanSelect').options[document.getElementById('queryBeanSelect').selectedIndex].value;
	//alert(phrase);
	if(filterdList!=null && document.theForm[filterdList]!=null && document.theForm[filterdList].value!=""){
		if(phrase!=""){
		 phrase = "("+phrase+") AND (" + filterdList+"="+document.theForm[filterdList].value + ") ";
		}else{
			phrase = "(" + filterdList+"="+document.theForm[filterdList].value + ") ";
		}
	}else if(filterdList!='' && document.theForm[filterdList]!=null && document.theForm[filterdList].value!=""){
          //AGGIUNTO SANDRO PER NUOVO VOCABOLOARIO se inserisci dentro filteredKey="[XML,/crono_eventi/attribuzione_resp/persname/@role]=&quot;Baritono&quot;" la frase di ricerca
	  phrase=filterdList;
	}else if(filterdList!=''){
          //AGGIUNTO SANDRO PER NUOVO VOCABOLOARIO se inserisci dentro filteredKey="[XML,/crono_eventi/attribuzione_resp/persname/@role]=&quot;Baritono&quot;" la frase di ricerca
	  phrase=filterdList;
	}
	appendice = "";
	if(theArch != null)
		appendice = "&theArch="+theArch;
	
	if(objVoc.start_param != null){
		if(typology == 'double'){
				start_param = " "+start_param;
		}
		else{
			if(start_param == '')
				start_param = startParam;
		}
	}else{
		if(start_param==''){
			start_param = startParam;
		}
 	}

	if(start_param.indexOf('"') == 0)
		start_param = start_param.substring(1);
	up_down='up';

	if(phrase == null)
		phrase = "";
	if(typology == null)
		typology = "multi";
	
	
	if(start_param.indexOf(" ")!=0 && typology == 'double'){
		start_param = " "+start_param;
	}
	
	if(num_docsParam!=null && num_docsParam!=''){
	  num_docs=num_docsParam;
	}
	//alert("VocabolarioServlet?urlAfterSave=/public/application/jsp/vocabolario.jsp&urlErrorPage=/public/application/jsp/vocabolario.jsp&vocabolario_alias="+alias+"&vocabolario_maxresult="+maxresult+"&vocabolario_up_down="+up_down+"&vocabolario_start_param="+start_param+"&vocabolario_return_id="+return_id+"&vocabolario_tipology="+typology+"&vocabolario_phrase="+phrase+appendice+"&num_docs="+num_docs);
	//window.open("VocabolarioServlet?urlAfterSave=/public/application/jsp/vocabolario.jsp&urlErrorPage=/public/application/jsp/vocabolario.jsp&vocabolario_alias="+alias+"&vocabolario_maxresult="+maxresult+"&vocabolario_up_down="+up_down+"&vocabolario_start_param="+start_param+"&vocabolario_return_id="+return_id+"&vocabolario_tipology="+typology+"&vocabolario_phrase="+phrase+appendice+"&num_docs="+num_docs,"vocabolario","toolbar=0,status=0,width=600,height=480,scrollbars=no,resizable=yes");
	window.open("./vocabulary.html?vocabolario_alias="+alias+"&vocabolario_maxresult="+maxresult+"&vocabolario_up_down="+up_down+"&vocabolario_start_param="+start_param+"&vocabolario_return_id="+return_id+"&vocabolario_tipology="+typology+"&vocabolario_phrase="+phrase+appendice+"&num_docs="+num_docs,"vocabolario","toolbar=0,status=0,width=600,height=480,scrollbars=no,resizable=yes");
	return false;
}
function findDate(theForm,action,elemento,tipologia){
	if(action == 'normalize' && elemento.value != ''){
			valore = elemento.value;
			valoreInt = parseInt(valore,10);
			if(tipologia == 'day'){
				if(valoreInt > 31 || valoreInt < 1){
					alert(getLocalizedString('Attenzione_data_non_valida','Attenzione: data non valida')+'!');
					elemento.value = "";
					elemento.focus();
				}else{
					if(valore.length == 1)
						valore = '0'+valore
					elemento.value = valore;					
				}
			}else{
				if(tipologia == 'month'){
					if(valoreInt > 12 || valoreInt < 1){
						alert(getLocalizedString('Attenzione_data_non_valida','Attenzione: data non valida')+'!');
						elemento.value = "";
						elemento.focus();
					}else{
					if(valore.length == 1)
						valore = '0'+valore
					elemento.value = valore;						
					}		
				}
				else{
					if(valore.length < 4){
						alert(getLocalizedString('Attenzione_data_non_valida','Attenzione: data non valida')+'!');
						elemento.value = "";
						elemento.focus();
					}					
				}
			}
		return false			
	}else{

		if(action == 'collect'){
			if(theForm['anno_ini'].value != ''){
				dataEsatta = false;
				for(i=0;i<theForm.length;i++){
					if(theForm.elements[i].name == 'dataEsatta'){
						dataEsatta = theForm.elements[i].checked;
					}
				}		 
				anno_ini = theForm['anno_ini'].value;
				anno_fin = theForm['anno_fin'].value;
				mese_ini = theForm['mese_ini'].value;
				mese_fin = theForm['mese_fin'].value;			
				giorno_ini = theForm['giorno_ini'].value;
				giorno_fin = theForm['giorno_fin'].value;			
				if(dataEsatta){
					if(mese_ini == '')
						mese_ini = '??';
					if(giorno_ini == '')
						giorno_ini = '??';	
					
					if(anno_fin != ''){									
						if(giorno_fin == '')
							giorno_fin = '??';				
						if(mese_fin == '')
							mese_fin = '??';	
					}else{
						anno_fin = '';	
						mese_fin = '';	
						giorno_fin = '';											
					}
				}else{
					if(mese_ini == '')
						mese_ini = '01';
					if(giorno_ini == '')
						giorno_ini = '01';				
					if(anno_fin != ''){
						if(giorno_fin == '')
							giorno_fin = '31';				
						if(mese_fin == '')
							mese_fin = '12';	
					}else{
					anno_fin = '';	
					mese_fin = '';	
					giorno_fin = '';											
					}
				}
				searchString = anno_ini+mese_ini+giorno_ini;
				if(!dataEsatta){
					searchString += "|"			
				}
				if(anno_fin != '')
					if(dataEsatta)
						searchString += "-"+anno_fin+mese_fin+giorno_fin;
					else
						searchString += parseInt(anno_fin+mese_fin+giorno_fin)+1;
				else{
					if(dataEsatta)
						searchString += ""; //searchString += anno_fin+mese_fin+giorno_fin;
					else
						searchString += "";
				}
				
				if(dataEsatta)
					theForm[elemento.value].value = ""+searchString+"";
				else
					theForm[elemento.value].value = "{"+searchString+"}";
				//alert(searchString)
			return true;
			}
		
			else 
				return false;
			}


	}

}

function oraNormal(campoIni,campoFine,campoQuery) {
 	//campoImpValue = campoImp.value;
// 	  alert("campoIni "+campoIni);
// 	  alert("campoFine "+campoFine);
// 	  alert("campoQuery "+campoQuery);
	campoINIValue = campoIni.value;
	campoFINValue = campoFine.value;
	if(campoINIValue==""){
		alert(getLocalizedString('Inserire_ora_iniziale','Inserire ora iniziale'));
		return;
	}
	if(campoINIValue.length < 2){
		campoINIValue = "0"+campoINIValue;
	}
	if(campoFINValue!="" && campoFINValue.length < 2){
		campoFINValue = "0"+campoFINValue;
	}	
	oraIniziale=campoINIValue+"00";
	if(campoFINValue==""){
		oraFinale=campoINIValue+"00";
	}else{
		oraFinale=campoFINValue+"00";
	}

	campoQuery.value = "{"+oraIniziale +"|"+ oraFinale+"}";
}

