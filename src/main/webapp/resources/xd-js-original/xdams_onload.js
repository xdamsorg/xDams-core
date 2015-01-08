function onLoadActions(act){
	if(act == 'tree'){

		if(locatPos.indexOf("#")>0){
			ele = locatPos.substring(locatPos.indexOf("#")+1);
	 	 	jQuery("a[name='"+ele+"']").css("border","1px dotted #A75947");
		}
		try {
		 	jQuery("[class^='nl']").hover(
			 		function(){
			 			jQuery(this).addClass("sfEv");
			 		},
			 		function(){
			 			jQuery(this).removeClass("sfEv");
			 		}
			 	);			
		} catch (e) {
		}

		loadCheck();
		fixTree();
	 	evidenziaDocumento();
	  	setDocInfo();
	 }

	 else if(act == 'workspace'){
		 window.focus();
	 	$('.arcPadre').click(
		function(){
			if($(this).next('.elArc:hidden').length){
				$(this).next('.elArc').slideDown('fast');
				$.cookie($(this).next('.elArc').attr("id"), 'open',{ expires: 365 });
			}else{
				$(this).next('.elArc').slideUp('fast');
				$.cookie($(this).next('.elArc').attr("id"), null);
			}
		}
		)
		$('.arcPadre + ul.elArc:empty').prev('.arcPadre').fadeTo('','0.5');
		$('.elArc').each(function(){
			if($.cookie($(this).attr("id")) != null){
					$(this).show();
				}
		})
	 }
	 else if(act == 'esito'){
	 	setDocInfo();
	 	tooltip();
	 	loadCheckEsito();
	 }

 }