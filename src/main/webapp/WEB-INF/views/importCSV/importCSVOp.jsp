<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.page.form.bean.CustomPageBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.xdams.xml.builder.XMLBuilder" %>
<%@ page errorPage="/error.jsp" %>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	Object importCSVOp = request.getAttribute("importCSVOp");
%>
<!DOCTYPE html> 
<html>
<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<script src="${frontUrl}/bootstrap-4/js/jquery-3.6.0.min.js"></script>
<script src="${frontUrl}/bootstrap-4/js/popper.min.js"></script>
<script src="${frontUrl}/bootstrap-4/js/bootstrap.min.js"></script>
<link href="${frontUrl}/bootstrap-4/css/bootstrap.min.css" rel="stylesheet" />
</head> 
<body>
<div class="container">
	<div class="row top-buffer">
		<div class="col-md-12">
		<%
		if(importCSVOp!=null && importCSVOp.equals("success")){
		%>
				 <h2>Operazione iniziata con successo, riceverai una mail al termine delle operazioni</h2>
		<%
				 
		}else if(importCSVOp!=null && importCSVOp.equals("error")){
		%>
				<h2>Errore nella procedura, messaggio: ${importCSVOpMsg}</h2>
		<%
		}
		%>
		
	 </div>
  </div> 
</body>
</html>