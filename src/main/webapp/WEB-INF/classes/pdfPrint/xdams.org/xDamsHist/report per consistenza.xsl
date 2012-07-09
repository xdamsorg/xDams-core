<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:strip-space elements="unittitle"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="1cm" margin-left="0.5cm" margin-bottom="1cm" margin-top="1cm" page-width="21cm" page-height="29.7cm" master-name="simpleA4">
					<fo:region-body margin-top="1.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed" width="18cm">
						<fo:table-column column-width="9cm"/>
						<fo:table-column column-width="9cm"/>
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
									<fo:block line-height="10pt" font-size="8pt" text-align="left">xDams OpenSource: Elenco di consistenza</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block line-height="10pt" font-size="8pt" text-align="right"> Pagina <fo:page-number/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<fo:table table-layout="fixed" width="18cm" space-before="15pt">
							<fo:table-column column-width="1cm"/>
							<fo:table-column column-width="3.5cm"/>
							<fo:table-column column-width="2cm"/>
							<fo:table-column column-width="4cm"/>
							<fo:table-column column-width="4cm"/>
							<fo:table-column column-width="3.5cm"/>
							<fo:table-header>
								<fo:table-row>
									<fo:table-cell>
										<fo:block/>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">codice</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">livello</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">titolo</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">date</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">consistenza</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>
							<xsl:for-each select="//c">
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block/>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" text-align="end" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<xsl:value-of select="./@id"/>
											</fo:block>
											<fo:block/>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" text-align="center">
												<xsl:call-template name="livello"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<fo:inline font-weight="bold">
													<xsl:value-of select="./did/unittitle/text()"/>
												</fo:inline>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" text-align="center">
												<xsl:value-of select="./did/unittitle/unitdate/text()"/>
											</fo:block>
											<fo:block/>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange">
											<fo:block font-size="9pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" keep-together="always">
												<fo:block-container width="3.5cm" overflow="hidden">
													<fo:block>
														<xsl:if test="./did/physdesc[@label='container']">
															<xsl:value-of select="./did/physdesc[@label='container']/genreform/text()"/>
															<xsl:if test="./did/physdesc[@label='container']/physfacet[@type='medium']">
																<fo:inline>&#160;</fo:inline>
																<xsl:value-of select="./did/physdesc[@label='container']/physfacet[@type='medium']/text()"/>
															</xsl:if>
														</xsl:if>
														<xsl:if test="./did/physdesc[not(@label) or @label='content']/*">
															<xsl:if test="./did/physdesc[@label='container']/* and ./did/physdesc[not(@label) or @label='content']/extent/text()">
																<fo:inline>&#160;di&#160;</fo:inline>
															</xsl:if>
															<xsl:for-each select="./did/physdesc[not(@label) or @label='content']">
																<fo:block>
																	<xsl:value-of select="./genreform/text()"/>
																	<fo:inline>&#160;</fo:inline>
																	<xsl:value-of select="./extent/text()"/>
																<xsl:if test="./dimensions">
																	<fo:block>
																		<xsl:value-of select="./dimensions/dimensions[@type='altezza']/text()"/>
																		<fo:inline>&#160;x&#160;</fo:inline>
																		<xsl:value-of select="./dimensions/dimensions[@type='base']/text()"/>
																	</fo:block>
																</xsl:if>
																<xsl:if test="./physfacet[@type='cromatismo']">
																	<fo:block>colore:&#160;<xsl:value-of select="./physfacet[@type='cromatismo']/text()"/>
																	</fo:block>
																</xsl:if>
																<xsl:if test="./physfacet[@type='scala']">
																	<fo:block>
																		<xsl:value-of select="./physfacet[@type='scala']/text()"/>
																	</fo:block>
																</xsl:if>
																<xsl:if test="./physfacet[@type='note']">
																	<fo:block>integrazioni:&#160;<xsl:value-of select="./physfacet[@type='note']/text()"/>
																	</fo:block>
																</xsl:if>
																<xsl:if test="position()!=last()">
																	<fo:block/>
																</xsl:if>
																</fo:block>
															</xsl:for-each>
														</xsl:if>
													</fo:block>
												</fo:block-container>
											</fo:block>
											<fo:block/>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</xsl:for-each>
						</fo:table>
					</fo:block>
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
		<xsl:if test="./@level='recordgrp'">
			<fo:inline>sezione</fo:inline>
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
		<xsl:if test="./@otherlevel='container'">
			<fo:inline>unità di conservazione</fo:inline>
		</xsl:if>
		<xsl:if test="./@otherlevel='subfile'">
			<fo:inline>sottounità archivistica</fo:inline>
		</xsl:if>
	</xsl:template>
	<xsl:template name="livelloHeader">
		<xsl:if test="./dsc/c/@level='fonds'">
			<fo:inline>fondo</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='subfonds'">
			<fo:inline>subfondo</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='recordgrp'">
			<fo:inline>sezione</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='series'">
			<fo:inline>serie</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='subseries'">
			<fo:inline>sottoserie</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='file'">
			<fo:inline>unità archivistica</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='item'">
			<fo:inline>unità documentaria</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@otherlevel='container'">
			<fo:inline>unità di conservazione</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@otherlevel='subfile'">
			<fo:inline>sottounità archivistica</fo:inline>
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
