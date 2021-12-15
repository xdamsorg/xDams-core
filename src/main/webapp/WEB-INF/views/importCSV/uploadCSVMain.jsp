<%@page import="org.xdams.utility.DateUtil"%>
<%@page import="it.highwaytech.db.HierPath"%>
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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@ page errorPage="/error.jsp" %>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	String physDoc = request.getParameter("physDoc");
	CustomPageBean customPageBean = (CustomPageBean)request.getAttribute("customPageBean");
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing(); 
	XMLBuilder builder = customPageBean.getXmlBuilder();
	HierPath laGerarchia = customPageBean.getHierPath(); 
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
		<h2><%=workFlowBean.getArchive().getArchiveDescr()%></h2>
 	 </div>
  </div> 
<form:form id="analysisDirectory" class="needs-validation" enctype="multipart/form-data" action="${contextPath}/importCSV/${workFlowBean.archive.alias}">
<input name="numDocFather" id="numDocFather" type="hidden" value="<%=physDoc%>" data-physDoc="<%=physDoc%>"/>
 <div class="form-group">
  <input type="checkbox" id="hierRemove"/>
	<label for="hierRemove">importa i documenti non gerarchici</label>
 </div>
 <div class="form-group">
	<label for="mailTo">Email (notifica termine operazione)</label>
	<input name="mailTo" id="mailTo" type="text" value="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" class="form-control" required/>
	</div>
	<div class="form-group">
    <label for="fileN">File csv</label>
    <input type="file" name="file" class="form-control-file" id="fileN" accept="csv" required>
  </div>
 	<div class="form-group">
  <button type="submit" class="btn btn-primary">Invia</button>
</div>
 	<%
	String dataCompilatoreCMPN = DateUtil.getDataSystem("dd/MM/yyyy");
	String nomeCompilatoreCMPN = userBean.getName() + " " + userBean.getLastName();
	for (int a = 0; a < theXMLconf.contaNodi("/root/fixedValues/elemento"); a++) {
		String ilNodoCorrente = theXMLconf.valoreNodo("/root/fixedValues/elemento[" + (a + 1) + "]/text()");
		String ilValoreCorrente = theXMLconf.valoreNodo("/root/fixedValues/elemento[" + (a + 1) + "]/@value");
		%>
		<input type="text" name="extraCVSXML_<%=ilNodoCorrente.replaceAll("/",".")%>" value="<%=ilValoreCorrente.replaceAll("@coluiCheInserisce@", nomeCompilatoreCMPN).replaceAll("@quandoColuiInserisce@", dataCompilatoreCMPN)%>" />
		<%
	}
	%>
 </form:form>
 </div>
</body>
<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
	 $(document).ready(function(){
        $('#hierRemove').click(function(){
            if($(this).prop("checked") == true){
              $('#numDocFather').val("-1");
							alert("ATTENZIONE!!!\n ATTIVANDO IL CHECKBOX I DOCUMENTE DEL CVS NON VERRANNO IMPORTATI COME FIGLI DELLA SCHEDA SELEZIONATA");
            } else if($(this).prop("checked") == false){
              $('#numDocFather').val($('#numDocFather').attr("data-physDoc"));
						}
        });
    });
</script>
</html>