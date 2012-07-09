<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:strip-space elements="unittitle"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="1cm" margin-left="0.5cm" margin-bottom="1cm" margin-top="1cm" page-width="29.7cm" page-height="21cm" master-name="simpleA4">
					<fo:region-body margin-top="1.5cm" margin-bottom="1.5cm"/>
					<fo:region-before extent="1cm"/>
					<fo:region-after extent="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:static-content flow-name="xsl-region-after">
					<fo:table table-layout="fixed" width="28cm">
						<fo:table-column column-width="12cm"/>
						<fo:table-column column-width="16cm"/>
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
									<fo:block line-height="10pt" font-size="8pt" text-align="left">xDams OpenSource: Elenco per segnature</fo:block>
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
						<fo:table table-layout="fixed" width="28cm" space-before="15pt">
							<fo:table-column column-width="2.2cm"/>
							<fo:table-column column-width="2.2cm"/>
							<fo:table-column column-width="3cm"/>
							<fo:table-column column-width="5cm"/>
							<fo:table-column column-width="6.4cm"/>
							<fo:table-column column-width="5cm"/>
							<fo:table-column column-width="4.4cm"/>
							<fo:table-header>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center" margin-bottom="10pt">segn. antica</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">segn. prec.</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">posizione attuale</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">livello e codice</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">titolo e date</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">ambiti e contenuto</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block font-size="8pt" font-weight="bold" text-align="center">consistenza</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>
							<xsl:for-each select="//c">
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<xsl:value-of select="./descgrp/odd[@type='segnatura antica o originaria']/p/text()"/>
												<xsl:if test="./descgrp/odd[@type='segnatura antica o originaria']/note/p/text()">
													<fo:block/>
													<xsl:value-of select="./descgrp/odd[@type='segnatura antica o originaria']/note/p/text()"/>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<xsl:value-of select="./descgrp/odd[@type='segnatura precedente']/p/text()"/>
												<xsl:if test="./descgrp/odd[@type='segnatura precedente']/note/p/text()">
													<fo:block/>
													<xsl:value-of select="./descgrp/odd[@type='segnatura precedente']/note/p/text()"/>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
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
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<xsl:call-template name="livello"/>
												<fo:block/>
												<xsl:value-of select="./did/unitid/text()"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<fo:inline font-weight="bold">
													<xsl:value-of select="./did/unittitle/text()"/>
												</fo:inline>
												<xsl:if test="./did/unittitle/unitdate/text()">
													<fo:inline>&#160;(</fo:inline>
													<xsl:value-of select="./did/unittitle/unitdate/text()"/>
													<fo:inline>)</fo:inline>
												</xsl:if>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange" border-right="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
												<xsl:apply-templates select="./descgrp/scopecontent/p"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-bottom="0.5pt solid orange">
											<fo:block font-size="9pt" font-weight="normal" color="black" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
												<fo:block-container width="4.4cm" overflow="hidden">
													<fo:block>
														<xsl:if test="./did/physdesc[@label='container']">
															<xsl:value-of select="./did/physdesc[@label='container']/genreform/text()"/>
															<xsl:if test="./did/physdesc[@label='container']/physfacet[@type='medium']/text()">
																<fo:inline>&#160;</fo:inline>
																<xsl:value-of select="./did/physdesc[@label='container']/physfacet[@type='medium']/text()"/>
															</xsl:if>
														</xsl:if>
														<xsl:if test="./did/physdesc[@label='content' and not(@null)]//*">
															<xsl:if test="./did/physdesc[@label='container']/* and ./did/physdesc[@label='content']/extent/text()">
																<fo:inline>&#160;di&#160;</fo:inline>
															</xsl:if>
															<xsl:for-each select="./did/physdesc[@label='content']">
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
															</xsl:for-each>
														</xsl:if>
													</fo:block>
												</fo:block-container>
											</fo:block>
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
	<xsl:template name="livelloHeader">
		<xsl:if test="./dsc/c/@level='fonds'">
			<fo:inline>fondo</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='subfonds'">
			<fo:inline>subfondo</fo:inline>
		</xsl:if>
		<xsl:if test="./dsc/c/@level='collection'">
			<fo:inline>collezione, raccolta</fo:inline>
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
