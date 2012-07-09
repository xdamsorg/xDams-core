		function lookupCronoEventiSpecial(){
		resetFormLookup();
		var db_name = "";
		var inputSort = "";
		var inputStrQuery = "";
		var campiDaValorizzare = "";
			for (var i = 0; i < arguments.length; i++){
			     //alert(arguments[i]);
			    if(i==0){
			    	db_name = arguments[i];
			    }if (i==1){
					inputSort = arguments[i];
			    }if(i==2){
				    inputStrQuery = arguments[i];
			    }if(i >= 3){
		   	 		campiDaValorizzare += arguments[i]+"','";
			    }
			}			
			campiDaValorizzare = "'" + campiDaValorizzare;
			campiDaValorizzare = campiDaValorizzare.substring(0,campiDaValorizzare.length-2);
//			alert("db_name "+db_name);
//			alert("campiDaValorizzare "+campiDaValorizzare);
//			alert("inputSort "+inputSort);
//			alert("inputStrQuery "+inputStrQuery);
//			alert("document.theForm[inputStrQuery] "+document.theForm[inputStrQuery]);
			campoData = document.theForm[inputStrQuery].value;
//			alert("campoData "+campoData);
			
			if(campoData.length==10){
				campoDataArr = campoData.split("/");
				/*
				 alert("campoDataArr[2] "+campoDataArr[2]);
				 alert("campoDataArr[1] "+campoDataArr[1]);
				 alert("campoDataArr[0] "+campoDataArr[0]);
				*/
				campoData = (campoDataArr[2] + campoDataArr[1] + campoDataArr[0])+"-"+(campoDataArr[2] + campoDataArr[1] + campoDataArr[0]);
			}
			
			
			//alert("campoData "+campoData);
			//campoData = '{'+campoData+'^}';
			document.lookupMainSpecial.campiDaValorizzare.value = campiDaValorizzare
			document.lookupMainSpecial.db_name.value = db_name;
			document.lookupMainSpecial.db_framework.value = "xw/"+db_name;
			document.lookupMainSpecial.inputUdType.value = "crono_eventi";
			document.lookupMainSpecial.inputQuery.value = "[XML,/crono_eventi/datazione/data/@normal]";
			document.lookupMainSpecial.theWebApp.value = "damCronoEventi";
			document.lookupMainSpecial.jspOutPut.value = "/custom/crono-eventi/lookupSpecialCronoEventi.jsp";
			document.lookupMainSpecial.inputSort.value = inputSort
			var regex = /\"/gi;
			document.lookupMainSpecial.inputStrQuery.value= (campoData).replace(regex,'');
			
			document.lookupMainSpecial.flagXML.value = 'true';
			
			window.open('','lookupWindow','status=yes,resizable=yes,scrollbars=yes,width=400, height=450');
			document.lookupMainSpecial.target = "lookupWindow"
			document.lookupMainSpecial.submit();
			document.lookupMainSpecial.jspOutPut.value="/public/application/jsp/lookup.jsp";
			document.lookupMainSpecial.flagXML.value="false";
		}
		
		function advOpenClipBoard(){
		   laPopupClip = window.open('','laPopupClip','width=450,height=400,toolbar=0,status=0,scrollbars=yes,resizable=yes');
		   document.clipForm.clipboard_user.value='<%=StringEscapeUtils.escapeJavaScript(userBean.getUserName())%>';
		   document.clipForm.target_archive.value=arguments[0];
		   document.clipForm.target_id.value=arguments[1];
		   document.clipForm.target_text.value=arguments[2];
		   document.clipForm.target_http.value=arguments[3];
		   document.clipForm.span_id.value="";
		   document.clipForm.target="laPopupClip";
		   document.clipForm.submit();
		}

		function lookupTitoliSpecial(){
		resetFormLookup();
		var db_name = "";
		var inputSort = "";
		var inputStrQuery = "";
		var inputExtraQuery = "";
		var campiDaValorizzare = "";
			for (var i = 0; i < arguments.length; i++){
			     //alert(arguments[i]);
			    if(i==0){
			    	db_name = arguments[i];
			    }if (i==1){
					inputSort = arguments[i];
			    }if(i==2){
				    inputStrQuery = arguments[i];
			    }if(i==3){
				    inputExtraQuery = arguments[i];
			    }if(i >= 4){
		   	 	    campiDaValorizzare += arguments[i]+"','";
			    }
			}			
			campiDaValorizzare = "'" + campiDaValorizzare;
			campiDaValorizzare = campiDaValorizzare.substring(0,campiDaValorizzare.length-2);
// 			alert("db_name "+db_name);
// 			alert("inputSort "+inputSort);
// 			alert("inputStrQuery "+inputStrQuery);
// 			alert("inputExtraQuery "+inputExtraQuery);
// 			alert("campiDaValorizzare "+campiDaValorizzare);
			campoExtra = document.theForm[inputExtraQuery];
// 			alert("campoExtra "+campoExtra);

			if(campoExtra!=null && campoExtra!=""){
				campoExtra = campoExtra.value;
			}
 
//	 		alert("campoExtra "+campoExtra);
			//campoData = '{'+campoData+'^}';
			document.lookupMainSpecial.campiDaValorizzare.value = campiDaValorizzare
			document.lookupMainSpecial.db_name.value = db_name;
			document.lookupMainSpecial.db_framework.value = "xw/"+db_name;
			document.lookupMainSpecial.inputUdType.value = "eac";
			if(campoExtra!=null && campoExtra!=""){
				document.lookupMainSpecial.inputExtraQuery.value = "[XML,/eac/condesc/titledesc/descentry/descnote/persname/@authfilenumber]="+campoExtra;
			}
			
			document.lookupMainSpecial.inputQuery.value = "[XML,/eac/condesc/identity/titlehead/part_titolo_accademia]";
			valoreInputQuery = document.theForm[inputStrQuery].value;
			
			document.lookupMainSpecial.theWebApp.value = "";
			document.lookupMainSpecial.jspOutPut.value = "/custom/crono-eventi/lookupTitoliEventi.jsp";
			document.lookupMainSpecial.inputSort.value = inputSort
			var regex = /\"/gi;
			document.lookupMainSpecial.inputStrQuery.value= (valoreInputQuery).replace(regex,'');
			
			document.lookupMainSpecial.flagXML.value = 'true';
			
			window.open('','lookupWindow','status=yes,resizable=yes,scrollbars=yes,width=400, height=450');
			document.lookupMainSpecial.target = "lookupWindow"
			document.lookupMainSpecial.submit();
			document.lookupMainSpecial.jspOutPut.value="/public/application/jsp/lookup.jsp";
			document.lookupMainSpecial.flagXML.value="false";
			
		}
		
		function resetFormLookup(){
			document.lookupMainSpecial.db_framework.value = '';
			document.lookupMainSpecial.theWebApp.value = '';
			document.lookupMainSpecial.theDb.value = '<%=theArch%>';
			document.lookupMainSpecial.db_name.value = '';
			document.lookupMainSpecial.inputQuery.value = '';
			document.lookupMainSpecial.inputExtraQuery.value = '';
			document.lookupMainSpecial.inputSort.value = '';
			document.lookupMainSpecial.inputTitleRule.value = '';
			document.lookupMainSpecial.inputUdType.value = '';
			document.lookupMainSpecial.inputPerPage.value = '<%=keyCountLookUp%>';
			document.lookupMainSpecial.inputStrQuery.value = '';
			document.lookupMainSpecial.jspOutPut.value = '/custom/lookupSpecial.jsp';
			document.lookupMainSpecial.db_name_orig.value = '<%=theArch%>';
			document.lookupMainSpecial.destSpan.value = '';
			document.lookupMainSpecial.flagCheckRadio.value = '';
			document.lookupMainSpecial.campiDaValorizzare.value = '';
			document.lookupMainSpecial.titleRule.value = '';
			document.lookupMainSpecial.extraField.value = '';
			document.lookupMainSpecial.flagXML.value = 'false';
			
		}
		
		