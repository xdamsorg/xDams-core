<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.page.view.modeling.FormGenerator"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.utility.DateUtil"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.EditingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	EditingBean editingBean = (EditingBean)request.getAttribute("editingBean");
	
	
   	String theArch = workFlowBean.getAlias();
 	String strPne = workFlowBean.getArchive().getPne();
	int userRole = Integer.parseInt(workFlowBean.getArchive().getRole());
	String findIt = MyRequest.getParameter("findIt",request);
	int docNumber = 0;
  	String keyCountLookUp = confBean.getKeyCountLookUp();
  	String imagePath ="?????????????????????";
	XMLBuilder theXML = editingBean.getXmlBuilder();
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
   	//out.println(theXMLconf.getXML("ISO-8859-1"));
 	//out.println("editingBean.getIlPath() "+editingBean.getIlPath());
	String ilPath ="root/docEdit";
	int numeroMacroarea = theXMLconf.contaNodi("/"+ilPath+"/macroarea");
	String uniqueId = editingBean.getPhysDoc() + userBean.getAccount().getId() + workFlowBean.getAlias();
%><head>
		<meta content="no-cache" http-equiv="pragma" />
		<meta content="now" http-equiv="expires" />
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<title>xDams - editing - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
		<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
		<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
		<link rel="stylesheet" href="${frontUrl}/css/jquery/jgrowl.css" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="${frontUrl}/css/jquery/jqac.css" /> 
		<script type="text/javascript">
		var globalOption = {frontPath:'${frontUrl}',lookupAction:'${contextPath}/lookup/<%=workFlowBean.getAlias()%>/',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
		loadJsBusiness('docEdit','${frontUrl}');
			$(document).ready(function(){
				xDamsUnblock();
				fromName2Title();
				setupZoom('${frontUrl}/img/jquery/zoom/');
 			});
		</script>
	<script type="text/javascript">
		var arrLayer = new Array;
		<%
		for(int i=0;i < numeroMacroarea; i++){
			%>arrLayer[<%=i%>] = "<%=theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+(i+1)+"]/@layer")%>"; <%
		}
		%>
			var layerCorrente = arrLayer[0]; //imposta il primo layer aperto
			var menuCorrente = '1'; //imposta la voce di menu evidenziata per prima
			var nomeCognome = "<%=userBean.getName()%> <%=userBean.getLastName()%>";
			laData = '<%=DateUtil.getDataSystem("dd/MM/yyyy")%>';
			var eleArray = new Array();
		</script>
		<link href="${frontUrl}/css/stile.css" rel="stylesheet" type="text/css"/>
		<link href="${frontUrl}/css/colors.css" rel="stylesheet" type="text/css"/>
		<link href="${frontUrl}/css/edit.css" rel="stylesheet" type="text/css"/>
	</head>
<body onload="self.focus();showHideLayers(arrLayer[0],'','show');return showAllSection();">
<script type="text/javascript">
xDamsModalMessage('caricamento in corso...');
</script>
	<div id="testa_edit">
		<div class="sotto_head">&nbsp;</div>
		<div class="riga_tit_diz">
			<div class="Tit_diz">modifica</div>
			<div class="butt_close"><a  href="#nogo"  onmouseover="window.status=('Chiude la schermata corrente senza salvare le modifiche effettuate')" onmouseout="window.status=('')" onclick="return chiudiPagina('Attenzione:\nchiudendo la pagina corrente le modifiche effettuate andranno perse!')" class="link_white" title="chiudi finestra">chiudi x</a></div>
		</div>
			<div class="cont_riga_dx">
				<div class="riga_sx">
					<a title="<%=theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+1+"]/@label")%>" class="link_u_w_nav" onclick="evidenziaLI(this,'link_u_w');this.className='link_u_w_nav';return  showHideLayers('<%=StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+1+"]/@layer"))%>','','show')" href="#nogo"><%=StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+1+"]/@name"))%></a>
					<%for(int i=1;i < numeroMacroarea; i++){%>
						<span class="barretta_div">|</span>
						<a title="<%=theXMLconf.valoreNodoHTML("/"+ilPath+"/macroarea["+(i+1)+"]/@label")%>" class="link_u_w" onclick="evidenziaLI(this,'link_u_w');this.className='link_u_w_nav';return  showHideLayers('<%=StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+(i+1)+"]/@layer"))%>','','show')" href="#nogo"><%=StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+(i+1)+"]/@name"))%></a>
					<%}%>
			</div>
			</div>
		</div>

	<div id="SALVATAGGIO" class="statusLayerNONVisibile">Attendere: <strong>salvataggio record in corso...</strong></div>
	<form name="theForm" action="${contextPath}/save/<%=workFlowBean.getAlias()%>/document.html" method="post">
	<div class="cont_menu_sx">
		<ul  class="lista_menu_sx">
				<xDamsJSTL:menugestionejslt confFile="bar-docedit"/>
		</ul>
	</div>
	<div id="contenutoEdit">
	<div class="contAllArea">
	<% 
	System.out.println("---- INFO ---- editing page: "+userBean.getAccount().getDescrAccount()+" | "+workFlowBean.getAlias() +" / "+userBean.getName()+" "+userBean.getLastName());
	FormGenerator generatoreForm = new FormGenerator(theXML,theXMLconf,workFlowBean,(String)request.getAttribute("contextPath"));
	//generatoreForm.setUserBean(userBean); 
	generatoreForm.setPhysDoc(editingBean.getPhysDoc());
  	boolean testaRigaSepara = false;
	generatoreForm.setValues("part01","<tr class=\"showdocLine\"><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td  class=\"showdocTitoloElemento\" width=\"30%\" valign=\"middle\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("part03","</td><td class=\"showdocValue\" width=\"20\"><span style=\"font-size:1px\">&#160;</span></td></tr>");
	generatoreForm.setValues("part00multi","<tr class=\"showdocLine\"><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"showdocTitoloElemento\"  valign=\"middle\" colspan=\"2\">");
	generatoreForm.setValues("part01multi","<tr class=\"showdocLine\"><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"showdocTitoloElemento\" valign=\"middle\" width=\"30%\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multi","</td><td class=\"showdocValue\">");
	generatoreForm.setValues("imagePath",imagePath);
	generatoreForm.setValues("part01multiGroup","<tr><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td  class=\"showdocTitoloElemento\" valign=\"middle\" colspan=\"2\" style=\"padding-left:5px\">");
	generatoreForm.setValues("part02multiGroup","<tr><td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">");
	generatoreForm.setValues("part03multiGroup","</td><td class=\"showdocValue\" style=\"border-bottom: 0px none;\">");
	String rigaSeparaMiddle = "<tr class=\"showdocLine\" ><td  width=\"4\" height=\"4\" ><span style=\"font-size:1px\">&#160;</span></td><td colspan=\"3\" ><span style=\"font-size:1px\">&#160;</span></td></tr>";
	String rigaSepara="<table><tr><td height=\"10\" colspan=\"4\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td></tr></table>";
	generatoreForm.setValues("rigaSepara",rigaSepara);
	generatoreForm.setValues("partAuther01","<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
	generatoreForm.setValues("partAuther02","<tr><td class=\"showdocValue\" style=\"padding:0px\">");
	generatoreForm.setValues("partAuther03","</td><td width=\"10%\"  class=\"showdocValue\">");
	generatoreForm.setValues("partAuther04","</td></tr>");
	generatoreForm.setValues("rigaSeparaAuther","<tr><td height=\"10\" colspan=\"2\"><span style=\"font-size:1px\">&#160;</span></td></tr>");

	try{%><%@include file="generateDocEdit.jsp"%><%}
	finally{}
%>
		
	</div>
</div>
	<input type="hidden" name="campiObbligatori" value="<%=theXMLconf.valoreNodo("/root/param/elemento[@id='campi_obbligatori']/text()").replaceAll("/","\\.")%>" />
  	<input type="hidden" name="physDoc" value="<%=editingBean.getPhysDoc()%>" />
	<input type="hidden" name="thePne" value="<%=editingBean.getThePne()%>" />
	<input type="hidden" name="theDb" value="<%=workFlowBean.getAlias()%>" />
	<input type="hidden" name="pos" value="<%=editingBean.getPos()%>" />
	<input type="hidden" name="selid" value="<%=editingBean.getSelid()%>" />
	<input type="hidden" name="urlErrorPage" value="/error.jsp" />
	<input type="hidden" name="goServlet" value="" />
	<input type="hidden" name="go" value="" />

</form>
<form action="" name="lookupMain" target="_blank" method="post" style="display:none">
<input type="hidden" name="inputQuery"       value="" size="50" />
<input type="hidden" name="inputExtraQuery"  value="" size="50" />
<input type="hidden" name="inputSort"        value="" size="50" />
<input type="hidden" name="inputTitleRule"   value="" size="50" />
<input type="hidden" name="inputUdType"      value="" size="50" />
<input type="hidden" name="inputPerPage"     value="<%=keyCountLookUp%>" size="50" />
<input type="hidden" name="inputStrQuery"    value="" size="50" />
<input type="hidden" name="campiDaValorizzare"    value="" size="50" />
<input type="hidden" name="titleRule"     value="" size="50" />
<input type="hidden" name="jspOutPut"     value="" size="50" />
<input type="hidden" name="flagXML"     value="false" size="50" />
</form>
<div id="footPage">
	<%@include file="inc_sub_menu.jsp"%>
</div>
<%@include file="../custom/form_docEdit.jsp"%>
 <div id="textAreaBigLayer" class="statusLayerNONVisibile"></div>
<script type="text/javascript">
//<![CDATA[
document.write('<div class="statusLayerNONVisibile" id="attendere"><blink>attendere...</blink><marquee>attendere...</marquee></div>');
//]]>
</script>
 </body>
</html>