<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
 
<%-- <div id="dialog" >
<%=request.getAttribute("workFlowBean") %><br />
<%=request.getAttribute("userBean") %>

<%
Enumeration enumeration = request.getAttributeNames();
while (enumeration.hasMoreElements()) {
	Object object = (Object) enumeration.nextElement();
	//out.println(object+"<br />");
}
%></div> --%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.utility.testing.TestingViewBean"%>
<script type="text/javascript">
//<![CDATA[
document.write('<div class="statusLayerNONVisibile" id="attendere"><blink><spring:message code="attendere" text="attendere..."/></blink><marquee><spring:message code="attendere" text="attendere..."/></marquee></div>');
//]]>
</script>
	<%@include file="inc_header.jsp"%>
	<div id="menu">
		<div class="al_left">
			<span class="ml20"></span>
			<a href="${contextPath}/search/<%=workFlowBean.getArchive().getAlias()%>/query.html" class="v_menu" title="ricerca"><spring:message code="nuova_ricerca" text="nuova ricerca"/></a><img src="${frontUrl}/img/arrow.gif" border="0" alt="<spring:message code="accedi" text="accedi"/>"  class="arrow_spacer" />
			<!-- <a href="../../../help/help.jsp?idAccount=<%=userBean.getAccountRef()%>" class="v_menu" title="help" target="_blank">help</a><img src="${frontUrl}/img/arrow.gif" alt="accedi" border="0" class="arrow_spacer" />-->
		</div>
		<div class="right"><%@include file="inc_ricercaSemplice.jsp"%></div>
	</div>
	<div class="sub_menu">
		<div id="tit_arc"><spring:message code="Benvenuto" text="Benvenuto"/> <%=userBean.getName()%> <%=userBean.getLastName()%> <%if(!(workFlowBean.getArchive().getRole()).equals("")){ %><em>(<spring:message code="livello" text="livello"/>: <%=workFlowBean.getArchive().getRole()%>)</em><%} %> <span class="briciole"><spring:message code="sei_in" text="sei in"/>:  <%=(workFlowBean.getArchive().getGroupName()).equals("")?workFlowBean.getArchive().getArchiveDescr():workFlowBean.getArchive().getGroupName()+"&#160;/&#160;"+workFlowBean.getArchive().getArchiveDescr()%></span></div>
	</div>
