<div id="header"><img src="${frontUrl}/img/logo.gif"  border="0" align="left" alt="xDams"/><div class="titolo_arc"><%=userBean.getAccount().getDescrAccount()%></div></div>
<div style="display:none;">
<form action="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html" method="POST" id="searchForm" name="searchForm">
<input type="submit"/>
<%
java.util.Map<String, String[]> map = request.getParameterMap();
for (java.util.Map.Entry<String, String[]> entry : map.entrySet()) {
	out.println("<input type=\"text\" name=\""+org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(entry.getKey())+"\"");
	for (String strings : entry.getValue()) {
		out.println(" value=\""+org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(strings.trim())+"\"");
	}	
	out.println("/>");
}
%>
</form>
</div>