<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="org.xdams.page.multiarchive.bean.MultiArchiveBean"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@page import="org.xdams.xw.paging.QRParser"%>
<%@page import="org.xdams.page.view.bean.TitleBean"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.page.query.bean.QueryBean"%>
<%@page import="java.util.List"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	ManagingBean managingBean = (ManagingBean)session.getAttribute(workFlowBean.getManagingBeanName());
	List<MultiArchiveBean> multiArchiveBeans = (List<MultiArchiveBean>)request.getAttribute("multiArchiveBeans");
%><head>
<title>xDams - title - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<link href="${frontUrl}/css/jquery/jquery.qtip.min.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<link rel="stylesheet" href="${frontUrl}/css/jquery/jgrowl.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${frontUrl}/css/jquery/jqac.css" /> 
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}',theExt:'.png',pageValue:'title${workFlowBean.alias}${userBean.id}',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('title','${frontUrl}');
    jQuery(document).ready(function(){
		onLoadActions('esito');
		$('.subMitAnchor').click(function() {
			var dataFormName = $(this).attr("data-formName"); 
			$('#'+dataFormName).submit();
			return false;
		});
		
	});
	
	function orderBy(obj){
	try{
	<%
	String queryStr = "";
	if(session.getAttribute("arrQueryBean") != null){
		
		List arrQueryBean = (ArrayList)session.getAttribute("arrQueryBean");
		QueryBean queryBean = (QueryBean)arrQueryBean.get(arrQueryBean.size()-1);		
		String testDB = queryBean.getDb();
		if(testDB.equals(workFlowBean.getAlias())){
			queryStr = queryBean.getQuery();
			try{queryStr = queryStr.substring(queryStr.indexOf("]]")+2,queryStr.length()-1);}catch(Exception e){}
			queryStr = queryStr.replaceAll("\"","&quot;");
		}
	}%>
	//alert('<%=queryStr%>');
	document.location.href='${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?sorting='+obj.value+'&qlphrase=<%=queryStr.replaceAll("&quot;","\"")%>';
	
	}catch(e){
		alert("<spring:message code="attenzione_errore" text="attenzione errore"/>");
	}
	
	}
	</script>
</head>

<body>
	<div id="headPageBig">
		<%@include file="../common/inc_menu.jsp" %>
 		<div class="sub_sub_menu">
			<div class="left_top2"></div>
		</div>
	</div>

	<div class="contentPageSxEs" id="contentPageSx">
		<div id="cont_campi">
			<ul class="elArc">
			 		<% 
			 			for(int i=0;i < multiArchiveBeans.size();i++){
			 				MultiArchiveBean multiArchiveBean = multiArchiveBeans.get(i);
			 				Archive archive = multiArchiveBean.getArchive();
			 				int recordFound = multiArchiveBean.getRecordFound();
			 				%>
			 				<form id="theForm_<%=archive.getAlias() %>" action="${contextPath}/search/<%=archive.getAlias()%>/title.html" target="<%=archive.getAlias()%>" method="post">
			 					<li>
									<img src="${frontUrl}/img/workspace/<%=archive.getIco()%>" class="icLista" alt="<%=archive.getArchiveDescr()%>" />
									<%if(recordFound!=0){ %><a href="#none" data-formName="theForm_<%=archive.getAlias()%>" class="subMitAnchor" target="<%=archive.getAlias()%>"><%} %><%=archive.getArchiveDescr()%> <spring:message code="documenti_trovati" text="documenti trovati"/>: <%=recordFound %><%if(recordFound!=0){ %></a><%} %>
										<%
										Enumeration enumeration = request.getParameterNames();
										while (enumeration.hasMoreElements()) {
											String paramName = (String) enumeration.nextElement();
											%><input type="hidden" name="<%=paramName%>" value="<%=request.getParameter(paramName)%>"/><%
											//out.println(paramName+"<br />");
										}
										%>
								</li>
							</form>	
			 				<% 
			 			}
			 		%>
			 </ul>		
		</div>
	</div>
	<div class="schedaBreve">
		
	</div>
 		<div id="footPage">
			<%@include file="../common/inc_sub_menu.jsp"%>
		</div>
</body>
</html>

<%!
public String extractSortValue(JspWriter out, XMLBuilder theXMLconf){
	String prefix = "/root/query/sort/element";
	int numField = theXMLconf.contaNodi(prefix);
 	String outputField = "";
	outputField += "<option name=\"sorting\" value=\"\"></option>";
	for(int z=0 ;z < numField; z++){
		try{
  

		String nameValue = theXMLconf.valoreNodo(prefix+"["+(z+1)+"]/text()");
		String attrLabel = theXMLconf.valoreNodo(prefix+"["+(z+1)+"]/@label");
//		<option value=\"XML\">crescente</option>
		outputField += "<option name=\"sorting\" value=\""+nameValue+"\">"+attrLabel+"</option>";
		if(numField-1==z){
			outputField += "</div>";
		}
		//arrOutputSortField.add(outputField);
		}catch(Exception e){
			try{
				out.println("ERRORE IN extractSortValue");
			}catch(Exception e1){
				
			}
				
		}
	}	
	return outputField;
}

%>

