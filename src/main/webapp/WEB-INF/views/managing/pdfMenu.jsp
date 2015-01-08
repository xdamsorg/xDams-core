<%@page import="org.xdams.page.view.bean.XSLBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	ManagingBean managingBean =(ManagingBean)request.getAttribute("managingBean") ;
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
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
// 	<!--
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('pdfPrint','${frontUrl}');

$(document).ready(function(){
  	$("#mailToSendInput").attr("disabled", "disabled");
	$("#sendByMailCheck").click(function(){
		var element = $("#mailToSendInput");
		if(element.attr("disabled") != undefined){ 
			element.removeAttr("disabled");
		}else{
			element.attr("disabled", "disabled");
		}
	});
	xDamsUnblock();
});
 
function impostaAndGo(){
	selectedRadio = false;
	
	for(var i=0;i<document.theForm.length;i++){
		if(document.theForm.elements[i].checked)selectedRadio=true;
	}
	
	if(!selectedRadio){
		xDamsModalAlert('<spring:message code="Specificare_unazione" text="Specificare un\\'azione"/>!<br /><br />');
		return;		
	}
	
	if(document.theForm.PDFXSLType.value==''){
		xDamsModalAlert('<spring:message code="Specify_print_template" text="Specificare template di stampa"/>!<br /><br />'); 
		return;
	} else  {
		if($('#sendByMailCheck').is(':checked')){
			var formOptions = {
				resetForm: false,
				//url: "ManagingServlet", 
				//type: "post", 
			   	success: function(responseText, statusText){
					xDamsModalAlert(responseText);
				}
			}
			
			$('#aForm').ajaxSubmit(formOptions);
		}else{
			document.theForm.submit();
		}
		
	}
	
	return false
}
 		
function proiettaModifica(sblocca){
	if(sblocca){self.close()}else{impostaAndGo(document.theForm)}
	return false
}
		
 
		//-->
		</script>

	</head>
<body >
<script type="text/javascript">xDamsModalMessage('analisi in corso...');</script>
<div id="STATO" class="statusLayerNONVisibile"><spring:message code="Attendere" text="Attendere"/>: <strong><spring:message code="modifica_record_in_corso" text="modifica record in corso"/>...</strong></div>
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
<form id="aForm" action="${contextPath}/<%=workFlowBean.getAlias() %>/pdfView.html" name="theForm" target="_blank" method="post">
<input type="hidden" name="actionFlag" value="xml2pdf" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<input type="hidden" name="idXpath" value="<%=MyRequest.getParameter("idXpath", request)%>" />
<input type="hidden" name="dominio" value="<%=MyRequest.getParameter("dominio", request)%>" />
<input type="hidden" name="theArch" value="<%=MyRequest.getParameter("theArch", request)%>" />
<input type="hidden" name="skipUser" value="<%=MyRequest.getParameter("skipUser", request)%>" />
<%
      String theOptions="";
      ArrayList listFile = null;
      listFile = (ArrayList)request.getAttribute("XSLBeanArrayList");
      for (int i = 0; i < listFile.size(); i++) {
         	String nomeValue = "";
      	nomeValue = ((XSLBean)listFile.get(i)).getFileName();
      	theOptions += "<option value=\""+nomeValue+"\">"+((XSLBean)listFile.get(i)).getLabel()+"</option>";
	  }
%>

<div class="m10">
applica la stampa a:
<br />
<input type="radio" name="applyTo" value="this"> <strong><spring:message code="elemento_selezionato" text="elemento selezionato"/></strong> <br />
<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected"> <strong><spring:message code="seleziona_multipla" text="seleziona multipla"/></strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid"> <strong><spring:message code="ricerca_corrente" text="ricerca corrente"/></strong> (<%=( managingBean.getNumElementi())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>

<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br /><%} %>
<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br /><%} %>

<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong><spring:message code="figli_diretti" text="figli diretti"/></strong> (<%=(managingBean.getNumElementiSons())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier"> <strong><spring:message code="tutto_il_ramo" text="tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<br /> 
 		<spring:message code="template_di_stampa" text="template di stampa"/>:<br /> 
		<span>
		<select name="PDFXSLType" class="long"><%=theOptions%></select>
		</span>
	<br />
	<spring:message code="tipologia_di_output" text="tipologia di output"/>:<br /> 
	<span>
        <select name="PrintType"  class="long">
            <option value="pdf">PDF - <spring:message code="non_modificabile" text="non modificabile"/></option>
            <option value="rtf">RTF - <spring:message code="modificabile" text="modificabile"/></option>
            <option value="html">html - <spring:message code="modificabile" text="modificabile"/></option> 
            <option value="jpg">jpg - <spring:message code="non_modificabile" text="non modificabile"/></option>
            <option value="eps">eps - <spring:message code="non_modificabile" text="non modificabile"/></option>
            <option value="tif">tif - <spring:message code="non_modificabile" text="non modificabile"/></option>
            <option value="svg">svg - <spring:message code="non_modificabile" text="non modificabile"/></option>
            <option value="csv">csv - <spring:message code="non_modificabile" text="non modificabile"/></option>
       </select>	
	</span>
 	<br />
	<br />
	<span>
		<input id="formattedText" type="checkbox" name="formattedText" value="true"/><spring:message code="idocumenti_contengono_elementi_di_formattazione_corsivo_grassetto_etc" text="i documenti contengono elementi di formattazione (corsivo, grassetto, etc.)"/>
		<br />
	</span>	
	<br />
	<br />
	<span>
		header<br />
		<textarea class="long" rows="6" cols="35" name="headerPDF"></textarea>
		<br />
		subtitle<br />
		<textarea class="long" rows="6" cols="35" name="subtitlePDF"></textarea>
		<br />
		footer<br />		
		<textarea class="long" rows="6" cols="35" name="footerPDF"></textarea>				
		<br />
	</span>	
	<br />
	<br />
</div>
</form>
</div>
<div id="foot">
	<div class="margin_foot">
 	<div class="cont_ul2">	
		<ul class="bottoniMenu" >
 			<li><a title="<spring:message code="ESEGUI" text="ESEGUI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a></li>
 			<li><a title="<spring:message code="chiudi" text="chiudi"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="chiudi" text="chiudi"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#"><spring:message code="chiudi" text="chiudi"/></a></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>



