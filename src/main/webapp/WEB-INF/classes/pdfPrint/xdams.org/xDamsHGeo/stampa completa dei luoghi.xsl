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
									<fo:block line-height="10pt" font-size="9pt" text-align="left">xDams OpenSource - stampa completa degli authority record luoghi</fo:block>
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
					<xsl:for-each select="//eac">
						<fo:block text-align="left" font-size="11pt" space-before="30pt">
							<fo:inline>Scheda&#160;</fo:inline>
							<xsl:value-of select="./eacheader/eacid/text()"/>
						</fo:block>
						<fo:block>
							<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">IDENTIFICAZIONE</fo:block>
							<fo:table table-layout="fixed" width="18cm">
								<fo:table-column column-width="6cm"/>
								<fo:table-column column-width="12cm"/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
											<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">forma/e autorizzata/e</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid gold">
											<xsl:for-each select="./condesc/identity/conhead[@type='authorized']">
												<fo:block start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
													<fo:inline font-size="10pt">
														<xsl:value-of select="./@normal"/>
													</fo:inline>
												</fo:block>
											</xsl:for-each>
										</fo:table-cell>
									</fo:table-row>
									<!--::::::::::::forme varianti:::::::::::::-->
									<xsl:if test="./condesc/identity/conhead[@type='parallel']/*">
										<fo:table-row>
											<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
												<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">forma/e variante/i</fo:block>
											</fo:table-cell>
											<fo:table-cell border-bottom="0.5pt solid gold">
												<xsl:for-each select="./condesc/identity/conhead[@type='parallel']">
													<fo:block start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<fo:inline font-size="10pt">
															<xsl:value-of select="./@normal"/>
														</fo:inline>
													</fo:block>
												</xsl:for-each>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
								</fo:table-body>
							</fo:table>
							<!--         DESCRIZIONE            -->
							<xsl:if test="./condesc/desc/*">
								<fo:block font-size="9pt" font-weight="bold" margin-top="20pt" text-align="center">DESCRIZIONE</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<!--::::::::::::informazioni integrative:::::::::::::-->
										<xsl:if test="./condesc/desc/ocd/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">informazioni integrative</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="8pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
														<xsl:value-of select="./condesc/desc/ocd/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</xsl:if>
							<!--         RELAZIONI            -->
							<xsl:if test="./condesc/eacrels/*">
								<fo:block font-size="10pt" font-weight="bold" margin-top="20pt" text-align="center">RELAZIONI</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<xsl:if test="./condesc/eacrels/eacrel[@reltype='superior']/* or ./condesc/eacrels/eacrel[@reltype='subordinate']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni gerarchiche</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:for-each select="./condesc/eacrels/eacrel[@reltype='superior']/place">
															<fo:block>
																<fo:inline text-decoration="underline">superiore</fo:inline>
																<fo:inline>: &#160;</fo:inline>
																<xsl:value-of select="./text()"/>
																<xsl:if test="../descnote/text()">
																	<fo:inline>;&#160;<xsl:value-of select="./descnote/text()"/>
																	</fo:inline>
																</xsl:if>
															</fo:block>
														</xsl:for-each>
														<xsl:for-each select="./condesc/eacrels/eacrel[@reltype='subordinate']/place">
															<fo:block>
																<fo:inline text-decoration="underline">inferiore</fo:inline>
																<fo:inline>: &#160;</fo:inline>
																<xsl:value-of select="./text()"/>
																<xsl:if test="../descnote/text()">
																	<fo:inline>;&#160;<xsl:value-of select="./descnote/text()"/>
																	</fo:inline>
																</xsl:if>
															</fo:block>
														</xsl:for-each>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./condesc/eacrels/eacrel[@reltype='earlier']/* or ./condesc/eacrels/eacrel[@reltype='later']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni cronologiche</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<xsl:for-each select="./condesc/eacrels/eacrel[@reltype='earlier']/place">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<fo:inline text-decoration="underline">predecessore</fo:inline>
															<fo:inline>: &#160;</fo:inline>
															<xsl:value-of select="./text()"/>
															<xsl:if test="../descnote/text()">
																<fo:inline>;&#160;<xsl:value-of select="./descnote/text()"/>
																</fo:inline>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
													<xsl:for-each select="./condesc/eacrels/eacrel[@reltype='later']/place">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<fo:inline text-decoration="underline">successore</fo:inline>
															<fo:inline>: &#160;</fo:inline>
															<xsl:value-of select="./text()"/>
															<xsl:if test="../descnote/text()">
																<fo:inline>;&#160;<xsl:value-of select="./descnote/text()"/>
																</fo:inline>
															</xsl:if>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./condesc/eacrels/eacrel[@reltype='associative']/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">relazioni associative</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<xsl:for-each select="./condesc/eacrels/eacrel[@reltype='associative']/place">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:value-of select="./text()"/>
															<xsl:if test="../descnote/text()">
																<fo:inline>;&#160;<xsl:value-of select="./descnote/text()"/>
																</fo:inline>
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
							<xsl:if test="./eacheader/*">
								<fo:block font-size="10pt" font-weight="bold" margin-top="20pt" text-align="center">CONTROLLO</fo:block>
								<fo:table table-layout="fixed" width="18cm">
									<fo:table-column column-width="6cm"/>
									<fo:table-column column-width="12cm"/>
									<fo:table-body>
										<xsl:if test="./eacheader/@status">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">stato</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./eacheader/@status"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./eacheader/@detaillevel">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">livello di dettaglio</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./eacheader/@detaillevel"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./eacheader/languagedecl/language/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">lingua della documentazione</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./eacheader/languagedecl/language/text()"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./eacheader/mainhist/mainevent/* or ./eacheader/mainhistory/mainevent/*">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">revisioni</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<xsl:for-each select="./eacheader/mainhist/mainevent">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:value-of select="./@maintype"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./maindate/text()"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./name/text()"/>
														</fo:block>
													</xsl:for-each>
													<xsl:for-each select="./eacheader/mainhistory/mainevent">
														<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
															<xsl:value-of select="./@maintype"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./maindate/text()"/>
															<fo:inline>,&#160;</fo:inline>
															<xsl:value-of select="./name/text()"/>
														</fo:block>
													</xsl:for-each>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="./eacheader/mainhist/mainevent/maindesc/abbr/text()">
											<fo:table-row>
												<fo:table-cell border-bottom="0.5pt solid gold" border-right="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">archivio d'origine</fo:block>
												</fo:table-cell>
												<fo:table-cell border-bottom="0.5pt solid gold">
													<fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
														<xsl:value-of select="./eacheader/mainhist/mainevent/maindesc/abbr/text()"/>
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
