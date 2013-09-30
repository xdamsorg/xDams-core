<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
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
loadJsBusiness('multiMove','${frontUrl}');
</script>
  	<script type="text/javascript">
// 	<!--
	function impostaAndGo(){
  	    document.theForm.submit();
	 	return false;
	}
		
	function proiettaModifica(sblocca){
		<%if((managingBean.getListPhysDoc()).size()==0){%>
		msg = "Attenzione!<br /><br />nessun documento selezionato<br />";
		xDamsModalAlert(msg);
		return false;
		<%}%>
		
		msg = "Attenzione!<br /><br />sta per essere effettuato lo spostamento dei documenti<br />";
		$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ conferma ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ annulla ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
		$('#yes').click(function() {	
	    	  impostaAndGo();
			 xDamsModalMessage('spostamento in corso...');
	      }); 
		  $('#no').click(function(){ 
			  xDamsUnblock();
			  return false;
		  });
 			return false
		}
		//-->
		</script>

	</head>
<body >
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="multiMove" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div id="content_multi">
<div class="riga_posiziona_multi">ramo selezionato: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>	
<div class="m10">
<div><input type="radio" name="whichTo" value="selected" checked="checked"><strong>seleziona multipla</strong> (<%=(managingBean.getListPhysDoc()).size()%>  elementi)</div> <br/>
Sposta come :
<br />
<input type="radio" name="applyTo" value="cut_as_son" checked="checked"> <strong>figlio</strong><br />
<input type="radio" name="applyTo" value="cut_as_before"> <strong>fratelli precedenti</strong><br />
<input type="radio" name="applyTo" value="cut_as_after"> <strong>fratelli successivi</strong><br />
<br /> 
</div>
</div>
</form>
<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<li><a title="Esegui la richiesta"  class="bottoneLink" onmouseover="window.status='esegui';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#">ESEGUI</a></li>
	 			<li><a title="Chiude la pagina corrente"  class="bottoneLink" onmouseover="window.status='chiudi';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#">CHIUDI</a></li>
			</ul>
		</div>
	</div>
</div>


	</body>
</html> 