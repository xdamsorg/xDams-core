<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.xw.utility.Key"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<%
	ConfBean confBean = (ConfBean)request.getAttribute("confBean");
	UserBean userBean = (UserBean)request.getAttribute("userBean");
	WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");

	int totChiavi =0;
	String vocabolarioType = (String)request.getAttribute("vocabolario_tipology");
%>
<head>
<title>xDams - idx - <%=workFlowBean.getArchive().getArchiveDescr()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<link id="popup" href="${frontUrl}/css/popup.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script>
<script type="text/javascript">
loadJsBusiness('vocabulary','${frontUrl}');
</script>
<script type="text/javascript">
			<%
			String toFillJ="";
			if(session.getAttribute("hashChekbox_" + workFlowBean.getAlias())!=null){
				Map<String, String> hashChekbox = (TreeMap<String, String>)session.getAttribute("hashChekbox_" + workFlowBean.getAlias());
				toFillJ="";
			 	for (Entry<String, String> entry : hashChekbox.entrySet()) {
					toFillJ+=(String)entry.getKey()+"~";
				}
			}
			%>
setVocParams('<%=StringEscapeUtils.escapeEcmaScript(toFillJ)%>','<%=vocabolarioType%>');
</script>
</head>

<body onload="window.focus();">

<%if(request.getAttribute("vocabolarioList")!=null && ((ArrayList<Key>)request.getAttribute("vocabolarioList")).size()>0) {
	List<Key> vocabolarioList=(ArrayList<Key>)request.getAttribute("vocabolarioList");
%>

<div style="display: none;">
<form name="precedenteForm" action="${contextPath}/search/${workFlowBean.alias}/vocabulary.html" method="get">
<input type="hidden" name="vocabolario_start_param" value=""/>
<input type="hidden" name="vocabolario_tocheck" value=""/>
<input type="hidden" name="vocabolario_alias" value="<%=request.getAttribute("vocabolario_alias")%>"/>
<input type="hidden" name="vocabolario_maxresult" value="<%=request.getAttribute("vocabolario_maxresult")%>"/>
<input type="hidden" name="vocabolario_up_down" value="down"/>
<input type="hidden" name="vocabolario_return_id" value="<%=request.getAttribute("vocabolario_return_id")%>"/>
<input type="hidden" name="vocabolario_tipology" value="<%=vocabolarioType%>"/>
<input type="hidden" name="vocabolario_phrase" value="<%=((String)request.getAttribute("vocabolario_phrase")).replaceAll("\"","&quot;")%>"/>
<input type="hidden" name="urlAfterSave" value="<%=request.getAttribute("urlAfterSave")%>"/>
<input type="hidden" name="urlErrorPage" value="<%=request.getAttribute("urlErrorPage")%>"/>
<input type="hidden" name="theArch" value="<%=request.getAttribute("theArch")%>"/>
<input type="hidden" name="num_docs" value="<%=request.getAttribute("num_docs")%>"/>
<input type="hidden" name="isExport" value=""/>
<%
 	for(int i = 0; i < vocabolarioList.size();i++){
          org.xdams.xw.utility.Key key=(org.xdams.xw.utility.Key)vocabolarioList.get(i);
 %>
            <input type="checkbox" id="vocabolario_ch<%=i%>p" name="vocabolario_ch<%=i%>p" value="<%=key.key%>" />
            <input type="checkbox" id="remove_vocabolario_ch<%=i%>p" name="remove_vocabolario_ch<%=i%>p" value="<%=key.key%>" />
           <%
           	}
           %>
</form>
<form name="successivoForm" action="${contextPath}/search/${workFlowBean.alias}/vocabulary.html" method="get">
<input type="text" name="vocabolario_start_param" value="" />
<input type="hidden" name="vocabolario_tocheck" value=""/>
<input type="hidden" name="vocabolario_alias" value="<%=request.getAttribute("vocabolario_alias")%>"/>
<input type="hidden" name="vocabolario_maxresult" value="<%=request.getAttribute("vocabolario_maxresult")%>"/>
<input type="hidden" name="vocabolario_up_down" value="up"/>
<input type="hidden" name="vocabolario_return_id" value="<%=request.getAttribute("vocabolario_return_id")%>"/>
<input type="hidden" name="vocabolario_tipology" value="<%=vocabolarioType%>"/>
<input type="hidden" name="vocabolario_phrase" value="<%=((String)request.getAttribute("vocabolario_phrase")).replaceAll("\"","&quot;")%>"/>
<input type="hidden" name="urlAfterSave" value="<%=request.getAttribute("urlAfterSave")%>"/>
<input type="hidden" name="urlErrorPage" value="<%=request.getAttribute("urlErrorPage")%>"/>
<input type="hidden" name="theArch" value="<%=request.getAttribute("theArch")%>"/>
<input type="hidden" name="num_docs" value="<%=request.getAttribute("num_docs")%>"/>
<input type="hidden" name="isExport" value=""/>
 <%
 	for(int i = 0; i < vocabolarioList.size();i++){

              org.xdams.xw.utility.Key key=(org.xdams.xw.utility.Key)vocabolarioList.get(i);
 %>
            <input type="checkbox" id="vocabolario_ch<%=i%>s" name="vocabolario_ch<%=i%>s" value="<%=key.key%>" />
            <input type="checkbox" id="remove_vocabolario_ch<%=i%>s" name="remove_vocabolario_ch<%=i%>s" value="<%=key.key%>" />



           <%
           	}
           %>
</form>
<form class="tendina" name="vocabolarioForm" action="${contextPath}/search/${workFlowBean.alias}/vocabulary.html" method="get">
<input type="hidden" name="vocabolario_tocheck" value=""/>
<input type="hidden" name="vocabolario_alias" value="<%=request.getAttribute("vocabolario_alias")%>"/>
<input type="hidden" name="vocabolario_maxresult" value="<%=request.getAttribute("vocabolario_maxresult")%>"/>
<input type="hidden" name="vocabolario_up_down" value="up"/>
<input type="hidden" name="vocabolario_return_id" value="<%=request.getAttribute("vocabolario_return_id")%>"/>
<input type="hidden" name="vocabolario_tipology" value="<%=vocabolarioType%>"/>
<input type="hidden" name="vocabolario_start_param" value=""/>
<input type="hidden" name="vocabolario_phrase" value="<%=((String)request.getAttribute("vocabolario_phrase")).replaceAll("\"","&quot;")%>"/>
<input type="hidden" name="urlAfterSave" value="<%=request.getAttribute("urlAfterSave")%>"/>
<input type="hidden" name="urlErrorPage" value="<%=request.getAttribute("urlErrorPage")%>"/>
<input type="hidden" name="theArch" value="<%=request.getAttribute("theArch")%>"/>
<input type="hidden" name="num_docs" value="<%=request.getAttribute("num_docs")%>"/>
<input type="hidden" name="isExport" value=""/>
<%
	for(int i = 0; i < vocabolarioList.size();i++){
	org.xdams.xw.utility.Key key=(org.xdams.xw.utility.Key)vocabolarioList.get(i);
%>
    <input type="checkbox" id="vocabolario_ch<%=i%>d" name="vocabolario_ch<%=i%>d" value="<%=key.key%>" />
    <input type="checkbox" id="remove_vocabolario_ch<%=i%>d" name="remove_vocabolario_ch<%=i%>d" value="<%=key.key%>" />
<%
	}
%>
</form>
</div>
<div id="head">
	<div class="sotto_head">&nbsp;</div>
	<div class="riga_tit_diz">
		<div class="Tit_diz">dizionario di campo</div>
		<div class="butt_close"><a href="javascript: self.close()" class="link_white" title="chiudi finestra">chiudi x</a></div>
	</div>
	<div class="riga_posiziona" ><span class="link">posiziona su</span><input type="text" class="middle" style="margin-left:4px;" name="valore_vocabolario" onchange="return toVoc(this);" onblur="return toVoc(this);" /><a href="#n" title="posiziona su" onclick="submitVocabolario();"><img src="${frontUrl}/img/posiziona.gif" alt="posiziona" border="0" hspace="3" /></a>
	</div>
	<div class="tot_pag_voc" style="">visualizza <select class="scelta_sel" id="selectVis" onchange="return toVocKeyCount(this);"><option value="<%=request.getAttribute("vocabolario_maxresult")%>"><%=request.getAttribute("vocabolario_maxresult")%></option><option value="20">20</option><option value="30">30</option><option value="50">50</option><option value="99999">tutte</option></select>  elementi per pagina</div>
</div>
<div id="content">
 
	<div class="m10">
		 <form name="fillForm" action="">
	         <%
	         	int br=0;
	         	           if(vocabolarioList.size()%2==0)
	         	               br=vocabolarioList.size()/2;
	         	           else
	         	               br=(vocabolarioList.size()+1)/2;
	         	            for(int i = 0; i < vocabolarioList.size();i++){
	         	             org.xdams.xw.utility.Key key=(org.xdams.xw.utility.Key)vocabolarioList.get(i);
	         	            //
//out.println(key.frequence+"<br />");
//out.println("AAAAAAAAAAAA"+key.key+"<hr />");
	         %>
	             <%if( i==0  ){%>

			<div class="colonna_sx"> 
	             <%}
	             //scrivo solo le chiavi con occorrenza maggiore di 0, se sono
	             // in visualizzazione compleata della chiavi se sono "double" faccio vedere solo quelle con lo spazio
	             if(key.frequence>0 && key.frequence<999999){
 	            boolean ilTest = true;
// 	            out.println("iffeee: "+(vocabolarioType.equals("double") && !((String)key.key).startsWith(" "))+"<br/>");
// 	            out.println("iffaaa: "+(vocabolarioType.equals("double"))+"<br/>");
// 	            out.println("iffoooo: "+!((String)key.key).startsWith(" ")+"<hr/>");
 	            if(vocabolarioType.equals("double") && !((String)key.key).startsWith(" ")){
 	            	ilTest = false;
 	            }
				if(ilTest){
 	            totChiavi++;
	             %>
	                  <div class="riga_diz">
	                  	<div class="dFloat"><input type="checkbox" name="vocabolario_ch<%=i%>" value="<%=key.key%>" onclick="setChecked(this,'vocabolario_ch<%=i%>');" /></div>
	                  	<div class="mt3">[<%=key.frequence%>]&#160;<%=key.key%></div>
	                  </div>

	              <%
	             }}
	              if(i==br-1){%>
	               </div>
			<div class="colonna_dx">
	              <%}%>
	              <%if(i==vocabolarioList.size()-1){%>
	                 </div>
	             <%}%>
	           <%}%>
	           </form>
	</div>
</div>
<div id="foot">
	<div class="margin_foot">
	<div class="cont_ul2" >
		<ul class="bottoniMenu">
		<%
			int max_result=Integer.parseInt((String)request.getAttribute("vocabolario_maxresult"));
		                  if(request.getAttribute("vocabolario_previous")==null){
		                  String vocFirst = ((org.xdams.xw.utility.Key)vocabolarioList.get(0)).key.toString();
		                  if(request.getAttribute("vocabolario_previous_string")!=null && !((String)request.getAttribute("vocabolario_previous_string")).equals("") ){
		                     vocFirst = (String)request.getAttribute("vocabolario_previous_string");
		                  }
		%>
                   <li>
                    	<a href="#n" title="precedente" onclick="precedente('<%=StringEscapeUtils.escapeEcmaScript(vocFirst)%>');">precedente</a>
                   </li>
        		  <%
        		  	}else{
        		  %>
        		  		<li><a class="hiddenButton"><del>precedente</del></a></li>
        		  <%
        		  	}
        		  %>
		<%
			if(request.getAttribute("vocabolario_next")!=null && !((String)request.getAttribute("vocabolario_next")).equals("") || ((String)request.getAttribute("vocabolario_up_down")).equals("down") ) {
				  String vocLast = (String)request.getAttribute("vocabolario_next");
		                  if(request.getAttribute("vocabolario_next_string")!=null && !((String)request.getAttribute("vocabolario_next_string")).equals("") ){
		                     vocLast = (String)request.getAttribute("vocabolario_next_string");
		                  }
		%>
                     <li>
                    	<a href="#n" onclick="successivo('<%=StringEscapeUtils.escapeEcmaScript(vocLast)%>');">successivo</a>
                     </li>
                 <%
                 	}else{
                 %>
        		  		<li><a class="hiddenButton"><del>successivo</del></a></li>
        		  <%
        		  	}
        		  %>
  		     <li>
				<a href="#n" title="inserisci" onclick="submitForm('<%=request.getAttribute("vocabolario_return_id")%>');">INSERISCI</a>
		     </li>
		   	 <li>
				<a href="javascript:submitFormExport();" title="export" >EXPORT</a>
		     </li>
		</ul>
	</div>
	</div>
</div>

<%if(session.getAttribute("hashChekbox_"+workFlowBean.getAlias())!=null){

	String vocabolario_tocheck="";
	Map<String,String> hashChekbox = (TreeMap<String,String>)session.getAttribute("hashChekbox_"+workFlowBean.getAlias());
 	for (Entry<String, String> entry : hashChekbox.entrySet()) {
 		vocabolario_tocheck+=(String)entry.getKey()+"~";
	}
 	out.println("vocabolario_tocheck "+vocabolario_tocheck);
 	if(!vocabolario_tocheck.equals("")){
	%>
		<script>
		fillCheckBox('<%=StringEscapeUtils.escapeEcmaScript(vocabolario_tocheck)%>'); 
		</script>
	<%}
}%>
<%}else{%>
<div id="head">
	<div class="sotto_head">&nbsp;</div>
	<div class="riga_tit_diz">
		<div class="Tit_diz">dizionario di campo</div>
		<div class="butt_close"><a href="javascript: self.close()" class="link_white" title="chiudi finestra">chiudi x</a></div>
	</div>
	<div class="riga_posiziona"><span class="link">posiziona su</span><input type="text" class="middle" style="margin-left:4px;" name="valore_vocabolario" onkeyup="document.vocabolarioForm.vocabolario_start_param.value=this.value" /><a href="#n" title="posiziona su" onclick="submitVocabolario();"><img src="${frontUrl}/img/posiziona.gif" alt="posiziona" border="0" hspace="3" /></a>


	</div>
</div>
<div id="content">
	<div style="margin:10px;">
		<div class="colonna_sx">
			<div class="dFloat">Nessun termine trovato nel vocabolario</div>
			<div class="mt3"></div>
		</div>
	</div>
</div>
<form class="tendina" name="vocabolarioForm" action="${contextPath}/search/${workFlowBean.alias}/vocabulary.html" method="get">
<input type="hidden" name="vocabolario_output" value=""/>
<input type="hidden" name="vocabolario_tocheck" value=""/>
<input type="hidden" name="vocabolario_alias" value="<%=request.getAttribute("vocabolario_alias")%>"/>
<input type="hidden" name="vocabolario_maxresult" value="<%=request.getAttribute("vocabolario_maxresult")%>"/>
<input type="hidden" name="vocabolario_up_down" value="up"/>
<input type="hidden" name="vocabolario_return_id" value="<%=request.getAttribute("vocabolario_return_id")%>"/>
<input type="hidden" name="vocabolario_tipology" value="<%=vocabolarioType%>"/>
<input type="hidden" name="vocabolario_start_param" value=""/>
<input type="hidden" name="vocabolario_phrase" value="<%=((String)request.getAttribute("vocabolario_phrase")).replaceAll("\"","&quot;")%>"/>
<input type="hidden" name="urlAfterSave" value="<%=request.getAttribute("urlAfterSave")%>"/>
<input type="hidden" name="urlErrorPage" value="<%=request.getAttribute("urlErrorPage")%>"/>
<input type="hidden" name="theArch" value="<%=request.getAttribute("theArch")%>"/>
<input type="hidden" name="num_docs" value="<%=request.getAttribute("num_docs")%>"/>
<input type="hidden" name="isExport" value=""/>
</form>
<%}%>
<%
if(((String)request.getAttribute("vocabolario_maxresult")).equals("99999")){%><script type="text/javascript">setVocKeyCount(<%=totChiavi%>)</script><%}
%>
 </body>
</html>   