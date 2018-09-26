<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.xdams.utility.TrasformXslt20"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="it.highwaytech.db.HierPath"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.page.view.bean.ViewBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	ViewBean viewBean = (ViewBean)request.getAttribute("viewBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");

	String pos = MyRequest.getParameter("pos",request);
	String selId = MyRequest.getParameter("selId",request);

	String theArch = workFlowBean.getAlias();
	int docNumber = viewBean.getPhysDoc();
	int docFather = viewBean.getDocFather();
	int docSon = viewBean.getDocSon();
	int docUpperBrother = viewBean.getDocUpperBrother();
	int docLowerBrother =  viewBean.getDocLowerBrother();
	int treePos = viewBean.getTreePos();
	HierPath laGerarchia = viewBean.getHierPath();

 	String imagePath=null;

	boolean flagInventario = false;
	if(docFather!=docNumber){flagInventario=true;}

	XMLBuilder theXML = viewBean.getXmlBuilder();
	XMLBuilder theXMLconf = confBean.getTheXMLConfPresentation();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLValControllati = confBean.getTheXMLValControllati();
 	XMLBuilder theXMLconfEditing = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLConfMedia = confBean.getTheXMLConfMedia();
	
	TitleManager titleManager = new TitleManager(theXMLconfTitle);
	
	String ilTitolo = titleManager.defaultParsedTitle(viewBean.getTitle(),"defaultTitle");
	String ilTitoloEncoded = java.net.URLEncoder.encode(ilTitolo,"ISO-8859-1");


  	String elementoPath="";
	String extraFunction="";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta content="no-cache" http-equiv="pragma" />
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}',infoURLPrefix:'${contextPath}/infoTab/',infoURLSuffix:'/infoTab.html',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('completeTab','${frontUrl}');
$(document).ready(function(){	

	if(jQuery.trim(jQuery('#contenutoScheda').text())==''){
		try{
			window.opener.xDamsMiniAlert('<spring:message code="documento_salvato_con_successo" text="documento salvato con successo"/>');
		}catch(e){
			alert('<spring:message code="documento_salvato_con_successo" text="documento salvato con successo"/>');
		};
		
		try{
			window.opener.reloadLocation();
		}catch(e){
		}
		self.close();
	}
	
	var counter = 0;
	$("div[class='campoFoto']").each(function(index) {
		var resource = $(this).find("a").attr("href");
		var resourceText = $(this).find("a").text();
		if(resource.match(/\.(jpeg|jpg|gif|png)$/) != null){
			$(this).find("a").remove();
			$(this).append("<a target=\"same\" href=\"${contextPath}/custom/${workFlowBean.archive.alias}/page.html?physDoc=${viewBean.physDoc}&mediaToView="+counter+"&pageName=custom/viewImage&confControl=media\"><img width=\"170px\" src=\""+encodeURI(resource)+"\"/></a>");
			counter++;
		}
		else{
			$(this).find("a").remove();
			$(this).append("<a href="+encodeURI(resource)+" class=\"v_menu\" target=\"_blank\">"+resourceText+"</a>");
		}
	});
	
});
eleArray=new Array();
function evidenziaFromLeft(){
	return evidenziaDocumento('<%=docNumber %>');
}

</script>
</head>
<body onload="<%if(docSon>0){ %>setRamoDoc(true,'<%=docNumber %>');<%} %>  evidenziaDocumento('<%=docNumber %>');return showAllSection();">

<div id="testa_scheda">
	<div class="sotto_head">&nbsp;</div>
	<div class="riga_tit_diz">
		<div class="Tit_diz"><spring:message code="scheda" text="scheda"/></div>
		<div class="butt_close"><a href="javascript: self.close()" class="link_white" title="<spring:message code="chiudi" text="chiudi"/>"><spring:message code="chiudi" text="chiudi"/> x</a></div>
	</div>
	<div class="cont_riga_dx">
		<div class="riga_dx"><a href="#" class="link_u_w" title=""><spring:message code="scheda" text="scheda"/></a><!--  <span class="barretta_div">|</span><a href="#" class="link_u_w" title="">Compilazione</a> --></div>
	</div>
</div>
<div class="cont_menu_sx">
	<ul class="lista_menu_sx">
		<xDamsJSTL:menugestionejslt confFile="bar-nav" />
		<xDamsJSTL:menugestionejslt confFile="bar-edt" />
	</ul>
</div>
<div id="contenutoScheda">
	<div class="contAllArea">
	<%if(laGerarchia!=null && laGerarchia.depth()>1){%>
		<div class="area">
			<span class="riga_tit_area"><spring:message code="Struttura_gerarchica" text="Struttura gerarchica"/></span>
			<div class="box_cont">
	 			<div>
					<ul class="riga">
						<li class="valore"><%
						//String valueStr = "window_"+(userBean.getArchiviBeanFromAliasDb(theArch)).getWebapp();
						%><xDamsJSTL:gerarchiajslt hierPath="<%=laGerarchia%>"/></li>
					</ul>
				</div>
			</div>
 		</div>
	  <%}%>
	  <div id="scriviTrova"></div>
		<%
		try { 
				if(confBean.getPresentationXsl()==null){
				%><%@include file="inc_completeTabViewResult.jsp"%><%			
				}else{
				%><%=TrasformXslt20.xslt(theXML.getXML("ISO-8859-1",false), confBean.getPresentationXsl(), ((Map<String,String>)request.getAttribute("mapParams")))%><%	
				}
			} catch (Exception e) {
				throw new Exception("error loading conf file "+ request.getAttribute("mapParams"));
			}
		%>
	</div>
</div> <!-- fine contenutoScheda-->
<div id="footPage">
	<%@include file="../common/inc_sub_menu.jsp"%>
</div>
</body>
</html>