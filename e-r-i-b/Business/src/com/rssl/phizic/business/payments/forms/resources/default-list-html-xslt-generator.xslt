<?xml version="1.0" encoding="windows-1251"?>
<?altova_samplexml MobilePayment\MobilePayment.list-pfd.xml?>
<?НЕ ИСПОЛЬЗУЙТЕ ВСТРОЕННЫЙ XSLT PROCESSOR ОТ ALTOVA!!!?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:axsl="file://namespace.alias">	
	<xsl:namespace-alias stylesheet-prefix="axsl" result-prefix="xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
		
	<xsl:template match="payment-list">
		<axsl:stylesheet version="2.0">
			<axsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
			<axsl:param name="personAvailable" select="true()"/>			
			
			<axsl:template match="/list-data">
				<table border="1">
					<axsl:apply-templates select="entity-list"/>
				</table>
			</axsl:template>

			<xsl:apply-templates select="hql-query"/>
		</axsl:stylesheet>
	</xsl:template>

	<xsl:template match="hql-query">
		<axsl:template name="header" match="entity-list">
			<tr>
				<td><xsl:value-of select="returnKey/@property"/></td>
				<xsl:for-each select="return">
					<td><xsl:value-of select="@property"/></td>
				</xsl:for-each>
			</tr>
			<axsl:apply-templates/>
		</axsl:template>
		
		<axsl:template name="row" match="entity">
			<tr>
				<td>
					<input type="checkbox" name="selectedIds">
						<axsl:attribute name="value">
							<axsl:value-of select="@key"/>
						</axsl:attribute>
					</input>
				</td>
				<xsl:for-each select="return">
					<td>
						<axsl:value-of>
							<xsl:attribute name="select">
								<xsl:value-of select="@property"/>
							</xsl:attribute>
						</axsl:value-of>
					</td>
				</xsl:for-each>
			</tr>
		</axsl:template>
	</xsl:template>	
</xsl:stylesheet>
