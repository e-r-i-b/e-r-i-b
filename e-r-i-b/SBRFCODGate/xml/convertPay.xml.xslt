<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" indent="yes"/>
	<xsl:template match="payment">
		<executePayment_q>
            <clientId></clientId>

            <account>
                <xsl:value-of select="receiver-account"/>
            </account>

            <alias>
                <xsl:value-of select="receiv-pay-alias"/>
            </alias>

            <sum>
                <xsl:value-of select="amount"/>
            </sum>

            <commision></commision>

            <debitAccount>
                <xsl:value-of select="payer-account"/>
            </debitAccount>

            <purpuce>
                <xsl:value-of select="ground"/>
            </purpuce>

            <beneficiar>
                <xsl:value-of select="receiver-name"/>
            </beneficiar>

            <specialClient>
              <specialClientNplat>

              </specialClientNplat>
              <vvodNumber></vvodNumber>
              <requisites>
   	            <xsl:for-each select="/payment/extra-parameters/*">
		         <xsl:if test="substring(name(),1,9)='addField_'">
				    <parameter name="{name()}" value="{text()}" type="string"/>
                 </xsl:if>
	            </xsl:for-each>
              </requisites>
            </specialClient>

            <nplatReuisites>

            </nplatReuisites>
        </executePayment_q>
	</xsl:template>
</xsl:stylesheet>