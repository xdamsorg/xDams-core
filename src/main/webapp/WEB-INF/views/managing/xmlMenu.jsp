<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);
	String xmlInteraction = managingBean.getXmlInteraction();
%>

<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<link rel="stylesheet" href="${frontUrl}/xd-js/sh/SyntaxHighlighter.css" type="text/css" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<style>
.dp-highlighter {
  white-space: nowrap;
  overflow: visible;
  width: 100%;
  font-size: 11px;
  font-family:Courier New,monospace;
}
</style>
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('nativeXML','${frontUrl}');
dp.SyntaxHighlighter.HighlightAll('code');
</script>
<script type="text/javascript">
		<!--
			function modAvanzata(theInput){
				theObj = document.getElementById(theInput);
				theObjValue = theObj.value;
				theObjName = theObj.name;		
				theObj.parentNode.innerHTML="<input class=\"docEditInputLong\" name=\""+theObjName+"\" value=\""+theObjValue+"\">";
				return true;
			}		
			function impostaAndGo(){
				msg = "<spring:message code="Eseguire_la_modifica_del_file_xml" text="Eseguire la modifica del file xml"/>?<br /><br />";
				$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
			      $('#yes').click(function() {	
			    	 	 document.theForm.submit();
						 xDamsModalMessage('<spring:message code="modifica_record_in_corso" text="modifica record in corso"/>...');
			       }); 
			       $('#no').click(function(){ 
					  xDamsUnblock();
					  return false;
				   });
				
				 
			return false;
			}
			function proiettaModifica(sblocca){
					if(sblocca){self.close();}else{impostaAndGo();}
					return false;
			}
			//-->
	</script>
</head>

<body onload="this.focus()">
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm" method="post"><input type="hidden" name="actionFlag" value="editXml" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<%if(xmlInteraction.equals("view")){
	String docXML = managingBean.getDocXML();
	%><div style="margin:5px;"><pre class="xml:nogutter" name="code"><%= docXML.replaceAll("<br>","\n")%></pre></div>
	<script type="text/javascript">
		dp.SyntaxHighlighter.HighlightAll('code');
	</script>
	<%}else	if(xmlInteraction.equals("edt") && (workFlowBean.getArchive().getRole().equals("1") || workFlowBean.getArchive().getRole().equals("2"))){ 
		String docXML = managingBean.getDocXML();
		if(docXML.startsWith("<?xml ")){
			docXML = StringUtils.substringAfter(docXML,"?>");
		}
		//docXML = docXML.replaceAll("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>","");
%><textarea class="docEditInput" name="docXML" style="width:98%;height:9000px;border:4;margin-top:2px"><%=docXML%></textarea>
	<%} %>
</form>
</div>

<div id="foot">
	<div class="margin_foot">
	<div class="cont_ul2" >	
		<ul class="bottoniMenu">
			<%if(xmlInteraction.equals("edt")){ %>
 			<li><a title="<spring:message code="SALVA" text="SALVA"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="SALVA" text="SALVA"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#"><spring:message code="SALVA" text="SALVA"/></a></li>
 			<%} %>
			<%
				//String ilLink ="ManagingServlet?actionFlag="+MyRequest.getParameter("actionFlag",request)+"&amp;selid="+managingBean.getSelid(); 
			%>
			
			<%if(managingBean.getDocFather()>0){ %>
 			<li><a title="<spring:message code="SUPERIORE" text="SUPERIORE"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="SUPERIORE" text="SUPERIORE"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocFather()%>"><spring:message code="SUPERIORE" text="SUPERIORE"/></a></li>
 			<%} %>
			<%if(managingBean.getDocUpperBrother()>0){ %> 			
 			<li><a title="<spring:message code="PRECEDENTE" text="PRECEDENTE"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="PRECEDENTE" text="PRECEDENTE"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocUpperBrother()%>"><spring:message code="PRECEDENTE" text="PRECEDENTE"/></a></li>
 			<%} %>
			<%if(managingBean.getDocLowerBrother()>0){ %> 			
 			<li><a title="<spring:message code="SUCCESSIVO" text="SUCCESSIVO"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="SUCCESSIVO" text="SUCCESSIVO"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocLowerBrother()%>"><spring:message code="SUCCESSIVO" text="SUCCESSIVO"/></a></li>
 			<%} %>
			<%if(managingBean.getDocFirstSon()>0){ %> 			
 			<li><a title="<spring:message code="INFERIORE" text="INFERIORE"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="INFERIORE" text="INFERIORE"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocFirstSon()%>"><spring:message code="INFERIORE" text="INFERIORE"/></a></li> 			 			 			
 			<%} %>
			<%if(managingBean.getDocPrev()>0){ %> 			
 			<li><a title="<spring:message code="INDIETRO" text="INDIETRO"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="INDIETRO" text="INDIETRO"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocPrev()%>&amp;pos=<%=(Integer.parseInt(managingBean.getPos())-1)%>"><spring:message code="INDIETRO" text="INDIETRO"/></a></li> 			 			 			
 			<%} %>
			<%if(managingBean.getDocNext()>0){ %> 			
 			<li><a title="<spring:message code="AVANTI" text="AVANTI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="AVANTI" text="AVANTI"/>';return true" onmouseout="window.status=''"  href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=<%=MyRequest.getParameter("actionFlag",request)%>&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getDocNext()%>&amp;pos=<%=(Integer.parseInt(managingBean.getPos())+1)%>"><spring:message code="AVANTI" text="AVANTI"/></a></li> 			 			 			
 			<%} %>
			<%
			if(workFlowBean.getArchive().getRole().equals("1") || workFlowBean.getArchive().getRole().equals("2")){
				if(xmlInteraction.equals("view")){
				%>
	 			<li><a title="<spring:message code="MODIFICA" text="MODIFICA"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="MODIFICA" text="MODIFICA"/>';return true" onmouseout="window.status=''" href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=editXml&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getPhysDoc()%>&amp;pos=<%=managingBean.getPos() %>"><spring:message code="MODIFICA" text="MODIFICA"/></a></li>
				<%}else{%>
	 			<li><a title="<spring:message code="VISUALIZZA" text="VISUALIZZA"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="VISUALIZZA" text="VISUALIZZA"/>';return true" onmouseout="window.status=''" href="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html?actionFlag=viewXml&amp;selid=<%=managingBean.getSelid()%>&amp;physDoc=<%=managingBean.getPhysDoc()%>&amp;pos=<%=managingBean.getPos() %>"><spring:message code="VISUALIZZA" text="VISUALIZZA"/></a></li>
				<%} 
			}%>
			 	<li><a title="<spring:message code="chiudi" text="chiudi"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="chiudi" text="chiudi"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#"><spring:message code="chiudi" text="chiudi"/></a></li>
 </ul>
</div>	
</div>
</div>


</body>
</html>
 
 
 







 
 
