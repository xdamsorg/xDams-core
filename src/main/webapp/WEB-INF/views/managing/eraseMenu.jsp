<%@page import="org.xdams.utility.bind.BindUtil"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
 	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
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
loadJsBusiness('eraseMenu','${frontUrl}');
$(document).ready(function(){
 	xDamsUnblock();
});
function impostaAndGo(msg){
      $.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} }); 
      $('#yes').click(function() {	
	      msg = '<spring:message code="Attenzione" text="Attenzione"/>!<br /><br /><spring:message code="stai_per_essere_effettuata_la_cancellazione_dei_documenti" text="stai per essere effettuata la cancellazione dei documenti"/> <br /><spring:message code="sicuro" text="sicuro"/>?';
	      $.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} }); 
	      $('#yes').click(function() {	
				 document.theForm.submit();
				 xDamsModalMessage('<spring:message code="cancellazione_in_corso" text="cancellazione in corso"/>...');
	       }); 
	       $('#no').click(function(){ 
			  xDamsUnblock();
			  return false;
		   }); 		
	  
	         }); 
       $('#no').click(function(){ 
		  xDamsUnblock();
		  return false;
	  }); 		
		return false;
}
		
function proiettaModifica(){
	selectedRadio = false;
	msg = "<spring:message code="Attenzione" text="Attenzione"/>!<br /><br /><spring:message code="stai_per_essere_effettuata_la_cancellazione_dei_documenti" text="stai per essere effettuata la cancellazione dei documenti"/> <br />";
	for(var i=0;i<document.theForm.length;i++){
		if(document.theForm.elements[i].checked){
		selectedRadio=true;
		msg+=jQuery(document.theForm.elements[i]).attr("rel");
		}
	}
	msg +="...<br /><br /> <spring:message code="il_processo_e_IRREVERSIBILE" text="il processo è IRREVERSIBILE"/>!";
	if(!selectedRadio){
		xDamsModalAlert('<spring:message code="Specificare_unazione" text="Specificare un\\'azione"/>!<br /><br />');
		return;		
	}
	impostaAndGo(msg);
	return false;
}
</script>
</head>
<body onload="self.focus()">
<script type="text/javascript">xDamsModalMessage('<spring:message code="analisi_in_corso" text="analisi in corso"/>...');</script>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="erase" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div id="STATO" class="statusLayerNONVisibile"><spring:message code="Attendere" text="Attendere"/>: <strong><spring:message code="eliminazione_record_in_corso" text="eliminazione record in corso"/>...</strong></div>
<div id="content_multi">
	<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
	<div class="m10">
	<div><spring:message code="elimina" text="elimina"/>:</div>
	<div ><br />
		<input type="radio" name="applyTo" value="hier" rel="<spring:message code="della_scheda_selezionata" text="della scheda selezionata"/><%if(managingBean.getNumElementiHier()>1){%> <spring:message code="edi_TUTTI_i" text="e di TUTTI i"/> <%=(managingBean.getNumElementiHier())-1%> <spring:message code="elementi_gerarchicamente_collegati" text="elementi gerarchicamente collegati"/><%} %>"> <strong><spring:message code="scheda_selezionata_e_tutto_il_ramo" text="scheda selezionata e tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><br />
		<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected" rel="<spring:message code="di" text="di"/> <%=(managingBean.getListPhysDoc()).size()%> <spring:message code="schede_selezionate_e_di_TUTTI_gli_elementi_gerarchicamente_collegati" text="schede selezionate e di TUTTI gli elementi gerarchicamente collegati"/>"> <strong><spring:message code="selezione_multipla" text="selezione multipla"/></strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="schede_e_tutti_gli_elementi_gerarchicamente_collegati" text="schede e tutti gli elementi gerarchicamente collegati"/>)<br /><%} %>
		<%if((managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid" rel="<spring:message code="di" text="di"/> <%=( managingBean.getNumElementi())%> <spring:message code="schede_selezionate_e_di_TUTTI_gli_elementi_gerarchicamente_collegati" text="schede selezionate e di TUTTI gli elementi gerarchicamente collegati"/>"> <strong><spring:message code="ricerca_corrente" text="ricerca corrente"/></strong> (<%=( managingBean.getNumElementi())%> <spring:message code="schede_e_tutti_gli_elementi_gerarchicamente_collegati" text="schede e tutti gli elementi gerarchicamente collegati"/>)<br /><%} %>
		<%if(false && (managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong> <spring:message code="egli_elementi_gerarchicamente_collegati" text="e gli elementi gerarchicamente collegati"/><br /><%} %>
		<%if(false && (managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong> <spring:message code="egli_elementi_gerarchicamente_collegati" text="e gli elementi gerarchicamente collegati"/><br /><%} %>
		<%if(false && (managingBean.getNumElementiHier())>1){ %><input type="radio" name="applyTo" value="hierfalse"> <strong><spring:message code="solo_le_schede_gerarchicamente_collegate" text="solo le schede gerarchicamente collegate"/></strong> (<%=(managingBean.getNumElementiHier()-1)%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
		</div>
	</div>
</div>
 
</form>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<li><a title="<spring:message code="ESEGUI" text="ESEGUI"/>" class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica();"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a></li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>