<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
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
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('multiMod','${frontUrl}');
</script>
</head>
<body onload="self.focus()">
<div id="content_multi">
	<div class="m10">elementi processati: <strong><%=elementi%></strong></div>
	<div class="m10">elementi modificati: <strong><%=elementiSuccessi%></strong></div>
	<div class="m10">errori occorsi: <strong><%=elementiErrore%></strong></div>
	<%
		ArrayList errorArray = managingBean.getErrorMsg();
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
			<li><a title="Chiude la pagina corrente"  class="bottoneLink" onmouseover="window.status='chiudi';return true" onmouseout="window.status=''" onclick="parent.reloadLocation();chiudiThisWin()" href="#">CHIUDI</a></li>
		</ul>
	</div>
	</div>
</div>
	</body>
</html>