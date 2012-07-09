<%@page import="org.xdams.utility.CommonUtils"%>
<%@page import="org.xdams.xw.utility.Key"%>
<%@page import="java.util.List"%>
<%
	response.setContentType("text/plain");
	response.setHeader("Content-Disposition", "attachment; filename=chiavi"+CommonUtils.titleUrl((String)request.getAttribute("vocabolario_alias"), "UTF-8", 100)+".txt");
	response.setHeader("Content-Transfer-Encoding", "ascii");
	int totChiavi = 0;
	String vocabolarioType = (String) request.getAttribute("vocabolario_tipology");
	if (request.getAttribute("vocabolarioList") != null && ((List<Key>) request.getAttribute("vocabolarioList")).size() > 0) {
		List<Key> vocabolarioVector = (List<Key>) request.getAttribute("vocabolarioList");

		for (int i = 0; i < vocabolarioVector.size(); i++) {
 				Key key = (Key) vocabolarioVector.get(i);
%><%=key.key%>
<%
	}
%>
<%
	} else {
%>
Nessun termine trovato nel vocabolario
<%
	}
%>