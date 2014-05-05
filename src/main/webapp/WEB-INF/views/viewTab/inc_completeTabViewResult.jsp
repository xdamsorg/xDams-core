<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.page.view.bean.MediaBean"%>
<%@page import="org.springframework.web.util.ExpressionEvaluationUtils"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.xdams.utility.testing.TestingViewBean"%>
<%
  String[] elementoPathGroup=null;

  boolean testaRiga = false;
  boolean testaRigaSepara = false;

  boolean campoMultiplo = false;
  boolean campoMultiploGroup = false;
  boolean campoMultiploAuther = false;

  boolean campoControllato = false;

  boolean campoCustom = false;

  String[] elementoPathGroupBefore=null;
  String[] elementoPathGroupAfter=null;
  String[] elementoPathGroupFormat=null;

	String[] fieldControlledArray = null;
	String[] fieldControlledValueArray = null;

  	String part01 ="<ul class=\"riga\" ><li class=\"etichetta\">";
	String part02 ="</li><li class=\"valore\">";
	String part03 ="</li></ul>";

	
	String partAuther01 ="";
	String partAuther02 ="<ul class=\"riga\"><li class=\"valore\">";
	//String partAuther03 ="</li><li class=\"valore\">";
	String partAuther04 ="</li></ul>";


 
    int nodiMacroarea = theXMLconf.contaNodi("/root/macroarea");

    for(int z = 0; z < nodiMacroarea;z++){
		boolean areaTest = false;
	    if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/@showdoc").equals("yes")){
			String nomeMacroarea = (theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/@name"));
			String idMacroarea  = ((nomeMacroarea.replaceAll(" ","-")).replaceAll("'","_")).replaceAll("\\\\","");
			%><div id="macroarea_<%=idMacroarea%>">
			<div class="area">
			<span class="riga_tit_area"><%=nomeMacroarea %></span><div class="box_cont"><%
			int nodiSezione = theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione");
			for(int i = 0; i < nodiSezione;i++){
			if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@showdoc").equals("yes")){
			String nomeSezione = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@name");
			String idSezione  = nomeSezione.replaceAll(" ","-");
			idSezione  = idSezione.replaceAll("'","_");
			idSezione  = idSezione.replaceAll("/","");
			  boolean multiSezione = false;
		      if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@type").equals("multi"))
		      	multiSezione = true;
		      else
		      	multiSezione = false;
				%><div class="sezione" id="sezione_head_<%=idSezione%>"><a href="#nogo" id="sezione_link_<%=idSezione%>" onclick="openSection('sezione_<%=idSezione%>',this)" class="apri_chiudi"><img alt="apri/chiudi" src="${frontUrl}/img/apri.gif" border="0" /><%=nomeSezione%></a></div>
				<div id="sezione_<%=idSezione%>" style="display:none"><%
			   boolean testSezione = false;
			   boolean testSezioneOpen = false;
			   if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@showdoc").equals("yes")){
			     int nodiElemento= theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento");
 				 if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@opened").equals("yes")){
					testSezioneOpen = true;
				 }
			 	 for(int x = 0; x < nodiElemento;x++){
						String fieldType= theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@type");
	  					String fieldPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()","");
    					String fieldPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix","");
    					String fieldName = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@name","");			
						String fieldMode = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@mode","");
						String fieldId = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@id","");
						String fieldSuffix = "";
						if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@showdoc").equals("yes")){
							if(theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@value").indexOf("document:") != -1){
								// value="document:valoriControllati.xml" externalPath="//elemento[@name='level']/opzione"
								campoControllato = true;
							} else {
								campoControllato = false;
							}
							if(fieldType.equals("multi_group")){
								elementoPathGroup = new String[theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento")];
								campoCustom = false;
								elementoPathGroupBefore = new String[elementoPathGroup.length];
								elementoPathGroupAfter = new String[elementoPathGroup.length];
								elementoPathGroupFormat = new String[elementoPathGroup.length];
								fieldControlledValueArray = new String[elementoPathGroup.length];
								fieldControlledArray = new String[elementoPathGroup.length];
								for(int w = 0; w < elementoPathGroup.length;w++){
									elementoPathGroup[w] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/text()");
									elementoPathGroupFormat[w] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/@format",false);
									elementoPathGroupBefore[w] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/@format_before",false);
									elementoPathGroupAfter[w] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/@format_after",false);
									fieldControlledArray[w] =  theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/@externalPath");
									fieldControlledValueArray[w] =  theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(w+1)+"]/@value");
								}
								//elementoPathGroup = (theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()")).split(";");
								campoMultiploGroup = true;
								elementoPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix");
								extraFunction = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@extraFunction");
							} else {
								if(fieldType.equals("multi_auther")){
									elementoPathGroup = (theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()")).split(";");
									campoMultiploAuther = true;
									campoCustom = false;
									elementoPath = elementoPathGroup[0];
									campoMultiplo = false;
									campoMultiploGroup = false;
								}
								else{
									if(fieldType.equals("custom")){

										campoCustom = true;
										campoMultiploAuther = false;
										campoMultiploGroup = false;
										campoMultiplo = false;
										elementoPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()");
										extraFunction = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@extraFunction");

									}
									else{
										campoCustom = false;
										campoMultiploAuther = false;
										campoMultiploGroup = false;
										campoMultiplo = false;
										elementoPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()");
										extraFunction = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@extraFunction");
										elementoPathGroupBefore = new String[1];
										elementoPathGroupAfter = new String[1];
										elementoPathGroupFormat = new String[1];
										elementoPathGroupFormat[0] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format",false);
										elementoPathGroupBefore[0] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format_before",false);
										elementoPathGroupAfter[0] = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format_after",false);
									}
							}}

    						if(fieldType.equals("mediaBean")){
								//facciamo il build del MediaBean
								String xmlMedia = theXMLconf.getXMLFromNode(theXMLconf.getSingleNode("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]"));
								XMLBuilder mediaBuilder = new XMLBuilder(xmlMedia,false);
								
								elementoPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@xPathPrefix");
							 
								if(theXML.contaNodi(elementoPath)>0){
								testSezione = true;
								testSezioneOpen=true;
								
								%><%=part01%>
								<%=part02%>
									<div title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
									<%String xPathPres="/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]"; %>
									<%
							  		MediaBean mediaBean = new MediaBean();
									mediaBean.setClipBoardType(mediaBuilder.valoreNodo("/elemento/@clipBoardType"));
									mediaBean.setMediaType(mediaBuilder.valoreNodo("/elemento/@mediaType"));
									mediaBean.setViewMode(mediaBuilder.valoreNodo("/elemento/@viewMode"));
									mediaBean.setXPathGroupDescr(mediaBuilder.valoreNodo("/elemento/@xPathGroupDescr"));
									mediaBean.setXPathGroupPrefix(mediaBuilder.valoreNodo("/elemento/@xPathGroupPrefix"));
									mediaBean.setXPathHref(mediaBuilder.valoreNodo("/elemento/@xPathHref"));
									mediaBean.setXPathPrefix(mediaBuilder.valoreNodo("/elemento/@xPathPrefix"));
									mediaBean.setXPathTitle(mediaBuilder.valoreNodo("/elemento/@xPathTitle"));
									int numeroElementi = mediaBuilder.contaNodi("/elemento/elemento");;
									int numeroFoto = theXML.contaNodi(mediaBean.getXPathPrefix());
									String suffixTitle = StringUtils.difference(mediaBean.getXPathPrefix(),mediaBean.getXPathTitle());//mediaBean.getXPathTitle().replaceAll(mediaBean.getXPathPrefix(),"");
									String suffixHref = StringUtils.difference(mediaBean.getXPathPrefix(),mediaBean.getXPathHref());// mediaBean.getXPathHref().replaceAll(mediaBean.getXPathPrefix(),"");
									for(int w = 0; w < numeroFoto; w++){
										String strTitleFoto = theXML.valoreNodoHTML(mediaBean.getXPathPrefix()+"["+(w+1)+"]"+suffixTitle,"<br />","&nbsp;");
										String strHrefFoto = theXML.valoreNodoNoHL(mediaBean.getXPathPrefix()+"["+(w+1)+"]"+suffixHref);
										String prefixFoto = theXMLConfMedia.valoreNodo("/root/media[@type='"+mediaBean.getMediaType()+"']/@prefix");
										if(mediaBean.getMediaType().indexOf("Alternative")!=-1){
											int countAlternative = theXMLConfMedia.contaNodi("/root/media[@type='"+mediaBean.getMediaType()+"']/media");
											for(int kk = 0; kk < countAlternative; kk++){
												String alternativePrefix = theXMLConfMedia.valoreNodo("/root/media[@type='"+mediaBean.getMediaType()+"']/media["+(kk+1)+"]/@prefix");
												 
											}
										}
										String urlFoto = "";
										if (ExpressionEvaluationUtils.isExpressionLanguage(prefixFoto)) {
											urlFoto = ExpressionEvaluationUtils.evaluateString(prefixFoto, prefixFoto, pageContext)+strHrefFoto;
										}else if(strHrefFoto.contains("http://")){
											urlFoto = strHrefFoto;	
										}else{
											urlFoto = prefixFoto+strHrefFoto;	 
										}
										
										%>
										<div class="box_sch_breFoto" title="<%=strTitleFoto%>">	
										<%
										if (((mediaBean.getViewMode()).toLowerCase()).indexOf("digital") != -1) {
											String alternativeTitle = strTitleFoto.trim();
											if (alternativeTitle.equals("")) {
												alternativeTitle = (theXMLConfMedia).valoreNodo("/elemento/@alternativeLinkTitle");
											}
											if (alternativeTitle.equals("")) {
												alternativeTitle = "accedi all'allegato digitale";
											}
											out.println("<div class=\"campoFoto\"><a href=\"" + urlFoto + "\" class=\"v_menu\" target=\"_blank\">" + alternativeTitle + "</a></div>\n");
										}else if (((mediaBean.getViewMode()).toLowerCase()).indexOf("image") != -1) {
											out.println("<div class=\"campoFoto\"><img alt=\""+strTitleFoto+"\" src=\""+urlFoto+"\"/></div>");
										}
										%>	
											
											<%
												for(int wa = 0; wa < numeroElementi; wa++){
													String suffixAppo = "";
												if(StringUtils.contains(mediaBuilder.valoreNodo("/elemento/elemento["+(wa+1)+"]/text()"),mediaBean.getXPathPrefix())){
													suffixAppo = mediaBean.getXPathPrefix()+"["+(w+1)+"]"+ StringUtils.difference(mediaBean.getXPathPrefix(),mediaBuilder.valoreNodo("/elemento/elemento["+(wa+1)+"]/text()"));
					 							}else{
													suffixAppo = mediaBuilder.valoreNodo("/elemento/elemento["+(wa+1)+"]/text()");
												}
 												if(!theXML.valoreNodoHTML(suffixAppo).equals("")){
											%>
													<div class="didaFoto">
															<em><%=mediaBuilder.valoreNodoHTML("/elemento/elemento["+(wa+1)+"]/@name")%></em> 
															<%=theXML.valoreNodoHTML(suffixAppo,"<br />","&nbsp;")%>
													</div>
													<%} %>
										<%}%></div>	
	   							  		<div class="riga_tit_area" title="<%=mediaBean.getXPathPrefix()+"["+(w+1)+"]"%>"><%=strTitleFoto%></div><%
									}
									
								 %>
								 	<xDamsJSTL:attachmentViewer attachMode="showdoc" mediaBuilder="<%=mediaBuilder%>" theXMLDoc="<%=theXML%>" physDoc="<%=viewBean.getPhysDoc()%>" presentationXpath="<%=xPathPres%>"/>
								<%=part03%>
								<%
								}	
							}		
     						//if(fieldType.equals("mediaBean")){}
							if(theXML.contaNodi(elementoPath)>0){
								testaRigaSepara = true;
								testSezione = true;

								 %>
								<%=part01%>
									<%=theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@name")%>
								<%=part02%>

									<%
									if(fieldType.equals("multi")){
										campoMultiplo = true;
										elementoPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix");
										extraFunction = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@extraFunction");

									}


									 if(campoMultiplo){
									 for(int q = 0; q < theXML.contaNodi(elementoPath);q++){
										if(extraFunction.equals("")){
										 %><%=theXML.valoreNodoHTML(elementoPath+"["+(q+1)+"]/text()","<br />","&nbsp;")%><br /><%
										}else{
											if(extraFunction.startsWith("substring")){
												int inizio = Integer.parseInt(extraFunction.substring(10));
										 %><%=theXML.valoreNodoHTML(elementoPath+"["+(q+1)+"]/text()","<br />","&nbsp;").substring(inizio)%><br /><%
											}
										}
										}
									   campoMultiplo = false;
									}else{

										 if(campoMultiploGroup){

										String prefix = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix");
										String autherQuery = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@query");
										String code = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@code");
										String arch = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@arch_ref");
										int nodiElementoSotto = theXML.contaNodi(prefix);
										
										//prefix = prefix.substring(3);
										for(int a = 0; a < nodiElementoSotto;a++){
											boolean skipInfo = false;
											for(int w = 0; w < elementoPathGroup.length;w++){
											String pathCorrente = prefix + "[" + (a+1) + "]" + elementoPathGroup[w].substring(elementoPathGroup[w].indexOf(prefix) + prefix.length());

											String valoreNodo = theXML.valoreNodoHTML(pathCorrente,"<br/>");
											String ilCodice = "";
											if(!code.startsWith("/")){
												ilCodice = theXML.valoreNodoHTML(prefix + "[" + (a+1) + "]/@"+code);
											}else{
												ilCodice = theXML.valoreNodoHTML(prefix + "[" + (a+1) + "]"+code);
											}
												if(!fieldControlledArray[w].equals("") && !fieldControlledValueArray[w].equals("") && !(valoreNodo.trim()).equals("")){


													
													
													int nodiOpzioni = theXMLValControllati.contaNodi(fieldControlledArray[w]);
														for (int d = 0; d < nodiOpzioni; d++) {
															if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodo(fieldControlledArray[w] + "[" + (d + 1) + "]/@value"))) {
																valoreNodo =theXMLValControllati.valoreNodo(fieldControlledArray[w] + "[" + (d + 1) + "]/text()");
															}
													}
												}
											//elementoPathGroup[w].replaceAll(prefix,prefix+"["+(a+1)+"]");
											%><%=elementoPathGroupFormat[w]%><%
											if(!(theXML.valoreNodoHTML(pathCorrente)).equals("")){
%>
											 <%=elementoPathGroupBefore[w]%>
											<%=valoreNodo%>
											<%if(!skipInfo && !ilCodice.equals("")){
												skipInfo = true;
												%>
		   										<a href="#nogo" onclick="return infoAuther('<%=ilCodice%>','<%=arch%>','<%=autherQuery%>')" class="cerca_b">info</a>&#160;&#160;&#160;
											 <%}%>											
											<%=elementoPathGroupAfter[w]%><%
												}
											}
											if(multiSezione){%><hr /><%}
											//out.println("<br />");
										}


										    campoMultiploGroup = false;

										 }
										else{

										 if(campoMultiploAuther){
										 %>
										 	<%=partAuther01%>
											<%
											String prefix = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix");
											String thePath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()");
											//String autherWebApp = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@web_app");
											String autherQuery = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@query");
											String alternative = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@alternative");
											String code = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@code");
											if (code.indexOf("@") == -1) {
												code = "@" + code;
											}
											String arch = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@arch_ref");
											thePath = thePath.substring(prefix.length()+1);
											for(int w = 0; w < theXML.contaNodi(elementoPath);w++){%>
											<%=partAuther02%>
											<%
	   													String ilCodice=theXML.valoreNodoHTML(prefix+"["+(w+1)+"]/"+code);
	   												if(!ilCodice.equals("")){%>
		   												<%=ilCodice %> <a href="#nogo" onclick="return infoAuther('<%=ilCodice%>','<%=arch%>','<%=autherQuery%>')" class="cerca_b">info</a>&#160;&#160;&#160;
												   <%}%>												   
													 
	   											
													<%if(!alternative.equals("") && !theXML.valoreNodoHTML(prefix+"["+(w+1)+"]/"+alternative).equals("")){
														%><%=theXML.valoreNodoHTML(prefix+"["+(w+1)+"]/"+alternative)%><%
													}else{
														%><%=theXML.valoreNodoHTML(prefix+"["+(w+1)+"]/"+thePath)%><%
													}

													%>	   											
	   											
												   <%=partAuther04%>
												<%
											}

										 
										    campoMultiploAuther = false;
										    elementoPathGroup=null;
										 }
										else{


											if(campoCustom){/* campo personalizzato */

												boolean testMacro = true;												
												
			
												try{
												fieldSuffix = fieldPath.substring(fieldPrefix.length());
												}catch(Exception e){
												fieldSuffix = "";
												}
												boolean isFilled = false;
												
												if(fieldId.equals("image")){
													String groupPrefix = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@groupPrefix");
													String imgSuffix = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgSuffix","");
													String imgMiddle = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgMiddle","");
													String imgPrefix = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgPrefix","");
													String clipBoardType = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@clipBoardType","");
													if(imgPrefix.equals("")){
														throw new Exception("imgPrefix mancante");
													}
													imagePath = imgPrefix+imgMiddle+imgSuffix;

													if(!groupPrefix.equals("")){
														
														//Prendo gli xPath che devo visualizzare
														String xPathHref = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='href']/text()","");
														String xPathTitle = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='title']/text()","");
														fieldPrefix = fieldPrefix.substring(0,fieldPrefix.indexOf(groupPrefix)+groupPrefix.length());
														//out.println(fieldPrefix);
														int numeroGruppi = theXML.contaNodi(fieldPrefix);

														for(int a = 0; a < numeroGruppi; a++){
														String xPathItemSuffix = xPathHref.replaceAll(fieldPrefix,"");
														xPathItemSuffix = xPathItemSuffix.substring(0,xPathItemSuffix.lastIndexOf("/"));
														//out.println(xPathItemSuffix);
														String xPathGroupPrefix = fieldPrefix+"["+(a+1)+"]"+xPathItemSuffix;
														int numeroFoto = theXML.contaNodi(xPathGroupPrefix);
														if(numeroFoto>0){%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div><%}											
															for(int w = 0; w < numeroFoto; w++){
															xPathItemSuffix = xPathHref.replaceAll(fieldPrefix,"");
															xPathItemSuffix = xPathItemSuffix.substring(xPathItemSuffix.lastIndexOf("/"));
															//out.println(xPathItemSuffix);
															//out.println(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix);
															String strHref = theXML.valoreNodoHTML(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix,"");
															xPathItemSuffix = xPathTitle.replaceAll(fieldPrefix,"");
															xPathItemSuffix = xPathItemSuffix.substring(xPathItemSuffix.lastIndexOf("/"));
															String strTitle = theXML.valoreNodoHTML(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix,"");
															%> 
																<div class="box_sch_bre">
																	<a href="#" onclick="openViewer('<%=viewBean.getPhysDoc()%>','<%=fieldId%>','<%=StringEscapeUtils.escapeEcmaScript("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='href']/text()") %>','<%=StringEscapeUtils.escapeEcmaScript("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='title']/text()") %>','<%=StringEscapeUtils.escapeEcmaScript("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]") %>','<%=w%>')"><img vspace="1"  hspace="0" alt="<%=strTitle%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%><%=strHref%>"></a>
																	<%=strTitle%>
																</div>
															<%
															}
														}	
													}else{
														//Prendo gli xPath che devo visualizzare
														String xPathHref = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='href']/text()","");
														String xPathTitle = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento[@id='title']/text()","");
														int numeroGruppi = theXML.contaNodi(fieldPrefix);
														String xPathItemSuffix = "";
														out.println("xPathItemSuffix aaa"+xPathItemSuffix+"<br />");
														String xPathGroupPrefix = fieldPrefix+xPathItemSuffix;
														int numeroFoto = theXML.contaNodi(xPathGroupPrefix);
															for(int w = 0; w < numeroFoto; w++){
															xPathItemSuffix = xPathHref.replaceAll("\\[","").replaceAll("\\]","").replaceAll(fieldPrefix.replaceAll("\\[","").replaceAll("\\]",""),"");
															String strHref = theXML.valoreNodoHTML(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix,"");
															xPathItemSuffix = xPathTitle.replaceAll("\\[","").replaceAll("\\]","").replaceAll(fieldPrefix.replaceAll("\\[","").replaceAll("\\]",""),"");
															String strTitle = theXML.valoreNodoHTML(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix,"");
															%>
																<br/><img vspace="1"  hspace="0" alt="<%=strTitle%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%><%=strHref.replaceAll(".tif",".jpg")%>">
																<div><%=strTitle %></div>
															<%
															}
													}
											
													
												}
												if(fieldId.equals("digitalAttach")){

													String imgPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgPrefix");

													if(!imgPrefix.equals(""))
														imagePath = imgPrefix;
													//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam singleParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance("com.regesta.upload.properties");
													//java.util.Properties propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile("com.regesta.upload.properties");
													//String pathProperties = "customization/"+userBean.getIdAccount()+"/com.regesta.upload.properties";
													//java.util.Properties propParam = null;
													try{
													//	com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
													//	propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}catch(Exception e){
			 										//	pathProperties = "com.regesta.upload.properties";
													//	com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
													//	propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}
													String visDir = "";
													String thumbPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@thumbPath","");										
													//if(propParam != null){
														//visDir = propParam.getProperty("java.thumb.dir")+"/";
													//}
													visDir += thumbPath;


													for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){ /* testo che almeno un campo sia pieno*/
														if(!theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"").equals("")){
															isFilled = true;
															break;
														}
													}
													if(isFilled){
														%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%></div>
														<div class="box_sch_bre">
														<%}
														if(fieldMode.indexOf("thumb")==0 || fieldMode.indexOf("simple")==0 || fieldMode.indexOf("mp3")==0){
																int numeroGruppi = theXML.contaNodi(fieldPrefix);
																//int perRiga = Integer.parseInt(fieldMode.substring(fieldMode.indexOf("(")+1,fieldMode.indexOf(",")));

																	int numeroFoto = theXML.contaNodi(fieldPrefix+fieldSuffix);%>
																	<div class="campo">
																	<table align="right">
																		<tr>
																			<td valign="top" class="showdocTitoloElemento"><em><%=theXML.valoreNodoHTML(fieldPrefix+"/daodesc//text()"," ")%></em></td>
																			<td align="left"><%
																			for(int w = 0; w < numeroFoto; w++){
																					if(fieldMode.indexOf("thumb")==0){
																						%><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(w+1)%>,'<%=theArch%>','dao')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>"></a><br /><%
																					}else{
																						if(fieldMode.indexOf("simple")==0){
																							%><a href="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>" target="_new">
																								<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)).equals("")){%>
																									<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%></span>
																								<%}else{%>
																									<span class="cerca_b">allegato <%=(w+1)%></span>
																								<%}%>
																							   </a> <%
																						}else{
																							if(fieldMode.indexOf("mp3")==0){%>
																								<a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(w+1)%>,'<%=theArch%>','dao')" >
																									<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)).equals("")){%>
																									<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%></span>
																									<%}else{%>
																									<span class="cerca_b">brano <%=(w+1)%></span>
																									<%}%></a> 
																							<%}
																						}
																					}
																				}
																			%>
																			</td>
																		</tr>
																	</table>
																	</div><%

														}else{%><div class="campo">
															<table align="right">
															<tr>
																<td valign="center" class="showdocTitoloElemento">
																	<center><a class="cerca_b" onclick="return visualizzaImg(<%=docNumber%>,1,'<%=theArch%>','dao')" href="#nogo">accedi agli allegati digitali</a></center>
																</td>
															</tr>
															</table>
															</div>
														<%}
														%></div><%
														testSezione = true;
					    								testMacro = true;
													}



												}else
												if(fieldId.equals("daoGrp")){

													String imgPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgPrefix");
													if(!imgPrefix.equals(""))
														imagePath = imgPrefix;
													//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam singleParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance("com.regesta.upload.properties");
													//java.util.Properties propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile("com.regesta.upload.properties");
													//String pathProperties = "customization/"+userBean.getIdAccount()+"/com.regesta.upload.properties";
													//java.util.Properties propParam = null;
													try{
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}catch(Exception e){
													System.out.println("QUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
														//pathProperties = "com.regesta.upload.properties";
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}
													String visDir = "";
													String thumbPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@thumbPath","");										
													//if(propParam != null){
														//visDir = propParam.getProperty("java.thumb.dir")+"/";
													//}
													visDir += thumbPath;


													for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){ /* testo che almeno un campo sia pieno*/
														if(!theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"").equals("")){
															isFilled = true;
															break;
														}
													}
													if(isFilled){
														%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%></div><%}%>
														<div class="box_sch_bre">
														<%
														if(fieldMode.indexOf("thumb")==0 || fieldMode.indexOf("simple")==0 || fieldMode.indexOf("mp3")==0){
																int numeroGruppi = theXML.contaNodi(fieldPrefix);
																//int perRiga = Integer.parseInt(fieldMode.substring(fieldMode.indexOf("(")+1,fieldMode.indexOf(",")));
																for(int a = 0; a < numeroGruppi; a++){
																	int numeroFoto = theXML.contaNodi(fieldPrefix+"["+(a+1)+"]/daoloc");%>
																	<div class="campo">
																	<table align="right">
																		<tr>
																			<td valign="top" class="showdocTitoloElemento"><em><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daodesc//text()"," ")%></em></td>
																			<td align="left"><%
																			for(int w = 0; w < numeroFoto; w++){
																					if(fieldMode.indexOf("thumb")==0){
																						%><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(w+1)%>,'<%=theArch%>','dao')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@href")%>"></a><br /><%
																					}else{
																						if(fieldMode.indexOf("simple")==0){
																							%><a href="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@href")%>" target="_new">
																								<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")).equals("")){%>
																									<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")%></span>
																								<%}else{%>
																									<span class="cerca_b">allegato <%=(a+1)%></span>
																								<%}%>
																							   </a> <%
																						}else{
																							if(fieldMode.indexOf("mp3")==0){%>
																								<a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=theArch%>','dao')" >
																									<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")).equals("")){%>
																									<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")%></span>
																									<%}else{%>
																									<span class="cerca_b">brano <%=(a+1)%></span>
																									<%}%></a> 
																							<%}
																						}
																					}
																				}
																			%>
																			</td>
																		</tr>
																	</table>
																	</div>
																	<%
																}
														}else{%>
														<div class="campo">
															<table align="right"> 
															<tr>
																<td valign="center" class="showdocTitoloElemento">
																	<center><a class="cerca_b" onclick="return visualizzaImg(<%=docNumber%>,1,'<%=theArch%>','dao')" href="#nogo">accedi agli allegati digitali</a></center>
																</td>
															</tr>
															</table>
															</div>

														<%}
														%></div><%
														testSezione = true;
					    								testMacro = true;
													}
												}if(fieldId.equals("extptr")){

													String imgPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgPrefix");
													String popPath = "";
														if(imgPrefix.equals(""))
															imgPrefix = imagePath+"/"+theArch;
														else
															popPath = imgPrefix;

													//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam singleParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance("com.regesta.upload.properties");
													//java.util.Properties propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile("com.regesta.upload.properties");
													//String pathProperties = "customization/"+userBean.getIdAccount()+"/com.regesta.upload.properties";
													//java.util.Properties propParam = null;
													try{
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}catch(Exception e){
													System.out.println("QUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
														//pathProperties = "com.regesta.upload.properties";
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}


													String visDir = "";
													String thumbPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@thumbPath","");										
													//if(propParam != null){
														//visDir = propParam.getProperty("java.thumb.dir")+"/";
													//}
													visDir += thumbPath;

													for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){ /* testo che almeno un campo sia pieno*/
														if(!theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"").equals("")){
															isFilled = true;
															break;
														}
													}
													if(isFilled){
														%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%>
														<div class="box_sch_bre">
														<%
														if(fieldMode.indexOf("thumb")==0 || fieldMode.indexOf("simple")==0 || fieldMode.indexOf("mp3")==0){
																int numeroFoto = theXML.contaNodi(fieldPrefix+"//extptr/@href");%>
																	<div class="campo"><table align="right" >
																	<%for(int a = 0; a < numeroFoto; a++){%>
																		<%if(fieldMode.indexOf("thumb")==0){%>
																			<tr>
																				<td valign="top" class="showdocTitoloElemento"><br /><em><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title"," ")%></em></td>
																				<td align="center"><br /><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(a+1)%>,'<%=theArch%>','extptr','<%=popPath%>')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>"></a></td>
																			</tr>
																		<%}else{
																			if(fieldMode.indexOf("simple")==0){%>
																				<tr><td valign="center" class="showdocTitoloElemento"><a href="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>" target="_new">
																					<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title","")).equals("")){%>
																					<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title"," ")%></span>
																					<%}else{%>
																					<span class="cerca_b">allegato <%=(a+1)%></span>
																					<%}%></a></td>
																				</tr>
																		<%}
																			else{
																				if(fieldMode.indexOf("mp3")==0){%>
																					<tr><td valign="center" class="showdocTitoloElemento"><a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=theArch%>','extptr')" >
																						<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title","")).equals("")){%>
																						<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title"," ")%></span>
																						<%}else{%>
																						<span class="cerca_b">brano <%=(a+1)%></span>
																						<%}%></a></td>
																					</tr>
																				<%}
																			}
																		}
																	}%>
																	</table></div><%
																}
														%></div><%
														testSezione = true;
					    								testMacro = true;
													}
												}
												if(fieldId.equals("extref")){

													String imgPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@imgPrefix");
													String popPath = "";
														if(imgPrefix.equals(""))
															imgPrefix = imagePath+"/"+theArch;
														else
															popPath = imgPrefix;

													//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam singleParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance("com.regesta.upload.properties");
													//java.util.Properties propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile("com.regesta.upload.properties");
													//String pathProperties = "customization/"+userBean.getIdAccount()+"/com.regesta.upload.properties";
													//java.util.Properties propParam = null;
													try{
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}catch(Exception e){
													System.out.println("QUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
														//pathProperties = "com.regesta.upload.properties";
														//com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getInstance(pathProperties);
														//propParam = com.regesta.dams.utility.uploadFoto.command.UploadFotoSingletonParam.getPropertiesFile(pathProperties);
													}


													String visDir = "";
													String thumbPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@thumbPath","");										
													//if(propParam != null){
														//visDir = propParam.getProperty("java.thumb.dir")+"/";
													//}
													visDir += thumbPath;

													for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){ /* testo che almeno un campo sia pieno*/
														if(!theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"").equals("")){
															isFilled = true;
															break;
														}
													}
													if(isFilled){
														%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}
														%><div class="box_sch_bre"><%
														if(fieldMode.indexOf("thumb")==0 || fieldMode.indexOf("simple")==0 || fieldMode.indexOf("mp3")==0){
																int numeroFoto = theXML.contaNodi(fieldPrefix+"//extref/@href");%>
																	<div class="campo"><table align="right">
																	<%for(int a = 0; a < numeroFoto; a++){%>
																		<%if(fieldMode.indexOf("thumb")==0){%>
																			<tr>
																				<td valign="top" class="showdocTitoloElemento"><em><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title"," ")%></em></td>
																				<td align="left"><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(a+1)%>,'<%=theArch%>','extref','<%=popPath%>')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>"></a></td>
																			</tr>
																		<%}else{
																			if(fieldMode.indexOf("simple")==0){%>
																				<tr><td valign="center" class="showdocTitoloElemento"><a href="<%=imagePath%>/<%=theArch%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>" target="_new">
																					<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title","")).equals("")){%>
																					<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title"," ")%></span>
																					<%}else{%>
																					<span class="cerca_b">allegato <%=(a+1)%></span>
																					<%}%></a></td>
																				</tr>
																		<%}
																			else{
																				if(fieldMode.indexOf("mp3")==0){%>
																					<tr><td valign="center" class="showdocTitoloElemento"><a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=theArch%>','extref')" >
																						<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title","")).equals("")){%>
																						<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title"," ")%></span>
																						<%}else{%>
																						<span class="cerca_b">brano <%=(a+1)%></span>
																						<%}%></a></td>
																					</tr>
																				<%}
																			}
																		}
																	}%>
																	</table></div><%
																}
														%></div><%
														testSezione = true;
					    								testMacro = true;
													}
												}




											}
											else{
											if(campoControllato){

													String ilValoreCorrente = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@value");
													String externalPath = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@externalPath");
													String valoreNodo = theXML.valoreNodoHTML(elementoPath,"");

													int nodiOpzioni = theXMLValControllati.contaNodi(externalPath);
													for (int d = 0; d < nodiOpzioni; d++) {
															if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodo(externalPath + "[" + (d + 1) + "]/@value"))) {
																valoreNodo =theXMLValControllati.valoreNodo(externalPath + "[" + (d + 1) + "]/text()");
															}
														}
													%><%=valoreNodo%><%
											}

											else{
												%><%=elementoPathGroupFormat[0]%><%
												if(extraFunction.equals("")){
													 if(!theXML.valoreNodoHTML(elementoPath,"<br />","&nbsp;").equals("")){
													 %><%=elementoPathGroupBefore[0]%><%=theXML.valoreNodoHTML(elementoPath,"<br />","&nbsp;")%><%=elementoPathGroupAfter[0]%><br /><%
													 }
													 }else{
														if(extraFunction.startsWith("substring")){
														int inizio = Integer.parseInt(extraFunction.substring(10));
													 %><%=theXML.valoreNodoHTML(elementoPath,"<br />","&nbsp;").substring(inizio)%><br /><%
														}
													}
												}
											

										}}}}%>

								<%=part03%>

							<%
							testaRiga = true;
						}
					}

					}
				}
					if(!testSezione){
					%>
					<script type="text/javascript">hideSection('<%=idSezione%>')</script>
					<%
					}
					else{
					if(testSezioneOpen){
						%><script type="text/javascript">showSection('<%=idSezione%>')</script><%
					}
					areaTest = true;
					}

					testaRiga = false;

					%> 
					</div>


					<%
					if(testaRigaSepara){
					testaRigaSepara = false;
					 
					}
				}
			}


		if(!areaTest){
		%><script type="text/javascript">hideArea('<%=idMacroarea%>')</script><%
		}
		%>
 </div>
		 </div>
		 </div> 
		 <%
		 }
    }




%>






 