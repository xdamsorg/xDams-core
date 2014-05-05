<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@ page errorPage="/error.jsp" %>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
 
	int elementi = managingBean.getNumElementi();
	int elementiErrore = managingBean.getDocErrori();
	int elementiSuccessi = managingBean.getDocSuccessi();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);

 %><html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('nativeXML','${frontUrl}');
</script>
</head>
<body onload="this.focus()">
<div id="content_multi">
	<div class="m10">elemento selezionato: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
	<div class="m10">elementi modificati: <strong><%=elementiSuccessi%></strong></div>
	<div class="m10">errori occorsi: <strong><%=elementiErrore%></strong></div>
<%
List errorArray = managingBean.getErrorMsg();
managingBean.setErrorMsg(null);
if(errorArray!=null && errorArray.size()>0){
	%><ul><%
	for (int i = 0; i < errorArray.size(); i++) {
		%><li><%=errorArray.get(i) %></li><%
	}
	%></ul><% 
}
%>
</div>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<li><a title="Chiude la pagina corrente"  class="bottoneLink" onmouseover="window.status='chiudi';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#">CHIUDI</a></li>
			</ul>
	    </div>
	</div>
</div>
</body>
</html>
 
 
 