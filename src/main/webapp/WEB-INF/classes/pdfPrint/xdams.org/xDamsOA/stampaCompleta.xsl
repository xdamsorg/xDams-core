<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
 <xsl:strip-space elements="*"/>
 <xsl:template match="/">
  <fo:root>
   <fo:layout-master-set>
    <fo:simple-page-master margin-right="36pt" margin-left="28pt" margin-bottom="28pt" margin-top="28pt" page-width="595pt" page-height="840pt" master-name="simpleA4">
     <fo:region-body margin-top="28pt" margin-bottom="42pt"/>
     <fo:region-before extent="1cm"/>
     <fo:region-after extent="28pt"/>
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
        <fo:table-cell>
         <fo:block line-height="10pt" font-size="9pt" text-align="left">xDams Open Source: stampa completa OA/OAC</fo:block>
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
      </fo:table-body>
     </fo:table>
     <xsl:for-each select="//c">
      <xsl:variable name="spazio">
       <xsl:call-template name="generaLivello"/>
       mm
      </xsl:variable>
      <fo:block start-indent="{$spazio}" text-align="center" font-size="12pt" font-weight="bold" space-before="30pt" color="#C65F1D">
       <fo:inline>Scheda</fo:inline>
       <fo:inline>
        &#160;
        <xsl:apply-templates select="./@id"/>
       </fo:inline>
      </fo:block>
      <fo:block start-indent="{$spazio}">
       <!-- IDENTIFICAZIONE -->
       <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">IDENTIFICAZIONE</fo:block>
       <fo:table table-layout="fixed" width="510pt" space-before="15pt">
        <fo:table-column column-width="170pt"/>
        <fo:table-column column-width="340pt"/>
        <fo:table-body>
         <xsl:if test="./did/unitid/@type">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">TSK - tipo scheda</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/unitid/@type"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./@level">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">livello di descrizione</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:if test="./@level='fonds'">
              fondo
             </xsl:if>
             <xsl:if test="./@level='collection'">
              collezione
             </xsl:if>
             <xsl:if test="./@level='recordgrp'">
              raccolta
             </xsl:if>
             <xsl:if test="./@level='file'">
              opera complessa/seriale
             </xsl:if>
             <xsl:if test="./@level='item'">
              opera singola/isolata
             </xsl:if>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/unitid/text()">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">codice identificativo gerarchia verticale</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/unitid/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./@audience">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">visibilità della scheda</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:if test="./@audience='external'">
              pubblica
             </xsl:if>
             <xsl:if test="./@audience='internal'">
              privata
             </xsl:if>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./originalsloc/p/num[@encodinganalog='INVN']/text() !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D" border-top="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">INVN - inventario</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-top="0.5pt solid darkblue">
            <xsl:for-each select="./originalsloc/p">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./num[@encodinganalog='INVN']/text()"/>
              <xsl:if test="./date/text() !=''">
               (
               <xsl:apply-templates select="./date/text()"/>
               )
              </xsl:if>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/materialspec[@encodinganalog='OGTD']/text() !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">OGTD - definizione</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/materialspec[@encodinganalog='OGTD']/materialspec[@encodinganalog='OGTT'] !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">OGTT - tipologia</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/materialspec[@encodinganalog='OGTT']/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/materialspec[@encodinganalog='OGTD']/title[@encodinganalog='OGTN'] !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">denominazione</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/title[@encodinganalog='OGTN']/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/materialspec[@encodinganalog='OGTD']/num[@encodinganalog='QNTN'] !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">QNTN - quantità</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/materialspec[@encodinganalog='OGTD']/num[@encodinganalog='QNTN']/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/unittitle[@encodinganalog='SGTI']/text() !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">SGTI - identificazione</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/unittitle[@encodinganalog='SGTI']/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/unittitle[@encodinganalog='SGTI']/title/text() !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">SGTT - titolo</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/unittitle[@encodinganalog='SGTI']/title/text()"/>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/unitdate[@encodinganalog='DT'] !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">DT - cronologia</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/unitdate[@encodinganalog='DT']/text()"/>
             <xsl:if test="./did/unitdate[@encodinganalog='DT']/emph[@altrender='motivazione']">
              <fo:block/>
              <xsl:for-each select="./did/unitdate[@encodinganalog='DT']/emph[@altrender='motivazione']">
               <fo:block/>
               <fo:inline font-style="italic">motivazione:</fo:inline>
               <xsl:apply-templates select="./text()"/>
              </xsl:for-each>
             </xsl:if>
             <xsl:if test="./did/unitdate[@encodinganalog='DT']/emph[@altrender='note']">
              <fo:block/>
              <xsl:for-each select="./did/unitdate[@encodinganalog='DT']/emph[@altrender='note']">
               <fo:block/>
               <fo:inline font-style="italic">note:</fo:inline>
               <xsl:apply-templates select="./text()"/>
              </xsl:for-each>
             </xsl:if>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/origination/corpname[@role='creator']/text()">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (ente)</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <xsl:for-each select="./did/origination/corpname[@role='creator']">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/origination/persname[@role='creator']/text()">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (persona)</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <xsl:for-each select="./did/origination/persname[@role='creator']">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/origination/famname[@role='creator']/text()">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto produttore (famiglia)</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <xsl:for-each select="./did/origination/famname[@role='creator']">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/repository/text() !=''">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Soggetto conservatore</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./did/repository/text()"/>
             <xsl:if test="./did/repository/address/addressline/text()">
              &#160;-&#160;
              <xsl:apply-templates select="./did/repository/address/addressline[1]/text()"/>
              &#160;-&#160;
              <xsl:apply-templates select="./did/repository/address/addressline[2]/text()"/>
             </xsl:if>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/origination/persname[@encodinganalog='AUT']">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">AUT - autore/i</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <xsl:for-each select="./did/origination/persname[@encodinganalog='AUT']">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
              <fo:block/>
              <xsl:if test="./@altrender">
               <fo:inline font-style="italic">rif. all'autore:</fo:inline>
               <xsl:apply-templates select="./@altrender"/>
               <fo:block/>
              </xsl:if>
              <fo:block/>
              <xsl:if test="./@role">
               <fo:inline font-style="italic">ruolo:</fo:inline>
               <xsl:apply-templates select="./@role"/>
               <fo:block/>
              </xsl:if>
              <xsl:if test="./emph/text()">
               <fo:inline font-style="italic">motivazione dell'attribuzione:</fo:inline>
               <xsl:apply-templates select="./emph/text()"/>
              </xsl:if>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/origination/persname[@encodinganalog='AAU']">
          <xsl:for-each select="./did/origination/persname[@encodinganalog='AAU']">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">AAU - altri autori</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
              <fo:block/>
              <fo:block/>
              <xsl:if test="./@role">
               <fo:inline font-style="italic">ruolo:</fo:inline>
               <xsl:apply-templates select="./@role"/>
               <fo:block/>
              </xsl:if>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:for-each>
         </xsl:if>
         <xsl:if test="./did/origination/corpname[@encodinganalog='ATB']">
          <xsl:for-each select="./did/origination/corpname[@encodinganalog='ATB']">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ATB - ambito culturale / di produzione</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
              <fo:block/>
              <xsl:if test="./@role">
               <fo:inline font-style="italic">ruolo:</fo:inline>
               <xsl:apply-templates select="./@role"/>
               <fo:block/>
              </xsl:if>
              <xsl:if test="./emph/text()">
               <fo:inline font-style="italic">motivazione dell'attribuzione:</fo:inline>
               <xsl:apply-templates select="./emph/text()"/>
              </xsl:if>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:for-each>
         </xsl:if>
         <xsl:if test="./bioghist/chronlist/chronitem/event/name">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">CMM - committenza</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
             <xsl:apply-templates select="./bioghist/chronlist/chronitem/event/name/text()"/>
             <fo:block/>
             <xsl:if test="./bioghist/chronlist/chronitem/date/text()">
              <fo:inline font-style="italic">data:</fo:inline>
              <xsl:apply-templates select="./bioghist/chronlist/chronitem/date/text()"/>
             </xsl:if>
             <fo:block/>
             <xsl:if test="./bioghist/chronlist/chronitem/event/text()">
              <fo:inline font-style="italic">circostanza:</fo:inline>
              <xsl:apply-templates select="./bioghist/chronlist/chronitem/event/text()"/>
             </xsl:if>
            </fo:block>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
         <xsl:if test="./did/physloc[@encodinganalog='LDC']">
          <fo:table-row>
           <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
            <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">LDC - Localizzazione specifica</fo:block>
           </fo:table-cell>
           <fo:table-cell border-bottom="0.5pt solid darkblue">
            <xsl:for-each select="./did/physloc[@encodinganalog='LDC']">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./text()"/>
              <xsl:if test="./@altrender">
               &#160;-&#160;
               <xsl:apply-templates select="./@altrender"/>
              </xsl:if>
              <xsl:if test="./emph/text()">
               <fo:inline>
                &#160;(
                <xsl:apply-templates select="./emph/text()"/>
                )
               </fo:inline>
              </xsl:if>
             </fo:block>
            </xsl:for-each>
           </fo:table-cell>
          </fo:table-row>
         </xsl:if>
        </fo:table-body>
       </fo:table>
       <xsl:if test="./did/physdesc/genreform !='' or ./did/physdesc/dimensions !='' or ./phystech  !='' or ./descgrp/scopecontent  !=''  or ./descgrp/odd  !='' or ./odd  !='' ">
        <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">DESCRIZIONE</fo:block>
        <fo:table table-layout="fixed" width="510pt" space-before="15pt">
         <fo:table-column column-width="170pt"/>
         <fo:table-column column-width="340pt"/>
         <fo:table-body>
          <xsl:if test="./did/physdesc[@label='container'] !=''">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">Consistenza</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./did/physdesc[@label='container']">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./genreform/text()"/>
               &#160;
               <xsl:apply-templates select="./extent/text()"/>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/genreform[@encodinganalog='MTC'] !=''">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MTC - materie e tecniche</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./did/physdesc[@encodinganalog='MT']/genreform[@encodinganalog='MTC']">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./text()"/>
               <xsl:if test="./emph/text() !=''">
                <fo:block/>
                <fo:inline font-style="italic">specifiche:</fo:inline>
                <xsl:apply-templates select="./emph/text()"/>
               </xsl:if>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/subject !=''">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MTCZ-MTCV - allestimento e varianti</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/subject/text()"/>
              <fo:block/>
              <xsl:if test="./did/physdesc[@encodinganalog='MT']/subject/emph/text() !=''">
               <fo:inline font-style="italic">varianti:</fo:inline>
               <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/subject/emph/text()"/>
              </xsl:if>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MIS']/@unit">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISU - unità di misura</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MIS']/@unit"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISA']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISA - altezza</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISA']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISL']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISL - larghezza</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISL']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISP']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISP - profondità</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISP']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISD']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISD - diametro</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISD']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISN']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISN - lunghezza</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISN']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISS']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISS - spessore</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISS']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISG']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISG - peso</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISG']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISV']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISV - varie</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISV']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISH']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISH - durata</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISH']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISB']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISB - memoria</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISB']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISI']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISI - ingombro</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISI']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISR']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MISR - mancanza</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MISR']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MIST']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MIST - validità</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/dimensions[@encodinganalog='MIST']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/physfacet[@encodinganalog='FIL']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">filigrana</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/physfacet[@encodinganalog='FIL']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./did/physdesc[@encodinganalog='MT']/physfacet[@encodinganalog='FRM']/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">FRM - formato</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./did/physdesc[@encodinganalog='MT']/physfacet[@encodinganalog='FRM']/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./phystech[@encodinganalog='STC']">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">STC - stato di conservazione</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./phystech[@encodinganalog='STC']/p/text()"/>
              <xsl:if test="./phystech[@encodinganalog='STC']/note/p/text()">
               <fo:block/>
               <fo:inline font-style="italic">specifiche:&#160;</fo:inline>
               <xsl:apply-templates select="./phystech[@encodinganalog='STC']/note/p/text()"/>
              </xsl:if>
              <xsl:if test="./phystech[@encodinganalog='STC']/phystech/p/text()">
               <fo:block/>
               <fo:inline font-style="italic">modalità:&#160;</fo:inline>
               <xsl:apply-templates select="./phystech[@encodinganalog='STC']//phystech/p/text()"/>
              </xsl:if>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./phystech[@encodinganalog='RST']/chronlist/chronitem">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">RST - restauro/i</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./phystech[@encodinganalog='RST']/chronlist/chronitem">
              <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
               <xsl:if test="./date[@encodinganalog='RSTD']/text()">
                <fo:inline font-style="italic">data:&#160;</fo:inline>
                <xsl:apply-templates select="./date[@encodinganalog='RSTD']/text()"/>
                <fo:block/>
               </xsl:if>
               <xsl:if test="./event/emph/text()">
                <fo:inline font-style="italic">situazione:&#160;</fo:inline>
                <xsl:apply-templates select="./event/emph/text()"/>
                <fo:block/>
               </xsl:if>
               <xsl:if test="./event/name[@encodinganalog='RSTN']/text()">
                <fo:inline font-style="italic">operatore:&#160;</fo:inline>
                <xsl:apply-templates select="./event/name[@encodinganalog='RSTN']/text()"/>
                <fo:block/>
               </xsl:if>
               <xsl:if test="./event/text()">
                <fo:inline font-style="italic">intervento:&#160;</fo:inline>
                <xsl:apply-templates select="./event/text()"/>
                <fo:block/>
               </xsl:if>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='DESO']/p/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">DESO - descrizione dell'oggetto</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='DESO']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='DA']/scopecontent[@encodinganalog='DESS']/p/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">DESS - descrizione del soggetto</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./descgrp[@encodinganalog='DA']/scopecontent[@encodinganalog='DESS']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='NSC']/p/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">NSC - notizie storico-critiche</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='NSC']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='DA']/scopecontent[@encodinganalog='ISR']">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ISR - iscrizioni</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./descgrp[@encodinganalog='DA']/scopecontent[@encodinganalog='ISR']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='STM']">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">STM - stemmi, emblemi, marchi</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./descgrp[@encodinganalog='DA']/odd[@encodinganalog='STM']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./odd[@encodinganalog='STI']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">STI - stima</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" margin-bottom="3pt" margin-top="3pt" linefeed-treatment="preserve" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./odd[@encodinganalog='STI']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
         </fo:table-body>
        </fo:table>
       </xsl:if>
       <xsl:if test="./descgrp/accessrestrict or ./descgrp/custodhist or ./descgrp/acqinfo or ./descgrp/userestrict ">
        <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">STATUS</fo:block>
        <fo:table table-layout="fixed" width="510pt" space-before="15pt">
         <fo:table-column column-width="170pt"/>
         <fo:table-column column-width="340pt"/>
         <fo:table-body>
          <xsl:if test="./descgrp[@encodinganalog='TU']/accessrestrict/legalstatus/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">CDG - condizione giuridica</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/accessrestrict/legalstatus/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='TU']/acqinfo[@encodinganalog='ACQ']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ACQ - acquisizione</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/acqinfo[@encodinganalog='ACQ']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='TU']/custodhist[@encodinganalog='ALN']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ALN - mutamenti della condizione giuridica o materiale</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/custodhist[@encodinganalog='ALN']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='TU']/accessrestrict/accessrestrict[@encodinganalog='NVC']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">NVC - provvedimenti di tutela</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/accessrestrict/accessrestrict[@encodinganalog='NVC']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='TU']/odd[@encodinganalog='ESP']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ESP - esportazioni</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/odd[@encodinganalog='ESP']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./descgrp[@encodinganalog='TU']/userestrict[@encodinganalog='CPR']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">CPR - copyright</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
              <xsl:apply-templates select="./descgrp[@encodinganalog='TU']/userestrict[@encodinganalog='CPR']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
         </fo:table-body>
        </fo:table>
       </xsl:if>
       <xsl:if test="./bibliography !='' or ./relatedmaterial !='' or ./altformavail !='' ">
        <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">DOCUMENTAZIONE DI RIFERIMENTO</fo:block>
        <fo:table table-layout="fixed" width="510pt" space-before="15pt">
         <fo:table-column column-width="170pt"/>
         <fo:table-column column-width="340pt"/>
         <fo:table-body>
          <xsl:if test="./relatedmaterial/p/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">RSE - riferimento altre schede</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./relatedmaterial">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./p/text()"/>
               <xsl:if test="./p/num/text()">
                <fo:inline>&#160;</fo:inline>
                <xsl:apply-templates select="./p/num/text()"/>
               </xsl:if>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./relatedmaterial/p/note">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D" border-top="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">RSE - riferimento altre schede</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-top="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./relatedmaterial/p/note/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./altformavail[@encodinganalog='FTA']/list[@type='simple']/item">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">FTA - documentazione fotografica</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:for-each select="./altformavail[@encodinganalog='FTA']/list[@type='simple']/item">
               <fo:block>
                <xsl:if test="./genreform/@type">
                 <xsl:apply-templates select="./genreform/@type"/>
                 <fo:inline>:&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./genreform/text()">
                 <xsl:apply-templates select="./genreform/text()"/>
                </xsl:if>
                <xsl:if test="./genreform/emph/text()">
                 <fo:inline>&#160;</fo:inline>
                 <xsl:apply-templates select="./genreform/emph/text()"/>
                </xsl:if>
                <xsl:if test="./num[@encodinganalog='FTAN']/text()">
                 <fo:inline>;&#160;</fo:inline>
                 <xsl:apply-templates select="./num[@encodinganalog='FTAN']/text()"/>
                </xsl:if>
                <xsl:if test="./note[@encodinganalog='FTAT']/p/text()">
                 <fo:inline>;&#160;</fo:inline>
                 <xsl:apply-templates select="./note[@encodinganalog='FTAT']/p/text()"/>
                </xsl:if>
                <xsl:if test="./persname/text()">
                 <fo:inline>&#160;/&#160;autore:&#160;</fo:inline>
                 <xsl:apply-templates select="./persname/text()"/>
                </xsl:if>
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./altformavail[@encodinganalog='VDC']/list/item">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">VDC - documentazione video</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:for-each select="./altformavail[@encodinganalog='VDC']/list/item">
               <fo:block>
                <xsl:if test="./genreform/@type">
                 <xsl:apply-templates select="./genreform/@type"/>
                 <fo:inline>:&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./genreform/text()">
                 <xsl:apply-templates select="./genreform/text()"/>
                </xsl:if>
                <xsl:if test="./genreform/emph/text()">
                 <fo:inline>;&#160;</fo:inline>
                 <xsl:apply-templates select="./genreform/emph/text()"/>
                </xsl:if>
                <xsl:if test="./note/p/text()">
                 <fo:inline>;&#160;</fo:inline>
                 <xsl:apply-templates select="./note/p/text()"/>
                </xsl:if>
                <xsl:if test="./persname/text()">
                 <fo:inline>&#160;/&#160;autore:&#160;</fo:inline>
                 <xsl:apply-templates select="./persname/text()"/>
                </xsl:if>
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./altformavail[@encodinganalog='ADM']/list/item">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">ADM - documentazione multimediale</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:for-each select="./altformavail[@encodinganalog='ADM']/list/item">
               <fo:block>
                <xsl:if test="./genreform/@type">
                 <xsl:apply-templates select="./genreform/@type"/>
                 <fo:inline>:&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./genreform/text()">
                 <xsl:apply-templates select="./genreform/text()"/>
                </xsl:if>
                <xsl:if test="./note/p/text()">
                 <fo:inline>;&#160;</fo:inline>
                 <xsl:apply-templates select="./note/p/text()"/>
                </xsl:if>
                <xsl:if test="./persname/text()">
                 <fo:inline>&#160;/&#160;autore:&#160;</fo:inline>
                 <xsl:apply-templates select="./persname/text()"/>
                </xsl:if>
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./bibliography[@encodinganalog='FNT']/archref">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">FNT - fonti archivistiche</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./bibliography[@encodinganalog='FNT']/archref">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./text()"/>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./bibliography[@encodinganalog='BIB']/bibref">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">BIB - bibliografia</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:for-each select="./bibliography[@encodinganalog='BIB']/bibref">
               <fo:block>
                <xsl:if test="./title[@encodinganalog='BIBT']/@type !=''">
                 <xsl:apply-templates select="./title[@encodinganalog='BIBT']/@type"/>
                 <fo:inline>:&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./persname[@encodinganalog='BIBA']/text() !=''">
                 <xsl:apply-templates select="./persname[@encodinganalog='BIBA']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./persname[@encodinganalog='BIBC']/text() !=''">
                 <xsl:apply-templates select="./persname[@encodinganalog='BIBC']/text()"/>
                 <fo:inline>&#160;(a cura di),&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./title[@encodinganalog='BIBT']/text() !=''">
                 <xsl:apply-templates select="./title[@encodinganalog='BIBT']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./edition[@encodinganalog='BIBG']/text() !=''">
                 <xsl:apply-templates select="./edition[@encodinganalog='BIBG']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./imprint/geogname[@encodinganalog='BIBL']/text() !=''">
                 <xsl:apply-templates select="./imprint/geogname[@encodinganalog='BIBL']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./imprint/publisher[@encodinganalog='BIBZ']/text() !=''">
                 <xsl:apply-templates select="./imprint/publisher[@encodinganalog='BIBZ']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./imprint/date[@encodinganalog='BIBD']/text() !=''">
                 <xsl:apply-templates select="./imprint/date[@encodinganalog='BIBD']/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./num[@encodinganalog='BIBV']/text() !=''">
                 <xsl:apply-templates select="./num[@encodinganalog='BIBV']/text()"/>
                </xsl:if>
                <xsl:if test="./num[@encodinganalog='BIBP']/text() !=''">
                 <fo:inline>,&#160;</fo:inline>
                 <xsl:apply-templates select="./num[@encodinganalog='BIBP']/text()"/>
                </xsl:if>
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./bibliography[@encodinganalog='BIB']/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">BIL - bibliografia (citazioni per esteso)</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./bibliography[@encodinganalog='BIB']/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./bibliography[@encodinganalog='BSE']/extref">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">BSE - bibliografia in rete</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./bibliography[@encodinganalog='BSE']/extref">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
               <xsl:apply-templates select="./title/text()"/>
               <xsl:if test="./@href !=''">
                <fo:inline>&#160;/&#160;</fo:inline>
                <xsl:apply-templates select="./@href"/>
               </xsl:if>
               <xsl:if test="./date/text() !=''">
                <fo:inline>&#160;(</fo:inline>
                <xsl:apply-templates select="./date/text()"/>
                <fo:inline>)</fo:inline>
               </xsl:if>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./bibliography/list/item">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">MST - mostre</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:for-each select="./bibliography/list/item">
               <fo:block>
                <xsl:if test="./title/text()!=''">
                 <xsl:apply-templates select="./title/text()"/>
                 <fo:inline>,&#160;</fo:inline>
                </xsl:if>
                <xsl:if test="./geogname/emph/text()!=''">
                 <xsl:apply-templates select="./geogname/emph/text()"/>
                </xsl:if>
                <xsl:if test="./geogname/text()!=''">
                 &#160;(
                 <xsl:apply-templates select="./geogname/text()"/>
                 ),&#160;
                </xsl:if>
                <xsl:if test="./corpname/text()!=''">
                 <xsl:apply-templates select="./corpname/text()"/>
                </xsl:if>
                <xsl:if test="./date/text()!=''">
                 <fo:inline>,&#160;</fo:inline>
                 <xsl:apply-templates select="./date/text()"/>
                </xsl:if>
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
         </fo:table-body>
        </fo:table>
       </xsl:if>
       <xsl:if test="./controlaccess  !=''">
        <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">CHIAVI DI ACCESSO</fo:block>
        <fo:table table-layout="fixed" width="510pt" space-before="15pt">
         <fo:table-column column-width="170pt"/>
         <fo:table-column column-width="340pt"/>
         <fo:table-body>
          <xsl:if test="./controlaccess/subject/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">soggetti / temi</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./controlaccess/subject">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./text()"/>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./controlaccess/name/text()">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">classificazione oggetto</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <xsl:for-each select="./controlaccess/name">
              <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">
               <xsl:apply-templates select="./text()"/>
              </fo:block>
             </xsl:for-each>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
         </fo:table-body>
        </fo:table>
       </xsl:if>
       <xsl:if test="./processinfo  !='' or ./note/p  !=''">
        <fo:block font-size="10pt" font-weight="bold" margin-bottom="6pt" margin-top="8pt" text-align="center" color="#C65F1D">ANNOTAZIONI E COMPILAZIONE</fo:block>
        <fo:table table-layout="fixed" width="510pt" space-before="15pt">
         <fo:table-column column-width="170pt"/>
         <fo:table-column column-width="340pt"/>
         <fo:table-body>
          <xsl:if test="./note/p">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">AN - annotazioni</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:apply-templates select="./note/p/text()"/>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
          <xsl:if test="./processinfo">
           <fo:table-row>
            <fo:table-cell border-bottom="0.5pt solid darkblue" border-right="0.5pt solid darkblue" color="#C65F1D">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt">CMP - compilazione</fo:block>
            </fo:table-cell>
            <fo:table-cell border-bottom="0.5pt solid darkblue">
             <fo:block font-size="10pt" start-indent="3pt" end-indent="3pt" margin-bottom="3pt" margin-top="3pt" white-space-treatment="ignore-if-after-linefeed">
              <xsl:if test="./processinfo/p/persname">
               Funzionario responsabile:&#160;
               <xsl:apply-templates select="./processinfo/p/persname/text()"/>
              </xsl:if>
              <xsl:for-each select="./processinfo/list/item">
               <fo:block>
                <xsl:apply-templates select="./text()"/>
                :
                <xsl:apply-templates select="./persname/text()"/>
                &#160;(
                <xsl:apply-templates select="./date/text()"/>
                )
               </fo:block>
              </xsl:for-each>
             </fo:block>
            </fo:table-cell>
           </fo:table-row>
          </xsl:if>
         </fo:table-body>
        </fo:table>
       </xsl:if>
      </fo:block>
      <fo:block text-align="center" margin-bottom="20pt" margin-top="20pt" color="#C65F1D"/>
     </xsl:for-each>
    </fo:flow>
   </fo:page-sequence>
  </fo:root>
 </xsl:template>
 <xsl:template match="abstract">
  <xsl:apply-templates/>
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
 <xsl:template name="generaLivello">
  <xsl:value-of select="count(ancestor::dsc)*2+1"/>
 </xsl:template>
</xsl:stylesheet>