<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes" standalone="yes"/>
 <xsl:template match="c">
  <xsl:element name="c">
   <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
   <xsl:attribute name="level"><xsl:value-of select="./@level"/></xsl:attribute>
   <xsl:if test="./did/unittitle/title[@type='proper']/text()">
    <xsl:element name="xDamsElement">
     <xsl:attribute name="presentation">titolo</xsl:attribute>
     <xsl:attribute name="label">proper title</xsl:attribute>
     <xsl:value-of select="./did/unittitle/title[@type='proper']/text()"/>
    </xsl:element>
   </xsl:if>
   <xsl:if test="./did/unittitle/unitdate/text()">
    <xsl:element name="xDamsElement">
     <xsl:attribute name="presentation">data</xsl:attribute>
     <xsl:attribute name="label">unitdate</xsl:attribute>
     <xsl:value-of select="./did/unittitle/unitdate/text()"/>
    </xsl:element>
   </xsl:if>
   <xsl:if test="./did/origination[@label='main credits']/corpname">
    <xsl:for-each select="./did/origination[@label='main credits']/corpname">
     <xsl:element name="xDamsElement">
      <xsl:attribute name="presentation">responsabilità principali</xsl:attribute>
      <xsl:attribute name="label">main credits</xsl:attribute>
      <xsl:attribute name="type">corpname</xsl:attribute>
      <xsl:value-of select="./text()"/>
     </xsl:element>
    </xsl:for-each>
   </xsl:if>
  </xsl:element>
 </xsl:template>
</xsl:stylesheet>