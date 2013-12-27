<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.xdams.user.bean.UserBean"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.xdams.user.bean.Archive"%>
<%@page import="java.util.List"%>
<%@ page errorPage="/error.jsp" %>
<%
Map<String, List<Archive>> usersArchives = (Map<String, List<Archive>>)request.getAttribute("usersArchives");
UserBean userBean = (UserBean)request.getAttribute("userBean");
%>
<head>
<title>xDams - workspace</title>
<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1" />
<link id="stile" href="${frontUrl}/css/stile.css" rel="stylesheet" />
<link id="colors" href="${frontUrl}/css/colors.css" rel="stylesheet" />
<script type="text/javascript" src="${frontUrl}/xd-js/jquery/jquery-last.js"></script> 
<script type="text/javascript" src="${frontUrl}/xd-js/loadJs.js"></script> 
<script type="text/javascript">
loadJsBusiness('workspace','${frontUrl}');
</script>
<script type="text/javascript">
function removeHighLight()
{
	$('span.highlight', '#contArchive').each(function() 
	  {      
		$(this).removeClass('highlight');    
	  });
}
function addHighLight(textVal){
	removeHighLight();
	var valoreDigitato = textVal;
	if(valoreDigitato != ""){
		$('#contArchive').find('a').each(function(){		
			var text = $(this).text();
			var valTokenizato = valoreDigitato.match(/\w+|"[^"]+"/g);
			var allTokenMatching = 0;
			for(i = 0; i<valTokenizato.length;i++){
				var result = text.match(valTokenizato[i],"i");
				var newString = '<span class="highlight">'+result+'</span>';
				if(result && $(this).attr("class") == "arcPadre"){
					allTokenMatching++;		
					text = text.replace( result, newString );
				}else if(result){
					allTokenMatching++;	
					text = text.replace( result, newString );
				}
			}
			if(allTokenMatching == valTokenizato.length){
				$(this).html(text);
				if($(this).attr("class") == "arcPadre"){
					$(this).attr("data-Open","true");
					if(!$(this).next('.elArc').is(':visible')){
						console.debug("SHOW dalPadre: "+$(this).next('.elArc').attr("id"));
						$(this).next('.elArc').show();
					}
					if(!$(this).is(':visible'))
						$(this).show();
				}else{
					if(!$(this).parent().parent().is(':visible')){
						console.debug("SHOW dalFiglio: "+$(this).parent().parent().attr("id"));
						$(this).parent().parent().show();
						$(this).parent().parent().prev().show();
					}
				}
			}else{
					if($(this).attr("class") == "arcPadre"){
						$(this).attr("data-Open","false");
						if($(this).next('.elArc').is(':visible')){
							console.debug("HIDE dal Padre: "+$(this).next('.elArc').attr("id"));
							$(this).next('.elArc').hide();	
						}
					}
					else{
						if($(this).parent().parent().is(':visible') && $(this).parent().parent().prev().attr("data-Open") != "true"){
							console.debug("HIDE dalFiglio: "+$(this).parent().parent().attr("id"));
							$(this).parent().parent().hide();
						}
					}
				}
		});	
		$('#contArchive').find('a').each(function(){
			if($(this).attr("class") == "arcPadre"){
				if(!$(this).next('.elArc').is(':visible')){
					console.debug("SHOW dalPadre: "+$(this).next('.elArc').attr("id"));
					$(this).hide();
				}
			}
		});
	}
	else{
		$('#contArchive').find('a').each(function(){
				if($(this).attr("class") == "arcPadre"){
					$(this).next().hide();	
					$(this).show();
				}
			});
		}
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
		  clearTimeout (timer);
		  timer = setTimeout(callback, ms);
	  	  };
	})();

jQuery(document).ready(function(){
    onLoadActions('workspace');
	$('#filterinput').keyup(function(){
		delay(function(){addHighLight($('#filterinput').val());} , 300 );
	});
	$('div[class="switchClazz"]').each(function(x){
	 	//console.debug(x);
	 	if(x%2==0){
	 		$('div[class="boxLeft"]').append($(this).detach());
	 	//console.debug();
	 	}else{
	 		$('div[class="boxRight"]').append($(this).detach());
	 	}
	});
	$('#generatePassword').click(function(){
		var dataField = "passwordField="+$('#passwordField').val();
		//alert(dataField);
		$.ajax({
			  url: 'generatePassword.html',
			  type: "GET",
		      data: dataField,
			  success: function(data) {
			    $('#resultGeneratePassword').html(data);
			  }
			});
	});

	//$('body').trigger("refresh");

});
</script>
<style>
.highlight { background-color: yellow }
</style>
</head>
<body>
<div id="headPageBig">
   	<div id="header">
		<div class="allineaS"><img src="${frontUrl}/img/logo.gif"  border="0" align="left" alt="xDams"/><div class="titolo_arc"><%=userBean.getAccount().getDescrAccount()%></div></div>
		<div class="allineaD"></div>
	</div>	<div id="menu">
		<div class="al_left">
		</div>
		<div class="right"></div>
	</div>
	<div class="sub_menu">
		<div id="tit_arc">Benvenuto <%=userBean.getName()%> <%=userBean.getLastName()%></div>
	</div>
	<div class="sub_sub_menu">
		<div class="left_top2"></div>
		<div id="data_agg">&#160;<a href="${contextPath}/j_spring_security_logout" class="v_menu"><strong>logout</strong></a></div>
	</div>
</div>
<div id="contentPage">
<div style="float:left;margin-left: 20px;">
<a class="arcPadre" href="#findInWorkSpace"  style="display:none">ricerca archivi</a>
<ul class="elArc" style="display:none">
<li>
<input type="text"  style="display:none"/>
</li>
</ul>
</div>
<%if(userBean.getRole().equals("ROLE_ADMIN")){ %>
<div style="float:left;">
<a class="arcPadre" href="#generatePassword">genera password</a>
<ul class="elArc" style="display:none">
<li>
<input type="text" id="passwordField"/><a href="#vai" id="generatePassword">invia</a>
<span id="resultGeneratePassword"></span>
</li>
</ul>
</div> 
<%}%>
<!-- 
 <center>
  <h1>Ricerca Archivi</h1>  
  <div id="form"><form class="filterform" action="#">Nome Archivio: <input id="filterinput" type="text"/><input type="reset" value="RESET" onclick="addHighLight('');"/></form></div>
  </center>
<center>
  <h1>genera password</h1> 
  <div id="form"><form class="filterform" action="#">Nome Archivio: <input id="filterinput" type="text"/><input type="reset" value="RESET" onclick="addHighLight('');"/></form></div>
  </center>  
  -->
		<div  class="fl" >
	<div class="cont_campi" >
		<div id ="contArchive" class="ml20mt30">
		<span class="bold">LISTA DEGLI ARCHIVI</span>
			<div class="boxLeft">
			
			</div>
			
			<div class="boxRight">
			
			</div>
			<%
			int numGrp = 0;
			String gruppoSwtch ="";
			for (Entry<String, List<Archive>> entry : usersArchives.entrySet()) {
				String correnteGruppo = entry.getKey();
				String correnteGruppoId = URLEncoder.encode(correnteGruppo.replaceAll(" ",""),"iso-8859-1");
				List<Archive> archives = usersArchives.get(entry.getKey());
			%>
				<div class="switchClazz">
				<a class="arcPadre" href="#nogo" ><%=correnteGruppo %></a>
						<ul id="<%=correnteGruppoId %>" class="elArc" style="display:none">
								<%for(int i=0;i<archives.size();i++){
									Archive archive = archives.get(i);
									if(archive.getAlias().equalsIgnoreCase("multiarchive")){
									%>
									<li>
										<img src="${frontUrl}/img/workspace/<%=archive.getIco()%>" class="icLista" alt="<%=archive.getArchiveDescr()%>" />
										<a href="${contextPath}/search/<%=archive.getAlias()%>/query-multiarchive.html" target="<%=archive.getAlias()%>"><%=archive.getArchiveDescr()%></a>
									</li>
									<%	
									}else{
									%>
									<li>
										<img src="${frontUrl}/img/workspace/<%=archive.getIco()%>" class="icLista" alt="<%=archive.getArchiveDescr()%>" />
										<a href="${contextPath}/search/<%=archive.getAlias()%>/query.html" target="<%=archive.getAlias()%>"><%=archive.getArchiveDescr()%></a>
									</li>
									<%	
									}
								%>
								<%}%>
						</ul>
				</div>		
				<% numGrp++;

				} %>
 		</div>
	</div>
</div>
</div>
<div id="footPage">
 <div style="float:left; width:100%;" class="sub_sub_menu">
 	<div style="float:left"><img vspace="1" border="0" alt="regesta.exe" class="regesta" src="${frontUrl}/img/spacer.gif" /></div>
 	<div style="float:right; margin-right:30px;"><img vspace="1" border="0" class="regesta" src="${frontUrl}/img/spacer.gif" alt="regesta.exe" /></div>
</div> 
</div>
</body>
</html>