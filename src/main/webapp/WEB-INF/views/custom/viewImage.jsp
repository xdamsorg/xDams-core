<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.xdams.xml.builder.XMLBuilder" %>
<%@page import="org.xdams.page.form.bean.CustomPageBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="A simple jQuery image viewing plugin.">
<title>xDams viewer</title>
<link rel="stylesheet" href="${contextPath}/resources/viewer/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/resources/viewer/dist/viewer.css">
<link rel="stylesheet" href="${contextPath}/resources/viewer/css/style.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<%       	
ConfBean confBean = (ConfBean)request.getAttribute("confBean");
UserBean userBean = (UserBean)request.getAttribute("userBean");
WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
CustomPageBean customPageBean = (CustomPageBean)request.getAttribute("customPageBean");
XMLBuilder theXMLConfMedia = confBean.getTheXMLConfMedia();
XMLBuilder builder = customPageBean.getXmlBuilder();
List<String> listaJpg = new ArrayList<String>();

// fotografico
int countGrp = builder.contaNodi("/c/did/physdesc");
for (int i = 0; i < countGrp; i++) {
	int countIMG = builder.contaNodi("/c/did/physdesc[" + (i + 1) + "]/extptr");
	for (int x = 0; x < countIMG; x++) {
		String temp = builder.valoreNodo("/c/did/physdesc[" + (i + 1) + "]/extptr[" + (x + 1) + "]/@href");
		listaJpg.add(temp);
	}
}

// audiovisivo
countGrp = builder.contaNodi("/c/daogrp/extrefloc");
for (int i = 0; i < countGrp; i++) { 
	String temp = builder.valoreNodo("/c/daogrp/extrefloc[" + (i + 1) + "]/@href");
	listaJpg.add(temp);
}

// disegni
countGrp = builder.contaNodi("/c/daogrp/daoloc");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/c/daogrp/daoloc[" + (i + 1) + "]/@href");
	listaJpg.add(temp);
}

// guida
countGrp = builder.contaNodi("/c/did/dao/resource");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/c/did/dao[" + (i + 1) + "]/resource/text()");
	listaJpg.add(temp);
}

// oggetti
countGrp = builder.contaNodi("/c/altformavail[@encodinganalog='FTA']/list[@type='simple']/item");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/c/altformavail[@encodinganalog='FTA']/list[@type='simple']/item[" + (i + 1) + "]/extptr/@href");
	listaJpg.add(temp);
}

// biblioteca
countGrp = builder.contaNodi("/mods/location/url");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/mods/location/url[" + (i + 1) + "]/text()");
	listaJpg.add(temp);
}

// temi
countGrp = builder.contaNodi("/eac/condesc/desc/persdesc/funactrels/funactrel/descnote/extptr[@type='generico']");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/eac/condesc/desc/persdesc/funactrels/funactrel/descnote/extptr[@type='generico'][" + (i + 1) + "]/@href");
	listaJpg.add(temp);
}

// lido
countGrp = builder.contaNodi("/lido/administrativeMetadata/resourceWrap/resourceSet");
for (int i = 0; i < countGrp; i++) {
	String temp = builder.valoreNodo("/lido/administrativeMetadata/resourceWrap/resourceSet[" + (i + 1) + "]/resourceRepresentation/linkResource/text()");
	listaJpg.add(temp);
}

// clear list images
List<String> listaJpgNew = listaJpg;
listaJpg = new ArrayList<String>();
for (int i = 0; i < listaJpgNew.size(); i++) {
	String imgVal = listaJpgNew.get(i).toLowerCase();
	if(imgVal.endsWith(".jpg") || imgVal.endsWith(".png") || imgVal.endsWith(".gif")){
		listaJpg.add(listaJpgNew.get(i));
	}
}

String prefixOriginal = request.getContextPath()+"/attach/"+ userBean.getAccountRef()+"/"+workFlowBean.getAlias()+"/high";
String prefixThumb = request.getContextPath()+"/attach/"+ userBean.getAccountRef()+"/"+workFlowBean.getAlias()+"/high";                                    
    
int mediaToView = Integer.parseInt(request.getParameter("mediaToView"));
%> 
</head>
<body onload="aprivisualizzatore()">

  <!-- Content -->
  <div class="container">
    <div class="row">
      <div class="col-sm-8 col-md-6">
        <div class="docs-galley">        
          <ul class="docs-pictures clearfix">
          <%
			for (int i=0; i<listaJpg.size(); i++){
				if(i==mediaToView){
					%>
					<li class="nascosto"><img id="primo" data-original="<%=prefixOriginal%><%=listaJpg.get(i)%>" src="<%=prefixThumb%><%=listaJpg.get(i)%>"></li>         
					<%          		  
				}
				else{
					%>
					<li class="nascosto"><img data-original="<%=prefixOriginal%><%=listaJpg.get(i)%>" src="<%=prefixThumb%><%=listaJpg.get(i)%>"></li>         
					<%
				}
			}          
          %>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <!-- Scripts -->
  <script src="${contextPath}/resources/viewer/assets/js/jquery.min.js"></script>
  <script src="${contextPath}/resources/viewer/assets/js/bootstrap.min.js"></script>
  <script src="${contextPath}/resources/viewer/dist/viewer.js"></script>
  <script src="${contextPath}/resources/viewer/js/main.js"></script>
<script>
function aprivisualizzatore(){
	$("#primo").click();
}
</script>
</body>
</html>