<%@page import="org.xdams.adv.configuration.Element"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.xdams.adv.utility.MappingAdv"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.xdams.adv.configuration.ConfigurationXMLReader"%>
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

XMLBuilder theXMLConfManaging = confBean.getTheXMLConfManaging();
XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
 TitleManager titleManager = new TitleManager(theXMLconfTitle);

 
// System.out.println(theXMLConfManaging.getXML("ISO-8859-1"));
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
function trim(stringa){
    while (stringa.substring(0,1) == ' ' || stringa.substring(0,1) == '\n'){
        stringa = stringa.substring(1, stringa.length);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == ' ' || stringa.substring(stringa.length-1, stringa.length) == '\n'){
        stringa = stringa.substring(0,stringa.length-1);
    }
    
    return stringa;
}


$(document).ready(function(){
 	xDamsUnblock();
    	$("#findWhat").keypress(function(e){
    		//alert(String.fromCharCode(e.which));
    		//var regEx = "(/\/-!"#$%&'()*,.\/:;<=>?@[\\\]_`{|}~\//)";	
      		if(containsPunctuation(e,$(this).val())){
      			$("#whole_word").attr('checked',false);  			
      		}else{
	      		$("#whole_word").attr('checked',true);
      		}
		});
 		$("#whole_word").click(function () { 
 		    
			if(containsPunctuation(null,$("#findWhat").val())){
				
				xDamsModalAlert('<spring:message code="Attenzione_il_campo_trova_contiene_della_punteggiatura_che_non_permette_di_utilizzare_il_parametro_Parola_intera" text="Attenzione il campo trova contiene della punteggiatura che non permette di utilizzare il parametro \"Parola intera\""/>!<br /><br />');
			}
        });
		
		
});


function proiettaModifica(sblocca){
	if(sblocca){self.close();}else{impostaAndGo(document.theForm);}
	return false;
}

function containsPunctuation(EWhich,thisValue){
	try{
	      	if(/[\-!"#$%&'()*+,.\/:;<=>?@[\\\]_`{|}~]/.test(String.fromCharCode(EWhich.which)) || (/[\-!"#$%&'()*+,.\/:;<=>?@[\\\]_`{|}~]/.test(thisValue))){
	    		return true;
	    	}else{
	    		return false;     		
	     	}
	}catch(exx){
	      	if((/[\-!"#$%&'()*+,.\/:;<=>?@[\\\]_`{|}~]/.test(thisValue))){
	    		return true;
	    	}else{
	    		return false;     		
	     	}
	}
}




function impostaAndGo(msg){
var applyToRadio = false;
var theXpathRadio = false;

	 for(var i=0;i<document.theForm.length;i++){
		if(document.theForm.elements[i].name=='applyTo' && document.theForm.elements[i].checked){
		   applyToRadio=true;
		   break;
		}
	 } 
	for(var i=0;i<document.theForm.length;i++){
		if((document.theForm.elements[i].name)=='theDeep' && document.theForm.elements[i].checked){
		   theXpathRadio=true;
		   break;
		}
	}
	
//	alert("applyToRadio "+applyToRadio);
//	alert("theXpathRadio "+theXpathRadio);	

	if(!applyToRadio){
		xDamsModalAlert('<spring:message code="Specificare_unazione" text="Specificare un\\\'azione"/>!<br /><br />');
		return;		
	}
	if(!theXpathRadio){
		xDamsModalAlert('<spring:message code="Specificare_una_sezione" text="Specificare una sezione"/>!<br /><br />');
		return;		
	}
	if(trim(document.theForm['findWhat'].value)==''){
	 	xDamsModalAlert('<spring:message code="Specificare_almeno_una_parola_di_ricerca" text="Specificare almeno una parola di ricerca"/>!<br /><br />');
		return;	
	} 
	if(trim(document.theForm['replaceWith'].value)==''){
	 	xDamsModalAlert('<spring:message code="Specificare_almeno_una_parola_per_la_sostituzione" text="Specificare almeno una parola per la sostituzione"/>!<br /><br />');
		return;		 
	}
	if(containsPunctuation(null,$("#findWhat").val()) && $("#whole_word").attr('checked')){
		xDamsModalAlert('<spring:message code="Attenzione_il_campo_trova_contiene_della_punteggiatura_che_non_permette_di_utilizzare_il_parametro_Parola_intera" text="Attenzione il campo trova contiene della punteggiatura che non permette di utilizzare il parametro \"Parola intera\""/>!<br /><br />');
		$("#whole_word").attr('checked',false); 
		return;		 
	}	
 	if(confirm('Confermi la sostituzione?')){
	 	 document.theForm.submit();
		 xDamsModalMessage('elaborazione in corso...');
	}

	return false;
}
</script>
</head>
<body onload="self.focus()">
<script type="text/javascript">xDamsModalMessage('<spring:message code="analisi_in_corso" text="analisi in corso"/>...');</script>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="findReplace" />
<input type="hidden" name="action" value="executeFindReplace" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
 	<div class="m10">
	<%if((managingBean.getPhysDoc())>0){ %><input type="radio" name="applyTo" value="thisDocument"> <strong><spring:message code="elemento_selezionato" text="elemento selezionato"/></strong> <!-- (<%=(managingBean.getPhysDoc())%>) --><br /><%} %>
	<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected"> <strong><spring:message code="selezione_multipla" text="seleziona multipla"/> </strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="elementi" text="elementi"/>)<br /><%} %>
	<%if((managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid"> <strong><spring:message code="ricerca_corrente" text="ricerca corrente"/></strong> (<%=( managingBean.getNumElementi())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
	<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br /><%} %>
	<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br /><%} %>
	<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong><spring:message code="figli_diretti" text="figli diretti"/></strong> (<%=(managingBean.getNumElementiSons())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
	<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier"> <strong><spring:message code="tutto_il_ramo" text="tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
	<div>&#160;</div>
	<div>Trova: </div>
	<div><input type="text" name="findWhat" value="" class="long" id="findWhat" /></div>
	<div>Sostituisci: </div>
	<div><input type="text" name="replaceWith" value="" class="long"/></div>
	<div>&#160;</div>
	<div><input type="checkbox" name="caseSensitive" /><spring:message code="Maiuscolo_minuscolo" text="Maiuscolo/minuscolo"/></div>
	<div><input type="checkbox" name="whole_word"  id="whole_word" checked="checked" /><spring:message code="Parola_intera" text="Parola intera"/></div>
	<div>&#160;</div>
	
	<div>scegli la sezione del documento xml da modificare: </div>
	<div>
	<%
	ConfigurationXMLReader configurationXMLReader = new ConfigurationXMLReader(theXMLConfManaging);
	Map<String, String> filters = new HashMap<String, String>();
	filters.put("multiMod", "true");
	MappingAdv mappingAdv = new MappingAdv();
	List<Element> arrayList = mappingAdv.extractMapping(configurationXMLReader.getObjects(), null, filters);
	int count = 0;
	for (Element object : arrayList) {
		%>
		<div><input type="checkbox" name="theDeep" value="<%=object.getDeep()%>"><strong><%=object.getName()%></strong></div>
		<div><em><%=object.getText()%></em></div>
		<%
		count++;
	}
 	%>
	</div>
	</div>
</div>	
</form>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<li><a title="<spring:message code="ESEGUI" text="ESEGUI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a></li>
	 			<li><a title="<spring:message code="chiudi" text="chiudi"/>" class="bottoneLink" onmouseover="window.status='<spring:message code="chiudi" text="chiudi"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#"><spring:message code="chiudi" text="chiudi"/></a></li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>







