jQuery(document).ready(function(){
});

function ajaxGetValues(theElement,theFile,thePath) {
 var ajaxSelect = $(theElement);
 if(ajaxSelect.find("option").length<2){
// strURL  = "AjaxServlet?flagTipologia=valoriControllati&nameFileXml="+theFile+"&xpath="+thePath;
 var options = {"flagTipologia":"valoriControllati", "nameFileXml": theFile,"xpath":thePath }
 $.post("AjaxServlet", options,  function(data){  processAjaxGetValuesXml(data,ajaxSelect);  }, "xml");
}
}

function processAjaxGetValuesXml(data,ajaxSelect){
	var ele = "";
	var selEle = ajaxSelect.find("option:first");
	ajaxSelect.empty();
 	ajaxSelect.append(selEle);
	$(data).find("optionList > option").each(function(){
		ele += "<option value=\""+$(this).find("value").text()+"\">"+$(this).find("text").text()+"</option>";
	})
	ajaxSelect.append(ele);
}

function showInput(divId){
	if(divId != ''){
		jQuery("#"+divId).parents("div:first").find("div").hide();
		jQuery("#"+divId).show();
	}

}
function makeAutocomplete(instance){
	var params = {};
	
	if(jQuery(instance).hasClass("complete")){
		//console.debug("STO QUIIIIIIIIIIIIII makeauthocomplter");
		params = {
 			ajax_get : (function(param){return function(key, cont){getAjaxData(key, cont, param)}})(instance),
			callback: (function(param){return function(data){callback(data, param)}})(instance),
			minchars:1,
			cache:false,
			noresults:"nessuna chiave nel vocabolario"
		};
	}else if(jQuery(instance).hasClass("completestatic")){
		//console.debug("STO QUAAAAAAAAAAAAAAA");
		params = {
			ajax_get : (function(param){return function(key, cont){getAjaxData(key, cont, param)}})(instance),
			callback: (function(param){return function(data){callback(data, param)}})(instance),
			minchars:1,
			delay:1,
			timeout:2500,
	  		cache:true,
			noresults:"nessuna chiave nel vocabolario"
		}
	}
//	console.debug("FINO A QUIIIII");
	
	//jQuery(instance).autocomplete(params);
	jQuery(instance).autocomplete({
		ajax_get : (function(param){return function(key, cont){getAjaxData(key, cont, param)}})(instance),
		callback: (function(param){return function(data){callback(data, param)}})(instance),
		minchars:1,
		multi:true,
		multi_separators:[" and not ", " or not ", " o non ", " e non ", " and ", " or ", " o ", " e "],
		cache:false,
		noresults:"nessuna chiave nel vocabolario"
	});	
	
}

function writeNextInput(data, target){
	jQuery(target).nextAll("input:first").val(data.info);
}

var pre_callback_regexp = /^\s*[A-Za-z_]+\w*\s*\(\s*target\s*(?:(,\s*[A-Za-z_]+\w*\s*)|\s*)\)\s*jQuery/;
var pre_callback_regexp_simple = /^\s*[A-Za-z_]+\w*\s*jQuery/;
function pre_callback(target){
	if(jQuery(target).attr("pre_callback")){
		if(pre_callback_regexp_simple.test(jQuery(target).attr("pre_callback"))){
			eval(jQuery(target).attr("pre_callback") + "(target)");
		}else if(pre_callback_regexp.test(jQuery(target).attr("pre_callback"))){
			eval(jQuery(target).attr("pre_callback"));
		}else{
			alert("errore di sintassi nella chiamata della funzione pre_callback");
		}
	}
}

var callback_regexp = /^\s*[A-Za-z_]+\w*\s*\(\s*data\s*,\s*target\s*(?:(,\s*[A-Za-z_]+\w*\s*)|\s*)\)\s*jQuery/;
var callback_regexp_simple = /^\s*[A-Za-z_]+\w*\s*jQuery/;
function callback(data, target){
 
	if(jQuery(target).attr("callback")){
		if(callback_regexp_simple.test(jQuery(target).attr("callback"))){
			eval(jQuery(target).attr("callback") + "(data, target)");
		}else if(callback_regexp.test(jQuery(target).attr("callback"))){
			eval(jQuery(target).attr("callback"));
		}else{
			alert("errore di sintassi nella chiamata della funzione callback");
		}
	}
}

function addExtraParams(data, target){
	/*Cerca nell'attuale elemento input l'attributo 'attrfilter' che contiene una lista separata da virgole
	**di parametri che se definiti nell'input vengono passati al server; nel caso di non trovare attrfilter
	**nell'input lo cerca in tutti i padri fino a trovarlo o arrivare al nodo root.
	**parametri:
	**	data = un Object al quale vengono aggiunti i parametri
	**	target = riferimento all'input
	*/
	var validAttr = jQuery(target).attr("attrfilter") ? jQuery(target).attr("attrfilter") : jQuery(target).parents("[attrfilter]:first").attr("attrfilter");
	validAttr = validAttr ? validAttr.split(",") : [];
	
	var validAttrLower = jQuery.map( validAttr, function(v){return jQuery.trim(v.toLowerCase());});
	
	jQuery.each(target.attributes, function(){
		var idx = jQuery.inArray(this.name, validAttrLower);
		if(idx != -1) data[jQuery.trim(validAttr[idx])] = this.value;
	});
}

function getAjaxData(key, cont, target){
	/*Funzione che si occupa di fare la richiesta ajax al server e processare i dati restituiti in
	**modo che possano essere visualizati.
	**parametri:
	**	key = il testo inserito nell'input, che se esiste viene passato al server nel parametro 'q'
	**	cont = funzione di "continuita" passata dall'autocompliter alla quale chiamo passandoli i dati da visualizare
	**	target = l'oggetto input sul cui si st? lavorando
	*/
//	console.debug("sto quiiiiiiiiiii getAjaxData");
	var params = {};
	if(key != null) params["value"] = key;
	addExtraParams(params, target);
	var script_name = jQuery(target).attr("servlet") ? jQuery(target).attr("servlet") : jQuery(target).parents("[servlet]:first").attr("servlet"); //'http://localhost:8080/TestWeb/ServletTest';
    if(!script_name){
    	alert("attributo 'servlet' non trovato");
    	return false;
    }
    // console.debug("zaaaaaaaaaa");
    // console.debug("script_name "+script_name);
    // console.debug("params "+params);
    jQuery.getJSON(script_name,params,function(json){
    		var res = [];
			jQuery.each(json.items, function(i, data){
				res.push({ id:i, value:data.label, info:data.frequency, affected_value:data.value});
			});
			cont(res);
	});
 
//    $.getJSON("http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?",
//    		  {
//    		    tags: "cat",
//    		    tagmode: "any",
//    		    format: "json"
//    		  },
//    		  function(data) {
//    		    $.each(data.items, function(i,item){
//    		      $("<img/>").attr("src", item.media.m).appendTo("#images");
//    		      if ( i == 3 ) return false;
//    		    });
//    		  });
//    console.debug("sto quiiiiiiiiiii ");
}

function  xDamsMiniAlert(msg){
		jQuery.jGrowl(msg,{
			sticky: false,
			theme:  'manilla',
			header: 'info',
			speed:  'fast',
			life: 1000
		});
}
function  xDamsAlert(msg){
		jQuery.jGrowl(msg,{
			sticky: false,
			theme:  'manilla',
			header: 'Attenzione!',
			speed:  'slow',
			life: 4000
		});
}

function ajaxCallInfo(jqElem){
    var ajaxUrl = jqElem.parents("[id^='ele_']:first").attr("id");
    ajaxUrl = ajaxUrl.substr("ele_".length);
	ajaxUrl = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=infoDoc&numDoc=" + ajaxUrl;
	jqElem.attr("data-ajaxUrl", ajaxUrl);
	console.debug(ajaxUrl);
}

function setDocInfo(){
	$(".infotrigger").bind("contextmenu", function(event){
		    //console.debug($(this).qtip("api"));
		if(!$(this).qtip("api")){
			var numDoc = $(this).attr('data-numDoc');
			$(this).qtip({
				id: 'myTooltip',
				content: {
		 			text: '<img src="'+globalOption.frontPath + '/img/jquery/loading.gif" alt="Loading..." />',
		 			ajax: {
		 				url:  globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html",
		 				data: { "actionFlag": "infoDoc", "numDoc":  numDoc},
		 				success: function(data, status) {
		 					var selfCont = this;
		 					selfCont.set('content.text', data);
		 	 				$.get(globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html",{"actionFlag": "infoDoc", "numDoc":  numDoc, "calcButtons":"true"}, function(dataButtons){
		 	 					selfCont.set('content.text', data + " " + dataButtons);
		 	 				});
		 				}
		 			},
		 			title: {
						text: "Info Documento", 
						button: true
					}
		 		},
		 		show: {
		 			event: 'contextmenu',
		 			solo: true
		 		},
		 		hide: "unfocus",
		 		events: {
					render: function(event, api) {
						$(this).draggable();
					}
				}
			});
		
		}
		$(this).qtip("api").show();
		event.preventDefault();
		event.stopPropagation();
		
	});

	
//	$(".infotrigger").bind("contextmenu", function(event){
//		console.debug(event.which);
//		if(event.which == 3) {
//			$(this).qtip({
//				content: {
//					text: '<img src="'+globalOption.frontPath + '/img/jquery/loading.gif" alt="Loading..." />',
//					ajax: {
//						url: ajaxCallInfo($(this))
//					}
//				},		
//				position: {
//					target: 'mouse', // Position it where the click was...
//					adjust: { mouse: false } // ...but don't follow the mouse
//				}		 
//			});			
//		}
//
//	});

	

	// Little snippet that stops the regular right-click menu from appearing
	//.bind('contextmenu', function(){ return false; });
	
//	$(".infotrigger").mousedown(function(e) {
//	    if(e.which == 3) {
//			var ajaxUrl = $(this).parents("[id^='ele_']:first").attr("id");
//			ajaxUrl = ajaxUrl.substr("ele_".length);
//			ajaxUrl = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=infoDoc&numDoc=" + ajaxUrl;
//			$(this).attr("data-ajaxUrl",ajaxUrl);
//			$(this).qtip({
//				content: {
//					text: '<img src="images/loading.gif" alt="Loading..." />',
//					ajax: {
//						url: $(this).attr("data-ajaxUrl")
//					}
//				},
//				show: {
//					event: 'mousedown'
//				}
//			});
//			return false;
//		
//	    }
//	});

	
	
//	$(".infotrigger").each(function(){
//		var ajaxUrl = $(this).parents("[id^='ele_']:first").attr("id");
//		ajaxUrl = ajaxUrl.substr("ele_".length);
//		ajaxUrl = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=infoDoc&numDoc=" + ajaxUrl;
//		$(this).attr("data-ajaxUrl",ajaxUrl);
//		$(this).qtip({
//			content: {
//				text: '<img src="images/loading.gif" alt="Loading..." />',
//				show: {
//					event: 'contextmenu'
//				},
//				ajax: {
//					url: $(this).attr("data-ajaxUrl")
//				}
//			}
//		});
//		return false;
//	});
	
/*	
(function(jQueryj) {
	
	jQueryj(".infotrigger").bind("contextmenu",function(e){
			if(jQueryj(this).attr("isInfoOpen") === "true"){
				return false;
			}
			jQueryj(this).attr("isInfoOpen","true");
			var ajaxUrl = jQueryj(this).parents("[id^='ele_']:first").attr("id");
			ajaxUrl = ajaxUrl.substr("ele_".length);
			//ajaxUrl = "AjaxServlet?flagTipologia=infoDoc&numDoc=" + ajaxUrl;
			ajaxUrl = globalOption.contextPath+"/"+globalOption.theArch+"/ajax.html?actionFlag=infoDoc&numDoc=" + ajaxUrl;
			console.debug(ajaxUrl);
			//"${contextPath}/${workFlowBean.alias}"
			var newInfo = jQueryj("body").createAppend(
		  	"div", {'class':"jqmNotice"}, [
		       "div", {'class':"jqmnTitle jqDrag"},[
		       		"h1",["Info documento"]
		       ],
		       "div",  {'class':"jqmnContent"} , [
		       		"p",["Analisi del documento in corso... ","img",{src:globalOption.frontPath + "/img/jquery/loading.gif", alt:"caricamento"}],
		       ],
		        
		       "img",{'class':"jqmClose", src: globalOption.frontPath + "/img/jquery/close_icon.png", alt:"chiudi"},
		       "img",{'class':"jqResize", src: globalOption.frontPath + "/img/jquery/resize.gif", alt:"resize"}
			]).parents(".jqmNotice:first");
			// console.debug(globalOption.frontPath + "/img/jquery/close_icon.png");
			newInfo
			.jqDrag('.jqDrag')
		    .jqResize('.jqResize')
		    .jqm({
		      trigger:false,
		      ajax:ajaxUrl,
		      target:".jqmnContent",
		      overlay: 0,
		      onShow: (function(param){
		      	return function(h){
		      		(function(h,param) {
		      			h.w.createAppend(
		      				"div", {'class':"jqmnTitle"},[
					       		"h1",["Naviga"]
					       ],
					       "div", {'class':"navContainer"},[
					       		"p",["img",{src:globalOption.frontPath + "/img/jquery/loading.gif", alt:"caricamento"}]
					       ]
		      			);
		      			h.w.find(".navContainer").load(ajaxUrl + "&calcButtons=true");
		        		var cssprop = jQueryj(param).offset({scroll:false});
		        	//	cssprop["opacity"] = 0.92;
		        		cssprop.left += jQueryj(param).width();
		        		h.w.css(cssprop).fadeIn();
		      		})(h, param)
		      	}
		      })(this),
		      onHide: (function(param){
		      	return function(h){
		      		(function(h,param) {
		        		h.w.slideUp("slow",function() { if(h.o) h.o.remove(); h.w.remove(); jQueryj(param).removeAttr("isInfoOpen")});
		      		})(h, param)
		      	}
		      })(this),
		      onLoad: (function(param){ 
		      	return function(h){
		      		(function(h,docHeight) {
		      			var myOffset = h.w.offset({scroll:false});
								var bottomY = myOffset.top + h.w.height();
								if(bottomY > docHeight){
									h.w.css("top", myOffset.top - ((bottomY - docHeight) + 5));
								}
							})(h, param)
		      	}
		      	return true;
		      })(jQueryj(document).height())
		    });
		    
		   newInfo.jqmShow();
		   
		   return false;
		}) 
		
		
	jQueryj(".actiontrigger_DISABLED").bind("contextmenu",function(e){
			if(jQueryj(this).attr("isInfoOpen") === "true"){
				return false;
			}
			jQueryj(this).attr("isInfoOpen","true");
			var ajaxUrl = jQueryj(this).parents("[id^='ele_']:first").attr("id");
			ajaxUrl = ajaxUrl.substr("ele_".length);
			ajaxUrl = "ServletView?go=schedaBtt.jsp&physDoc=" + ajaxUrl; 		 
			var newInfo = jQueryj("body").createAppend(
		  	"div", {'class':"jqmNotice"}, [
		       "div", {'class':"jqmnTitle jqDrag"},[
		       		"h1",["Azioni disponibili"]
		       ],
		       "div",  {'class':"jqmnContent"} , [
		       		"p",["Analisi del documento in corso... ","img",{src:globalOption.frontPath + "/img/jquery/loading.gif", alt:"caricamento"}],
		       ],
		       
		       "img",{'class':"jqmClose", src:globalOption.frontPath + "/img/jquery/close_icon.png", alt:"chiudi"},
		       "img",{'class':"jqResize", src:globalOption.frontPath + "/img/jquery/resize.gif", alt:"resize"}
			]).parents(".jqmNotice:first");
			newInfo.jqDrag('.jqDrag').jqResize('.jqResize').jqm({
		      trigger:false,
		      
		      ajax:ajaxUrl,
		      target:".jqmnContent",
		      overlay: 0,
		      onShow: (function(param){
		      	return function(h){
		      		(function(h,param) {
		      			      			 
		      			
		        		var cssprop = jQueryj(param).offset({scroll:false});
		        	//	cssprop["opacity"] = 0.92;
		        		//cssprop.left += jQueryj(param).width();
		        		h.w.css("left",100);
		        		h.w.css(cssprop).fadeIn();
		      		})(h, param);
		      	};
		      })(this),
		      onHide: (function(param){
		      	return function(h){
		      		(function(h,param) {
		        		h.w.slideUp("slow",function() { if(h.o) h.o.remove(); h.w.remove(); jQueryj(param).removeAttr("isInfoOpen");});
		      		})(h, param);
		      	};
		      })(this),
		      onLoad: (function(param){ 
		      	return function(h){
		      		(function(h,docHeight) {
		      			var myOffset = h.w.offset({scroll:false});
								var bottomY = myOffset.top + h.w.height();
								if(bottomY > docHeight){
									h.w.css("top", myOffset.top - ((bottomY - docHeight) + 5));
								}
							})(h, param);
		      	};
		      	return true;
		      })(jQueryj(document).height())
		    });
		   newInfo.jqmShow();
		   return false;
		});
	}	
	)(jQuery);
*/
}
function xDamsUnblock(){
	jQuery.unblockUI();
}
function xDamsModalAlert(msg){
	jQuery.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yesAlert" value=" [ ok ]" /></div> ', css: { height:'100px',width: '275px' } }); 
	jQuery('#yesAlert').click(function() {xDamsUnblock();return true;}); 
}

function xDamsModalMessageParam(msg,height,width){jQuery.blockUI({ message: '<h1 class="msg"><img src="'+globalOption.frontPath+'/img/busy.gif" />'+msg+'</h1>', css: { height:height,width: width } });}

function xDamsModalMessage(msg){jQuery.blockUI({ message: '<h1 class="msg"><img src="'+globalOption.frontPath+'/img/busy.gif" />'+msg+'</h1>', css: { height:'40px',width: '180px' } });}
function xDamsModalConfirm(msg){
jQuery.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yesConfirm" value=" [ si ]" />&#160;&#160;&#160;<input type="button" id="noConfirm" value=" [ no ]" /> </div> ', css: { height:'100px',width: '275px' } }); 
jQuery('#yesConfirm').click(function() {xDamsUnblock();return true;}); 
jQuery('#noConfirm').click(function() {xDamsUnblock();return false;});
}
function fillZero(param,lenghtZero){
var result = param;
if(result != null && result!=("") && result.length < lenghtZero){
    for(; result.length < lenghtZero; result = "0" + result) { }
}
return result;
}
 
function addCookie(valueToWrite) {
	  $.cookie(globalOption.pageValue, valueToWrite);
}



