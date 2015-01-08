<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLValControllati = confBean.getTheXMLValControllati();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);
%>
<html>
<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('multiMod','${frontUrl}');
</script>
<script type="text/javascript">
//<!--
	$(document).ready(function(){
		$(".hiddenElement").hide();
		xDamsUnblock();
		$("#theXpath").change(function(){
			var valueMaster = $(this).find("option:selected").attr("theID");
			var theSelect = $("#"+valueMaster);
			$(".hiddenElement").hide();
			if(theSelect.attr("id") === undefined){
				$("#theValue").show();
				$("#theValue").attr("name","theValue");
				//alert("un input"+valueMaster);
			          $("select").each(function () {
			             if($(this).attr('id')!='theXpath'){
			              $(this).attr('name','');
			             }
			          });
			}else{
				$("#theValue").hide();
				$("#theValue").attr("name","EXtheValue");
				theSelect.attr("name","theValue");
			          $("select").each(function () {
			             if($(this).attr('id')!=theSelect.attr('id') && $(this).attr('id')!='theXpath'){
			              $(this).attr('name','');
			             }
			          });				
				theSelect.show();
				//alert("una select");
			}
		});
		$("form[name='theForm02'] input[name='applyTo']").change(function() {
			  if($(this).val()=='hier'){
				  $("#countBlock").show();
			  }else{
				  $("#countBlock").hide();
				  $("input[name='countBlock']").attr('checked', false);
		      }
		});		
		
	});
 	function modAvanzata(theInput){
		theObj = document.getElementById(theInput);
		theObjValue = theObj.value;
		theObjName = theObj.name;
		theObj.parentNode.innerHTML="<input class=\"long\" name=\""+theObjName+"\" value=\""+theObjValue+"\">";
		return true;
	}		
	function impostaAndGo(theForm){
		ilval="";
		typeHtml="";
		for(var i=0;i<theForm.length;i++){
			if(theForm.elements[i].name=='theValue'){
				ilval = theForm.elements[i].value;
				typeHtml = theForm.elements[i].type;
				//alert("indice ["+i+"]"+theForm.elements[i].style.display);
				//alert("indicea ["+i+"]"+theForm.elements[i].type);
				//alert("indiceas ["+i+"]"+theForm.elements[i].name);
				
			}
		}
		 //alert(typeHtml);
		selectedRadio = false;
		for(var i=0;i<theForm.length;i++){
			//alert(theForm.elements[i].name +"="+ theForm.elements[i].value);
			if(theForm.elements[i].checked)selectedRadio=true;
		}
		//return false;
		if(!selectedRadio){
				xDamsModalAlert('<spring:message code="Specificare_unazione" text="Specificare un\\'azione"/>!<br /><br />');
				return;		
		}
		
		if(theForm.theXpath != null && (ilval=="" || theForm.theXpath.value=="")){
				if(typeHtml.indexOf('select')!=-1){
					msg = "<spring:message code="Attenzione" text="Attenzione"/>!<br /><br /><spring:message code="vuoi_proiettare_un_valore_vuoto" text="vuoi proiettare un valore vuoto"/>? <br />";
					$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
				      $('#yes').click(function() {	
							 theForm.submit();
							 xDamsModalMessage('<spring:message code="elaborazione_in_corso" text="elaborazione in corso"/>...');
				       }); 
				       $('#no').click(function(){ 
						  xDamsUnblock();
						  return false;
					  });					
				}else{
					xDamsModalAlert('<spring:message code="Specificare_valore_e_xpath" text="Specificare valore e xpath"/>!<br /><br />');
					return;
				}
		}
		 else  {
				msg = "<spring:message code="Attenzione" text="Attenzione"/>!<br /><br /><spring:message code="Confermi_la_modifica" text="Confermi la modifica"/>? <br />";
				$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
			      $('#yes').click(function() {	
						 theForm.submit();
						 xDamsModalMessage('<spring:message code="elaborazione_in_corso" text="elaborazione in corso"/>...');
			       }); 
			       $('#no').click(function(){ 
					  xDamsUnblock();
					  return false;
				  });			 
		 }
			return false;
		}
		function proiettaModifica(sblocca){
			if(sblocca){self.close();}else{impostaAndGo(document.theForm);}
			return false;
		}
		
		function proiettaRinumera(sblocca){
			if(sblocca){self.close();}else{impostaAndGo(document.theForm02);}
			return false;
		}		
		//-->
		</script>
	</head>
<body>
<script type="text/javascript">xDamsModalMessage('<spring:message code="analisi_in_corso" text="analisi in corso"/>...');</script>
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="multiMod" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div class="m10">
<strong><spring:message code="ASSEGNA_VALORE" text="ASSEGNA VALORE"/></strong>, <spring:message code="applica_la_modifica_a" text="applica la modifica a"/>:
<br />
<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected"> <strong><spring:message code="selezione_multipla" text="selezione multipla"/></strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if(( managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid"> <strong><spring:message code="ricerca_corrente" text="ricerca corrente"/></strong> (<%=( managingBean.getNumElementi())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong><strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br /><%} %>
<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br /><%} %>
<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong><spring:message code="figli" text="figli"/></strong> (<%=(managingBean.getNumElementiSons())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier"> <strong><spring:message code="tutto_il_ramo" text="tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<br /> 
	<% 
	String externalPath = "/root/docEdit/macroarea";
	String ilNome ="";
	int nodiMacroAree = theXMLconf.contaNodi(externalPath);
	String theOptions = "<option value=\"\">"+workFlowBean.getLocalizedString("scegli", "scegli")+"...</option>";
  	ArrayList theOptionsValContr = new ArrayList();
	for (int i = 0; i < nodiMacroAree; i++) {
		externalPath = "/root/docEdit/macroarea["+(i+1)+"]/sezione";
		int nodiMacroSezioni = theXMLconf.contaNodi(externalPath);
		if(!theXMLconf.valoreNodo(externalPath+"/@name").equals(""))
			ilNome = theXMLconf.valoreNodo(externalPath+"/@name");
		for (int a = 0; a < nodiMacroSezioni; a++) {
			externalPath = "/root/docEdit/macroarea["+(i+1)+"]/sezione["+(a+1)+"]/elemento[@multiMod='true']";
			int nodiOpzioni = theXMLconf.contaNodi(externalPath);
			for (int z = 0; z < nodiOpzioni; z++) {
 				if(!theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/@name").equals("")){
					ilNome = theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/@name");
				}
				if(!theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/text()").equals("")){
					if(theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/@input_type").equals("select")){
		 				String valContrExtPath = theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/@externalPath");
						int countOpt = theXMLValControllati.contaNodi(valContrExtPath);
						ArrayList arrayList = new ArrayList();
						if(countOpt>0){
							arrayList.add("<select id=\""+URLEncoder.encode(theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/text()"),"UTF-8").replaceAll("[%+]","")+"\" class=\"long hiddenElement\">");
						}
						for (int kk = 0; kk < countOpt; kk++) {
							/*
							out.println("select"+countOpt);
							out.println("select");
							out.println("select"+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/text()"));
							out.println("select");
							out.println("select");
							out.println("select");	
							*/
							arrayList.add("<option value=\""+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/@value")+"\">"+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/text()")+"</option>");
						}
						if(countOpt>0){
							arrayList.add("</select>");
						}
						theOptionsValContr.add(arrayList);
 					}					
 					theOptions += "<option value=\""+theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/text()")+"\" theID=\""+URLEncoder.encode(theXMLconf.valoreNodo(externalPath+"["+(z+1)+"]/text()"),"UTF-8").replaceAll("[%+]","")+"\">"+ilNome+"</option>";
 				}
				String externalPathSub = externalPath+"["+(z+1)+"]/elemento[@multiMod='true']";
				int nodiOpzioniSub = theXMLconf.contaNodi(externalPathSub);
				String nomeSup = ilNome;
				for (int x = 0; x < nodiOpzioniSub; x++) {
					if(!theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/@name").equals(""))
						ilNome = theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/@name");
					ilNome = nomeSup+ " "+ilNome;
					ilNome = ilNome.trim();
					if(theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/@input_type").equals("select")){
						String valContrExtPath = theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/@externalPath");
						int countOpt = theXMLValControllati.contaNodi(valContrExtPath);
						ArrayList arrayList = new ArrayList();
						if(countOpt>0){
							arrayList.add("<select id=\""+URLEncoder.encode(theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/text()"),"UTF-8").replaceAll("[%+]","")+"\" class=\"long hiddenElement\">");
						}
						for (int kk = 0; kk < countOpt; kk++) {
							/*
							out.println("select"+countOpt);
							out.println("select");
							out.println("select"+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/text()"));
							out.println("select");
							out.println("select");
							out.println("select");	
							*/
							arrayList.add("<option value=\""+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/@value")+"\">"+theXMLValControllati.valoreNodo(valContrExtPath+"["+(kk+1)+"]/text()")+"</option>");
//							theOptions += "<option value=\""+theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/text()")+"\">"+ilNome+"</option>";
						}
						if(countOpt>0){
							arrayList.add("</select>");
						}
						if(!theOptionsValContr.contains(arrayList)){
							theOptionsValContr.add(arrayList);
						}
 					}
					theOptions += "<option value=\""+theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/text()")+"\" theID=\""+URLEncoder.encode(theXMLconf.valoreNodo(externalPathSub+"["+(x+1)+"]/text()"),"UTF-8").replaceAll("[%+]","")+"\">"+ilNome+"</option>";
				}
			}
		}
	}
%>
		valore:<br /> 
		<input type="text" id="theValue" name="theValue" class="long" /> 
		<%
			for(int xx=0;xx<theOptionsValContr.size();xx++){
				ArrayList arrayList = (ArrayList)theOptionsValContr.get(xx);
 				for(int jj=0;jj<arrayList.size();jj++){%>
					<%=(String)arrayList.get(jj) %>
				<%}
 			}
		%>		
		<br /><br />
		<spring:message code="proietta_su" text="proietta su"/>:<br /> 
		<span><select id="theXpath" name="theXpath" class="long"><%=theOptions%></select><br /><a href="javascript:void(0)"  class="link"  onclick="return modAvanzata('theXpath')"><spring:message code="modalita_avanzata" text="modalità avanzata"/> <img src="${frontUrl}/img/arrow.gif" border="0"/></a></span>
		<br />
	    <a title="<spring:message code="ESEGUI" text="ESEGUI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a>
<br /><br />
</div>
</form>
<%
String codiceId=theXMLconf.valoreNodo("/root/param/elemento[@id='codice_identificativo']/text()");	
 if(!codiceId.equals("")){
	 try{
		 XMLBuilder selectedDoc  = new XMLBuilder(managingBean.getDocXML(),"iso-8859-1");
%>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm02">
<input type="hidden" name="actionFlag" value="multiModRenumber" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<input type="hidden" name="codiceId" value="<%=codiceId%>" />
<div class="m10">
<strong><spring:message code="RINUMERA_ELEMENTI" text="RINUMERA ELEMENTI"/></strong>, <spring:message code="applica_la_modifica_a" text="applica la modifica a"/>:
<br />
<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br /><%} %>
<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br /><%} %>
<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong><spring:message code="sons" text="figli diretti"/></strong> (<%=(managingBean.getNumElementiSons())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier"> <strong><spring:message code="tutto_il_ramo" text="tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<br /> 
			<spring:message code="a_partire_da" text="a partire da"/>:<br /> 
			<input type="text" name="theValue" class="long" value="<%=selectedDoc.valoreNodo(codiceId) %>" /> <br />
			<spring:message code="numero_di_cifre_per_blocco" text="numero di cifre per blocco"/>:<br />
			<input type="text" name="numZeri" class="short" value="5" /><br />
			<div style="display:none" id="countBlock"><spring:message code="contrassegna_blocchi_in_partenza" text="contrassegna blocchi in partenza"/>: <input type="checkbox" name="countBlock" /></div>
	 	<br />
	    <a title="<spring:message code="ESEGUI" text="ESEGUI"/>" class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaRinumera(false);"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a>
</div>
</form>
<%}catch(Exception e){}
 }%>
</div>
<div id="foot">
	<div class="margin_foot">
 	<div class="cont_ul2">	
		<ul class="bottoniMenu" >
 			<li><a title="<spring:message code="CHIUDI" text="CHIUDI"/>" class="bottoneLink" onmouseover="window.status='<spring:message code="CHIUDI" text="CHIUDI"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#"><spring:message code="CHIUDI" text="CHIUDI"/></a></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>