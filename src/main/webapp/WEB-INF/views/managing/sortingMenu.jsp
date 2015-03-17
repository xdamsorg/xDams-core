<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
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
<%=workFlowBean.getGlobalLangOption()%>
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
						if(iParametriTemp.indexOf('XML')!=-1){
							iParametri += iParametriTemp.replace('XML','xml');
						}else if(iParametriTemp.indexOf('XSL')!=-1){
							iParametri += iParametriTemp.replace('XSL','xsl');
						}
					}
					else
						iParametri += iParametriTemp
				}
			}
		}
	if(iParametri == ''){
		xDamsModalAlert('<spring:message code="Scegliere_almeno_un_parametro_di_riordinamento" text="Scegliere almeno un parametro di riordinamento"/>!<br /><br />');
		return;		
	}
	msg = "<spring:message code="Attenzione" text="Attenzione"/>!<br /><br /><spring:message code="sta_per_essere_effettuato_il_riordino_del_ramo" text="sta per essere effettuato il riordino del ramo"/><br />";
	$.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
	$('#yes').click(function() {	
    	  impostaAndGo(iParametri);
		 xDamsModalMessage('<spring:message code="riordino_in_corso" text="riordino in corso"/>...');
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
	<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
	<div class="riga_posiziona_multi"><spring:message code="elementi_da_processare" text="elementi da processare"/>: <strong><%=elementi%></strong></div>
<div class="m10">
<form name="optionForm" action="?">
	<spring:message code="riordina_gli_elementi_per" text="riordina gli elementi per"/>:<br/><br/><%

	String externalPath = "/root/param/elemento[@id='riordina']/opzione";
	int nodiOpzioni = theXMLconf.contaNodi(externalPath);
	String theOptions = "<option value=\"\">"+workFlowBean.getLocalizedString("scegli", "scegli")+"...</option>";
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
		<select name="ordina<%=i%>verso"><option value="1"><spring:message code="crescente" text="crescente"/></option><option value="0"><spring:message code="decrescente" text="decrescente"/></option></select>
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
 			<li><a title="<spring:message code="ESEGUI" text="ESEGUI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica();"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a></li>
 			<li><a title="<spring:message code="chiudi" text="chiudi"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="chiudi" text="chiudi"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin();" href="#"><spring:message code="chiudi" text="chiudi"/></a></li>
		</ul>
	</div>
</div>
</div>
</body>
</html>