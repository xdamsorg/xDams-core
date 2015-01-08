<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.page.view.modeling.FormGenerator"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.PreInsertBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html xmlns="http://www.w3.org/1999/xhtml" >
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
 	PreInsertBean preInsertBean = (PreInsertBean)request.getAttribute("preInsertBean");
   	String theArch = workFlowBean.getAlias();
  	String keyCountLookUp = confBean.getKeyCountLookUp();
	XMLBuilder theXML = preInsertBean.getXmlBuilderSelected();
	XMLBuilder theXMLFather = preInsertBean.getXmlBuilderFather();
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing(); 
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);
 
	FormGenerator generatoreForm = new FormGenerator(theXMLconf,theXMLconf,workFlowBean,(String)request.getAttribute("contextPath"));
	boolean testaRigaSepara = false;
	String rigaSeparaMiddle = "<tr class=\"showdocLine\" ><td  width=\"4\" bgcolor=\"#B4B7BA\" height=\"4\" ><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td colspan=\"3\" width=\"100%\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>";
  	String rigaSepara="<table><tr><td height=\"10\" colspan=\"4\" width=\"4\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr></table>";
  	generatoreForm.setValues("part01","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td  class=\"showdocTitoloElemento\" width=\"30%\" valign=\"middle\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("part03","</td><td class=\"showdocValue\" width=\"20\"><img height=\"1\" width=\"20\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>");
	generatoreForm.setValues("part00multi","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td class=\"showdocTitoloElemento\"  valign=\"middle\" colspan=\"2\">");
	generatoreForm.setValues("part01multi","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td class=\"showdocTitoloElemento\" valign=\"middle\" width=\"30%\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multi","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("part01multiGroup","<tr ><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td  class=\"showdocTitoloElemento\" valign=\"middle\" colspan=\"2\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multiGroup","<tr><td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">");
	generatoreForm.setValues("part03multiGroup","</td><td class=\"showdocValue\" style=\"border-bottom: 0px none;\">");
	generatoreForm.setValues("partAuther01","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">");
	generatoreForm.setValues("partAuther02","<tr><td class=\"showdocValue\" style=\"padding:0px\">");
	generatoreForm.setValues("partAuther03","</td><td width=\"10%\"  class=\"showdocValue\">");
	generatoreForm.setValues("partAuther04","</td></tr>");
	generatoreForm.setValues("rigaSeparaAuther","<tr><td height=\"10\" colspan=\"2\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>");
	int numeroMacroarea = theXMLconf.contaNodi("/root/preInsert/macroarea");
	%>
	<head>
		<meta content="no-cache" http-equiv="pragma" />
		<meta content="now" http-equiv="expires" />
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<title>xDams - preInsert - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
		<link href="${frontUrl}/css/stile.css" rel="stylesheet" type="text/css"/>
		<link href="${frontUrl}/css/colors.css" rel="stylesheet" type="text/css"/>
		<link href="${frontUrl}/css/edit.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
		<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
		<link rel="stylesheet" href="${frontUrl}/css/jquery/jgrowl.css" type="text/css"/>
		<link rel="stylesheet" href="${frontUrl}/css/jquery/jqac.css" type="text/css" />
  		<script type="text/javascript">
  		<%=workFlowBean.getGlobalLangOption()%>
		var globalOption = {frontPath:'${frontUrl}',lookupAction:'${contextPath}/lookup/<%=workFlowBean.getAlias()%>/',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
		loadJsBusiness('preInsert','${frontUrl}');
		
		</script>	
		
		<script type="text/javascript">
 		var arrLayer = new Array;
		var layerCorrente = arrLayer[0]; //imposta il primo layer aperto
		var menuCorrente = '1'; //imposta la voce di menu evidenziata per prima
		var eleArray = new Array();
		</script>
	</head><%
	String ilPath ="/preInsert";
%><body onload="window.focus();showAllSection();return suggerisciLevel('<%=MyRequest.getParameter(".c.@level",request) %>')" >
	<div id="testa_edit">
		<div class="sotto_head">&nbsp;</div>
		<div class="riga_tit_diz">
			<div class="Tit_diz"><spring:message code="INSERISCI" text="INSERISCI"/></div>
			<div class="butt_close"><a href="javascript: self.close()" class="link_white" title="chiudi finestra"><spring:message code="chiudi" text="chiudi"/> x</a></div>
		</div>
			<div class="cont_riga_dx">
				<div class="riga_sx">
  				  <%if(preInsertBean.getTitleSelected()!=null && !(preInsertBean.getTitleSelected().equals(""))){ %>
					  <spring:message code="estata_selezionata_la_scheda" text="è stata selezionata la scheda"/>: <strong><%=titleManager.defaultParsedTitle(preInsertBean.getTitleSelected(), "defaultTitle")%></strong>
				  <%} %>
			</div>
			</div>
		</div>

	<div id="SALVATAGGIO" class="statusLayerNONVisibile"><spring:message code="Attendere" text="Attendere"/>: <strong><spring:message code="creazione_record_in_corso" text="creazione record in corso"/>...</strong></div>

	<form name="theForm" action="${contextPath}/editing/<%=workFlowBean.getAlias()%>/insert.html" method="post">
	<div class="cont_menu_sx">
		<ul class="lista_menu_sx">
				<xDamsJSTL:menugestionejslt confFile="bar-preinsert"/>
		</ul>
	</div>
	<div id="contenutoEdit">
	<div class="contAllArea">
	<%
	String excludeRel = theXMLconf.valoreNodo("/"+ilPath+"/@excludeRel");
	String suggestedRel = theXMLconf.valoreNodo("/"+ilPath+"/@suggestedRel");
	String strChecked = "checked=\"checked\"";
	%>	<div class="area">
			<span class="riga_tit_area"></span>
			<div class="box_cont">
				<table cellspacing="0" cellpadding="0" border="0" >
					<tr class="doceditTitoloSottoSezione">
						<td bgcolor="#B4B7BA" height="12" width="4"><img height="1" width="1" vspace="0" hspace="0" border="0" src="/dams_common/img/null.gif" alt="" /></td>
						<td class="doceditTitoloSottoSezioneSfDark" height="12">&#160;&#160;<spring:message code="informazioni_di_relazione" text="informazioni di relazione"/></td>
					</tr>
				</table>
<table cellspacing="0" cellpadding="0" border="0" >
<tr class="showdocLine" ><td  width="4" height="4" ><img height="1" width="4" vspace="0" hspace="0" border="0" src="/dams_common/img/null.gif" alt="" /></td></tr>
	<tr>
		<td nowrap="nowrap">
		<table cellspacing="0" cellpadding="0" border="0" >
			 <%if(excludeRel.indexOf("son")==-1 && preInsertBean.getPhysDocRoot()!=-1){
				if(suggestedRel.indexOf("son")!=-1){
					strChecked = "checked=\"checked\"";
				}else{
					strChecked = "";
				}
			%>
			<tr class="showdocLine">

				<td  class="showdocTitoloElemento" width="30%" valign="middle" style="padding-left:5px"><input type="radio" name="relation" <%=strChecked%> value="1" /> <spring:message code="figlio" text="figlio"/></td>
				<td class="showdocValue"></td>
			</tr>
			<%}if(preInsertBean.getPhysDocRoot()!=preInsertBean.getPhysDocSelected() && excludeRel.indexOf("upperbrother")==-1){
				if(suggestedRel.indexOf("upperbrother")==0){
					strChecked = "checked=\"checked\"";
				}else{
					strChecked = "";
				}
			%>
			<tr class="showdocLine">
				<td  class="showdocTitoloElemento" width="30%" valign="middle" style="padding-left:5px"><input type="radio" name="relation" <%=strChecked%> value="2" /> <spring:message code="fratello_precedente" text="fratello precedente"/></td>
				<td class="showdocValue"></td>
			</tr>
			<%}if(preInsertBean.getPhysDocRoot()!=preInsertBean.getPhysDocSelected() && excludeRel.indexOf("lowerbrother")==-1){
				if(suggestedRel.indexOf("lowerbrother")==0){
					strChecked = "checked=\"checked\"";
				}else{
					strChecked = "";
				}
			%>
			<tr class="showdocLine">
				<td  class="showdocTitoloElemento" width="30%" valign="middle" style="padding-left:5px"><input type="radio" name="relation" <%=strChecked%> value="4" /> <spring:message code="fratello_successivo" text="fratello successivo"/></td>
				<td class="showdocValue"></td>
			</tr>
			<%}if(excludeRel.indexOf("none")==-1 || preInsertBean.getPhysDocRoot()==-1){
				if(suggestedRel.indexOf("none")==0 || preInsertBean.getPhysDocRoot()==-1){
					strChecked = "checked=\"checked\"";
				}else{
					strChecked = "";
				}
			%>
			<tr class="showdocLine">
				<td  class="showdocTitoloElemento" width="30%" valign="middle" style="padding-left:5px"><input type="radio" name="relation" <%=strChecked%> value="0" /> <spring:message code="scheda_scollegata" text="scheda scollegata"/></td>
				<td class="showdocValue"></td>
			</tr>
		<%}%>
		</table>
		</td>
	</tr>
</table>
</div>
</div>
<%
if(true || preInsertBean.getPhysDocFather() > 0){ %>
<div class="area">
			<span class="riga_tit_area"></span>
			<div class="box_cont">

			<span id="sezione_head_multiplo">
<table cellspacing="0" cellpadding="0" border="0">
	<tr class="doceditTitoloSottoSezione">
		<td bgcolor="#B4B7BA" height="12" width="4"><img height="1" width="1" vspace="0" hspace="0" border="0" src="/dams_common/img/null.gif" alt="" /></td>
		<td class="doceditTitoloSottoSezioneSfDark" height="12"><a class="doceditActionLink" id="sezione_link_multiplo" onclick="openSection('sezione_multiplo',this)" href="javascript:void(0)">+</a>&#160;<spring:message code="inserimento_multiplo_di_schede" text="inserimento multiplo di schede"/></td>
	</tr>
</table></span>
<span id="sezione_multiplo" style="display:none">
<table cellspacing="0" cellpadding="0" border="0" >
<tr class="showdocLine" ><td  width="4" height="4" ><img height="1" width="4" vspace="0" hspace="0" border="0" src="/dams_common/img/null.gif" alt="" /></td></tr>
	<tr>
		<td nowrap="nowrap">

		<table cellspacing="0" cellpadding="0" border="0" >
			<tr class="showdocLine">
				<td  class="showdocTitoloElemento" width="30%" valign="middle" style="padding-left:5px"><spring:message code="numero_di_elementi_da_creare" text="numero di elementi da creare"/></td>
				<td class="showdocValue"><input type="text" class="docEditInputShort"  onfocus="setCurrInput(this)"   name="elementiMultipli" onkeypress="allowOnlyNumbers()" /></td>
			</tr>

		</table>
		</td>
	</tr>
</table></span>
</div>
</div>
<%}

request.setAttribute("showAll","true");  //per mostrare le macroaree aperte una sotto l'altra e non in pagine
try{
%><%@include file="generateDocEdit.jsp"%><%}
finally{}
theXML = preInsertBean.getXmlBuilderFather();
numeroMacroarea = theXMLconf.contaNodi("/root"+preInsertBean.getXPathHierValues()+"/macroarea");
ilPath = "/root"+preInsertBean.getXPathHierValues();
if(theXML !=null){
	generatoreForm = new FormGenerator(theXML,theXMLconf,workFlowBean,(String)request.getAttribute("contextPath"));
	testaRigaSepara = false;
	rigaSeparaMiddle = "<tr class=\"showdocLine\" ><td  width=\"4\" bgcolor=\"#B4B7BA\" height=\"4\" ><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td colspan=\"3\" width=\"100%\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>";
	rigaSepara="<table><tr><td height=\"10\" colspan=\"4\" width=\"4\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr></table>";
	generatoreForm.setValues("part01","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td  class=\"showdocTitoloElemento\" width=\"30%\" valign=\"middle\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("part03","</td><td class=\"showdocValue\" width=\"20\"><img height=\"1\" width=\"20\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>");
	generatoreForm.setValues("part00multi","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td class=\"showdocTitoloElemento\"  valign=\"middle\" colspan=\"2\">");
	generatoreForm.setValues("part01multi","<tr class=\"showdocLine\"><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td class=\"showdocTitoloElemento\" valign=\"middle\" width=\"30%\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multi","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("part01multiGroup","<tr ><td bgcolor=\"#B4B7BA\" height=\"12\" width=\"4\"><img height=\"1\" width=\"4\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td><td  class=\"showdocTitoloElemento\" valign=\"middle\" colspan=\"2\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multiGroup","<tr><td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">");
	generatoreForm.setValues("part03multiGroup","</td><td class=\"showdocValue\" style=\"border-bottom: 0px none;\">");
	//generatoreForm.setValues("rigaSeparaMiddle",rigaSeparaMiddle);
	generatoreForm.setValues("partAuther01","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">");
	generatoreForm.setValues("partAuther02","<tr><td class=\"showdocValue\" style=\"padding:0px\">");
	generatoreForm.setValues("partAuther03","</td><td width=\"10%\"  class=\"showdocValue\">");
	generatoreForm.setValues("partAuther04","</td></tr>");
	generatoreForm.setValues("rigaSeparaAuther","<tr><td height=\"10\" colspan=\"2\"><img height=\"1\" width=\"1\" vspace=\"0\" hspace=\"0\" border=\"0\" src=\""+request.getAttribute("frontUrl")+"/img/null.gif\"></td></tr>");
	try{
		%><%@include file="generateDocEdit.jsp"%><%}
	catch(Exception e){}
}

%>
	</div>
</div>
	<input type="hidden" name="campiObbligatori" value="<%=theXMLconf.valoreNodo("/root/param/elemento[@id='campi_obbligatori']/text()").replaceAll("/","\\.")%>" />
 	<input type="hidden" name="go" value="insertRecord" />
	<input type="hidden" name="relatedPhysDoc" value="<%=preInsertBean.getPhysDocSelected()%>" />
	<input type="hidden" name="srcArchive" value="<%=MyRequest.getAttribute("srcArchive", request)!=null ? MyRequest.getAttribute("srcArchive", request) : ""%>" />
	<input type="hidden" name="goServlet" value="" />
	
	<%
	for (int a = 0; a < theXMLconf.contaNodi("/root/fixedValues/elemento"); a++) {
		String ilNodoCorrente = theXMLconf.valoreNodo("/root/fixedValues/elemento[" + (a + 1) + "]/text()");
		String ilValoreCorrente = theXMLconf.valoreNodo("/root/fixedValues/elemento[" + (a + 1) + "]/@value");
		%>
		<input type="hidden" name="<%=ilNodoCorrente.replaceAll("/",".")%>" value="<%=ilValoreCorrente%>" />
		<%
	}
	%>
</form>
<form action="" name="lookupMain" target="_blank" method="post" style="display:none">
<input type="hidden" name="inputQuery"       value="" size="50" />
<input type="hidden" name="inputExtraQuery"  value="" size="50" />
<input type="hidden" name="inputSort"        value="" size="50" />
<input type="hidden" name="inputTitleRule"   value="" size="50" />
<input type="hidden" name="inputUdType"      value="" size="50" />
<input type="hidden" name="inputPerPage"     value="<%="10"%>" size="50" />
<input type="hidden" name="inputStrQuery"    value="" size="50" />
<input type="hidden" name="campiDaValorizzare"    value="" size="50" />
<input type="hidden" name="titleRule"     value="" size="50" />
<input type="hidden" name="jspOutPut"     value="" size="50" />
<input type="hidden" name="flagXML"     value="false" size="50" />
</form>
<div id="footPage">
	<%@include file="inc_sub_menu.jsp"%>
<!--inizio content-->
</div>
<%@include file="../custom/form_docEdit.jsp"%>
 <div id="textAreaBigLayer" class="statusLayerNONVisibile"><center><textarea name="textAreaBig" id="textAreaBig" rows="2" cols="2" class="docEditTextAreaBig"></textarea></center></div>

</body>
</html>


