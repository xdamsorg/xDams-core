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
         <fo:block line-height="10pt" font-size="9pt" text-align="right">
          Pagina
          <fo:page-number/>
         </fo:block>
        </fo:table-cell>
       </fo:table-row>
      </fo:table-body>
     </fo:table>
    </fo:static-content>
    <fo:flow flow-name="xsl-region-body">
     <xsl:for-each select="//c">
      <fo:block text-align="left" font-size="11pt" space-before="30pt">
       <fo:inline>Scheda&#160;</fo:inline>
       <xsl:value-of select="./@id"/>
      </fo:block>
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