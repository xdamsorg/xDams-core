<%@page import="org.xdams.utility.StringsUtils"%>
<%@page import="org.xdams.page.view.modeling.FormGenerator"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%
%>
<%

for (int z = 0; z < numeroMacroarea; z++) {
	String laSezione = theXMLconf.valoreNodo("/" + ilPath + "/macroarea[" + (z + 1) + "]/@label");
	String macroArea = "/" + ilPath + "/macroarea[" + (z + 1) + "]";%>

 
<div  <%if(request.getAttribute("showAll")==null){ %>class="statusLayerNONVisibile"<%} %> id="<%=theXMLconf.valoreNodo("/"+ilPath+"/macroarea["+(z+1)+"]/@layer")%>">
		<div class="area">
		<span class="riga_tit_area" ><%=laSezione %></span>
		<div class="box_cont">
<% 
		int nodiSezione = theXMLconf.contaNodi(macroArea + "/sezione");

		 		for (int i = 0; i < nodiSezione; i++) {
 
					String sezione = "/sezione[" + (i + 1) + "]";
					String nomeSezione = theXMLconf.valoreNodo(macroArea + sezione + "/@name");
					//out.println("nomeSezione "+nomeSezione);
					//String idSezione = StringEscapeUtils.escapeEcmaScript(((((nomeSezione.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-')).replaceAll("\\\\","");
					// String idSezione = StringEscapeUtils.escapeEcmaScript(((((nomeSezione.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-'));
					String idSezione = StringsUtils.escapeSingleApex(((((nomeSezione.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-'));
					
					//out.println(idSezione);
					String typeSezione = theXMLconf.valoreNodo(macroArea + sezione + "/@type");
					String extra = theXMLconf.valoreNodo(macroArea + sezione + "/@extra",false);
					if (typeSezione.equals("custom")) {
						if ((theXMLconf.valoreNodo(macroArea + sezione + "/@id")).equals("daogrp")) {
							String elemento = "/elemento[1]";
							System.out.println("elemento "+elemento);
							System.out.println("sezione "+sezione);
							System.out.println("macroArea "+macroArea);
							generatoreForm.setValues(elemento, sezione, macroArea);
							generatoreForm.generateSection(out, theXMLconf.valoreNodo(macroArea + sezione + "/@id"));
						}
					} else {%><span <%=extra%>><span id="sezione_head_<%=idSezione%>"><table cellspacing="0" cellpadding="0" border="0" >
	<tr class="dcTSS">
		<td bgcolor="#B4B7BA" height="12" width="4"><span style="font-size:1px">&#160;</span></td>
		<td class="dcTSSSD" height="12" width="12"><a class="doceditActionLink" id="sezione_link_<%=idSezione%>" onclick="openSection('sezione_<%=idSezione%>',this)" href="#no">+</a></td><td class="dcTSSSD" ><%=nomeSezione%></td>
	</tr>
</table></span>
<span id="sezione_<%=idSezione%>" style="display:none"><table cellspacing="0" cellpadding="0" border="0" >
	<%=rigaSeparaMiddle%>
	<tr>
		<td nowrap="nowrap" colspan="4"><%boolean testSezione = false;
						boolean testSezioneOpen = false;
						if (!theXMLconf.valoreNodo(macroArea + sezione + "/@disable").equals("yes")) {

							String ilTest = theXMLconf.valoreNodo(macroArea + sezione + "/@test");
							String ilValore = theXMLconf.valoreNodo(macroArea + sezione + "/@value");
							boolean processa = true;

							if (!ilTest.equals("")) {
								if (ilTest.indexOf("not ") == 0) {
									ilTest = ilTest.substring(4);
									if (theXML.valoreNodo(ilTest, "").equals(ilValore))
										processa = false;
								} else {
									if (!theXML.valoreNodo(ilTest, "").equals(ilValore))
										processa = false;
								}
							}
							if (processa) {
								testSezione = true;
								int nodiElemento = theXMLconf.contaNodi(macroArea + sezione + "/elemento");

								if (theXMLconf.valoreNodo(macroArea + sezione + "/@opened").equals("yes")) {
									testSezioneOpen = true;
								}
								for (int x = 0; x < nodiElemento; x++) {

									String elemento = "/elemento[" + (x + 1) + "]";
									String newXPathControl = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@changeXPath");
							 
									if (!theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@disable").equals("yes")) {
 									%>
										<table cellspacing="0" cellpadding="0" border="0" >
											<%
											if(!newXPathControl.equals("")){
												generatoreForm.setValues("newXPath", newXPathControl);
											}else{
												generatoreForm.setValues("newXPath", null);
											}
											generatoreForm.setValues(elemento, sezione, macroArea);
											generatoreForm.generateSection(out);
											%>
										</table>
										<%}
								}
								testaRigaSepara = true;
							}
						}

						%></td></tr></table><%if (!testSezione) {
%><script type="text/javascript">hideSection('<%=idSezione%>')</script><%} else {
							if (testSezioneOpen) {%><script type="text/javascript">showSection('<%=idSezione%>')</script><%}
						}%></span><%if (testaRigaSepara) {
							testaRigaSepara = false;%><%=rigaSepara%><%}
					%></span><%
					}
				}

			%></div></div></div><%}
if(true){
int numeroCampiHidden = theXMLconf.contaNodi("/"+ilPath+"/sezione[@name='campiHidden']/elemento");
if(numeroCampiHidden>0){
	for(int i = 0;i<numeroCampiHidden;i++){
		String nomeNodo = theXMLconf.valoreNodo("/"+ilPath+"/sezione[@name='campiHidden']/elemento["+(i+1)+"]/text()");
		String valoreNodo = theXML.valoreNodo(nomeNodo,"");
		%><input type="hidden" name="<%=nomeNodo.replace('/','.')%>" value="<%=valoreNodo%>" /><%
	}

}}
		%>
  