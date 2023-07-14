<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>
            <payer-account>
                <xsl:value-of select="cardNumber"/>
            </payer-account>

            <amount>
                <xsl:value-of select="sellAmount"/>
            </amount>

            <exact-amount>
                <xsl:value-of select="'charge-off-field-exact'"/>
            </exact-amount>

             <extra-parameters>
                 <parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                 <parameter name="auto-payment-link-id" value="{linkId}" type="string"/>
                 <parameter name="cardId" value="{cardId}" type="string"/>
            </extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>