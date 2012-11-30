<%@page import="org.xdams.utility.CommonUtils"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.page.upload.bean.UploadBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	confBean.setPageContext(pageContext); 
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean");
	UploadBean uploadResponse =(UploadBean) request.getAttribute("uploadResponse");
	
%>
<html>
<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/uploadify/uploadify.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('upload','${frontUrl}');
</script>
</head>
<body>
  <%if(uploadResponse.getResultError().toString().equals("")){%>
	<h1>Upload eseguito con successo</h1>
  	<script type="text/javascript">
  		top.$("input[name='<%=CommonUtils.escapeJqueryName(uploadResponse.getDestField())%>']").val('<%=StringEscapeUtils.escapeEcmaScript(uploadResponse.getResult().toString())%>');
  		//top.$("input[name='.c.did.dao\\[@type=\\'documenti grafici\\'\\]\\[1\\].resource.text()']").val('ciao');
  	</script>
  <%}if(!uploadResponse.getResultError().toString().equals("")){%>
  	<h1>Errore in fase di upload <%=uploadResponse.getResultError()%></h1>
  <%}%>
</body>
</html>