<%@page import="java.util.Map.Entry"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="org.xdams.admin.bean.CreateArchiveResultBean"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>xDams - creazione archivi</title>
<meta name="description" content="">
<meta name="author" content="claudia">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<script src="${frontUrl}/bootstrap-3/assets/jquery.min.js"></script>
<link href="${frontUrl}/bootstrap-3/css/bootstrap.min.css" rel="stylesheet" />
<link href="${frontUrl}/css/signin.css" rel="stylesheet">
<script src="${frontUrl}/bootstrap-3/js/bootstrap.min.js"></script>
<script src="${frontUrl}/bootstrap-3/assets/form-validator/jquery.form-validator.min.js"></script>
</head>
<%
CreateArchiveResultBean createArchiveResultBean = (CreateArchiveResultBean)request.getAttribute("createArchiveResultBean");
%>
<body> 
	<div class="container">
		<div class="page-header">
			<h1 class="text-center">
				<img src="${frontUrl}/img/logo.gif" />
			</h1>
			<%if(!createArchiveResultBean.isCreated()){ %>
			<div class="text-center alert alert-error">
				<h3 class="text-center">Errore! <%=createArchiveResultBean.getMsg() %></h3>
			</div>
			<%}else{ %>
			 <div class="text-center alert alert-success">
			 	<h3>Creazione Archivio ${archive.archiveDescr} (${archive.alias}) eseguita con successo.</h3>
				<h3>Ricordati di associare l'archivio all'utente</h3>
			 </div>
			
			<%} %>
		</div>
		<div class="page-header">
			<a class="btn btn-default text-uppercase text-center" onclick="window.close()">CLOSE</a>
		</div>
	</div>
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg">
			<div class="alert alert-success">
				<strong>Creazione Archivio in corso!</strong>
			</div>
		</div>
	</div>
</body>
</html>