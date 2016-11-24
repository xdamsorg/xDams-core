<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
 <xsl:template match="/">
  <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
   <fo:layout-master-set>
    <fo:simple-page-master margin-right="42.5pt" margin-left="42.5pt" margin-bottom="28pt" margin-top="28pt" page-width="595pt" page-height="840pt" master-name="simpleA4">
     <fo:region-body margin-top="28pt" margin-bottom="42.5pt"/>
     <fo:region-before extent="28pt"/>
     <fo:region-after extent="42.5pt"/>
    </fo:simple-page-master>
   </fo:layout-master-set>
   <fo:page-sequence master-reference="simpleA4">
    <fo:static-content flow-name="xsl-region-after">
     <fo:table table-layout="fixed" width="510pt">
      <fo:table-column column-width="340pt"/>
      <fo:table-column column-width="170pt"/>
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
         <fo:block line-height="10pt" font-size="9pt" text-align="left">xDams OS - Biblioteca: Stampa di tutte le informazioni</fo:block>
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
     <xsl:for-each select="//mods">
      <fo:block text-align="left" font-size="11pt" font-weight="bold">
       <xsl:call-template name="livello"/>
       <fo:inline>&#160;</fo:inline>
       <xsl:value-of select="./@ID"/>
       <fo:inline>&#160;-&#160;</fo:inline>
       <xsl:value-of select="./titleInfo[@type='uniform']/title/text()"/>
      </fo:block>
      <!-- IDENTIFICAZIONE -->
      <fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">IDENTIFICAZIONE</fo:block>
      <fo:table table-layout="fixed" width="510pt" space-before="8pt">
       <fo:table-column column-width="130pt"/>
       <fo:table-column column-width="370pt"/>
       <fo:table-body>
        <!--::::::::::::livello::::::::::::: -->
        <xsl:if test="./extension/level/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">livello</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:call-template name="livello"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::tipo di risorsa/supporto::::::::::::: -->
        <xsl:if test="./typeOfResource/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">tipo di risorsa/supporto</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:call-template name="tipoSupporto"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::emissione/livello bibliografico::::::::::::: -->
        <xsl:if test="./originInfo/issuance/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">emissione/livello bibliografico</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:call-template name="livelloBibliografico"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::accesso::::::::::::: -->
        <xsl:if test="./accessCondition/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">accesso</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:call-template name="audience"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::segnatura::::::::::::: -->
        <xsl:if test="./identifier[@type='referenceCode']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">segnatura</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./identifier[@type='referenceCode']/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::denominazione/titolo::::::::::::: -->
        <fo:table-row>
         <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
          <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">titolo</fo:block>
         </fo:table-cell>
         <fo:table-cell border-bottom="0.5pt solid indianred">
          <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
           <xsl:value-of select="./titleInfo[@type='uniform']/title/text()"/>
           <xsl:if test="./titleInfo[@type='uniform']/subtitle/text()">
            <fo:inline>.&#160;</fo:inline>
            <xsl:value-of select="./titleInfo[@type='uniform']/subtitle/text()"/>
           </xsl:if>
           <xsl:if test="./titleInfo[@type='translated']/title/text()">
            <fo:inline>.&#160;</fo:inline>
            <xsl:value-of select="./titleInfo[@type='translated']/title/text()"/>
           </xsl:if>
          </fo:block>
         </fo:table-cell>
        </fo:table-row>
        <!--::::::::::::formulazione di responsabilità::::::::::::: -->
        <xsl:if test="./note[@type='statement of responsibility']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">formulazione di responsabilità</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./note[@type='statement of responsibility']">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::prima formulazione di responsabilità::::::::::::: -->
        <xsl:if test="./note[@type='first statement of responsibility']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">prima formulazione di responsabilità</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./note[@type='first statement of responsibility']">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::successiva formulazione di responsabilità::::::::::::: -->
        <xsl:if test="./note[@type='following statement of responsibility']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">successiva formulazione di responsabilità</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./note[@type='following statement of responsibility']">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::fa parte di::::::::::::: -->
        <xsl:if test="./relatedItem[@type='series']/titleInfo/title/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">fa parte di</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./relatedItem[@type='series']/titleInfo/title/text()">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::contenuto in::::::::::::: -->
        <xsl:if test="./relatedItem[@type='references']/titleInfo/title/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">contenuto in</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <!--xsl:choose> <xsl:when test="./relatedItem[@type='references']/titleInfo/title[starts-with(text(),'in: ')]"> <xsl:value-of select="substring-after(./relatedItem[@type='references']/titleInfo/title/text(),'in: ')"/> </xsl:when> <xsl:otherwise> <xsl:value-of select="./relatedItem[@type='references']/titleInfo/title/text()"/> </xsl:otherwise> </xsl:choose -->
            <xsl:if test="./relatedItem[@type='references']/titleInfo/title/text()">
             <xsl:value-of select="./relatedItem[@type='references']/titleInfo/title/text()"/>
            </xsl:if>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::descrizione fisica::::::::::::: -->
        <xsl:if test="./extension/level/text()!='item' and ./physicalDescription/form/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">descrizione fisica</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./physicalDescription/form/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <xsl:if test="./extension/level/text()='item' and (./physicalDescription/form[@type='material']/text() or ./physicalDescription/note/text() or ./physicalDescription/extent/text() or ./physicalDescription/form[@type='attachments']/text())">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">descrizione fisica</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./physicalDescription">
             <xsl:if test="form[@type='material']/text()">
              <fo:block>
               <fo:inline font-style="italic">indicazione specifica del materiale ed estensione del documento:</fo:inline>
               <xsl:value-of select="form[@type='material']/text()"/>
              </fo:block>
             </xsl:if>
             <xsl:if test="note/text()">
              <fo:block>
               <fo:inline font-style="italic">altre particolarità fisiche:</fo:inline>
               <xsl:value-of select="note/text()"/>
              </fo:block>
             </xsl:if>
             <xsl:if test="extent/text()">
              <fo:block>
               <fo:inline font-style="italic">dimensioni:</fo:inline>
               <xsl:value-of select="extent/text()"/>
              </fo:block>
             </xsl:if>
             <xsl:if test="form[@type='attachments']/text()">
              <fo:block>
               <fo:inline font-style="italic">materiale allegato:</fo:inline>
               <xsl:value-of select="form[@type='attachments']/text()"/>
              </fo:block>
             </xsl:if>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::descrizione::::::::::::: -->
        <xsl:if test="./genre/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">descrizione</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./genre/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::abstract::::::::::::: -->
        <xsl:if test="./abstract/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">abstract</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./abstract/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::lingua::::::::::::: -->
        <xsl:if test="./language/languageTerm/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">lingua</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./language/languageTerm/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::editore::::::::::::: -->
        <xsl:if test="./originInfo/publisher/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">editore</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./originInfo/publisher/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::luogo::::::::::::: -->
        <xsl:if test="./originInfo/place/placeTerm/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">luogo</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./originInfo/place/placeTerm/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::data::::::::::::: -->
        <xsl:if test="./originInfo/dateIssued/text() or ./originInfo/dateOther[@type='web publication']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">data/e</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:if test="./originInfo/dateIssued/text()">
             <xsl:value-of select="./originInfo/dateIssued/text()"/>
            </xsl:if>
            <xsl:if test="./originInfo/dateOther[@type='web publication']/text()">
             <fo:block>
              <fo:inline font-style="italic">data di messa in linea:&#160;</fo:inline>
              <xsl:value-of select="./originInfo/dateOther[@type='web publication']/text()"/>
             </fo:block>
            </xsl:if>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::note::::::::::::: -->
        <xsl:if test="./physicalDescription/note/text() or ./note[@type='general notes']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">note</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./physicalDescription/note/text()"/>
            <xsl:value-of select="./note[@type='general notes']/text()"/>
            <fo:block/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::authority/legami::::::::::::: -->
        <xsl:if test="./name/namePart/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">autority/legami</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./name">
             <xsl:value-of select="./namePart/text()"/>
             <xsl:if test="./@type or ./role/roleTerm/text()">
              <fo:inline>&#160;(</fo:inline>
              <xsl:if test="./@type">
               <xsl:value-of select="./@type"/>
               <fo:inline>,&#160;</fo:inline>
              </xsl:if>
              <xsl:if test="./role/roleTerm/text()">
               <xsl:value-of select="./role/roleTerm/text()"/>
              </xsl:if>
              <fo:inline>)</fo:inline>
             </xsl:if>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::soggetto::::::::::::: -->
        <xsl:if test="./subject/topic/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">soggetto</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./subject/topic">
             <xsl:value-of select="./text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::soggetto::::::::::::: -->
        <xsl:if test="./classification[@authority='ddc']/*">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Classificazione Dewey</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./classification[@authority='ddc']">
             <fo:block>
              <fo:inline font-style="italic">edizione:</fo:inline>
              <xsl:value-of select="./@edition"/>
             </fo:block>
             <fo:block>
              <fo:inline font-style="italic">lingua:</fo:inline>
              <xsl:value-of select="./@lang"/>
             </fo:block>
             <fo:block>
              <fo:inline font-style="italic">codice:</fo:inline>
              <xsl:value-of select="./text()"/>
             </fo:block>
             <fo:block>
              <fo:inline font-style="italic">etichetta:</fo:inline>
              <xsl:value-of select="./@displayLabel"/>
             </fo:block>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::localizzazione::::::::::::: -->
        <xsl:if test="./location/physicalLocation/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">localizzazione</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./location/physicalLocation">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::copertura::::::::::::: -->
        <xsl:if test="./subject/geographic/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">copertura</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./subject/geographic">
             <xsl:value-of select="text()"/>
             <xsl:if test="position()!=last()">
              <fo:block margin-top="4pt"/>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::ISBN / ISSN::::::::::::: -->
        <xsl:if test="./identifier[@type='ISBN']/text()">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ISBN / ISSN</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:value-of select="./identifier[@type='ISBN']/text()"/>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
        <!--::::::::::::allegati digitali::::::::::::: -->
        <xsl:if test="./location/@displayLabel or location/url">
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">allegati digitali</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:if test="./location/@displayLabel">
             <fo:block>
              <fo:inline font-style="italic">numero allegati:</fo:inline>
              <xsl:value-of select="./location/@displayLabel"/>
             </fo:block>
            </xsl:if>
            <xsl:for-each select="./location/url">
             <xsl:if test="@access='preview'">
              <fo:block>è stata allegata l'anteprima</fo:block>
             </xsl:if>
             <xsl:if test="@access='raw object'">
              <fo:block>
               <xsl:value-of select="./@note"/>
               <fo:inline>:</fo:inline>
               <xsl:value-of select="./text()"/>
               <xsl:if test="position()!=last()">
                <fo:block margin-top="4pt"/>
               </xsl:if>
              </fo:block>
             </xsl:if>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </xsl:if>
       </fo:table-body>
      </fo:table>
      <!-- INFO REDAZIONALI -->
      <xsl:if test="./recordInfo/item">
       <fo:block font-size="10pt" font-weight="bold" margin-top="15pt" margin-left="170pt">INFO REDAZIONALI</fo:block>
       <fo:table table-layout="fixed" width="510pt" space-before="8pt">
        <fo:table-column column-width="130pt"/>
        <fo:table-column column-width="370pt"/>
        <fo:table-body>
         <fo:table-row>
          <fo:table-cell border-bottom="0.5pt solid indianred" border-right="0.5pt solid indianred">
           <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">info record</fo:block>
          </fo:table-cell>
          <fo:table-cell border-bottom="0.5pt solid indianred">
           <fo:block font-size="10pt" text-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
            <xsl:for-each select="./recordInfo/item">
             <fo:block>
              <xsl:value-of select="./recordOrigin/text()"/>
              <fo:inline>,&#160;</fo:inline>
              <xsl:value-of select="./recordChangeDate/text()"/>
              <fo:inline>&#160;-&#160;</fo:inline>
              <xsl:value-of select="./recordContentSource/text()"/>
             </fo:block>
            </xsl:for-each>
           </fo:block>
          </fo:table-cell>
         </fo:table-row>
        </fo:table-body>
       </fo:table>
      </xsl:if>
      <fo:block margin-bottom="40pt" margin-top="30pt" text-align="center">:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</fo:block>
     </xsl:for-each>
    </fo:flow>
   </fo:page-sequence>
  </fo:root>
 </xsl:template>
 <xsl:template name="livello">
  <xsl:if test="./extension/level/text()='collection'">
   <fo:inline>collezione</fo:inline>
  </xsl:if>
  <xsl:if test="./extension/level/text()='item'">
   <fo:inline>unità bibliografica</fo:inline>
  </xsl:if>
 </xsl:template>
 <xsl:template name="tipoSupporto">
  <xsl:if test="./typeOfResource/text()='a'">
   materiale a stampa
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='b'">
   materiale manoscritto
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='c'">
   partiture musicali a stampa
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='d'">
   partiture musicali manoscritte
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='e'">
   materiale cartografico a stampa
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='f'">
   materiale cartografico manoscritto
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='g'">
   materiali video e proiettato (film, filmine, diapositive, trasparenti, videoregistrazioni)
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='i'">
   registrazioni sonore non musicali
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='j'">
   registrazioni sonore musicali
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='k'">
   grafica bidimensionale (dipinti, disegni etc.)
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='l'">
   risorsa elettronica
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='m'">
   materiale misto
  </xsl:if>
  <xsl:if test="./typeOfResource/text()='r'">
   manufatti tridimensionali o oggetti presenti in natura
  </xsl:if>
 </xsl:template>
 <xsl:template name="livelloBibliografico">
  <xsl:if test="./originInfo/issuance/text()='a'">
   analitico (parte componente)
  </xsl:if>
  <xsl:if test="./originInfo/issuance/text()='i'">
   risorsa integrativa
  </xsl:if>
  <xsl:if test="./originInfo/issuance/text()='m'">
   monografia
  </xsl:if>
  <xsl:if test="./originInfo/issuance/text()='s'">
   risorsa in continuazione
  </xsl:if>
  <xsl:if test="./originInfo/issuance/text()='c'">
   collezione
  </xsl:if>
 </xsl:template>
 <xsl:template name="audience">
  <xsl:if test="./accessCondition/text()='external'">
   pubblico
  </xsl:if>
  <xsl:if test="./accessCondition/text()='internal'">
   privato
  </xsl:if>
  <xsl:if test="./accessCondition/text()='restricted'">
   riservato
  </xsl:if>
 </xsl:template>
</xsl:stylesheet>