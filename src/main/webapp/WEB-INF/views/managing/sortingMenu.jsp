<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
 <%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	confBean.setPageContext(pageContext);
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean)request.getAttribute("managingBean") ;
	//prendi valori controllati
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
	int elementi = managingBean.getNumElementi();
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
loadJsBusiness('sortingMenu','${frontUrl}');
</script>
<script type="text/javascript">
// 	<!--
function impostaAndGo(iParametri)
{
	document.theForm.sortingCriteria.value=iParametri;
	document.theForm.submit();
	return false;
}

function proiettaModifica(){
	selectedRadio = false;
	iParametri = "";
		for(var i=0;i<document.optionForm.length;i++){
			if(document.optionForm.elements[i].value != '')
			{
				if(!(document.optionForm.elements[i].name.indexOf('verso') > 0)){
					if(iParametri != '')
						iParametri += ',';
					iParametriTemp = document.optionForm.elements[i].value;
					if(document.optionForm.elements[i+1].name.indexOf('verso') > 0 && document.optionForm.elements[i+1].value == '0'){
						iParametri += iParametriTemp.replace('XML','xml');
					}
					else
						iParametri += iParametriTemp
				}
			}
		}
	if(iParametri == ''){
		xDamsModalAlert('Scegliere almeno un parametro di riordinamento!<br /><br />');
		return;		
	}
	msg = "Attenzione!<br /><br />sta per essere effettuato il riordino del ramo<br />";
	$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ conferma ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ annulla ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
	$('#yes').click(function() {	
    	  impostaAndGo(iParametri);
		 xDamsModalMessage('riordino in corso...');
      }); 
	  $('#no').click(function(){ 
		  xDamsUnblock();
		  return false;
	  });
	
	return false;
}
//-->
</script>

	</head>
<body onload="self.focus()">
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm">
<input type="hidden" name="actionFlag" value="sorting" />
<input type="hidden" name="sortingCriteria" value="" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
</form>
<div id="content_multi">
	<div class="riga_posiziona_multi">elemento selezionato: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
	<div class="riga_posiziona_multi">elementi da processare: <strong><%=elementi%></strong></div>
<div class="m10">
<form name="optionForm" action="?">
	riordina gli elementi per:<br/><br/><%

	String externalPath = "/root/param/elemento[@id='riordina']/opzione";
	int nodiOpzioni = theXMLconf.contaNodi(externalPath);
	String theOptions = "<option value=\"\">scegli...</option>";
	for (int i = 0; i < nodiOpzioni; i++) {
		
		String ilPercorso = theXMLconf.valoreNodo("/root/param/elemento[@id='riordina']/opzione["+(i+1)+"]/@value");
		if(ilPercorso.indexOf("xpart")==-1)
			ilPercorso = "XML(xpart:"+ilPercorso+")";
		theOptions += "<option value=\""+ilPercorso+"\">"+theXMLconf.valoreNodo("/root/param/elemento[@id='riordina']/opzione["+(i+1)+"]/text()")+"</option>";
			
	}
	int totParameters = 4;
	for(int i = 0;i<totParameters;i++)
	{
		%>
		<select name="ordina<%=i%>"><%=theOptions%></select>
		<select name="ordina<%=i%>verso"><option value="1">crescente</option><option value="0">decrescente</option></select>
		<br /><br />
		<%
	}
%>
</form></div>
</div>

<div id="foot">
	<div class="margin_foot">
 	<div class="cont_ul2">	
		<ul class="bottoniMenu" >
 			<li><a title="Esegui la richiesta"  class="bottoneLink" onmouseover="window.status='esegui';return true" onmouseout="window.status=''" onclick="return proiettaModifica();"  href="#">ESEGUI</a></li>
 			<li><a title="Chiude la pagina corrente"  class="bottoneLink" onmouseover="window.status='chiudi';return true" onmouseout="window.status=''" onclick="chiudiThisWin();" href="#">CHIUDI</a></li>
		</ul>
	</div>
</div>
</div>
</body>
</html>