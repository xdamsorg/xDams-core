<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="org.xdams.utility.testing.TestingGeneric"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Hashtable"%>
<%@page import="org.xdams.page.query.bean.QueryBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.springframework.web.util.WebUtils"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	QueryBean queryBean = (QueryBean)request.getAttribute("queryBean");
	System.out.println(queryBean);
  	String thePne = workFlowBean.getArchive().getPne();
	XMLBuilder builderQuery = confBean.getTheXMLConfQueryMultiArchive();
	String perpage = "10";
	try{
		perpage = WebUtils.getCookie(request, "title"+workFlowBean.getAlias()+userBean.getId()).getValue();
	}catch(Exception e){
		//out.println("ERRORE");
	} 
%> 
<head>
<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1" />
<title>xDams - multi archivio - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<link rel="stylesheet" type="text/css" href="${frontUrl}/css/jquery/jgrowl.css" />
<link rel="stylesheet" type="text/css" href="${frontUrl}/css/jquery/jqac.css" />
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}'};
loadJsBusiness('query','${frontUrl}');
</script>
<script type="text/javascript"> 
jQuery(document).ready(function(){
	$('a[onclick*="apriIdx"]').remove();
	$('.subMitClass').click(function() {
		//multiArchivedbName
		var isCheckedArchive = false;
		$('input:checkbox[name="multiArchivedbName"]').each(function(index) {
			if($(this).is(':checked')){
				//alert("almeno uno checcato");	
				isCheckedArchive = true;
				return false;
			}
		});
		 if(isCheckedArchive){
			 
			 return query(document.theForm,'<spring:message code="Inserire_almeno_un_valore_nei_campi_di_ricerca" text="Inserire almeno un valore nei campi di ricerca"/>'); 
		 }    
	      return false;
	});
	$('.selectAllArchive').click(function() {
		$('input:checkbox[name="multiArchivedbName"]').each(function(index) {
			$(this).attr('checked','');
		});
	});
	$('.selectRemoveArchive').click(function() {
		$('input:checkbox[name="multiArchivedbName"]').each(function(index) {
			$(this).removeAttr('checked');
		});
	});
});
</script>
</head>
<body onload="window.focus();">

<div id="headPageBig"> 
	<%@include file="../common/inc_menu.jsp" %>
<div class="sub_sub_menu">
	<div class="left_top2"><%
		int numeroSezione = builderQuery.contaNodi("/root/access_method/hierbrowse[@active='yes']");
		String noElements = builderQuery.valoreNodo("/root/access_method/@noElements");
		String withElements = builderQuery.valoreNodo("/root/access_method/@withElements");
		String userValue = builderQuery.valoreNodo("/root/access_method/@userValue");
		TestingGeneric testingGeneric = new TestingGeneric();
		boolean isUserMod = true;
		if(!userValue.trim().equals("")){
			isUserMod = testingGeneric.controllaLivello(workFlowBean,userValue.split(";"));
		}
		if(isUserMod && noElements.equals("insert") && queryBean.getTotNumDoc() == 0){
	%><a class="link1" target="_new1" href="${contextPath}/editing/${workFlowBean.alias}/preInsert.html"><spring:message code="inserisci_il_primo_documento" text="inserisci il primo documento"/></a><img src="${frontUrl}/img/arrow.gif" border="0" class="ml4_r" alt="accedi" /><%
		}else{
			for(int i=0;i<numeroSezione;i++){
			%>
					  <a  class="link1" href="${contextPath}/hier/<%=workFlowBean.getArchive().getAlias()%>/hierBrowser.html?go=hierBrowser.jsp&amp;docToggle=<%=builderQuery.valoreNodo("/root/access_method/hierbrowse[@active='yes']["+(i+1)+"]/text()")%>&amp;docStart=<%=builderQuery.valoreNodo("/root/access_method/hierbrowse[@active='yes']["+(i+1)+"]/text()")%>"><%=builderQuery.valoreNodo("/root/access_method/hierbrowse[@active='yes']["+(i+1)+"]/@label")%></a><img src="${frontUrl}/img/arrow.gif" border="0" class="ml4_r" alt="<spring:message code="accedi" text="accedi"/>" />
					<%
						} 
							numeroSezione = builderQuery.contaNodi("/root/access_method/query[@active='yes']");
							for(int i=0;i<numeroSezione;i++){
					%><a class="link1" href="javascript:cerca('<%=builderQuery.valoreNodo("/root/access_method/query[@active='yes']["+(i+1)+"]/text()")%>',document.theForm)"><%=builderQuery.valoreNodo("/root/access_method/query[@active='yes']["+(i+1)+"]/@label")%></a><img src="${frontUrl}/img/arrow.gif" border="0" class="ml4_r" alt="<spring:message code="accedi" text="accedi"/>" /><%
						}
							if(isUserMod && withElements.equals("insert")){
					%><a class="link1" target="_new1" href="${contextPath}/editing/${workFlowBean.alias}/preInsert.html"><spring:message code="inserisci" text="inserisci"/></a><img src="${frontUrl}/img/arrow.gif" border="0" class="ml4_r" alt="<spring:message code="accedi" text="accedi"/>" /><%
						}
						}
					%>
		</div>
			 
		</div>
</div>
<div id="contentPage">
<form  name="theForm" id="theForm" action="${contextPath}/search/<%=workFlowBean.getAlias()%>/title-multiarchive.html" method="post">
<%
Map<String, List<Archive>> usersArchives = (Map<String, List<Archive>>)request.getAttribute("usersArchives");
%>
 			<div>
			<div style="float:left; width:100%; margin:0 0 10px 20px;"><div style="float:left; margin-right:30px;"><a href="#koko" class="selectAllArchive"><spring:message code="seleziona_tutti" text="seleziona tutti"/></a></div>
			<div style="float:left;"><a href="#koko" class="selectRemoveArchive"><spring:message code="deseleziona_tutti" text="deseleziona tutti"/></a></div>
			</div>
			<div class="mt30">
			<ul class="elArc"> 
			<%
			for (Entry<String, List<Archive>> entry : usersArchives.entrySet()) {
				String correnteGruppo = entry.getKey(); 
				String correnteGruppoId = URLEncoder.encode(correnteGruppo.replaceAll(" ",""),"iso-8859-1");
				List<Archive> archives = usersArchives.get(entry.getKey());
				for(int i=0;i<archives.size();i++){
					Archive archive = archives.get(i);
					if(!archive.getAlias().equalsIgnoreCase("multiArchive")){
				%>
					<li><input type="checkbox" name="multiArchivedbName" value="<%=archive.getAlias()%>"/><%=archive.getArchiveDescr()%></li>
					<%}
					}%>
				<%}%>
				</ul>
			</div>	
			</div>
				<input type="hidden" name="qlphrase" />
				<input type="hidden" name="perpage" value="<%=perpage%>" />
	  			<div  class="fl" >
				<div class="cont_campi" >
				<ul class="bottoniMenu" style="margin-right:20px;float:right;"><xDamsJSTL:menugestionejslt confFile="bar-query" /></ul>
				<div class="cont_butt">
						<div class="mt10"><a href="#noffo" class="cerca_but subMitClass"><spring:message code="cerca" text="cerca"/></a>&nbsp;&nbsp;&nbsp;<a href="#" class="cerca_but" onclick="document.theForm.reset()"><spring:message code="cancella" text="cancella"/></a></div>
				</div>
				<div class="cont_div"><div class="divisorio"><img src="${frontUrl}/img/spacer.gif" width="100" height="1" alt="" /></div></div>
				<div class="mt30">
 	 			<%
 	 				Map<String, List<Map<String, String>>> positionMap = (Map<String, List<Map<String, String>>>)request.getAttribute("positionMap");
 	 			 	 			Map<String, List<Map<String, String>>> positionAdminMap = (Map<String, List<Map<String, String>>>)request.getAttribute("positionAdminMap");
 	 			 	 			List<String> outputHourField = (List<String>)request.getAttribute("outputHourField");
 	 			 	 			List<String> outputDataField = (List<String>)request.getAttribute("outputDataField");
 	 			 	 			List<String> outputSortField = (List<String>)request.getAttribute("outputSortField");
 	 			 	 			
 	 			 	 			//out.println(positionMap);
 	 			 	 			
 	 			 	 			for (Entry<String, List<Map<String, String>>> entry : positionMap.entrySet()) {
 	 							String positionDiv = entry.getKey();
 	 							List<Map<String, String>> listOutput = entry.getValue();
 	 							if(positionDiv.equals("") || positionDiv.equals("center")){
 	 								for (int z = 0; z < listOutput.size() ; z++) {
 	 									Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);
 	 									for (Entry<String, String> entryLabel : hashLabel.entrySet()) {
 	 										String attrLabel = entryLabel.getKey();
 	 										String outputField = entryLabel.getValue();
 	 			%>
									<div class="ml20" ><label><%=attrLabel%></label></div>
									<div class="ml20"><%=outputField%></div>
								<%
									}
													}
												}else if(positionDiv.equals("sx") || positionDiv.equals("left")){
								%><div class="col_sx"  ><%
									for (int z = 0; z < listOutput.size() ; z++) {
														Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);
														for (Entry<String, String> entryLabel : hashLabel.entrySet()) {	
															String attrLabel = entryLabel.getKey();
															String outputField = entryLabel.getValue();
								%>
										<div class="mlmt20"><label><%=attrLabel%></label></div>
										<div class="ml20"><%=outputField%></div>
								<%
									}
													}
								%></div><%
									}else	if(positionDiv.equals("dx") || positionDiv.equals("right")){
								%><div class="col_dx"  ><%
									for (int z = 0; z < listOutput.size() ; z++) {
														Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);
														for (Entry<String, String> entryLabel : hashLabel.entrySet()) {		
															String attrLabel = entryLabel.getKey();
															String outputField = entryLabel.getValue();
								%>
										<div class="mlmt20"><label><%=attrLabel%></label></div>
										<div class="ml20"><%=outputField%></div>
								<%
									}
													}
								%></div><%
									}
											}
								%>
				</div>
				<div class="fl">
					<%
						for (int z = 0; z < outputHourField.size() ; z++) {
									out.println((String)outputHourField.get(z));
								}
					%>
				</div>
				<div class="fl">
					<%
						for (int z = 0; z < outputDataField.size() ; z++) {
									out.println((String)outputDataField.get(z));
								}
					%>
				</div>
				<div class="fl">
					<%
						for (int z = 0; z < outputSortField.size() ; z++) {
									out.println((String)outputSortField.get(z));
								}
					%>
				</div>
				<div class="cont_div">
					<div class="ml20mt30"><spring:message code="Ultime_ricerche_effettuate" text="Ultime ricerche effettuate"/>:</div><%
						String ultimeRicerche = "";
									/* creazione bean di ricerca */
									boolean valorizzato = false;

									if(session.getAttribute("arrQueryBean") != null){
										ArrayList arrQueryBean = (ArrayList)session.getAttribute("arrQueryBean");
										for(int i = 0; i< arrQueryBean.size();i++){
											org.xdams.page.query.bean.QueryBean ilBean = (org.xdams.page.query.bean.QueryBean)arrQueryBean.get(i);
											String ilDb = ilBean.getDb();
											if(ilDb.equals(workFlowBean.getArchive().getAlias())){
												valorizzato = true;
												String laQuery = ilBean.getQuery();
												laQuery = laQuery.substring(laQuery.indexOf("]]")+2,laQuery.length()-1);
												String laQueryVis = ((laQuery.replaceAll("\\[","")).replaceAll("\\]","")).replaceAll("XML,","").replace('(',' ').replace(')',' ');
												String ilTot = ilBean.getTot();
												ultimeRicerche+="<option  value=\""+laQuery.replaceAll("\"","&quot;")+"\">["+ilTot+"] - "+laQueryVis+"</option>\n";
											}
										}
									}
									/* fine creazione bean di ricerca*/
									if(valorizzato)
										ultimeRicerche = "<option value=\"\">"+workFlowBean.getLocalizedString("scegli","scegli")+"...</option>"+ultimeRicerche;
									else
										ultimeRicerche = "<option value=\"\">"+workFlowBean.getLocalizedString("nessuna_ricerca_effettuata","nessuna ricerca effettuata")+"</option>";
					%><div class="ml20"><select class="button" id="queryBeanSelect" onchange="return findIt(this)"><%=ultimeRicerche %></select> <a title="<spring:message code="svuota" text="svuota"/>" class="cerca_b" href="#n" onclick="return ajaxEraseQueryBean('<%=workFlowBean.getArchive().getAlias() %>');"><spring:message code="svuota" text="svuota"/></a></div>
					<div class="cont_div">
					<%if(!positionAdminMap.isEmpty()){%><div class="ml20mt30"><spring:message code="Informazioni_sulla_descrizione_archivistica" text="Informazioni sulla descrizione archivistica"/>:</div><%}%>
					
	<%
					for (Entry<String, List<Map<String, String>>> entry : positionAdminMap.entrySet()) {	
						String positionDiv = entry.getKey();
						List<Map<String, String>> listOutput = entry.getValue();
						if(positionDiv.equals("") || positionDiv.equals("center")){
							for (int z = 0; z < listOutput.size() ; z++) {
								Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);
								for (Entry<String, String> entryLabel : hashLabel.entrySet()) {		
									String attrLabel = entryLabel.getKey();
									String outputField = entryLabel.getKey();
									%>
									<div class="ml20"><%=attrLabel%></div>
									<div class="ml20"><%=outputField%></div>
								<%}
							}
						}else if(positionDiv.equals("sx") || positionDiv.equals("left")){
						%><div class="col_sx"><%
							for (int z = 0; z < listOutput.size() ; z++) {
								Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);	
								for (Entry<String, String> entryLabel : hashLabel.entrySet()) {
									String attrLabel = entryLabel.getKey();
									String outputField = entryLabel.getValue();
									%>
										<div class="mlmt20"><%=attrLabel%></div>
										<div class="ml20"><%=outputField%></div>
								<%}
							}
						%></div><%
						}else if(positionDiv.equals("dx") || positionDiv.equals("right")){
						%><div class="col_dx"><%
							for (int z = 0; z < listOutput.size() ; z++) {
								Map<String, String> hashLabel = (Map<String, String>)listOutput.get(z);	
								for (Entry<String, String> entryLabel : hashLabel.entrySet()) {
									String attrLabel = entryLabel.getKey();
									String outputField = entryLabel.getValue();	
									%>
										<div class="mlmt20"><%=attrLabel%></div>
										<div class="ml20"><%=outputField%></div>
								<%}
							}
						%></div><%
						}
					}
				%>
				</div>

		  		</div>
				<div class="cont_div"><div class="divisorio"><img src="${frontUrl}/img/spacer.gif" width="100" height="1" alt="" /></div></div>
				<div class="cont_butt">
						<div class="mt10"><a href="#noffo" class="cerca_but subMitClass"><spring:message code="cerca" text="cerca"/></a>&nbsp;&nbsp;&nbsp;<a href="#" class="cerca_but" onclick="document.theForm.reset()"><spring:message code="cancella" text="cancella"/></a></div>
				</div>
			</div>
		</div>
		
		<%
		String prefix = "/root/query/data/element";
		int numField = builderQuery.contaNodi(prefix);
		if(numField>0){
		%>
			<input type="hidden" name="dataField" value="[<%=builderQuery.valoreNodo("/root/query/data/element/text()","")%>]" />
			<input type="hidden"  name="[<%=builderQuery.valoreNodo("/root/query/data/element/text()","")%>]" value="" />
		<%}%>
		
  	</form>
</div>
<div id="footPage">

	<%@include file="../common/inc_sub_menu.jsp"%>
</div>
<form name="theparsedform" id="parsedform" action="?">
	<input type="hidden" id="parsedphrase" name="qlphrase" />
	<input type="hidden" value="[[<%=workFlowBean.getArchive().getAlias()%>]]" id="multiarchivio" name="multiarchivio" />
</form>
</body>
</html>
  