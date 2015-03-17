function stampaRamo(numDoc,theArch,accountPath,theHost,mode,theGestWebApp){
	alert(getLocalizedString('Questa_funzionalita_permette_di_creare_una_stampa_in_formato_PDF_del_documento_o_di_tutta_la_sua_gerarchia','Questa funzionalità permette di creare una stampa in formato PDF del documento o di tutta la sua gerarchia')+'.');
}
/*function stampaRamo(numDoc,theArch,accountPath,theHost,mode,theGestWebApp){
if(theGestWebApp == null || theGestWebApp == '')
	theGestWebApp = 'RegestaDAMS_JMSProducer';
	percorso = '/'+theGestWebApp+'/gestione/stampaRamo.jsp?theDb='+theArch+'&docNumber='+numDoc+'&accountPath='+accountPath+'&theHost='+theHost+'&mode='+mode

	if(parent.document.esitoForm != null)
		if(parent.document.esitoForm.selid.value != '')
			percorso += '&selid='+parent.document.esitoForm.selid.value

	laStampa=window.open(percorso,'stampa','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=250,height=250')
   	laStampa.focus()
   	return false;
}*/
function PDFPrinter(theArch,theWebApp,level,numDoc_phrase,mail){
	alert(getLocalizedString('Questa_funzionalita_permette_di_creare_una_stampa_in_formato_PDF_del_documento_o_di_tutta_la_sua_gerarchia','Questa funzionalità permette di creare una stampa in formato PDF del documento o di tutta la sua gerarchia')+'.');
}
/*function PDFPrinter(theArch,theWebApp,level,numDoc_phrase,mail){

	if(parent.document.esitoForm != null){
		if(parent.document.esitoForm.selid.value != ''){
			percorso = '/'+theWebApp+'/print/printer.jsp?PDFXMLArchive=xw/'+theArch+'&userLevel='+level+'&PDFPhrase='+parent.document.esitoForm.selid.value+'&PDFMail='+mail+'&theWebApp='+theWebApp;
		}else{
		     percorso = '/'+theWebApp+'/print/printer.jsp?PDFXMLArchive=xw/'+theArch+'&userLevel='+level+'&PDFNdoc='+numDoc_phrase+'&PDFMail='+mail+'&theWebApp='+theWebApp;
		}
        }else{
             percorso = '/'+theWebApp+'/print/printer.jsp?PDFXMLArchive=xw/'+theArch+'&userLevel='+level+'&PDFNdoc='+numDoc_phrase+'&PDFMail='+mail+'&theWebApp='+theWebApp;
        }


        laStampa=window.open(percorso,'stampa','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=400,height=400')
   	laStampa.focus()

   	return false;
}*/


function importaZip(numDoc,theArch,userPermis,accountPath){
	alert(getLocalizedString('Questa_funzionalita_permette_di_importare_nellarchivio_correnten_i_documenti_xml_creati_con_il_modulo_xDamsoffline','Questa funzionalità permette di importare nell\'archivio corrente\n i documenti xml creati con il modulo xDams-offline'));
}
/*function importaZip(numDoc,theArch,userPermis,accountPath){
	percorso = 'ZipUpload.jsp?theArch='+theArch+'&physDoc='+numDoc+'&accountPath='+accountPath+'&userPermis='+userPermis
	uploadModule=window.open(percorso,'stampa','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=350,height=250')
   	uploadModule.focus()
   	return false;
}
function evidenziaDocumento(){
	if(parent.leftArea.document != null && parent.leftArea.pageLoaded){
		parent.leftArea.evidenziaDocumentoInList()
	}
	else
		setTimeout("evidenziaDocumentoInList()",100);
}*/


function evidenziaDocumentoInList(){
	if(parent.schedaBreve.document.theForm != null)
	{
		documento = parent.schedaBreve.document.theForm.physDoc.value
		obj = document.getElementById('anchor'+documento)
		if(obj != null)
			settaDocumento(obj)
	}
	return true
}


function svuotaAppunti(){
	top.document.gestione['numDocTaglia'].value='';
	top.document.gestione['descrizioDocTaglia'].value='';
	top.document.gestione['numDocElementoPadre'].value='';
	scriviAppunti()

	if(document.getElementById('incollaFiglio')!=null)
		document.getElementById('incollaFiglio').style.display = 'none';
	if(document.getElementById('incollaDopo')!=null)
		document.getElementById('incollaDopo').style.display = 'none';
	if(document.getElementById('incollaPrima')!=null)
		document.getElementById('incollaPrima').style.display = 'none';
	if(document.getElementById('tagliaElemento')!=null)
		document.getElementById('tagliaElemento').style.display = '';
	if(document.getElementById('copiaElemento')!=null)
		document.getElementById('copiaElemento').style.display = '';
    return false;
}


function scriviAppunti(){
	var appunti = top.document.gestione['descrizioDocTaglia'].value
	var test = top.document.gestione['numDocTaglia'].value

	if(test == '' || test == 'null')
		appunti = ''

	if(appunti == 'null')
		appunti = ''

	if(appunti != ''){
		document.getElementById('areaAppunti').innerHTML = '<div class="areaAppunti"><strong>elemento contenuto negli appunti:<a href="javascript:void(0)" onclick="return svuotaAppunti()" title="svuota appunti"><img align="right" border="0" src="/dams_common/img/cestino.gif"/></a><br></strong><span  style="padding:10px"> '+appunti+' </span></div>'
			if(document.getElementById('incollaFiglio')!=null)
				document.getElementById('incollaFiglio').style.display = '';
			if(document.getElementById('incollaPrima')!=null)
				document.getElementById('incollaPrima').style.display = '';
			if(document.getElementById('incollaDopo')!=null)
				document.getElementById('incollaDopo').style.display = '';
			if(document.getElementById('tagliaElemento')!=null)
				document.getElementById('tagliaElemento').style.display = 'none'
			if(document.getElementById('copiaElemento')!=null)
				document.getElementById('copiaElemento').style.display = 'none'
	}
	else
		document.getElementById('areaAppunti').innerHTML = '';
	return false;
}


function unisci(phydoc,theDb,theWebApp,theElement,theRule,theGestWebApp){
if(theGestWebApp == null || theGestWebApp == '')
	theGestWebApp = 'RegestaDAMS_JMSProducer';

	theWebApp = theWebApp.substring(0,(theWebApp.substring(1)).indexOf('/')+1)

	elementi = top.document.gestione.documentiTagliati.value

	pagina = window.open('/'+theGestWebApp+'/gestione/gestioneUnisci.jsp?theRule='+theRule+'&theDb='+theDb+'&theElements='+elementi+'&thePhydoc='+phydoc+'&theWebApp='+theWebApp,'riordina','toolbar=no,location=no,status=yes,scrollbars=yes,width=350,height=400');
	pagina.focus()

	return false;
}


function modificaMulti(phydoc,theDb,theWebApp,theElement,ilCodice,accountPath,theEmail,theGestWebApp){
if(theGestWebApp == null || theGestWebApp == '')
	theGestWebApp = 'RegestaDAMS_JMSProducer';

	theWebApp = theWebApp.substring(0,(theWebApp.substring(1)).indexOf('/')+1)

	window.open('/'+theGestWebApp+'/gestione/gestioneModificaMulti.jsp?theDb='+theDb+'&thePhydoc='+phydoc+'&theEmail='+theEmail+'&accountPath='+accountPath+'&theElement='+theElement+'&unitId='+ilCodice,'riordina','toolbar=no,location=no,status=yes,scrollbars=yes,width=350,height=400');
	//riordina.focus()
   	return false
}


function riordina(phydoc,theDb,theWebApp,theElement,accountPath,theGestWebApp){
if(theGestWebApp == null || theGestWebApp == '')
	theGestWebApp = 'RegestaDAMS_JMSProducer';

	theWebApp = theWebApp.substring(0,(theWebApp.substring(1)).indexOf('/')+1)
	window.open('gestione/gestioneRiordina.jsp?theDb='+theDb+'&thePhydoc='+phydoc+'&theWebApp='+theWebApp+'&theElement='+theElement+'&accountPath='+accountPath,'riordina','toolbar=no,location=no,status=yes,scrollbars=yes,resizable=yes,width=400,height=400');
	//riordina.focus()
   	return false
}


function visXML(docNumber,theArch){
	window.open('xml.jsp?theDb='+theArch+'&docNumber='+docNumber,'visXML','toolbar=yes,location=no,status=yes,scrollbars=yes,resizable=yes,width=400,height=400');
	return false;
}

function incolla(doveIncolla,flag,theDb,accountPath,theGestWebApp){
if(theGestWebApp == null || theGestWebApp == '')
	theGestWebApp = 'RegestaDAMS_JMSProducer';
	percorso = "";
	if(top.document.gestione['azione'].value == 'duplica'){
		if(!confirm(getLocalizedString('Duplicare_la_scheda_contenuta_negli_appunti','Duplicare la scheda contenuta negli appunti')+'?'))
			return false
		percorso = 'gestioneDuplica.jsp?';
		}
	else
		percorso = 'gestioneIncolla.jsp?';

	top.document.gestione['azione'].value=flag;
	top.document.gestione['numDocIncolla'].value=doveIncolla;
	tagliato = top.document.gestione['numDocTaglia'].value

	tagliaIncolla = window.open('gestione/'+percorso+'theDb='+theDb+'&flag='+flag+'&accountPath='+accountPath+'&tagliato='+tagliato+'&incolla='+doveIncolla,'tagliaIncolla','toolbar=no,location=no,status=yes,scrollbars=yes,width=10,height=10');
   return false;
}

function taglia(phydoc,descrizio,docFather,copia){

	top.document.gestione['numDocTaglia'].value=phydoc;
	top.document.gestione['descrizioDocTaglia'].value=descrizio;
	top.document.gestione['numDocElementoPadre'].value=docFather;
	if(copia)
		top.document.gestione['azione'].value='duplica';
	else
		top.document.gestione['azione'].value='';

	scriviAppunti()
   	return false;
}

function tagliaMultiplo(action,theElement,theRule,theWebApp,theGestWebApp){

	varInput = document.getElementsByTagName('input')


//alert('2 -->' + top.gestione.document.gestione.flagGestione.name);


	if(top.document.gestione.documentiTagliati != null)
	{

				if(action == 'start-stop')
				{
					if(top.document.gestione.flagGestione.value != '1')
					{
						//tagliaMultiplo('svuota')
						tagliaMultiplo('show')
					}
					else
					{
						tagliaMultiplo('show')
						tagliaMultiplo('load')
					}
				}

				if(action == 'switch')
				{
					if(top.document.gestione.flagGestione.value == '1')
					{
						top.document.gestione.flagGestione.value = '0'
						tagliaMultiplo('show')
						tagliaMultiplo('svuota')
					}
					else{
						top.document.gestione.flagGestione.value = '1'
						tagliaMultiplo('show')
					}
				}

				if(action == 'show')
				{


					if(top.document.gestione.flagGestione.value == '1')
					{
						for(var i=0;i<varInput.length;i++)
						{
							if(varInput[i].name.indexOf('gestioneInput') == 0)
							{
								varInput[i].style.display = ''

							}
						}

						tagliaMultiplo('load')
					}
					else
					{
						for(var i=0;i<varInput.length;i++)
						{
							if(varInput[i].name.indexOf('gestioneInput') == 0)
							{
								varInput[i].style.display = 'none'

							}
						}
					}
				}


				var elementi = top.document.gestione.documentiTagliati.value
				var elementiArray = elementi.split("~")


				if(action == 'load')
				{
					for(var i=0;i<elementiArray.length;i++)
					{

						// GESTIRE !!!
						elementoTest = ""
						eval('elementoTest = \'gestioneInput'+elementiArray[i]+'\'')
						//alert(elementoTest)
					 /*	if(document.gerarchiaForm[elementoTest] != null)
							document.gerarchiaForm[elementoTest].checked = true
							*/

					}
				}

				if(action == 'add')
				{
					elemento = theElement.value + "~"
					elementoTest = "~" + theElement.value + "~"

					if(theElement.checked == true)
					{
						if(elementi.indexOf(elementoTest)==-1)
						{
							elementi += elemento
						}
					}
					else
					{
						if(elementi.indexOf(elementoTest)!=-1)
						{
							elementi = elementi.replace(elementoTest,"~")
						}
					}

					top.document.gestione.documentiTagliati.value = elementi
				}

				if(action == 'svuota')
				{
					if(confirm(getLocalizedString('Svuotare_gli_appunti','Svuotare gli appunti')+'?'))
					{
						top.document.gestione.documentiTagliati.value = '~'
						top.document.gestione.documentoPadre.value = ''

						for(var i=0;i<varInput.length;i++)
						{

							if(varInput[i].checked)
								varInput[i].checked = false
						}

					}
				}

				if(action == 'incolla')
				{
					//alert(theElement)
					if(theElement.value != null)
						elemento = theElement.value
					else
						elemento = theElement

					if(top.document.gestione.documentiTagliati.value != '~')
					{
						percorso = "gestione/gestioneIncollaMultiplo.jsp?thePage=gestioneIncollaMultiplo&thElements="+elementi+"&theParent="+elemento+"&theDb="+document.gerarchiaForm.db.value+"&theRule="+theRule+"&theWebApp="+theWebApp

						popup = window.open(percorso,'gestionePop','width=620,height=520')
					}
				}

	}

	//alert(parent.frames.gestione.gestione.documentiTagliati.value)
	return true
}

