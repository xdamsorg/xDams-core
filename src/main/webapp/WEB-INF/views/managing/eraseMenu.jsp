<%@page import="org.xdams.utility.bind.BindUtil"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="java.util.ArrayList"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
 	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);
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
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('eraseMenu','${frontUrl}');
$(document).ready(function(){
 	xDamsUnblock();
});
function impostaAndGo(msg){
      $.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ conferma ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ annulla ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} }); 
      $('#yes').click(function() {	
	      msg = 'Attenzione!<br /><br />sta per essere effettuata la cancellazione <br />sicuro?';
	      $.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ conferma ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ annulla ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} }); 
	      $('#yes').click(function() {	
				 document.theForm.submit();
				 xDamsModalMessage('cancellazione in corso...');
	       }); 
	       $('#no').click(function(){ 
			  xDamsUnblock();
			  return false;
		   }); 		
	  
	         }); 
       $('#no').click(function(){ 
		  xDamsUnblock();
		  return false;
	  }); 		
		return false;
}
		
function proiettaModifica(){
	selectedRadio = false;
	msg = "Attenzione!<br /><br />sta per essere effettuata la cancellazione <br />";
	for(var i=0;i<document.theForm.length;i++){
		if(document.theForm.elements[i].checked){
		selectedRadio=true;
		msg+=jQuery(document.theForm.elements[i]).attr("rel");
		}
	}
	msg +="...<br /><br /> il processo è IRREVERSIBILE!";
	if(!selectedRadio){
		xDamsModalAlert('Specificare un\'azione!<br /><br />');
		return;		
	}
	impostaAndGo(msg);
	return false;
}
</script>
</head>
<body onload="self.focus()">
<script type="text/javascript">xDamsModalMessage('analisi in corso...');</script>
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="erase" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div id="STATO" class="statusLayerNONVisibile">Attendere: <strong>eliminazione record in corso...</strong></div>
<div id="content_multi">
	<div class="riga_posiziona_multi">elemento selezionato: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
	<div class="m10">
	<div  >elimina:</div>
	<div ><br />
		<input type="radio" name="applyTo" value="hier" rel="della scheda selezionata<%if(managingBean.getNumElementiHier()>1){%> e di TUTTI i <%=(managingBean.getNumElementiHier())-1%> elementi gerarchicamente collegati<%} %>"> <strong>scheda selezionata e tutto il ramo</strong> (<%=(managingBean.getNumElementiHier())%> elementi)<br /><br />
		<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input type="radio" name="applyTo" value="selected" rel="di <%=(managingBean.getListPhysDoc()).size()%> schede selezionate e di TUTTI gli elementi gerarchicamente collegati"> <strong>seleziona multipla</strong> (<%=(managingBean.getListPhysDoc()).size()%>  elementi e tutti gli elementi gerarchicamente collegati)<br /><%} %>
		<%if((managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid" rel="di <%=( managingBean.getNumElementi())%> schede e di TUTTI gli elementi gerarchicamente collegati"> <strong>ricerca corrente</strong> (<%=( managingBean.getNumElementi())%> schede e gli elementi gerarchicamente collegati)<br /><%} %>
		<%if(false && (managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling"> <strong>fratelli precedenti</strong> e gli elementi gerarchicamente collegati<br /><%} %>
		<%if(false && (managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling"> <strong>fratelli successivi</strong> e gli elementi gerarchicamente collegati<br /><%} %>
		<%if(false && (managingBean.getNumElementiHier())>1){ %><input type="radio" name="applyTo" value="hierfalse"> <strong>solo le schede gerarchicamente collegate</strong> (<%=(managingBean.getNumElementiHier()-1)%> elementi)<br /><%} %>
		</div>
	</div>
</div>
 
</form>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<li><a title="Esegui la richiesta"  class="bottoneLink" onmouseover="window.status='esegui';return true" onmouseout="window.status=''" onclick="return proiettaModifica();"  href="#">ESEGUI</a></li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>