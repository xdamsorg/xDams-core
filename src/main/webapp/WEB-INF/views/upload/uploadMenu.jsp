<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	confBean.setPageContext(pageContext); 
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	
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
$(document).ready(function(){
    <%
    if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("resize")){
  	  %>top.$("span.ui-dialog-title").html("UPLOAD IMMAGINI");<%
    }else if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("simple")){
  	  %>top.$("span.ui-dialog-title").html("UPLOAD");<%
    }else{
  	  %>//var exts = ['doc','docx','rtf','odt'];<%
    }
    %>	
	
	
 	xDamsUnblock();
    $("form").submit(function() {
      var file = $('input[type="file"]').val();
      <%
      if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("resize")){
    	  %>var exts = ['jpg','tif','tiff','jpeg','gif','png'];<%
      }else if(request.getParameter("uploadType")!=null && request.getParameter("uploadType").equals("simple")){
    	  %>//var exts = ['doc','docx','rtf','odt'];<%
      }else{
    	  %>//var exts = ['doc','docx','rtf','odt'];<%
      }
      %>
      if (file) {
    	  if(exts){
    	        var get_ext = file.split('.');
    	        get_ext = get_ext.reverse();  
    	        if($.inArray(get_ext[0], exts)){
    	            $("span").text("File non valido!").show().fadeOut(2000);
    	            return false;
    	        }    		  
    	  }
        return true;
      }
      return false;
      });
});
</script>
</head>
<body>
 	<form:form modelAttribute="uploadBean" action="${contextPath}/upload/${workFlowBean.alias}/execute.html" method="post" enctype="multipart/form-data">
          <fieldset>
                <legend>Upload</legend>
                 <p> 
                    <form:input path="filedata" type="file" />
                    <form:input path="idRecord" type="hidden"/>
                    <form:input path="destField" type="hidden"/>
                    <form:input path="uploadType" type="hidden"/>
                    <input name="physDoc" type="hidden" value="<%=request.getParameter("physDoc")%>"/>
                    <span></span>
                </p>
 	              <p>
                    <input type="submit" />
                </p>
          </fieldset>
	</form:form>
</body>
</html>