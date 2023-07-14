<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="*/field" mode="control">
		<xsl:value-of select="."/>
	</xsl:template>

	<xsl:template match="*[field/@name='type'][field/@name='currencyCode'][field/@name='amountDecimal']" mode="control">
		<xsl:value-of select="./@key"/>
		[<xsl:value-of select="field[@name='type']"/>]
		<xsl:value-of select="format-number(field[@name='amountDecimal'], '0.00')"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="field[@name='currencyCode']"/>
	</xsl:template>

	<xsl:template name="select">
		<xsl:param name="name"/>
		<xsl:param name="list"/>
		<xsl:param name="value" select="*[name()=$name]"/>
			<select id="{$name}" name="{$name}">
				<xsl:for-each select="$list">
					<option>
						<xsl:attribute name="value">
							<xsl:value-of select="./@key"/>
						</xsl:attribute>
						<xsl:if test="$value=./@key">
							<xsl:attribute name="selected">true</xsl:attribute>
						</xsl:if>
						<xsl:apply-templates select="." mode="control"/>
					</option>
				</xsl:for-each>
			</select>
	</xsl:template>

</xsl:stylesheet>