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
   return false;
}


function infoByNumDoc(physDoc,ilArchivio){
	if(physDoc==null || physDoc==""){
		alert("Impossibile avviare info, physDoc non presente.");
	}else{
		//window.open('./ServletDocumentInfo?go=info.jsp&db_name='+ilArchivio+'&idCode='+ilLemmaCode+'&queryFix='+queryFix,'info');
		//window.open(globalOption.infoURLPrefix+ilArchivio+globalOption.infoURLSuffix+'?idCode='+ilLemmaCode+'&queryFix='+queryFix,'schedaXdamsAuther','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
		urlWin = globalOption.infoURLPrefix+ilArchivio+globalOption.infoURLSuffix+'?physDoc='+physDoc;
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
   return false;
}

function chiudiDialog() {
	$('#aDialog').dialog('destroy');
	$('#aDialog').remove();
}