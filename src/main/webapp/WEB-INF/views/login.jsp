<%@page import="java.util.Enumeration"%>
<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=iso-8859-1" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>xDams</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link id="login" href="${frontUrl}/css/login.css" rel="stylesheet" />
<script type="text/javascript">
  BrowserDetect = {
		init: function () {
			this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
			this.version = this.searchVersion(navigator.userAgent)
				|| this.searchVersion(navigator.appVersion)
				|| "an unknown version";
			this.OS = this.searchString(this.dataOS) || "an unknown OS";
		},
		searchString: function (data) {
			for (var i=0;i<data.length;i++)	{
				var dataString = data[i].string;
				var dataProp = data[i].prop;
				this.versionSearchString = data[i].versionSearch || data[i].identity;
				if (dataString) {
					if (dataString.indexOf(data[i].subString) != -1)
						return data[i].identity;
				}
				else if (dataProp)
					return data[i].identity;
			}
		},
		searchVersion: function (dataString) {
			var index = dataString.indexOf(this.versionSearchString);
			if (index == -1) return;
			return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
		},
		dataBrowser: [
			{
				string: navigator.userAgent,
				subString: "Chrome",
				identity: "Chrome"
			},
			{ 	string: navigator.userAgent,
				subString: "OmniWeb",
				versionSearch: "OmniWeb/",
				identity: "OmniWeb"
			},
			{
				string: navigator.vendor,
				subString: "Apple",
				identity: "Safari",
				versionSearch: "Version"
			},
			{
				prop: window.opera,
				identity: "Opera"
			},
			{
				string: navigator.vendor,
				subString: "iCab",
				identity: "iCab"
			},
			{
				string: navigator.vendor,
				subString: "KDE",
				identity: "Konqueror"
			},
			{
				string: navigator.userAgent,
				subString: "Firefox",
				identity: "Firefox"
			},
			{
				string: navigator.vendor,
				subString: "Camino",
				identity: "Camino"
			},
			{		// for newer Netscapes (6+)
				string: navigator.userAgent,
				subString: "Netscape",
				identity: "Netscape"
			},
			{
				string: navigator.userAgent,
				subString: "MSIE",
				identity: "Explorer",
				versionSearch: "MSIE"
			},
			{
				string: navigator.userAgent,
				subString: "Gecko",
				identity: "Mozilla",
				versionSearch: "rv"
			},
			{ 		// for older Netscapes (4-)
				string: navigator.userAgent,
				subString: "Mozilla",
				identity: "Netscape",
				versionSearch: "Mozilla"
			}
		],
		dataOS : [
			{
				string: navigator.platform,
				subString: "Win",
				identity: "Windows"
			},
			{
				string: navigator.platform,
				subString: "Mac",
				identity: "Mac"
			},
			{
				   string: navigator.userAgent,
				   subString: "iPhone",
				   identity: "iPhone/iPod"
		    },
			{
				string: navigator.platform,
				subString: "Linux",
				identity: "Linux"
			}
		]

	};
	BrowserDetect.init();
</script>
</head>
<body onload="document.f.j_username.focus();" class="bodyLogin">
	<div id="wrapLogin">
		<div class="contLogoLogin"><img src="${frontUrl}/img/login/logo.gif" /></div>
		 <c:if test="${not empty param.login_error}">
	      <div class="righeLogin" style="color:red;">
	        Error<br/><br/>
	        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
	      </div>
	    </c:if>		
	    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
			<div class="righeLogin">
				<label class="loginlabel"><spring:message code="Nome_utente" text="User Name"/></label>
				<input type='text' id="filterinput" name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
			</div>
			<div class="righeLogin">
				<label class="loginlabel">Password</label>
				<input type='password' id="filterinput" name='j_password' value="">
			</div>		
			<div class="righeLogin">
				<label class="loginlabel">Account</label>
				<input type="text" id="filterinput" readonly="readonly" name="j_company" value="xdams.org" />
			</div>
			<!-- 
			<div class="righeLogin">
				<label class="loginlabel">Resta connesso</label>
				<input type="checkbox"  name="_spring_security_remember_me"/>
			</div> 
			-->
			<div class="bottoniLogin">
				<input id="subBtt" class="btLogin" name="submit" type="submit" value="Invia">
				<input id="resetBtt" class="btLogin" name="reset" type="reset">
			</div>								
		</form>
				<div class="righeLogin"><span id="brDetect"></span></div>
	</div>
	<script type="text/javascript">
		document.getElementById('brDetect').innerHTML = BrowserDetect.browser+' '+BrowserDetect.version+' ('+BrowserDetect.OS+')';
		if(BrowserDetect.browser  != 'Chrome' && BrowserDetect.browser  != 'Firefox'){
			//alert('Attenzione: accesso non consentito con il browser '+BrowserDetect.browser+"\n"+"Allo stato attuale è possibile utilizzare solo Mozilla Firefox o Google Chrome");
			//document.getElementById('subBtt').disabled = true;
			//document.getElementById('resetBtt').disabled = true;
		}
	</script>
</body>
</html>
