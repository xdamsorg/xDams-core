function submitVocabolario(){
		document.lookupForm.inputStrQuery.value=document.vocabolarioForm.vocabolario_start_param.value;
		document.lookupForm.selId.value="";
		document.lookupForm.startPage.value="";
		//alert(document.lookupForm.inputStrQuery.value);
		document.lookupForm.action="LookupServlet";
		document.lookupForm.submit();
}
function impostaValore(ilCampo){
	document.lookupForm.inputPerPage.value=ilCampo.value;
	perPage = ilCampo.value
	//alert(perPage)
	goPage('first')

}
function pageNavigator(first, prev, next, last){
	if(first)
		document.getElementById('firstPage').innerHTML = '<a class="bottoneLink" onmouseover="window.status=\'Prima pagina\';return true" onmouseout="window.status=\'\'" onclick="return goPage(\'first\')" href="#">PRIMA PAGINA</a>';
	else
		document.getElementById('firstPage').innerHTML = '<span class="bottoneLink">&#160;</span>';

	if(prev)
		document.getElementById('prevPage').innerHTML = '<a class="bottoneLink" onmouseover="window.status=\'Pagina precedente\';return true" onmouseout="window.status=\'\'" onclick="return goPage(\'prev\')" href="#">PRECEDENTE</a>';
	else
		document.getElementById('prevPage').innerHTML = '<span class="bottoneLink">&#160;</span>';

	if(next)
		document.getElementById('nextPage').innerHTML = '<a class="bottoneLink" onmouseover="window.status=\'Pagina successiva\';return true" onmouseout="window.status=\'\'" onclick="return goPage(\'next\')" href="#">SUCCESSIVA</a>';
	else
		document.getElementById('nextPage').innerHTML = '<span class="bottoneLink">&#160;</span>';

	if(last)
		document.getElementById('lastPage').innerHTML = '<a class="bottoneLink" onmouseover="window.status=\'Ultima pagina\';return true" onmouseout="window.status=\'\'" onclick="return goPage(\'last\')" href="#">ULTIMA PAGINA</a>';
	else
		document.getElementById('lastPage').innerHTML = '<span class="bottoneLink">&#160;</span>';
}


function goPage(theAction){
	startPage = ""
	if(document.pageForm != null){
	if(theAction == 'prev'){startPage = document.pageForm.prevPage.value}
	if(theAction == 'next'){startPage = document.pageForm.nextPage.value}
	if(theAction == 'last'){startPage = document.pageForm.totPages.value}
	if(theAction == 'first'){startPage = '1'}
		startPage = (startPage-1)*perPage+1
		document.lookupForm.startPage.value=startPage;
 		document.lookupForm.submit();
	}
}



function impostaPagina(){
	if(document.pageForm != null){
		document.getElementById('laPagina').innerHTML = document.pageForm.thisPage.value;
		document.getElementById('totPagine').innerHTML = document.pageForm.totPages.value;
	}
}



function creaRecordPop(autherWebApp, theDb){
	percorso = "/"+autherWebApp+"/public/application/jsp/preInsert.jsp?theDb="+theDb
	window.open(percorso,'schedaXdamsAuthority','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
return false
}


function inserisciPopNewAuth(theDbAuth,theDbOrigine,theType,fromAuth,theWebApp,theDbOrigineDescr){
	
	percorso = theWebApp+'/preInsert.jsp?fromAuth=false&theDb='+theDbAuth+'&theType='+theType+'&theDbOrigine='+theDbOrigine+'&theDbOrigineDescr='+theDbOrigineDescr;
	laScheda=window.open(percorso,'schedaXdamsAuther','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
	laScheda = null;
	return false;
}