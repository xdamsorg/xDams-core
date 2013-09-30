<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.utility.DateUtil"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.managing.bean.ModifyAutherBean"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean)request.getAttribute("managingBean") ;
	ModifyAutherBean modifyAutherBean = (ModifyAutherBean)request.getAttribute("modifyAutherBean");
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);

	int contatoreAssoluto = 0;
	String codeToFind = (MyRequest.getParameter("codeToFind",request));
	String ilLemma = (MyRequest.getParameter("nameToFind",request));
	String prefix = (MyRequest.getParameter("prefix",request));
	String elementoXML = "";
	String ilCodice = codeToFind;
	String elementoModifica = (MyRequest.getParameter("modifica",request));
%><html>
<head>
<title>xDams - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
var globalOption = {frontPath:'${frontUrl}' ,theArch:'${workFlowBean.alias}', contextPath:'${contextPath}'};
loadJsBusiness('modifyAuther','${frontUrl}');
</script>
<script>

  		function impostaAndGo(ilPercorso,laFrase){
	 		serviceProcessName = "<%=userBean.getName()%> <%=userBean.getLastName()%>";
			document.theForm['.action'].value="modifica da authority";
			document.theForm['.data'].value = '<%=DateUtil.getDataSystem("dd/MM/yyyy")%>';
			document.theForm['.persona'].value = serviceProcessName;
		    if(document.theForm.nameToChange!=null && document.theForm.nameToChange.value == ''){
			 		alert('Valore obbligatorio mancante!')
			 		document.theForm.nameToChange.focus();
			}else{
				// alert(serviceXPath)
		 	}
		    xDamsModalMessage('elaborazione in corso...');
		 	document.theForm.submit();
		}
		
		function proiettaModifica(objA){
			isCheck = false;
			for(var i=0;i< document.theForm.length;i++){
				nomeVar = document.theForm.elements[i].name;
				 
				if(nomeVar.indexOf("theArchiveToProcess_")!=-1 && document.theForm[nomeVar].checked){
					isCheck = true;
				}
			}
			if(!isCheck){
				alert("Attenzione:selezionare almeno un archivio!");
				return;
			}
 			if(confirm('Attenzione:: la richiesta comporterà l\'immediata modifica di tutti i record associati al record negli archivi collegati')){
 				objA.innerHTML = "";
 				//document.getElementById('STATO').className = 'statusLayerVisibile';
 				impostaAndGo("","");
 		 	}else{
				
			}
			return false;
		}		
 	
	</script>
</head>
<body onload="self.focus();document.getElementById('scriviQUI').innerHTML=unescape('<%=ilLemma%>');document.getElementById('scriviQUA').innerHTML=unescape('<%=ilLemma%>');">
<form action="${contextPath}/<%=workFlowBean.getAlias() %>/managing.html" name="theForm" method="post">
<input type="hidden" name="actionFlag" value="<%=MyRequest.getParameter("actionFlag",request)%>" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="codeToFind" value="<%=MyRequest.getParameter("codeToFind",request)%>" />
<input type="hidden" value="" name=".action"/>
<input type="hidden" value="" name=".data"/>
<input type="hidden" value="" name=".persona"/>
<div id="content_multi"> 
	<div class="riga_posiziona_multi" id="scriviQUI"><%=ilLemma.trim()%></div>	
    <%
    boolean almenoUno = false;
    	for(int x=0;x<modifyAutherBean.getArrModifyAutherBean().size();x++){
    		ModifyAutherBean autherBean = (ModifyAutherBean)(modifyAutherBean.getArrModifyAutherBean()).get(x);
			if(autherBean.getNumElementi()>0)
	    		almenoUno=true;
		%><div class="m10" <%if(autherBean.getNumElementi()==0){%>style="display:none"<%} %> title="<%=autherBean.getQuery()%>"><span class="testoMain12"><input type="checkbox" name="theArchiveToProcess_<%=x%>" value="<%=autherBean.getArchivioAlias()%>"/><em class="testoMainBold12"><%=autherBean.getArchivioDescr()%></em><br /> trovate <strong><%=autherBean.getNumElementi()%></strong> occorrenze</span><br /></div><%	
    	}
    if(almenoUno==false){
    	%><div class="m10">Nessun elemento associato</div><%
    }
    %></div>
<textarea name="nameToChange" class="doceditInput" cols="57" style="display:none;visibility:hidden;" id="scriviQUA"><%=ilLemma%></textarea>
</form>

<div id="foot">
	<div class="margin_foot">
	 	<div class="cont_ul2">	
			<ul class="bottoniMenu" >
	 			<%if(almenoUno){ %><li><a title="Esegui la richiesta"  class="bottoneLink" onmouseover="window.status='esegui';return true" onmouseout="window.status=''" onclick="return proiettaModifica(this);"  href="#">ESEGUI</a></li><%}%>
			</ul>
		</div>
	</div>
</div>
</body>
</html>