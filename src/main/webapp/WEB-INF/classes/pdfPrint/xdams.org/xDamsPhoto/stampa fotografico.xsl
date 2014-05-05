<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:strip-space elements="*"/>
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="36pt" margin-left="28pt" margin-bottom="28pt" margin-top="28pt" page-width="595pt" page-height="840pt" master-name="simpleA4">
					<fo:region-body margin-top="28pt" margin-bottom="42pt"/>
					<fo:region-before extent="28pt"/>
					<fo:region-after extent="28pt"/>
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
								<fo:table-cell>
									<fo:block line-height="10pt" font-size="9pt" text-align="left">Fotografico xDams Open Source: stampa completa</fo:block>
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
					<!--		<fo:block font-size="14pt" line-height="20pt" space-after.optimum="14pt" text-align="center">
					<xsl:value-of select="./rsp/xw_export/c/did/unittitle/text()"/>
				</fo:block>-->
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
						</fo:table-body>
					</fo:table>
					<xsl:for-each select="//c">
						<xsl:variable name="spazio">
							<xsl:call-template name="generaLivello"/>mm</xsl:variable>
						<fo:block text-align="center" start-indent="{$spazio}" font-size="12pt" font-weight="bold" space-before="30pt" color="#C65F1D">
							<fo:inline>Scheda</fo:inline>
							<fo:inline>&#160;<xsl:apply-templates select="./@id"/>
							</fo:inline>
						</fo:block>
						<fo:block start-indent="{$spazio}">
							<!--         IDENTIFICAZIONE            -->
							
								<xsl:if test="not(./@level='fonds' or ./@level='collection' or ./@level='series' or ./@level='subseries' or ./@level='file' or ./@level='item')">
											<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D"></fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
									
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ATTENZIONE</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														DATI NON STAMPABILI PER "OGTS" NON DEFINITO
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
								
										</fo:table-body>
										</fo:table>
												</xsl:if>
										
						<xsl:if test="./@level">
								<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">IDENTIFICAZIONE</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<xsl:if test="./did/materialspec[@encodinganalog='OGTS']/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Forma specifica dell'oggetto (OGTS)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTS']/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/materialspec[@encodinganalog='OGTD']/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Definizione dell'oggetto (OGTD)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/materialspec[@encodinganalog='OGTD']/num/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Quantità (QNTN)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/num/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./@audience">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Specifiche di accesso ai dati (ADS)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:if test="./@audience='external'">pubblica</xsl:if>
														<xsl:if test="./@audience='internal'">privata</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unitid/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Codice identificativo gerarchico</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unitid/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./originalsloc/p/num/text() !='' and ./@level='file' ">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Numeri d'inventario (INVN)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./originalsloc[@encodinganalog='INVN']/p/num[@type='from']/text()"/>
														<fo:inline>-</fo:inline>
														<xsl:apply-templates select="./originalsloc[@encodinganalog='INVN']/p/num[@type='to']/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./originalsloc/p/num/text() !='' and ./@level='item'">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Numero inventario (INVN)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./originalsloc[@encodinganalog='INVN']/p/num/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unitid/@repositorycode">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Codice ente responsabile</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unitid/@repositorycode"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unitid/abbr">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Denominazione abbreviata dell'archivio</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unitid/abbr"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unitid/@identifier">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Nome virtuale dell'archivio</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unitid/@identifier"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/title[@encodinganalog='SGLT']/text() !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Titolo proprio (SGLT)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unittitle[@encodinganalog='SGLA']/title[@encodinganalog='SGLT']/text()"/>
														<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/title[@encodinganalog='SGLT']/emph/text() !=''">
															<fo:block/>
															<fo:inline font-style="italic">specifiche:&#160;</fo:inline>
															<xsl:apply-templates select="./did/unittitle[@encodinganalog='SGLA']/title[@encodinganalog='SGLT']/emph/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/text() !='' and ./@level='fonds' or ./@level='collection' or ./@level='series' or ./@level='subseries' ">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Denominazione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unittitle[@encodinganalog='SGLA']/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										
										<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/text() !='' and ./@level='file' or ./@level='item' ">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Titolo attribuito (SGLA)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./did/unittitle[@encodinganalog='SGLA']/text()"/>
														<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/emph/text() !=''">
															<fo:block/>
															<fo:inline font-style="italic">specifiche:&#160;</fo:inline>
															<xsl:apply-templates select="./did/unittitle[@encodinganalog='SGLA']/emph/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/title[@type='altra denominazione']/text() !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Altra/e denominazione/i</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/unittitle[@encodinganalog='SGLA']/title[@type='altra denominazione']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unitdate[@encodinganalog='DT']/text() !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Cronologia (DT)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unitdate[@encodinganalog='DT']/text()"/>
														<xsl:if test="./did/unitdate[@encodinganalog='DT']/emph">
															<fo:block/>
															<fo:inline font-style="italic">motivazione: </fo:inline>
															<xsl:apply-templates select="./did/unitdate[@encodinganalog='DT']/emph/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/unittitle[@encodinganalog='SGLA']/geogname/text() !='' ">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Luogo della ripresa (LRC)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/unittitle[@encodinganalog='SGLA']/geogname/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/origination/persname[@encodinganalog='AUFN']">
											<xsl:for-each select="./did/origination/persname[@encodinganalog='AUFN']">
												<fo:table-row>
													<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Autore della fotografia - persona (AUFN)</fo:block>
													</fo:table-cell>
													<fo:table-cell border-bottom="0.5pt solid darkblue">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
															<fo:block/>
															<xsl:if test="./@role">
																<fo:inline font-style="italic">ruolo: </fo:inline>
																<xsl:apply-templates select="./@role"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./emph/text()">
																<fo:inline font-style="italic">motivazione dell'attribuzione: </fo:inline>
																<xsl:apply-templates select="./emph/text()"/>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="./did/origination/corpname[@encodinganalog='AUFB']">
											<xsl:for-each select="./did/origination/corpname[@encodinganalog='AUFB']">
												<fo:table-row>
													<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Autore della fotografia - ente (AUFB)</fo:block>
													</fo:table-cell>
													<fo:table-cell border-bottom="0.5pt solid darkblue">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
															<fo:block/>
															<xsl:if test="./@role">
																<fo:inline font-style="italic">ruolo: </fo:inline>
																<xsl:apply-templates select="./@role"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./emph/text()">
																<fo:inline font-style="italic">motivazione dell'attribuzione: </fo:inline>
																<xsl:apply-templates select="./emph/text()"/>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="./did/origination/persname[@encodinganalog='PDFN']">
											<xsl:for-each select="./did/origination/persname[@encodinganalog='PDFN']">
												<fo:table-row>
													<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Responsabilità su produzione e diffusione - persona (PDFN)</fo:block>
													</fo:table-cell>
													<fo:table-cell border-bottom="0.5pt solid darkblue">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
															<fo:block/>
															<xsl:if test="./@role">
																<fo:inline font-style="italic">ruolo: </fo:inline>
																<xsl:apply-templates select="./@role"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./emph/text()">
																<fo:inline font-style="italic">motivazione dell'attribuzione: </fo:inline>
																<xsl:apply-templates select="./emph/text()"/>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="./did/origination/corpname[@encodinganalog='PDFB']">
											<xsl:for-each select="./did/origination/corpname[@encodinganalog='PDFB']">
												<fo:table-row>
													<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Responsabilità su produzione e diffusione - ente (PDFB)</fo:block>
													</fo:table-cell>
													<fo:table-cell border-bottom="0.5pt solid darkblue">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
															<fo:block/>
															<xsl:if test="./@role">
																<fo:inline font-style="italic">ruolo: </fo:inline>
																<xsl:apply-templates select="./@role"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./emph/text()">
																<fo:inline font-style="italic">motivazione dell'attribuzione: </fo:inline>
																<xsl:apply-templates select="./emph/text()"/>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="./did/repository and ./@level='fonds' or ./@level='collection' or ./@level='series' or ./@level='subseries'">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto conservatore</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/repository/text()"/>
														<xsl:if test="./did/repository/address/addressline[1]">&#160;/&#160;<xsl:value-of select="./did/repository/address/addressline[1]"/>
														</xsl:if>
														<xsl:if test="./did/repository/address/addressline[2]">&#160;/&#160;<xsl:value-of select="./did/repository/address/addressline[2]"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/origination/corpname[@role='creator']/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (ente)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/origination/corpname[@role='creator']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/origination/persname[@role='creator']/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (persona)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/origination/persname[@role='creator']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/origination/famname[@role='creator']/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (famiglia)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/origination/famname[@role='creator']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/physloc[@encodinganalog='LDCN'] or ./did/physloc[@encodinganalog='LDCS']">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Collocazione specifica (LDC) - contenitore</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/physloc[@encodinganalog='LDCN']/text()"/>
														<fo:block/>
														<xsl:if test="./did/physloc[@encodinganalog='LDCS']/text()">
															<fo:inline font-style="italic">specifiche: </fo:inline>
															<xsl:apply-templates select="./did/physloc[@encodinganalog='LDCS']/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/container">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Ubicazione - collocazione fisica (UBFC)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./did/container/@type"/>&#160;<xsl:apply-templates select="./did/container/text()"/>
														<xsl:if test="./did/container/emph/text()">
															<fo:inline>&#160;(<xsl:apply-templates select="./did/container/emph/text()"/>)</fo:inline>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
					</xsl:if>
							<xsl:if test="./@level and ./did/physdesc !='' or ./controlaccess !='' or ./phystech  !='' or ./odd  !='' ">
								<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">DESCRIZIONE</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general'] !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Dati generali (servizio/reportage/album)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@encodinganalog='MTX' and @type='colore']">
															<fo:inline font-style="italic">colore:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@encodinganalog='MTX' and @type='colore']/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/genreform">
															<fo:inline font-style="italic">materia e tecnica:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/genreform/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/dimensions[@encodinganalog='FRM']">
															<fo:inline font-style="italic">formato:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/dimensions[@encodinganalog='FRM']/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/dimensions[@encodinganalog='MIS']">
															<fo:inline font-style="italic">misure:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/dimensions[@encodinganalog='MIS']/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@encodinganalog='MTS' and @type='note']">
															<fo:inline font-style="italic">note tecniche:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@encodinganalog='MTS' and @type='note']/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/subject">
															<fo:inline font-style="italic">indicazioni generali sul soggetto:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/subject/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@type='stato conservazione' and @encodinganalog='STCC']">
															<fo:inline font-style="italic">stato di conservazione:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@type='stato conservazione' and @encodinganalog='STCC']/text()"/>
															<fo:block/>
														</xsl:if>
														<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@type='specifiche conservazione' and @encodinganalog='STCS']">
															<fo:inline font-style="italic">specifiche di conservazione:&#160;</fo:inline>
															<xsl:apply-templates select="./did/physdesc[@encodinganalog='MT' and @label='general']/physfacet[@type='specifiche conservazione' and @encodinganalog='STCS']/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/physdesc[@encodinganalog='MT' and @label='specific'] !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Dati specifici (singola/e fotografia/e)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/physdesc[@encodinganalog='MT' and @label='specific']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:if test="./@altrender">
																<fo:inline font-style="italic">n.inv.:&#160;</fo:inline>
																<xsl:apply-templates select="./@altrender"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./@id">
																<fo:inline font-style="italic">n.immagine:&#160;</fo:inline>
																<xsl:apply-templates select="./@id"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@encodinganalog='MTX' and @type='colore']">
																<fo:inline font-style="italic">colore:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@encodinganalog='MTX' and @type='colore']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./genreform">
																<fo:inline font-style="italic">materia e tecnica:&#160;</fo:inline>
																<xsl:apply-templates select="./genreform/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./dimensions[@encodinganalog='FRM']">
																<fo:inline font-style="italic">formato:&#160;</fo:inline>
																<xsl:apply-templates select="./dimensions[@encodinganalog='FRM']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./dimensions[@encodinganalog='MIS']">
																<fo:inline font-style="italic">altezza e larghezza:&#160;</fo:inline>
																<xsl:apply-templates select="./dimensions[@encodinganalog='MIS']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@encodinganalog='MTS' and @type='note']">
																<fo:inline font-style="italic">note tecniche:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@encodinganalog='MTS' and @type='note']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='scatto']">
																<fo:inline font-style="italic">orientamento:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@type='scatto']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./subject">
																<fo:inline font-style="italic">indicazioni sul soggetto:&#160;</fo:inline>
																<xsl:apply-templates select="./subject/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='inquadratura']">
																<fo:inline font-style="italic">inquadratura:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@type='inquadratura']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='stato conservazione' and @encodinganalog='STCC']">
																<fo:inline font-style="italic">stato di conservazione:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@type='stato conservazione' and @encodinganalog='STCC']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@type='specifiche conservazione' and @encodinganalog='STCS']">
																<fo:inline font-style="italic">specifiche di conservazione:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@type='specifiche conservazione' and @encodinganalog='STCS']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./extptr/@href">
																<fo:inline font-style="italic">url:&#160;</fo:inline>
																<xsl:apply-templates select="./extptr/@href"/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/physdesc[@encodinganalog='FVC' and @label='specific'] !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Dati specifici (singola/e fotografia/e virtuali)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/physdesc[@encodinganalog='FVC' and @label='specific']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:if test="./genreform[@encodinganalog='FVCF']">
																<fo:inline font-style="italic">formato di memorizzazione:&#160;</fo:inline>
																<xsl:apply-templates select="./genreform[@encodinganalog='FVCF']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@encodinganalog='FVCP']">
																<fo:inline font-style="italic">programma di memorizzazione:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@encodinganalog='FVCP']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./dimensions[@encodinganalog='FVCC']">
																<fo:inline font-style="italic">profondità di colore:&#160;</fo:inline>
																<xsl:apply-templates select="./dimensions[@encodinganalog='FVCC']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./dimensions[@encodinganalog='FVCU']/@unit">
																<fo:inline font-style="italic">unità di misura:&#160;</fo:inline>
																<xsl:apply-templates select="./dimensions[@encodinganalog='FVCU']/@unit"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./dimensions[@encodinganalog='FVCM']">
																<fo:inline font-style="italic">misure:&#160;</fo:inline>
																<xsl:apply-templates select="./dimensions[@encodinganalog='FVCM']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./physfacet[@encodinganalog='FVCN' and @type='note']">
																<fo:inline font-style="italic">note:&#160;</fo:inline>
																<xsl:apply-templates select="./physfacet[@encodinganalog='FVCN' and @type='note']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./genreform[@encodinganalog='FVM']">
																<fo:inline font-style="italic">memoria di massa:&#160;</fo:inline>
																<xsl:apply-templates select="./genreform[@encodinganalog='FVM']/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./subject">
																<fo:inline font-style="italic">indicazioni sul soggetto:&#160;</fo:inline>
																<xsl:apply-templates select="./subject/text()"/>
																<fo:block/>
															</xsl:if>
															<xsl:if test="./extptr/@href">
																<fo:inline font-style="italic">url:&#160;</fo:inline>
																<xsl:apply-templates select="./extptr/@href"/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
																<fo:block/>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./did/physdesc[@label='container'] !=''">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Consistenza</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./did/physdesc[@label='container']">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./genreform/text()"/>&#160;<xsl:apply-templates select="./extent/text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./phystech/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Conservazione (CO)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./phystech[@encodinganalog='STCC']/p/text()"/>
														<fo:block/>
														<xsl:if test="./phystech[@encodinganalog='STCS']/p">
															<fo:inline font-style="italic">specifiche: </fo:inline>
															<xsl:apply-templates select="./phystech[@encodinganalog='STCS']/p/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./scopecontent[@encodinganalog='SGTD']/p and ./@level='fonds' or ./@level='collection' or ./@level='series' or ./@level='subseries'">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Descrizione del soggetto (SGTD)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./scopecontent[@encodinganalog='SGTD']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./odd[@encodinganalog='DSO']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Descrizione dell'oggetto (DSO)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./odd[@encodinganalog='DSO']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./odd[@encodinganalog='NSC']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Notizie storico-critiche (NSC)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./odd[@encodinganalog='NSC']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./scopecontent[@encodinganalog='ISR']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Iscrizioni (ISR)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./scopecontent[@encodinganalog='ISR']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./odd[@encodinganalog='STM']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Stemmi/marchi (STM)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./odd[@encodinganalog='STM']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./odd[@encodinganalog='OSS']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Annotazioni/osservazioni (AN)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./odd[@encodinganalog='OSS']/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./controlaccess/persname/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Chiavi di accesso - persone</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./controlaccess/persname">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./controlaccess/corpname/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Chiavi di accesso - enti</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./controlaccess/corpname">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./controlaccess/geogname/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Chiavi di accesso - luoghi</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./controlaccess/geogname">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./controlaccess/subject/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Chiavi di accesso - temi</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./controlaccess/subject">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:apply-templates select="./text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!-- corretto fino a qui-->
							<xsl:if test="./@level and ./descgrp/accessrestrict !='' or ./descgrp/custodhist  !='' or ./descgrp/userestrict  !='' or ./descgrp/acquinfo  !=''">
								<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">STATUS</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<xsl:if test="./descgrp[@encodinganalog='TU']/accessrestrict/legalstatus">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Condizione giuridica (CDG)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./descgrp[@encodinganalog='TU']/accessrestrict/legalstatus/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./descgrp[@encodinganalog='TU']/acqinfo[@encodinganalog='ACQ']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Acquisizione del materiale (ACQ)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./descgrp[@encodinganalog='TU']/acqinfo[@encodinganalog='ACQ']/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./descgrp[@encodinganalog='TU']/accessrestrict/accessrestrict[@encodinganalog='NVC']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Restrizioni di accesso (NVC)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./descgrp[@encodinganalog='TU']/accessrestrict/accessrestrict[@encodinganalog='NVC']/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./descgrp/custodhist/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Storia della condizione (ALN-ESP)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./descgrp/custodhist/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./descgrp[@encodinganalog='TU']/userestrict/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Copyrigth (CPR)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./descgrp[@encodinganalog='TU']/userestrict/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<xsl:if test="./@level and ./bibliography !=''  or ./relatedmaterial or ./altformavail !='' or ./note/p  !='' ">
								<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">FONTI E DOCUMENTI DI RIFERIMENTO</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<xsl:if test="./relatedmaterial/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Riferimento altre schede (RSE)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:apply-templates select="./relatedmaterial/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./bibliography[@encodinganalog='FNT']/archref">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Fonti archivistiche (FNT)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:apply-templates select="./bibliography[@encodinganalog='FNT']/archref/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./bibliography[@encodinganalog='BIB']/bibref">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Bibliografia (BIB)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:for-each select="./bibliography[@encodinganalog='BIB']/bibref">
															<fo:block>
																<xsl:if test="./title[@encodinganalog='BIBT']/@type !=''">
																	<xsl:apply-templates select="./title[@encodinganalog='BIBT']/@type"/>
																	<fo:inline>:&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./persname[@encodinganalog='BIBA']/text() !=''">
																	<xsl:apply-templates select="./persname[@encodinganalog='BIBA']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./persname[@encodinganalog='BIBC']/text() !=''">
																	<xsl:apply-templates select="./persname[@encodinganalog='BIBC']/text()"/>
																	<fo:inline>&#160;(a cura di),&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./title[@encodinganalog='BIBT']/text() !=''">
																	<xsl:apply-templates select="./title[@encodinganalog='BIBT']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./edition[@encodinganalog='BIBG']/text() !=''">
																	<xsl:apply-templates select="./edition[@encodinganalog='BIBG']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./imprint/geogname[@encodinganalog='BIBL']/text() !=''">
																	<xsl:apply-templates select="./imprint/geogname[@encodinganalog='BIBL']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./imprint/publisher[@encodinganalog='BIBZ']/text() !=''">
																	<xsl:apply-templates select="./imprint/publisher[@encodinganalog='BIBZ']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./imprint/date[@encodinganalog='BIBD']/text() !=''">
																	<xsl:apply-templates select="./imprint/date[@encodinganalog='BIBD']/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./num[@encodinganalog='BIBV']/text() !=''">
																	<xsl:apply-templates select="./num[@encodinganalog='BIBV']/text()"/>
																</xsl:if>
																<xsl:if test="./num[@encodinganalog='BIBP']/text() !=''">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:apply-templates select="./num[@encodinganalog='BIBP']/text()"/>
																</xsl:if>
															</fo:block>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./bibliography[@encodinganalog='BIB']/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Bibliografia (testo libero)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:apply-templates select="./bibliography[@encodinganalog='BIB']/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./bibliography[@encodinganalog='BSE']/extref">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Bibliografia in rete (BSE)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<xsl:for-each select="./bibliography[@encodinganalog='BSE']/extref">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
															<xsl:apply-templates select="./title/text()"/>
															<xsl:if test="./@href !=''">
																<fo:inline>&#160;/&#160;</fo:inline>
																<xsl:apply-templates select="./@href"/>
															</xsl:if>
															<xsl:if test="./date/text() !=''">
																<fo:inline>&#160;(</fo:inline>
																<xsl:apply-templates select="./date/text()"/>
																<fo:inline>)</fo:inline>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./bibliography/list/item">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Mostre (MST)</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:for-each select="./bibliography/list/item">
															<fo:block>
																<xsl:if test="./title/text()!=''">
																	<xsl:apply-templates select="./title/text()"/>
																	<fo:inline>,&#160;</fo:inline>
																</xsl:if>
																<xsl:if test="./geogname/emph/text()!=''">
																	<xsl:apply-templates select="./geogname/emph/text()"/>
																</xsl:if>
																<xsl:if test="./geogname/text()!=''">&#160;(<xsl:apply-templates select="./geogname/text()"/>),&#160;</xsl:if>
																<xsl:if test="./corpname/text()!=''">
																	<xsl:apply-templates select="./corpname/text()"/>
																</xsl:if>
																<xsl:if test="./date/text()!=''">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:apply-templates select="./date/text()"/>
																</xsl:if>
															</fo:block>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<xsl:if test="./@level and ./processinfo  !='' or ./note/p  !=''">
								<fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">NOTE E COMPILAZIONE</fo:block>
								<fo:table table-layout="fixed" width="510pt" space-before="15pt">
									<fo:table-column column-width="170pt"/>
									<fo:table-column column-width="340pt"/>
									<fo:table-body>
										<xsl:if test="./processinfo/note/p">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Note redazionali</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:apply-templates select="./processinfo/note/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./processinfo">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Informazioni sulla compilazione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid darkblue">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
														<xsl:for-each select="./processinfo/list/item">
															<fo:block>
																<xsl:apply-templates select="./text()"/>: 
<xsl:apply-templates select="./persname/text()"/>
&#160;(<xsl:apply-templates select="./date/text()"/>)
</fo:block>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
						</fo:block>
						<fo:block text-align="center" margin-bottom="20pt" margin-top="20pt" color="#C65F1D"/>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="abstract">
		<xsl:apply-templates/>
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
	<xsl:template name="generaLivello">
		<xsl:value-of select="count(ancestor::dsc)*2+1"/>
	</xsl:template>
</xsl:stylesheet>
