<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
	<xsl:strip-space elements="*" />
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes" />
	<xsl:template match="/">
		<xsl:element name="dsc">
			<xsl:element name="rootElement">
				<xsl:for-each select="./rsp/dsc/rootElement/@*">
					<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
				</xsl:for-each>
				<xsl:apply-templates select="./rsp/dsc/rootElement/*" />
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<xsl:template match="@* | text() | *">
		<xsl:copy>
			<xsl:apply-templates select="*[not(self::rootElement[@audience='internal' or not(@audience) and ancestor::rootElement[@audience='internal'] or not(@audience)])] | @* | text()" />
			<!-- <xsl:apply-templates select="* | @* | text()" />-->
		</xsl:copy>
	</xsl:template>
	<!-- 
	<xsl:template match="text()">
		<xsl:analyze-string select="." regex="(\[\[omissis:[^\]]+\]\])">
			<xsl:matching-substring>
				<xsl:if test="regex-group(1)">
					<xsl:text>&#160;[omissis]&#160;</xsl:text>
				</xsl:if>
			</xsl:matching-substring>
			<xsl:non-matching-substring>
				<xsl:analyze-string select="." regex="(\[\[link:[^\]]+:http:[^\]]+\]\])">
					<xsl:matching-substring>
						<xsl:if test="regex-group(1)">
							<xsl:variable name="testoLinkIni" select="substring-after(regex-group(2),'[[link:')" />
							<xsl:variable name="testoLink" select="substring-before($testoLinkIni,':http')" />
							<xsl:variable name="urlLinkIni" select="substring-after(regex-group(2),':http')" />
							<xsl:variable name="urlLink" select="substring-before($urlLinkIni,']]')" />
							<xsl:element name="a">
								<xsl:attribute name="target">_blank</xsl:attribute>
								<xsl:attribute name="href"><xsl:text>http</xsl:text><xsl:value-of select="$urlLink" /></xsl:attribute>
								<xsl:value-of select="$testoLink" />
							</xsl:element>
						</xsl:if>
					</xsl:matching-substring>
					<xsl:non-matching-substring>
						<xsl:value-of select="." />
					</xsl:non-matching-substring>
				</xsl:analyze-string>
			</xsl:non-matching-substring>
		</xsl:analyze-string>
	</xsl:template>-->
</xsl:stylesheet>