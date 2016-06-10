<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes" standalone="yes" />
	<xsl:template match="c">
		<xsl:element name="c">
			<xsl:attribute name="id"><xsl:value-of select="./@id" /></xsl:attribute>
			<xsl:attribute name="level"><xsl:value-of select="./@level" /></xsl:attribute>
			<xsl:if test="./controlaccess[child::head/text()='access points']/persname">
				<xsl:for-each select="./controlaccess[child::head/text()='access points']/persname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">persone</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">persname</xsl:attribute>
						<xsl:if test="./@role">
						<xsl:attribute name="role"><xsl:value-of select="./@role" /></xsl:attribute>
						</xsl:if>
						<xsl:if test="./@altrender">
						<xsl:attribute name="altrender"><xsl:value-of select="./@altrender" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./controlaccess[child::head/text()='access points']/corpname">
				<xsl:for-each select="./controlaccess[child::head/text()='access points']/corpname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">enti</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">corpname</xsl:attribute>
						<xsl:if test="./@role">
						<xsl:attribute name="role"><xsl:value-of select="./@role" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./controlaccess[child::head/text()='access points']/geogname">
				<xsl:for-each select="./controlaccess[child::head/text()='access points']/geogname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">luoghi</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">geogname</xsl:attribute>
						<xsl:if test="./emph/text()">
						<xsl:attribute name="altrender"><xsl:value-of select="./emph/text()" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./controlaccess[child::head/text()='access points']/famname">
				<xsl:for-each select="./controlaccess[child::head/text()='access points']/famname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">famiglie</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">famname</xsl:attribute>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./controlaccess[child::head/text()='access points']/subject">
				<xsl:for-each select="./controlaccess[child::head/text()='access points']/subject">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">soggetti</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">subject</xsl:attribute>
						<xsl:if test="./emph/text()">
						<xsl:attribute name="altrender"><xsl:value-of select="./emph/text()" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./controlaccess[child::head/text()='cast']/persname">
				<xsl:for-each select="./controlaccess[child::head/text()='cast']/persname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">personaggi e interpreti</xsl:attribute>
						<xsl:attribute name="label">controlaccess</xsl:attribute>
						<xsl:attribute name="type">cast</xsl:attribute>
						<xsl:if test="./@role">
						<xsl:attribute name="role"><xsl:value-of select="./@role" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./did/abstract/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">abstract</xsl:attribute>
					<xsl:attribute name="label">abstract</xsl:attribute>
					<xsl:value-of select="./did/abstract/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/langmaterial/language[@altrender='main language']/text()">
				<xsl:element name="xDamsElement">
				 	<xsl:attribute name="presentation">lingua</xsl:attribute> 
					<xsl:attribute name="label">main language</xsl:attribute>
					<xsl:value-of select="./did/langmaterial/language[@altrender='main language']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/materialspec[@label='genre']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">genere</xsl:attribute> 
					<xsl:attribute name="label">genre</xsl:attribute>
					<xsl:value-of select="./did/materialspec[@label='genre']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/materialspec[@label='typology']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">tipologia</xsl:attribute> 
					<xsl:attribute name="label">typology</xsl:attribute>
					<xsl:value-of select="./did/materialspec[@label='typology']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/origination[@label='main credits']/persname">
				<xsl:for-each select="./did/origination[@label='main credits']/persname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">responsabilità principali</xsl:attribute> 
						<xsl:attribute name="label">main credits</xsl:attribute>
						<xsl:attribute name="type">persname</xsl:attribute>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./did/origination[@label='main credits']/corpname">
				<xsl:for-each select="./did/origination[@label='main credits']/corpname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">responsabilità principali</xsl:attribute> 
						<xsl:attribute name="label">main credits</xsl:attribute>
						<xsl:attribute name="type">corpname</xsl:attribute>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./did/origination[@label='production crew']/persname">
				<xsl:for-each select="./did/origination[@label='production crew']/persname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">altre responsabilità</xsl:attribute> 
						<xsl:attribute name="label">production crew</xsl:attribute>
						<xsl:attribute name="type">persname</xsl:attribute>
						<xsl:if test="./@role">
							<xsl:attribute name="role"><xsl:value-of select="./@role" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./did/origination[@label='production crew']/corpname">
				<xsl:for-each select="./did/origination[@label='production crew']/corpname">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">altre responsabilità</xsl:attribute> 
						<xsl:attribute name="label">production crew</xsl:attribute>
						<xsl:attribute name="type">corpname</xsl:attribute>
						<xsl:if test="./@role">
							<xsl:attribute name="role"><xsl:value-of select="./@role" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="./did/unittitle/bibseries/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">testata/serie</xsl:attribute> 
					<xsl:attribute name="label">bibseries title</xsl:attribute>
					<xsl:value-of select="./did/unittitle/bibseries/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/unittitle/bibseries/num/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">numero testata/serie</xsl:attribute>
					<xsl:attribute name="label">bibseries num</xsl:attribute>
					<xsl:value-of select="./did/unittitle/bibseries/num/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/unittitle/text()">
				<xsl:element name="xDamsElement">
				 	<xsl:attribute name="presentation">titolo attribuito</xsl:attribute>
					<xsl:attribute name="label">assigned title</xsl:attribute>
					<xsl:value-of select="./did/unittitle/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/unittitle/title[@type='proper']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">titolo proprio</xsl:attribute>
					<xsl:attribute name="label">proper title</xsl:attribute>
					<xsl:value-of select="./did/unittitle/title[@type='proper']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/unittitle/date[@type='production']">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">data di produzione</xsl:attribute>
					<xsl:attribute name="label">production</xsl:attribute>
					<xsl:attribute name="type">date</xsl:attribute>
					<xsl:attribute name="normal"><xsl:value-of select="./did/unittitle/date[@type='production']/@normal" /></xsl:attribute>
					<xsl:value-of select="./did/unittitle/date[@type='production']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/unittitle/geogname[@role='production']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">luogo di produzione</xsl:attribute>
					<xsl:attribute name="label">production</xsl:attribute>
					<xsl:attribute name="type">geogname</xsl:attribute>
					<xsl:value-of select="./did/unittitle/geogname[@role='production']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/archref/physloc/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">file location</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">file location</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/archref/physloc/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/dimensions/dimensions[@label='start']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">time code: iniziale</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">time code start</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/dimensions/dimensions[@label='start']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/dimensions/dimensions[@label='end']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">time code: finale</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">time code end</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/dimensions/dimensions[@label='end']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/dimensions/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">durata</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">duration</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/dimensions/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/extref[@altrender='url web']/@href">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">url web</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">url web</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/extref[@altrender='url web']/@href" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/genreform/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">formato file</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">genreform</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/genreform/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/physfacet[@type='colour']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">cromatismo</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">colour</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/physfacet[@type='colour']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='digital object']/physfacet[@type='sound']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">audio</xsl:attribute>
					<xsl:attribute name="label">digital object</xsl:attribute>
					<xsl:attribute name="type">sound</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='digital object']/physfacet[@type='sound']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/archref/physloc/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">collocazione</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">collocation</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/archref/physloc/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/archref/container/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">identificativo unità di conservazione</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">container</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/archref/container/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/archref/container/@type">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">tipo</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">container type</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/archref/container/@type" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/archref/unitid/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">numero inventario</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">inventory</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/archref/unitid/text()" />
				</xsl:element>
			</xsl:if>
			<!-- <xsl:element name="xDamsElement"> <xsl:attribute name="label">video</xsl:attribute> <xsl:attribute name="type">time code start</xsl:attribute> <xsl:value-of select="./did/physdesc[@label='video']/dimensions/dimensions[@label='start']/text()"/> </xsl:element> <xsl:element name="xDamsElement"> <xsl:attribute name="label">video</xsl:attribute> <xsl:attribute name="type">time code end</xsl:attribute> <xsl:value-of select="./did/physdesc[@label='video']/dimensions/dimensions[@label='end']/text()"/> </xsl:element> -->
			<xsl:if test="./did/physdesc[@label='video']/dimensions/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">durata</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">duration</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/dimensions/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/extent/@unit">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">unità fisica</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">unit</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/extent/@unit" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/extent/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">unità fisica</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">occurrence</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/extent/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/genreform/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">tipologia di supporto</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">genreform</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/genreform/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/genreform/@type">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">tipologia di materiale</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">typology</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/genreform/@type" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/physfacet[@type='colour']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">colore</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">colour</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/physfacet[@type='colour']/text()" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="./did/physdesc[@label='video']/physfacet[@type='sound']/text()">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="presentation">audio</xsl:attribute>
					<xsl:attribute name="label">video</xsl:attribute>
					<xsl:attribute name="type">sound</xsl:attribute>
					<xsl:value-of select="./did/physdesc[@label='video']/physfacet[@type='sound']/text()" />
				</xsl:element>
			</xsl:if>
			<!-- da rivedere manca time code iniziale e finale -->
			<xsl:if test="./scopecontent/list/item">
				<xsl:for-each select="./scopecontent/list/item">
					<xsl:element name="xDamsElement">
						<xsl:attribute name="presentation">sequenza</xsl:attribute>
						<xsl:attribute name="label">sequence</xsl:attribute>
						<xsl:if test="./extref[@role='start']/text()">
						<xsl:attribute name="id"><xsl:value-of select="./extref[@role='start']/text()" /></xsl:attribute>
						</xsl:if>
						<xsl:if test="./extref[@role='end']/text()">
							<xsl:attribute name="altrender"><xsl:value-of select="./extref[@role='end']/text()" /></xsl:attribute>
						</xsl:if>
						<xsl:value-of select="./text()" />
					</xsl:element>
				</xsl:for-each>
			</xsl:if>
			<!-- da rivedere 
			<xsl:for-each select="processinfo/list/item">
				<xsl:element name="xDamsElement">
					<xsl:attribute name="label">processinfo</xsl:attribute>
					<xsl:attribute name="normal"><xsl:value-of select="./date/text()" /></xsl:attribute>
					<xsl:attribute name="role"><xsl:value-of select="./persname/text()" /></xsl:attribute>
					<xsl:value-of select="./text()" />
				</xsl:element>
			</xsl:for-each>
			-->
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>