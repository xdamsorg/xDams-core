<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="org.xdams.utility.DateUtil"%>
<%@page import="org.xdams.xw.XWConnection"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="it.highwaytech.db.QueryResult"%>
<%@page import="org.xdams.xmlengine.connection.manager.ConnectionManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.utility.StringsUtils"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page trimDirectiveWhitespaces="true"%>
<%
	ConnectionManager connectionManager = new ConnectionManager();
	UserBean userBean = (UserBean) request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	Archive archiviBean = workFlowBean.getArchive();
	XWConnection xwConnection = null;
	try {
		xwConnection = connectionManager.getConnection(archiviBean.getAlias(), archiviBean.getHost(), archiviBean.getPort(),  archiviBean.getPne());
		%>invalidate host ok!<%
	} catch (Exception e) {
		out.println("ERRORE: " + e.getMessage());

	} finally {
		connectionManager.closeConnection(xwConnection);
		xwConnection.invalidateHost();
	}
%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

