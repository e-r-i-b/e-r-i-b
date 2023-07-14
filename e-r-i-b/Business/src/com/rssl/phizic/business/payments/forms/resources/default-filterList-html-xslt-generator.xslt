<?xml version="1.0" encoding="windows-1251"?>
<?altova_samplexml MobilePayment\MobilePayment.list-pfd.xml?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:axsl="file://namespace.alias">	
	<xsl:namespace-alias stylesheet-prefix="axsl" result-prefix="xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:template match="payment-list">
		<axsl:stylesheet version="2.0">
			<axsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
			<axsl:param name="personAvailable" select="true()"/>			

			<axsl:template match="/">
				<axsl:apply-templates select="list-data/filter-data"/>
			</axsl:template>
			
			<axsl:template match="filter-data">
				<table>
					<xsl:for-each select="filter/fields/field">
						<xsl:call-template name="row"/>
					</xsl:for-each>
				</table>				
			</axsl:template>			
		</axsl:stylesheet>
	</xsl:template>

	<xsl:template name="row" match="fields/field">
		<xsl:param name="readOnly" select="false()"/>
		<tr>
			<td><xsl:value-of select="@description"/></td>
			<td>
				<xsl:apply-templates mode="control" select=".">
					<xsl:with-param name="readOnly" select="$readOnly"/>
				</xsl:apply-templates>
			</td>
		</tr>
	</xsl:template>	

	<xsl:include href="controls.xslt"/>

</xsl:stylesheet>
