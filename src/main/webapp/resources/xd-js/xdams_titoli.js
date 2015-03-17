function evidenziaDocumento(theDoc){
	(function($){
		if(theDoc==null){schedaBreve.evidenziaFromLeft()}
		var elementiTarget = $("#contentPageSx");
		if(elementiTarget == undefined || elementiTarget.length <= 0){
			elementiTarget = $("body");
		}
		cont = elementiTarget.contents().find("#contentTree");
		if(cont.length <= 0){
			cont = elementiTarget.find("#cont_campi");
	 		cont.find(".ele_tito_sel").addClass("ele_tito");
	 		cont.find(".ele_tito_sel").removeClass("ele_tito_sel");
		    cont.find("#ele_"+theDoc).removeClass("ele_tito");
		    cont.find("#ele_"+theDoc).addClass("ele_tito_sel");
		}else{
			cont.find(".sfTree").removeClass("sfTree");
	    	cont.find("#ele_"+theDoc).parents(".nl,.nd,.nl_no_line").addClass("sfTree");
	    }
    })(jQuery);
}

function loadCheckEsito(){
(function($){ 
	globalOption.multiCheckMode = false;
	globalOption.doingMultiCheck = false;
	$(document).keydown(function(event){
		if(event.keyCode == 16)
			globalOption.multiCheckMode = true;
	})
	$(document).keyup(function(event){
		if(event.keyCode == 16){
			globalOption.multiCheckMode = false;
			globalOption.lastChecked = null;
		} 
	})
	globalOption.allEle = $("span.spck img").length;
	globalOption.allEleJ = $("span.spck img");
	
	(globalOption.allEleJ).hover(
	 		function(){
		 		if($(this).attr("src").indexOf("_on")==-1){/*NON selezionato*/		
		 			$(this).data("status",false);
					$(this).attr("src",globalOption.frontPath+"/img/icons/check_on"+globalOption.theExt);
		 		}else{/*selezionato*/
					 $(this).data("status",true);
				}	
	 		},function(){
	 			  if(!$(this).data("status")){
	 			  	$(this).attr("src",globalOption.frontPath+"/img/icons/check"+globalOption.theExt);
	 			  }
	 		}
	 	);
		(globalOption.allEleJ).click(
	 		function(eventObject){
	 		 if(!$(this).data("status")){
	 			 if(ajaxSetSessionDocs($(this).attr("name"),true)){
	 			 	$(this).data("status",true);	
		 			$(this).attr("src",globalOption.frontPath+"/img/icons/check_on"+globalOption.theExt);	
	 			 }
	 		 }else{
	 			 if(ajaxSetSessionDocs($(this).attr("name"),false)){
	 			 	$(this).data("status",false);	
				 	$(this).attr("src",globalOption.frontPath+"/img/icons/check"+globalOption.theExt);
	 			 } 			 
	 		 }
		 		 	if(!globalOption.multiCheckMode && !globalOption.doingMultiCheck){
			 		 	globalOption.lastChecked = $(this);
			 		 }
					if(globalOption.multiCheckMode && !globalOption.doingMultiCheck){
						globalOption.doingMultiCheck = true;
	 			 		if(globalOption.lastChecked!=null){
		 			 		globalOption.thisChecked = $(this);
	 			 			
	 			 			thisChk = parseInt($(this).attr("rel"),10);
	 			 			lastChk = parseInt((globalOption.lastChecked).attr("rel"),10);	 			 			
	 			 			 	
		 			 			for(i = Math.min(thisChk,lastChk);i<Math.max(thisChk,lastChk);i++){
		 			 				thisEle = $((globalOption.allEleJ).get(i));
			 			 			if(Math.max(thisChk,lastChk)-1==i){
			 			 			thisEle.click();
			 			 			}
			 			 			thisEle.click();
			 			 			try{
			 			 			globalOption.lastChecked = $((globalOption.allEleJ).get(i+1));	
			 			 			}catch(e){}
								}
							xDamsMiniAlert('elaborati '+(Math.max(thisChk,lastChk)-Math.min(thisChk,lastChk))+' elementi')
	 			 		}
	 			 		globalOption.doingMultiCheck = false;
	 			 	}
	 		 		if(!globalOption.doingMultiCheck){
			 		 	globalOption.lastChecked = $(this);
			 		 }
			}	
		)
	   })(jQuery);
}
function testRaffina(obj){
theForm = jQuery(obj);
theOption = theForm.find(":selected");
if(theOption.attr("rel")=='disabled'){
theOption.get(0).selected=false;
theForm.find("option:first").get(0).selected=true;	
return false;}
return true;
}

function setRamoDoc(abilita,theDoc){
	try{
	theForm = jQuery("form[name='inc_theForm']");
	if(theForm){
		theForm = parent.jQuery("form[name='inc_theForm']");	
	}if(theForm){
		if(abilita){
			theForm.find("input[name='ramoDoc']").val(theDoc);
			//theForm.find("select > option[value='tree']").css("background-color",'#fff');
			//theForm.find("select > option[value='tree']").removeAttr("rel");	
			}
		else{
			theForm.find("input[name='ramoDoc']").val('');
			//theForm.find("select > option[value='tree']").css("background-color",'#ddd');
			//theForm.find("select > option[value='tree']").attr("rel",'disabled');		
			//theForm.find("select > option[value='tree']").get(0).selected=false;	
			//theForm.find("select > option:first").get(0).selected=true;				
		}	
	}
	}catch(e){
// 		alert(e);
	}

	
	
}
function lanciaPerpage(linkHref,obj){
  	document.location = linkHref+'&perpage='+obj.value;
}