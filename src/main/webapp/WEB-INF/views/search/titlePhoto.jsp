<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.xw.paging.QRParser"%>
<%@page import="org.xdams.page.view.bean.TitleBean"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.page.query.bean.QueryBean"%>
<%@page import="java.util.List"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.xdams.utility.ExpressionEvaluationUtils"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	ManagingBean managingBean = (ManagingBean)session.getAttribute(workFlowBean.getManagingBeanName());

	String sorting = confBean.getSorting();
  	String accountId = userBean.getAccount().getId();
	String theArch = workFlowBean.getAlias();
	QRParser qRParser = (QRParser)session.getAttribute("QRParser");
	org.xdams.xw.paging.QRPage qRPage = (org.xdams.xw.paging.QRPage)session.getAttribute("QRPage");
	int perpage = 10;
	int pageToShow = 1;

	if(request.getParameter("pageToShow")!=null){
		pageToShow=((Integer)session.getAttribute("pageToShow")).intValue();
	}
 	if(request.getParameter("perpage")!=null && (request.getParameter("perpage")).equals("all")){
		perpage =  qRParser.getTot();
	}else if(request.getParameter("perpage")!=null){
		perpage = Integer.parseInt(request.getParameter("perpage"));
	}else if(request.getParameter("perpage")==null){
		 perpage = 10;
	}

	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	XMLBuilder theXMLconfMedia = confBean.getTheXMLConfMedia();
	String previewPrefix = theXMLconfMedia.valoreNodo("/root/media[@type='viewPreview']/@prefix");
	TitleManager titleManager = new TitleManager(theXMLconfTitle,userBean);

	String displayMode = "none";
	String checked = "";
	if(managingBean!=null){
		 //out.println("managingBean.getMultipleChoise() "+managingBean.getMultipleChoise());
		 displayMode = managingBean.getMultipleChoise();
	}
%><head>
<title>xDams - title - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="stile" href="${frontUrl}/css/stilePhoto.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<link href="${frontUrl}/css/jquery/jquery.qtip.min.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<link rel="stylesheet" href="${frontUrl}/css/jquery/jgrowl.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${frontUrl}/css/jquery/jqac.css" /> 
<script type="text/javascript">
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}',infoURLPrefix:'${contextPath}/infoTab/',infoURLSuffix:'/infoTab.html',theArch:'${workFlowBean.alias}',theExt:'.png',pageValue:'title${workFlowBean.alias}${userBean.id}',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('title','${frontUrl}');
    jQuery(document).ready(function(){
		onLoadActions('esito');
 	});
	
	function orderBy(obj){
	try{
	<%
	String queryStr = "";
	QueryBean queryBean = null;
	if(session.getAttribute(workFlowBean.getQueryBeanName()) != null){
		List arrQueryBean = (ArrayList) session.getAttribute(workFlowBean.getQueryBeanName());
		queryBean = (QueryBean) arrQueryBean.get(arrQueryBean.size()-1);		
		String testDB = queryBean.getDb();
		if(testDB.equals(theArch)){
			queryStr = queryBean.getQuery();
			try{queryStr = queryStr.substring(queryStr.indexOf("]]")+2,queryStr.length()-1);}catch(Exception e){}
			queryStr = queryStr.replaceAll("\"","&quot;");
		}
	}%>
	document.location.href='${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?sorting='+obj.value+'&qlphrase=<%=org.apache.commons.lang3.StringEscapeUtils.escapeEcmaScript(queryStr.replaceAll("&quot;","\""))%>&perpageChanged=true&viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&pageName=<%=request.getAttribute("pageName")%>';
	}catch(e){
		alert("<spring:message code="attenzione_errore" text="attenzione errore"/>");
	}
	}
</script>
</head>

<%	 
try{
	perpage = Integer.parseInt(WebUtils.getCookie(request, "title"+workFlowBean.getAlias()+userBean.getId()).getValue());
}catch(Exception e){
	//out.println("ERRORE");
} 
%>
<body>
	<div id="headPageBig">
		<%@include file="../common/inc_menu.jsp" %>
		 
 		<div class="sub_sub_menu">
			<div class="left_top2">elementi trovati <span class="bold"> <%=qRParser.getQrElements()%></span></div>
			<%if(qRParser.getQrElements()>0){%>
			<div class="paginazione"><!-- inizio paginazione -->
				<span class="num_margin_sx"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;primo=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="prima_pagina" text="prima pagina"/>"><img src="${frontUrl}/img/spacer.gif" class="prima" border="0" alt="<spring:message code="prima_pagina" text="prima pagina"/>" vspace="1" /></a></span>
				<span class="num_margin"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;pageToShow=<%=pageToShow%>&amp;before10=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="precedenti" text="precedenti"/> <%=perpage%> <spring:message code="risultati" text="risultati"/>"><img src="${frontUrl}/img/spacer.gif" class="prevPerPage" border="0" alt="<spring:message code="precedenti" text="precedenti"/> <%=perpage%> <spring:message code="risultati" text="risultati"/>" vspace="1" /></a></span>
				<span class="num_margin"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;pageToShow=<%=pageToShow%>&amp;before=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="pagina_precedente" text="pagina precedente"/>"><img src="${frontUrl}/img/spacer.gif" class="prev" border="0" alt="<spring:message code="pagina_precedente" text="pagina precedente"/>" vspace="1" /></a></span>
				<%
 						List pagesToShow = qRPage.getPageToShow();
						String classhref ="";
		     			for(int i=0;i<pagesToShow.size();i++){
								if( Integer.parseInt((String)pagesToShow.get(i))== qRPage.getNumPage() ){
									classhref = "num_select";%>
										<span class="<%=classhref%>"><%=pagesToShow.get(i)%></span>
								<%}else{
									classhref = "num_margin";
								%>
									<span class="<%=classhref%>"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?pageToShow=<%=pagesToShow.get(i)%>&amp;perpage=<%=perpage%>&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" class="num"><%=pagesToShow.get(i)%></a></span>
								<%
								}
	     				}
				%>
  				<span class="num_margin"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;pageToShow=<%=pageToShow%>&amp;next=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="pagina_successiva" text="pagina successiva"/>"><img src="${frontUrl}/img/spacer.gif" class="next" border="0" alt="<spring:message code="pagina_successiva" text="pagina successiva"/>" vspace="1" /></a></span>
				<span class="num_margin"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;pageToShow=<%=pageToShow%>&amp;next10=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="successivi" text="successivi"/> <%=perpage%> <spring:message code="risultati" text="risultati"/>"><img src="${frontUrl}/img/spacer.gif" class="nextPerPage" border="0" alt="<spring:message code="successivi" text="successivi"/> <%=perpage%> <spring:message code="risultati" text="risultati"/>" vspace="1" /></a></span>
				<span class="num_margin"><a href="${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?perpage=<%=perpage%>&amp;pageToShow=<%=pageToShow%>&amp;ultimo=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>" title="<spring:message code="ultima_pagina" text="ultima pagina"/>"><img src="${frontUrl}/img/spacer.gif" class="last" border="0" alt="<spring:message code="ultima_pagina" text="ultima pagina"/>" vspace="1" /></a></span>
			</div><!-- fine paginazione -->

			<div class="tot_pag"><spring:message code="visualizza" text="visualizza"/> <select class="scelta_sel" id="selectVis" onchange="lanciaPerpage('${contextPath}/search/<%=workFlowBean.getAlias() %>/title.html?primo=0&amp;qrId=<%=qRParser.getIdQR()%>&amp;fromId=ye&amp;perpageChanged=true&amp;viewInputPreview=<%=MyRequest.getParameter("viewInputPreview",request)%>&amp;pageName=<%=request.getAttribute("pageName")%>',this)"><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option><option value="100">100</option></select>  <spring:message code="elementi_per_pagina" text="elementi per pagina"/></div>
			<div class="buttonRefresh"><a href="#" onclick="return reloadLocation()" title="<spring:message code="aggiorna_la_pagina" text="aggiorna la pagina"/>"><img src="${frontUrl}/img/aggiorna.gif" border="0" alt="<spring:message code="aggiorna_la_pagina" text="aggiorna la pagina"/>" /></a>&#160;&#160;<a href="#" title="<spring:message code="salva_elementi_per_pagina" text="salva elementi per pagina"/>" onclick="addCookie(document.getElementById('selectVis').value)"><img src="${frontUrl}/img/save_as.gif" border="0" alt="<spring:message code="salva_elementi_per_pagina" text="salva elementi per pagina"/>" vspace="2" /></a></div>
<script type="text/javascript">
//<![CDATA[
	document.getElementById('selectVis').value='<%=perpage%>';
//]]>
</script>
			<%}%> 
<div style="float:left; margin-left:10px;"><spring:message code="ordina_la_ricerca_per" text="ordina la ricerca per"/>: <select class="scelta_sel" id="selectOrderBy" onchange="orderBy(this);"><%=extractSortValue(out, confBean.getTheXMLConfQuery())%></select></div>
<%if(!previewPrefix.equals("")){%><div style="float:left; margin-left:10px;"> preview&#160; <input type="checkbox" id="viewPreview" name="viewPreview" <%=(request.getParameter("viewInputPreview")==null || request.getParameter("viewInputPreview").equals("")) ? "" : "checked=\"true\""%>/></div><%}%>

<script type="text/javascript">
//<![CDATA[
	document.getElementById('selectOrderBy').value='<%=MyRequest.getParameter("sorting",request)%>';
//]]>
</script>			
		</div>
	</div>
	<div class="containerPhoto">
		<form><div id="cont_campi">
			 		<%int absC=0;
						List v2 = qRPage.getElements();
			 			int firstPos = 0;
						for(int x=0;x<v2.size();x++){
							boolean skipNext =false;
							boolean hasIcon = false;
							String iconType = "";
							//String strTitle = (String)v2.elementAt(x);
							TitleBean titleBean = (TitleBean)v2.get(x);
			 				//out.println(titleBean.getTitle());
			 				 
							java.util.ArrayList arrTitolo = titleManager.parseTitle(titleBean.getTitle(), "title");

							String strTitoloManager = "";
							String physDoc = titleBean.getPhysDoc();

							String archive = "";
							String cssImageStyle = "scheda";
							String isAttach ="";
							String imageResource ="";

							for(int k=0;k<arrTitolo.size();k++){
								String valueArr = (String)arrTitolo.get(k);
								strTitoloManager += valueArr+" ";
								//out.println(valueArr);
								if((valueArr.indexOf("hasImage"))!=-1){
									//cssImageStyle = "scheda_dig";
									strTitoloManager = strTitoloManager.replaceAll("hasImage","");
								}

								 if(valueArr.indexOf("<isAttach>")!=-1){
										if(!valueArr.equals("<isAttach></isAttach>")){
						hasIcon = true;
						String[] icoArray = valueArr.split(" ");

						for(int ks=0;ks<icoArray.length;ks++){
						if(!icoArray[ks].equals("")){

							if((icoArray[ks].toLowerCase()).indexOf("jpg")!=-1&& iconType.indexOf("jpg")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/jpg.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((valueArr.toLowerCase()).indexOf("pdf")!=-1&& iconType.indexOf("pdf")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/pdf.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("mp3")!=-1&& iconType.indexOf("mp3")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/mp3.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("wav")!=-1&& iconType.indexOf("wav")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/mp3.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("flv")!=-1 && iconType.indexOf("flv")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/flv.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("wmv")!=-1 && iconType.indexOf("wmv")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/flv.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}
							/*
							else {
							if(iconType.indexOf("generic")==-1){
							iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/generic.gif\" alt=\"allegato\" class=\"treeIcon\" hspace=\"3\" />";}}*/
						}}

						}
							isAttach = strTitoloManager.replaceAll("<isAttach>","").replaceAll("</isAttach>","");
							imageResource = org.apache.commons.lang3.StringUtils.substringBetween(strTitoloManager, "<isAttach>", "</isAttach>");
							strTitoloManager = strTitoloManager.replaceAll("<isAttach>[^<]*</isAttach>","");
						}else if(valueArr.indexOf("<skip>")!=-1 ){
							if(!valueArr.equals("<skip></skip>")){
								skipNext = true;
								break;
								}
							}


							}
							if(cssImageStyle.equals("scheda")){/* gestione generica basata su presenza di gerarchia */
								if(titleBean.getFirstSon()>0){
									cssImageStyle="folder";
								}
							}
							if(managingBean!=null && managingBean.isChecked(physDoc)){
								checked = "_on";
							}else{
								checked = "";
							}
							int pos = ((perpage*qRPage.getNumPage())+x)-perpage;
							if(x==0){
								firstPos = pos;
							}
if(skipNext){continue;}
%>

<div class="wrapThumbEsito">
 <div class="jcarousel-wrapper">
	<div class="jcarousel">
		<ul>
			<%
				boolean imageViewer = false;
				String[] splitImage = imageResource.split("¢");
				for(String strImage: splitImage){
					if(strImage!=null && !strImage.equals("")){
						imageViewer = true;
					
			%>
			<li><img src="<%=ExpressionEvaluationUtils.evaluate(theXMLconfMedia.valoreNodo("/root/media[@type='viewPreview']/@prefix"), String.class, pageContext)%><%=strImage%>" data-numDoc="<%=physDoc %>" border="0" ></li>
 			<%
					}
				}
			%>
		</ul>
	</div>
	
	<ul class="wrapDida">
		<li><%=strTitoloManager%></li>
	</ul> 	
	 <ul class="btThumbOn">
		<li><a href="${contextPath}/hier/${workFlowBean.archive.alias}/hierBrowser.html?docToggle=<%=physDoc %>&amp;docStart=<%=physDoc %>" title="<spring:message code="vedi_gerarchia" text="vedi gerarchia"/>"><img src="${frontUrl}/img/titlePhoto/btGerarchia.jpg" /></a></li>
		<!--<li><a href="#n" title="<spring:message code="apri_scheda_descrittiva" text="apri la scheda descrittiva"/>" onclick="return infoByNumDoc('<%=physDoc %>','${workFlowBean.alias}')"><img src="${frontUrl}/img/titlePhoto/btScheda.jpg" /></a></li>-->
		<li><a href="${contextPath}/editing/${workFlowBean.alias}/docEdit.html?physDoc=<%=physDoc%>&amp;thePne=${workFlowBean.archive.pne}&amp;pos=<%=pos%>&amp;selid=<%=qRParser.getIdQR()%>" title="<spring:message code="apri_scheda_descrittiva" text="apri la scheda di modifica"/>" target="_new${workFlowBean.alias}"><img src="${frontUrl}/img/titlePhoto/btScheda.jpg" /></a></li>
		<%
		if(imageViewer){
		%>
		<!-- <li><a href="#n" onclick="window.open('${contextPath}/custom/${workFlowBean.archive.alias}/page.html?physDoc=<%=physDoc%>&mediaToView=&pageName=custom/viewImage&confControl=media');" title="ingrandisci"><img src="${frontUrl}/img/titlePhoto/btZoom.jpg" /></a></li> -->
		<%}%>
		<%
		if(splitImage.length>1){
		%>
		<li><a href="#" class="jcarousel-control-prev"><img src="${frontUrl}/img/titlePhoto/prevPhoto.jpg" /></a></li>
		<li><a href="#" class="jcarousel-control-next"><img src="${frontUrl}/img/titlePhoto/nextPhoto.jpg" /></a></li>
		<%}%>
	</ul>	
 </div>	
</div>
<%
	} //end for
						if(v2.size()==0){%>
							<div class="ele_tito"><spring:message code="Nessun_record_trovato" text="Nessun record trovato"/></div>
						<%}

					String hrefScheda = "";
					if(qRParser.getPhysDoc()!=null && !qRParser.getPhysDoc().equals("")){
						if(!MyRequest.getParameter("backToResult",request).equals("")){
 							firstPos = ((Integer)session.getAttribute("posInQr")).intValue();
						}
						hrefScheda = request.getAttribute("contextPath")+"/viewTab/"+workFlowBean.getAlias()+"/shortTab.html?physDoc="+qRParser.getPhysDoc()+"&amp;selid="+qRParser.getIdQR()+"&amp;pos="+firstPos+"&amp;pageToShow="+pageToShow+"&amp;perpage="+perpage;
					}
				%>
			</div><!--fine cont_campi--></form>
		</div>
<!--
	<div class="schedaBreve">
		<iframe id="schedaBreve" height="100%" width="100%"   name="schedaBreve" src="<%=hrefScheda%>" frameborder="0"></iframe>
	</div>
-->	
 		<div id="footPage">
			<%@include file="../common/inc_sub_menu.jsp"%>
<div style="display:none;visibility:hidden;">
<form name="esitoForm" action="?">
<input type="hidden" value="<%=qRParser.getIdQR()%>" name="selid" />
</form>
<form name="gestione" action="?">
	descrizioDocTaglia <input type="text" name="descrizioDocTaglia" />
	azione <input type="text" name="azione" />
      <input type="text" name="docNumber" />
      <input type="text" name="docStart" value="" />
      <input type="text" name="documentiTagliati" value="~" />
	  <input type="text" name="documentoPadre" />
	  <input type="text" name="flagGestione" />
	  <input type="text" name="numDocAggancia" />
	  <input type="text" name="numDocElementoPadre" />
	  <input type="text" name="numDocIncolla" />
	  <input type="text" name="numDocStart" />
	  <input type="text" name="numDocTaglia" />
	<!-- INIZIO NECESSARI PER CANCELLAZIONE MULTIPLA BIBLIOTECA -->
	  <input type="text" name="regesta_multischeda_id" />
	  <input type="text" name="regesta_multischeda_selId" />
	  <input type="text" name="thePage" value="gestione" />
</form>
</div>
</div>
</body>
</html>

<%!
public String extractSortValue(JspWriter out, XMLBuilder theXMLconf){
	String prefix = "/root/query/sort/element";
	int numField = theXMLconf.contaNodi(prefix);
 	String outputField = "";
	outputField += "<option name=\"sorting\" value=\"\"></option>";
	for(int z=0 ;z < numField; z++){
		try{
		String nameValue = theXMLconf.valoreNodo(prefix+"["+(z+1)+"]/text()");
		String attrLabel = theXMLconf.valoreNodo(prefix+"["+(z+1)+"]/@label");
//		<option value=\"XML\">crescente</option>
		outputField += "<option name=\"sorting\" value=\""+nameValue+"\">"+attrLabel+"</option>";
		if(numField-1==z){
			outputField += "</div>";
		}
		//arrOutputSortField.add(outputField);
		}catch(Exception e){
			try{
				out.println("ERRORE IN extractSortValue");
			}catch(Exception e1){
				
			}
				
		}
	}	
	return outputField;
}

%>

 