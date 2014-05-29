<%@page import="org.xdams.utility.ExpressionEvaluationUtils"%>
<%@page import="org.xdams.utility.testing.TestingViewBean"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.xdams.page.view.bean.MediaBean"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%
//	 INIZIO GESTIONE DATI
 	    int nodiMacroarea = theXMLconf.contaNodi("/root/macroarea");
	    for(int z = 0; z < nodiMacroarea;z++){
		    if(!theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/@summary","").equals("no")){
				boolean testMacro = false;
		    	int nodiSezione = theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione");
		    	for(int i = 0; i < nodiSezione;i++){
		    		if(!theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/@summary","").equals("no")){
					boolean testSezione = false;
		    			int nodiElemento= theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento");
		    			for(int x = 0; x < nodiElemento;x++){
		    				if(!theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@summary","").equals("no")){
		    					/*SCRIVO L'ELEMENTO*/
		    					String fieldType = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@type","");
		    					String fieldPath = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/text()","");
		    					String fieldFormatBefore = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format_before","");
		    					String fieldFormatAfter = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format_after","");
		    					String fieldFormat = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@format","");
		    					String fieldName = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@name","");
		    					String fieldPrefix = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@prefix","");
							String fieldInLine = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@inline","");
							String fieldControlled = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@externalPath","");
							String fieldControlledValue = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@value","");
							if(fieldType.equals("")){/* campo semplice*/
		    						String valoreNodo = theXML.valoreNodoHTML(fieldPath,"<br />","&nbsp;");
		    						 if(!fieldControlled.equals("") && !(valoreNodo.trim()).equals("")){
										int nodiOpzioni = theXMLValControllati.contaNodi(fieldControlled);
											for (int d = 0; d < nodiOpzioni; d++) {
												if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/@value"))) {
													valoreNodo =theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/text()");
												}
										}
									}
		    						if(!valoreNodo.equals("")){ /* esiste un valore */
		    							%><div class="riga_sch_bre" title="<%=fieldPath %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
		    							  		<div class="box_sch_bre">
									<div class="campo"><%=fieldFormatBefore+valoreNodo+fieldFormatAfter%></div></div>
		    						<%
		    							testSezione = true;
		    							testMacro = true;
		    						}
		    					}
		    					if(fieldType.equals("multi")){/* campo multiplo semplice */
									String fieldSuffix = fieldPath.substring(fieldPrefix.length());
									boolean isFilled = false;
									for(int a =0;a<theXML.contaNodi(fieldPrefix);a++){
										if(!theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]"+fieldSuffix,"").equals("")){
											isFilled = true;
											break;
										}
									}
									if(isFilled){
									%>
										<div class="riga_sch_bre" title="<%=fieldPath %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
										<div class="box_sch_bre">
									<%
									for(int a =0;a<theXML.contaNodi(fieldPrefix);a++){
											String valoreNodo = theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]"+fieldSuffix,"<br />","&nbsp;");
								 			if(!fieldControlled.equals("") && !(valoreNodo.trim()).equals("")){
										  		//DomManager domManager = DomManager.getInstance(confBean.getIsStatic(), confBean.getRealPath());
												//XMLBuilder theXMLValControllati  = new XMLBuilder(domManager.getDocument("/" + fieldControlled.replaceAll("document:", "")));
												int nodiOpzioni = theXMLValControllati.contaNodi(fieldControlled);
													for (int d = 0; d < nodiOpzioni; d++) {
														if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/@value"))) {
															valoreNodo =theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/text()");
														}
												}
											}
											if(!valoreNodo.equals("")){%><div class="campo"><%=fieldFormatBefore+valoreNodo+fieldFormatAfter+fieldFormat%><%
												if(!fieldInLine.equals("yes")){%> <%}%></div><%
											}
											else{%><%=fieldFormat%><%}
											}%>
											</div>
										<%
									}
		    						testSezione = true;
		    						testMacro = true;
	    						}
		    					if(fieldType.equals("multi_auther") || fieldType.equals("auther")){/* campo multiplo e singolo Authority semplice */
									String fieldSuffix = fieldPath.substring(fieldPrefix.length());
									boolean isFilled = false;
									for(int a =0;a<theXML.contaNodi(fieldPrefix);a++){
										if(!theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]"+fieldSuffix,"").equals("")){
											isFilled = true;
											break;
										}
									}
									if(isFilled){
										String fieldCode = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@code","");
										String fieldArchRef = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@arch_ref","");
										//String fieldWebApp = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@web_app","");
										String fieldQuery = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@query","");
										String fieldAlternative = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@alternative","");
										%><div class="riga_sch_bre"  title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
										<div class="box_sch_bre">
			 
										<%
											for(int a =0;a<theXML.contaNodi(fieldPrefix);a++){
											String valoreNodo = theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]"+fieldSuffix,"<br />","&nbsp;");
											if(!fieldControlled.equals("") && !(valoreNodo.trim()).equals("")){
												int nodiOpzioni = theXMLValControllati.contaNodi(fieldControlled);
													for (int d = 0; d < nodiOpzioni; d++) {
														if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/@value"))) {
															valoreNodo =theXMLValControllati.valoreNodoHTML(fieldControlled + "[" + (d + 1) + "]/text()");
														}
												}
											}
											if(!valoreNodo.equals("")){
												if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/@"+fieldCode,"")).equals("") && !fieldArchRef.equals("")){
													%><a href="#nogo" onclick="return infoAuther('<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/@"+fieldCode,"")%>','<%=fieldArchRef%>','<%=fieldQuery%>')" class="cerca_b">info</a>&#160;&#160;&#160;<%
												}
												if(!fieldAlternative.equals("")){
													if(!theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/"+fieldAlternative,"").equals("")){
														valoreNodo = theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/"+fieldAlternative," ");
													}
												}
												%><div class="campo"><%=fieldFormatBefore+valoreNodo+fieldFormatAfter+fieldFormat%><%
											if(!fieldInLine.equals("yes")){%> <%}%>
											</div>
											<%
											}
											else{%><%=fieldFormat%><%}
											}%>
											</div>
										<%
		    							testSezione = true;
		    							testMacro = true;
									}
	    						}
		    					if(fieldType.equals("multi_group")){
		    						/* campo multiplo complesso */
									int nodiSottoElemento = theXMLconf.contaNodi("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento");

									String[] fieldPathArray = new String[nodiSottoElemento];
									String[] fieldFormatBeforeArray = new String[nodiSottoElemento];
									String[] fieldFormatAfterArray = new String[nodiSottoElemento];
									String[] fieldFormatArray = new String[nodiSottoElemento];
									String[] fieldNameArray = new String[nodiSottoElemento];
									String[] fieldTypeArray = new String[nodiSottoElemento];
									String[] fieldControlledArray = new String[nodiSottoElemento];
									String[] fieldControlledValueArray = new String[nodiSottoElemento];

									boolean isFilled = false;

									for(int a =0;a<nodiSottoElemento;a++){
										fieldPathArray[a] = theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/text()");
										fieldFormatBeforeArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@format_before");
										fieldFormatAfterArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@format_after");
										fieldFormatArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@format");
										fieldNameArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@name");
										fieldTypeArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@type");
										fieldControlledArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@externalPath");
										fieldControlledValueArray[a] =  theXMLconf.valoreNodoHTML("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/elemento["+(a+1)+"]/@value");
									}
									for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){ /* testo che almeno un campo sia pieno*/
										for(int a =0;a<nodiSottoElemento;a++){
											String fieldSuffix = fieldPathArray[a].substring(fieldPrefix.length());
											if(!theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"").equals("")){
												isFilled = true;
												break;
											}
										}
									}
									if(isFilled){
										%><div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
										<div class="box_sch_bre">
										 
									 
										<%
										String autherQuery = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@query");
										String code = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@code");
										String arch = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@arch_ref");
										
										//out.println(autherQuery);
										//out.println(code);
										//out.println(arch);
										
										for(int y =0;y<theXML.contaNodi(fieldPrefix);y++){
											boolean skipInfo = false;
											String ilCodice = "";
											if(!code.startsWith("/")){
												ilCodice = theXML.valoreNodoHTML(fieldPrefix + "[" + (y+1) + "]/@"+code);
											}else{
												ilCodice = theXML.valoreNodoHTML(fieldPrefix + "[" + (y+1) + "]"+code);
											}
											//out.println(ilCodice);
								 			%><div class="campo">
								 			<%
											for(int a =0;a<nodiSottoElemento;a++){
												String fieldSuffix = fieldPathArray[a].substring(fieldPrefix.length());
												String valoreNodo = theXML.valoreNodoHTML(fieldPrefix+"["+(y+1)+"]"+fieldSuffix,"<br />","&nbsp;");
									 			if(!fieldControlledArray[a].equals("") && !(valoreNodo.trim()).equals("")){
													int nodiOpzioni = theXMLValControllati.contaNodi(fieldControlledArray[a]);
														for (int d = 0; d < nodiOpzioni; d++) {
															if (valoreNodo.equalsIgnoreCase(theXMLValControllati.valoreNodoHTML(fieldControlledArray[a] + "[" + (d + 1) + "]/@value"))) {
																valoreNodo =theXMLValControllati.valoreNodoHTML(fieldControlledArray[a] + "[" + (d + 1) + "]/text()");
															}
													}
												}
		
												if(!valoreNodo.equals("")){%><%=fieldNameArray[a]%> 
												
												<%if(fieldTypeArray[a].equals("auther")){%>
												gestire AUTHER sotto MULTI_GROUP
												<%}else{%>
												<%if(!skipInfo && !ilCodice.equals("")){
													skipInfo = true;
													String infoAuth = "<a href=\"#nogo\" onclick=\"return infoAuther('"+ilCodice+"','"+arch+"','"+autherQuery+"')\" class=\"cerca_b\">info</a>&#160;";
												%>
													<%=fieldFormatBeforeArray[a]+valoreNodo+infoAuth+fieldFormatAfterArray[a]+fieldFormatArray[a]%>
												<%}else{%>
													<%=fieldFormatBeforeArray[a]+valoreNodo+fieldFormatAfterArray[a]+fieldFormatArray[a]%>
												<%}%>
												
												<%}
												}
												else{%><%=fieldFormat%><%}
											
											}
											if(!fieldInLine.equals("yes")){%> <%}
											%></div><%	
										}
										%></div><%
		    							testSezione = true;
		    							testMacro = true;
									}
	    						}

								//TODO
	    						if(false && fieldType.equals("mediaBean")){
									//facciamo il build del MediaBean
									String xmlMedia = theXMLconf.getXMLFromNode(theXMLconf.getSingleNode("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]"));
									XMLBuilder mediaBuilder = new XMLBuilder(xmlMedia,false);
									out.println("<div id=\""+java.net.URLEncoder.encode("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]","iso-8859-1")+"\">");
									%> 
										<div class="riga_sch_bre" title="<%=fieldPrefix %>"><%if(!fieldName.equals("")){%><%=fieldName%><%}%></div>
										
										</div>
									<%
 								}
	    						//
	    						if(fieldType.equals("mediaBean")){
									//facciamo il build del MediaBean
									String xmlMedia = theXMLconf.getXMLFromNode(theXMLconf.getSingleNode("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]"));
									XMLBuilder mediaBuilder = new XMLBuilder(xmlMedia,false);
									 
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
											urlFoto = ExpressionEvaluationUtils.evaluate(prefixFoto, String.class, pageContext)+strHrefFoto;
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
													//out.println("suffixAppo "+ suffixAppo + "<br/>");
													//out.println("suffixAll "+ mediaBean.getXPathPrefix()+"["+(w+1)+"]"+suffixAppo + "<br/>");
												}else{
													suffixAppo = mediaBuilder.valoreNodo("/elemento/elemento["+(wa+1)+"]/text()");
													//out.println("suffixAppo111 "+ suffixAppo + "<br/>");
													
												}
										
												if(!theXML.valoreNodoHTML(suffixAppo).equals("")){
											%>
													<div class="didaFoto">
															<em><%=mediaBuilder.valoreNodoHTML("/elemento/elemento["+(wa+1)+"]/@name") %></em> 
															<%=theXML.valoreNodoHTML(suffixAppo,"<br />","&nbsp;") %>
													</div>
												<%} %>	
										<%}%></div>	
	   							  		<div class="riga_sch_breFoto" title="<%=mediaBean.getXPathPrefix()+"["+(w+1)+"]"%>"><%=strTitleFoto%></div><%
									}
									
								}
								if(fieldType.equals("custom")){/* campo personalizzato */
	    							String fieldMode = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@mode","");
									String fieldId = theXMLconf.valoreNodo("/root/macroarea["+(z+1)+"]/sezione["+(i+1)+"]/elemento["+(x+1)+"]/@id","");
									String fieldSuffix = "";
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
												String strTitle = theXML.valoreNodoHTML(xPathGroupPrefix+"["+(w+1)+"]"+xPathItemSuffix,"<br />","&nbsp;");
												%>
													<%if(new TestingViewBean().controllaLivello(workFlowBean,"1;5;2".split(";"))){%><span class="link">[</span><a href="#" onclick="copyInClipBoard('<%=strHref%>','<%=StringEscapeUtils.escapeEcmaScript(strTitle)%>','<%=clipBoardType %>')" class="link">copia nella clipboard</a><span class="link">]</span><%}%>
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
											//out.println("xPathItemSuffix aaa"+xPathItemSuffix+"<br />");
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
																			%><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(w+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','dao')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>"></a><br /><%
																		}else{
																			if(fieldMode.indexOf("simple")==0){
																				%><a href="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%>" target="_new">
																					<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)).equals("")){%>
																						<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(w+1)+"]"+fieldSuffix)%></span>
																					<%}else{%>
																						<span class="cerca_b">allegato <%=(w+1)%></span>
																					<%}%>
																				   </a> <%
																			}else{
																				if(fieldMode.indexOf("mp3")==0){%>
																					<a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(w+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','dao')" >
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
														<center><a class="cerca_b" onclick="return visualizzaImg(<%=docNumber%>,1,'<%=workFlowBean.getArchive().getAlias()%>','dao')" href="#nogo">accedi agli allegati digitali</a></center>
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
																			%><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(w+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','dao')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@href")%>"></a><br /><%
																		}else{
																			if(fieldMode.indexOf("simple")==0){
																				%><a href="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@href")%>" target="_new">
																					<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")).equals("")){%>
																						<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/daoloc["+(w+1)+"]/@title")%></span>
																					<%}else{%>
																						<span class="cerca_b">allegato <%=(a+1)%></span>
																					<%}%>
																				   </a> <%
																			}else{
																				if(fieldMode.indexOf("mp3")==0){%>
																					<a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','dao')" >
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
														<center><a class="cerca_b" onclick="return visualizzaImg(<%=docNumber%>,1,'<%=workFlowBean.getArchive().getAlias()%>','dao')" href="#nogo">accedi agli allegati digitali</a></center>
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
												imgPrefix = imagePath+"/"+workFlowBean.getArchive().getAlias();
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
																	<td align="center"><br /><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(a+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','extptr','<%=popPath%>')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>"></a></td>
																</tr>
															<%}else{
																if(fieldMode.indexOf("simple")==0){%>
																	<tr><td valign="center" class="showdocTitoloElemento"><a href="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@href")%>" target="_new">
																		<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title","")).equals("")){%>
																		<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extptr/@title"," ")%></span>
																		<%}else{%>
																		<span class="cerca_b">allegato <%=(a+1)%></span>
																		<%}%></a></td>
																	</tr>
															<%}
																else{
																	if(fieldMode.indexOf("mp3")==0){%>
																		<tr><td valign="center" class="showdocTitoloElemento"><a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','extptr')" >
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
												imgPrefix = imagePath+"/"+workFlowBean.getArchive().getAlias();
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
																	<td align="left"><a href="#nogo" onclick="return visualizzaImg(<%=docNumber%>,<%=(a+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','extref','<%=popPath%>')"><img vspace="1"  hspace="0" alt="<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>"  border="1" bordercolor="#000000" style="border:1px solid #000000" src="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>"></a></td>
																</tr>
															<%}else{
																if(fieldMode.indexOf("simple")==0){%>
																	<tr><td valign="center" class="showdocTitoloElemento"><a href="<%=imagePath%>/<%=workFlowBean.getArchive().getAlias()%><%=visDir%>/<%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@href")%>" target="_new">
																		<%if(!(theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title","")).equals("")){%>
																		<span class="cerca_b"><%=theXML.valoreNodoHTML(fieldPrefix+"["+(a+1)+"]/extref/@title"," ")%></span>
																		<%}else{%>
																		<span class="cerca_b">allegato <%=(a+1)%></span>
																		<%}%></a></td>
																	</tr>
															<%}
																else{
																	if(fieldMode.indexOf("mp3")==0){%>
																		<tr><td valign="center" class="showdocTitoloElemento"><a href="#nogo" onclick="return visualizzaMP3(<%=docNumber%>,<%=(a+1)%>,'<%=workFlowBean.getArchive().getAlias()%>','extref')" >
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


		    				}
		    			}
						if(testSezione){
						%>
							 
						<%
						}
		    		}
		    	}
			    if(testMacro){
			    	/*%><hr><%*/
				}
		    }
	    }
%>

