function evidenziaLI(riga,ilClass){
	ilMenu = riga.parentNode.parentNode
	iTagA = ilMenu.getElementsByTagName('A');
	for(i=0;i<iTagA.length;i++){
		iTagA[i].className = ilClass;
	}
 
}
function showHideLayers(nomeLayer){
	
	hideAllLayers()
	layerCorrente = nomeLayer
	//alert(nomeLayer)
	daMostrare = document.getElementById(layerCorrente)
	if(daMostrare !=null){
		daMostrare.className="doceditLayerVisibile";
	}
		
	return true;
}

function lockDocEdit(show){

	if(show == true){
		$.blockUI({ message: '<h1><img src="'+globalOption.frontPath+'/img/busy.gif" /> salvataggio in corso...</h1>' });
	}else{
		$.unblockUI();
	}

 
}

function lockLoadingDocEdit(show){
	if(document.getElementById('CARICAMENTO') != null){ 
		document.getElementById('CARICAMENTO').style.top = document.body.scrollTop;
		document.getElementById('CARICAMENTO').style.height = document.body.clientHeight;
		if(show == true){
			document.getElementById('CARICAMENTO').className = 'statusLayerVisibile'
		}
		else{
			document.getElementById('CARICAMENTO').className = 'statusLayerNONVisibile'
		}
	}
}

function hideAllLayers(){
try{
	if(salvaRTA()!=null){
		if(typeof(arrLayer)!=null && arrLayer!=null  && typeof(arrLayer)!='undefined'){
			for(i=0;i<arrLayer.length;i++){
				ilClassName = document.getElementById(arrLayer[i]).className;
					if(ilClassName == 'doceditLayerVisibile'){
						document.getElementById(arrLayer[i]).className = 'statusLayerNONVisibile'
	//					alert('nascondo! '+arrLayer[i])
					}
			}
			areaBig = document.getElementById('textAreaBigLayer');
			if(areaBig!=null){
				 areaBig.className = 'statusLayerNONVisibile'
			}
		}
	}

}catch(e){}

}
 
function showLastLayer(){
	if(typeof(layerCorrente)!=null && typeof(layerCorrente)!='undefined'){
		eval('document.getElementById(\''+layerCorrente+'\').className = \'doceditLayerVisibile\'')
		}
}

function hideSection(prefix){
	//document.getElementById(prefix).style.visibility = '';
	if(salvaRTA()){
	document.getElementById('sezione_head_'+prefix).style.display = 'none';
	document.getElementById('sezione_'+prefix).style.display = 'none';
	if(document.getElementById('control_'+prefix) !=null)	
		document.getElementById('control_'+prefix).style.display = 'none';
	}
}

function hideArea(prefix){
	if(salvaRTA()){
	//document.getElementById(prefix).style.visibility = '';
	//document.getElementById('sezione_head_'+prefix).style.display = 'none';
		document.getElementById('macroarea_'+prefix).style.display = 'none';
	 }
}
var sectionToOpenArray = new Array();
function showSection(prefix){
	sectionToOpenArray[sectionToOpenArray.length]=prefix;
}

function showAllSection(){
for(i=0;i<sectionToOpenArray.length;i++){
 	prefix = sectionToOpenArray[i];
 	document.getElementById('sezione_head_'+prefix).style.display = '';
	document.getElementById('sezione_'+prefix).style.display = '';
	replaceText = (document.getElementById('sezione_link_'+prefix).innerHTML)
	if(replaceText.indexOf('+')==0 || replaceText.indexOf('-')==0){
		replaceText = replaceText.replace('+','-');
	}else{
		replaceText = replaceText.replace('apri.gif','chiudi.gif');
	}
	try{
	 document.getElementById('sezione_link_'+prefix).innerHTML = replaceText;
	}catch(e){
	}
	if(document.getElementById('control_'+prefix) !=null)	
		document.getElementById('control_'+prefix).style.display = '';
}
return true;
}

function openSection(sezione,bottone){
	verso = bottone.innerHTML
	
 	if(verso.indexOf('+')==0 || verso.indexOf('-')==0){
		if(verso.indexOf('+')==0){
			if(salvaRTA()){
				document.getElementById(sezione).style.display = ''
				bottone.innerHTML = verso.replace('+','-')
				sezione = sezione.substring(("sezione_").length)
				if(document.getElementById('control_'+sezione)!=null)
					document.getElementById('control_'+sezione).style.display = '';
			}
		}
		else{
			if(salvaRTA()){
			document.getElementById(sezione).style.display = 'none'
			bottone.innerHTML = verso.replace('-','+')
			sezione = sezione.substring(("sezione_").length)
			if(document.getElementById('control_'+sezione)!=null)
				document.getElementById('control_'+sezione).style.display = 'none';
			}
		}	
	}else{
	if(verso.indexOf('apri.gif')>0){
		document.getElementById(sezione).style.display = ''
		bottone.innerHTML = verso.replace('apri.gif','chiudi.gif');
		sezione = sezione.substring(("sezione_").length)
		if(document.getElementById('control_'+sezione)!=null)
			document.getElementById('control_'+sezione).style.display = '';
	}
	else{
		if(salvaRTA()){
		document.getElementById(sezione).style.display = 'none'
		bottone.innerHTML = verso.replace('chiudi.gif','apri.gif');
		sezione = sezione.substring(("sezione_").length)
		if(document.getElementById('control_'+sezione)!=null)
			document.getElementById('control_'+sezione).style.display = 'none';
		}
	}}
}

function openSection2(sezione,bottone,frase){
	verso = document.getElementById(sezione).style.display
	if(verso == 'none'){
		document.getElementById(sezione).style.display = ''
		bottone.innerHTML = frase
	}
	else{
		document.getElementById(sezione).style.display = 'none'
		bottone.innerHTML = frase
	}
}

 

function cambiaLayer(verso){
if(Windows.focusedWindow!=null){
	returnTextArea();
}
	nuovoMenu = parseInt(menuCorrente)+verso
	if(nuovoMenu  == 0)
		return
	if(nuovoMenu  == arrLayer.length+1)
		return
	disEvidenzia(menuCorrente)
	menuCorrente = parseInt(nuovoMenu)
	evidenzia(nuovoMenu)
	eval('showHideLayers(\''+arrLayer[menuCorrente-1]+'\',\'\',\'show\')')

}


function disEvidenzia(riga){
	var voceOld = 'menu'+riga
	var voceNew = 'menu'+menuCorrente
	eval('document.getElementById(\'menu'+menuCorrente+'\').className = \'menuLeftHover\'')
	menuCorrente = riga
	eval('document.getElementById(\'menu'+menuCorrente+'\').className = \'menuLeft\'')
}

function evidenzia(riga){
	var voceOld = 'menu'+menuCorrente
	var voceNew = 'menu'+riga
	eval('document.getElementById(\'menu'+menuCorrente+'\').className = \'menuLeft\'')
	menuCorrente = riga
	eval('document.getElementById(\'menu'+menuCorrente+'\').className = \'menuLeftHover\'')
}


function scriviRigaMenu(nomeLayer,nomeId,nomeEsteso,idTabella,numRighe){
	document.writeln('<td width="'+((100/numRighe)+1)+'%" title="'+nomeEsteso+'" name="03" align="center" class="menuLeft" ')
	if(nomeLayer!=''){
		document.writeln('onclick="evidenzia(\''+idTabella+'\');showHideLayers(\''+nomeLayer+'\',\'\',\'show\')" ')
	}
	document.writeln('id="menu'+idTabella+'" style="padding-left:5px">');
	document.writeln('<a href="javascript:void(0)" class="menuLeftLink" onclick="evidenzia(\''+idTabella+'\');showHideLayers(\''+nomeLayer+'\',\'\',\'show\')">'+nomeId+' </a></td>');
}

function ajaxConfirm(){
//	Dialog.login(document.getElementById('SALVATAGGIO').innerHTML, {className:"alphacube", width:500, cancelLabel: "annulla", okLabel: "salva", onCancel:function(win){ Windows.focusedWindow.close(); return false;}, onOk:function(win){ Windows.focusedWindow.close(); return true;}); 
}


function salvaRTA(){ 
if(eleArray!=null){
	for(i =0;i<eleArray.length;i++){
		elementoArray = eleArray[i].split(";");
		elementoId = elementoArray[0];
		elementoName = elementoArray[1];
		try{
			
			
			
			
			appoggio = thisMovie(elementoId+'-ID').getValidHTML();
			appoggio = clearChar(appoggio,String.fromCharCode(8217),"'");
			appoggio = clearChar(appoggio,String.fromCharCode(8219),"'");
//			appoggio = clearChar(appoggio,String.fromCharCode(8221),'"');
//			appoggio = clearChar(appoggio,String.fromCharCode(8222),'"');
//			appoggio = clearChar(appoggio,String.fromCharCode(8220),'"');
			
			appoggio = clearChar(appoggio,String.fromCharCode(0x201C),'"');
			appoggio = clearChar(appoggio,String.fromCharCode(0x201D),'"');
			appoggio = clearChar(appoggio,String.fromCharCode(0x201E),'"');
			appoggio = clearChar(appoggio,String.fromCharCode(0x201F),'"');
			appoggio = clearChar(appoggio,String.fromCharCode(0x2034),'"');
			
			appoggio = clearChar(appoggio,String.fromCharCode(8211),'-');		
			appoggio = clearChar(appoggio,String.fromCharCode(8212),'-');
		   	document.theForm[elementoName].value = appoggio;
		}catch(e){
		//	alert(e)
		}
	}
	}
	return true;
}

function thisMovie(movieName) {
    var obj =  document[movieName];
    if(obj==null)
     obj = window[movieName]
    return obj;
   
}

function loadRTA(){
if(eleArray!=null){
 	for(i =0;i<eleArray.length;i++){
		elementoArray = eleArray[i].split(";");
		elementoName = elementoArray[1];
		elementoId = elementoArray[0];
		theTextarea = document.theForm[elementoName]
		
 		try{
	 		thisMovie(elementoId+'-ID').setHTML(theTextarea.value);
		}catch(e){
	 	//	alert(e)
		}
	}
	}
	//alert('loadRTA')
}
function mceInitialize(obj) {
	if(obj!=null){
	obj.tinymce( {
		// General options
		/*
		 * mode : "specific_textareas", editor_selector : "mceEditor",
		 */
		// convert_newlines_to_brs : true,
		force_br_newlines : true,
		convert_urls : false,
		theme : "advanced",

		language : "it",
		entity_encoding : "named",
		fix_nesting : true,
		skin : "o2k7",
		plugins : "safari,pagebreak,style,layer,table,save,advhr,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,paste,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,inlinepopups",

		// Theme options
		theme_advanced_buttons1 : "bold,italic,|,justifyleft,justifycenter,justifyright,styleselect,formatselect,|,hr,removeformat,|,sub,sup,|,charmap,iespell,backcolor ,forecolor,|,preview,|,fullscreen",
		theme_advanced_buttons2 : "search,replace,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,tablecontrols,|,print,code",
		theme_advanced_buttons3 : "",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_blockformats : "h2,h3",
		invalid_elements : "a",
		theme_advanced_resizing : false,

		// Example content CSS (should be your site CSS)
		content_css : globalOption.frontPath + "/css/testo.css?" + (new Date()).getMilliseconds(),

		// Drop lists for link/image/media/template dialogs
		template_external_list_url : "lists/template_list.js",
		external_link_list_url : "lists/link_list.js",
		external_image_list_url : "lists/image_list.js",
		media_external_list_url : "lists/media_list.js",

		// Replace values for the template plugin
		template_replace_values : {
			username : "Some User",
			staffid : "991234"
		}
	});	
	}
}
$(document).ready(function(){
	mceInitialize($('textarea.mceEditor'));
});