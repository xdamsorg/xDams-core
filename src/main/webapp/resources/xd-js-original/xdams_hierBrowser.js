function fixTree(){
	$('.prevIsLast').each(function(){
		 var ele = $(this).prev('div').find("div[class^='img_']");
 		 if(ele.hasClass('img_close')){ele.removeClass('img_close');ele.addClass('img_close_last')}
		 if(ele.hasClass('img_open')){ele.removeClass('img_open');ele.addClass('img_open_last')}
		 ele.parent('div').removeClass('nl_24');ele.parent('div').addClass('nl_nl')
	})

	$('.prevIsFather').each(function(){
		 var ele = $(this).prev('div').find("div[class^='img_']");
 		 //if(ele.hasClass('img_close')){ele.removeClass('img_close');ele.addClass('img_file')}
		 if(ele.hasClass('img_open')){ele.removeClass('img_open');ele.addClass('img_file')}
	//	 if(ele.hasClass('img_open_last')){ele.removeClass('img_open');ele.addClass('img_file')}
		// ele.parent('div').removeClass('nl_24');ele.parent('div').addClass('nl_nl')
	})


	}
function loadCheck(){
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
	 			 if(ajaxSetSessionDocs($(this).attr("name"),true,globalOption.theArchName)){
	 			 	$(this).data("status",true);
		 			$(this).attr("src",globalOption.frontPath+"/img/icons/check_on"+globalOption.theExt);
	 			 }
	 		 }else{
	 			 if(ajaxSetSessionDocs($(this).attr("name"),false,globalOption.theArchName)){
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
			 			 			if(i==Math.max(thisChk,lastChk)-1){
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
}
function evidenziaDocumento(){
if(parent.schedaBreve!=null){
	if(typeof(top.schedaBreve.evidenziaFromLeft)!= 'undefined'){
		top.schedaBreve.evidenziaFromLeft();
		}
	}
}

function lanciaPerpageHier(theUrlObj,obj){
theUrlObj = leftArea
theUrl = theUrlObj.location;
if(theUrl==null){
theUrl = theUrlObj.src;
}else{
theUrl = theUrl.href;
}
theBase = theUrl.substring(0,theUrl.indexOf("?"))+"?";
theQuery = theUrl.substring(theUrl.indexOf("?")+1);
theQueryArray = theQuery.split("&");
foundOne = false;
for(i=0;i<theQueryArray.length;i++){
	tempNameArray = theQueryArray[i].split("=");
	name = tempNameArray[0];
	value = tempNameArray[1];
	if(name == 'docCount'){
		value = obj.value;
		foundOne = true;
	}
	theBase+="&"+name+"="+value;
	if(!foundOne){
		theBase+="&"+"docCount"+"="+ obj.value;
	}
}

	theUrlObj.location.href = theBase;
	return true;
}