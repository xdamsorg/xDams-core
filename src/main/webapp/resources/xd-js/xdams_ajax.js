
var isIE;
var completeTable;
var completeField;
var autorow;
var distFromTop;
var isIE = false;
var req;
var ajaxSelect;

var agt=navigator.userAgent.toLowerCase();
var is_major = parseInt(navigator.appVersion);
var is_minor = parseFloat(navigator.appVersion);
var is_nav  = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1) && (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1) && (agt.indexOf('webtv')==-1) && (agt.indexOf('hotjava')==-1));

function AJAXInteraction(url,innerName) {
  // alert("quiiiii"+agt);
   this.url = url;
  // alert("quaaaa"); 
   var req = init();
  // alert("quooooo"); 
   req.onreadystatechange = processRequest;
  // alert("quppppppp");
   function init() {
     //if (is_nav && window.XMLHttpRequest) {
       return new XMLHttpRequest();
     //} else if (window.ActiveXObject) {
       //isIE = true;
      // return new ActiveXObject("Microsoft.XMLHTTP");
     //}
   } 
 
    function processRequest () { 
    	
    	//alert("(req.status " +req.status);
   	if (req.readyState == 1) {
     	attendere(true);
    }
    if (req.readyState == 2) {
     	attendere(true);
    }   
   	if (req.readyState == 3) {
     	attendere(true);
    }if (req.readyState == 4) {
     	attendere(false); 	
        if (req.status == 200 || req.status == 201) {
        	var cType = this.getResponseHeader("Content-Type");
        	//alert("aaaareq.cType " +cType);
        	var resProcess = req.responseXML;
        	//alert("responseXML.responseXML" +req.responseXML);
        	if(!resProcess){
        		resProcess = req.responseText;
        	}
 	        dispatcherProcess(req.responseXML,req,innerName);
        }else{
     	 //alert("1 "+req.parseError);
         }
      }else{
     	 //alert("2 "+req.parseError);
      }
    }

    this.send = function() {
        req.open("GET", url, true);
        req.send(null);
    };
}


function getElementY(element){
	var targetTop = 0;
	if (element.offsetParent) {
		while (element.offsetParent) {
			targetTop += element.offsetTop;
            element = element.offsetParent;
		}
	} else if (element.y) {
		targetTop += element.y;
    }
	return targetTop;
}

function getElementX(element){
	var targetTop = 0;
	element = element.parentElement;
	if (element.offsetLeft ) {
	    targetTop = element.offsetLeft;
            //element = element.offsetParent;

	} else
		if (element.x) {
		targetTop += element.x;
    }

	return targetTop;
}


function init(fromTop) {
    distFromTop =  fromTop;
    completeTable = document.createElement("table");
    completeTable.style.textColor = '#000000';
    completeTable.style.width = '150px';
    completeTable.style.borderColor = '#993300';
    completeTable.style.borderCollapse = 'collapse';
}

function doCompletion(theElement,theTypology,theMode) {
    completeField = document.getElementById(theElement.id);
    ilValore = completeField.value;
    if(ilValore.indexOf("\"")==0)
    	ilValore = ilValore.substring(1);
    if (completeField.value == "") {
        clearTable();
    } else {
        var url = "";
        if(theMode == 'idx'){
			url = "../../ajax.html?actionFlag=vocabolarioSelect&value=" + escape(ilValore)+"&key=" + escape(theElement.name)+"&typology=" + escape(theTypology);
        	//url = "../../ajax.html?actionFlag=vocabolarioSelect&db=" + escape(document.theForm.db.value)+"&value=" + escape(ilValore)+"&key=" + escape(theElement.name)+"&typology=" + escape(theTypology);
        	//url = "ajaxVoc.jsp?actionFlag=vocabolarioSelect&db=" + escape(document.theForm.db.value)+"&value=" + escape(ilValore)+"&key=" + escape(theElement.name)+"&typology=" + escape(theTypology);
       	}
         //alert("11111111")
        var ajax = new AJAXInteraction(url);
        //alert("22222222")
        ajax.send();
        //alert("33333333")
    }
}

function controlledLookup(theElement,theTypology,theMode) {
   // alert("controlledLookup");
    completeField = document.getElementById(theElement.id);
    ilValore = completeField.value;
    if(ilValore.indexOf("\"")==0)
    	ilValore = ilValore.substring(1);
    if (completeField.value == "") {
        clearTable();
    } else {

        var url = "";

        if(theMode == 'idx'){
        	url = "ajaxVoc.jsp?db=" + document.theForm.db.value+"&value=" + ilValore+"&key=" + theElement.name+"&typology=" + theTypology+"&theHost=<%=theHost%>";
        	}

       //alert(url)
        var ajax = new AJAXInteraction(url);
        ajax.send();
    }
}




function dispatcherProcess(responseXML,responseAll,innerName){
	//alert("alert 00");
 	var vocKeys = null;
 	try{
 		vocKeys = responseXML.getElementsByTagName("keyList")[0];	
 	}catch(e){}
 	
 	//alert("alert 1");
 	var optionKeys = null;
 	try{
 		optionKeys = responseXML.getElementsByTagName("optionList")[0];
 	}catch(e){}
 	
 	//alert("alert 2");
	var sessionList = null;
	try{
		sessionList = responseXML.getElementsByTagName("sessionList")[0];
	}catch(e){}
	// alert("alert 3");
//	var cutList = responseXML.getElementsByTagName("div")[0];
	var errorAjax = null;
	try{
		errorAjax = responseXML.getElementsByTagName("error")[0];
	}catch(e){}
	//alert("alert 4");
	var succesAjax = null;
	try{
		succesAjax = responseXML.getElementsByTagName("succes")[0];
	}catch(e){}
	//alert("alert 5");
// alert(responseAll.responseText);
	if(vocKeys!=null){
		idxProcess(responseXML);
	}else if(optionKeys!=null){
		optionProcess(responseXML);
	}else if(sessionList!=null){
	//document.write(responseXML);
		//sessionProcess(responseXML)
	}else if(!errorAjax && !succesAjax && responseAll.responseText!=null && innerName!=null){
	//document.write(responseXML);
		//sessionProcess(responseXML)
		//alert("alert 00000");
		if(document.getElementById(innerName)!=null){
			document.getElementById(innerName).innerHTML = unescape((responseAll.responseText));		
		}else{
			alert((getLocalizedString('Attenzione_innerName_non_impostato','Attenzione innerName non impostato' )+" >> "+innerName+" <<"));
		}

	}else if(errorAjax){
		alert(errorAjax.childNodes[0].nodeValue);
	}else if(succesAjax){
		eval(succesAjax.childNodes[0].nodeValue);
	}
}

function clearSelect(theSelect){
	//alert('pulisci')
	var toDelete = theSelect;
	//alert(toDelete)
	for (var i = 0; i < toDelete.childNodes.length; i++) {
		if(i>2){
			toDelete.removeChild(toDelete.childNodes[i]);
		}
	}
}

function optionProcess(responseXML) {
  var optionKeys = responseXML.getElementsByTagName("optionList")[0];
  //alert(ajaxSelect.find("option").length);
  if (ajaxSelect.find("option").length == 2 || ajaxSelect.find("option").length == 0) {

	 //clearSelect(ajaxSelect);
     if(optionKeys!=null){

	    if(appendSelectKey("------","")){

	    for (var loop = 0; loop < optionKeys.childNodes.length; loop++) {

		var optionKey = optionKeys.childNodes[loop];

		var optionKeyVal = optionKey.getElementsByTagName("value")[0];
		var optionKeyText = optionKey.getElementsByTagName("text")[0];

		 // alert(optionKeyText.childNodes[0]);
		 //alert(optionKeyVal.childNodes[0]);

		 keyText = "";
		 keyVal = "";
		 if(optionKeyVal.childNodes[0]!=null){
			keyVal = optionKeyVal.childNodes[0].nodeValue;
		 }
		 if(optionKeyText.childNodes[0]!=null){
			keyText = optionKeyText.childNodes[0].nodeValue;
		 }

		if(appendSelectKey(keyText,keyVal)){
			//codiceHTML = ajaxSelect.innerHTML

			//ajaxSelect.innerHTML = codiceHTML

		}

		}
	    }
    }else{
   //    alert(vocKeys);
    }
	ajaxSelect.parentNode.appendChild(ajaxSelect);
   }

}


function idxProcess(responseXML) {

    clearTable();
   //alert((responseXML));
    var vocKeys = responseXML.getElementsByTagName("keyList")[0];


    if(vocKeys!=null){


    if (vocKeys.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "1");
    } else {
        clearTable();
    }

    for (var loop = 0; loop < vocKeys.childNodes.length; loop++) {
	var vocKey = vocKeys.childNodes[loop];
        var vocKeyVal = vocKey.getElementsByTagName("val")[0];
        var vocKeyId = vocKey.getElementsByTagName("freq")[0];
      //  alert("vocKeyVal "+vocKeyVal.childNodes[0].nodeValue);
        appendvocKey(vocKeyVal.childNodes[0].nodeValue, vocKeyId.childNodes[0].nodeValue);
    }
    }else{
   //    alert(vocKeys);
    }


    scriviResponse(completeField);
}



function clearTable() {
    if (completeTable) {
      completeTable.setAttribute("bordercolor", "white");
      completeTable.setAttribute("border", "0");
      completeTable.style.visible = false;
      	for (var loop = completeTable.childNodes.length-1; loop >= 0 ; loop--) {
      	  completeTable.removeChild(completeTable.childNodes[loop]);
      	}
    }
}
function appendSelectKey(optionText,optionValue) {
var optionElement = document.createElement("option");
 optionElement.appendChild(document.createTextNode(optionText));
 optionElement.setAttribute("value",optionValue);
 //alert(optionText+" - "+optionValue )
 ajaxSelect.appendChild(optionElement);
return true;
}



function appendvocKey(vocKeyVal,vocKeyId) {
//    var vocKeyValCell;
//    var vocKeyIdCell;
    var row;
    var nameCell;
    var idCell;
    if (isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
        idCell = row.insertCell(1);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
      //  nameCell.setAttribute("nowrap","nowrap");
        idCell = document.createElement("td");
        row.appendChild(nameCell);
        row.appendChild(idCell);
        completeTable.appendChild(row);
    }

    var linkElement = document.createElement("a");
    linkElement.className = "testoMain11";

    linkElement.setAttribute("href", "javascript:returnValue('"+escape(escape(vocKeyVal))+"')");

    linkElement.appendChild(document.createTextNode(vocKeyVal));
    nameCell.appendChild(linkElement);
    idCell.className = "testoMain11";
    idCell.appendChild(document.createTextNode(vocKeyId));
}

function scriviResponse(){
   	ilPadre = completeField.parentElement;
   	loSpan = document.createElement("span");
   	loSpan.className = "suggSpan";
   	ilPadre.appendChild(loSpan);
   	loSpan.style.top = (getElementY(completeField) - distFromTop) + "px";
   	loSpan.style.left = (getElementX(completeField)+2) + "px";
   	loSpan.appendChild(completeTable);
}

function returnValue(valore,campo){
	valore = unescape(unescape(valore));
	if(valore.indexOf(" ")==0)
		valore = '"'+valore+'"';
	completeField.value = valore;
	clearTable();
}


// NUOVE FUNZIONI


function ajaxSessionValues(physDocNumber,actionValue,actionFlag,physDocToPaste,theArchName) {
 if(actionFlag==null){
	 actionFlag = "selectedDoc";
 }
 if(physDocToPaste==null){
	 physDocToPaste = "";
 }
 /*
 if(theArchName==null){
	 alert("Attenzione impostare archivio! "+globalOption.theArch);
 }*/
 	try{
 	 	strURL = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag="+actionFlag+"&action="+actionValue+"&physDoc="+physDocNumber+"&physDocToPaste="+physDocToPaste;
 	 	//alert(strURL);
 	 	var ajax = new AJAXInteraction(strURL,"scriviQui");
 	 	ajax.send(); 		
 	}catch(e){
 		alert("e"+e);
 		return false;		
 	}

 return true;
}

function ajaxSetSessionDocs(physDocNumber,addEle,theArchName){
actionToSend = "removeElement";
	if(addEle){
		actionToSend="addElement";
	}
return ajaxSessionValues(physDocNumber,actionToSend,'selectedDoc','',theArchName);
}

function ajaxSetSessionDocsToCut(physDocNumber,physDocToPaste,actionType,theArchName){
	return ajaxSessionValues(physDocNumber, actionType, "cutPaste", physDocToPaste, theArchName);
}

function ajaxSearchRelatedRecords(codeToFind,physDoc,innerName){
 strURL  = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=searchRelatedRecords&codeToFind="+codeToFind+"&physDoc="+physDoc;
 //alert(strURL);
 var ajax = new AJAXInteraction(strURL,innerName);
 ajax.send();
 return true;
}


function ajaxMultipleSelectionMode(theArchName) {
var extraLink = "";
if(confirm(getLocalizedString('Vuoi_svuotare_gli_appunti','Vuoi svuotare gli appunti')+"?")){
	extraLink = "&svuota=ok";
}
strURL  = "../../"+theArchName+"/ajax.html?actionFlag=selectedDoc&action=switch"+extraLink;
var ajax = new AJAXInteraction(strURL);
ajax.send();
return true;
}



function ajaxEraseQueryBean(theArch) {
 strURL  = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=eraseQueryBean&theArch="+theArch;
 //alert(strURL);
  var ajax = new AJAXInteraction(strURL);
  ajax.send();
  if(document.getElementById('queryBeanSelect')!=null){
	  document.getElementById('queryBeanSelect').options.length=0;
	  document.getElementById('queryBeanSelect').options[0]=new Option(getLocalizedString('nessuna_ricerca_effettuata','nessuna ricerca effettuata'), "", true, true);
  }
return true;
}

function ajaxUnlockDocument(physDoc) {
	strURL  = "../../ajax.html?actionFlag=unlockRecord&action=unlock&physDoc="+physDoc;
	//alert(strURL);
	var ajax = new AJAXInteraction(strURL);
	ajax.send();
 return true;
}


function attendere(flagWait){
	if(top.document.getElementById("attendere")!=null){
		if(flagWait){
			top.document.getElementById("attendere").className="attendere";
		}else{
			top.document.getElementById("attendere").className="statusLayerNONVisibile";
		}
	}
}

