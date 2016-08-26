<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
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
	WorkFlowBean workFlowBean = (WorkFlowBean) request.getAttribute("workFlowBean");
	ManagingBean managingBean =(ManagingBean) request.getAttribute("managingBean") ;
	XMLBuilder theXMLconf = confBean.getTheXMLConfEditing();
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLValControllati = confBean.getTheXMLValControllati();
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
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('multiMod','${frontUrl}');
</script>
<script type="text/javascript">
//<!--
	$(document).ready(function(){
		xDamsUnblock();
		$( ".removeHier" ).click(function() {
			$( "#hierExport" ).hide();
			$( "#flagAudience" ).hide();
			
			$( "input[name='exportType']" ).prop("checked",false);
			$( "input[name='flagAudience']" ).prop("checked",false);
			
		});
		$( "#actionGrp > input:radio" ).click(function() {
			if($(this).attr("class")!='removeHier'){
				$( "#hierExport" ).show();
				$( "#flagAudience" ).show();
				$( "input[name='exportType']" ).prop("checked",false);
				$( "input[name='flagAudience']" ).prop("checked",false);
			}
		});
		$( "input[name='exportType']" ).click(function() {
			if($(this).is(":checked") && $(this).val()=='flat'){
				$( "#flagAudience" ).hide();
				$( "input[name='flagAudience']" ).prop("checked",false);
			}else{
				$( "#flagAudience" ).show();
			}
		});
		
		
	});
	function impostaAndGo(theForm){
 		selectedRadio = false;
		$( "#actionGrp > input:radio" ).each(function( index ) {
			if($( this ).is(":checked")){
				selectedRadio=true;
			}
		});

		if(!selectedRadio){
				xDamsModalAlert('<spring:message code="Specificare_unazione" text="Specificare un\\'azione"/> !<br /><br />');
				return;		
		}
		if(!$( "input[name='exportType']" ).is(":checked")){
			xDamsModalAlert('<spring:message code="Specificare_la_tipologia_di_export" text="Specificare la tipologia di export"/> !<br /><br />');
			return;			
		}
		
		 msg = "<br /><spring:message code="Confermi_l_export" text="Confermi l\\'export"/> ? <br />";
		 $.blockUI({ message: '<div id="question" style="cursor: default"><h1 class="msg">'+msg+'</h1><input type="button" id="yes" value=" [ <spring:message code="conferma" text="conferma"/> ]" />&#160;&#160;&#160;<input type="button" id="no" value=" [ <spring:message code="annulla" text="annulla"/> ]" /> </div> ', css: { height:'200px',width: '400px' ,top:'15px',left:'15px'} });
	     $('#yes').click(function() {	
			 theForm.submit();
			 xDamsModalMessage('<spring:message code="elaborazione_in_corso" text="elaborazione in corso"/>...');
	      }); 
	      $('#no').click(function(){ 
		  xDamsUnblock();
		  return false;
	  });			 
		return false;
		}
		function proiettaModifica(sblocca){
			if(sblocca){self.close();}else{impostaAndGo(document.theForm);}
			return false;
		}
		
		function proiettaRinumera(sblocca){
			if(sblocca){self.close();}else{impostaAndGo(document.theForm02);}
			return false;
		}		
		//-->
		</script>
	</head>
<body>
<script type="text/javascript">xDamsModalMessage('<spring:message code="analisi_in_corso" text="analisi in corso"/>...');</script>
<div id="content_multi">
<div class="riga_posiziona_multi"><spring:message code="elemento_selezionato" text="elemento selezionato"/>: <strong><%=titleManager.defaultParsedTitle(managingBean.getTitle(),"defaultTitle")%></strong></div>
<form action="${contextPath}/admin/<%=workFlowBean.getAlias() %>/exportMenu.html" name="theForm">
<input type="hidden" name="actionFlag" value="exportArchive" />
<input type="hidden" name="makeAction" value="true" />
<input type="hidden" name="physDoc" value="<%=managingBean.getPhysDoc()%>" />
<input type="hidden" name="selid" value="<%=managingBean.getSelid()%>" />
<div class="m10">
<strong><spring:message code="EXPORT_ARCHIVIO" text="EXPORT ARCHIVIO XML"/></strong>:
<br />
<span id="actionGrp">
<%if((managingBean.getListPhysDoc())!=null && (managingBean.getListPhysDoc()).size()>0){ %><input class="actionVal" type="radio" name="applyTo" value="selected"> <strong><spring:message code="selezione_multipla" text="selezione multipla"/></strong> (<%=(managingBean.getListPhysDoc()).size()%>  <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if(( managingBean.getNumElementi())>0){ %><input type="radio" name="applyTo" value="selid" class="removeHier"> <strong><spring:message code="ricerca_corrente" text="ricerca corrente"/></strong> (<%=( managingBean.getNumElementi())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getDocUpperBrother())>0){ %><input type="radio" name="applyTo" value="prevSibling" class="removeHier"> <strong><spring:message code="fratelli_precedenti" text="fratelli precedenti"/></strong><br /><%} %>
<%if((managingBean.getDocLowerBrother())>0){ %><input type="radio" name="applyTo" value="nextSibling" class="removeHier"> <strong><spring:message code="fratelli_successivi" text="fratelli successivi"/></strong><br /><%} %>
<%if((managingBean.getNumElementiSons())>0){ %><input type="radio" name="applyTo" value="sons"> <strong><spring:message code="figli" text="figli"/></strong> (<%=(managingBean.getNumElementiSons())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
<%if((managingBean.getNumElementiHier())>0 && managingBean.getNumElementiHier()>managingBean.getNumElementiSons()){ %><input type="radio" name="applyTo" value="hier" class="actionVal"> <strong><spring:message code="tutto_il_ramo" text="tutto il ramo"/></strong> (<%=(managingBean.getNumElementiHier())%> <spring:message code="elementi" text="elementi"/>)<br /><%} %>
</span>
<br /> 
<strong><spring:message code="TIPOLOGIA_DI_EXPORT" text="TIPOLOGIA DI EXPORT"/></strong>:
<br />
<span id="hierExport"><input type="radio" name="exportType" value="hier"/> <strong><spring:message code="gerarchico" text="gerarchico"/></strong></span> <span id="flagAudience"><input type="checkbox" name="flagAudience"/> <strong><spring:message code="applica_audience" text="applica audience"/></strong></span> 	<br /> 
<span id="flatExport"><input type="radio" name="exportType" value="flat"/> <strong><spring:message code="piatto" text="piatto"/></strong></span>
<br />

<br />

</div>
</form>
</div>
<div id="foot">
	<div class="margin_foot">
 	<div class="cont_ul2">	
		<ul class="bottoniMenu" >
			<li><a title="<spring:message code="ESEGUI" text="ESEGUI"/>"  class="bottoneLink" onmouseover="window.status='<spring:message code="ESEGUI" text="ESEGUI"/>';return true" onmouseout="window.status=''" onclick="return proiettaModifica(false);"  href="#"><spring:message code="ESEGUI" text="ESEGUI"/></a></li>
 			<li><a title="<spring:message code="chiudi" text="CHIUDI"/>" class="bottoneLink" onmouseover="window.status='<spring:message code="chiudi" text="CHIUDI"/>';return true" onmouseout="window.status=''" onclick="chiudiThisWin()" href="#"><spring:message code="chiudi" text="CHIUDI"/></a></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html> 