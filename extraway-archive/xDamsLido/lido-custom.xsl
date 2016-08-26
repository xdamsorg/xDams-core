<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" encoding="ISO-8859-1" indent="yes" version="1.0"/>
	<xsl:template match="lido">
		<xsl:if test="./descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue/text() or ./descriptiveMetadata/eventWrap/eventSet/event/eventName/appellationValue/text()">
			<xsl:element name="formaNormalizzata">
				<xsl:if test="./descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term/text()">
					<xsl:value-of select="./descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term/text()"/>
					<xsl:text>&#160;-&#160;</xsl:text>
				</xsl:if>
				<xsl:if test="./descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue/text()">
					<strong>
						<xsl:value-of select="./descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue/text()"/>
					</strong>
				</xsl:if>
				<xsl:if test="./descriptiveMetadata/eventWrap/eventSet/event/eventType/term/text()">
					<xsl:value-of select="./descriptiveMetadata/eventWrap/eventSet/event/eventType/term/text()"/>
					<xsl:text>&#160;-&#160;</xsl:text>
				</xsl:if>
				<strong>
					<xsl:value-of select="./descriptiveMetadata/eventWrap/eventSet/event/eventName/appellationValue/text()"/>
				</strong>
				<xsl:if test="./descriptiveMetadata/eventWrap/eventSet/event/roleInEvent/term/text()">
					<xsl:text>,&#160;</xsl:text>
					<xsl:value-of select="./descriptiveMetadata/eventWrap/eventSet/event/roleInEvent/term/text()"/>
				</xsl:if>
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
