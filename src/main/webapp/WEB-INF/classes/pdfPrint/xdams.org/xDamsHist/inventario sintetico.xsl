<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:strip-space elements="unittitle"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master margin-right="1.5cm" margin-left="1.5cm" margin-bottom="1cm" margin-top="1cm" page-width="21cm" page-height="29.7cm" master-name="simpleA4">
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
									<fo:block line-height="10pt" font-size="8pt" text-align="left">xDams OpenSource - Inventario sintetico (tutta la struttura)</fo:block>
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
						<xsl:for-each select="//c">
							<xsl:call-template name="scriviDocumento"/>
						</xsl:for-each>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template name="generaLivello">
		<xsl:value-of select="count(ancestor::dsc)*5+2"/>
	</xsl:template>
	<xsl:template name="scriviDocumento">
		<xsl:variable name="spazio">
			<xsl:call-template name="generaLivello"/>mm</xsl:variable>
		<fo:block start-indent="{$spazio}" font-size="12pt" font-weight="normal" color="black" margin-bottom="5mm">
			<xsl:call-template name="livello"/>
			<xsl:text>&#160;</xsl:text>
			<xsl:value-of select="./did/unitid/text()"/>
			<xsl:text>&#160;-&#160;</xsl:text>
			<fo:inline font-weight="bold">
				<xsl:value-of select="./did/unittitle/text()"/>
			</fo:inline>
			<xsl:if test="./did/unittitle/unitdate/text()">
				<xsl:text>&#160;(</xsl:text>
				<xsl:value-of select="./did/unittitle/unitdate/text()"/>
				<xsl:text>)</xsl:text>
			</xsl:if>
		</fo:block>
		<xsl:if test="./did/physdesc">
			<fo:block start-indent="{$spazio}" font-size="10pt" font-weight="normal" color="black" margin-bottom="5mm">
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
						<xsl:value-of select="./genreform/text()"/>
						<xsl:text>&#160;</xsl:text>
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
		</xsl:if>
		<xsl:if test="./descgrp/scopecontent/p">
			<fo:block start-indent="{$spazio}" font-size="10pt" font-weight="normal" color="black" margin-bottom="5mm" linefeed-treatment="preserve" white-space="pre" wrap-option="wrap">
				<fo:inline>Ambiti e contenuto</fo:inline>
				<fo:block/>
				<xsl:apply-templates select="./descgrp/scopecontent/p"/>
			</fo:block>
		</xsl:if>
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
			<fo:inline>unit� archivistica</fo:inline>
		</xsl:if>
		<xsl:if test="./@level='item'">
			<fo:inline>unit� documentaria</fo:inline>
		</xsl:if>
		<xsl:if test="./@otherlevel='subfile'">
			<fo:inline>sottounit� archivistica</fo:inline>
		</xsl:if>
		<xsl:if test="./@otherlevel='container'">
			<fo:inline>unit� di conservazione</fo:inline>
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
