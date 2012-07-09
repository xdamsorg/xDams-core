<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	MyRequest myRequest = new MyRequest(request);
	String hierCount = myRequest.getParameter("hierCount");
	String docStart = myRequest.getParameter("docStart");
	String docToggle = myRequest.getParameter("docToggle");
	String openDoc = myRequest.getParameter("openDoc");
	String perpage = myRequest.getParameter("perpage","10");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%> - Browser Gerarchico</title>
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link href="${frontUrl}/css/jquery/jquery.qtip.min.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
var globalOption = {frontPath:'${frontUrl}',pageValue:'hier${workFlowBean.alias}${userBean.id}',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('hierBrowser','${frontUrl}');
</script>
<%	 
try{
	perpage = WebUtils.getCookie(request, "hier"+workFlowBean.getAlias()+userBean.getId()).getValue();
}catch(Exception e){
	//out.println("ERRORE");
} 
%>
</head>
<body>
	<div id="headPageBig">
		<%@include file="../common/inc_menu.jsp" %>
		<div class="sub_sub_menu">
			<div class="tot_pag">visualizza <select class="scelta_sel" id="selectVis" onchange="return lanciaPerpageHier('contentPageSx',this);"><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option><option value="100">100</option></select>  elementi per pagina</div>
			<div class="buttonRefresh"><a href="#n" onclick="return reloadLocation()" title="aggiorna la pagina"><img src="${frontUrl}/img/aggiorna.gif" border="0" alt="aggiorna la pagina" /></a>&#160;&#160;<a href="#" title="salva elementi per pagina" onclick="addCookie(document.getElementById('selectVis').value)"><img src="${frontUrl}/img/save_as.gif" border="0" alt="salva elementi per pagina" vspace="2" /></a></div>
			<script type="text/javascript">
			//<![CDATA[
				document.getElementById('selectVis').value='<%=perpage%>';
			//]]>
			</script>
		</div>
	</div>
	<div class="contentPageSx">
		<iframe id="contentPageSx" height="100%" width="100%" src="${contextPath}/hier/<%=workFlowBean.getAlias()%>/tree.html?docStart=<%=docStart%>&amp;docToggle=<%=docToggle%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage",perpage)%>&amp;docCount=<%=myRequest.getParameter("perpage",perpage)%>"   frameborder="0"  name="leftArea"></iframe>
	</div>
	<div class="schedaBreve">
		<iframe id="schedaBreve" height="100%" width="100%"  name="schedaBreve" src="${contextPath}/viewTab/<%=workFlowBean.getAlias()%>/shortTab.html?physDoc=<%=docStart%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage",perpage)%>&amp;docCount=<%=myRequest.getParameter("perpage",perpage)%>" frameborder="0"></iframe>
	</div>
	<div id="footPage">
		<%@include file="../common/inc_sub_menu.jsp"%>
	</div>
</body>
</html>
