<%@page import="org.springframework.util.TypeUtils"%>
<%@page import="org.xdams.page.view.bean.HierBrowserBean"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.page.view.bean.TreeBean"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%@taglib uri="/WEB-INF/xDamsJSTL.tld" prefix="xDamsJSTL"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%String useragent = request.getHeader("user-agent");
if(useragent.indexOf("MSIE 7")==-1){%><!-- Put IE into quirks mode --><%}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
	TreeBean treeBean = (TreeBean)request.getAttribute("treeBean");
	MyRequest myRequest = new MyRequest(request);
	ManagingBean managingBean = (ManagingBean)session.getAttribute(workFlowBean.getManagingBeanName());
	String evidNode = "";
	XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
	TitleManager titleManager = new TitleManager(theXMLconfTitle);

	boolean levelMode = !theXMLconfTitle.valoreNodo("/root/titleManager/sezione[@name='hierBrowser']/elemento[@flag='level']/text()").equals("");
	String treeCssPrefix = theXMLconfTitle.valoreNodo("/root/titleManager/sezione[@name='hierBrowser']/elemento[@flag='level']/@value");
	if(levelMode){
		treeCssPrefix +="/";
	} 
%><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>xDams - Browser Gerarchico - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<link href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link href="${frontUrl}/css/<%=treeCssPrefix%>treeF3.css" rel="stylesheet" />
<link rel="stylesheet" href="${frontUrl}/css/jquery/jgrowl.css" type="text/css"/>
<link href="${frontUrl}/css/jquery/jquery.qtip.min.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
<%
String extPng = ".png";
if(useragent.indexOf("MSIE 6")!=-1){
	extPng = ".gif";
} %>
<%=workFlowBean.getGlobalLangOption()%>
var globalOption = {frontPath:'${frontUrl}',theExt:'<%=extPng%>',theArchName:'<%=workFlowBean.getAlias()%>',theArch:'${workFlowBean.alias}',contextPath:'${contextPath}'};
loadJsBusiness('tree','${frontUrl}');
</script>
<script type="text/javascript">
locatPos=document.location.href;
jQuery(document).ready(function(){onLoadActions('tree');});
</script>
</head>
<body><%
String displayMode = "none";
String checked = "";
if(managingBean!=null){
	 //out.println("managingBean.getMultipleChoise() "+managingBean.getMultipleChoise());
	 displayMode = managingBean.getMultipleChoise();
}
%><!--inizio albero--><%
%><div id="contentTree" class="contentTree"><%try{
  java.util.Vector vectHierBrowserBean = treeBean.getVectHierBrowserBean();
  if(treeBean.isUpEnabled()){
  %><div class="liv_sp"><a href="?hierStatus=<%=treeBean.getHierStatus()%>&amp;docStart=<%=treeBean.getFirstDocNumber()%>&amp;backward=1&amp;docCount=<%=treeBean.getDocCount()%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage")%>"><spring:message code="precedente" text="precedente"/></a></div><%
  }%>
<div class="contList">
	<%if(vectHierBrowserBean!=null){
		int absC = 0;
	    for(int index=0;index<vectHierBrowserBean.size();index++){
		boolean hasIcon = false;
		String iconType = "";
	    try{

	if(index<vectHierBrowserBean.size()){
		boolean skipNext = false;
		HierBrowserBean hierBrowserBean = (HierBrowserBean)vectHierBrowserBean.get(index);
		String strTitle = hierBrowserBean.getTitle();
		java.util.ArrayList arrTitolo = titleManager.parseTitle(strTitle, "hierBrowser");
		String strTitoloManager = "";
		String level = "";


		for(int k=0;k<arrTitolo.size();k++){
			String valueArr = (String)arrTitolo.get(k);
			if((valueArr.indexOf("hasImage"))!=-1){
				strTitoloManager = strTitoloManager.replaceAll("hasImage","");
			}else if(valueArr.indexOf("<isAttach>")!=-1){
					if(!valueArr.equals("<isAttach></isAttach>")){
						hasIcon = true;
						String[] icoArray = valueArr.split(" ");

						for(int ks=0;ks<icoArray.length;ks++){
						if(!icoArray[ks].equals("")){
							if(((icoArray[ks].toLowerCase()).indexOf("jpg")!=-1&& iconType.indexOf("jpg")==-1) || ((icoArray[ks].toLowerCase()).indexOf("jpeg")!=-1&& iconType.indexOf("jpeg")==-1)){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/jpg.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if(((valueArr.toLowerCase()).indexOf("pdf")!=-1 && iconType.indexOf("pdf")==-1)){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/pdf.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("mp3")!=-1 && iconType.indexOf("mp3")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/mp3.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("wav")!=-1 && iconType.indexOf("wav")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/mp3.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("flv")!=-1 && iconType.indexOf("flv")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/flv.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((icoArray[ks].toLowerCase()).indexOf("wmv")!=-1 && iconType.indexOf("wmv")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/flv.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							}else if((valueArr.toLowerCase()).indexOf("pdf")!=-1&& iconType.indexOf("pdf")==-1){
								iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/pdf.gif\" alt=\""+workFlowBean.getLocalizedString("allegato","allegato")+"\" class=\"treeIcon\" hspace=\"3\" />";
							} else {
//								if(iconType.indexOf("generic")==-1){
//									iconType += "<img src=\""+request.getAttribute("frontUrl")+"/img/icons/generic.gif\" alt=\"allegato\" class=\"treeIcon\" hspace=\"3\" />";}
//								}
						}} 

					}
				}else if(valueArr.indexOf("<skip>")!=-1 ){
					if(!valueArr.equals("<skip></skip>")){
					skipNext = true;
					break;
					}
				}else

					  if(valueArr.indexOf("<level>")!=-1 ){
						if(!valueArr.equals("<level></level>")){
							level = StringUtils.substringBetween(valueArr,"<level>","</level>");
						}
					  }else
				{
					strTitoloManager += valueArr+"";
				}
		}
		if(skipNext){
		/*	if(hierBrowserBean.isLastChild()){

				%><span class="prevIsLast"></span><%
			}else{*/
				%><span class="prevIsFather"></span><%

			//}


			continue;}
		String titolo = strTitoloManager;
		String descrizione="";
		if(descrizione.length()>500){
			String toSpace=descrizione.substring(500,descrizione.length());
			descrizione=descrizione.substring(0,500);
			if(toSpace.indexOf(" ")!=-1){
			  descrizione+=toSpace.substring(0,toSpace.indexOf(" "))+"...";
			}
		}
		if(hierBrowserBean.getDepth()==1){
			%><div><div class="folder_list_node spr"><div class="ndn" id="ele_<%=hierBrowserBean.getDocNumber()%>"><%
			 %>
			  <a name="pos<%=hierBrowserBean.getDocNumber() %>" data-numDoc="<%=hierBrowserBean.getDocNumber() %>" href="#n" class="folder_href infotrigger" >&#160;</a>
			  <a style="left:-12px;position:relative;" class="actiontrigger" href="${contextPath}/viewTab/<%=workFlowBean.getArchive().getAlias()%>/shortTab.html?physDoc=<%=hierBrowserBean.getDocNumber()%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage")%>" target="schedaBreve"><%=titolo%></a>
              	</div></div></div>
		<%}else{
			 String href="";
			 href="?&amp;docToggle="+hierBrowserBean.getDocNumber()+"&amp;docStart="+hierBrowserBean.getFirstDocNumber()+"&amp;hierStatus="+treeBean.getHierStatus()+"&amp;docCount="+treeBean.getDocCount()+"&amp;pageToShow="+myRequest.getParameter("pageToShow")+"&amp;perpage="+myRequest.getParameter("perpage")+"#pos"+hierBrowserBean.getDocNumber();
				String img_item="img_close";
				if(hierBrowserBean.isHasSons()){
						if(hierBrowserBean.isOpened()){
							img_item="img_open";
						}
						if(hierBrowserBean.isLastChild()){
							img_item+="_last";
						}
				}else{
					img_item="img_file";
					if(hierBrowserBean.isLastChild()){
						img_item+="_last";
					}
				}
				for(int i=0;i<hierBrowserBean.getFathersLastChild().length;i++){
					if(managingBean!=null && managingBean.isChecked(String.valueOf(hierBrowserBean.getDocNumber()))){
						checked = "_on";
					}else{
						checked = "";
					}
						if(i==0){
						%><div class="nl<%if(hierBrowserBean.getFathersLastChild()[i]==true  || (hierBrowserBean.isLastChild() && i==hierBrowserBean.getFathersLastChild().length-1 )){%><%}else{%> spr nl_ln <%} %>"><%
						}else{
						if(hierBrowserBean.getFathersLastChild()[i]==true  || (hierBrowserBean.isLastChild() && i==hierBrowserBean.getFathersLastChild().length-1 )){
						%><div class="nl_nl"><%}else{%><div class="spr nl_24"><%}
					}
				}%><div class="<%=img_item%> new_dots spr" id="ele_<%=hierBrowserBean.getDocNumber()%>">
							<a name="pos<%=hierBrowserBean.getDocNumber() %>" data-numDoc="<%=hierBrowserBean.getDocNumber() %>" href="<%=href%>" class="folder_href infotrigger">&#160;</a>
							<%if(displayMode.equals("")){
								absC++;
							%><span class="spck" ><img rel="<%=absC %>" src="${frontUrl}/img/icons/check<%=checked%><%=extPng %>" name="<%=hierBrowserBean.getDocNumber()%>" /></span><%}
							%>
							<% if(hasIcon){ %><%=iconType%><%}%>
							<a href="${contextPath}/viewTab/<%=workFlowBean.getArchive().getAlias()%>/shortTab.html?physDoc=<%=hierBrowserBean.getDocNumber()%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage")%>" target="schedaBreve" class="link_scheda actiontrigger"><%=!level.equals("")?"<span class=\"spr mrg "+level+"\">&nbsp;</span>":"" %><%=titolo%></a>
                        </div>
					<%for(int i=0;i<hierBrowserBean.getFathersLastChild().length;i++){%></div><%}
			}
	   }
	 }catch(Exception e){
		  e.printStackTrace();
	 }
          }
            }%></div>
<%if(treeBean.isDownEnabled()){
String andToggle = "";
if(treeBean.getLastDocNumber() == treeBean.getDocToggle())andToggle=String.valueOf(treeBean.getDocToggle());%>
<div class="liv_in" ><a href="?hierStatus=<%=treeBean.getHierStatus()%>&amp;docStart=<%=treeBean.getLastDocNumber()%>&amp;docToggle=<%=andToggle%>&amp;docCount=<%=treeBean.getDocCount()%>&amp;pageToShow=<%=myRequest.getParameter("pageToShow")%>&amp;perpage=<%=myRequest.getParameter("perpage")%>"><spring:message code="successivo" text="successivo"/></a></div>
<%}%>
<%}catch(Exception e){
out.println(e.getMessage());
 }%></div></body>
</html>