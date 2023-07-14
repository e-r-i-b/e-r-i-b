<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<deposit-claim>
			<product>
				<xsl:value-of select="product"/>
			</product>
			<period>
				<xsl:value-of select="period"/>
			</period>
			<fromAccount>
				<xsl:value-of select="fromAccount"/>
			</fromAccount>
			<toAccount>
				<xsl:value-of select="toAccount"/>
			</toAccount>
			<currency>
				<xsl:value-of select="currency"/>
			</currency>
			<amount>
				<xsl:value-of select="amount"/>
			</amount>
		</deposit-claim>
	</xsl:template>
</xsl:stylesheet>
