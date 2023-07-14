<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" indent="yes"/>
	<xsl:template match="form-data">
		<lossingPassbook_q>
            <clientId>
                <xsl:value-of select="receiv-pay-alias"/>
            </clientId>

            <signature>
                <xsl:value-of select="amount"/>
            </signature>

            <Copying>   </Copying>

            <Account>
                <xsl:value-of select="ground"/>
            </Account>

        </lossingPassbook_q>
	</xsl:template>
</xsl:stylesheet>
