theTextArea = null
currInput = null
iSimboli = ""
function setTextArea(obj){
	if(obj != null){
		theTextArea = obj
	}
}
function setCurrInput(obj){
	if(obj != null){
		ilTag = (obj.tagName).toUpperCase()
		if(ilTag == 'INPUT' || ilTag == 'TEXTAREA')
			if(ilTag == 'TEXTAREA'){setTextArea(obj)}
			else{theTextArea = null}
			if(currInput == null){
				currInput = obj

				if(currInput.style.background.toUpperCase() != '#FF886A')
					currInput.style.background = "#FFFFCC"
				}
				else{
				if(currInput.style.background.toUpperCase() != '#FF886A')
					currInput.style.background = "#FFFFFF"
				currInput = obj

				if(currInput.style.background.toUpperCase() != '#FF886A')
					currInput.style.background = "#FFFFCC"
				}
		}
}
function closeSymbols(thePage){
	thePage.close()
	return false
}
function openSymbols(obj,path){
		simboli = generateSymbols()
		sybolArea = window.open("","sybolArea","width=300,height=210")
		theHead = "<html><head><style type=\"text/css\">body{background:#ccc}a{text-decoration:none;color:#000}a:hover{text-decoration:underline}</style></head><body class=\"bodyMain\"><script>self.focus()</script>";
		theFoot = "</body></html>";
		sybolArea.document.write(theHead+simboli+theFoot)
		sybolArea = null
}
function returnChar(theChar){
	if(currInput != null){
		if(document.getElementById('textAreaBigLayer').className == 'doceditLayerTextAreaBig'){
			ilValore = document.getElementById('textAreaBig').value
			ilValore += theChar
			document.getElementById('textAreaBig').value = ilValore
		}
		else{
			ilValore = currInput.value
			ilValore += theChar
			currInput.value = ilValore
		}
		}
	else{alert('Attenzione: selezionare un campo')}
	return false
}
function generateSymbols(){
	if(iSimboli == ""){
		iSimboli = "<div class=\"doceditLayerSymbol\"><tt class=\"doceditActionLinkBorder\">";
		for(z=161;z<255;z++){
			if(z!=73 && z!=180 && z!=180 && z!=180 && z!=180)
				iSimboli += " &#160;<a href=\"javascript:void(0)\" onclick=\"return window.opener.returnChar('"+String.fromCharCode(z)+"')\" class=\"doceditActionLinkBorder\"><tt style=\"font-size:20px;\">"+String.fromCharCode(z)+"</tt></a>&#160;"
			}
		iSimboli += "<br><br> </tt><span class=\"doceditActionLinkBorder\">[</span><a href=\"javascript:void(0)\" onclick=\"return window.opener.closeSymbols(window)\" class=\"doceditActionLink\">chiudi</a><span class=\"doceditActionLinkBorder\">]</span></div>"
		}
	return iSimboli
}
 
function returnTextArea(){
	document.getElementById('contenutoEdit').style.display='';
	if(theTextArea != null){
		theTextArea.value = document.getElementById('textAreaBig').value
		theTextArea.focus()
	}
	else{
		alert('Errore: impossibile trovare la textArea.')
	}	
}
 

function openTextArea(findIt){
 	var anchor = $(findIt);
	var textArea = anchor.parent("td").parent("tr").find("textarea:first");
 
	setCurrInput(textArea.get(0));
	if(!salvaRTA()){
		return false;
	}
	hideAllLayers()

       $.blockUI({ message: '<div id="question" style="cursor: default"><textarea name="textAreaBig" id="textAreaBig" style="margin-top:5px">'+theTextArea.value+'</textarea><div><input type="button" id="yes" value=" conferma" />&#160;&#160;&#160;<input type="button" id="no" value=" annulla" /></div></div>', allowBodyStretch: true, css: { left:'10%',top:'10%',height:'80%',width: '80%' } }); 
      $("textarea:first",'.blockPage').height($('.blockPage').height()-40);

	 $('#yes').click(function() {	
     		//returnTextArea()
		textArea.val($("textarea:first",'.blockPage').val());
	        $.unblockUI();	
	        showLastLayer();
       }); 
     $('#no').click(function() {	
	        $.unblockUI();	
	        showLastLayer()
       }); 
return true;
}
function returnRichTextArea(val){
	globalOption.richEditWin.close();
	if(theTextArea != null){
		theTextArea.value = val
		theTextArea.focus()
	}
}
function getRichTextAreaValue(){
return theTextArea.value;
}

function openRichTextArea(findIt){
	while(findIt.parentNode!=null){
		theTextAreaArray = findIt.getElementsByTagName('textarea');
		if(theTextAreaArray!=null && theTextAreaArray.length==1){
			theTextArea = theTextAreaArray[0];
 			break;
		}else{
			findIt = findIt.parentNode;
		}
	}
	setCurrInput(theTextArea);
	urlWin = globalOption.frontPath+"/rte/rte.html";
	globalOption.richEditWin = window.open(urlWin,'editorWind','toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=775,height=400');
	globalOption.richEditWin.focus();
}
