<%@page import="java.util.List"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.managing.bean.ModifyAutherBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean)request.getAttribute("managingBean") ;
	ModifyAutherBean modifyAutherBean = (ModifyAutherBean)request.getAttribute("modifyAutherBean");
 	int elementi = modifyAutherBean.getNumElementi();
	int elementiErrore = modifyAutherBean.getDocErrori();
	int elementiSuccessi = modifyAutherBean.getDocSuccessi();
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
var globalOption = {frontPath:'${frontUrl}' ,theArch:'${workFlowBean.alias}', contextPath:'${contextPath}'};
loadJsBusiness('modifyAuther','${frontUrl}');
</script>
<body>
<div id="content_multi">
	<div class="riga_posiziona_multi"><%=MyRequest.getParameter("nameToChange", request).trim()%></div>	
	<div class="m10">elementi processati: <strong><%=elementiSuccessi%></strong><br>
	 errori occorsi: <strong><%=elementiErrore%></strong></div>
	<%
	List errorArray = modifyAutherBean.getErrorMsg();
	if(errorArray!=null && errorArray.size()>0){
		modifyAutherBean.setErrorMsg(null);
		%><ul><%
		for (int i = 0; i < errorArray.size(); i++) {
			%><li><%=errorArray.get(i) %></li><%
		}
		%></ul><% 
	}
	%>			
	<%
// 	out.println(modifyAutherBean.getArrModifyAutherBean());
	
	for (int i = 0; i < modifyAutherBean.getArrModifyAutherBean().size(); i++) {
		ModifyAutherBean autherBean = (ModifyAutherBean) modifyAutherBean.getArrModifyAutherBean().get(i);
		%>
		<div class="riga_posiziona_multi">
				<strong><%=autherBean.getArchivioDescr() %></strong><br/>
				numero occorrenze modificate : <strong><%=autherBean.getElementToFindBean().getNumTotXPathToChange() %></strong><br/>
				<%=autherBean.getElementToFindBean().getXPathToChange() %><br/>
		</div>
		<%
 
	}
	/*
											autherBean.setArchivioAlias(archiviBean.getTheArch());
											autherBean.setArchivioDescr(descrArchive);
											autherBean.setElementToFindBean(elementToFindBean);
											autherBean.setQuery(query);
											autherBean.setNumElementi(qr.elements);
											autherBean.setDocSuccessi(modificati);
											autherBean.setDocErrori(errori);
											modifyAutherBean.addArrModifyAutherBean(autherBean);
	*/
	%>		
</div>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			
			</ul>
		</div>
	</div>
</div>

	</body>
</html>