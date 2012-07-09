<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:strip-space elements="*"/>
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
									<fo:block line-height="10pt" font-size="8pt" text-align="left">xDams OpenSource - Struttura (full)</fo:block>
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
		<fo:block start-indent="{$spazio}" font-size="10pt" font-weight="normal" color="black" margin-bottom="5mm">
			<xsl:for-each select=".">
				<xsl:call-template name="livello"/>
				<xsl:text>&#160;-&#160;</xsl:text>
				<fo:inline font-weight="bold">
					<xsl:value-of select="./did/unittitle/text()"/>
				</fo:inline>
				<xsl:if test="./did/unittitle/unitdate[text()!=' ']">
					<xsl:text>&#160;(</xsl:text>
					<xsl:value-of select="./did/unittitle/unitdate/text()"/>
					<xsl:text>)</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</fo:block>
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
</xsl:stylesheet>
