function copyInClipBoard(imgHref,didaTxt,clipType){
	
	document.formClipBoard.clipboard_user.value='<%=StringEscapeUtils.escapeJavaScript(userBean.getUserName())%>';

	document.formClipBoard.clipboard_archive.value='<%=theArch%>';
	document.formClipBoard.clipboard_http_url.value=imgHref;
	document.formClipBoard.clipboard_id.value='<%=theXML.valoreNodo("/"+userBean.getThePne()+"/@id")%>';
	document.formClipBoard.clipboard_text.value=didaTxt;
	document.formClipBoard.clipboard_type.value=clipType;
	if(confirm("Sicuro di voler aggiungere alla clipboard?")){
		laPopupClip = window.open('','laPopupClip','width=450,height=400,toolbar=0,status=0,scrollbars=yes,resizable=yes');
		document.formClipBoard.target="laPopupClip";
		document.formClipBoard.submit();
	}
}