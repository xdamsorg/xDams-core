/*function inserisciNew(physDoc,level,unittitle,theDb){
	relation=4;
	laLingua = "";
	if(top.data.document.data.lingua!=null)
		laLingua = top.data.document.data.lingua.value
	percorso = "preInsert.jsp?physDoc="+physDoc+"&relation="+relation+"&level="+level+"&unittitle="+unittitle+"&theDb="+theDb+'&userPermis='+top.data.document.hbfeeder.userPermis.value+'&language='+laLingua
	laScheda=window.open(percorso,'schedaXdams','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}*/
function inserisciNew(physDoc,level){
	relation=4;
	percorso = "preInsert.jsp?physDoc="+physDoc+"&relation="+relation+"&level="+level;
	laScheda=window.open(percorso,'schedaXdams','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}
/*function inserisci(physDoc,level,unittitle,theDb){
	relation=4;
	laLingua = "";
	if(window.opener.top.data.document.data.lingua!=null)
		laLingua = window.opener.top.data.document.data.lingua.value	
	percorso = "preInsert.jsp?physDoc="+physDoc+"&relation="+relation+"&level="+level+"&unittitle="+unittitle+"&theDb="+theDb+'&userPermis='+window.opener.top.data.hbfeeder.userPermis.value+'&language='+laLingua
	laScheda=window.open(percorso,'schedaXdams','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}*/
function inserisci(physDoc,level,unittitle,theDb){
	relation=4;
	percorso = "preInsert.jsp?physDoc="+physDoc+"&relation="+relation+"&level="+level+"&unittitle="+unittitle;
	laScheda=window.open(percorso,'schedaXdams','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}
function inserisciNewAuth(theDb,theDbDesc){
		theType = "";
		// TODO generalizzare
		if(theDb.indexOf('Nomi')>0 || theDb.indexOf('Persone')>0)
			theType = 'person'
		if(theDb.indexOf('Luoghi')>0 || theDb.indexOf('Geo')>0 || theDb.indexOf('Topo')>0)
			theType = 'place'
		if(theDb.indexOf('Impianti')>0)
			theType = 'plant'
		if(theDb.indexOf('Enti')>0)
			theType = 'corporatebody'
		if(theDb.indexOf('Famiglie')>0)
			theType = 'famname'
		if(theDb.indexOf('Soggetti')>0 || theDb.indexOf('Temi')>0 || theDb.indexOf('Quilici')>0)
			theType = 'topic'



	percorso = 'preInsert.jsp?theAction=new&theDb='+theDb+'&theType='+theType+'&theDbDesc='+theDbDesc+'&userPermis='+top.data.document.hbfeeder.userPermis.value
	laScheda=window.open(percorso,'schedaXdams','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}
/*function apriPopDocumento(numDoc, theArch, selId, pos){
	laLingua ="";
	if(top.data.document.data.lingua!=null)
		laLingua = top.data.document.data.lingua.value
  percorso = 'scheda.jsp?theDb='+theArch+'&docNumber='+numDoc+'&pos='+pos+'&selId='+selId+'&userPermis='+top.data.document.hbfeeder.userPermis.value+'&language='+laLingua
  laScheda=window.open(percorso,'schedaXdams','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=760,height=540')
  laScheda.focus()
  return false;
}*/
function apriPopDocumento(numDoc, theArch, selId, pos){
 percorso = 'scheda.jsp?theDb='+theArch+'&docNumber='+numDoc+'&pos='+pos+'&selId='+selId
  laScheda=window.open(percorso,'schedaXdams','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=760,height=540')
  laScheda.focus()
  return false;
}

function findFromAuth(theCode,theDb,codiceSup,eleSup){
	parent.location = 'accessoFromAuth.jsp?codeToFind='+theCode+'&amp;db='+theDb+'&amp;codiceSup='+codiceSup+'&amp;eleSup='+eleSup;
	return false
}
/*function apriPopModifica(numDoc, theArch){
	laLingua ="";
	if(top.data.document.data.lingua!=null)
		laLingua = top.data.document.data.lingua.value
  percorso = 'docEdit.jsp?theDb='+theArch+'&physDoc='+numDoc+'&userPermis='+top.data.document.hbfeeder.userPermis.value+'&language='+laLingua;
  laScheda=window.open(percorso,'schedaXdams','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=760,height=540')
  laScheda.focus()
  return false;
}*/

function apriPopModifica(numDoc, theArch){
  percorso = 'docEdit.jsp?theDb='+theArch+'&physDoc='+numDoc;
  laScheda=window.open(percorso,'schedaXdams','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=760,height=540')
  laScheda.focus()
  return false;
}



/*function apriModifica(numDoc, theArch){
	laLingua ="";
	if(window.opener.top.data.document.data.lingua!=null)
		laLingua = window.opener.top.data.document.data.lingua.value
  percorso = 'docEdit.jsp?theDb='+theArch+'&physDoc='+numDoc+'&userPermis='+window.opener.top.data.hbfeeder.userPermis.value+'&language='+laLingua
  self.location = percorso
  self.focus()
  return false;
}*/

function apriModifica(numDoc, theArch){
  percorso = 'docEdit.jsp?theDb='+theArch+'&physDoc='+numDoc
  self.location = percorso
  self.focus()
  return false;
}

function cancellaDocumento(numDoc,theArch, selId, msg, userpermis){
 if(confirm(msg)){
   percorso = 'eliminaScheda.jsp?db='+theArch+'&docNumber='+numDoc+'&selId='+selId+'&userPermis='+userpermis
   laScheda=window.open(percorso,'elimina','toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=10,height=10')
   laScheda.focus()
  }
   return false;
}
function inserisciDoc(ilDocumento){
   percorso =  'preinsert.jsp?theDoc='+ilDocumento+'&theDb=<%=theArch%>&';
   self.location=percorso
   return false;
}
/*function apriDocumento(numDoc, treePos, theArch, selId, pos){
	laLingua ="";
	if(window.opener.top.data.document.data.lingua!=null)
		laLingua = window.opener.top.data.document.data.lingua.value
   percorso = 'scheda.jsp?theDb='+theArch+'&docNumber='+numDoc+'&pos='+pos+'&selId='+selId+'&userPermis='+window.opener.top.data.document.hbfeeder.userPermis.value+'&language='+laLingua
   document.location = percorso;
   return false;
}*/
function apriDocumento(numDoc, treePos, theArch, selId, pos){
   percorso = 'scheda.jsp?theDb='+theArch+'&docNumber='+numDoc+'&pos='+pos+'&selId='+selId;
   document.location = percorso;
   return false;
}


function visualizzaMP3(numeroDocumento,startFrom,theArch,theElement){
	if(typeof(customVisualizzaMP3) == 'undefined'){
		//if(theElement == 'dao')
		   window.open('playerMP3.jsp?theDb='+theArch+'&theElement='+theElement+'&docNumber='+numeroDocumento+'&startFrom='+startFrom,'theAuther','width=550,height=195,scrollbars=yes,resizable=yes')
	}else{
		customVisualizzaMP3(numeroDocumento,startFrom,theArch,theElement)
	}
	return false
}
function visualizzaMMS(numeroDocumento,imgPrefix,theArch,theElement){
	if(typeof(customVisualizzaMMS) == 'undefined'){
		   window.open('playerMMS.jsp?theDb='+theArch+'&theElement='+theElement+'&docNumber='+numeroDocumento+'&imgPrefix='+imgPrefix+'&startFrom=1','theAuther','width=550,height=500,scrollbars=yes,resizable=yes')
	}else{
		customVisualizzaMMS(numeroDocumento,imgPrefix,theArch,theElement)
	}
	return false
}
function visualizzaImg(numeroDocumento,startFrom,theArch,theElement,imgPrefix){
	if(typeof(customVisualizzaImgIw) == 'undefined'){
		if(theElement == 'dao')
		   window.open('iwStorico.jsp?theDb='+theArch+'&docNumber='+numeroDocumento+'&startFrom='+startFrom+'&imgPrefix='+imgPrefix,'theAuther','width=550,height=600,scrollbars=yes,resizable=yes')
		if(theElement == 'extptr')
		   window.open('iwFotografico.jsp?theDb='+theArch+'&docNumber='+numeroDocumento+'&startFrom='+startFrom+'&imgPrefix='+imgPrefix,'theAuther','width=550,height=600,scrollbars=yes,resizable=yes')
	}else{
		customVisualizzaImgIw(numeroDocumento,startFrom,theArch,theElement)
	}
	return false
}



