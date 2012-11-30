<%@page import="java.util.Enumeration"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.utility.Titles"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@page import="org.xdams.page.command.LookupCommand"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	confBean.setPageContext(pageContext);
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	List<String> titleList = (ArrayList<String>)request.getAttribute("titleList");
	Titles titlesPage = (Titles)request.getAttribute("titlesPage");
	String titleRule = request.getParameter("titleRule");
	String ruleDescr = "";
	String ruleCode = "";
	String extra = "";
//	out.println(titleRule);
//	out.println(request.getParameter("inputTitleRule"));

	try{
		ruleDescr = (titleRule.substring(titleRule.indexOf("descr:")+6,titleRule.indexOf("code:"))).trim();
		ruleCode = (titleRule.substring(titleRule.indexOf("code:")+5,titleRule.indexOf("extra:"))).trim();
		extra = (titleRule.substring(titleRule.indexOf("extra:")+6)).trim();
	}catch(Exception e){
//		out.println("ERORRRORORORO ");
		ruleDescr = (titleRule.substring(titleRule.indexOf("descr:")+6,titleRule.indexOf("code:"))).trim();
		ruleCode = (titleRule.substring(titleRule.indexOf("code:")+5)).trim();
		extra = "";
	}
	boolean infoTest = false;
	String queryInfo = "";
	if(request.getParameter("inputTitleRule")!=null && !request.getParameter("inputTitleRule").equals("")){

		queryInfo = TitleManager.myTitle(ruleCode,request.getParameter("inputTitleRule"));
		if(queryInfo.indexOf("XML")!=-1){
			infoTest = true;
			queryInfo = StringUtils.remove(queryInfo,"XML,");
			queryInfo = StringUtils.remove(queryInfo,"\"");
			if(queryInfo!=null){
				queryInfo = StringUtils.deleteWhitespace(queryInfo);
			}
			queryInfo = "[XML,"+queryInfo+"]";
		}else{
			infoTest = false;
			queryInfo = "";
		}
	}
%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<title>xDams - lookup - <%=workFlowBean.getArchiveLookup().getArchiveDescr()%></title>
	<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet"/>
	<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet"/>
	<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet"/>
	<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
	<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
	<script type="text/javascript">
		var globalOption = {frontPath:'${frontUrl}',infoURLPrefix:'${contextPath}/infoTab/',infoURLSuffix:'/infoTab.html'};
		loadJsBusiness('lookup','${frontUrl}');
 	</script>	
	<script>
	self.history.forward();
	perPage = <%=request.getParameter("inputPerPage")%>;
	function confermaFieldQuery(descr,code){
		var apex='';
		var toFill='';
		var toFileNumber='';
		var nomeCampo = [<%=request.getParameter("campiDaValorizzare")%>];
		test01 = true;
		test02 = false;
		for(var i=0; i < window.opener.document.theForm.length;i++){
		 if(window.opener.document.theForm.elements[i].name == nomeCampo[0]){
			window.opener.document.theForm.elements[i].style.background = '#FFFFFF';
			window.opener.document.theForm.elements[i].value = descr;
			test01 = true;
		 }
		 if(window.opener.document.theForm.elements[i].name == nomeCampo[1]){
			window.opener.document.theForm.elements[i].value = code;
			test02 = true;
		 }
		 if(test01 && test02)
		 {
			self.close();
			return true;
		 }
		}
	}
	
 	function inserisciPopAuth(){
		 
		laScheda=window.open('${contextPath}/editing/${workFlowBean.archiveLookup.alias}/${workFlowBean.alias}/preInsert.html','schedaXdamsAuther','status=yes,resizable=yes,scrollbars=yes,width=760, height=540');
		 laScheda = null;
	} 

  </script>
 </head>

<body onload="window.focus();">
<div id="head">
	<div class="sotto_head">&nbsp;</div>
	<div class="riga_tit_diz">
		<div class="Tit_diz">LOOKUP</div>
		<div class="butt_close"><a href="javascript: self.close()" class="link_white" title="chiudi finestra">chiudi x</a></div>
	</div>
		<div class="riga_posiziona"><%if(workFlowBean.getArchiveLookup().getRole().equals("1") || workFlowBean.getArchiveLookup().getRole().equals("2")){%><a class="bottoneLink" href="#" onclick="return inserisciPopAuth()">NUOVO</a><%}%></div>
		<div class="riga_posiziona" ><a class="bottoneLink" onclick="self.close()" href="#">ANNULLA</a></div>
</div>
<%if(titleList!=null && (titleList).size()>0){%>
<div style="display:none;visibility:hidden">
	<form name="lookupForm" action="${contextPath}/lookup/<%=workFlowBean.getAlias()%>/<%=workFlowBean.getArchiveLookup().getAlias()%>.html" method="post">
	   <%
	   for (Enumeration enume = request.getParameterNames(); enume.hasMoreElements();) {
		String parametro = (String)enume.nextElement();
		//out.println(parametro+"=" + request.getParameter(parametro)+"<br>");
		if(!(parametro.equals("selId"))){
		%>
		<%=parametro%><textarea name="<%=parametro%>"><%=request.getParameter(parametro)%></textarea><br>
		<%
		}
		}
		if((request.getParameter("selId"))==null){
		%>
		<textarea name="startPage"></textarea>
		<%}%>
		<textarea name="selId"><%=request.getAttribute("selId")%></textarea>
	</form>
</div>
<div id="content">
	<div class="m10">
		<form name="formCheck" action="">
		<%System.out.println("aAAAaaa"+titleList);%>
			<%
	        for(int i = 0; i < titleList.size();i++){
	        String ilTitolo = (String)titleList.get(i);
			
			String[] titleSepar = ilTitolo.split("¢");
			String theTitle = (TitleManager.myTitle(ruleDescr,ilTitolo)).trim();
			theTitle = theTitle.replaceAll("\n","");
			if(theTitle.indexOf("¢")>0)
				theTitle = theTitle.substring(0,theTitle.indexOf("¢"));

			String theCode = (TitleManager.myTitle(ruleCode,ilTitolo)).trim();
			//out.println(extra);

			String theExtra = "";
			if(!extra.equals("")){
				theExtra =(TitleManager.myTitle(extra,ilTitolo)).trim();
			}

			//out.println(ilTitolo);
			%>
			<div id="cont_campi">
				<div class="ele_tito">
					<a href="javascript:void(0);" ><img src="${frontUrl}/img/spacer.gif" class="scheda" border="0" vspace="0"></a>
					<a id="anchor<%=i%>" class="foldertree" TITLE="seleziona" onmouseover="window.status='seleziona';return true" onmouseout="window.status=''" href="javascript:void(0);" onclick="return confermaFieldQuery('<%=(theTitle.replaceAll("'","\\\\'")).replaceAll("\"","&#34;")%>','<%=theCode%>');"><%=theTitle%> <strong><%=theExtra%></strong> <em>(<%=theCode%>)</em> </a>
					<%if(infoTest){%><span class="doceditActionLinkBorder">[</span><a href="javascript:void(0)" onclick="return infoAuther('<%=theCode%>','<%=workFlowBean.getArchiveLookup().getAlias()%>','<%=queryInfo%>')" class="doceditActionLink">info</a><span class="doceditActionLinkBorder">]</span><%}%>
				</div>
			</div>
			<%}%>
		</form>
	</div>
</div>

<div id="foot">
	<div class="margin_foot">
		<div class="cont_ul2" >
			<ul class="bottoniMenu">
				<li id="firstPage">&#160;</li>
				<li id="prevPage">&#160;</li>
				<li id="nextPage">&#160;</li>
				<li id="lastPage">&#160;</li>
			</ul>
		</div>
	</div>
</div>
<script>
	<%
	String navString =titlesPage.getNavString();
	%>
	self.pageNavigator(<%=navString%>)
</script>

<%}%>
<form name="pageForm">
	<input type="hidden" name="totPages" value="<%=titlesPage.getLastPage()%>">
	<input type="hidden" name="thisPage" value="<%=titlesPage.getThisPage()%>">
	<input type="hidden" name="nextPage" value="<%=titlesPage.getThisPage()+1%>">
	<input type="hidden" name="prevPage" value="<%=titlesPage.getThisPage()-1%>">
</form>

</body>
</html>