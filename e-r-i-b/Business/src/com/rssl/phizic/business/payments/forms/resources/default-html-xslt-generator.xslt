<?xml version="1.0" encoding="windows-1251"?>
<?altova_samplexml MobilePayment\MobilePayment.pfd.xml?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:axsl="file://namespace.alias">	
	<xsl:namespace-alias stylesheet-prefix="axsl" result-prefix="xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:template match="payment-metadata">
		<axsl:stylesheet version="2.0">
			<axsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
			<axsl:param name="mode" select="'view'"/>
			<axsl:param name="personAvailable" select="true()"/>			

			<axsl:template match="/">
				<axsl:choose>
					<axsl:when test="$mode = 'edit'">
						<axsl:apply-templates mode="edit"/>
					</axsl:when>
					<axsl:when test="$mode = 'view'">
						<axsl:apply-templates mode="view"/>
					</axsl:when>
				</axsl:choose>			
			</axsl:template>
			
			<axsl:template match="metadata-data" mode="edit">
				<table>
					<xsl:for-each select="fields/field">
						<xsl:call-template name="row">
							<xsl:with-param name="readOnly" select="false()"/>
						</xsl:call-template>
					</xsl:for-each>
				</table>				
			</axsl:template>
			
			<axsl:template match="metadata-data" mode="view">
				<table>
					<xsl:for-each select="fields/field">
						<xsl:call-template name="row">
							<xsl:with-param name="readOnly" select="true()"/>
						</xsl:call-template>
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
