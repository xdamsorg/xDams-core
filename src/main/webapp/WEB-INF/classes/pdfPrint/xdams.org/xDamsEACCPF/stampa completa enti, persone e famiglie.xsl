<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="1.3cm" margin-left="1cm" margin-bottom="1cm" margin-top="1cm" page-width="21cm" page-height="29.7cm" master-name="simpleA4">
					<fo:region-body margin-top="1cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1.5cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed" width="18cm">
						<fo:table-column column-width="12cm"/>
						<fo:table-column column-width="6cm"/>
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
									<fo:block line-height="10pt" font-size="9pt" text-align="left">xDams OpenSource - stampa completa degli authority record enti, persone, famiglie</fo:block>
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
					<xsl:for-each select="//eac-cpf">
						<fo:block text-align="center" font-size="11pt" space-before="30pt">
							<fo:inline>Scheda&#160;</fo:inline>
							<xsl:value-of select="./control/recordId/text()"/>
						</fo:block>
						<fo:block>
							<!--         IDENTIFICAZIONE            -->
							<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">IDENTIFICAZIONE</fo:block>
							<fo:table table-layout="fixed" width="18cm">
								<fo:table-column column-width="6cm"/>
								<fo:table-column column-width="12cm"/>
								<fo:table-body>
									<xsl:if test="./cpfDescription/identity/entityType/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">tipologia</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:value-of select="./cpfDescription/identity/entityType/text()"/>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<xsl:if test="./cpfDescription/description/functions/function/text()">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">funzione (rispetto alla documentazione d'archivio)</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<xsl:for-each select="./cpfDescription/description/functions/function">
														<xsl:value-of select="./text()"/>
														<xsl:if test="position()!=last()">
															<fo:inline>,&#160;</fo:inline>
														</xsl:if>
													</xsl:for-each>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<!--::::::::::::forma autorizzata:::::::::::::-->
									<fo:table-row>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">intestazione/i</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<xsl:for-each select="./cpfDescription/identity/nameEntry[1]">
													<fo:block>
														<fo:inline font-weight="bold">
															<xsl:apply-templates select="./part[@localType='normal']/text()"/>
														</fo:inline>
														<xsl:if test="./useDates/date/text()">
															<fo:inline>,&#160;</fo:inline>
															<fo:inline>
																<xsl:value-of select="./useDates/date/text()"/>
															</fo:inline>
														</xsl:if>
														<xsl:if test="./authorizedForm/text()">
															<fo:block>
																<fo:inline>secondo la norma: </fo:inline>
																<fo:inline font-weight="bold">
																	<xsl:value-of select="./authorizedForm/text()"/>
																</fo:inline>
															</fo:block>
														</xsl:if>
													</fo:block>
												</xsl:for-each>
												<!--::::::::::::altre forme:::::::::::::-->
												<xsl:if test="./cpfDescription/identity/nameEntry[position()!=0]/*">
													<xsl:for-each select="./cpfDescription/identity/nameEntry[position()!=0]">
														<fo:block start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:if test="./part[@localType='normal']/text()">
																<fo:block>
																	<xsl:value-of select="./part[@localType='normal']/text()"/>
																	<xsl:if test="./useDates/date/text()">
																		<fo:inline>,&#160;</fo:inline>
																		<fo:inline>
																			<xsl:value-of select="./useDates/date/text()"/>
																		</fo:inline>
																	</xsl:if>
																	<xsl:if test="./authorizedForm/text()">
																		<fo:block>
																			<fo:inline>secondo la norma: </fo:inline>
																			<fo:inline font-weight="bold">
																				<xsl:value-of select="./authorizedForm/text()"/>
																			</fo:inline>
																		</fo:block>
																	</xsl:if>
																</fo:block>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<!--::::::::::::forme parallele:::::::::::::-->
									<xsl:if test="./cpfDescription/identity/nameEntryParallel/nameEntry/*">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
												<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">forma/e parallela/e</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid orange">
												<xsl:for-each select="./cpfDescription/identity/nameEntryParallel/nameEntry">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./part[@localType='normal']/text()"/>
														<xsl:if test="./useDates/date/text()">
															<fo:inline>,&#160;</fo:inline>
															<fo:inline>
																<xsl:value-of select="./useDates/date/text()"/>
															</fo:inline>
														</xsl:if>
													</fo:block>
												</xsl:for-each>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
								</fo:table-body>
							</fo:table>
							<!--         DESCRIZIONE            -->
							<xsl:if test="./cpfDescription/description/*">
								<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">DESCRIZIONE</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<!--::::::::::::cronologia:::::::::::::-->
										<xsl:if test="./cpfDescription/description/existDates/date/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">date di esistenza</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/existDates/date/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/description/existDates/dateRange/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">date di esistenza</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/existDates/dateRange/fromDate/text()"/>
														<fo:inline>&#160;-&#160;</fo:inline>
														<xsl:value-of select="./cpfDescription/description/existDates/dateRange/toDate/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::storia::::::::::::-->
										<xsl:if test="./cpfDescription/description/biogHist/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">storia</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
														<xsl:apply-templates select="./cpfDescription/description/biogHist/p"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::luoghi::::::::::::-->
										<xsl:if test="./cpfDescription/description/places/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">luogo/hi</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./cpfDescription/description/places/place">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:if test="./placeEntry/text()">
																<fo:block start-indent="5pt">
																	<fo:inline text-decoration="underline">luogo</fo:inline>
																	<fo:inline>: </fo:inline>
																	<fo:inline>
																		<xsl:value-of select="./placeEntry/text()"/>
																	</fo:inline>
																</fo:block>
															</xsl:if>
															<xsl:if test="./descriptiveNote/p/text()">
																<fo:block start-indent="5pt">
																	<fo:inline text-decoration="underline">tipo di relazione</fo:inline>
																	<fo:inline>: </fo:inline>
																	<fo:inline>
																		<xsl:value-of select="./descriptiveNote/p/text()"/>
																	</fo:inline>
																</fo:block>
															</xsl:if>
															<xsl:if test="./date/text()">
																<fo:block start-indent="5pt">
																	<fo:inline text-decoration="underline">data</fo:inline>
																	<fo:inline>: </fo:inline>
																	<fo:inline>
																		<xsl:value-of select="./date/text()"/>
																	</fo:inline>
																</fo:block>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::condizione giuridica:::::::::::::-->
										<xsl:if test="./cpfDescription/description/legalStatus/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">condizione giuridica</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:apply-templates select="./cpfDescription/description/legalStatus/term/text()"/>
														<xsl:if test="./cpfDescription/description/legalStatus/date">
															<fo:inline>&#160;(</fo:inline>
															<xsl:value-of select="./cpfDescription/description/legalStatus/date/text()"/>
															<fo:inline>)</fo:inline>
														</xsl:if>
														<xsl:if test="./cpfDescription/description/legalStatus/descriptiveNote/p">
															<fo:inline>;&#160;</fo:inline>
															<xsl:value-of select="./cpfDescription/description/legalStatus/descriptiveNote/p/text()"/>
														</xsl:if>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::funzioni, occupazione, attività:::::::::::::-->
										<xsl:if test="./cpfDescription/description/functions/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">funzioni, occupazione, attività</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/functions/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::mandato/fonti normative:::::::::::::-->
										<xsl:if test="./cpfDescription/description/mandates/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">mandato/fonti normative</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/mandates/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::struttura ammnistrativa/genealogia:::::::::::::-->
										<xsl:if test="./cpfDescription/description/structureOrGenealogy/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">struttura amministrativa/genealogia</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/structureOrGenealogy/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!--::::::::::::contesto sociale e culturale:::::::::::::-->
										<xsl:if test="./cpfDescription/description/generalContext/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">contesto sociale e culturale</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./cpfDescription/description/generalContext/p/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         RELAZIONI            -->
							<xsl:if test="./cpfDescription/relations/cpfRelation/*">
								<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">RELAZIONI</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-parent']/* or ./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-child']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni gerarchiche</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-parent']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-parent']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (superiore</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-child']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='hierarchical-child']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (inferiore</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-earlier']/* or ./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-later']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni cronologiche</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-earlier']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-earlier']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (predecessore</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-later']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='temporal-later']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (successore</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='belongingTo']/* or ./cpfDescription/relations/cpfRelation[@cpfRelationType='ownedBy']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni di appartenenza</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='belongingTo']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='belongingTo']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (fa parte dell'unità descritta</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
													<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='ownedBy']/*">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='ownedBy']/relationEntry">
																<xsl:apply-templates select="./text()"/>
																<fo:inline> (comprende l'unità descritta</fo:inline>
																<xsl:choose>
																	<xsl:when test="../descriptiveNote/p">
																		<fo:inline>;&#160;</fo:inline>
																		<xsl:value-of select="../descriptiveNote/p/text()"/>
																		<fo:inline>)</fo:inline>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:inline>)</fo:inline>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:if test="./date/text()">
																	<fo:inline>,&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</xsl:if>
																<fo:block/>
															</xsl:for-each>
														</fo:block>
													</xsl:if>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='family']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni familiari</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='family']/relationEntry">
															<xsl:apply-templates select="./text()"/>
															<xsl:if test="./date/text()">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="./date/text()"/>
															</xsl:if>
															<xsl:if test="../descriptiveNote/p">
																<fo:inline>;&#160;</fo:inline>
																<xsl:value-of select="../descriptiveNote/p/text()"/>
															</xsl:if>
															<fo:block/>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/relations/cpfRelation[@cpfRelationType='associative']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni associative</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:for-each select="./cpfDescription/relations/cpfRelation[@cpfRelationType='associative']/relationEntry">
															<xsl:apply-templates select="./text()"/>
															<xsl:if test="./date/text()">
																<fo:inline>,&#160;</fo:inline>
																<xsl:value-of select="./date/text()"/>
															</xsl:if>
															<xsl:if test="../descriptiveNote/p">
																<fo:inline>;&#160;</fo:inline>
																<xsl:value-of select="../descriptiveNote/p/text()"/>
															</xsl:if>
															<fo:block/>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<xsl:if test="./cpfDescription/relations/resourceRelation/*">
								<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">RISORSE COLLEGATE</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<xsl:if test="./cpfDescription/relations/resourceRelation[@resourceRelationType='creatorOf']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">complessi archivistici prodotti</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./cpfDescription/relations/resourceRelation[@resourceRelationType='creatorOf']">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<fo:inline font-weight="bold">
																<xsl:apply-templates select="./relationEntry/text()"/>
															</fo:inline>
															<fo:block>
																<xsl:value-of select="./relationEntry/@localType"/>
															</fo:block>
															<xsl:if test="./descriptiveNote/p/text()">
																<fo:block>
																	<fo:inline>natura della relazione:&#160;</fo:inline>
																	<xsl:value-of select="./descriptiveNote/p/text()"/>
																</fo:block>
															</xsl:if>
															<xsl:if test="./date/text()">
																<fo:block>
																	<fo:inline>data della relazione:&#160;</fo:inline>
																	<xsl:value-of select="./date/text()"/>
																</fo:block>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./cpfDescription/relations/resourceRelation[@resourceRelationType='other']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">altre risorse collegate</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./cpfDescription/relations/resourceRelation[@resourceRelationType='other']">
														<xsl:variable name="linkWeb">
															<xsl:value-of select="./@href"/>
														</xsl:variable>
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<fo:inline font-weight="bold">
																<xsl:apply-templates select="./relationEntry/text()"/>
															</fo:inline>
															<xsl:if test="./relationEntry/@localType">
																<fo:block>
																	<fo:inline>tipologia della risorsa:&#160;</fo:inline>
																	<xsl:value-of select="./relationEntry/@localType"/>
																</fo:block>
															</xsl:if>
															<xsl:if test="$linkWeb">
																<fo:block>
																	<fo:inline>link alla risorsa:&#160;</fo:inline>
																	<fo:basic-link external-destination="{$linkWeb}" show-destination="new" color="blue">
																		<xsl:value-of select="$linkWeb"/>
																	</fo:basic-link>
																</fo:block>
															</xsl:if>
															<xsl:if test="./relationEntry/descriptiveNote/p/text()">
																<fo:block>
																	<fo:inline>natura della relazione:&#160;</fo:inline>
																	<xsl:value-of select="./relationEntry/descriptiveNote/p/text()"/>
																</fo:block>
															</xsl:if>
															<xsl:if test="./relationEntry/date/text()">
																<fo:block>
																	<fo:inline>data della relazione:&#160;</fo:inline>
																	<xsl:value-of select="./relationEntry/date/text()"/>
																</fo:block>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         CONTROLLO            -->
							<xsl:if test="./control/*">
								<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">CONTROLLO</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<xsl:if test="./control/maintenanceStatus">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">stato</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/maintenanceStatus/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/localControl[@localType='detailLevel']">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">livello di dettaglio</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/localControl[@localType='detailLevel']/term/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/languageDeclaration/language/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">lingua della documentazione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/languageDeclaration/language/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/sources/source/sourceEntry/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">fonti</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/sources/source/sourceEntry/text()" disable-output-escaping="yes"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/maintenanceAgency/descriptiveNote/p/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">informazioni redazionali</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/maintenanceAgency/descriptiveNote/p/text()" disable-output-escaping="yes"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/maintenanceHistory/maintenanceEvent/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">revisioni</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<xsl:for-each select="./control/maintenanceHistory/maintenanceEvent">
														<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:value-of select="./eventType/text()"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./eventDateTime/text()"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./agent/text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./control/maintenanceHistory/maintenanceEvent/eventDescription/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">archivio d'origine</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid orange">
													<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./control/maintenanceHistory/maintenanceEvent/eventDescription/text()"/>
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
