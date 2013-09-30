<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="42.5pt" margin-left="42.5pt" margin-bottom="28pt" margin-top="28pt" page-width="595pt" page-height="840pt" master-name="simpleA4">
					<fo:region-body margin-top="28pt" margin-bottom="42.5pt"/>
					<fo:region-before extent="28pt"/>
					<fo:region-after extent="42.5pt"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed" width="510pt">
						<fo:table-column column-width="340pt"/>
						<fo:table-column column-width="170pt"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell height="3pt">
									<fo:block/>
								</fo:table-cell>
								<fo:table-cell height="3pt">
									<fo:block/>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell height="3pt">
									<fo:block/>
								</fo:table-cell>
								<fo:table-cell height="3pt">
									<fo:block/>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row>
								<fo:table-cell>
									<fo:block line-height="10pt" font-size="9pt" text-align="left">xDams OpenSource: Stampa di tutte le informazioni</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block line-height="10pt" font-size="9pt" text-align="right"> Pagina <fo:page-number/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<xsl:for-each select="//c">
						<fo:table table-layout="fixed" width="510pt" space-before="50pt">
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block text-align="left" font-size="11pt" font-weight="bold">
											<fo:inline>Scheda&#160;</fo:inline>
											<xsl:value-of select="./@id"/>
											<fo:inline>&#160;-&#160;</fo:inline>
											<xsl:choose>
												<xsl:when test="./@level!='otherlevel'">
													<xsl:call-template name="livello"/>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="./@otherlevel"/>
												</xsl:otherwise>
											</xsl:choose>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						<fo:block>
							<!--         IDENTIFICAZIONE            -->
							<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">IDENTIFICAZIONE</fo:block>
							<fo:table table-layout="fixed" width="510pt" space-before="8pt">
								<fo:table-column column-width="170pt"/>
								<fo:table-column column-width="340pt"/>
								<fo:table-body>
									<xsl:if test="./did/unitid/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">codice interno</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:if test="./did/unitid/abbr/text()">
														<xsl:value-of select="./did/unitid/abbr/text()"/>
														<fo:inline>&#160;-&#160;</fo:inline>
													</xsl:if>
													<xsl:apply-templates select="./did/unitid/text()"/>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::numero di ordinamento:::::::::::::-->
									<xsl:if test="./did/unittitle/num[@type='ordinamento']/text() or ./did/unittitle/num[@type='ordinamento']/emph/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">numero di ordinamento</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:if test="./did/unittitle/num[@type='ordinamento']/text()">
														<fo:inline>definitivo:&#160;</fo:inline>
														<xsl:value-of select="./did/unittitle/num[@type='ordinamento']/text()"/>
													</xsl:if>
													<xsl:if test="./did/unittitle/num[@type='ordinamento']/emph/text()">
														<xsl:if test="./did/unittitle/num[@type='ordinamento']/text()">
															<fo:block/>
														</xsl:if>
														<fo:inline>provvisorio:&#160;</fo:inline>
														<xsl:value-of select="./did/unittitle/num[@type='ordinamento']/emph/text()"/>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::segnature:::::::::::::-->
									<xsl:if test="./did/container[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">segnatura attuale (posizione fisica)</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:for-each select="./did/container">
														<xsl:value-of select="./@type"/>
														<fo:inline>&#160;</fo:inline>
														<xsl:value-of select="./text()"/>
														<xsl:if test="following-sibling::container">
															<fo:inline>,&#160;</fo:inline>
														</xsl:if>
													</xsl:for-each>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<xsl:if test="./descgrp/odd[@type='segnatura antica o originaria']/p[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<fo:block font-size="10pt" font-weight="normal" color="black">segnatura antica o originaria</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:value-of select="./descgrp/odd[@type='segnatura antica o originaria']/p/text()"/>
													<xsl:if test="./descgrp/odd[@type='segnatura antica o originaria']/note/p/text()">
														<fo:block/>
														<xsl:value-of select="./descgrp/odd[@type='segnatura antica o originaria']/note/p/text()"/>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<xsl:if test="./descgrp/odd[@type='segnatura precedente']/p[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">segnatura precedente</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:value-of select="./descgrp/odd[@type='segnatura precedente']/p/text()"/>
													<xsl:if test="./descgrp/odd[@type='segnatura precedente']/note/p/text()">
														<fo:block/>
														<xsl:value-of select="./descgrp/odd[@type='segnatura precedente']/note/p/text()"/>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::denominazione:::::::::::::-->
									<fo:table-row>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">denominazione</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange">
											<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<fo:inline font-weight="bold">
													<xsl:value-of select="./did/unittitle/text()"/>
												</fo:inline>
												<xsl:if test="./did/unittitle/emph/text()">
													<fo:block/>
													<fo:inline font-style="italic">
														<xsl:value-of select="./did/unittitle/emph/text()"/>
													</fo:inline>
												</xsl:if>
												<xsl:if test="./did/unittitle/title/text()">
													<fo:block start-indent="5pt">Altra/e denominazione/i:</fo:block>
													<xsl:for-each select="./did/unittitle/title">
														<fo:list-block>
															<fo:list-item>
																<fo:list-item-label>
																	<fo:block/>
																</fo:list-item-label>
																<fo:list-item-body>
																	<fo:block start-indent="5pt">-&#160;<xsl:value-of select="./text()"/>
																	</fo:block>
																</fo:list-item-body>
															</fo:list-item>
														</fo:list-block>
													</xsl:for-each>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<!--::::::::::::titolario di classificazione:::::::::::::-->
									<xsl:if test="./descgrp/fileplan/p/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">titolario di classificazione</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:value-of select="./descgrp/fileplan/p/text()"/>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::soggetto conservatore:::::::::::::-->
									<xsl:if test="./did/repository[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">soggetto conservatore</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:apply-templates select="./did/repository/text()"/>
													<xsl:if test="./did/repository/address/addressline[1]/text()">
														<fo:inline>,&#160;</fo:inline>
														<xsl:value-of select="./did/repository/address/addressline[1]/text()"/>
													</xsl:if>
													<xsl:if test="./did/repository/address/addressline[2]/text()">
														<xsl:variable name="link">
															<xsl:value-of select="./did/repository/address/addressline[2]/text()"/>
														</xsl:variable>
														<fo:inline>,&#160;</fo:inline>
														<fo:basic-link external-destination="{$link}" show-destination="new" color="blue">
															<xsl:value-of select="$link"/>
														</fo:basic-link>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::cronologia:::::::::::::-->
									<xsl:if test="./did/unittitle/unitdate[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">data/e</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:if test="./did/unittitle/geogname/text()">
														<xsl:value-of select="./did/unittitle/geogname/text()"/>
														<fo:inline>,&#160;</fo:inline>
													</xsl:if>
													<xsl:value-of select="./did/unittitle/unitdate/text()"/>
													<xsl:if test="./did/unittitle/unitdate/emph/text()">
														<fo:block/>
														<xsl:value-of select="./did/unittitle/unitdate/emph/text()"/>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::descrizione fisica:::::::::::::-->
									<xsl:if test="./did/physdesc/genreform/text() or ./did/physdesc/extent/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">descrizione fisica</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:if test="./did/physdesc[@label='container']">
														<xsl:value-of select="./did/physdesc[@label='container']/genreform/text()"/>
														<xsl:if test="./did/physdesc[@label='container']/physfacet[@type='medium']">
															<xsl:text>&#160;</xsl:text>
															<xsl:value-of select="./did/physdesc[@label='container']/physfacet[@type='medium']/text()"/>
														</xsl:if>
													</xsl:if>
													<xsl:if test="./did/physdesc[@label='container' and ../physdesc[@label='content']]">
														<xsl:text>&#160;di&#160;</xsl:text>
													</xsl:if>
													<xsl:if test="./did/physdesc[@label='content'] or ./did/physdesc[not(@label)]">
														<xsl:for-each select="./did/physdesc[@label='content' or not(@label)]">
															<xsl:value-of select="./extent/text()"/>
															<xsl:text>&#160;</xsl:text>
															<xsl:value-of select="./genreform/text()"/>
															<xsl:if test="./dimensions">
																<fo:block/>
																<fo:inline>dimensioni:&#160;</fo:inline>
																<xsl:value-of select="./dimensions/dimensions[@type='altezza']/text()"/>
																<fo:inline>&#160;x&#160;</fo:inline>
																<xsl:value-of select="./dimensions/dimensions[@type='base']/text()"/>
																<fo:inline>&#160;(mm)&#160;</fo:inline>
															</xsl:if>
															<xsl:if test="./physfacet[@type='cromatismo']">
																<xsl:text>,&#160;</xsl:text>
																<xsl:value-of select="./physfacet[@type='cromatismo']/text()"/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='scala']">
																<xsl:text>,&#160;in&#160;scala&#160;</xsl:text>
																<xsl:value-of select="./physfacet[@type='scala']/text()"/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='note']">
																<fo:block margin-top="5pt">integrazioni:</fo:block>
																<fo:block linefeed-treatment="preserve">
																	<xsl:value-of select="./physfacet[@type='note']/text()"/>
																</fo:block>
															</xsl:if>
															<xsl:if test="following-sibling::physdesc[@label='content' or not(@label)]">
																<fo:block margin-bottom="5pt"/>
															</xsl:if>
														</xsl:for-each>
													</xsl:if>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::collocazione:::::::::::::-->
									<xsl:if test="./did/physloc[text()]">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">collocazione</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:for-each select="./did/physloc">
														<xsl:value-of select="./text()"/>
														<xsl:if test="following-sibling::physloc">
															<fo:inline>,&#160;</fo:inline>
														</xsl:if>
													</xsl:for-each>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
								</fo:table-body>
							</fo:table>
							<!--         CONTESTO            -->
							<xsl:if test="./did/origination//text() or ./descgrp[@encodinganalog='ISAD 2 context area']//text()">
								<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">CONTESTO</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="8pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<!--::::::::::::soggetto produttore:::::::::::::-->
										<xsl:if test="./did/origination//text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">soggetto/i produttore/i</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./did/origination/*">
														<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:value-of select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::storia istituzionale/amministrativa, nota biografica:::::::::::::-->
										<xsl:if test="./descgrp/bioghist/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">storia istituzionale/amministrativa, nota biografica</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/bioghist/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::storia archivistica:::::::::::::-->
										<xsl:if test="./descgrp/custodhist/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">storia archivistica</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/custodhist/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::modalità di acquisizione:::::::::::::-->
										<xsl:if test="./descgrp/acqinfo/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">modalità di acquisizione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/acqinfo/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         CONTENUTO            -->
							<xsl:if test="./descgrp[@encodinganalog='ISAD 3 content and structure area']//text() or ./controlaccess//text()">
								<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">CONTENUTO</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="8pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<!--::::::::::::ambiti e contenuto:::::::::::::-->
										<xsl:if test="./descgrp/scopecontent/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ambiti e contenuto</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/scopecontent/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::allegati:::::::::::::-->
										<!--materiale a stampa-->
										<xsl:if test="./descgrp/scopecontent/list/head[text()='Materiale a stampa allegato']">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">materiale a stampa</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./descgrp/scopecontent/list/item/bibref">
														<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="5pt" margin-top="3pt">
															<xsl:if test="./persname">
																<fo:inline text-transform="uppercase">
																	<xsl:value-of select="normalize-space(./persname/text())"/>
																</fo:inline>
															</xsl:if>
															<xsl:if test="./title">
																<xsl:if test="./persname/text()">
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<fo:inline font-style="italic">
																	<xsl:value-of select="normalize-space(./title/text())"/>
																</fo:inline>
															</xsl:if>
															<xsl:if test="./edition">
																<fo:inline>,&#160;in&#160;«<xsl:value-of select="normalize-space(./edition/text())"/>»</fo:inline>
															</xsl:if>
															<xsl:if test="./imprint/geogname">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./imprint/geogname/text())"/>
															</xsl:if>
															<xsl:if test="./imprint/publisher">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./imprint/publisher/text())"/>
															</xsl:if>
															<xsl:if test="./imprint/date">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./imprint/date/text())"/>
															</xsl:if>
															<xsl:if test="./num">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./num/text())"/>
															</xsl:if>
															<xsl:if test="./text()">
																<fo:inline>.&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./text())"/>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--materiale cartografico allegato-->
										<xsl:if test="./descgrp/scopecontent/list/head[text()='Materiale cartografico allegato']">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">materiale cartografico allegato</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./descgrp/scopecontent/list[child::head/text()='Materiale cartografico allegato']/item">
														<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="5pt" margin-top="3pt">
															<xsl:if test="./unittitle">
																<fo:inline font-weight="bold">
																	<xsl:value-of select="normalize-space(./unittitle/text())"/>
																</fo:inline>
															</xsl:if>
															<xsl:if test="./unittitle/text() and ./text()!=''">
																<fo:inline>,&#160;</fo:inline>
															</xsl:if>
															<xsl:if test="./text()">
																<xsl:value-of select="normalize-space(./text())"/>
															</xsl:if>
															<xsl:if test="./date">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./date/text())"/>
															</xsl:if>
															<xsl:if test="./genreform">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./genreform/text())"/>
															</xsl:if>
															<xsl:if test="./num[@type='quantità']">
																<fo:inline>&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./num[@type='quantità']/text())"/>
															</xsl:if>
															<xsl:if test="./num[@type='base'] or ./num[@type='altezza']">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./num[@type='altezza']/text())"/>
																<fo:inline>&#160;x&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./num[@type='base']/text())"/>
																<fo:inline>&#160;(B x H in mm)</fo:inline>
															</xsl:if>
															<xsl:if test="./note/p">
																<fo:inline>.&#160;Note:&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./note/p/text())"/>
															</xsl:if>
															<xsl:if test="following-sibling::item">
																<fo:block space-after="6pt"/>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--altri allegati-->
										<xsl:if test="./descgrp/scopecontent/list/head[text()='Altri allegati']">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">altri allegati</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./descgrp/scopecontent/list[child::head/text()='Altri allegati']/item">
														<xsl:variable name="descrizione" select="./descgrp/scopecontent/list[child::head/text()='Altri allegati']/item/text()"/>
														<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="5pt" margin-top="3pt">
															<xsl:if test="./unittitle">
																<xsl:value-of select="./unittitle/text()"/>
															</xsl:if>
															<xsl:if test="./unittitle/text() and ./text()">
																<fo:inline>,&#160;</fo:inline>
															</xsl:if>
															<xsl:if test="./text()">
																<xsl:value-of select="normalize-space(./text())"/>
															</xsl:if>
															<xsl:if test="./date">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./date/text())"/>
															</xsl:if>
															<xsl:if test="./genreform">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./genreform/text())"/>
															</xsl:if>
															<xsl:if test="./num[@type='quantità']">
																<fo:inline>&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./num[@type='quantità']/text())"/>
															</xsl:if>
															<xsl:if test="./note/p">
																<fo:inline>.&#160;Note:&#160;</fo:inline>
																<xsl:value-of select="normalize-space(./note/p/text())"/>
															</xsl:if>
															<xsl:if test="following-sibling::item">
																<fo:block space-after="6pt"/>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::procedure, tempi e criteri di valutazione e scarto:::::::::::::-->
										<xsl:if test="./descgrp/appraisal/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">procedure, tempi e criteri di valutazione e scarto</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/appraisal/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::incrementi previsti:::::::::::::-->
										<xsl:if test="./descgrp/accruals/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">incrementi previsti</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/accruals/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::criteri di ordinamento:::::::::::::-->
										<xsl:if test="./descgrp/arrangement/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">criteri di ordinamento</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/arrangement/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::chiavi di accesso:::::::::::::-->
										<xsl:if test="./controlaccess//text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">chiavi di accesso</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:if test="./controlaccess/corpname">
															<fo:inline font-weight="bold" font-style="italic">Enti e istituzioni</fo:inline>
															<xsl:for-each select="./controlaccess/corpname">
																<fo:block>
																	<fo:inline>- </fo:inline>
																	<xsl:value-of select="./text()"/>
																	<xsl:if test="./@role">
																		<fo:inline>&#160;(</fo:inline>
																		<xsl:value-of select="./@role"/>
																		<fo:inline>)</fo:inline>
																	</xsl:if>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./controlaccess/persname">
															<fo:inline font-weight="bold" font-style="italic">Persone</fo:inline>
															<xsl:for-each select="./controlaccess/persname">
																<fo:block>
																	<fo:inline>- </fo:inline>
																	<xsl:value-of select="./text()"/>
																	<xsl:if test="./@role">
																		<fo:inline>&#160;(</fo:inline>
																		<xsl:value-of select="./@role"/>
																		<fo:inline>)</fo:inline>
																	</xsl:if>
																	<xsl:if test="./@altrender">
																		<fo:inline>,&#160;vedi&#160;anche&#160;</fo:inline>
																		<xsl:value-of select="./@altrender"/>
																	</xsl:if>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./controlaccess/famname">
															<fo:inline font-weight="bold" font-style="italic">Famiglie</fo:inline>
															<xsl:for-each select="./controlaccess/famname">
																<fo:block>
																	<fo:inline>- </fo:inline>
																	<xsl:value-of select="./text()"/>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./controlaccess/geogname">
															<fo:inline font-weight="bold" font-style="italic">Luoghi</fo:inline>
															<xsl:for-each select="./controlaccess/geogname">
																<fo:block>
																	<fo:inline>- </fo:inline>
																	<xsl:value-of select="./text()"/>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./controlaccess/name">
															<fo:inline font-weight="bold" font-style="italic">Notevoli</fo:inline>
															<xsl:for-each select="./controlaccess/name">
																<fo:block>
																	<fo:inline>- </fo:inline>
																	<xsl:value-of select="./text()"/>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row>
											<fo:table-cell>
												<fo:block/>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         ACCESSO            -->
							<xsl:if test="./did/langmaterial/language/text() or ./descgrp[@encodinganalog='ISAD 4 conditions of access and use area']//text()">
								<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">ACCESSO</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="8pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<!--::::::::::::condizioni che regolano l'accesso:::::::::::::-->
										<xsl:if test="./descgrp/accessrestrict/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">condizioni che regolano l'accesso</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/accessrestrict/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::condizioni di riproduzione e copyright:::::::::::::-->
										<xsl:if test="./descgrp/userestrict/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">condizioni di riproduzione e copyright</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/userestrict/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::lingua della documentazione:::::::::::::-->
										<xsl:if test="./did/langmaterial/language/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">lingua della documentazione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./did/langmaterial/language/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::caratteristiche materiali e requisiti tecnici:::::::::::::-->
										<xsl:if test="./descgrp/phystech//text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">caratteristiche materiali e requisiti tecnici</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:if test="./descgrp/phystech/p/text()">
															<xsl:apply-templates select="./descgrp/phystech/p"/>
														</xsl:if>
														<xsl:if test="./descgrp/phystech/phystech[@type='danno']/p/text()">
															<fo:block/>
															<fo:inline>danno/i:&#160;</fo:inline>
															<xsl:for-each select="./descgrp/phystech/phystech[@type='danno']">
																<xsl:value-of select="./p/text()"/>
																<xsl:if test="following-sibling::phystech[@type='danno']">
																	<xsl:text>,&#160;</xsl:text>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./descgrp/phystech/phystech[@type='causa']/p/text()">
															<fo:block/>
															<fo:inline>causa/e:&#160;</fo:inline>
															<xsl:for-each select="./descgrp/phystech/phystech[@type='causa']">
																<xsl:value-of select="./p/text()"/>
																<xsl:if test="following-sibling::phystech[@type='causa']">
																	<xsl:text>,&#160;</xsl:text>
																</xsl:if>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./descgrp/phystech/note/p/text()">
															<fo:block/>
															<xsl:value-of select="./descgrp/phystech/note/p/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::strumenti di ricerca:::::::::::::-->
										<xsl:if test="./descgrp/otherfindaid//text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">strumenti di ricerca</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:if test="./descgrp/otherfindaid/p/text()">
															<fo:block text-indent="3pt" end-indent="3pt">
																<xsl:apply-templates select="./descgrp/otherfindaid/p"/>
															</fo:block>
														</xsl:if>
														<xsl:if test="./descgrp/otherfindaid/bibref//text()">
															<fo:block start-indent="5pt" margin-top="5pt">pubblicato a stampa:</fo:block>
															<xsl:for-each select="./descgrp/otherfindaid/bibref">
																<fo:list-block>
																	<fo:list-item>
																		<fo:list-item-label>
																			<fo:block/>
																		</fo:list-item-label>
																		<fo:list-item-body>
																			<fo:block start-indent="3pt" end-indent="3pt">
																				<fo:inline>-&#160;</fo:inline>
																				<xsl:if test="./persname">
																					<fo:inline text-transform="uppercase">
																						<xsl:value-of select="normalize-space(./persname/text())"/>
																					</fo:inline>
																				</xsl:if>
																				<xsl:if test="./title">
																					<fo:inline>,&#160;</fo:inline>
																					<fo:inline font-style="italic">
																						<xsl:value-of select="normalize-space(./title/text())"/>
																					</fo:inline>
																				</xsl:if>
																				<xsl:if test="./edition">
																					<fo:inline>,&#160;in&#160;«<xsl:value-of select="normalize-space(./edition/text())"/>»</fo:inline>
																				</xsl:if>
																				<xsl:if test="./imprint/geogname">
																					<fo:inline>,&#160;</fo:inline>
																					<xsl:value-of select="normalize-space(./imprint/geogname/text())"/>
																				</xsl:if>
																				<xsl:if test="./imprint/publisher">
																					<fo:inline>,&#160;</fo:inline>
																					<xsl:value-of select="normalize-space(./imprint/publisher/text())"/>
																				</xsl:if>
																				<xsl:if test="./imprint/date">
																					<fo:inline>,&#160;</fo:inline>
																					<xsl:value-of select="normalize-space(./imprint/date/text())"/>
																				</xsl:if>
																				<xsl:if test="./num">
																					<fo:inline>,&#160;</fo:inline>
																					<xsl:value-of select="normalize-space(./num/text())"/>
																				</xsl:if>
																				<xsl:if test="./text()">
																					<fo:inline>.&#160;</fo:inline>
																					<xsl:value-of select="normalize-space(./text())"/>
																				</xsl:if>
																			</fo:block>
																		</fo:list-item-body>
																	</fo:list-item>
																</fo:list-block>
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="./descgrp/otherfindaid/archref//text()">
															<fo:block start-indent="5pt" margin-top="5pt">pubblicato su web:</fo:block>
															<xsl:for-each select="./descgrp/otherfindaid/archref">
																<fo:list-block>
																	<fo:list-item>
																		<fo:list-item-label>
																			<fo:block/>
																		</fo:list-item-label>
																		<fo:list-item-body>
																			<fo:block start-indent="3pt" end-indent="3pt">
																				<fo:inline>-&#160;</fo:inline>
																				<xsl:if test="./unittitle/text()">
																					<xsl:value-of select="./unittitle/text()"/>
																				</xsl:if>
																				<xsl:if test="./abstract/text()">
																					<fo:inline>,&#160;</fo:inline>
																					<xsl:value-of select="./abstract/text()"/>
																				</xsl:if>
																				<xsl:if test="./@href">
																					<xsl:variable name="linkWeb">
																						<xsl:value-of select="./@href"/>
																					</xsl:variable>
																					<fo:inline>,&#160;</fo:inline>
																					<fo:basic-link external-destination="{$linkWeb}" show-destination="new" color="blue">
																						<xsl:value-of select="$linkWeb"/>
																					</fo:basic-link>
																				</xsl:if>
																			</fo:block>
																		</fo:list-item-body>
																	</fo:list-item>
																</fo:list-block>
															</xsl:for-each>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         DOCUMENTAZIONE            -->
							<xsl:if test="./descgrp[@encodinganalog='ISAD 5 allied materials area']//text()">
								<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">DOCUMENTAZIONE</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="8pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<!--::::::::::::esistenza e localizzazione degli originali:::::::::::::-->
										<xsl:if test="./descgrp/originalsloc/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">esistenza e localizzazione degli originali</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/originalsloc/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::esistenza e localizzazione di copie:::::::::::::-->
										<xsl:if test="./descgrp/altformavail/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">esistenza e localizzazione di copie</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/altformavail/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::unità di descrizione collegate:::::::::::::-->
										<xsl:if test="./descgrp/relatedmaterial/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">unità di descrizione collegate</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/relatedmaterial/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::unità di descrizione separate:::::::::::::-->
										<xsl:if test="./descgrp/separatedmaterial/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">unità di descrizione separate</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./descgrp/separatedmaterial/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::bibliografia:::::::::::::-->
										<xsl:if test="./descgrp/bibliography//text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">bibliografia</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:if test="./descgrp/bibliography/p/text()">
															<fo:block text-indent="3pt" end-indent="3pt">
																<xsl:apply-templates select="./descgrp/bibliography/p"/>
															</fo:block>
														</xsl:if>
														<xsl:if test="./descgrp/bibliography/bibref//text()">
															<xsl:if test="./descgrp/bibliography/bibref//text() and ./descgrp/bibliography/p/text()">
																<fo:block margin-top="5pt"/>
															</xsl:if>
															<fo:block>
																<xsl:for-each select="./descgrp/bibliography/bibref">
																	<fo:list-block>
																		<fo:list-item>
																			<fo:list-item-label>
																				<fo:block/>
																			</fo:list-item-label>
																			<fo:list-item-body>
																				<fo:block start-indent="3pt" end-indent="3pt">
																					<fo:inline>-&#160;</fo:inline>
																					<xsl:if test="./persname">
																						<fo:inline text-transform="uppercase">
																							<xsl:value-of select="normalize-space(./persname/text())"/>
																						</fo:inline>
																					</xsl:if>
																					<xsl:if test="./title">
																						<fo:inline>,&#160;</fo:inline>
																						<fo:inline font-style="italic">
																							<xsl:value-of select="normalize-space(./title/text())"/>
																						</fo:inline>
																					</xsl:if>
																					<xsl:if test="./edition">
																						<fo:inline>,&#160;in&#160;«<xsl:value-of select="normalize-space(./edition/text())"/>»</fo:inline>
																					</xsl:if>
																					<xsl:if test="./imprint/geogname">
																						<fo:inline>,&#160;</fo:inline>
																						<xsl:value-of select="normalize-space(./imprint/geogname/text())"/>
																					</xsl:if>
																					<xsl:if test="./imprint/publisher">
																						<fo:inline>,&#160;</fo:inline>
																						<xsl:value-of select="normalize-space(./imprint/publisher/text())"/>
																					</xsl:if>
																					<xsl:if test="./imprint/date">
																						<fo:inline>,&#160;</fo:inline>
																						<xsl:value-of select="normalize-space(./imprint/date/text())"/>
																					</xsl:if>
																					<xsl:if test="./num">
																						<fo:inline>,&#160;</fo:inline>
																						<xsl:value-of select="normalize-space(./num/text())"/>
																					</xsl:if>
																					<xsl:if test="./text()">
																						<fo:inline>.&#160;</fo:inline>
																						<xsl:value-of select="normalize-space(./text())"/>
																					</xsl:if>
																				</fo:block>
																			</fo:list-item-body>
																		</fo:list-item>
																	</fo:list-block>
																</xsl:for-each>
															</fo:block>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         NOTE            -->
							<xsl:if test="./note/p/text() or ./processinfo/note/p/text()">
								<fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">NOTE</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="8pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<!--::::::::::::note:::::::::::::-->
										<xsl:if test="./note/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">note</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./note/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::nota dell'archivista:::::::::::::-->
										<xsl:if test="./processinfo/note/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">note redazionali</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="10pt" font-weight="normal" color="black" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
														<xsl:apply-templates select="./processinfo/note/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
						</fo:block>
						<fo:block text-align="center" margin-bottom="20pt" margin-top="20pt">- --  ---   -- : --   ---  -- -</fo:block>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="livello">
		<xsl:if test="./@level='fonds'">
			<fo:inline>fondo</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='subfonds'">
			<fo:inline>subfondo</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='collection'">
			<fo:inline>collezione, raccolta</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='series'">
			<fo:inline>serie</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='subseries'">
			<fo:inline>sottoserie</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='file'">
			<fo:inline>unità archivistica</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='item'">
			<fo:inline>unità documentaria</fo:inline>
		</xsl:if>
		<xsl:if test="./@otherlevel='subfile'">
			<fo:inline>sottounità archivistica</fo:inline>
		</xsl:if>
		<xsl:if test="./@otherlevel='container'">
			<fo:inline>unità di conservazione</fo:inline>
		</xsl:if>
	</xsl:template>
	<xsl:template match="p">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="em">
		<fo:wrapper font-style="italic">
			<xsl:apply-templates/>
		</fo:wrapper>
	</xsl:template>
	<xsl:template match="strong">
		<fo:wrapper font-weight="bold">
			<xsl:apply-templates/>
		</fo:wrapper>
	</xsl:template>
	<xsl:template match="br">
		<fo:block/>
	</xsl:template>
</xsl:stylesheet>
