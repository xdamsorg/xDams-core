<%@page import="org.xdams.page.view.bean.XSLBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	confBean.setPageContext(pageContext);
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
		xDamsModalAlert('Specificare un\'azione!<br /><br />') 
		return;		
	}
	
	if(document.theForm.PDFXSLType.value==''){
		xDamsModalAlert('Specificare template di stampa!<br /><br />') 
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
			document.theForm.submit()
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
<div id="STATO" class="statusLayerNONVisibile">Attendere: <strong>modifica record in corso...</strong></div>
<div id="content_multi">
<div class="riga_posiziona_multi">elemento selezionato: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
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
<input type="radio" name="applyTo" value="this"> <strong>elemento selezionato</strong> <br />
<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected"> <strong>seleziona multipla</strong> (<%=(managingBean.getListPhysDoc()).size()%>  elementi)<br /><%} %>
<%if((managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid"> <strong>ricerca corrente</strong> (<%=( managingBean.getNumElementi())%> elementi)<br /><%} %>

<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong>fratelli precedenti</strong><br /><%} %>
<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong>fratelli successivi</strong><br /><%} %>

<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong>figli diretti</strong> (<%=(managingBean.getNumElementiSons())%> elementi)<br /><%} %>
<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier"> <strong>tutto il ramo</strong> (<%=(managingBean.getNumElementiHier())%> elementi)<br /><%} %>
<br /> 
 		template di stampa:<br /> 
		<span>
		<select name="PDFXSLType" class="long"><%=theOptions%></select>
		</span>
	<br />
	tipologia di output:<br /> 
	<span>
        <select name="PrintType"  class="long">
            <option value="pdf">PDF - non modificabile</option>
            <option value="rtf">RTF - modificabile</option>
            <option value="html">html - modificabile</option> 
            <option value="jpg">jpg - non modificabile</option>
            <option value="eps">eps - non modificabile</option>
            <option value="tif">tif - non modificabile</option>
            <option value="svg">svg - non modificabile</option>
            <option value="csv">csv - non modificabile</option>
       </select>	
	</span>
 	<br />
	<br />
	<span>
		<input id="formattedText" type="checkbox" name="formattedText" value="true"/>i documenti contengono elementi di formattazione (corsivo, grassetto, etc.)
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
 			<li><a title="Esegui la richiesta"  class="bottoneLink" onmouseover="window.status='esegui';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#">ESEGUI</a></li>
 			<li><a title="Chiude la pagina corrente"  class="bottoneLink" onmouseover="window.status='chiudi';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#">CHIUDI</a></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>



