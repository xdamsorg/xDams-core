function infoAuther(ilLemmaCode,ilArchivio,queryFix){
	if(queryFix==null || queryFix==""){
		alert("Impossibile avviare info, query non presente.");		
	}else{
		//window.open('./ServletDocumentInfo?go=info.jsp&db_name='+ilArchivio+'&idCode='+ilLemmaCode+'&queryFix='+queryFix,'info');
		//window.open(globalOption.infoURLPrefix+ilArchivio+globalOption.infoURLSuffix+'?idCode='+ilLemmaCode+'&queryFix='+queryFix,'schedaXdamsAuther','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
		urlWin = globalOption.infoURLPrefix+ilArchivio+globalOption.infoURLSuffix+'?idCode='+ilLemmaCode+'&queryFix='+queryFix;
		top.$('#aDialog').remove();
		top.$('body').append('<div id="aDialog"></div>');
	 
		// chiudiDialog();
		var widthProto = "450";
		var heightProto = "400";
		var statusBar; 
		var anIframe = $('<iframe style="border: 0px;" width="100%" height="100%"></iframe>');
		anIframe.attr('src', urlWin);
		top.$('#aDialog').html(anIframe);
		top.$('#aDialog').dialog({
			autoOpen : true,
			modal : false,
			height : heightProto ? heightProto : 150,
			width : widthProto ? widthProto : 250,
			resizable : false,
			title : statusBar ? statusBar : 'xDams info'
		});		
	}
	//(thePage,actionFlag,statusBar,physDoc,selId,pos)
   //window.open('/'+autherWebApp+'/public/application/jsp/infoAutherLemma.jsp?theDb='+ilArchivio+'&codeToFind='+ilLemmaCode+'&nameToFind='+ilLemma,'theAuther','width=400,height=450,scrollbars=yes')
   return false;
} 

function chiudiDialog() {
	$('#aDialog').dialog('destroy');
	$('#aDialog').remove();
}