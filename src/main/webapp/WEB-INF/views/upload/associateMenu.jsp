<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.utility.CommonUtils"%>
<%@page import="org.xdams.page.upload.bean.UploadBean"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.io.File"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	UploadBean uploadBean =(UploadBean) request.getAttribute("uploadBean");
	
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
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('upload','${frontUrl}');
$(document).ready(function(){
    <%
    if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("resize")){
  	  %>top.$("span.ui-dialog-title").html("UPLOAD IMMAGINI");<%
    }else if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("simple")){
  	  %>top.$("span.ui-dialog-title").html("UPLOAD");<%
    }else if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("associate")){
  	  %>top.$("span.ui-dialog-title").html("ASSOCIATE");<%
    }
    %>	
 	xDamsUnblock();
 	$( ".folderRouting" ).click(function() {
 		$("#pathToView").val($(this).attr("data-url"));
 		$("form").submit();
 	});
 	
 	$( ".fileRouting" ).click(function() {
 		top.$("input[name='<%=CommonUtils.escapeJqueryName(uploadBean.getDestField())%>']").val($(this).attr("data-url"));
 		top.$dialog.dialog('close');
 	});
  
});
</script>
<style type="text/css">a.doceditActionLink {
    background-color: #DDDDDD;
    border: 1px solid #8B8C8F;
    border-radius: 5px;
    color: #000000;
   /*   display: block;
  float: left;*/
    font-size: 12px;
    font-weight: normal;
    margin-right: 5px;
    padding: 2px 6px;
    text-decoration: none;
  /*  text-transform: uppercase; */
}
.riga_posiziona_multi {
    border-bottom: 0px dotted #8B8C8F;
    margin: 5px 0 10px 5px;
}</style>
</head> 
<body>
<div id="content_multi"> 
<div class="riga_posiziona_multi"><span><a href="#nano" class="doceditActionLink folderRouting" data-url="<%=request.getParameter("pathToView")==null || request.getParameter("pathToView").equals(uploadBean.getAssociatePathDir()) ? uploadBean.getAssociatePathDir() : StringUtils.substringBeforeLast(request.getParameter("pathToView"), "\\") %>"><spring:message code="torna_indietro" text="torna indietro"/></a></span></div>

 	<form:form modelAttribute="uploadBean" id="associateForm" action="${contextPath}/associate/${workFlowBean.alias}/associateMenu.html" method="post" enctype="multipart/form-data">
          <fieldset>
  			<%
  			if(request.getAttribute("files")!=null){
  	  			File[] files = (File[])request.getAttribute("files");
  					for (File file : files) {
  						if(file.isDirectory()){
  							%><div style="padding:5px;"><img src="${frontUrl}/img/tree/folder_node_chiuso.gif"/><a href="#nano" class="doceditActionLink folderRouting" data-url="<%=file.getPath()%>"><%=file%></a></div><%	
  						}else if(file.isFile()){
  							String pathToView = (StringUtils.difference(uploadBean.getAssociatePathDir(), file.getAbsolutePath())).replaceAll("\\\\", "/");
  							%><div style="padding:5px;"><img src="${frontUrl}/img/tree/file_node_chiuso.gif"/><a href="#nano" class="doceditActionLink fileRouting" data-url="<%=pathToView%>"><%=pathToView%></a></div><%
  						}
  					}  				
  				
  			}else{
  				%><div style="padding:5px;"><spring:message code="ATTENZIONE_IMPOSSIBILE_LEGGERE_LA_DIRECTORY" text="ATTENZIONE IMPOSSIBILE LEGGERE LA DIRECTORY"/></div><%
  			}

			%>         
                 <p> 
                    <form:input path="idRecord" type="hidden"/>
                    <form:input path="destField" type="hidden"/>
                    <form:input path="uploadType" type="hidden"/>
                    <form:input path="flagOriginalFileName" type="hidden"/>
                    <form:input path="destOriginalFileName" type="hidden"/>
                  	<form:input path="xPathPrefix" type="hidden"/>
                    <input name="physDoc" type="hidden" value="<%=request.getParameter("physDoc")%>"/>
                    <input name="pathToView" id="pathToView" type="hidden" value=""/>
                    <span></span>
                </p>
          </fieldset>
	</form:form> 
</div>	
</body>
</html>
 
