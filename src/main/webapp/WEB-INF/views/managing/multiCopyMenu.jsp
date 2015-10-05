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
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLValControllati = confBean.getTheXMLValControllati();
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
loadJsBusiness('multiMod','${frontUrl}');
</script> 
 	<script type="text/javascript">
// 	<!--
 	$(document).ready(function(){
		xDamsUnblock();
	})
	function impostaAndGo(){
		if(confirm('Confermi la duplicazione?')){
			 document.getElementById('STATO').className = 'statusLayerVisibile';
				xDamsModalMessage('<spring:message code="duplicazione_record_in_corso" text="duplicazione record in corso"/>...');
			 document.theForm.submit();
		 }
		return false
	}
		
	function proiettaModifica(sblocca){
			if(sblocca){self.close();}else{impostaAndGo();}
			return false
		}
		//-->
		</script>

	</head>
<body >
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="multiCopy" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div id="STATO" class="statusLayerNONVisibile"><spring:message code="Attendere" text="Attendere"/>: <strong><spring:message code="duplicazione_record_in_corso" text="duplicazione record in corso"/>...</strong></div>
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="ramo_selezionato" text="ramo selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>	
<div class="m10">
<div><input type="radio" name="whichTo" value="selected" checked="checked"><strong><spring:message code="selezione_multipla" text="seleziona multipla"/></strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="elementi" text="elementi"/>)</div> <br/>
Sposta come :
<br />
<input type="radio" name="applyTo" value="cut_as_son" checked="checked"> <strong><spring:message code="figlio" text="figlio"/></strong><br />
<input type="radio" name="applyTo" value="cut_as_before"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br />
<input type="radio" name="applyTo" value="cut_as_after"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br />
<br /> 
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