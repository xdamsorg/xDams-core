<%
Object messageOffLine = getServletContext().getAttribute("messageOffLine");
if(messageOffLine!=null){
	String messageOffLineStr = (String)messageOffLine;
%><div class="attenzione" style="z-index:999;position:absolute;width:90%;font-size:18px;height:80px">
		<strong>Attenzione!!</strong>  
		<br />
		<%=messageOffLineStr%>
	</div><%}%>
<div id="header"><img src="${frontUrl}/img/logo.gif"  border="0" align="left" alt="xDams"/><div class="titolo_arc"><%=userBean.getDescrAccount()%></div></div>