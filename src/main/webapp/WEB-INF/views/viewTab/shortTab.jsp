<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Map"%>
<%@page import="org.xdams.utility.TrasformXslt20"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.apache.commons.lang3.text.StrSubstitutor"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="it.highwaytech.db.HierPath"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.page.view.bean.ViewBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	ViewBean viewBean = (ViewBean)request.getAttribute("viewBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	
	//da vedere quando facciamo ricerca per titoli
	String pos = viewBean.getPos();
	//da vedere quando facciamo ricerca per titoli
	String selId = viewBean.getSelid();

	int docNumber = viewBean.getPhysDoc();
	int docFather = viewBean.getDocFather();
	int docSon = viewBean.getDocSon();
	int docUpperBrother = viewBean.getDocUpperBrother();
	int docLowerBrother =  viewBean.getDocLowerBrother();
	int treePos = viewBean.getTreePos();
	//da rivedere per la gestione generica dello hierpath
	HierPath laGerarchia = viewBean.getHierPath();

 	String imagePath = "????????????????";

	boolean flagInventario = false;
	if(docFather!=docNumber){flagInventario=true;}

	XMLBuilder theXML = viewBean.getXmlBuilder();
	XMLBuilder theXMLconf = confBean.getTheXMLConfPresentation();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLValControllati = confBean.getTheXMLValControllati();
	XMLBuilder theXMLconfEditing = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLConfMedia = confBean.getTheXMLConfMedia();
	XMLBuilder theXMLConfManaging = confBean.getTheXMLConfManaging();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);

	String ilTitolo = titleManager.defaultParsedTitle(viewBean.getTitle(),"defaultTitle");
	String ilTitoloEncoded = java.net.URLEncoder.encode(ilTitolo,"ISO-8859-1");

  	String elementoPath="";
	String extraFunction="";
    
	/*
	LockData lockData = null;
	Object objData = getServletContext().getAttribute("lockData");
	if(objData!=null){
		lockData = ((LockData)objData);
	}
	String uniqueId = viewBean.getPhysDoc() + userBean.getIdAccount() + userBean.getTheArch();
	*/
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
var globalOption = {frontPath:'${frontUrl}',infoURLPrefix:'${contextPath}/infoTab/',infoURLSuffix:'/infoTab.html',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('shortTab','${frontUrl}');
</script>

<script type="text/javascript">
$(document).ready(function(){
 
try{
	parent.evidenziaDocumento('<%=docNumber %>');
}catch(e){}


});
function evidenziaFromLeft(){
try{
	parent.evidenziaDocumento('<%=docNumber %>');
}catch(e){}

}
</script>
</head>
<body onunload="return setRamoDoc(false)" onload="<%if(docSon>0){ %>setRamoDoc(true,'<%=docNumber %>');<%} %>;">

<%
if(theXMLConfManaging.contaNodi("/root/param/elemento[@id='substitutor']")>0){
	StrSubstitutor strSubstitutor = new StrSubstitutor();
}
%>
<div id="headSchedaBreve">
<div class="cont_ul2">
	<ul class="bottoniMenu">
		<xDamsJSTL:menugestionejslt confFile="bar-managing"/>
	</ul>
</div>
	<%
	  if(session.getAttribute(workFlowBean.getManagingBeanName())==null || ((ManagingBean) session.getAttribute(workFlowBean.getManagingBeanName())).getCutPhysDoc()==-1){%>
			<div id="scriviQui"></div>
	<%}else{
		ManagingBean managingBean = (ManagingBean) session.getAttribute(workFlowBean.getManagingBeanName());
		if(managingBean.getCutPhysDoc()!=-1){%>
			<div id="scriviQui"><%=managingBean.getCutHtmlOutput(String.valueOf(viewBean.getPhysDoc()))%></div>
		<%}
 	  }%>
</div>

	<div id="contentSchedaBreve">
		<xDamsJSTL:gerarchiajslt hierPath="<%=laGerarchia%>"/>
		<div id="textSc">
			<div id="scriviTrova"></div>
		<%
		try {
				if(confBean.getPresentationXsl()==null){
				%><%@include file="inc_shortTabViewResult.jsp"%><%			
				}else{
				%><%=TrasformXslt20.xslt(theXML.getXML("ISO-8859-1",false), confBean.getPresentationXsl(),((Map<String,String>)request.getAttribute("mapParams")))%><%	
				}
			} catch (Exception e) {
				throw new Exception("error loading conf file aaa"+e.getMessage());
			}
		%>
		</div>
</div> <!-- fine content -->

<div id="footSchedaBreve">
	<div class="cont_ul2">
		<ul class="bottoniMenu">
			<xDamsJSTL:menugestionejslt confFile="bar-vis" />
		</ul>
	</div>
	<%System.out.println(" ---- INFO ---- schedabreve page"); %>
	<div class="cont_ul2">
		<ul class="bottoniMenu">
			<xDamsJSTL:menugestionejslt confFile="bar-nav" />
		</ul>
	</div>
	<%System.out.println(" ---- INFO ---- schedabreve page"); %>
	<div class="cont_ul2" >
		<ul class="bottoniMenu">
			<xDamsJSTL:menugestionejslt confFile="bar-edt" />
		</ul>
	</div>
	<%System.out.println(" ---- INFO ---- schedabreve page"); %>
</div>

</body>
</html>
